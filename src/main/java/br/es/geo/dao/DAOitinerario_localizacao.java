/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.es.geo.dao;

import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Crianca;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 20091bsi0222
 */
public class DAOitinerario_localizacao extends DAOGeneric{
      public long insert(long itinerario, long localizacao) throws SQLException, ClassNotFoundException {

        this.openConnection();

        String sql = "INSERT INTO itinerario_localizacao (itinerario, localizacao) "
                + "VALUES (" + itinerario + "," + localizacao + ")";

        long id = this.executeUpdate(sql);

        this.closeConnection();
        return id;
    }

    public long update(long id, long itinerario, long localizacao) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE itinerario_localizacao"
                + " SET itinerario = " + itinerario
                + ", localizacao = " + localizacao
                + " Where id = " + id;

        System.out.println(sql);
        long Id;
        Id = this.executeUpdate(sql);

        this.closeConnection();
        return Id;
    }

    public void delete(Crianca obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM itinerario_localizacao" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    public List<Long> findAllByLocalizacaoID(long localizacao) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM itinerario_localizacao WHERE localizacao =" + localizacao;

        ResultSet rs = this.executeQuery(sql);

        List<Long> list = retriveDadosL(rs, localizacao);

        this.closeConnection();

        return list;
    }

    public List<Long> findAllByItinerarioID(long itinerario) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM itinerario_localizacao WHERE itinerario=" + itinerario;

        ResultSet rs = this.executeQuery(sql);

        List<Long> list = retriveDadosC(rs, itinerario);

        this.closeConnection();

        return list;
    }

    private List<Long> retriveDadosC(ResultSet rs, long id) throws SQLException {
        List<Long> l = new ArrayList<>();

        while (rs.next()) {
            long itinerario = rs.getLong("itinerario");
            if (itinerario == id) {
                l.add(rs.getLong("localizacao"));
            }
        }

        return l;
    }

    private List<Long> retriveDadosL(ResultSet rs, long id) throws SQLException {
        List<Long> l = new ArrayList<>();

        while (rs.next()) {
            long localizacao = rs.getLong("localizacao");
            if (localizacao == id) {
                l.add(rs.getLong("itinerario"));
            }
        }

        return l;
    }
}
