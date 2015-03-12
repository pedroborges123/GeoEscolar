/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.modelo.util;

import java.text.SimpleDateFormat;

import java.util.Date;

/**
 *
 * @author Pedro H. Borges Lopes
 */
public enum Turno {

    Matutino, Vespertino, Noturno;

    public static Turno getCurrentTurno(String horaInicio) {
        System.out.println("hora: " + horaInicio);

        if (horaInicio.length() == 4) {
            horaInicio = '0' + horaInicio;
        }

        int currentH = Integer.parseInt(horaInicio.substring(0, 2));

        if (currentH > 5 && currentH < 13) {
            return Turno.Matutino;
        } else if (currentH > 13 && currentH < 17) {
            return Turno.Vespertino;
        } else if (currentH > 17 && currentH < 23) {
            return Turno.Noturno;
        } else {
            throw new UnsupportedOperationException("Está de madrugada.. Vá dormir");
        }

    }

}
