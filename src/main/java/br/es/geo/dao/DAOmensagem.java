/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.Adm;

import br.es.geo.modelo.Mensagem;
import br.es.geo.modelo.Responsavel;
import br.es.geo.modelo.util.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOmensagem extends DAOGeneric implements DAO<Mensagem> {
    
    public Mensagem create() {
        
        return new Mensagem();
    }
    
    @Override
    public long insert(Mensagem obj) throws SQLException, ClassNotFoundException {
        
        this.openConnection();
        
        String sql = "INSERT INTO mensagem (mensagem, destinatarioEmail, remetenteEmail, horaEnvio) "
                + "VALUES ('" + obj.getConteudo() + "','" + obj.getDestinatario().getEmail() + "','" + obj.getRemetente().getEmail() + "','" + obj.getHoraEnvio() + "')";
        
        long id = this.executeUpdate(sql);
        
        obj.setId(id);
        
        this.closeConnection();
        return id;
    }
    
    @Override
    public long update(Mensagem obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();
        String sql = "";
        
        long id = this.executeUpdate(sql);
        
        obj.setId(id);
        
        this.closeConnection();
        return id;
    }
    
    @Override
    public void delete(Mensagem obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();
        
        String sql = "DELETE FROM mensagem" + "Where id = " + obj.getId();
        
        long id = this.executeUpdate(sql);
        
        obj.setId(id);
        
        this.closeConnection();
        
    }
    
    @Override
    public Mensagem findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o mensagem
        this.openConnection();
        
        String sql = "SELECT * FROM mensagem WHERE id =" + id;
        
        ResultSet rs = this.executeQuery(sql);
        
        List<Mensagem> mensagem = retriveMensagems(rs);
        
        this.closeConnection();
        
        if (mensagem.isEmpty()) {
            return null;
        }
        
        return mensagem.get(0);
    }
    
    public List<Mensagem> findByRemetente(String email) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();
        
        String sql = "SELECT * FROM mensagem WHERE remetenteEmail='" + email + "'";
        
        ResultSet rs = this.executeQuery(sql);
        
        List<Mensagem> mensagem = retriveMensagems(rs);
        
        this.closeConnection();
        
        return mensagem;
    }
    
    public List<Mensagem> findByDestinatario(String email) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();
        
        String sql = "SELECT * FROM mensagem WHERE destinatarioEmail='" + email + "'";
        
        ResultSet rs = this.executeQuery(sql);
        
        List<Mensagem> mensagem = retriveMensagems(rs);
        
        this.closeConnection();
        
        return mensagem;
    }
    
    @Override
    public List<Mensagem> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();
        
        String sql = "SELECT * FROM mensagem ";
        
        ResultSet rs = this.executeQuery(sql);
        
        List<Mensagem> mensagem = retriveMensagems(rs);
        
        this.closeConnection();
        
        return mensagem;
    }
    
    private List<Mensagem> retriveMensagems(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Mensagem> mensagems = new ArrayList<>();
        DAOadm da = new DAOadm();
        DAOresponsavel dr = new DAOresponsavel();
        List<Adm> la = da.findAll();
        List<Responsavel> lr = dr.findAll();
        List<Usuario> lu = new ArrayList<>();
        lu.addAll(la);
        lu.addAll(lr);
        while (rs.next()) {
            Mensagem obj = new Mensagem();
            
            obj.setId(rs.getLong("id"));
            
            obj.setConteudo(rs.getString("mensagem"));
            
            String remetente = rs.getString("remetenteEmail");
            String destinatario = rs.getString("destinatarioEmail");
            
            for (int i = 0; i < lu.size(); i++) {
                if (lu.get(i).getEmail().equals(remetente)) {
                    obj.setRemetente(lu.get(i));
                }
                
                if (lu.get(i).getEmail().equals(destinatario)) {
                    obj.setDestinatario(lu.get(i));
                }
            }
            
            obj.setHoraEnvio(rs.getDate("horaEnvio"));
            
            mensagems.add(obj);
        }
        
        return mensagems;
    }
    
}
