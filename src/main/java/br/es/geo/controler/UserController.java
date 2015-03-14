/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.controler;

import br.es.geo.controler.util.JsfUtil;
import br.es.geo.dao.DAOadm;
import br.es.geo.dao.DAOmotorista;
import br.es.geo.dao.DAOresponsavel;
import br.es.geo.modelo.Adm;
import br.es.geo.modelo.Crianca;
import br.es.geo.modelo.Motorista;
import br.es.geo.modelo.Responsavel;
import br.es.geo.modelo.util.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
//import java.time.Clock;
//import java.time.Instant;
//import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Pedro
 */
@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    private String email;
    private String senha;
    private String senhaAtual;
    private Usuario current;
    private String codigo;
    private final String msgS = "Login Efetuado Com Sucesso";
    private final String msgE = "Falha no Login";
    private final String msgAn = "Acesso negado";
    private final String usr = "usuario";
    private final String usrTipo = "TipoUsuario";

    public boolean logado() {

        return JsfUtil.getElementSession(usr) != null;

    }

    public String getCodigo() {
        return codigo;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        this.current = current;
    }

    private void loginMotorista(List<Motorista> l) {
        current = l.get(0);
        senha = Usuario.convertPasswordToMD5(senha);
        if (current.getSenha().equals(senha)) {
            JsfUtil.addSuccessMessage(msgS);

            JsfUtil.putElementSession(usr, current);
            JsfUtil.putElementSession(usrTipo, Motorista.class);

            JsfUtil.redirectTo("motorista/motorista.xhtml");
        } else {
            JsfUtil.addErrorMessage(msgE);
            JsfUtil.redirectTo("index.xhtml");
        }

    }

    private void mudarSenhaMotorista() throws ClassNotFoundException, SQLException {
        Motorista m = (Motorista) JsfUtil.getElementSession(usr);
        DAOmotorista dm = new DAOmotorista();
        senhaAtual = Usuario.convertPasswordToMD5(senhaAtual);
        if (m.getSenha().equals(senhaAtual)) {
            senha = Usuario.convertPasswordToMD5(senha);
            m.setSenha(senha);

            dm.update(m);

            JsfUtil.putElementSession(usr, m);

        }

    }

    public void loginResponsavelByEmail() {
        Responsavel r = (Responsavel) JsfUtil.getElementSession("responsavel");
        List<Crianca> list = (List<Crianca>) JsfUtil.getElementSession("criancas");
        DAOresponsavel dr = new DAOresponsavel();

        try {
            List<Responsavel> resp = dr.findbyEmail(email);
            Responsavel responsavel = resp.get(0);
            if (responsavel.getSenha().equals(senha)) {

                JsfUtil.addSuccessMessage(msgS);

                responsavel.setCriancas((ArrayList<Crianca>) list);
                dr.update(responsavel);
                dr.delete(r);
                JsfUtil.removeElementSession("responsavel");
                JsfUtil.removeElementSession("criancas");
                JsfUtil.putElementSession(usr, responsavel);
                JsfUtil.putElementSession(usrTipo, Responsavel.class);

                JsfUtil.redirectTo("responsavel/responsavel.xhtml");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(msgE);
            JsfUtil.redirectTo("index.xhtml");
        }

    }

    private void mudarSenhaResponsavel() throws ClassNotFoundException, SQLException {
        Responsavel r = (Responsavel) JsfUtil.getElementSession(usr);
        DAOresponsavel dr = new DAOresponsavel();
        senhaAtual = Usuario.convertPasswordToMD5(senhaAtual);
        if (r.getSenha().equals(senhaAtual)) {
            senha = Usuario.convertPasswordToMD5(senha);
            r.setSenha(senha);

            dr.update(r);

            JsfUtil.putElementSession(usr, r);

        }
    }

    private void mudarSenhaAdm() throws ClassNotFoundException, SQLException {
        Adm a = (Adm) JsfUtil.getElementSession(usr);
        DAOadm da = new DAOadm();
        senhaAtual = Usuario.convertPasswordToMD5(senhaAtual);
        if (a.getSenha().equals(senhaAtual)) {
            senha = Usuario.convertPasswordToMD5(senha);
            a.setSenha(senha);

            da.update(a);

            JsfUtil.putElementSession(usr, a);

        }

    }

    public void mudarSenha() {

        try {
            if (this.motoristaLogado()) {
                this.mudarSenhaMotorista();
            } else if (this.responsavelLogado()) {
                this.mudarSenhaResponsavel();
            } else if (this.admLogado()) {
                this.mudarSenhaAdm();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loginResponsavel(List<Responsavel> r) {
        current = r.get(0);
        senha = Usuario.convertPasswordToMD5(senha);
        if (current.getSenha().equals(senha)) {

            JsfUtil.addSuccessMessage(msgS);

            JsfUtil.putElementSession(usr, current);
            JsfUtil
                    .putElementSession(usrTipo, Responsavel.class
                    );

            JsfUtil.redirectTo("responsavel/responsavel.xhtml");
        } else {
            JsfUtil.addErrorMessage(msgE);
            JsfUtil.redirectTo("index.xhtml");
        }

    }

    public void login() throws SQLException, ClassNotFoundException {
        DAOmotorista dm = new DAOmotorista();
        DAOresponsavel dr = new DAOresponsavel();
        System.out.println(email);
        List<Motorista> l = dm.findByEmail(email);
        List<Responsavel> r = dr.findbyEmail(email);
        if (!l.isEmpty() || !r.isEmpty()) {
            if (!l.isEmpty()) {
                this.loginMotorista(l);
            } else {

                if (!r.isEmpty()) {
                    this.loginResponsavel(r);
                }
            }
        } else {
            JsfUtil.addErrorMessage(msgE);
            JsfUtil.redirectTo("index.xhtml");
        }
    }

    public void prepareAdm() {

        if (JsfUtil.getElementSession(usrTipo) == Adm.class) {

            JsfUtil.redirectTo(
                    "admgeo.xhtml");
        } else {
            JsfUtil.addErrorMessage(msgAn);
            JsfUtil.redirectTo("adm.xhtml");
        }

    }

    public void prepareMotorista() {
        if (!this.motoristaLogado()) {

            JsfUtil.addErrorMessage(msgAn);
            JsfUtil.redirectTo("index.xhtml");
        }

    }

    public void prepareResponsavel() {
        if (!this.responsavelLogado()) {

            JsfUtil.addErrorMessage(msgAn);
            JsfUtil.redirectTo("index.xhtml");
        }

    }

    public boolean verificaEmailInBDMotorista(String email) throws SQLException, ClassNotFoundException {

        DAOmotorista dao = new DAOmotorista();
        return dao.findByEmail(email).isEmpty();

    }

    public boolean verificaEmailInBDResponsavel(String email) throws ClassNotFoundException, SQLException {

        DAOresponsavel dao = new DAOresponsavel();
        return dao.findbyEmail(email).isEmpty();
    }

    public void loginAdm() throws SQLException, ClassNotFoundException, IOException {
        DAOadm daoa = new DAOadm();
        List<Adm> list = daoa.findByEmail(email);

        if (!list.isEmpty()) {

            current = list.get(0);
            senha = Usuario.convertPasswordToMD5(senha);

            if (current.getSenha().equals(senha)) {

                JsfUtil.putElementSession(usr, current);
                JsfUtil
                        .putElementSession(usrTipo, Adm.class
                        );
                System.out.println(
                        "Login Sucesso");

                JsfUtil.addSuccessMessage(msgS);

                JsfUtil.redirectTo(
                        "admgeo.xhtml");

            } else {
                System.out.println("Login Fail");
                JsfUtil.addErrorMessage(msgE);
                JsfUtil.redirectTo("adm.xhtml");
            }

        }

    }

    public void logOut() throws ClassNotFoundException, SQLException {
        System.out.println("LogOut");
        if (admLogado()) {
            Adm a = (Adm) JsfUtil.getElementSession(usr);
            //Date data = Date.from(Instant.now(Clock.tickMinutes(ZoneId.systemDefault())));
            Date data = Calendar.getInstance().getTime();
            a.setUltimaUtilizacao(data.toString());
            DAOadm d = new DAOadm();
            d.update(a);
        }

        JsfUtil.removeElementSession(usr);
        JsfUtil.removeElementSession(usrTipo);
        JsfUtil.addSuccessMessage("Volte Sempre!!");
        JsfUtil.redirectTo("index.xhtml");
    }

    public void RedirectToIndex() {
        JsfUtil.redirectTo("index.xhtml");
    }

    public void redirectTo(String url) {
        JsfUtil.redirectTo(url);
    }

    public void RedirectToSobre() {
        JsfUtil.redirectTo("sobre.xhtml");
    }

    public void RedirectToCadastro() {
        JsfUtil.redirectTo("cadastro.xhtml");
    }

    public boolean admLogado() {

        return JsfUtil.getElementSession(usrTipo) == Adm.class;
    }

    public boolean responsavelLogado() {

        return JsfUtil.getElementSession(usrTipo) == Responsavel.class;
    }

    public boolean motoristaLogado() {

        return JsfUtil.getElementSession(usrTipo) == Motorista.class;
    }

    public String userName() {

        Usuario u = (Usuario) JsfUtil.getElementSession(usr);
        return u.getNome();
    }

    public void controleAcessoAdm() throws IOException {
        if (!this.admLogado()) {
            JsfUtil.addErrorMessage("Acesso Restrito");
            JsfUtil.addErrorMessage("Voce precisa esta logado como ADM");

            JsfUtil.redirectTo("adm.xhtml");
        }
    }

}
