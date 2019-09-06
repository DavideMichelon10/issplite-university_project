/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.SSP;

/**
 *
 * @author Davide
 */
public interface SSPDAO extends DAO<SSP, String> {
    public SSP getByIdAndPassword(String id, String password) throws DAOException;
    public SSP getById(String id) throws DAOException;

}
