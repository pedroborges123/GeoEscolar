/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Itinerario;
import br.es.geo.modelo.Localizacao;
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
public class DAOitinerario extends DAOGeneric implements DAO<Itinerario> {

    @Override
    public Itinerario create() {

        return new Itinerario();
    }

    @Override
    public long insert(Itinerario obj) throws SQLException, ClassNotFoundException {

        this.openConnection();
        String sql = "INSERT INTO itinerario (turno, horaInicio, horaFim, transporteescolar) "
                + "VALUES ('" + obj.getTurno() + "','" + obj.getHoraInicio() + "','" + obj.getHoraFim() + "', "+ obj.getTransEscolar().getId() + ")";

        long id = this.executeUpdate(sql);

        obj.setId(id);
        DAOitinerario_localizacao daoil = new DAOitinerario_localizacao();
        DAOlocalizacao daol = new DAOlocalizacao();
        if (obj.getListLocalizacoes()!= null) {
            for (int x = 0; x < obj.getListLocalizacoes().size(); x++) {
                long idLocalizacao = daol.insert(obj.getListLocalizacoes().get(x));
                daoil.insert(id, idLocalizacao);
            }
        }
        this.closeConnection();
        return id;
    }

    @Override
    public long update(Itinerario obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE itinerario"
                + " SET turno = '" + obj.getTurno().name()
                + "', horaInicio = '" + obj.getHoraInicio()
                + "', horaFim = '" + obj.getHoraFim()
                + "' Where id = " + obj.getId();

        System.out.println(sql);
        long id = this.executeUpdate(sql);

        obj.setId(id);
        DAOregistrolocalizacao daorl = new DAOregistrolocalizacao();
        for (int i = 0; i < obj.getRegistroLocalizacaoList().size(); i++) {
            RegistroLocalizacao rl = obj.getRegistroLocalizacaoList().get(i);

            rl.setItinerario(obj);
            daorl.update(rl);

        }

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Itinerario obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM itinerario" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    public List<Itinerario> findbyTransporteID(long transporteID) throws ClassNotFoundException, SQLException {
        //Query para buscar o itinerario
        this.openConnection();

        String sql = "SELECT * FROM itinerario WHERE transporteescolar =" + transporteID;

        ResultSet rs = this.executeQuery(sql);

        List<Itinerario> itinerario = retriveItinerarios(rs);

        this.closeConnection();

        return itinerario;
    }

    @Override
    public Itinerario findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o itinerario
        this.openConnection();

        String sql = "SELECT * FROM itinerario WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Itinerario> itinerario = retriveItinerarios(rs);

        this.closeConnection();

        return itinerario.get(0);
    }

    @Override
    public List<Itinerario> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM itinerario ";

        ResultSet rs = this.executeQuery(sql);

        List<Itinerario> itinerario = retriveItinerarios(rs);

        this.closeConnection();

        return itinerario;
    }

    private List<Itinerario> retriveItinerarios(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Itinerario> itinerarios = new ArrayList<>();
        List<RegistroLocalizacao> rls = new ArrayList<>();
        DAOregistrolocalizacao daorl = new DAOregistrolocalizacao();
        while (rs.next()) {
            Itinerario obj = new Itinerario();

            obj.setId(rs.getLong("id"));

            obj.setHoraFim(rs.getString("horaFim"));

            obj.setHoraInicio(rs.getString("horaInicio"));

            obj.setTurno(Turno.valueOf(rs.getString("turno")));

            rls = daorl.findByItinerarioid(obj.getId());

            for (RegistroLocalizacao rl : rls) {
                rl.setItinerario(obj);
            }

            obj.setRegistroLocalizacaoList((ArrayList<RegistroLocalizacao>) rls);
            ArrayList<Localizacao> listLocalizacao = new ArrayList<>();
            DAOitinerario_localizacao daoil = new DAOitinerario_localizacao();
            DAOlocalizacao daol = new DAOlocalizacao();
            List<Long> list = daoil.findAllByItinerarioID(obj.getId());
            for (int x = 0; x < list.size(); x++) {
                Localizacao localizacao = daol.findbyID(list.get(x));
                listLocalizacao.add(localizacao);
            }
            obj.setListLocalizacoes(listLocalizacao);

            itinerarios.add(obj);
        }

        return itinerarios;
    }

}
