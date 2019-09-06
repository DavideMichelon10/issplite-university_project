/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.SSP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Davide
 */

public class JDBCMedicoDAO extends JDBCDAO<Medico, String> implements MedicoDAO {

    private static final String GETMEDICO = "SELECT * FROM medico WHERE idmedico = ? AND password = ?";
    private static final String GETMEDICOBYID = "SELECT * FROM medico WHERE idmedico = ? ";

    
    public JDBCMedicoDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Medico getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Medico> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Medico getByIdAndPassword(String id, String password) throws DAOException {
        
        if ((id == null) || (password == null)) {
            throw new DAOException("id and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETMEDICO)) {
            stm.setString(1, id);
            stm.setString(2, password);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getString("idmedico"));
                    medico.setProvincia(rs.getString("provincia"));
                    medico.setPassword(rs.getString("password"));
                    medico.setEmail(rs.getString("email"));
                    medico.setCity(rs.getString("city"));
                    medico.setSurname(rs.getString("surname"));

                    return medico;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public Medico getById(String id) throws DAOException {
        if ((id == null)) {
            throw new DAOException("id and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETMEDICOBYID)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getString("idmedico"));
                    medico.setProvincia(rs.getString("provincia"));
                    medico.setPassword(rs.getString("password"));
                    medico.setEmail(rs.getString("email"));
                    medico.setCity(rs.getString("city"));
                    medico.setSurname(rs.getString("surname"));

                    return medico;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }
    
}
