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
 * @author Pedro
 */
public class DAOcrianca_localidade extends DAOGeneric {

    public long insert(long crianca, long localidade) throws SQLException, ClassNotFoundException {

        this.openConnection();

        String sql = "INSERT INTO crianca_localidade (crianca, localidade) "
                + "VALUES (" + crianca + "," + localidade + ")";

        long id = this.executeUpdate(sql);

        this.closeConnection();
        return id;
    }

    public long update(long id, long crianca, long localidade) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE crianca_localidade"
                + " SET crianca = " + crianca
                + ", localidade = " + localidade
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

        String sql = "DELETE FROM crianca_localidade" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    public List<Long> findAllByLocalidadeID(long localidade) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM crianca_localidade WHERE localidade =" + localidade;

        ResultSet rs = this.executeQuery(sql);

        List<Long> list = retriveDadosL(rs, localidade);

        this.closeConnection();

        return list;
    }

    public List<Long> findAllByCriancaID(long crianca) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM crianca_localidade WHERE crianca=" + crianca;

        ResultSet rs = this.executeQuery(sql);

        List<Long> list = retriveDadosC(rs, crianca);

        this.closeConnection();

        return list;
    }

    private List<Long> retriveDadosC(ResultSet rs, long id) throws SQLException {
        List<Long> l = new ArrayList<>();

        while (rs.next()) {
            long crianca = rs.getLong("crianca");
            if (crianca == id) {
                l.add(rs.getLong("localidade"));
            }
        }

        return l;
    }

    private List<Long> retriveDadosL(ResultSet rs, long id) throws SQLException {
        List<Long> l = new ArrayList<>();

        while (rs.next()) {
            long localidade = rs.getLong("localidade");
            if (localidade == id) {
                l.add(rs.getLong("crianca"));
            }
        }

        return l;
    }

}
