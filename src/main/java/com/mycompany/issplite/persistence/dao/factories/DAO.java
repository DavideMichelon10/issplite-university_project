/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.factories;

import java.util.List;

/**
 *
 * @author Davide
 */
public interface DAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {

    public Long getCount() throws DAOException;

    public ENTITY_CLASS getByPrimaryKey(PRIMARY_KEY_CLASS primaryKey) throws DAOException;

    public List<ENTITY_CLASS> getAll() throws DAOException;
   
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoClass) throws DAOFactoryException;
}
