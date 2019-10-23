package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.EsamiSostenutiPaziente;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.RicettePrescrittePaziente;
import com.mycompany.issplite.persistence.entities.RichiamiPrescrittiPaziente;
import com.mycompany.issplite.persistence.entities.TicketPagati;
import com.mycompany.issplite.persistence.entities.Visita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davide
 */
public class JDBCPazienteDAO extends JDBCDAO<Paziente, String> implements PazienteDAO {

    private static final String GETPAZIENTE = "SELECT * FROM paziente WHERE idpaziente = ? AND password = ?";
    private static final String GETPAZIENTEBYID = "SELECT * FROM paziente WHERE idpaziente = ? ";
    private static final String GETPAZIENTEFORMEDICO = "SELECT * FROM paziente WHERE medico_idmedico = ?;";
    private static final String GETESAMIBYIDPAZIENTE = "SELECT V.visitdate, Pr.erogationdate, Es.name, Pr.ticketPagato, R.resultdate, R.idRisultato\n"
            + "FROM (Paziente P\n"
            + "INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n"
            + "INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita)\n"
            + "INNER JOIN Prescrizione Pr ON (V.idVisita = PR.visita_idVisita)\n"
            + "INNER JOIN Risultato R ON (Pr.Risultato_idRisultato = R.idRisultato)\n"
            + "INNER JOIN Esame Es ON (R.esame_idEsame = Es.idEsame))\n"
            + "WHERE idPaziente = ?";
    private static final String GETDRUGBYIDPAZIENTE = "SELECT V.visitdate, Pr.erogationdate, Pr.ticketpagato, F.name, Pr.idPrescrizione\n"
            + "FROM Paziente P\n"
            + "INNER JOIN Eroga E ON (P.idpaziente = E.paziente_idpaziente)\n"
            + "INNER JOIN Visita V ON (E.visita_idvisita = V.idvisita)\n"
            + "INNER JOIN Prescrizione Pr ON (V.idvisita = Pr.visita_idvisita)\n"
            + "INNER JOIN Farmaco F ON (F.idfarmaco = Pr.farmaco_idfarmaco)\n"
            + "WHERE P.idpaziente = ?";
    private static final String GETRECALLBYIDPAZIENTE = "SELECT Pr.erogationdate, R.motivation, E.name\n"
            + "FROM Paziente P\n"
            + "INNER JOIN PrescrizioneRichiamo Pr ON (P.idpaziente = Pr.paziente_idpaziente)\n"
            + "INNER JOIN Richiamo R ON (R.idrichiamo = Pr.richiamo_idrichiamo)\n"
            + "INNER JOIN Esame E ON (Pr.esame_idesame = E.idEsame)\n"
            + "WHERE P.idPaziente = ?";
    private static final String GETVISITBYPAZIENTE = "SELECT V.visitdate, V.ticket, V.ticketpagato\n"
            + "FROM (Paziente P\n"
            + "INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n"
            + "INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita))\n"
            + "WHERE P.idpaziente = ?";
    private static final String CHANGEPASSWORD = "UPDATE Paziente SET password = ? WHERE idPaziente = ?;";
    private static final String CHANGEEMAIL = "UPDATE Paziente SET email = ? WHERE idPaziente = ?;";
    private static final String CHANGEMEDICO = "UPDATE Paziente SET medico_idMedico = ? WHERE idPaziente = ?;";
    private static final String GETPAZIENTIIDSELECTEDBYRICHIAMO = "SELECT * FROM paziente WHERE sex = ? AND birthdate > ? AND birthdate < ?";
    private static final String INSERTINPRESCRIZIONERICHIAMO = "INSERT INTO prescrizionerichiamo(richiamo_idrichiamo, paziente_idpaziente, esame_idesame, erogationdate) VALUES (?,?,?,?);";
    private static final String CHANGEPHOTOPATH = "UPDATE Paziente SET photopath = ? WHERE idPaziente = ?;";
    private static final String GETALLPAIDVISITBYIDPAZIENTE = "SELECT V.visitdate, V.ticket\n"
            + "FROM (Paziente P\n"
            + "INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n"
            + "INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita))\n"
            + "WHERE P.idpaziente = ? AND V.ticketPagato = 1";
    private static final String GETALLPAIDRECIPE = "SELECT Pr.erogationDate, F.name, F.price\n"
            + "FROM (Paziente P\n"
            + "INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n"
            + "INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita)\n"
            + "INNER JOIN Prescrizione Pr ON (Pr.Visita_idVisita = V.idVisita)\n"
            + "INNER JOIN FARMACO F ON (Pr.Farmaco_idFarmaco = F.idFarmaco))\n"
            + "WHERE P.idpaziente = ? AND Pr.ticketPagato = 1";
    private static final String GETALLPAIDEXAMES = "SELECT Pr.erogationdate, Es.name, Es.costo\n"
            + "FROM (Paziente P\n"
            + "INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n"
            + "INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita)\n"
            + "INNER JOIN Prescrizione Pr ON (V.idVisita = PR.visita_idVisita)\n"
            + "INNER JOIN Risultato R ON (Pr.Risultato_idRisultato = R.idRisultato)\n"
            + "INNER JOIN Esame Es ON (R.esame_idEsame = Es.idEsame))\n"
            + "WHERE idPaziente = ? AND Pr.ticketPagato = 1";

