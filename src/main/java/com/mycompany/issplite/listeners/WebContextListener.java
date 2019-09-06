/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.listeners;

import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAOFactory;

/**
 * Web application lifecycle listener.
 *
 * @author Davide
 */
public class WebContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            JDBCDAOFactory.configure();
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            
            sce.getServletContext().setAttribute("daoFactory", daoFactory);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("CONTEXT DESTROYED");
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        if (daoFactory != null) {
            daoFactory.shutdown();
        }
        daoFactory = null;
    }
}
