/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;


import br.es.geo.modelo.util.Usuario;
 
 

/**
 *
 * @author Pedro H. Borges Lopes
 */
 
public class Motorista extends Usuario{
    private static final long serialVersionUID = 1L;
    
   
     
    private String carteiraMotorista;

    
    public String getCarteiraMotorista() {
        return carteiraMotorista;
    }

    public void setCarteiraMotorista(String carteiraMotorista) {
        this.carteiraMotorista = carteiraMotorista;
    }

    @Override
    public String toString() {
        return this.getNome()+ ", " + getTelefone()+ ", CNH: " + this.carteiraMotorista ;
    }
    
    
    
    
}
