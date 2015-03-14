package br.es.geo.controler;

import br.es.geo.modelo.Responsavel;
import br.es.geo.controler.util.JsfUtil;
import br.es.geo.controler.util.PaginationHelper;
import br.es.geo.dao.DAOcrianca;
import br.es.geo.dao.DAOresponsavel;
import br.es.geo.dao.DAOtransporteescolar;
import br.es.geo.controler.util.ModuloEmail;
import br.es.geo.dao.DAOitinerario;
import br.es.geo.dao.DAOlocalizacao;
import br.es.geo.dao.DAOregistrolocalizacao;
import br.es.geo.modelo.Crianca;
import br.es.geo.modelo.Itinerario;
import br.es.geo.modelo.Localizacao;
import br.es.geo.modelo.Motorista;
import br.es.geo.modelo.RegistroLocalizacao;
import br.es.geo.modelo.TransporteEscolar;
import br.es.geo.modelo.util.TipoResponsavel;
import br.es.geo.modelo.util.Turno;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

@Named("respController")
@SessionScoped
@ViewScoped
public class ResponsavelController implements Serializable {

    private Responsavel current;
    private DataModel items = null;
    private Crianca currentCrianca;
    private List<String> localizacao;
    private String inicio;
    private String fim;
    private List<RegistroLocalizacao> listRegistroLocalizao;
    private DAOresponsavel ejbFacade;
    private int ListTam;
    private List<Responsavel> listResp;
    private List<Responsavel> listAllresp;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String codigo;
    private Boolean filhos;

    public ResponsavelController() {
    }

    public List<Responsavel> getListResp() {
        return listResp;
    }

