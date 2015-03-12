/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

/**
 *
 * @author Pedro
 */
import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.es.geo.modelo.Adm;
import br.es.geo.modelo.util.Usuario;

public class DAOadm extends DAOGeneric implements DAO<Adm> {

    public Adm create() {

        return new Adm();
    }

    @Override
    public long insert(Adm obj) throws SQLException, ClassNotFoundException {

        this.openConnection();
        
        String sql = "INSERT INTO adm (nome, email, senha) "
                + "VALUES ('" + obj.getNome() + "','" + obj.getEmail() + "','" + Usuario.convertPasswordToMD5(obj.getSenha()) + "')";

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public long update(Adm obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE adm"
                + " SET nome = '" + obj.getNome()
                + "', senha = '" + obj.getSenha()
                + "', email = '" + obj.getEmail()
                + "', ultimoLogin = '" + obj.getUltimaUtilizacao()
                + "' Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Adm obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM adm Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public Adm findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o adm
        this.openConnection();

        String sql = "SELECT * FROM adm WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Adm> adm = retriveAdms(rs);

        this.closeConnection();

        if (adm.isEmpty()) {
            return null;
        }

        return adm.get(0);
    }

    public List<Adm> findByEmail(String email) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM adm WHERE email='" + email + "'";

        ResultSet rs = this.executeQuery(sql);

        List<Adm> adm = retriveAdms(rs);

        this.closeConnection();

        return adm;
    }

    @Override
    public List<Adm> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM adm ";

        ResultSet rs = this.executeQuery(sql);

        List<Adm> adm = retriveAdms(rs);

        this.closeConnection();

        return adm;
    }

    private List<Adm> retriveAdms(ResultSet rs) throws SQLException {
        List<Adm> adms = new ArrayList<Adm>();

        while (rs.next()) {
            Adm obj = new Adm();

            obj.setId(rs.getLong("id"));

            obj.setNome(rs.getString("nome"));

            obj.setEmail(rs.getString("email"));

            obj.setSenha(rs.getString("senha"));

            obj.setUltimaUtilizacao(rs.getString("ultimoLogin"));

            adms.add(obj);
        }

        return adms;
    }

}