    public JDBCPazienteDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Paziente> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paziente getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Paziente getByIdAndPassword(String id, String password) throws DAOException {
        if ((id == null) || (password == null)) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETPAZIENTE)) {
            stm.setString(1, id);
            stm.setString(2, password);
            try ( ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Paziente paziente = new Paziente();
                    paziente.setIdPaziente(rs.getString("idpaziente"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setPassword(rs.getString("password"));
                    paziente.setName(rs.getString("name"));
                    paziente.setSurname(rs.getString("surname"));
                    paziente.setPhotoPath(rs.getString("photopath"));
                    paziente.setProvincia(rs.getString("provincia"));
                    paziente.setBirthPlace(rs.getString("birthplace"));
                    paziente.setBirthDate(rs.getString("birthdate"));
                    paziente.setSex(rs.getBoolean("sex"));
                    paziente.setMedico(rs.getString("medico_idmedico"));
                    paziente.setSsn(rs.getString("ssn"));

                    return paziente;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public Paziente getById(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETPAZIENTEBYID)) {
            stm.setString(1, id);
            try ( ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    Paziente paziente = new Paziente();
                    paziente.setIdPaziente(rs.getString("idpaziente"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setPassword(rs.getString("password"));
                    paziente.setName(rs.getString("name"));
                    paziente.setSurname(rs.getString("surname"));
                    paziente.setPhotoPath(rs.getString("photopath"));
                    paziente.setProvincia(rs.getString("provincia"));
                    paziente.setBirthPlace(rs.getString("birthplace"));
                    paziente.setBirthDate(rs.getString("birthdate"));
                    paziente.setSex(rs.getBoolean("sex"));
                    paziente.setMedico(rs.getString("medico_idmedico"));
                    paziente.setSsn(rs.getString("ssn"));

                    return paziente;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public List<EsamiSostenutiPaziente> getAllExamByIdPaziente(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("Something went wrong");
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETESAMIBYIDPAZIENTE)) {
            stm.setString(1, id);
            try ( ResultSet rs = stm.executeQuery()) {
                List<EsamiSostenutiPaziente> esamiSostenuti = new ArrayList<>();

                while (rs.next()) {
                    EsamiSostenutiPaziente result = new EsamiSostenutiPaziente();
                    result.setDataVisita(rs.getString("visitdate"));
                    result.setDataEsecuzione(rs.getString("erogationDate"));
                    result.setNomeEsame(rs.getString("name"));
                    result.setPagato(rs.getBoolean("ticketpagato"));
                    result.setDataRisultato(rs.getString("resultdate"));
                    result.setIdRisultato("" + rs.getInt("idRisultato"));
                    esamiSostenuti.add(result);
                }
                return esamiSostenuti;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Error inside method getAllExamByIdPaziente", ex);
        }
    }

    @Override
    public List<RicettePrescrittePaziente> getAllDrugByIdPaziente(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("Email and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETDRUGBYIDPAZIENTE)) {
            stm.setString(1, id);
            try ( ResultSet rs = stm.executeQuery()) {
                List<RicettePrescrittePaziente> ricettePrescritte = new ArrayList<>();

                while (rs.next()) {
                    RicettePrescrittePaziente result = new RicettePrescrittePaziente();
                    result.setIdPrescrizione(rs.getString("idPrescrizione"));
                    result.setDataVisita(rs.getString("visitdate"));
                    result.setDataPrescrizione(rs.getString("erogationDate"));
                    result.setTicketPagato(rs.getBoolean("ticketpagato"));
                    result.setNomeFarmaco(rs.getString("name"));
                    ricettePrescritte.add(result);
                }
                return ricettePrescritte;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Error inside method getAllDrugByIdPaziente", ex);
        }
    }

    @Override
    public List<RichiamiPrescrittiPaziente> getAllRecallByIdPaziente(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("Something went wrong");
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETRECALLBYIDPAZIENTE)) {
            stm.setString(1, id);
            try ( ResultSet rs = stm.executeQuery()) {
                List<RichiamiPrescrittiPaziente> richiamiPrescritti = new ArrayList<>();

                while (rs.next()) {
                    RichiamiPrescrittiPaziente result = new RichiamiPrescrittiPaziente();
                    result.setDataPrescrizione(rs.getString("erogationdate"));
                    result.setMotivazione(rs.getString("motivation"));
                    result.setNomeEsame(rs.getString("name"));
                    richiamiPrescritti.add(result);
                }
                return richiamiPrescritti;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new DAOException("Error inside method RichiamoPrescrittoPaziente", ex);
        }
    }

    @Override
    public List<Visita> getAllVisitByIdPaziente(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("Something went wrong");
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETVISITBYPAZIENTE)) {
            stm.setString(1, id);
            try ( ResultSet rs = stm.executeQuery()) {
                List<Visita> visitePaziente = new ArrayList<>();

                while (rs.next()) {
                    Visita result = new Visita();
                    result.setDataVisita(rs.getString("visitDate"));
                    result.setCostoVisita(rs.getInt("ticket"));
                    result.setTicketPagato(rs.getBoolean("ticketpagato"));
                    visitePaziente.add(result);
                }
                return visitePaziente;
            }
        } catch (SQLException ex) {
            throw new DAOException("Error inside method RichiamoPrescrittoPaziente", ex);
        }
    }

    @Override
    public List<Paziente> getPazientiForMedico(String idMedico) throws DAOException {
        if (idMedico == null) {
            throw new DAOException("Something went wrong");
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETPAZIENTEFORMEDICO)) {
            stm.setString(1, idMedico);
            try ( ResultSet rs = stm.executeQuery()) {
                List<Paziente> pazienti = new ArrayList<>();

                while (rs.next()) {
                    Paziente paziente = new Paziente();
                    paziente.setSsn(rs.getString("ssn"));
                    paziente.setBirthDate(rs.getString("birthdate"));
                    paziente.setIdPaziente(rs.getString("idpaziente"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setPassword(rs.getString("password"));
                    paziente.setName(rs.getString("name"));
                    paziente.setSurname(rs.getString("surname"));
                    paziente.setPhotoPath(rs.getString("photopath"));
                    pazienti.add(paziente);
                }
                return pazienti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Error inside method getPazientiForMedico", ex);
        }
    }

    @Override
    public void changePassword(String password, String idPaziente) throws DAOException {
        if (password == null || idPaziente == null) {
            throw new DAOException("Something went wrong");
        }
        try ( PreparedStatement stm = CON.prepareStatement(CHANGEPASSWORD)) {
            stm.setString(1, password);
            stm.setString(2, idPaziente);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error inside method changePassword", ex);
        }
    }

    @Override
    public void changeEmail(String email, String idPaziente) throws DAOException {
        if (email == null || idPaziente == null) {
            throw new DAOException("Something went wrong");
        }
        try ( PreparedStatement stm = CON.prepareStatement(CHANGEEMAIL)) {
            stm.setString(1, email);
            stm.setString(2, idPaziente);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error inside method changeEmail", ex);
        }
    }

    public void changeMedico(String medico, String idPaziente) throws DAOException {
        if (medico == null || idPaziente == null) {
            throw new DAOException("Something went wrong");
        }
        try ( PreparedStatement stm = CON.prepareStatement(CHANGEMEDICO)) {
            stm.setString(1, medico);
            stm.setString(2, idPaziente);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error inside method changeMedico", ex);
        }
    }

    @Override
    public List<Paziente> getPazientiIdSelected(int sex, String dateStart, String dateEnd) throws DAOException {
        if ((dateStart == null) || (dateEnd == null)) {
            throw new DAOException("Something went wrong");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = new Date();
        Date parseDate_ = new Date();
        try {
            parseDate = dateFormat.parse(dateStart);
            parseDate_ = dateFormat.parse(dateEnd);
        } catch (ParseException ex) {
            Logger.getLogger(JDBCSSPDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timestamp timestampStart = new java.sql.Timestamp(parseDate.getTime());
        Timestamp timestampEnd = new java.sql.Timestamp(parseDate_.getTime());

        try ( PreparedStatement stm = CON.prepareStatement(GETPAZIENTIIDSELECTEDBYRICHIAMO)) {
            stm.setInt(1, sex);
            stm.setTimestamp(2, timestampStart);
            stm.setTimestamp(3, timestampEnd);

            try ( ResultSet rs = stm.executeQuery()) {
                List<Paziente> pazienti = new ArrayList<>();

                while (rs.next()) {
                    Paziente paziente = new Paziente();
                    paziente.setSsn(rs.getString("ssn"));
                    paziente.setBirthDate(rs.getString("birthdate"));
                    paziente.setIdPaziente(rs.getString("idpaziente"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setPassword(rs.getString("password"));
                    paziente.setName(rs.getString("name"));
                    paziente.setSurname(rs.getString("surname"));
                    paziente.setPhotoPath(rs.getString("photopath"));
                    pazienti.add(paziente);
                    //idPazienti.add(rs.getString("idpaziente"));
                }
                return pazienti;
            }
        } catch (SQLException ex) {
            throw new DAOException("Error inside method getPazientiIdSelected", ex);
        }
    }

    //richiamo_idrichiamo, paziente_idpaziente, esame_idesame, erogationdate
    @Override
    public void insertInPrescrizioneRichiamo(String idPaziente, int idRichiamo, int idEsame) throws DAOException {

        Timestamp erogationDate = new Timestamp(System.currentTimeMillis());

        try ( PreparedStatement stm = CON.prepareStatement(INSERTINPRESCRIZIONERICHIAMO)) {

            stm.setInt(1, idRichiamo);
            stm.setString(2, idPaziente);
            stm.setInt(3, idEsame);
            stm.setTimestamp(4, erogationDate);
            
            System.out.println("ID Richiamo" + idRichiamo);

            System.out.println("ID PAZIENTE" + idPaziente);
            stm.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("Impossible to insert visita", ex);
        }
    }

    @Override
    public void changePhotoPath(String name, String idPaziente) throws DAOException {
        if (name == null || idPaziente == null) {
            throw new DAOException("Something went wrong");
        }
        try ( PreparedStatement stm = CON.prepareStatement(CHANGEPHOTOPATH)) {
            stm.setString(1, name);
            stm.setString(2, idPaziente);
            stm.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error inside method changePhotoPath", ex);
        }
    }

    @Override
    public List<TicketPagati> getAllTicketPagati(String idPaziente) throws DAOException {
        if (idPaziente == null) {
            throw new DAOException("Something went wrong");
        }
        List<TicketPagati> ticketPagati = new ArrayList<>();
        try ( PreparedStatement stm = CON.prepareStatement(GETALLPAIDVISITBYIDPAZIENTE)) {
            stm.setString(1, idPaziente);
            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    TicketPagati result = new TicketPagati();
                    result.setMotivazione("Visita");
                    result.setCosto(rs.getInt("ticket"));
                    result.setDataPagamento(rs.getString("visitDate"));
                    ticketPagati.add(result);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error inside method getAllTicketPagati", ex);
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETALLPAIDRECIPE)) {
            stm.setString(1, idPaziente);
            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    TicketPagati result = new TicketPagati();
                    result.setMotivazione(rs.getString("name"));
                    result.setCosto(rs.getInt("price"));
                    result.setDataPagamento(rs.getString("erogationDate"));
                    ticketPagati.add(result);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error inside method getAllTicketPagati", ex);
        }

        try ( PreparedStatement stm = CON.prepareStatement(GETALLPAIDEXAMES)) {
            stm.setString(1, idPaziente);
            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    TicketPagati result = new TicketPagati();
                    result.setMotivazione(rs.getString("name"));
                    result.setCosto(rs.getInt("costo"));
                    result.setDataPagamento(rs.getString("erogationDate"));
                    ticketPagati.add(result);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error inside method getAllTicketPagati", ex);
        }

        return ticketPagati;
    }
}
