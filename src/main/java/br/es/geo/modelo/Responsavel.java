/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.TipoResponsavel;
import br.es.geo.modelo.util.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Pedro H. Borges Lopes
 */
public class Responsavel extends Usuario {

    private static final long serialVersionUID = 1L;

    private TipoResponsavel tipoRes;
    private Boolean validado;
    private Boolean ehprincipal;
    private ArrayList<Crianca> criancas;

    public TipoResponsavel getTipoRes() {
        return tipoRes;
    }

    public void setTipoRes(TipoResponsavel tipoRes) {
        this.tipoRes = tipoRes;
    }

    public ArrayList<Crianca> getCriancas() {
        return criancas;
    }

    public void setCriancas(ArrayList<Crianca> criancas) {
        this.criancas = criancas;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Responsavel(TipoResponsavel tipoRes, String nome, String senha, String email, String Telefone) {
        super(nome, senha, email, Telefone);
        this.tipoRes = tipoRes;
        this.validado = false;
    }

    public Responsavel(TipoResponsavel tipoRes, String nome, String senha, String email) {
        super(nome, senha, email);
        this.tipoRes = tipoRes;
        this.validado = false;
    }

    public Responsavel(TipoResponsavel tipoRes) {
        super();
        this.tipoRes = tipoRes;
        this.validado = false;
    }

    public Responsavel(String nome, String senha, String email, String Telefone) {
        super(nome, senha, email, Telefone);
        this.validado = false;
    }

    public Responsavel(String nome, String senha, String email) {
        super(nome, senha, email);
        this.validado = false;

    }

    public Responsavel() {
        super();
        this.validado = false;
        this.criancas = new ArrayList<>();

    }

    public Boolean getEhprincipal() {
        return ehprincipal;
    }

    public void setEhprincipal(Boolean ehprincipal) {
        this.ehprincipal = ehprincipal;
    }

    public int getEhprincipalInt() {

        return ehprincipal ? 1 : 0;
    }

    public int getValidadoInt() {

        return validado ? 1 : 0;
    }

}
