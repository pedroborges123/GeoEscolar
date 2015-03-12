/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;


import br.es.geo.modelo.util.Usuario;
import java.util.ArrayList;
import java.util.Objects;
 
/**
 *
 * @author Pedro H. Borges Lopes
 */

public class Crianca extends Usuario{
    private static final long serialVersionUID = 1L;
    
    
   
    private char sexo;
    
    private int idade;
    
   
    private Localidade escola;
   
    private TransporteEscolar transporte;
    
    private Localidade casa;
        
    
    private ArrayList<Localidade> locaisAdicionais;

   
    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Localidade getEscola() {
        return escola;
    }

    public void setEscola(Localidade escola) {
        this.escola = escola;
    }

    public Localidade getCasa() {
        return casa;
    }

    public void setCasa(Localidade casa) {
        this.casa = casa;
    }

    public ArrayList<Localidade> getLocaisAdicionais() {
        return locaisAdicionais;
    }

    public void setLocaisAdicionais(ArrayList<Localidade> locaisAdicionais) {
        this.locaisAdicionais = locaisAdicionais;
    }

    public TransporteEscolar getTransporte() {
        return transporte;
    }

    public void setTransporte(TransporteEscolar transporte) {
        this.transporte = transporte;
    }

    @Override
    public String toString() {
        return "Nome: " + this.getNome()  +  ", idade= " + idade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (int) (41 * hash + this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Crianca other = (Crianca) obj;
        return Objects.equals(this.getId(), other.getId());
    }
    
    
    
}
