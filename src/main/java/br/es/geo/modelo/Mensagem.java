/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.Model;
import br.es.geo.modelo.util.Usuario;
//import java.time.Clock;
//import java.time.Instant;
//import java.time.ZoneId;
import java.util.Calendar;

import java.util.Date;

/**
 *
 * @author Pedro
 */

public class Mensagem extends Model{
    private static final long serialVersionUID = 1L;
    
    private Usuario remetente;
    
    private Usuario destinatario;
    
    private String conteudo;
    
    private Date horaEnvio;

    public Mensagem() {
        
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getHoraEnvio() {
        return horaEnvio;
    }

    public void setHoraEnvio(Date horaEnvio) {
        this.horaEnvio = horaEnvio;
    }

    public Mensagem(Usuario remetente, Usuario destinatario, String conteudo) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.conteudo = conteudo;
        this.horaEnvio = Calendar.getInstance().getTime();
        //this.horaEnvio = Date.from(Instant.now(Clock.tickMinutes(ZoneId.systemDefault())));
    }
    
    
    
    
            
            
    
}
