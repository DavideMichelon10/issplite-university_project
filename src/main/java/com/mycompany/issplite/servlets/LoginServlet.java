
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.SSPDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.SSP;
import java.io.IOException;
import java.time.Clock;
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

    private static HashMap<String, String> authenticatedUsers; // jsession + ID

    public LoginServlet() {
        super();       
        authenticatedUsers = new HashMap<>();
    }
    
    public static String retrieveId(String jSessionId) {
        return authenticatedUsers.get(jSessionId);
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

        } catch (DAOFactoryException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("username");
        String password = request.getParameter("password");

        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

            Paziente paziente;
            Medico medico;
            SSP ssp;
            paziente = searchForPaziente(id, password);
            if (paziente == null) {
                medico = searchForMedico(id, password);
                if(medico == null){
                    ssp = searchForSSP(id, password);
                    if(ssp == null){
                        request.getSession().setAttribute("status", "not_found");
                        HttpSession session = request.getSession(false);
                        removeSession(session);
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "login.jsp"));

                    }else{
                        addUserToCookie(ssp.getIdSSP(),request, response);
                        HttpSession session = request.getSession(false);
                        removeSession(session);
                        request.getSession().setAttribute("ssp", ssp);
                        response.sendRedirect(response.encodeRedirectURL(contextPath + "sspi/sspi.html"));
                    }
                }else{
                    addUserToCookie(medico.getIdMedico(),request, response);
                    request.getSession().setAttribute("medico", medico);
                    HttpSession session = request.getSession(false);
                    removeSession(session);
                    response.sendRedirect("/medici/medici.html");

                    //response.sendRedirect(response.encodeRedirectURL(contextPath + "medici/medici.html?idMedico="+medico.getIdMedico()));
                }
            } else {
                
                addUserToCookie(paziente.getIdPaziente(),request, response);
                request.getSession().setAttribute("paziente", paziente);
                response.sendRedirect(response.encodeRedirectURL(contextPath + "pazienti/pazienti.html"));
            }
        
    }

    private Medico searchForMedico(String id, String password) {
        try {
            return medicoDao.getByIdAndPassword(id, password);
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    private SSP searchForSSP(String id, String password) {
        try {
            return sspDao.getByIdAndPassword(id, password);
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private Paziente searchForPaziente(String id, String password) {
        try {
            return pazienteDao.getByIdAndPassword(id, password);
        } catch (DAOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void addUserToCookie(String username,HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("rememberMe") !=  null){
            
            String jSessionId = Long.toHexString(Double.doubleToLongBits(Math.random()));

            authenticatedUsers.put(jSessionId, username);
            Cookie cookie = new Cookie("ISSPLiteId", jSessionId);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
        }
    }
    
    private void removeSession(HttpSession session) {
        if (session != null) {
            try{
                Paziente paziente = (Paziente) session.getAttribute("paziente");
                removeSessionForParticularUser(session, paziente, "paziente");
            }catch(NullPointerException e){
                try{
                    Medico medico = (Medico) session.getAttribute("medico");
                    removeSessionForParticularUser(session, medico, "medico");
                }catch(NullPointerException ex){
                    try{
                        SSP ssp = (SSP) session.getAttribute("ssp");
                        removeSessionForParticularUser(session, ssp, "ssp");
                    }catch(NullPointerException exc){
                    
                    }
                }
            }
            
        }
    }
        private void removeSessionForParticularUser(HttpSession session, Object user, String name){
        if (user != null) {
            session.setAttribute(name, null);
            session.invalidate();
            user = null;
        } 
    }
}
