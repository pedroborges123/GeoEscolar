package br.es.geo.controler;

import br.es.geo.modelo.Itinerario;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOitinerario;
import br.es.geo.dao.DAOtransporteescolar;
import br.es.geo.modelo.Motorista;
import br.es.geo.modelo.TransporteEscolar;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.CellEditEvent;

@Named("itinerarioController")
@SessionScoped
public class ItinerarioController implements Serializable {

    private Itinerario current;
    private DataModel items = null;

    private DAOitinerario ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ItinerarioController() {
    }

    public Itinerario getSelected() {
        if (current == null) {
            current = new Itinerario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DAOitinerario getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    int num = 0;
                    try {
                        num = getFacade().findAll().size();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AdmController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return num;
                }

                @Override
                public DataModel createPageDataModel() {
                    List<Itinerario> sublist = new ArrayList<>();
                    try {
                        //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                        sublist = getFacade().findAll().subList(getPageFirstItem(), getPageFirstItem() + getPageSize());
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AdmController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return new ListDataModel(sublist);
                }
            };
        }
        return pagination;
    }

    public void create() {
        try {
            Motorista m = (Motorista) JsfUtil.getElementSession("usuario");
            DAOtransporteescolar dao = new DAOtransporteescolar();
            TransporteEscolar t = dao.findbyMotoristaID(m.getId());
            current.setTransEscolar(t);
            getFacade().insert(current);
            JsfUtil.addSuccessMessage("Itinerario Cadastrado");
            this.prepareItinerarioByMotorista();
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, "Falha no cadastro");
            this.prepareItinerarioByMotorista();
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        ejbFacade = new DAOitinerario();

        try {
            System.out.println("aqui");
            System.out.println(oldValue);
            System.out.println(newValue);
            if (newValue != null && !newValue.equals(oldValue)) {
                JsfUtil.addSuccessMessage("Celula Aterada, Velha: " + oldValue + ", Nova:" + newValue);
                Itinerario i = (Itinerario) event.getSource();
                System.out.println("aqui2");
                 System.out.println(i);
                ejbFacade.update(i);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ItinerarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void prepareItinerarioByMotorista() {
        ejbFacade = new DAOitinerario();
        this.current = new Itinerario();
        JsfUtil.redirectTo("motorista/itinerario.xhtml");
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public boolean getQuantidadeItinerarioMaior3() {
        
        return this.selectedItemIndex <3;

    }

    //retorna uma lista de itinerarios de acordo com o motorista
    public DataModel getItinerarioByMotorista() {

        try {
            Motorista m = (Motorista) JsfUtil.getElementSession("usuario");
            DAOtransporteescolar daoTE = new DAOtransporteescolar();
            TransporteEscolar te = daoTE.findbyMotoristaID(m.getId());

            ListDataModel<Itinerario> list = new ListDataModel<>(ejbFacade.findbyTransporteID(te.getId()));
            this.selectedItemIndex = list.getRowCount();
            return list;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ItinerarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ListDataModel();
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public SelectItem[] getItemsAvailableSelectMany() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Itinerario getItinerario(long id) {
        Itinerario i = new Itinerario();
        try {
            i = ejbFacade.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ItinerarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    @FacesConverter(forClass = Itinerario.class)
    public static class ItinerarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItinerarioController controller = (ItinerarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itinerarioController");
            return controller.getItinerario(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Itinerario 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Itinerario 
            // and therefore Converter.getKey() method could not be pre-generated.
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Itinerario) {
                Itinerario o = (Itinerario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Itinerario.class.getName());
            }
        }

    }

}
