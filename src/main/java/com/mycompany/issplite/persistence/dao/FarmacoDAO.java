/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.Farmaco;
import java.util.List;

/**
 *
 * @author Davide
 */
public interface FarmacoDAO extends DAO<Farmaco, String> {
    
    @Override
    public List<Farmaco> getAll() throws DAOException;
    
    public Farmaco getById(int idFarmaco) throws DAOException;
}