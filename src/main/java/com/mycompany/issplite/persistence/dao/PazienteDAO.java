package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.Paziente;

/**
 *
 * @author Davide
 */
public interface PazienteDAO extends DAO<Paziente, String> {
    
    public Paziente getByIdAndPassword(String id, String password) throws DAOException;
    public Paziente getById(String id) throws DAOException;

}
