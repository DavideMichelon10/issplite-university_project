package com.mycompany.issplite.persistence.dao.jdbc;


import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.EsamiSostenutiPaziente;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.RicettePrescrittePaziente;
import com.mycompany.issplite.persistence.entities.RichiamiPrescrittiPaziente;
import com.mycompany.issplite.persistence.entities.Visita;
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
public class JDBCPazienteDAO extends JDBCDAO<Paziente, String> implements PazienteDAO {

    private static final String GETPAZIENTE = "SELECT * FROM paziente WHERE idpaziente = ? AND password = ?";
    private static final String GETPAZIENTEBYID = "SELECT * FROM paziente WHERE idpaziente = ? ";
    private static final String GETPAZIENTEFORMEDICO = "SELECT * FROM paziente WHERE medico_idmedico = ?;";
    private static final String GETESAMIBYIDPAZIENTE = "SELECT V.visitdate, Pr.erogationdate, Es.name, Pr.ticketPagato, R.resultdate\n" +
                                                        "FROM (Paziente P\n" +
                                                        "INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n" +
                                                        "INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita)\n" +
                                                        "INNER JOIN Prescrizione Pr ON (V.idVisita = PR.visita_idVisita)\n" +
                                                        "INNER JOIN Risultato R ON (Pr.Risultato_idRisultato = R.idRisultato)\n" +
                                                        "INNER JOIN Esame Es ON (R.esame_idEsame = Es.idEsame))\n" +
                                                        "WHERE idPaziente = ?";
    private static final String GETDRUGBYIDPAZIENTE = "SELECT V.visitdate, Pr.erogationdate, Pr.ticketpagato, F.name\n" +
                                                        "FROM Paziente P\n" +
                                                        "INNER JOIN Eroga E ON (P.idpaziente = E.paziente_idpaziente)\n" +
                                                        "INNER JOIN Visita V ON (E.visita_idvisita = V.idvisita)\n" +
                                                        "INNER JOIN Prescrizione Pr ON (V.idvisita = Pr.visita_idvisita)\n" +
                                                        "INNER JOIN Farmaco F ON (F.idfarmaco = Pr.farmaco_idfarmaco)\n" +
                                                        "WHERE P.idpaziente = ?";
    private static final String GETRECALLBYIDPAZIENTE = "SELECT Pr.erogationdate, R.motivation, E.name\n" +
                                                        "FROM Paziente P\n" +
                                                        "INNER JOIN PrescrizioneRichiamo Pr ON (P.idpaziente = Pr.paziente_idpaziente)\n" +
                                                        "INNER JOIN Richiamo R ON (R.idrichiamo = Pr.richiamo_idrichiamo)\n" +
                                                        "INNER JOIN Esame E ON (Pr.esame_idesame = E.idEsame)\n" +
                                                        "WHERE P.idPaziente = ?";
    private static final String GETVISITBYPAZIENTE = "SELECT V.visitdate, V.ticket, V.ticketpagato\n" +
                                                        "FROM (Paziente P\n" +
                                                        "INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n" +
                                                        "INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita))\n" +
                                                        "WHERE P.idpaziente = ?";


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

        try (PreparedStatement stm = CON.prepareStatement(GETPAZIENTE)) {
            stm.setString(1, id);
            stm.setString(2, password);
            try (ResultSet rs = stm.executeQuery()) {

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

        try (PreparedStatement stm = CON.prepareStatement(GETPAZIENTEBYID)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {

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
                    
                    paziente.setSsn(rs.getString("ssn"));
                    //paziente.setSex(rs.getString("email"));
                    //paziente.setPassword(rs.getString("password"));
                    //paziente.setName(rs.getString("name"));
                    //paziente.setSurname(rs.getString("surname"));
                    //paziente.setPhotoPath(rs.getString("photopath"));

                   
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

        try (PreparedStatement stm = CON.prepareStatement(GETESAMIBYIDPAZIENTE)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                List<EsamiSostenutiPaziente> esamiSostenuti = new ArrayList<>();
                               
                while (rs.next()) {
                    EsamiSostenutiPaziente result = new EsamiSostenutiPaziente();
                    result.setDataVisita(rs.getString("visitdate"));
                    result.setDataEsecuzione(rs.getString("erogationDate"));
                    result.setNomeEsame(rs.getString("name"));
                    result.setPagato(rs.getBoolean("ticketpagato"));
                    result.setDataRisultato(rs.getString("resultdate"));
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

        try (PreparedStatement stm = CON.prepareStatement(GETDRUGBYIDPAZIENTE)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                List<RicettePrescrittePaziente> ricettePrescritte = new ArrayList<>();
                               
                while (rs.next()) {
                    RicettePrescrittePaziente result = new RicettePrescrittePaziente();
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

        try (PreparedStatement stm = CON.prepareStatement(GETRECALLBYIDPAZIENTE)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {
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

        try (PreparedStatement stm = CON.prepareStatement(GETVISITBYPAZIENTE)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {
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
            System.out.println(ex.getMessage());
            throw new DAOException("Error inside method RichiamoPrescrittoPaziente", ex);
        }
    }

    @Override
    public List<Paziente> getPazientiForMedico(String idMedico) throws DAOException {
        if (idMedico == null) {
            throw new DAOException("Something went wrong");
        }

        try (PreparedStatement stm = CON.prepareStatement(GETPAZIENTEFORMEDICO)) {
            stm.setString(1, idMedico);
            try (ResultSet rs = stm.executeQuery()) {
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
}
