/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.es.geo.dao.DAOadm;
import br.es.geo.modelo.Adm;
import java.sql.SQLException;
import java.util.List;
import static junit.framework.Assert.*;
import org.junit.Test;

/**
 *
 * @author Pedro
 */

public class TesteAddAdm {

    private final String email = "adm@adm.com";
    private final String senha = "adm123";
    private final String nome = "adm";

    @Test
    public void AddAdm() throws SQLException, ClassNotFoundException {
        DAOadm dao = new DAOadm();
        List<Adm> list = dao.findByEmail(email);
        System.out.println(list);
        if ( !list.isEmpty()) {

            dao.delete(list.get(0));
        }
        Adm adm = new Adm();
        adm.setEmail(email);
        adm.setNome(nome);
        adm.setSenha(senha);
        long id = dao.insert(adm);
        
        assertEquals(id, (long) dao.findByEmail(email).get(0).getId());

    }

    
   
    
}
