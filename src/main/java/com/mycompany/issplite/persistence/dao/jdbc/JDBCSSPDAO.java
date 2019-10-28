package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.SSPDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.RicetteErogatePerGiorno;
import com.mycompany.issplite.persistence.entities.RisultatoNonEseguito;
import com.mycompany.issplite.persistence.entities.SSP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class JDBCSSPDAO extends JDBCDAO<SSP, String> implements SSPDAO {

    private static final String GETSSP = "SELECT * FROM SSP WHERE idssp = ? and password = ?;";
    private static final String GETSSPBYID = "SELECT * FROM SSP WHERE idssp = ?;";
    private static final String GETRICETTEPERDAY = "SELECT M.name as \"name_medico\", M.surname, F.name, PA.ssn, V.ticket, P.ErogationDate FROM Medico M\n"
            + "	JOIN Eroga E ON E.Medico_idMedico = M.idMedico\n"
            + "	JOIN Paziente PA ON PA.idPaziente = E.Paziente_IdPaziente\n"
            + "	JOIN Visita V ON V.idVisita = E.Visita_idVisita\n"
            + "	JOIN Prescrizione P ON P.Visita_idVisita = V.idVisita\n"
            + "	JOIN Farmaco F ON F.idFarmaco = P.Farmaco_idFarmaco\n"
            + "	WHERE M.Provincia = ? AND P.erogationdate > ? AND P.erogationdate < ?;";

    private static final String INSERTRICHIAMO = "INSERT INTO Richiamo(motivation, targetsex, targetdatestart, targetdateend, ssp_idssp) VALUES (?,?,?,?,?);";
    private static final String GETESAMIWITHOUTRESULT = "SELECT R.idRisultato, Pr.erogationdate, Es.name, P.name as pazientename, P.surname\n"
            + "            FROM (Paziente P\n"
            + "            INNER JOIN Eroga E ON (P.idPaziente = E.Paziente_idPaziente)\n"
            + "            INNER JOIN Visita V ON (E.Visita_idVisita = V.idVisita)\n"
            + "            INNER JOIN Prescrizione Pr ON (V.idVisita = PR.visita_idVisita)\n"
            + "            INNER JOIN Risultato R ON (Pr.Risultato_idRisultato = R.idRisultato)\n"
            + "            INNER JOIN Esame Es ON (R.esame_idEsame = Es.idEsame))\n"
            + "			WHERE P.provincia = ? AND R.resultDate IS NULL;";

    private static final String GETRESULTBYID = "SELECT R.idRisultato, Pr.erogationdate, Es.name,P.ssn, P.name as pazientename, P.surname FROM risultato R\n"
            + "	JOIN esame Es ON Es.idEsame = R.esame_idesame\n"
            + "	JOIN prescrizione Pr ON Pr.risultato_idrisultato = R.idrisultato\n"
            + "	JOIN visita V ON V.idvisita = Pr.visita_idvisita\n"
            + "	JOIN Eroga E ON E.visita_idvisita = V.idvisita\n"
            + "	JOIN Paziente P ON P.idpaziente = E.paziente_idpaziente\n"
            + "	WHERE R.idrisultato = ?;\n"
            + "	";
    private static final String UPDATERESULT = "UPDATE Risultato\n"
            + "	SET description = ?,\n"
            + "	resultdate = ?\n"
            + "	WHERE idrisultato = ?;";

    public JDBCSSPDAO(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SSP getByPrimaryKey(String arg0) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SSP> getAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SSP getByIdAndPassword(String id, String password) throws DAOException {
        if ((id == null) || (password == null)) {
            throw new DAOException("id and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETSSP)) {
            stm.setString(1, id);
            stm.setString(2, password);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    SSP ssp = new SSP();
                    ssp.setIdSSP(rs.getString("idssp"));
                    ssp.setProvincia(rs.getString("provincia"));
                    ssp.setPassword(rs.getString("password"));

                    return ssp;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public SSP getById(String id) throws DAOException {
        if (id == null) {
            throw new DAOException("id and password are mandatory fields", new NullPointerException("email or password are null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(GETSSPBYID)) {
            stm.setString(1, id);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one user with the same email! WHY???");
                    }
                    SSP ssp = new SSP();
                    ssp.setIdSSP(rs.getString("idssp"));
                    ssp.setProvincia(rs.getString("provincia"));
                    ssp.setPassword(rs.getString("password"));

                    return ssp;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of users", ex);
        }
    }

    @Override
    public List<RicetteErogatePerGiorno> getRicettePerDay(String provincia, String date) throws DAOException {
        try {
            if (date == null) {
                throw new DAOException("Date mandatory fields", new NullPointerException("date is null"));
            }

            String dateFine;
            String[] parts = date.split("-");
            int dayAfter = Integer.parseInt(parts[2]);
            dayAfter++;
            dateFine = "" + parts[0] + "-" + parts[1] + "-" + dayAfter;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            Date parsedDate_ = dateFormat.parse(dateFine);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            Timestamp timestamp_ = new java.sql.Timestamp(parsedDate_.getTime());

            List<RicetteErogatePerGiorno> ricette = new ArrayList<>();
            try (PreparedStatement stm = CON.prepareStatement(GETRICETTEPERDAY)) {
                stm.setString(1, provincia);
                stm.setTimestamp(2, timestamp);
                stm.setTimestamp(3, timestamp_);

                try (ResultSet rs = stm.executeQuery()) {

                    while (rs.next()) {

                        RicetteErogatePerGiorno r = new RicetteErogatePerGiorno();

                        r.setDate(rs.getString("erogationdate"));
                        r.setFarmacoName(rs.getString("name"));
                        r.setMedicoName(rs.getString("name_medico"));
                        r.setMedicoSurname(rs.getString("surname"));
                        r.setPazienteSSN(rs.getString("ssn"));
                        r.setTicket(rs.getInt("ticket"));

                        ricette.add(r);
                    }

                    return ricette;
                }
            } catch (SQLException ex) {
                throw new DAOException("Impossible to get the list of ricette", ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(JDBCSSPDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int insertRichiamo(String motivation, int sex, String dateStart, String dateEnd, String idSsp) throws DAOException {
        if ((motivation == null) || (dateEnd == null) || (dateStart == null) || (idSsp == null)) {
            throw new DAOException("Date mandatory fields", new NullPointerException("date is null"));
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

        try (PreparedStatement stm = CON.prepareStatement(INSERTRICHIAMO, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, motivation);
            stm.setInt(2, sex);
            stm.setTimestamp(3, timestampStart);
            stm.setTimestamp(4, timestampEnd);
            stm.setString(5, idSsp);

            stm.executeUpdate();

            ResultSet keys = stm.getGeneratedKeys();
            int lastKey = 1;
            while (keys.next()) {
                lastKey = keys.getInt(1);
            }
            return lastKey;

        } catch (SQLException ex) {
            Logger.getLogger(JDBCSSPDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public List<RisultatoNonEseguito> getEsamiWithoutResults(String provincia) throws DAOException {
        if (provincia == null) {
            throw new DAOException("provincia mandatory fields", new NullPointerException("provincia is null"));
        }
        List<RisultatoNonEseguito> risultati = new ArrayList<>();

        try (PreparedStatement stm = CON.prepareStatement(GETESAMIWITHOUTRESULT)) {
            stm.setString(1, provincia);
            try (ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {

                    RisultatoNonEseguito r = new RisultatoNonEseguito();
                    r.setIdRisultato(rs.getInt("idrisultato"));
                    r.setErogationDateVisit(rs.getString("erogationdate"));
                    r.setExamName(rs.getString("name"));
                    r.setPazienteName(rs.getString("pazientename"));
                    r.setPazienteSurname(rs.getString("surname"));
                    risultati.add(r);
                }

            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the list of exams", ex);
        }
        return risultati;
    }

    @Override
    public RisultatoNonEseguito getRisultatoNonEseguitoById(int idRisultato) throws DAOException {
        try {
            String.valueOf(idRisultato);

        } catch (NullPointerException e) {
            throw new DAOException("idRisultato is mandatory fields", new NullPointerException("idRisultato is null"));

        }

        try (PreparedStatement stm = CON.prepareStatement(GETRESULTBYID)) {
            stm.setInt(1, idRisultato);
            try (ResultSet rs = stm.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    count++;
                    if (count > 1) {
                        throw new DAOException("Unique constraint violated! There are more than one result with the same id! WHY???");
                    }
                    RisultatoNonEseguito r = new RisultatoNonEseguito();
                    r.setIdRisultato(rs.getInt("idrisultato"));
                    r.setErogationDateVisit(rs.getString("erogationdate"));
                    r.setExamName(rs.getString("name"));
                    r.setPazienteName(rs.getString("pazientename"));
                    r.setPazienteSurname(rs.getString("surname"));
                    r.setSsn(rs.getString("ssn"));
                    return r;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossible to get the result", ex);
        }
    }

    @Override
    public void insertDescription(int idRisultato, String description) throws DAOException {
        if (description == null) {
            throw new DAOException("Date mandatory fields", new NullPointerException("date is null"));
        }
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        try (PreparedStatement stm = CON.prepareStatement(UPDATERESULT)) {
            stm.setString(1, description);
            stm.setTimestamp(2,ts);
            stm.setInt(3, idRisultato);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCSSPDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
