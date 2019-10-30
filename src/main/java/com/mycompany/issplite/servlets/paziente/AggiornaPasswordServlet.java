/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets.paziente;

import com.mycompany.issplite.persistence.dao.HashDAO;
import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Hash;
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
public class AggiornaPasswordServlet extends HttpServlet {

    private PazienteDAO pazienteDao;
    private HashDAO hashDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);
            hashDao = daoFactory.getDAO(HashDAO.class);

        } catch (DAOFactoryException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        allOkay(request, response);

        response.sendRedirect("/pazienti/areaPersonale.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        allOkay(request, response);
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Paziente paziente = (Paziente) session.getAttribute("paziente");

        String newPassword = "";
        String oldPassword = request.getParameter("oldPassword");
        newPassword = request.getParameter("newPassword");

        if (comparePassword(paziente, oldPassword)) {
            newPassword = getSecurePassword(newPassword, paziente);
            try {
                pazienteDao.changePassword(newPassword, paziente.getIdPaziente());
                request.getSession().setAttribute("status", "found");
            } catch (DAOException ex) {
                Logger.getLogger(AggiornaPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.getSession().setAttribute("status", "not_found");
        }

        response.sendRedirect("/pazienti/areePersonali.html");
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

    private boolean comparePassword(Paziente paziente, String oldPassword) {
        try {
            Hash hash = hashDao.getById(paziente.getIdPaziente());

            boolean b = false;
            if (paziente != null) {
                b = HashGenerator.verifyUserPassword(oldPassword, paziente.getPassword(), hash.getSalt());

            }
            System.out.println("RISULTATO FUNZIONE: " + b);

            return b;
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private String getSecurePassword(String password, Paziente paziente) {
        try {
            String newSalt = HashGenerator.getSalt(30);

            hashDao.updateSalt(paziente.getIdPaziente(), newSalt);

            String securePassword = HashGenerator.generateSecurePassword(password, newSalt);

            return securePassword;
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
