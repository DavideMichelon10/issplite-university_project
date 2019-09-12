/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.Paziente;
import com.mycompany.issplite.persistence.entities.SSP;
import java.io.IOException;
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
public class LogoutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        removeSession(session);
        
        Cookie[] cookies = request.getCookies();
        removeCookies(session,response, cookies);
        
        String contextPath = getServletContext().getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }

        if (!response.isCommitted()) {
            response.sendRedirect(response.encodeRedirectURL(contextPath + "login.jsp"));
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

    private void removeCookies(HttpSession session, HttpServletResponse response, Cookie[] cookies) {
        Cookie [] localCookies = cookies;
        if(cookies != null){             
            for (Cookie cookie : localCookies) {                     
                if(cookie.getName().equals("ISSPLiteId")){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        
   
    }
}
