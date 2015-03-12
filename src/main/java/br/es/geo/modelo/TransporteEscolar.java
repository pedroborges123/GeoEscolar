/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.Model;
import java.util.ArrayList;
 
 
 


/**
 *
 * @author Pedro H. Borges Lopes
 */
 
public class TransporteEscolar extends Model{
    private static final long serialVersionUID = 1L;
    
    
     
    private String placa;
     
    private String marca;
     
    private int capacidade;
     
    private String registro;
     
    private Motorista motorista;
     
    private String codigoVan;
    
   
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public String getCodigoVan() {
        return codigoVan;
    }

    public void setCodigoVan(String codigoVan) {
        this.codigoVan = codigoVan;
    }

      
    
    
    
}
