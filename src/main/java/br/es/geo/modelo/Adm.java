/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.Usuario;
import java.util.Date;
 
 

/**
 *
 * @author Pedro
 */
 
public class Adm extends Usuario{
     private static final long serialVersionUID = 1L;
     
    
     private String ultimaUtilizacao;

    public String getUltimaUtilizacao() {
        return ultimaUtilizacao;
    }

    public void setUltimaUtilizacao(String ultimaUtilizacao) {
        this.ultimaUtilizacao = ultimaUtilizacao;
    }
     
     
    
}
