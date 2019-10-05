/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import java.io.IOException;
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
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //maschio e femmina, se sesso[0] = 1 c'è maschio altrimenti no
        Boolean maschio = true;
        Boolean femmina = true;
        
        String dateStart = request.getParameter("from");
        String dateEnd = request.getParameter("to");
        String maschi = request.getParameter("maschi");
        String femmine = request.getParameter("femmine");
        String motivation = request.getParameter("motivation");
        
        if("".equals(maschi)){
            maschio = false;
        }
        
        if("".equals(femmine)){
            femmina = false;
        }
        
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }       
        
        
        HttpSession session = ((HttpServletRequest)request).getSession(false);            
        
       
        

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/sspi/richiamo.html"));
        dispatcher.forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
