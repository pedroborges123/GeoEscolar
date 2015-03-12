/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Pedro
 */
@MappedSuperclass
public abstract class Usuario extends Model {

    private static final long serialVersionUID = 1L;

    private String nome;

    private String senha;
    
    private String codigo;
        
    private String email;

    private String Telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = Usuario.convertPasswordToMD5(codigo);
    }

    
    
    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public Usuario(String nome, String senha, String email, String Telefone) {
        this.nome = nome;

        this.senha = senha;
        this.email = email;
        this.Telefone = Telefone;
    }

    public Usuario(String nome, String senha, String email) {
        this.nome = nome;

        this.senha = senha;
        this.email = email;
    }

    public Usuario() {
       
    }

    public static String convertPasswordToMD5(String senha) {
        BigInteger pass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            pass = new BigInteger(1, md.digest(senha.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return String.format("%32x", pass);
    }

}
