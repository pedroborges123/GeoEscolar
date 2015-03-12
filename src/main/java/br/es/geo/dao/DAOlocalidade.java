/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Endereco;
import br.es.geo.modelo.Localidade;
import br.es.geo.modelo.util.TipoLocal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOlocalidade extends DAOGeneric implements DAO<Localidade> {

    @Override
    public Localidade create() {

        return new Localidade();
    }

    @Override
    public long insert(Localidade obj) throws SQLException, ClassNotFoundException {

        this.openConnection();

        String sql = "INSERT INTO localidade (descricao, tipoLocal, horaChegada, horaSaida, diasUtilizacao, endereco) "
                + "VALUES ('"+obj.getDescricao() + "','"+ obj.getTipoLocal().name() + "','" + obj.getHoraChegada() + "','"
                + obj.getHoraSaida() + "','" + obj.getDiasUtilizacao() + "', " + obj.getEndereco().getId() + ")";

        long id = this.executeUpdate(sql);
		//falta adicionar a casa e escola

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public long update(Localidade obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE localidade"
                + " SET tipoLocal = '" + obj.getTipoLocal()
                + "', descricao = '" + obj.getDescricao()
                + "', horaChegada = '" + obj.getHoraChegada()
                + "', horaSaida = '" + obj.getHoraSaida()
                + "', diasUtilizacao = '" + obj.getDiasUtilizacao()
                + "' Where id = " + obj.getId();

        
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Localidade obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM localidade" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public Localidade findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o localidade
        this.openConnection();

        String sql = "SELECT * FROM localidade WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Localidade> localidade = retriveLocalidades(rs);

        this.closeConnection();

        return localidade.get(0);
    }

    @Override
    public List<Localidade> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM localidade ";

        ResultSet rs = this.executeQuery(sql);

        List<Localidade> localidade = retriveLocalidades(rs);

        this.closeConnection();

        return localidade;
    }

    private List<Localidade> retriveLocalidades(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Localidade> localidades = new ArrayList<Localidade>();

        while (rs.next()) {
            Localidade obj = new Localidade();

            obj.setId(rs.getLong("id"));

            obj.setTipoLocal(TipoLocal.valueOf(rs.getString("tipoLocal")));
            
            obj.setDescricao(rs.getString("descricao"));
            
            obj.setHoraChegada(rs.getString("horaChegada"));

            obj.setHoraSaida(rs.getString("horaSaida"));

            obj.setDiasUtilizacao(rs.getString("diasUtilizacao"));
            DAOendereco daoE = new DAOendereco();
            long x = rs.getLong("endereco");
            System.out.println("endereco ID:" + x);
            Endereco e =  daoE.findbyID(x);
            
            obj.setEndereco(e);
            
            localidades.add(obj);
        }

        return localidades;
    }

}
