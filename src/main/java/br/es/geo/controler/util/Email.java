/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.controler.util;

import com.sun.mail.smtp.SMTPTransport;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Pedro
 */
public class Email {

    private static void enviarEmail(String email, Properties props, String login, String senha, String assunto, String mensagem) {

        final String e = email;
        final Properties p = props;
        final String s = senha;
        final String l = login;
        final String a = assunto;
        final String m = mensagem;

        Session session = Session.getDefaultInstance(p, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(l, s);
            }

        });

        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(l));// remetente

            String end = e;

            Address[] toUser = InternetAddress.parse(end); // destinatario

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(a);// assunto
            message.setText(m);//texto
            Transport tr = session.getTransport("smtp");
            tr.connect(p.getProperty("mail.smtp.host"), l, s);
            message.saveChanges();      // don't forget this
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

        } catch (MessagingException me) {
            System.err.println(me);
        }

    }

    public static void enviarEmailV1(String email, String login, String senha, String assunto, String mensagem) {

        Properties props = new Properties();
        //propriedades de conexao com gmail..
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        enviarEmail(email, props, login, senha, assunto, mensagem);
    }

    public static void enviarEmailV2(String email, String assunto, String mensagem) {

        Properties props = new Properties();
        //propriedades de conexao com gmail..
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ehlo", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");

        enviarEmail(email, props, "suporte.geoescolar@gmail.com", "GeoEscolar123", assunto, mensagem);
    }

    public static boolean envia(String email, String assunto, String mensagem) {
        System.out.println("Entrou");
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("suporte.geoescolar@gmail.com", "GeoEscolar123");
            }
        });
        System.out.println("Autenticou");
        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);
        System.out.println("Tentando mandar mensagem");
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("suporte.geoescolar@gmail.com")); //Remetente

            //Destinatário(s)
            Address[] toUser = InternetAddress.parse(email);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);
            message.setText(mensagem);
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
        return true;
    }
}
