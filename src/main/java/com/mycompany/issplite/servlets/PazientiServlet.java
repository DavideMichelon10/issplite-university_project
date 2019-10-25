/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.EsamiSostenutiPaziente;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.RicettePrescrittePaziente;
import com.mycompany.issplite.persistence.entities.RichiamiPrescrittiPaziente;
import com.mycompany.issplite.persistence.entities.TicketPagati;
import com.mycompany.issplite.persistence.entities.Visita;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class PazientiServlet extends HttpServlet {

    private PazienteDAO pazienteDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }     
        HttpSession session = ((HttpServletRequest)request).getSession(false);            
        Paziente paziente = (Paziente) session.getAttribute("paziente");
        String idPaziente = paziente.getIdPaziente();
        
        if (idPaziente == null) {
            response.sendRedirect(cp + "login.jsp");
            return;
        }
        
        List<EsamiSostenutiPaziente> esamiSostenuti = new ArrayList<>();
        List<RicettePrescrittePaziente> ricettePrescritte = new ArrayList<>();
        List<RichiamiPrescrittiPaziente> richiamiPrescritti = new ArrayList<>();
        List<Visita> visitePaziente = new ArrayList<>();
        List<TicketPagati> ticketPagati = new ArrayList<>();
        try {
            esamiSostenuti = pazienteDao.getAllExamByIdPaziente(idPaziente);
            ricettePrescritte = pazienteDao.getAllDrugByIdPaziente(idPaziente);
            richiamiPrescritti = pazienteDao.getAllRecallByIdPaziente(idPaziente);
            visitePaziente = pazienteDao.getAllVisitByIdPaziente(idPaziente);
            ticketPagati = pazienteDao.getAllTicketPagati(idPaziente);
        } catch (DAOException ex) {
            response.sendError(500, ex.getMessage());
            return;
        }
        request.setAttribute("ticketPagati", ticketPagati);        
        request.setAttribute("esamiSostenuti", esamiSostenuti);
        request.setAttribute("ricettePrescritte", ricettePrescritte);
        request.setAttribute("richiamiPrescritti", richiamiPrescritti);
        request.setAttribute("visitePrescritte", visitePaziente);
        request.setAttribute("datiPaziente", paziente);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/pazienti/paziente.html"));
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}