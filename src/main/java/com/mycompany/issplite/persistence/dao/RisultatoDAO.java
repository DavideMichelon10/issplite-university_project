/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.Risultato;

/**
 *
 * @author lollo
 */
public interface RisultatoDAO extends DAO<Risultato, String>{
    public Risultato getRisultatoById(String idRicetta) throws DAOException;
}
