/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.es.geo.dao;

import br.es.geo.dao.util.DAO;
import br.es.geo.dao.util.DAOGeneric;
import br.es.geo.modelo.FaleConosco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class DAOfaleConosco extends DAOGeneric implements DAO<FaleConosco> {

	public FaleConosco create() {
		
		return new FaleConosco();
	}
	

        @Override
	public long insert(FaleConosco obj) throws SQLException, ClassNotFoundException {
		
		this.openConnection();
		
		String sql = "INSERT INTO faleconosco (nome, email, assunto, texto) "
                        + "VALUES ('"+obj.getNome()+"','"+obj.getEmail()+"','"+obj.getAssunto()+"','" +obj.getTexto() +"')"; 
		
		long id = this.executeUpdate(sql);
		
		obj.setId(id);
		
		this.closeConnection();
		return id;
	}

        @Override
	public long update(FaleConosco obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		this.openConnection();

		String sql = "UPDATE faleconosco" +
		" SET nome = '" + obj.getNome() +
		"', assunto = '" + obj.getAssunto()+
                "', email = '" + obj.getEmail()+
                "', texto = '"+ obj.getTexto()+
                "', resposta = '"+ obj.getResposta()+
                "', responsavel = "+ obj.getUserResponse().getId()+        
		" WHERE id = " + obj.getId();

                System.out.println(sql);
		long id = this.executeUpdate(sql);

		obj.setId(id);

		this.closeConnection();
                return id;
	}

        @Override
	public void delete(FaleConosco obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		this.openConnection();
		
		String sql = "DELETE FROM faleconosco WHERE id = " + obj.getId(); 
		
		long id = this.executeUpdate(sql);
		
		obj.setId(id);
		
		this.closeConnection();
		
		
	}

        @Override
	public FaleConosco findbyID(long id) throws ClassNotFoundException, SQLException {
		//Query para buscar o faleconosco
		this.openConnection();
		
		String sql = "SELECT * FROM faleconosco WHERE id ="+id;
		
		ResultSet rs = this.executeQuery(sql);
		
		List<FaleConosco> faleconosco = retriveFaleConoscos(rs);
		
		this.closeConnection();
		
		return faleconosco.get(0);
	}
        
   
        
        
        @Override
	public List<FaleConosco> findAll() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		this.openConnection();
		
		String sql = "SELECT * FROM faleconosco ";
		
		ResultSet rs = this.executeQuery(sql);
		
		List<FaleConosco> faleconosco = retriveFaleConoscos(rs);
		
		this.closeConnection();
		
		return faleconosco;
	}
	
	private List<FaleConosco> retriveFaleConoscos(ResultSet rs) throws SQLException, ClassNotFoundException
	{
		List<FaleConosco> faleconoscos = new ArrayList<FaleConosco>();
		
		while (rs.next())
		{
			FaleConosco obj = new FaleConosco();
			
			obj.setId(rs.getLong("id"));
			
                        obj.setNome(rs.getString("nome"));
                        
			obj.setEmail(rs.getString("email"));
			
			obj.setAssunto(rs.getString("assunto"));
                        
                        obj.setTexto(rs.getString("texto"));
                        
                        obj.setResposta(rs.getString("resposta"));
                        
                        DAOadm daoa= new DAOadm();
                        obj.setUserResponse(daoa.findbyID(rs.getLong("responsavel")));
			
			faleconoscos.add(obj);
		}
		
		return faleconoscos;
	}

    

}

