/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.controler;

import br.es.geo.controler.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
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
        advancedModel = new DefaultMapModel();
        List<String> list = (List<String>) JsfUtil.getElementSession("localizacao");
        System.out.println(list);
        //Shared coordinates

        for (int i = 0; i < list.size(); i++) {
            String[] y = list.get(i).split(",");
            String lat = y[0];
            String lon = y[1];

            Double la = Double.parseDouble(lat);
            Double lo = Double.parseDouble(lon);

            LatLng coord = new LatLng(la, lo);

            advancedModel.addOverlay(new Marker(coord));

        }

    }

}
