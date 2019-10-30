/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.Hash;
/**
 *
 * @author lollo
 */
public interface HashDAO extends DAO<Hash, String>{
    public Hash getById(String idUser) throws DAOException;
    public void updateSalt(String idUser, String salt) throws DAOException;
}
