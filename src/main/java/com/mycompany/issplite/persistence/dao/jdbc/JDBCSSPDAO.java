package com.mycompany.issplite.persistence.dao.jdbc;

import com.mycompany.issplite.persistence.dao.SSPDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.jdbc.JDBCDAO;
import com.mycompany.issplite.persistence.entities.RicetteErogatePerGiorno;
import com.mycompany.issplite.persistence.entities.SSP;
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

        try ( PreparedStatement stm = CON.prepareStatement(GETSSP)) {
            stm.setString(1, id);
            stm.setString(2, password);
            try ( ResultSet rs = stm.executeQuery()) {

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

        try ( PreparedStatement stm = CON.prepareStatement(GETSSPBYID)) {
            stm.setString(1, id);
            try ( ResultSet rs = stm.executeQuery()) {

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
            try ( PreparedStatement stm = CON.prepareStatement(GETRICETTEPERDAY)) {
                stm.setString(1, provincia);
                stm.setTimestamp(2, timestamp);
                stm.setTimestamp(3, timestamp_);
                System.out.println("DATE: " + date + "  DATEFINE: " + dateFine);
                try ( ResultSet rs = stm.executeQuery()) {

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
    public void insertRichiamo(String motivation, int sex, String dateStart, String dateEnd, String idSsp) {
        if ((motivation == null) || (dateEnd == null) || (dateStart == null) || (idSsp == null)) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
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

            System.out.println("TIMEST: " + timestampStart + "  TIME END: " + timestampEnd);

            try ( PreparedStatement stm = CON.prepareStatement(GETSSP)) {
                stm.setString(1, motivation);
                stm.setInt(2, sex);
                stm.setTimestamp(3, timestampStart);
                stm.setTimestamp(4, timestampEnd);
                stm.setString(5, idSsp);

                stm.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCSSPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
