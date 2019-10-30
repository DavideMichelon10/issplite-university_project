/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets.medico;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.EsamePrescritto;
import com.mycompany.issplite.persistence.entities.FarmacoPrescritto;
import com.mycompany.issplite.persistence.entities.Paziente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Davide
 */
public class MediciPrescrizioniServlet extends HttpServlet {

    private MedicoDAO medicoDao;
    private PazienteDAO pazienteDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            medicoDao = daoFactory.getDAO(MedicoDAO.class);
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
        Paziente paziente = new Paziente();
        List<EsamePrescritto> esamiPrescritti = new ArrayList<>();
        List<FarmacoPrescritto> farmaciPrescritti = new ArrayList<>();
        
        
        String idPaziente = request.getParameter("idPaziente");
        
        if (idPaziente == null) {
            response.sendRedirect(cp + "login.jsp");
            return;
        }
        try {
            paziente = pazienteDao.getById(idPaziente);
            farmaciPrescritti = medicoDao.getFarmaciPrescritti(idPaziente);
            esamiPrescritti = medicoDao.getEsamiPrescritti(idPaziente);
            
        } catch (DAOException ex) {
            response.sendError(500, ex.getMessage());
            return;
        }
        request.setAttribute("paziente", paziente);
        request.setAttribute("farmaciPrescritti", farmaciPrescritti);
        request.setAttribute("esamiPrescritti", esamiPrescritti);
        
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/medici/prescrizioni.html"));
        dispatcher.forward(request, response);
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
