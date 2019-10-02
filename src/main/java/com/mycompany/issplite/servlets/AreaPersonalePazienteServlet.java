/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.Paziente;
import java.io.IOException;
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
 * @author Aster
 */
public class AreaPersonalePazienteServlet extends HttpServlet {

    private PazienteDAO pazienteDao;
    private MedicoDAO medicoDao;
    
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);
            medicoDao = daoFactory.getDAO(MedicoDAO.class);

        } catch (DAOFactoryException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Medico medico = new Medico();
        
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }     
        
        HttpSession session = ((HttpServletRequest)request).getSession(false);            
        Paziente paziente = (Paziente) session.getAttribute("paziente");
        
        if (paziente.getIdPaziente() == null) {
            response.sendRedirect(cp + "login.jsp");
            return;
        }
        
        try {
            medico = medicoDao.getById(paziente.getMedico());            
        } catch (DAOException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("Medico: " + medico.getName());
        request.setAttribute("medico", medico);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/pazienti/areaPersonale.html"));
        dispatcher.forward(request, response);

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("SONO ENTRATO NELLA SERVLET s<sfdsd");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
