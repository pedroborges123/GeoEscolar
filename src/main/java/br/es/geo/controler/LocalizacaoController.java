package br.es.geo.controler;

import br.es.geo.modelo.Localizacao;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOlocalizacao;


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

@Named("localizacaoController")
@SessionScoped
public class LocalizacaoController implements Serializable {

    private Localizacao current;
    private DataModel items = null;

    private DAOlocalizacao ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public LocalizacaoController() {
    }

    public Localizacao getSelected() {
        if (current == null) {
            current = new Localizacao();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DAOlocalizacao getFacade() {
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
                    List<Localizacao> sublist = new ArrayList<>();
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Localizacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Localizacao();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().insert(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LocalizacaoCreated"));
            return prepareCreate();
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Localizacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().update(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LocalizacaoUpdated"));
            return "View";
        } catch (ClassNotFoundException | SQLException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Localizacao) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().delete(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LocalizacaoDeleted"));
        } catch (ClassNotFoundException | SQLException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = 0;
        try {
            count = getFacade().findAll().size();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            // current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
            List<Localizacao> sublist = new ArrayList<>();
            try {
                sublist = getFacade().findAll().subList(selectedItemIndex, selectedItemIndex + 1);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AdmController.class.getName()).log(Level.SEVERE, null, ex);
            }
            current = sublist.get(0);
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

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Localizacao getLocalizacao(long id) {
        Localizacao l = new Localizacao();
        try {
            l= ejbFacade.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocalizacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    @FacesConverter(forClass = Localizacao.class)
    public static class LocalizacaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocalizacaoController controller = (LocalizacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "localizacaoController");
            return controller.getLocalizacao(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
             // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Localizacao 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Localizacao 
            // and therefore Converter.getKey() method could not be pre-generated.
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Localizacao) {
                Localizacao o = (Localizacao) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Localizacao.class.getName());
            }
        }

    }

}
