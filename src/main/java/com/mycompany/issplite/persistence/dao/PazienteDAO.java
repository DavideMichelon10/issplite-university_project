package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.EsamiSostenutiPaziente;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.RicettePrescrittePaziente;
import com.mycompany.issplite.persistence.entities.RichiamiPrescrittiPaziente;
import com.mycompany.issplite.persistence.entities.Visita;
import java.util.List;

/**
 *
 * @author Davide
 */
public interface PazienteDAO extends DAO<Paziente, String> {
    
    public Paziente getByIdAndPassword(String id, String password) throws DAOException;
    public Paziente getById(String id) throws DAOException;
    public List<EsamiSostenutiPaziente> getAllExamByIdPaziente(String id) throws DAOException;
    public List<RicettePrescrittePaziente> getAllDrugByIdPaziente(String id) throws DAOException;
    public List<RichiamiPrescrittiPaziente> getAllRecallByIdPaziente(String id) throws DAOException;
    public List<Visita> getAllVisitByIdPaziente(String id) throws DAOException;
    public List<Paziente> getPazientiForMedico(String idMedico) throws DAOException;
    public void changePassword(String password, String idPaziente) throws DAOException;
    public void changeEmail(String email, String idPaziente) throws DAOException;
    public void changeMedico(String medico, String idPaziente) throws DAOException;
    public List<Paziente> getPazientiIdSelected(int sex, String dateStart, String dateEnd) throws DAOException;
    public void insertInPrescrizioneRichiamo(String idPaziente, int idRichiamo, int idEsame) throws DAOException;
}
