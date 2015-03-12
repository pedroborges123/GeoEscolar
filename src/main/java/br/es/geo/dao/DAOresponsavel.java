/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Crianca;
import br.es.geo.modelo.Responsavel;
import br.es.geo.modelo.util.TipoResponsavel;
import br.es.geo.modelo.util.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOresponsavel extends DAOGeneric implements DAO<Responsavel> {

    @Override
    public Responsavel create() {

        return new Responsavel();
    }

    @Override
    public long insert(Responsavel obj) throws SQLException, ClassNotFoundException {

        this.openConnection();

        String sql = "INSERT INTO responsavel (nome, telefone, email, senha, tipoResponsavel, ehprincipal, validado, codigo ) "
                + "VALUES ('" + obj.getNome() + "','" + obj.getTelefone() + "','"
                + obj.getEmail() + "','" + Usuario.convertPasswordToMD5(obj.getSenha())
                + "','" + obj.getTipoRes().name() + "'," + obj.getEhprincipalInt() + ", " + obj.getValidadoInt() +",'" + obj.getCodigo() +"')";

        long id = this.executeUpdate(sql);
        obj.setId(id);
        if (obj.getCriancas() != null) {
            DAOresponsavel_crianca daorc = new DAOresponsavel_crianca();

            for (int i = 0; i < obj.getCriancas().size(); i++) {
                long c = obj.getCriancas().get(i).getId();
                daorc.insert(c, obj.getId());
            }
        }
        this.closeConnection();
        return id;
    }

    @Override
    public long update(Responsavel obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "UPDATE responsavel"
                + " SET nome = '" + obj.getNome()
                + "', telefone ='" + obj.getTelefone()
                + "', email = '" + obj.getEmail()
                + "', senha = '" + obj.getSenha()
                + "', tipoResponsavel ='" + obj.getTipoRes().name()
                + "', validado = " + obj.getValidadoInt()
                + ", ehprincipal = " + obj.getEhprincipalInt()
                + " Where id = " + obj.getId();

        DAOresponsavel_crianca daorc = new DAOresponsavel_crianca();

        for (int i = 0; i < obj.getCriancas().size(); i++) {

            long c = obj.getCriancas().get(i).getId();
            daorc.insert(c, obj.getId());
        }

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Responsavel obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM responsavel Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public Responsavel findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o responsavel
        this.openConnection();

        String sql = "SELECT * FROM responsavel WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Responsavel> responsavel = retriveResponsavels(rs);

        this.closeConnection();

        return responsavel.get(0);
    }
    
     public Responsavel findbyCodigo(String codigo) throws ClassNotFoundException, SQLException {
        //Query para buscar o responsavel
        this.openConnection();

        String sql = "SELECT * FROM responsavel WHERE codigo ='" + codigo + "'";

        ResultSet rs = this.executeQuery(sql);

        List<Responsavel> responsavel = retriveResponsavels(rs);

        this.closeConnection();

        return responsavel.get(0);
    }

    public List<Responsavel> findbyEmail(String email) throws ClassNotFoundException, SQLException {
        //Query para buscar o responsavel
        this.openConnection();

        String sql = "SELECT * FROM responsavel WHERE email ='" + email + "'";

        ResultSet rs = this.executeQuery(sql);

        List<Responsavel> responsavel = retriveResponsavels(rs);

        this.closeConnection();

        return responsavel;
    }

    public List<Responsavel> findAllValidados() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM responsavel WHERE validado=" + 1;

        ResultSet rs = this.executeQuery(sql);

        List<Responsavel> responsavel = retriveResponsavels(rs);

        this.closeConnection();

        return responsavel;
    }

    @Override
    public List<Responsavel> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM responsavel ";

        ResultSet rs = this.executeQuery(sql);

        List<Responsavel> responsavel = retriveResponsavels(rs);

        this.closeConnection();

        return responsavel;
    }

    private List<Responsavel> retriveResponsavels(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Responsavel> responsavels = new ArrayList<>();

        while (rs.next()) {
            Responsavel obj = new Responsavel();

            obj.setId(rs.getLong("id"));

            obj.setNome(rs.getString("nome"));

            obj.setEmail(rs.getString("email"));

            obj.setTipoRes(TipoResponsavel.valueOf(rs.getString("tipoResponsavel")));

            obj.setSenha(rs.getString("senha"));

            obj.setTelefone(rs.getString("telefone"));

            int x = rs.getByte("validado");

            if (x == 0) {
                obj.setValidado(Boolean.FALSE);
            } else {
                obj.setValidado(Boolean.TRUE);
            }
            x = rs.getByte("ehprincipal");
            if (x == 0) {
                obj.setEhprincipal(Boolean.FALSE);
            } else {
                obj.setEhprincipal(Boolean.TRUE);
            }

            DAOcrianca daoc = new DAOcrianca();
            ArrayList<Crianca> list = new ArrayList<>();
            DAOresponsavel_crianca daorc = new DAOresponsavel_crianca();
            List<Long> l = daorc.findAllByResponsavelID(obj.getId());

            for (Long l1 : l) {
                list.add(daoc.findbyID(l1));
            }
            obj.setCriancas(list);

            responsavels.add(obj);
        }

        return responsavels;
    }

}
