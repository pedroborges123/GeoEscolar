/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;

import br.es.geo.modelo.RegistroLocalizacao;
import br.es.geo.modelo.util.Turno;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOregistrolocalizacao extends DAOGeneric implements DAO<RegistroLocalizacao> {

    @Override
    public RegistroLocalizacao create() {

        return new RegistroLocalizacao();
    }

    @Override
    public long insert(RegistroLocalizacao obj) throws SQLException, ClassNotFoundException {

        this.openConnection();
        String sql = "INSERT INTO registrolocalizacao (data, hora, itinerario, localizacao, finalizado, inicio) "
                + "VALUES ('" + obj.getDia() + "','" + obj.getHora() + "'," + obj.getItinerario().getId() + "," + obj.getLocalizacao().getId()
                + ", " + obj.getFinalizado() + ", " + obj.getInicio() + ")";

        // salvar a localizacao
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public long update(RegistroLocalizacao obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE registrolocalizacao"
                + " SET data = '" + obj.getDia()
                + "', hora = '" + obj.getHora()
                + "', itinerario = " + obj.getItinerario().getId()
                + ", localizacao = " + obj.getLocalizacao().getId()
                + ", finalizado= " + obj.getFinalizado()
                + ", inicio =" + obj.getInicio()
                + " Where id = " + obj.getId();

        System.out.println(sql);
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(RegistroLocalizacao obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM registrolocalizacao Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public RegistroLocalizacao findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o registrolocalizacao
        this.openConnection();

        String sql = "SELECT * FROM registrolocalizacao WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<RegistroLocalizacao> registrolocalizacao = retriveRegistrolocalizacaos(rs);

        this.closeConnection();

        return registrolocalizacao.get(0);
    }

    public List<RegistroLocalizacao> findByItinerarioid(long id) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM registrolocalizacao where itinerario=" + id;

        ResultSet rs = this.executeQuery(sql);

        List<RegistroLocalizacao> registrolocalizacao = retriveRegistrolocalizacaos(rs);

        this.closeConnection();

        return registrolocalizacao;
    }

    @Override
    public List<RegistroLocalizacao> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM registrolocalizacao ";

        ResultSet rs = this.executeQuery(sql);

        List<RegistroLocalizacao> registrolocalizacao = retriveRegistrolocalizacaos(rs);

        this.closeConnection();

        return registrolocalizacao;
    }

    private List<RegistroLocalizacao> retriveRegistrolocalizacaos(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<RegistroLocalizacao> registrolocalizacaos = new ArrayList<>();

        while (rs.next()) {
            RegistroLocalizacao obj = new RegistroLocalizacao();

            obj.setId(rs.getLong("id"));

            obj.setDia(rs.getString("data"));

            obj.setHora(rs.getString("hora"));

            int x = rs.getByte("finalizado");

            if (x == 0) {
                obj.setFinalizado(Boolean.FALSE);
            } else {
                obj.setFinalizado(Boolean.TRUE);
            }
            x = rs.getByte("inicio");
            if (x == 0) {
                obj.setInicio(Boolean.FALSE);
            } else {
                obj.setInicio(Boolean.TRUE);
            }

            long l = rs.getLong("localizacao");
            DAOlocalizacao daol = new DAOlocalizacao();
            obj.setLocalizacao(daol.findbyID(l));

            registrolocalizacaos.add(obj);
        }

        return registrolocalizacaos;
    }

}
