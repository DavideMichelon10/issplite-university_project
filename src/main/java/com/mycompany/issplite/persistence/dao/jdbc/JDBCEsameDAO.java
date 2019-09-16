/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.EsameDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.Esame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davide
 */
public class JDBCEsameDAO extends JDBCDAO<Esame, String> implements EsameDAO{

    private static final String GETALLESAMI = "SELECT * FROM esame;";

    public JDBCEsameDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Esame getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Esame> getAll() throws DAOException {
    
        List<Esame> esami = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement(GETALLESAMI)) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {

                    Esame esame = new Esame();
                    esame.setIdEsame(rs.getInt("idesame"));
                    esame.setName(rs.getString("name"));
                    esame.setCosto(rs.getFloat("costo"));

                    esami.add(esame);
                }
                return esami;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of esami", ex);
        }  
    }
}
