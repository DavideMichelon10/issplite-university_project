/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.factories.jdbc;

import com.mycompany.issplite.persistence.dao.factories.DAOException;
import java.sql.Connection;
import java.util.List;


public class JDBCDAOImpl<ENTITY_CLASS, PRIMARY_KEY_CLASS> extends JDBCDAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {

    public JDBCDAOImpl(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ENTITY_CLASS getByPrimaryKey(PRIMARY_KEY_CLASS arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ENTITY_CLASS> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
