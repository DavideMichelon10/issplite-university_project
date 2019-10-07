/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.EsameDAO;
import com.mycompany.issplite.persistence.dao.SSPDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Esame;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.SSP;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Davide
 */
public class CreaRichiamoServlet extends HttpServlet {

    private EsameDAO esameDao;
    private SSPDAO sspDao;
    
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            esameDao = daoFactory.getDAO(EsameDAO.class);
            sspDao = daoFactory.getDAO(SSPDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                        
        
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        SSP ssp = (SSP) session.getAttribute("ssp");
        String idSsp = ssp.getIdSSP();

        //maschio 1, femmina 0
        Boolean maschio = true;
        Boolean femmina = true;

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
        if(esame != null){
            String[] arrOfStr = esame.split(" - ");
        int idEsame = Integer.parseInt(arrOfStr[0]);

        if ("".equals(maschi)) {
            maschio = false;
        }

        if ("".equals(femmine)) {
            femmina = false;
        }

        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }

       

        //INSERT INTO Richiamo
        try {
            if(maschio){
                sspDao.insertRichiamo(motivation, 1, dateStart, dateEnd, idSsp);
            }
            
            if(femmina){
                sspDao.insertRichiamo(motivation, 0, dateStart, dateEnd, idSsp);

            }
        } catch (DAOException ex) {
            Logger.getLogger(CreaRichiamoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //GET List<Paziente> a cui prescrivere l'esame
        //Insert into PrescrizioneRichiamo
        //Invia E-mail
        }
        

        request.setAttribute("esami", esami);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/sspi/richiamo.html"));
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
