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

    private static final String GETMEDICO = "SELECT * FROM medico M WHERE idmedico = ? AND password = ? ORDER BY M.name";
    private static final String GETMEDICOBYID = "SELECT * FROM medico M WHERE idmedico = ? ORDER BY M.name";
    private static final String GETESAMIPRESCRITTI = "select ES.idEsame, ES.name, R.description, PR.erogationdate, R.ResultDate, PR.ticketPagato from paziente P, Eroga E, Visita V, Prescrizione PR, Risultato R, Esame ES where P.idPaziente = ? AND E.Paziente_IdPaziente = P.IdPaziente AND V.idVisita = E.Visita_idVisita AND PR.Visita_idVisita = V.idVisita AND R.idRisultato = PR.Risultato_idRisultato AND R.esame_idEsame = ES.idEsame ORDER BY PR.erogationdate DESC, ES.name;";
    private static final String GETFARMACIPRESCRITTI = "select  F.idFarmaco, F.name, F.description, PR.erogationdate, PR.ticketPagato from paziente P, Eroga E, Visita V, Prescrizione PR, Farmaco F where P.idPaziente = ? AND E.Paziente_IdPaziente = P.IdPaziente AND V.idVisita = E.Visita_idVisita AND PR.Visita_idVisita = V.idVisita AND F.idFarmaco = PR.Farmaco_idFarmaco ORDER BY PR.erogationdate DESC, F.name;";
    private static final String INSERTVISITA = "INSERT INTO visita(visitdate, ticket, ticketpagato) VALUES(?,11, ?);";
    private static final String INSERTEROGA = "insert into Eroga(medico_idmedico, paziente_idpaziente, visita_idvisita) values (?, ?, ?);";
    private static final String INSERTRISULTATO = "insert into risultato(resultdate, esame_idesame) values (?,?);";
    private static final String INSERTPRESCRIZIONE = "insert into Prescrizione(erogationdate, ticketpagato, visita_idvisita, risultato_idrisultato) values (?, 0, ?, ?);";
    private static final String INSERTPRESCRIZIONEFARMACO = "insert into Prescrizione(erogationdate, ticketpagato, visita_idvisita, farmaco_idfarmaco) values (?, 0, ?, ?);";

    private static final String GETMEDICIBYPROVINCIA = "SELECT * FROM MEDICO WHERE provincia = ?";
    private static final String GETBYIDANDPROVINCIA = "SELECT * FROM medico WHERE idmedico = ? AND provincia = ?";
    private static final String GETLASTESAMEFARMACODATE = "WITH LastEsame AS(SELECT DISTINCT ON (P.idPaziente) P.idPaziente, P.ssn, Pr.erogationDate, Es.name\n"
            + "FROM MEDICO M\n"
            + "INNER JOIN EROGA E ON (M.idMedico = E.Medico_idMedico)\n"
            + "INNER JOIN PAZIENTE P ON(E.Paziente_idPaziente = P.idPaziente)\n"
            + "INNER JOIN VISITA V ON (V.idVisita = E.visita_idVisita)\n"
            + "INNER JOIN PRESCRIZIONE Pr ON(Pr.visita_idVisita = V.idVisita)\n"
            + "INNER JOIN RISULTATO R ON(R.idRisultato = Pr.Risultato_idRisultato)\n"
            + "INNER JOIN ESAME Es ON (R.Esame_idEsame = Es.idEsame)\n"
            + "WHERE M.idMedico = ?\n"
            + "ORDER  BY P.idPaziente, Pr.erogationDate DESC),\n"
            + "LastFarmaco AS(SELECT DISTINCT ON (P.idPaziente) P.idPaziente, P.ssn, Pr.erogationDate, F.name\n"
            + "FROM MEDICO M\n"
            + "INNER JOIN EROGA E ON (M.idMedico = E.Medico_idMedico)\n"
            + "INNER JOIN PAZIENTE P ON(E.Paziente_idPaziente = P.idPaziente)\n"
            + "INNER JOIN VISITA V ON (V.idVisita = E.visita_idVisita)\n"
            + "INNER JOIN PRESCRIZIONE Pr ON(Pr.visita_idVisita = V.idVisita)\n"
            + "INNER JOIN FARMACO F ON(Pr.Farmaco_idFarmaco = F.idFarmaco)\n"
            + "WHERE M.idMedico = ?\n"
            + "ORDER  BY P.idPaziente, Pr.erogationDate DESC)\n"
            + "SELECT E.idPaziente, E.ssn, E.erogationDate AS esameDate, E.name AS esameName, F.erogationDate AS farmacoDate, F.name farmacoName\n"
            + "FROM LastFarmaco F\n"
            + "INNER JOIN LastEsame E ON (F.idPaziente = E.idPaziente)";

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
                    medico.setName(rs.getString("name"));
                    medico.setSurname(rs.getString("surname"));
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
                    medico.setName(rs.getString("name"));
                    medico.setSurname(rs.getString("surname"));
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
            stm.setString(2, idMedico);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    PazienteUltimiEsamiFarmaci pazienteUltimiEsamiFarmaci = new PazienteUltimiEsamiFarmaci();
                    pazienteUltimiEsamiFarmaci.setIdpaziente(rs.getString("idpaziente"));
                    pazienteUltimiEsamiFarmaci.setNameEsame(rs.getString("esameName"));
                    pazienteUltimiEsamiFarmaci.setSsn(rs.getString("ssn"));
                    pazienteUltimiEsamiFarmaci.setNameFarmaco(rs.getString("farmaconame"));
                    pazienteUltimiEsamiFarmaci.setErogationDateEsame(rs.getString("esameDate"));
                    pazienteUltimiEsamiFarmaci.setErogationDateFarmaco(rs.getString("farmacoDate"));
                    lastEsamiFarmaci.add(pazienteUltimiEsamiFarmaci);
                }
                return lastEsamiFarmaci;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Error inside getLastEsameFarmacoDate", ex);

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

                    if (rs.getBoolean("ticketPagato")) {
                        esamePrescritto.setIsPagato(true);
                    } else {
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
    public List<Medico> getByProvincia(String provinciaP) throws DAOException {
        ArrayList<Medico> medici = new ArrayList<>();
        if (provinciaP == null) {
            throw new DAOException("provincia is mandatory field", new NullPointerException("provincia is null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETMEDICIBYPROVINCIA)) {
            stm.setString(1, provinciaP);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getString("idmedico"));
                    medico.setProvincia(rs.getString("provincia"));
                    medico.setName(rs.getString("name"));
                    medico.setSurname(rs.getString("surname"));
                    medico.setPassword(rs.getString("password"));
                    medico.setEmail(rs.getString("email"));
                    medico.setCity(rs.getString("city"));
                    medico.setSurname(rs.getString("surname"));
                    medici.add(medico);

                }
                return medici;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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

                    if (rs.getBoolean("ticketPagato")) {
                        farmacoPrescritto.setIsPagato(true);
                    } else {
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
            if (isPagato) {
                stm.setInt(2, 1);
            } else {
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

        if (((Integer) idVisita == null) || (idMedico == null) || (idPaziente == null)) {
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
        if (((Integer) idEsame == null) || ((Integer) idVisita == null)) {
            throw new DAOException("esame and visita and visita are mandatory fields", new NullPointerException("esame or visita are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(INSERTRISULTATO, Statement.RETURN_GENERATED_KEYS)) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            stm.setTimestamp(1, null);
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

    @Override
    public Medico getByIdAndProvincia(String id, String provincia) throws DAOException {

        if ((id == null) || (provincia == null)) {
            throw new DAOException("id and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETBYIDANDPROVINCIA)) {
            stm.setString(1, id);
            stm.setString(2, provincia);
            try (ResultSet rs = stm.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Medico medico = new Medico();
                    medico.setIdMedico(rs.getString("idmedico"));
                    medico.setName(rs.getString("name"));
                    medico.setSurname(rs.getString("surname"));
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
    public void insertPrescrizioneFarmaco(int idFarmaco, int idVisita) throws DAOException {
        if (((Integer) idFarmaco == null) || ((Integer) idVisita == null)) {
            throw new DAOException(" visita are mandatory fields", new NullPointerException("farmaco or visita are null"));
        }

        try (PreparedStatement stmt = CON.prepareStatement(INSERTPRESCRIZIONEFARMACO)) {
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            stmt.setTimestamp(1, ts);
            stmt.setInt(2, idVisita);
            stmt.setInt(3, idFarmaco);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCMedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
