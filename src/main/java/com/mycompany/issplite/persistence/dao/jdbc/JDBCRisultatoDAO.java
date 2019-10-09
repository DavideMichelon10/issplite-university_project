/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.Risultato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.mycompany.issplite.persistence.dao.RisultatoDAO;

/**
 *
 * @author lollo
 */
public class JDBCRisultatoDAO extends JDBCDAO<Risultato, String>implements RisultatoDAO{
    private static final String GETBYIDRISULTATO = "SELECT * FROM RISULTATO WHERE idRisultato = ?";
    
    public JDBCRisultatoDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Risultato getRisultatoById(String idRisultato) throws DAOException {
        if (idRisultato == null) {
            throw new DAOException("IdRicetta is a mandatory field", new NullPointerException("idRisultato is null"));
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETBYIDRISULTATO)) {
            int idRis = Integer.parseInt(idRisultato);
            stm.setInt(1, idRis);
            try ( ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Risultato risultato = new Risultato();
                    risultato.setDescription(rs.getString("description"));
                    risultato.setIdEsame(rs.getString("esame_idEsame"));
                    risultato.setIdRisultato(rs.getString("idRisultato"));
                    risultato.setResultDate(rs.getString("resultDate"));

                    return risultato;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of results", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    @Override
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> arg0) throws DAOFactoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Risultato getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Risultato> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
