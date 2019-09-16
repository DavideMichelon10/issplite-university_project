/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.EsamePrescritto;
import com.mycompany.issplite.persistence.entities.FarmacoPrescritto;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.PazienteUltimiEsamiFarmaci;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davide
 */

public class JDBCMedicoDAO extends JDBCDAO<Medico, String> implements MedicoDAO {

    private static final String GETMEDICO = "SELECT * FROM medico WHERE idmedico = ? AND password = ?";
    private static final String GETMEDICOBYID = "SELECT * FROM medico WHERE idmedico = ? ";
    private static final String GETLASTESAMEFARMACODATE = "select FA.idpaziente, FA.ssn, FA.name AS farmaconame, FA.erogationdate AS erogationdatefarmaco, ES.name AS esamename, ES.erogationdate AS erogationdateesame from (select idPaziente, ssn, PR.erogationDate, F.name, E.medico_idmedico as medico from paziente P, eroga E, visita V, prescrizione PR, farmaco F where P.idpaziente = E.paziente_idpaziente  AND E.visita_idVisita = V.idVisita AND PR.Visita_idVisita = V.idVisita AND F.idFarmaco = PR.Farmaco_idFarmaco order by erogationdate DESC limit 1) AS FA, (select  P.idPaziente, PR.erogationDate, ES.name, E.medico_idmedico as medico_ from paziente P, eroga E, visita V, prescrizione PR, Risultato R, Esame ES where P.idpaziente = E.paziente_idpaziente  AND E.visita_idVisita = V.idVisita AND PR.Visita_idVisita = V.idVisita AND PR.risultato_idRisultato = R.idRisultato AND R.esame_idEsame = ES.idEsame order by erogationdate DESC limit 1) AS ES where FA.idPaziente = ES.idPaziente AND FA.medico = ES.medico_ AND FA.medico = ?;";
    private static final String GETESAMIPRESCRITTI = "select ES.idEsame, ES.name, R.description, PR.erogationdate, R.ResultDate, V.ticketPagato from paziente P, Eroga E, Visita V, Prescrizione PR, Risultato R, Esame ES where P.idPaziente = ? AND E.Paziente_IdPaziente = P.IdPaziente AND V.idVisita = E.Visita_idVisita AND PR.Visita_idVisita = V.idVisita AND R.idRisultato = PR.Risultato_idRisultato AND R.esame_idEsame = ES.idEsame;";
    private static final String GETFARMACIPRESCRITTI = "select  F.idFarmaco, F.name, F.description, PR.erogationdate, V.ticketPagato from paziente P, Eroga E, Visita V, Prescrizione PR, Farmaco F where P.idPaziente = ? AND E.Paziente_IdPaziente = P.IdPaziente AND V.idVisita = E.Visita_idVisita AND PR.Visita_idVisita = V.idVisita AND F.idFarmaco = PR.Farmaco_idFarmaco;";
    private static final String INSERTVISITA = "INSERT INTO visita(visitdate, ticket, ticketpagato) VALUES(?,11, ?);";
    private static final String INSERTEROGA = "insert into Eroga(medico_idmedico, paziente_idpaziente, visita_idvisita) values (?, ?, ?);";
    private static final String INSERTRISULTATO = "insert into risultato(resultdate, esame_idesame) values (?,?);";
    private static final String INSERTPRESCRIZIONE = "insert into Prescrizione(erogationdate, ticketpagato, visita_idvisita, risultato_idrisultato) values (?, 0, ?, ?);";
    
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

