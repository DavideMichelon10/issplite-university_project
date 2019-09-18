package com.mycompany.issplite.persistence.dao.jdbc;


import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.Paziente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Davide
 */
public class JDBCPazienteDAO extends JDBCDAO<Paziente, String> implements PazienteDAO {

    private static final String GETPAZIENTE = "SELECT * FROM paziente WHERE idpaziente = ? AND password = ?";
    private static final String GETPAZIENTEBYID = "SELECT * FROM paziente WHERE idpaziente = ? ";

    public JDBCPazienteDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Paziente> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paziente getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paziente getByIdAndPassword(String id, String password) throws DAOException {
        if ((id == null) || (password == null)) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETPAZIENTE)) {
            stm.setString(1, id);
            stm.setString(2, password);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Paziente paziente = new Paziente();
                    paziente.setIdPaziente(rs.getString("idpaziente"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setPassword(rs.getString("password"));
                    paziente.setName(rs.getString("name"));
                    paziente.setSurname(rs.getString("surname"));
                    paziente.setPhotoPath(rs.getString("photopath"));
                    
                    paziente.setSsn(rs.getString("ssn"));
                   
                    return paziente;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public Paziente getById(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETPAZIENTEBYID)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Paziente paziente = new Paziente();
                    paziente.setIdPaziente(rs.getString("idpaziente"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setPassword(rs.getString("password"));
                    paziente.setName(rs.getString("name"));
                    paziente.setSurname(rs.getString("surname"));
                    paziente.setPhotoPath(rs.getString("photopath"));
                    
                    paziente.setSsn(rs.getString("ssn"));
                    //paziente.setSex(rs.getString("email"));
                    //paziente.setPassword(rs.getString("password"));
                    //paziente.setName(rs.getString("name"));
                    //paziente.setSurname(rs.getString("surname"));
                    //paziente.setPhotoPath(rs.getString("photopath"));

                   
                    return paziente;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }
}
