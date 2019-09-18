/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.Esame;


/**
 *
 * @author Davide
 */
public interface EsameDAO extends DAO<Esame, String> {
    
    public Esame getById(int id) throws DAOException; 
}
