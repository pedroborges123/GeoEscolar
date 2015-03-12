/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.Model;
import java.util.Date;
import java.util.List;
  

/**
 *
 * @author Pedro H. Borges Lopes
 */
 
public class RegistroLocalizacao extends Model{
    private static final long serialVersionUID = 1L;
    
     
    private Itinerario itinerario;
     
    private Localizacao localizacao;
    
    private boolean finalizado;
    
    private boolean inicio;
    
    private String dia;
    
    private String hora;

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public int getFinalizado(){
        return this.finalizado ? 1 : 0;
    }
    
    
    
    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
            
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isInicio() {
        return inicio;
    }

    public int getInicio(){
        return inicio ? 1 : 0;
    }
    
    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    
    
   
    
}
