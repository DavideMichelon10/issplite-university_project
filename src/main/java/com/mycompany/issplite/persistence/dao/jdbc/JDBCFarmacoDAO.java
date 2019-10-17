/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.FarmacoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.Farmaco;
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
public class JDBCFarmacoDAO extends JDBCDAO<Farmaco, String> implements FarmacoDAO {

    private static final String GETALLFARMACI = "SELECT * FROM Farmaco F ORDER BY F.name;";
    private static final String GETFARMACOBYID = "SELECT * FROM Farmaco F WHERE F.idfarmaco = ?";

    public JDBCFarmacoDAO(Connection con) {
        super(con);
    }

    @Override
    public List<Farmaco> getAll() throws DAOException {

        List<Farmaco> farmaci = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement(GETALLFARMACI)) {
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {

                    Farmaco f = new Farmaco();

                    f.setIdFarmaco(rs.getInt("idfarmaco"));
                    f.setName(rs.getString("name"));
                    f.setPrice(rs.getInt("price"));

                    farmaci.add(f);
                }
                return farmaci;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of esami", ex);
        }
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Farmaco getByPrimaryKey(String primaryKey) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Farmaco getById(int idFarmaco) throws DAOException {
        Farmaco f = new Farmaco();

        try (PreparedStatement stm = CON.prepareStatement(GETFARMACOBYID)) {
            stm.setInt(1, idFarmaco);

            
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {

                    f.setIdFarmaco(rs.getInt("idfarmaco"));
                    f.setName(rs.getString("name"));
                    f.setPrice(rs.getInt("price"));

                }
                return f;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of farmaci", ex);
        }
    }
}
