package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.HashDAO;
import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.SSPDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Hash;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.SSP;
import com.mycompany.issplite.utilities.HashGenerator;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Davide
 */
public class LoginServlet extends HttpServlet {

    private PazienteDAO pazienteDao;
    private MedicoDAO medicoDao;
    private SSPDAO sspDao;
    private HashDAO hashDao;

    private static HashMap<String, Object> authenticatedUsers; // jsession + ID

    public LoginServlet() {
        super();
        authenticatedUsers = new HashMap<>();
    }

    public static Object retrieveId(String isspCookie) {
        return authenticatedUsers.get(isspCookie);
    }

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);
            medicoDao = daoFactory.getDAO(MedicoDAO.class);
            sspDao = daoFactory.getDAO(SSPDAO.class);
            hashDao = daoFactory.getDAO(HashDAO.class);

        } catch (DAOFactoryException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("username");
        String providedPassword = request.getParameter("password");

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        Paziente paziente;
        Medico medico;
        SSP ssp;
        paziente = searchForPaziente(id, providedPassword);
        System.out.println("paziente: " + paziente);
        if (paziente == null) {
            medico = searchForMedico(id, providedPassword);
            if (medico == null) {
                ssp = searchForSSP(id, providedPassword);
                if (ssp == null) {
                    request.getSession().setAttribute("status", "not_found");
                    HttpSession session = request.getSession(false);
                    removeSession(session);
                    response.sendRedirect(response.encodeRedirectURL(contextPath + "login.jsp"));

                } else {
                    addUserToCookie(ssp, request, response);
                    request.getSession().setAttribute("ssp", ssp);
                    response.sendRedirect(response.encodeRedirectURL(contextPath + "sspi/sspi.html"));
                }
            } else {
                addUserToCookie(medico, request, response);
                request.getSession().setAttribute("medico", medico);
                response.sendRedirect("/medici/medici.html");

            }
        } else {

            addUserToCookie(paziente, request, response);
            request.getSession().setAttribute("paziente", paziente);
            response.sendRedirect(response.encodeRedirectURL(contextPath + "pazienti/pazienti.html"));
        }

    }

    private Medico searchForMedico(String id, String providedPassword) {
        try {
            Hash hash = hashDao.getById(id);
            Medico medico = medicoDao.getById(id);
            boolean b = false;
            if(medico != null)
                b = HashGenerator.verifyUserPassword(providedPassword, medico.getPassword(), hash.getSalt());
            return b ? medico : null;
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private SSP searchForSSP(String id, String providedPassword) {
        try {
            Hash hash = hashDao.getById(id);
            SSP ssp = sspDao.getById(id);
            boolean b = false;
            if(ssp != null)
                 b = HashGenerator.verifyUserPassword(providedPassword, ssp.getPassword(), hash.getSalt());
            return b ? ssp : null;
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Paziente searchForPaziente(String id, String providedPassword) {
        try {
            Hash hash = hashDao.getById(id);
            Paziente paziente = pazienteDao.getById(id);
            boolean b = false;
            if(paziente != null)
                b = HashGenerator.verifyUserPassword(providedPassword, paziente.getPassword(), hash.getSalt());
            return b ? paziente : null;
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void addUserToCookie(Object user, HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("rememberMe") != null) {

            String isspCookie = Long.toHexString(Double.doubleToLongBits(Math.random()));

            authenticatedUsers.put(isspCookie, user);
            Cookie cookie = new Cookie("ISSPLiteId", isspCookie);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
        }
    }

    private void removeSession(HttpSession session) {
        if (session != null) {
            Medico medico = (Medico) session.getAttribute("medico");
            Paziente paziente = (Paziente) session.getAttribute("paziente");
            SSP ssp = (SSP) session.getAttribute("ssp");
            if (medico != null) {
                removeSessionForParticularUser(session, medico, "medico");
                if (paziente != null) {
                    removeSessionForParticularUser(session, paziente, "paziente");
                }
                if (ssp != null) {
                    removeSessionForParticularUser(session, ssp, "ssp");
                }
            }
        }
    }

    private void removeSessionForParticularUser(HttpSession session, Object user, String name) {
        if (user != null) {
            session.setAttribute(name, null);
            session.invalidate();
            user = null;
        }
    }
}
