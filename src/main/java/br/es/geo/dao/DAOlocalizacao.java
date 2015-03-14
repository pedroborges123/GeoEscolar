/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Localizacao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOlocalizacao  extends DAOGeneric implements DAO<Localizacao> {

    @Override
    public Localizacao create() {

        return new Localizacao();
    }

    @Override
    public long insert(Localizacao obj) throws SQLException, ClassNotFoundException {

        this.openConnection();
        String sql = "INSERT INTO localizacao (longitude, latitude) "
                + "VALUES ('" + obj.getLongitude() + "'," + obj.getLatitude()+ ")";

        long id = this.executeUpdate(sql);
		
        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public long update(Localizacao obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE localizacao"
                + " SET longitude = '" + obj.getLongitude()
                + "', latitude = '" + obj.getLatitude()
                + "' Where id = " + obj.getId();

        System.out.println(sql);
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Localizacao obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM localizacao Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public Localizacao findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o localizacao
        this.openConnection();

        String sql = "SELECT * FROM localizacao WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Localizacao> localizacao = retriveLocalizacaos(rs);

        this.closeConnection();

        return localizacao.get(0);
    }

    @Override
    public List<Localizacao> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM localizacao ";

        ResultSet rs = this.executeQuery(sql);

        List<Localizacao> localizacao = retriveLocalizacaos(rs);

        this.closeConnection();

        return localizacao;
    }

     
    public List<Localizacao> findAllByRegistroLocalizacaoAndData(long itinerario, String data ) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT localizacao.id, localizacao.latitude, localizacao.longitude "
                + "FROM  itinerario join registrolocalizacao join localizacao" 
                + "where localizacao.id = registrolocalizacao.localizacao " 
                + " and itinerario.id ="+ itinerario  
                + " and registrolocalizacao.data= '" + data +"'";  

        ResultSet rs = this.executeQuery(sql);

        List<Localizacao> localizacao = retriveLocalizacaos(rs);

        this.closeConnection();

        return localizacao;
    }
    
    
    
    private List<Localizacao> retriveLocalizacaos(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Localizacao> localizacaos = new ArrayList<>();

        while (rs.next()) {
            Localizacao obj = new Localizacao();

            obj.setId(rs.getLong("id"));

            obj.setLatitude(rs.getString("latitude"));

            obj.setLongitude(rs.getString("longitude"));
          

            localizacaos.add(obj);
        }

        return localizacaos;
    }

}

