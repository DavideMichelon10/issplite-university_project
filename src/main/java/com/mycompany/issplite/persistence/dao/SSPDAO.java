package com.mycompany.issplite.persistence.dao;

import com.mycompany.issplite.persistence.dao.factories.DAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.EsamePrescritto;
import com.mycompany.issplite.persistence.entities.RicetteErogatePerGiorno;
import com.mycompany.issplite.persistence.entities.RisultatoNonEseguito;
import com.mycompany.issplite.persistence.entities.SSP;
import java.util.List;


public interface SSPDAO extends DAO<SSP, String> {
    public SSP getByIdAndPassword(String id, String password) throws DAOException;
    public SSP getById(String id) throws DAOException;
    public List<RicetteErogatePerGiorno> getRicettePerDay(String provincia,String date) throws DAOException;
    public int insertRichiamo(String motivation, int sex, String dateStart, String dateEnd, String idSsp) throws  DAOException;
    public List<RisultatoNonEseguito> getEsamiWithoutResults(String provincia) throws DAOException;
    public RisultatoNonEseguito getRisultatoNonEseguitoById(int idRisultato) throws DAOException;
    public void insertDescription(int idRisultato, String description) throws DAOException;
}
