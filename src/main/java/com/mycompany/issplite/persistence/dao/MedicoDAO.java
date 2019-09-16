package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.EsamePrescritto;
import com.mycompany.issplite.persistence.entities.FarmacoPrescritto;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.PazienteUltimiEsamiFarmaci;
import java.util.List;

/**
 *
 * @author Davide
 */
public interface MedicoDAO extends DAO<Medico, String> {
    
    public Medico getById(String id) throws DAOException;

    public Medico getByIdAndPassword(String id, String password) throws DAOException;
    
    public List<PazienteUltimiEsamiFarmaci> getLastEsameFarmacoDate(String idMedico) throws DAOException;
    
    public List<EsamePrescritto> getEsamiPrescritti(String idPaziente) throws DAOException;
    
    public List<FarmacoPrescritto> getFarmaciPrescritti(String idPaziente) throws DAOException;

}
