/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.HashDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.Hash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lollo
 */
public class JDBCHashDAO extends JDBCDAO<Hash, String> implements HashDAO {

    private static final String GETBYID = "SELECT * FROM HashPassword WHERE User_idUser = ?;";
    private static final String UPDATESALT = "UPDATE HashPassword SET SALT = ? WHERE user_idUser = ?";

    public JDBCHashDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Hash getById(String idUser) throws DAOException {
        if (idUser == null) {
            throw new DAOException("id is mandatory fields", new NullPointerException("id is null"));
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETBYID)) {
            stm.setString(1, idUser);
            try ( ResultSet rs = stm.executeQuery()) {
                Hash hash = new Hash();

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one exam with the same id!");
                    }
                    hash.setIdUser(rs.getString("User_idUser"));
                    hash.setSalt(rs.getString("salt"));

                    return hash;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the exam", ex);
        }
    }

    @Override
    public Hash getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Hash> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSalt(String idUser, String salt) throws DAOException {
        if (idUser == null) {
            throw new DAOException("id is mandatory fields", new NullPointerException("id is null"));
        }

        try ( PreparedStatement stm = CON.prepareStatement(UPDATESALT)) {
            stm.setString(1, salt);
            stm.setString(2, idUser);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCHashDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
