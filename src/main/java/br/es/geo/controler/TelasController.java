/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.controler;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Pedro
 */
@Named("telasController")
@SessionScoped
public class TelasController implements Serializable {

    public String RedirectHome() {
        return "index.xhtml";
    }

    public String RedirectCadastro() {
        return "cadastro.xhtml";
    }

    public String RedirectAboutUs() {
        return "sobre.xhtml";
    }

    public String RedirectAdm() {
        return "adm.xhtml";
    }

    public String RedirectFaleConosco() {
        return "conosco.xhtml";
    }

    

}
