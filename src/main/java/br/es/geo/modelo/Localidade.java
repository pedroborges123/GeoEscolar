/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo;

import br.es.geo.modelo.util.Model;
import br.es.geo.modelo.util.TipoLocal;
import java.util.Objects;

/**
 *
 * @author Pedro H. Borges Lopes
 */
public class Localidade extends Model {

    private static final long serialVersionUID = 1L;

    private String Descricao;

    private TipoLocal tipoLocal;

    private String horaChegada;

    private String horaSaida;

    private String diasUtilizacao;

    private Endereco endereco;

    public TipoLocal getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(TipoLocal tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public Localidade() {
    }

    public String getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(String horaChegada) {
        this.horaChegada = horaChegada;
    }

    public String getHoraSaida() {

        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    

    public String getDiasUtilizacao() {
        return diasUtilizacao;
    }

    public void setDiasUtilizacao(String diasUtilizacao) {
        this.diasUtilizacao = diasUtilizacao;
    }

    @Override
    public String toString() {
        return  tipoLocal+ ": " + Descricao + ", "+  endereco.toString() ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (hash * 53 + this.getId());
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
        final Localidade other = (Localidade) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    
    
    
    
    
    
}
