/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.controler;

import br.es.geo.controler.util.JsfUtil;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.json.*;
import org.json.simple.JSONObject;
import org.primefaces.event.map.OverlaySelectEvent;

import org.primefaces.model.map.*;

/**
 *
 * @author Pedro
 */
@Named("maps")
@SessionScoped
@ViewScoped
public class geoController implements Serializable {

    private MapModel advancedModel;

    private Marker marker;

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }

    @PostConstruct
    public void init() {
        JSONObject map = new org.json.simple.JSONObject();

        advancedModel = new DefaultMapModel();
        List<String> list = (List<String>) JsfUtil.getElementSession("localizacao");

        String inicio = (String) JsfUtil.getElementSession("inicio");
        String fim = (String) JsfUtil.getElementSession("fim");
        if (inicio != null) {
            //Shared coordinates
            // adicionando o ponto inicial
            String[] y = inicio.split(",");
            String lat = y[0];
            String lon = y[1];
            map.put("inicio", inicio);
            Double la = Double.parseDouble(lat);
            Double lo = Double.parseDouble(lon);
            LatLng coord = new LatLng(la, lo);
            advancedModel.addOverlay(new Marker(coord, "Inicio da Rota"));
            // adicionando o ponto intermediarios da rota
            for (int i = 0; i < list.size(); i++) {
                y = list.get(i).split(",");
                lat = y[0];
                lon = y[1];
                map.put("intermediario" + i, list.get(i));
                la = Double.parseDouble(lat);
                lo = Double.parseDouble(lon);

                coord = new LatLng(la, lo);

                advancedModel.addOverlay(new Marker(coord, "Pontos Intermediarios"));

            }
            if (fim != null) {
                // adicionando o ponto final da rota
                y = fim.split(",");
                lat = y[0];
                lon = y[1];
                la = Double.parseDouble(lat);
                lo = Double.parseDouble(lon);
                coord = new LatLng(la, lo);
                map.put("fim", fim);
                advancedModel.addOverlay(new Marker(coord, "Fim da Rota"));
                JsfUtil.addSuccessMessage("Rota finalizada");
            } else{
                JsfUtil.addWarningMessage("Rota em Andamento");
            }

            FileWriter file = null;
            try {
                file = new FileWriter("coordenadas.json");
                file.write(map.toJSONString());
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + map);
                file.flush();
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(geoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JsfUtil.addWarningMessage("A rota Nao foi inicializada");

        }
    }

}
