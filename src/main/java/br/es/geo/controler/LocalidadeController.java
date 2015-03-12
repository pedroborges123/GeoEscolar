package br.es.geo.controler;

import br.es.geo.modelo.Localidade;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOendereco;
import br.es.geo.dao.DAOlocalidade;
import br.es.geo.modelo.Endereco;
import br.es.geo.modelo.util.TipoEndereco;
import br.es.geo.modelo.util.TipoLocal;

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

@Named("localidadeController")
@SessionScoped
public class LocalidadeController implements Serializable {

    private Localidade current;
    private DataModel items = null;
    private Endereco currentEndereco;
    private DAOendereco ejbEndereco;
    private DAOlocalidade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public LocalidadeController() {
    }

    public Localidade getSelected() {
        if (current == null) {
            current = new Localidade();
            selectedItemIndex = -1;
        }
        return current;
    }

    public Localidade getCurrent() {
        return current;
    }

    public void setCurrent(Localidade current) {
        this.current = current;
    }

    public Endereco getCurrentEndereco() {
        return currentEndereco;
    }

    public void setCurrentEndereco(Endereco currentEndereco) {
        this.currentEndereco = currentEndereco;
    }

    public DAOendereco getEjbEndereco() {
        return ejbEndereco;
    }

    public void setEjbEndereco(DAOendereco ejbEndereco) {
        this.ejbEndereco = ejbEndereco;
    }

    public DAOlocalidade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(DAOlocalidade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    private DAOlocalidade getFacade() {
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
                    List<Localidade> sublist = new ArrayList<>();
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
        current = (Localidade) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreateByMotorista() {
        current = new Localidade();
        this.ejbFacade = new DAOlocalidade();
        this.currentEndereco = new Endereco();
        this.ejbEndereco = new DAOendereco();
        selectedItemIndex = -1;
        JsfUtil.redirectTo("motorista/cadastrarEscola.xhtml");
    }

    public void createEscolaByMotorista() {
        try {
            current.setTipoLocal(TipoLocal.Escola);
            
            this.ejbEndereco.insert(currentEndereco);
            current.setEndereco(currentEndereco);
            getFacade().insert(current);
            JsfUtil.addSuccessMessage("Escola Cadastrada");
            JsfUtil.redirectTo("motorista/motorista.xhtml");
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            prepareCreateByMotorista();
        }
    }

    public List<Localidade> getAllEscolas() {
        
       
        try {
            this.ejbFacade = new DAOlocalidade();
            List<Localidade> list = this.ejbFacade.findAll();
            
            for (int x = 0; x < list.size(); x++) {
                if (!list.get(x).getTipoLocal().equals(TipoLocal.Escola)) {
                    list.remove(x);
                }

            }
            
            //System.out.println(list);
            return list;
           
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(LocalidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }
          return new ArrayList();

    }

    public String prepareEdit() {
        current = (Localidade) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().update(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LocalUpdated"));
            return "View";
        } catch (ClassNotFoundException | SQLException e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Localidade) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LocalDeleted"));
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
            List<Localidade> sublist = new ArrayList<>();
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

    public SelectItem[] getItemsAvailableSelectOne(List l) throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(l, true);
    }
    
    
    public SelectItem[] getItemsAvailableSelectMany() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public static Localidade getLocal(long id) {
        Localidade l = new Localidade();
        try {
            DAOlocalidade dao = new DAOlocalidade();
            l = dao.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocalidadeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    @FacesConverter(forClass = Localidade.class)
    public static class LocalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            System.out.println("value:" + value);
            //facesContext.getApplication().getELResolver().getValue(null, value, current)
           //LocalidadeController controller = (LocalidadeController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), component, "localController");
            return getLocal(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Local 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Local 
            // and therefore Converter.getKey() method could not be pre-generated.
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Localidade) {
                Localidade o = (Localidade) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Localidade.class.getName());
            }
        }

    }

}
