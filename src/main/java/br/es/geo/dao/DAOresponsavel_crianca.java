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
public class DAOresponsavel_crianca extends DAOGeneric {

    public long insert(long crianca, long responsavel) throws SQLException, ClassNotFoundException {

        this.openConnection();

        System.out.println("DAORC: " + crianca);
        
        String sql = "INSERT INTO responsavel_crianca (crianca, responsavel) "
                + "VALUES (" + crianca + "," + responsavel + ")";

        long id = this.executeUpdate(sql);

        this.closeConnection();
        return id;
    }

    public long update(long id, long crianca, long responsavel) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE responsavel_crianca"
                + " SET crianca = '" + crianca
                + "', responsavel = '" + responsavel
                + " Where id = " + id;

        System.out.println(sql);
        long Id = this.executeUpdate(sql);

        this.closeConnection();
        return Id;
    }

    public void delete(Crianca obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM responsavel_crianca" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    public List<Long> findAllByResponsavelID(long responsavel) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM responsavel_crianca WHERE responsavel=" + responsavel;

        ResultSet rs = this.executeQuery(sql);

        List<Long> list = retriveDadosR(rs, responsavel);

        this.closeConnection();

        return list;
    }

    public List<Long> findAllByCriancaID(long crianca) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM responsavel_crianca WHERE crianca=" + crianca;

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

                l.add(rs.getLong("responsavel"));
            }

        }

        return l;
    }

    private List<Long> retriveDadosR(ResultSet rs, long id) throws SQLException {
        List<Long> l = new ArrayList<>();

        while (rs.next()) {
            long responsavel = rs.getLong("responsavel");
            if (responsavel == id) {

                l.add(rs.getLong("crianca"));
            }

        }

        return l;
    }

}
