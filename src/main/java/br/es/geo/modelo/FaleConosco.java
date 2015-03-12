/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.Model;
import br.es.geo.modelo.util.Usuario;
 
 
 
 

/**
 *
 * @author Pedro
 */
 
public class FaleConosco extends Model {

    private static final long serialVersionUID = 1L;

     
    private String nome;
     
    private String Email;
     
    private String assunto;
     
    private String texto;
     
    private String resposta;
    
    private Adm userResponse;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Adm getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(Adm userResponse) {
        this.userResponse = userResponse;
    }

}
