/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets.paziente;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.servlets.LoginServlet;
import com.mycompany.issplite.utilities.HashGenerator;
import java.io.IOException;
import java.util.ArrayList;
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
        allOkay(request, response);

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Paziente paziente = (Paziente) session.getAttribute("paziente");
        Medico medico = new Medico();
        ArrayList<Medico> mediciDisponibili = new ArrayList();
        try {            
            medico = medicoDao.getById(paziente.getMedico());           
            mediciDisponibili = (ArrayList<Medico>) medicoDao.getByProvincia(paziente.getProvincia());
        } catch (DAOException ex) {
            System.out.println(ex.getMessage());
        }
        request.setAttribute("medico", medico);
        request.setAttribute("mediciDisponibili", mediciDisponibili);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/pazienti/areePersonali.html"));
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        allOkay(request, response);
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Paziente paziente = (Paziente) session.getAttribute("paziente");
        Medico medico = new Medico();
        String newMedico = "";
        String email = "";
        newMedico = request.getParameterValues("newMedico")[0];
        email = request.getParameterValues("email")[0];
        
        if (checkEmail(email, paziente.getEmail())) {
            try {
                pazienteDao.changeEmail(email, paziente.getIdPaziente());
                paziente = pazienteDao.getById(paziente.getIdPaziente());
            } catch (DAOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (checkMedico(newMedico, paziente.getProvincia())) {
            try {
                pazienteDao.changeMedico(newMedico, paziente.getIdPaziente());
                paziente = pazienteDao.getById(paziente.getIdPaziente());
                medico = medicoDao.getById(paziente.getMedico());
            } catch (DAOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        session.setAttribute("paziente", paziente);
        request.setAttribute("medico", medico);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/pazienti/areePersonali.html"));
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private boolean checkEmail(String newEmail, String oldEmail) {
        return (newEmail != "" && newEmail != null) && newEmail != oldEmail;
    }

    private boolean checkMedico(String idMedico, String provincia) {
        try {
            Medico medico = medicoDao.getByIdAndProvincia(idMedico, provincia);;
            return (medico != null);
        } catch (DAOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    private void allOkay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Paziente paziente = (Paziente) session.getAttribute("paziente");      
        if (paziente == null) {
            response.sendRedirect(cp + "login.jsp");
        } else if (paziente.getIdPaziente() == null) {
            response.sendRedirect(cp + "login.jsp");
        }
    }

}
