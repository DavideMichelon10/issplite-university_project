package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.SSP;


public interface SSPDAO extends DAO<SSP, String> {
    public SSP getByIdAndPassword(String id, String password) throws DAOException;
    public SSP getById(String id) throws DAOException;

}
