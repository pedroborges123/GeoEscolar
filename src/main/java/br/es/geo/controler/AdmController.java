package br.es.geo.controler;

import br.es.geo.modelo.Adm;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOadm;

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

@Named("admController")
@SessionScoped
public class AdmController implements Serializable {

    private Adm current;
    private DataModel items = null;

    private DAOadm ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AdmController() {
    }

    public Adm getSelected() {
        if (current == null) {
            current = new Adm();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DAOadm getFacade() {
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
                    List<Adm> sublist = new ArrayList<>();
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
        current = new Adm();
        selectedItemIndex = -1;
        JsfUtil.redirectTo("adm/Create.xhtml");
    }

    public void create() {
        try {
            getFacade().insert(current);
            JsfUtil.addSuccessMessage("Um novo Adm foi Criado");
            JsfUtil.redirectTo("adm/List.xhtml");
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            prepareCreate();
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

    public Adm getAdm(long id){
        Adm a = new Adm();
        try {
            a= ejbFacade.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @FacesConverter(forClass = Adm.class)
    public static class AdmControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AdmController controller = (AdmController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "admController");
            return controller.getAdm(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Adm 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Adm 
            // and therefore Converter.getKey() method could not be pre-generated.
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Adm) {
                Adm o = (Adm) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Adm.class.getName());
            }
        }

    }

}
