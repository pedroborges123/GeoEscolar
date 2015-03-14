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
import br.es.geo.modelo.Crianca;
import br.es.geo.modelo.Localidade;
import br.es.geo.modelo.util.TipoLocal;

public class DAOcrianca extends DAOGeneric implements DAO<Crianca> {

    @Override
    public Crianca create() {

        return new Crianca();
    }

    @Override
    public long insert(Crianca obj) throws SQLException, ClassNotFoundException {

        this.openConnection();

        String sql = "INSERT INTO crianca (nome, email, telefone, idade, sexo, transporte) "
                + "VALUES ('" + obj.getNome() + "','" + obj.getEmail() + "','"
                + obj.getTelefone() + "'," + obj.getIdade() + ",'" + obj.getSexo() +"', "+ obj.getTransporte().getId()+ ")";

        DAOlocalidade dao = new DAOlocalidade();
        long c = dao.insert(obj.getCasa());
        DAOcrianca_localidade dCL = new DAOcrianca_localidade();

        long e = obj.getEscola().getId();
        long id = this.executeUpdate(sql);

        obj.setId(id);
        dCL.insert(id, c);
        dCL.insert(id, e);
        this.closeConnection();
        return id;
    }

    @Override
    public long update(Crianca obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();
        DAOlocalidade dao = new DAOlocalidade();
        String sql = "UPDATE crianca"
                + " SET nome = '" + obj.getNome()
                + "', email = '" + obj.getEmail()
                + "', telefone = '" + obj.getTelefone()
                + "', sexo = '" + obj.getSexo()
                + "', idade = " + obj.getIdade()
                + ", transporte =" + obj.getTransporte().getId()
                + " Where id = " + obj.getId();

        dao.update(obj.getCasa());
        dao.update(obj.getEscola());

      
        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();
        return id;
    }

    @Override
    public void delete(Crianca obj) throws ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub

        this.openConnection();

        String sql = "DELETE FROM crianca" + "Where id = " + obj.getId();

        long id = this.executeUpdate(sql);

        obj.setId(id);

        this.closeConnection();

    }

    @Override
    public Crianca findbyID(long id) throws ClassNotFoundException, SQLException {
        //Query para buscar o crianca
        this.openConnection();

        String sql = "SELECT * FROM crianca WHERE id =" + id;

        ResultSet rs = this.executeQuery(sql);

        List<Crianca> crianca = retriveCriancas(rs);

        this.closeConnection();

        return crianca.get(0);
    }

    @Override
    public List<Crianca> findAll() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM crianca ";

        ResultSet rs = this.executeQuery(sql);

        List<Crianca> crianca = retriveCriancas(rs);

        this.closeConnection();

        return crianca;
    }

    
    public List<Crianca> findbyTransporteId(long id) throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub
        this.openConnection();

        String sql = "SELECT * FROM crianca WHERE transporte= "+ id;

        ResultSet rs = this.executeQuery(sql);

        List<Crianca> crianca = retriveCriancas(rs);

        this.closeConnection();

        return crianca;
    }
    
    
    private List<Crianca> retriveCriancas(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Crianca> criancas = new ArrayList<Crianca>();

        while (rs.next()) {
            Crianca obj = new Crianca();

            obj.setId(rs.getLong("id"));

            obj.setNome(rs.getString("nome"));

            obj.setEmail(rs.getString("email"));

            long x = rs.getLong("transporte");

            DAOtransporteescolar dte = new DAOtransporteescolar();
            
            obj.setTransporte(dte.findbyID(x));
            
            obj.setSexo(rs.getString("Sexo").charAt(0));

            obj.setIdade(rs.getInt("idade"));

            DAOcrianca_localidade daocl = new DAOcrianca_localidade();

            List<Long> listIDs = daocl.findAllByCriancaID(obj.getId());

            DAOlocalidade daol = new DAOlocalidade();
            Localidade l;
            ArrayList<Localidade> list = new ArrayList<>();
            for (int i = 0; i < listIDs.size(); i++) {

                l = daol.findbyID(listIDs.get(i));
                if (l.getTipoLocal().equals(TipoLocal.Casa)) {
                    obj.setCasa(l);
                } else if (l.getTipoLocal().equals(TipoLocal.Escola)) {
                    obj.setEscola(l);
                } else {
                    list.add(l);
                }

            }

            criancas.add(obj);
        }

        return criancas;
    }

}
