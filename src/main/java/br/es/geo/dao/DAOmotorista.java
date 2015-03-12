/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Motorista;
import br.es.geo.modelo.util.TipoLocal;
import br.es.geo.modelo.util.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOmotorista extends DAOGeneric implements DAO<Motorista> {

    @Override
    public Motorista create() {

        return new Motorista();
    }

    @Override
    public long insert(Motorista obj) throws SQLException, ClassNotFoundException {

        this.openConnection();

        String sql = "INSERT INTO motorista (nome, telefone, email, senha, carteiraMotorista) "
                + "VALUES ('" + obj.getNome() + "','" + obj.getTelefone() + "','"
                + obj.getEmail() + "','" + Usuario.convertPasswordToMD5(obj.getSenha()) + "','" + obj.getCarteiraMotorista() + "')";

        long id = this.executeUpdate(sql);
		//falta adicionar a casa e escola

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public long update(Motorista obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE motorista"
                + " SET nome = '" + obj.getNome()
                + "', telefone = '" + obj.getTelefone()
                + "', email = '" + obj.getEmail()
                + "', senha = '" + obj.getSenha()
                + "', carteiraMotorista = '" + obj.getCarteiraMotorista()
                + "' Where id = " + obj.getId();

        System.out.println(sql);
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Motorista obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM motorista" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public Motorista findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o motorista
        this.openConnection();

        String sql = "SELECT * FROM motorista WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Motorista> motorista = retriveMotoristas(rs);

        this.closeConnection();

        return motorista.get(0);
    }

    @Override
    public List<Motorista> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM motorista ";

        ResultSet rs = this.executeQuery(sql);

        List<Motorista> motorista = retriveMotoristas(rs);

        this.closeConnection();

        return motorista;
    }

    public  List<Motorista> findByEmail(String email) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM motorista WHERE email='"+email+"'";

        ResultSet rs = this.executeQuery(sql);

        List<Motorista> motorista = retriveMotoristas(rs);

        this.closeConnection();

        return motorista;
    }
    
    
    
    
    private List<Motorista> retriveMotoristas(ResultSet rs) throws SQLException {
        List<Motorista> motoristas = new ArrayList<Motorista>();

        while (rs.next()) {
            Motorista obj = new Motorista();

            obj.setId(rs.getLong("id"));

            obj.setNome(rs.getString("nome"));

            obj.setEmail(rs.getString("email"));

            obj.setCarteiraMotorista(rs.getString("carteiraMotorista"));

            obj.setSenha(rs.getString("senha"));
            
            obj.setTelefone(rs.getString("telefone"));

            motoristas.add(obj);
        }

        return motoristas;
    }

}
