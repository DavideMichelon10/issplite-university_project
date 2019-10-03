package com.mycompany.issplite.persistence.dao.factories.jdbc;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDAOFactory implements DAOFactory {
    
    private transient Connection CON = null;
    private final transient HashMap<Class, DAO> DAO_CACHE;

    private static JDBCDAOFactory instance;
    
    
    public static DAOFactory getInstance() throws DAOFactoryException {
        if (instance == null) {
            throw new DAOFactoryException("DAOFactory not yet configured. Call DAOFactory.configure(String dbUrl) before use the class");
        }
        return instance;
    }

    
    
    private JDBCDAOFactory() throws DAOFactoryException {
        super();            
        DAO_CACHE = new HashMap<>();
        try {
            String url = "jdbc:postgresql://localhost/issplite";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "cisco");
            props.setProperty("password", "broSonoSUper123QQ");
            //props.setProperty("ssl", "false");
            
            CON = DriverManager.getConnection(url,props);
            System.out.println("DATABASE CONNECTED!");
        } catch (SQLException ex) {
            System.out.println("DATABASE NOT CONNECTED!");

            Logger.getLogger(JDBCDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void configure() throws DAOFactoryException {
        if (instance == null) {
            instance = new JDBCDAOFactory();
        } else {
            throw new DAOFactoryException("DAOFactory already configured. You can call configure only one time");
        }
    }
    
    @Override
    public void shutdown() {
        try {
            CON.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException {
        DAO dao = DAO_CACHE.get(daoInterface);
        if (dao != null) {                                    
            return (DAO_CLASS) dao;
        }

        Package pkg = daoInterface.getPackage();
        String prefix = pkg.getName() + ".jdbc.JDBC";
        try {
            
            Class daoClass = Class.forName(prefix + daoInterface.getSimpleName());

            Constructor<DAO_CLASS> constructor = daoClass.getConstructor(Connection.class);
            DAO_CLASS daoInstance = constructor.newInstance(CON);

            if (!(daoInstance instanceof JDBCDAO)) {
                throw new DAOFactoryException("The daoInterface passed as parameter doesn't extend JDBCDAO class");
            }

            DAO_CACHE.put(daoInterface, daoInstance);
            return daoInstance;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | SecurityException ex) {
            throw new DAOFactoryException("Impossible to return the DAO", ex);
        }
    }
}
