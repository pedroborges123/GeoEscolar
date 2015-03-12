package br.es.geo.controler;

import br.es.geo.modelo.FaleConosco;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOfaleConosco;
import br.es.geo.modelo.Adm;
import br.es.geo.modelo.util.Usuario;
import java.io.IOException;

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

@Named("faleConoscoController")
@SessionScoped
public class FaleConoscoController implements Serializable {

    private FaleConosco current;
    private DataModel items = null;

    private DAOfaleConosco ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public FaleConoscoController() {
        ejbFacade = new DAOfaleConosco();
    }

    public FaleConosco getSelected() {
        if (current == null) {
            current = new FaleConosco();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DAOfaleConosco getFacade() {
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
                    List<FaleConosco> sublist = new ArrayList<>();
                    try {

                        sublist = getFacade().findAll();
                        if (sublist.size() > 10) {
                            sublist = sublist.subList(getPageFirstItem(), getPageFirstItem() + getPageSize());
                        }

                        //return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (FaleConosco) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate() throws IOException {
        //System.out.println("passou aqui prepareCreate");
        Usuario user = (Usuario) JsfUtil.getElementSession("usuario");
        this.ejbFacade = new DAOfaleConosco();
        current = new FaleConosco();
        if(user != null){
            current.setNome(user.getNome());
            current.setEmail(user.getEmail());
        }
       
        selectedItemIndex = -1;
        JsfUtil.redirectTo("conosco.xhtml");
        
    }

    public void create() throws IOException {
        try {

            System.out.println(current.getNome());
            getFacade().insert(current);

            JsfUtil.addSuccessMessage("Mensagem Enviada Com Sucesso!");
            JsfUtil.redirectTo("index.xhtml");
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            prepareCreate();
        }
    }

    public void prepareEdit() {
        current = (FaleConosco) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        current.setUserResponse((Adm) JsfUtil.getElementSession("usuario"));
        JsfUtil.redirectTo("faleConosco/Edit.xhtml");
    }

    public void update() {
        try {
            // adicionar metodo de envio de email
            getFacade().update(current);
            JsfUtil.addSuccessMessage("Mensagem Enviada Com Sucesso");
            JsfUtil.redirectTo("admgeo.xhtml");

        } catch (ClassNotFoundException | SQLException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            JsfUtil.redirectTo("admgeo.xhtml");
        }
    }

    public String destroy() {
        current = (FaleConosco) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FaleConoscoDeleted"));
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
            List<FaleConosco> sublist = new ArrayList<>();
            try {
                sublist = getFacade().findAll().subList(selectedItemIndex, selectedItemIndex + 1);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AdmController.class.getName()).log(Level.SEVERE, null, ex);
            }
            current = sublist.get(0);
        }
    }

    public DataModel getAllItems() throws SQLException, ClassNotFoundException {
        return new ListDataModel(ejbFacade.findAll());
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        // System.out.println("getItems= " + items.getRowCount());
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

    public FaleConosco getFaleConosco(long id) {
        FaleConosco fc = new FaleConosco();
        try {
            fc = ejbFacade.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FaleConoscoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fc;
    }

    @FacesConverter(forClass = FaleConosco.class)
    public static class FaleConoscoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FaleConoscoController controller = (FaleConoscoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "faleConoscoController");
            return controller.getFaleConosco(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.FaleConosco 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.FaleConosco 
            // and therefore Converter.getKey() method could not be pre-generated.
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FaleConosco) {
                FaleConosco o = (FaleConosco) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FaleConosco.class.getName());
            }
        }

    }

}