    public List<String> getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(List<String> localizacao) {
        this.localizacao = localizacao;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFim() {
        return fim;
    }

    public Boolean getFilhos() {
        return filhos;
    }

    public Responsavel getCurrent() {
        return current;
    }

    public List<RegistroLocalizacao> getListRegistroLocalizao() {
        return listRegistroLocalizao;
    }

    public void setListRegistroLocalizao(List<RegistroLocalizacao> listRegistroLocalizao) {
        this.listRegistroLocalizao = listRegistroLocalizao;
    }

    public List<Responsavel> getListAllresp() {
        return listAllresp;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getListTam() {
        return ListTam;
    }

    public void setListTam(int ListTam) {
        this.ListTam = ListTam;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Crianca getCurrentCrianca() {
        return currentCrianca;
    }

    public void setCurrentCrianca(Crianca currentCrianca) {
        this.currentCrianca = currentCrianca;
    }

    public String getTipoPai() {
        return TipoResponsavel.Pai.name();
    }

    public String getTipoMae() {
        return TipoResponsavel.Mae.name();
    }

    public String getTipoTios() {
        return TipoResponsavel.Tios.name();
    }

    public String getTipoAvos() {
        return TipoResponsavel.Avos.name();
    }

    public String getTipoTutor() {
        return TipoResponsavel.Tutor.name();
    }

    public String getTipoPadastros() {
        return TipoResponsavel.Padastros.name();
    }

    public Responsavel getSelected() {
        if (current == null) {
            current = new Responsavel();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DAOresponsavel getFacade() {
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
                    List<Responsavel> sublist = new ArrayList<>();
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
        current = (Responsavel) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate() {
        this.ejbFacade = new DAOresponsavel();
        current = new Responsavel();
        selectedItemIndex = -1;
        JsfUtil.redirectTo("responsavel/Create.xhtml");
    }

    public void prepareCreateByMotorista() {
        this.ejbFacade = new DAOresponsavel();
        current = new Responsavel();
        selectedItemIndex = -1;
        this.currentCrianca = new Crianca();
        JsfUtil.redirectTo("motorista/cadastrarResponsavel.xhtml");
    }

    private String getRandomSenha() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void createByMotorista() {
        try {
            System.out.println(current.getNome());
            current.getCriancas().add(currentCrianca);
            current.setValidado(Boolean.FALSE);
            String senha = this.getRandomSenha();
            current.setSenha(senha);
            current.setCodigo(Calendar.getInstance().toString());
            getFacade().insert(current);

            this.enviarEmailCadastro();
            JsfUtil.addSuccessMessage("Usuario Criado Com Sucesso");
            JsfUtil.redirectTo("motorista/motorista.xhtml");

        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, "Error");
            e.printStackTrace();
            prepareCreateByMotorista();

        } catch (RuntimeException e) {
            this.delete();
            e.printStackTrace();

            JsfUtil.addErrorMessage(e, "Error no Envio do Email, Cadastre Novamente");
            prepareCreateByMotorista();
        }

    }

    public void inializa() {
        filhos = false;
        try {
            this.current = (Responsavel) JsfUtil.getElementSession("usuario");
            if (current.getCriancas() != null || current.getCriancas().size() > 0) {
                this.currentCrianca = current.getCriancas().get(0);
                filhos = true;
                getLogalizacaoCrianca();

            }else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Cuidado", "O senhor(a) precisa ter filhos para poder visualizar as rota.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ResponsavelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Responsavel getResponsavelByCodigo() {
        this.ejbFacade = new DAOresponsavel();

        try {
            return this.ejbFacade.findbyCodigo(codigo);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void atualizaDadosByEmail() {
        current = this.getResponsavelByCodigo();
        List<Crianca> list = current.getCriancas();
        JsfUtil.putElementSession("criancas", list);
        JsfUtil.putElementSession("responsavel", current);
    }

    public void salvarDadosByEmail() {
        try {
            System.out.println(current.getNome());
            current.setValidado(Boolean.TRUE);
            getFacade().update(current);
            JsfUtil.addSuccessMessage("Usuario Ativado Com Sucesso");
            JsfUtil.redirectTo("index.xhtml");
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, "Ërror");
            prepareCreate();
        }

    }

    public List<Crianca> getAllCriancaOfResp() {
        System.out.println("Filhos: " + this.current.getCriancas());
        return this.current.getCriancas();

    }

    //pegando o itinerario do turno da crianca
    private Itinerario getItinerarioByCrianca() throws SQLException, ClassNotFoundException {
        Itinerario iti = null;
        long tid = this.currentCrianca.getTransporte().getId();
        DAOitinerario daoit = new DAOitinerario();
        List<Itinerario> list = daoit.findbyTransporteID(tid);
        String hora = currentCrianca.getCasa().getHoraSaida();
        Turno t = Turno.getCurrentTurno(hora);

        for (Itinerario list1 : list) {
            if (list1.getTurno().equals(t)) {

                iti = list1;
            }
        }
        return iti;
    }

    //pegando a localizacoes da crianca 

    public void getLogalizacaoCrianca() throws ClassNotFoundException, SQLException {
        Itinerario iti = getItinerarioByCrianca();

       
        List<RegistroLocalizacao> listRL = iti.getRegistroLocalizacaoList();
        List<RegistroLocalizacao> listRL1 = listRL;
        System.out.println(listRL);
        System.out.println(listRL.size());
        // pegando o dia atual
        Date data = Calendar.getInstance().getTime();
        String d = new SimpleDateFormat("dd/MM/yyyy").format(data);
        
        for (int i = 0; i < listRL.size(); i++) {
            
            if (!listRL.get(i).getDia().equals(d)) {
                System.out.println("entrou aqui");
                listRL.remove(i);
            }
        }

        
        if (listRL.isEmpty()) {
            String diaAnterior = getDiaAnterior();
            for (int i = 0; i < listRL1.size(); i++) {
                if (!listRL.get(i).getDia().equals(diaAnterior)) {
                    listRL.remove(i);
                }
            }
            JsfUtil.addWarningMessage("Essa Localizacao nao e' do dia Atual");
            createListLocalizacao(listRL1);
        }

        createListLocalizacao(listRL);

    }

    // transformando a lista de localizacao em String lat,long

    private void createListLocalizacao(List<RegistroLocalizacao> list) {
        this.listRegistroLocalizao = list;
        localizacao = new ArrayList<>();
      
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isInicio()) {
                inicio = list.get(i).getLocalizacao().getLatitude() + "," + list.get(i).getLocalizacao().getLongitude();
            } else if (list.get(i).isFinalizado()) {
                fim = list.get(i).getLocalizacao().getLatitude() + "," + list.get(i).getLocalizacao().getLongitude();
            } else {

                localizacao.add(list.get(i).getLocalizacao().getLatitude() + "," + list.get(i).getLocalizacao().getLongitude());
            }
        }

        // se for usar apenas api do maps nao precisa disso, se for usar gmap do primefaces descomentar    
//        JsfUtil.putElementSession("localizacao", this.localizacao);
//        JsfUtil.putElementSession("fim", fim);
//        JsfUtil.putElementSession("inicio", inicio);
    }

  // pega o dia anterior do atual, se o dia anterior for fim de semana, ele pega sexta feira
    private String getDiaAnterior() {

        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DATE, -2);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DATE, -1);
        }
        return dateFormat.format(cal.getTime());
    }

    private void delete() {
        try {
            getFacade().delete(current);
        } catch (ClassNotFoundException | SQLException ex) {
            JsfUtil.addErrorMessage(ex, "Error");
        }
    }

    private void enviarEmailCadastro() {
        ModuloEmail e = new ModuloEmail();
        Motorista m = (Motorista) JsfUtil.getElementSession("usuario");
        e.enviarCadastroRespByMotorista(current, m);
    }

    public void create() {
        try {
            System.out.println(current.getNome());
            current.setEhprincipal(Boolean.TRUE);
            getFacade().insert(current);
            JsfUtil.addSuccessMessage("Usuario Criado Com Sucesso");
            JsfUtil.redirectTo("index.xhtml");
        } catch (SQLException | ClassNotFoundException e) {
            JsfUtil.addErrorMessage(e, "Ërror");
            prepareCreate();
        }
    }

    public void prepareEdit() {
        this.current = (Responsavel) JsfUtil.getElementSession("usuario");
        this.ejbFacade = new DAOresponsavel();
        JsfUtil.redirectTo("responsavel/Edit.xhtml");
    }

    public void prepareEditByMotorista() {

        current = (Responsavel) getItems().getRowData();
        System.out.println(current);
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

    }

    public void createOrUpdate() {

        if (this.current.getId() == null) {
            this.createByMotorista();
        } else {
            updateByMotorista();
        }

    }

    private void enviarEmailUpdate() {
        ModuloEmail me = new ModuloEmail();
        Motorista m = (Motorista) JsfUtil.getElementSession("usuario");
        me.enviarAddCriancaInResponsavelByMotorista(current, m, currentCrianca);
    }

    public void updateByMotorista() {
        try {
            System.out.println(currentCrianca.getId());
            current.getCriancas().add(currentCrianca);

            getFacade().update(current);
            this.enviarEmailUpdate();
            JsfUtil.addSuccessMessage("Responsavel Cadastrado");
            JsfUtil.redirectTo("motorista/motorista.xhtml");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, "Error");
            JsfUtil.redirectTo("motorista/cadastrarResponsavel.xhtml");
        }
    }

    public void update() {
        try {
            getFacade().update(current);
            JsfUtil.addSuccessMessage("Responsavel Cadastrado");
            JsfUtil.redirectTo("responsavel/responsavel.xhtml");
        } catch (ClassNotFoundException | SQLException e) {
            JsfUtil.addErrorMessage(e, "Error");
            prepareEdit();
        }
    }

    public String destroy() {
        current = (Responsavel) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ResponsavelDeleted"));
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
            List<Responsavel> sublist = new ArrayList<>();
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

    public List<Crianca> getCriancas() {
        DAOcrianca dc = new DAOcrianca();
        DAOtransporteescolar dte = new DAOtransporteescolar();
        try {

            Motorista m = (Motorista) JsfUtil.getElementSession("usuario");

            TransporteEscolar te;
            te = dte.findbyMotoristaID(m.getId());
            List<Crianca> l = dc.findbyTransporteId(te.getId());
            System.out.println("lista: " + l);
            return l;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ResponsavelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList();

    }

    @PostConstruct
    public void init() {
        getAllResponsaveis();
    }

    public void showTooltip() {

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dica", "Antes de Cadastrar Responsavel, verifique se o mesmo já está cadastrado!");

        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void getAllResponsaveis() {
        this.ejbFacade = new DAOresponsavel();
        try {
            listAllresp = this.ejbFacade.findAll();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ResponsavelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //return new ListDataModel();
    }

    public List<Responsavel> getAllResponsaveisAtivos() {
        this.ejbFacade = new DAOresponsavel();

        try {
            listResp = this.ejbFacade.findAll();
            for (int x = 0; x < listResp.size(); x++) {
                if (!listResp.get(x).getValidado()) {
                    listResp.remove(x);
                }
            }
            return listResp;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ResponsavelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList();
    }

    public SelectItem[] getItemsAvailableSelectMany() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() throws SQLException, ClassNotFoundException {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Responsavel getResponsavel(long id) {
        Responsavel r = new Responsavel();
        try {
            r = ejbFacade.findbyID(id);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ResponsavelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @FacesConverter(forClass = Responsavel.class)
    public static class ResponsavelControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ResponsavelController controller = (ResponsavelController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "responsavelController");
            return controller.getResponsavel(getKey(value));
        }

        long getKey(String value) {
            long key = Long.valueOf(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Responsavel 
            // and therefore Converter.getKey() method could not be pre-generated.
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            // TODO: no getter methods were found for primary key in your entity class
            //    br.es.geo.modelo.Responsavel 
            // and therefore Converter.getKey() method could not be pre-generated.
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Responsavel) {
                Responsavel o = (Responsavel) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Responsavel.class.getName());
            }
        }

    }

}
