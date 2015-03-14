package br.es.geo.controler;

import br.es.geo.modelo.Motorista;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOmotorista;
import br.es.geo.dao.DAOtransporteescolar;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

@Named("motoristaController")
@SessionScoped
public class MotoristaController implements Serializable {

    private Motorista current;
    private DataModel items = null;
    private TransporteEscolar transporte;
    private DAOmotorista ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DAOtransporteescolar ejbFacedeTransporte;

    public MotoristaController() {
    }

    public Motorista getSelected() {
        if (current == null) {
            current = new Motorista();
            transporte = new TransporteEscolar();

            selectedItemIndex = -1;
        }
        return current;
    }

    public DAOtransporteescolar getEjbFacedeTransporte() {
        return ejbFacedeTransporte;
    }

    public void setEjbFacedeTransporte(DAOtransporteescolar ejbFacedeTransporte) {
        this.ejbFacedeTransporte = ejbFacedeTransporte;
    }

    public Motorista getCurrent() {
        return current;
    }

    public void setCurrent(Motorista current) {
        this.current = current;
    }

    public TransporteEscolar getTransporte() {
        return transporte;
    }

    public void setTransporte(TransporteEscolar transporte) {
        this.transporte = transporte;
    }

    public DAOmotorista getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(DAOmotorista ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    private DAOmotorista getFacade() {
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
                    List<Motorista> sublist = new ArrayList<>();
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

    public void prepareCreate() {
        this.ejbFacade = new DAOmotorista();
        this.ejbFacedeTransporte = new DAOtransporteescolar();
        current = new Motorista();
        this.transporte = new TransporteEscolar();
        selectedItemIndex = -1;
        JsfUtil.redirectTo("motorista/Create.xhtml");
    }

    public void create() {
        try {

            long id = getFacade().insert(current);
            current.setId(id);
            this.transporte.setMotorista(current);
            getEjbFacedeTransporte().insert(transporte);
            JsfUtil.addSuccessMessage("Motorista Cadastrado Com Sucesso");
            JsfUtil.redirectTo("index.xhtml");
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            prepareCreate();
        }
    }

    public void update() {
        try {

            getFacade().update(current);
            this.ejbFacedeTransporte.update(transporte);
            JsfUtil.addSuccessMessage("Motorista Atualizado");
            JsfUtil.redirectTo("motorista/Edit.xhtml");
        } catch (ClassNotFoundException | SQLException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public void prepareEdit() {

        try {
            current = (Motorista) JsfUtil.getElementSession("usuario");
            this.ejbFacade = new DAOmotorista();
            this.ejbFacedeTransporte = new DAOtransporteescolar();
            this.transporte = this.ejbFacedeTransporte.findbyMotoristaID(current.getId());
            JsfUtil.redirectTo("motorista/Edit.xhtml");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MotoristaController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex,"Error");
            JsfUtil.redirectTo("motorista/motorista.xhtml");
        }

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

    public Motorista getMotorista(long id) {
        Motorista m = new Motorista();
        try {
            m = ejbFacade.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MotoristaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

    @FacesConverter(forClass = Motorista.class)
    public static class MotoristaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MotoristaController controller = (MotoristaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "motoristaController");
            return controller.getMotorista(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Motorista 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {

            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Motorista 
            // and therefore Converter.getKey() method could not be pre-generated.
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Motorista) {
                Motorista o = (Motorista) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Motorista.class.getName());
            }
        }

    }

}
