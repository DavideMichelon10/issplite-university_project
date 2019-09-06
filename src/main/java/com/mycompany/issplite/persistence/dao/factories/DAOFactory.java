/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.factories;

/**
 *
 * @author Davide
 */
public interface DAOFactory {
        /**
     * Shutdowns the connection to the storage system.
     * 
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public void shutdown();
    
    /**
     * Returns the concrete {@link DAO dao} which type is the class passed as
     * parameter.
     * 
     * @param <DAO_CLASS> the class name of the {@code dao} to get.
     * @param daoInterface the class instance of the {@code dao} to get.
     * @return the concrete {@code dao} which type is the class passed as
     * parameter.
     * @throws DAOFactoryException if an error occurred during the operation.
     * 
     * @author Stefano Chirico
     * @since 1.0.0.190406
     */
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException;

}
