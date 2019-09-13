/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.PazienteUltimiEsamiFarmaci;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davide
 */

public class JDBCMedicoDAO extends JDBCDAO<Medico, String> implements MedicoDAO {

    private static final String GETMEDICO = "SELECT * FROM medico WHERE idmedico = ? AND password = ?";
    private static final String GETMEDICOBYID = "SELECT * FROM medico WHERE idmedico = ? ";
    private static final String GETLASTESAMEFARMACODATE = "select FA.idpaziente, FA.ssn, FA.name AS farmaconame, FA.erogationdate AS erogationdatefarmaco, ES.name AS esamename, ES.erogationdate AS erogationdateesame from (select idPaziente, ssn, PR.erogationDate, F.name, E.medico_idmedico as medico from paziente P, eroga E, visita V, prescrizione PR, farmaco F where P.idpaziente = E.paziente_idpaziente  AND E.visita_idVisita = V.idVisita AND PR.Visita_idVisita = V.idVisita AND F.idFarmaco = PR.Farmaco_idFarmaco order by erogationdate DESC limit 1) AS FA, (select  P.idPaziente, PR.erogationDate, ES.name, E.medico_idmedico as medico_ from paziente P, eroga E, visita V, prescrizione PR, Risultato R, Esame ES where P.idpaziente = E.paziente_idpaziente  AND E.visita_idVisita = V.idVisita AND PR.Visita_idVisita = V.idVisita AND PR.risultato_idRisultato = R.idRisultato AND R.esame_idEsame = ES.idEsame order by erogationdate DESC limit 1) AS ES where FA.idPaziente = ES.idPaziente AND FA.medico = ES.medico_ AND FA.medico = ?;";
    
    public JDBCMedicoDAO(Connection con) {
        super(con);
    }
    
    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Medico getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Medico> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Medico getByIdAndPassword(String id, String password) throws DAOException {
        
        if ((id == null) || (password == null)) {
            throw new DAOException("id and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETMEDICO)) {
            stm.setString(1, id);
            stm.setString(2, password);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getString("idmedico"));
                    medico.setProvincia(rs.getString("provincia"));
                    medico.setPassword(rs.getString("password"));
                    medico.setEmail(rs.getString("email"));
                    medico.setCity(rs.getString("city"));
                    medico.setSurname(rs.getString("surname"));

                    return medico;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public Medico getById(String id) throws DAOException {
        if ((id == null)) {
            throw new DAOException("id and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETMEDICOBYID)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getString("idmedico"));
                    medico.setProvincia(rs.getString("provincia"));
                    medico.setPassword(rs.getString("password"));
                    medico.setEmail(rs.getString("email"));
                    medico.setCity(rs.getString("city"));
                    medico.setSurname(rs.getString("surname"));

                    return medico;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public List<PazienteUltimiEsamiFarmaci> getLastEsameFarmacoDate(String idMedico) throws DAOException {
    if ((idMedico == null)) {
        throw new DAOException("paziente and medico are mandatory fields", new NullPointerException("paziente or medico are null"));
    }
    
        List<PazienteUltimiEsamiFarmaci> lastEsamiFarmaci = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement(GETLASTESAMEFARMACODATE)) {
            stm.setString(1, idMedico);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    PazienteUltimiEsamiFarmaci pazienteUltimiEsamiFarmaci = new PazienteUltimiEsamiFarmaci();
                    pazienteUltimiEsamiFarmaci.setIdpaziente(rs.getString("idpaziente"));
                    pazienteUltimiEsamiFarmaci.setNameEsame(rs.getString("esamename"));
                    pazienteUltimiEsamiFarmaci.setSsn(rs.getString("ssn"));
                    pazienteUltimiEsamiFarmaci.setNameFarmaco(rs.getString("farmaconame"));
                    pazienteUltimiEsamiFarmaci.setErogationDateEsame(rs.getString("erogationdateesame"));
                    pazienteUltimiEsamiFarmaci.setErogationDateFarmaco(rs.getString("erogationdatefarmaco"));
                    lastEsamiFarmaci.add(pazienteUltimiEsamiFarmaci);
                }
                return lastEsamiFarmaci;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }
    
}
