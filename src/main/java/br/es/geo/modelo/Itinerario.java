/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.Model;
import br.es.geo.modelo.util.Turno;
import java.util.ArrayList;


/**
 *
 * @author Pedro H. Borges Lopes
 */
 
public class Itinerario extends Model{
    private static final long serialVersionUID = 1L;
    
     
    private Turno turno;
     
    private ArrayList<Localizacao> listLocalizacoes;
    
    private String horaInicio;
     
    private String horaFim;
     
    private TransporteEscolar transEscolar;

    private ArrayList<RegistroLocalizacao> registroLocalizacaoList;
    
    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public TransporteEscolar getTransEscolar() {
        return transEscolar;
    }

    public void setTransEscolar(TransporteEscolar transEscolar) {
        this.transEscolar = transEscolar;
    }


    public ArrayList<RegistroLocalizacao> getRegistroLocalizacaoList() {
        return registroLocalizacaoList;
    }

    public void setRegistroLocalizacaoList(ArrayList<RegistroLocalizacao> registroLocalizacaoList) {
        this.registroLocalizacaoList = registroLocalizacaoList;
    }

    public ArrayList<Localizacao> getListLocalizacoes() {
        return listLocalizacoes;
    }

    public void setListLocalizacoes(ArrayList<Localizacao> listLocalizacoes) {
        this.listLocalizacoes = listLocalizacoes;
    }
    
    
    
    
}
