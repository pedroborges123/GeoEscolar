/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Endereco;
import br.es.geo.modelo.util.TipoEndereco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOendereco extends DAOGeneric implements DAO<Endereco> {

    @Override
    public Endereco create() {

        return new Endereco();
    }

    @Override
    public long insert(Endereco obj) throws SQLException, ClassNotFoundException {

        this.openConnection();
        String sql = "INSERT INTO endereco (CEP, logradouro, numero, complemento, bairro, uf, cidade) "
                + "VALUES ('" + obj.getCEP() + "','" + obj.getLogradouro()
                + "'," + obj.getNumero() + ",'" + obj.getComplemento() + "','" + obj.getBairro() + "','" + obj.getUf() + "','" + obj.getCidade() + "')";

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public long update(Endereco obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE endereco"
                + " SET tipoEndereco = '" + obj.getTipoEndereco().name()
                + "', CEP = '" + obj.getCEP()
                + "', logradouro = '" + obj.getLogradouro()
                + "', complemento = '" + obj.getComplemento()
                + "', numero = " + obj.getNumero()
               
                + ", cidade = '" + obj.getCidade()
                + "', uf ='" + obj.getUf()
                + "',bairro='" + obj.getBairro()
                + "' Where id = " + obj.getId();

        System.out.println(sql);
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Endereco obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM endereco" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public Endereco findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o endereco
        this.openConnection();

        String sql = "SELECT * FROM endereco WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Endereco> endereco = retriveEnderecos(rs);

        this.closeConnection();

        return endereco.get(0);
    }

    @Override
    public List<Endereco> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM endereco ";

        ResultSet rs = this.executeQuery(sql);

        List<Endereco> endereco = retriveEnderecos(rs);

        this.closeConnection();

        return endereco;
    }

    private List<Endereco> retriveEnderecos(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Endereco> enderecos = new ArrayList<>();

        while (rs.next()) {
            Endereco obj = new Endereco();

            obj.setId(rs.getLong("id"));

            obj.setCEP(rs.getString("CEP"));

            obj.setComplemento(rs.getString("complemento"));

            obj.setLogradouro(rs.getString("logradouro"));

            //obj.setTipoEndereco(TipoEndereco.valueOf(rs.getString("tipoEndereco")));

            obj.setBairro(rs.getString("bairro"));
            obj.setCidade(rs.getString("cidade"));
            obj.setUf(rs.getString("uf"));
            obj.setNumero(rs.getInt("numero"));

            DAOlocalizacao daol = new DAOlocalizacao();
            String x = rs.getString("localizacao");
            if( x != null ){
                obj.setLocalizacao(daol.findbyID(Integer.parseInt(x)));
            }
            
            

            enderecos.add(obj);
        }

        return enderecos;
    }

}
