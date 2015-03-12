/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Crianca;
import br.es.geo.modelo.TransporteEscolar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOtransporteescolar extends DAOGeneric implements DAO<TransporteEscolar> {

    @Override
    public TransporteEscolar create() {

        return new TransporteEscolar();
    }

    @Override
    public long insert(TransporteEscolar obj) throws SQLException, ClassNotFoundException {

        this.openConnection();
        String sql = "INSERT INTO transporteescolar (placa, capacidade, registro, marca, idMotorista ) "
                + "VALUES ('" + obj.getPlaca() + "'," + obj.getCapacidade() + ",'"
                + obj.getRegistro() + "','" + obj.getMarca() + "'," + obj.getMotorista().getId() + ")";

        long id = this.executeUpdate(sql);
        
        
        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public long update(TransporteEscolar obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE transporteescolar"
                + " SET placa = '" + obj.getPlaca()
                + "', capacidade = " + obj.getCapacidade()
                + ", registro = '" + obj.getRegistro()
                + "', marca = '" + obj.getMarca()
                + "', idMotorista = " + obj.getMotorista().getId()
                + ", codigoVan = '" + obj.getCodigoVan()
                + "' Where id = " + obj.getId();

        System.out.println(sql);
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(TransporteEscolar obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM transporteescolar" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public TransporteEscolar findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o transporteescolar
        this.openConnection();

        String sql = "SELECT * FROM transporteescolar WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<TransporteEscolar> transporteescolar = retriveTransporteEscolars(rs);

        this.closeConnection();

        return transporteescolar.get(0);
    }

    public TransporteEscolar findbyMotoristaID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o transporteescolar
        this.openConnection();

        String sql = "SELECT * FROM transporteescolar WHERE idMotorista =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<TransporteEscolar> transporteescolar = retriveTransporteEscolars(rs);

        this.closeConnection();

        return transporteescolar.get(0);
    }

    @Override
    public List<TransporteEscolar> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM transporteescolar ";

        ResultSet rs = this.executeQuery(sql);

        List<TransporteEscolar> transporteescolar = retriveTransporteEscolars(rs);

        this.closeConnection();

        return transporteescolar;
    }

    private List<TransporteEscolar> retriveTransporteEscolars(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<TransporteEscolar> transporteescolars = new ArrayList<>();

        while (rs.next()) {
            TransporteEscolar obj = new TransporteEscolar();

            obj.setId(rs.getLong("id"));

            obj.setRegistro(rs.getString("registro"));

            obj.setMarca(rs.getString("marca"));

            obj.setCodigoVan(rs.getString("codigoVan"));

            obj.setCapacidade(rs.getInt("capacidade"));

            DAOmotorista dao = new DAOmotorista();

            long l =rs.getLong("idMotorista");
            
            obj.setMotorista(dao.findbyID(l));

            obj.setPlaca(rs.getString("placa"));

            

            transporteescolars.add(obj);
        }

        return transporteescolars;
    }

    

}
