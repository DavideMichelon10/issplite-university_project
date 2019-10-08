/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.EsameDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.SSPDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Esame;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.SSP;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Davide
 */
public class CreaRichiamoServlet extends CreaVisitaServlet {

    private PazienteDAO pazienteDao;
    private EsameDAO esameDao;
    private SSPDAO sspDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);
            esameDao = daoFactory.getDAO(EsameDAO.class);
            sspDao = daoFactory.getDAO(SSPDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Boolean success = false;
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        SSP ssp = (SSP) session.getAttribute("ssp");
        String idSsp = ssp.getIdSSP();

        //maschio 1, femmina 0
        Boolean maschio = false;
        Boolean femmina = false;

        String dateStart = request.getParameter("from");
        String dateEnd = request.getParameter("to");
        String maschi = request.getParameter("maschi");
        String femmine = request.getParameter("femmine");
        String motivation = request.getParameter("motivation");

        List<Esame> esami = new ArrayList<>();
        try {
            esami = esameDao.getAll();
        } catch (DAOException ex) {
            Logger.getLogger(CreaRichiamoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String esame = request.getParameter("esame");
        if (esame != null) {
            String[] arrOfStr = esame.split(" - ");
            int idEsame = Integer.parseInt(arrOfStr[0]);

            if (maschi != null) {
                maschio = true;
            }

            if (femmine != null) {
                femmina = true;
            }

            String cp = getServletContext().getContextPath();
            if (!cp.endsWith("/")) {
                cp += "/";
            }

            int idMaschioRichiamo = -2;
            int idFemminaRichiamo = -2;
            //INSERT INTO Richiamo
            try {
                if (maschio) {

                    idMaschioRichiamo = sspDao.insertRichiamo(motivation, 1, dateStart, dateEnd, idSsp);
                }

                if (femmina) {
                    idFemminaRichiamo = sspDao.insertRichiamo(motivation, 0, dateStart, dateEnd, idSsp);

                }
            } catch (DAOException ex) {
                Logger.getLogger(CreaRichiamoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            //GET List<Paziente> a cui prescrivere l'esame
            List<Paziente> pazientiMaschi = new ArrayList<>();
            List<Paziente> pazientiFemmine = new ArrayList<>();

            Esame esamePrescrivibile = null;
            try {
                //GET ESAME BYID
                esamePrescrivibile = esameDao.getById(idEsame);
            } catch (DAOException ex) {
                Logger.getLogger(CreaRichiamoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (maschio) {
                try {
                    pazientiMaschi.addAll(pazienteDao.getPazientiIdSelected(1, dateStart, dateEnd));
                    for (Paziente s : pazientiMaschi) {
                        pazienteDao.insertInPrescrizioneRichiamo(s.getIdPaziente(), idMaschioRichiamo, idEsame);
                        sendEmail(esamePrescrivibile, s, "Prescrizione esame da richiamo: ");
                    }
                } catch (DAOException ex) {
                    Logger.getLogger(CreaRichiamoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (femmina) {
                try {
                    pazientiFemmine.addAll(pazienteDao.getPazientiIdSelected(0, dateStart, dateEnd));
                    for (Paziente s : pazientiFemmine) {
                        pazienteDao.insertInPrescrizioneRichiamo(s.getIdPaziente(), idFemminaRichiamo, idEsame);
                        sendEmail(esamePrescrivibile, s, "Prescrizione esame da richiamo: ");

                    }
                } catch (DAOException ex) {
                    Logger.getLogger(CreaRichiamoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
            success = true;
        }

        request.setAttribute("success", success);
        request.setAttribute("esami", esami);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/sspi/richiamo.html"));
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
