package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.Medico;

/**
 *
 * @author Davide
 */
public interface MedicoDAO extends DAO<Medico, String> {
    
    public Medico getById(String id) throws DAOException;

    public Medico getByIdAndPassword(String id, String password) throws DAOException;
}