    @Override
    public List<EsamePrescritto> getEsamiPrescritti(String idPaziente) throws DAOException {
    
        if (idPaziente == null) {
            throw new DAOException("paziente and medico are mandatory fields", new NullPointerException("paziente or medico are null"));
        }
    
        List<EsamePrescritto> esamiPrescritti = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement(GETESAMIPRESCRITTI)) {
            stm.setString(1, idPaziente);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    EsamePrescritto esamePrescritto = new EsamePrescritto();
                    esamePrescritto.setIdEsame(rs.getInt("idesame"));
                    esamePrescritto.setName(rs.getString("name"));
                    esamePrescritto.setRisultato(rs.getString("description"));
                    esamePrescritto.setErogationDate(rs.getString("erogationDate"));
                    
                    if(rs.getBoolean("ticketPagato")){
                        esamePrescritto.setIsPagato(true);
                    }else{
                        esamePrescritto.setIsPagato(false);
                    }

                    esamiPrescritti.add(esamePrescritto);
                }
                return esamiPrescritti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }    
    

    @Override
    public List<FarmacoPrescritto> getFarmaciPrescritti(String idPaziente) throws DAOException {
        
        if (idPaziente == null) {
            throw new DAOException("paziente and medico are mandatory fields", new NullPointerException("paziente or medico are null"));
        }
    
        List<FarmacoPrescritto> farmaciPrescritti = new ArrayList<>();
        try (PreparedStatement stm = CON.prepareStatement(GETFARMACIPRESCRITTI)) {
            stm.setString(1, idPaziente);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {

                    FarmacoPrescritto farmacoPrescritto = new FarmacoPrescritto();

                    farmacoPrescritto.setIdFarmaco(rs.getInt("idfarmaco"));
                    farmacoPrescritto.setName(rs.getString("name"));
                    farmacoPrescritto.setDescription(rs.getString("description"));

                    if(rs.getBoolean("ticketPagato")){
                        farmacoPrescritto.setIsPagato(true);
                    }else{
                        farmacoPrescritto.setIsPagato(false);
                    }

                    farmacoPrescritto.setPrescriptionDate(rs.getString("erogationdate"));                    
                    farmaciPrescritti.add(farmacoPrescritto);
                }
                return farmaciPrescritti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }    

    @Override
    public int insertVisita(boolean isPagato) throws DAOException {

        try (PreparedStatement stm = CON.prepareStatement(INSERTVISITA, Statement.RETURN_GENERATED_KEYS)) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            stm.setTimestamp(1, ts);
            if(isPagato){
                stm.setInt(2, 1);
            }else{
                stm.setInt(2, 0);
            }
            
            stm.executeUpdate();
            
            ResultSet keys = stm.getGeneratedKeys();
            int lastKey = 1;
            while (keys.next()) {
              lastKey = keys.getInt(1);
            }
            return lastKey;
            
        } catch (SQLException ex) {
            throw new DAOException("Impossible to insert visita", ex);
        }
    }

    @Override
    public void insertEroga(String idMedico, String idPaziente, int idVisita) throws DAOException {
        
        if ( ((Integer) idVisita == null) || (idMedico == null) || (idPaziente == null)){
            throw new DAOException("paziente and medico and visita are mandatory fields", new NullPointerException("paziente or medico or visita are null"));
        }
        
        try (PreparedStatement stm = CON.prepareStatement(INSERTEROGA)) {
            

            stm.setString(1, idMedico);
            stm.setString(2, idPaziente);
            stm.setInt(3, idVisita);
           
            stm.executeUpdate();
            
            
        } catch (SQLException ex) {
            throw new DAOException("Impossible to insert eroga", ex);
        }
    }

    @Override
    public void insertPrescrizione(int idEsame, int idVisita) throws DAOException {
        if ( ((Integer) idEsame == null) || ((Integer) idVisita == null)){
            throw new DAOException("esame and visita and visita are mandatory fields", new NullPointerException("esame or visita are null"));
        }
        
        try (PreparedStatement stm = CON.prepareStatement(INSERTRISULTATO, Statement.RETURN_GENERATED_KEYS)) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            stm.setTimestamp(1, ts);
            stm.setInt(2, idEsame);
            
            stm.executeUpdate();
            
            ResultSet keys = stm.getGeneratedKeys();
            int lastKey = 1;
            while (keys.next()) {
              lastKey = keys.getInt(1);
            }
            
            try (PreparedStatement stmt = CON.prepareStatement(INSERTPRESCRIZIONE)) {
                
                stmt.setTimestamp(1, ts);
                stmt.setInt(2, idVisita);
                stmt.setInt(3, lastKey);
                
                stmt.executeUpdate();
            }

            
            
        } catch (SQLException ex) {
            throw new DAOException("Impossible to insert visita", ex);
        }
    }
    
}
