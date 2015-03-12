/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.controler.util;

import br.es.geo.mail.Email;
import br.es.geo.modelo.Adm;
import br.es.geo.modelo.Crianca;
import br.es.geo.modelo.Motorista;
import br.es.geo.modelo.Responsavel;
import javax.mail.MessagingException;

/**
 *
 * @author Pedro
 */
public class ModuloEmail {

    private Email email;
    private String mensagem;
    private String assunto;
    private final String ls = System.lineSeparator();
    private final String titulo = "GeoEscolar" + ls;

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void enviarResposta(String mensagem, String email, String assunto, Adm a) {
        this.assunto = "RE:" + assunto;
        this.mensagem = titulo;
        this.mensagem += "Mensagem Respondida por: " + a.getNome() + ls;
        this.mensagem += "Resposta: " + mensagem + ls;
        this.mensagem += "Obrigado pela preferencia" + ls;
        this.mensagem += "GeoEscolar Team" + ls;
        Email.envia(email, this.assunto, this.mensagem);
    }

    public void enviarCadastroRespByMotorista(Responsavel r, Motorista m) {

        assunto = "O Senhor(a) foi cadastro em nosso sistema por" + m.getNome();
        mensagem = titulo;

        mensagem += "O motorista " + m.getNome() + ", Fez um Cadastro com seu Email: " + r.getEmail() + ", " + ls
                + "Voce deve completar o Cadastro clicando no link a seguir: " + ls;

        // depois mudar pra um arquivo aonde ele irá pegar o link verdadeiro
        String link = "http://localhost:8080/GeoEscolar/faces/motorista/cadastrarResponsavelStep2.xhtml?codigo=";
        
         link += r.getCodigo();
       
        mensagem += ls + link + ls;
        
        mensagem+= ls+ "sua senha é:" + r.getSenha() + ls+ ls;
        mensagem += "divirta-se, e nao hesite em nos contactar " + ls;

        mensagem += "GeoEscolar Team" + ls;
        try {
            System.out.println("Email: " + r.getEmail());
            System.out.println("Assunto: " + assunto);
            System.out.println("mensagem: " + mensagem);

            Email.envia(r.getEmail(), assunto, mensagem);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public void enviarAddCriancaInResponsavelByMotorista(Responsavel r, Motorista m, Crianca c) {
        assunto = "Uma Crianca foi Vinculada a sua conta ";

        mensagem = titulo + ls;

        mensagem += "O motorista" + m.getNome() + ", vinculou a um(a) " + c.getNome() + "." + ls;

        mensagem += "GeoEscolar Team" + ls;

        try {
            System.out.println("Email: " + r.getEmail());
            System.out.println("Assunto: " + assunto);
            System.out.println("mensagem: " + mensagem);
            //Email.enviarEmailV2(r.getEmail(), assunto, mensagem);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

}
