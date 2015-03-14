package br.es.geo.controler;

import br.es.geo.modelo.Crianca;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOcrianca;
import br.es.geo.dao.DAOendereco;
import br.es.geo.dao.DAOlocalidade;
import br.es.geo.dao.DAOtransporteescolar;
import br.es.geo.modelo.Endereco;
import br.es.geo.modelo.Localidade;
import br.es.geo.modelo.Motorista;
import br.es.geo.modelo.TransporteEscolar;
import br.es.geo.modelo.util.TipoLocal;

import java.io.Serializable;
import java.sql.SQLException;
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

@Named("criancaController")
@SessionScoped
public class CriancaController implements Serializable {

    private Crianca current;
    private Endereco currentEndereco;
    private Localidade currentLocalidade;
    private DataModel items = null;
    private Localidade escola;
    private DAOendereco ejbDAOendereco;
    private DAOlocalidade ejbDAOlocalidade;
    private DAOcrianca ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int rowCont = 0;

    public CriancaController() {
    }

    public Crianca getSelected() {
        if (current == null) {
            current = new Crianca();
            selectedItemIndex = -1;
        }
        return current;
    }

    public Localidade getEscola() {
        return escola;
    }

    public void setEscola(Localidade escola) {
        this.escola = escola;
    }

    public int getRowCont() {
        return rowCont;
    }

    public Endereco getCurrentEndereco() {
        return currentEndereco;
    }

    public void setCurrentEndereco(Endereco currentEndereco) {
        this.currentEndereco = currentEndereco;
    }

    public Localidade getCurrentLocalidade() {
        return currentLocalidade;
    }

    public void setCurrentLocalidade(Localidade currentLocalidade) {
        this.currentLocalidade = currentLocalidade;
    }

    private DAOendereco getFacadeDAOendereco() {
        return ejbDAOendereco;
    }

    private DAOlocalidade getFacadeDAOlocalidade() {
        return ejbDAOlocalidade;
    }

    private DAOcrianca getFacade() {
        return ejbFacade;
    }

    public DataModel getListCriancas() {
        Motorista m = (Motorista) JsfUtil.getElementSession("usuario");
        DAOtransporteescolar daoT = new DAOtransporteescolar();
        this.ejbFacade = new DAOcrianca();
        try {
            TransporteEscolar te = daoT.findbyMotoristaID(m.getId());
            DataModel<Crianca> dm = new ListDataModel<>(this.ejbFacade.findbyTransporteId(te.getId()));
            this.rowCont = dm.getRowCount();
            return dm;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CriancaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ListDataModel<>();
    }

    public void prepareCreate() {
        this.ejbDAOendereco = new DAOendereco();
        this.ejbDAOlocalidade = new DAOlocalidade();
        this.ejbFacade = new DAOcrianca();
        current = new Crianca();
        this.currentEndereco = new Endereco();
        this.currentLocalidade = new Localidade();
        selectedItemIndex = -1;
        JsfUtil.redirectTo("motorista/cadastrarCrianca.xhtml");
    }

    public void create() {
        DAOtransporteescolar daoT = new DAOtransporteescolar();
        try {
            long id;
            id = this.ejbDAOendereco.insert(currentEndereco);
            this.currentEndereco.setId(id);
            this.currentLocalidade.setTipoLocal(TipoLocal.Casa);
            this.currentLocalidade.setEndereco(currentEndereco);
            id = this.ejbDAOlocalidade.insert(currentLocalidade);
            this.currentLocalidade.setId(id);
            this.current.setCasa(currentLocalidade);
            this.current.setEscola(escola);
            Motorista m = (Motorista) JsfUtil.getElementSession("usuario");
            TransporteEscolar te = daoT.findbyMotoristaID(m.getId());
            current.setTransporte(te);
            getFacade().insert(current);
            JsfUtil.addSuccessMessage("Crianca Criada");
            JsfUtil.redirectTo("motorista/motorista.xhtml");
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, "Ocorreu um Error");
            prepareCreate();
        }
    }
 
  
    public Crianca getCrianca(long id) {
        Crianca c = new Crianca();
        try {
            c = ejbFacade.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CriancaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    @FacesConverter(forClass = Crianca.class)
    public static class CriancaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CriancaController controller = (CriancaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "criancaController");
            return controller.getCrianca(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Crianca 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {
            //
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Crianca 
            // and therefore Converter.getKey() method could not be pre-generated.
            System.out.println("GetStringKey: " + sb.toString());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                System.out.println("Objeto Vazio");
            }
            if (object instanceof Crianca) {
                Crianca o = (Crianca) object;
                String s = "0";
                if (o.getId() != null) {
                    System.out.println("Crianca: " + o.toString() + "-" + o.getId());
                    s = getStringKey(o.getId());

                } else {
                    return null;
                }
                return s;
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Crianca.class.getName());
            }
        }

    }

}
