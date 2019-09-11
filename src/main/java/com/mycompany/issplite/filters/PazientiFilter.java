/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.filters;

import com.mycompany.issplite.persistence.entities.Paziente;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Davide
 */
public class PazientiFilter extends MediciFilter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        if (DEBUG) {
            log("AuthenticationFilter:doFilter()");
        }
        
        if (request instanceof HttpServletRequest) {
            ServletContext servletContext = ((HttpServletRequest) request).getServletContext();
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            
            String contextPath = servletContext.getContextPath();
                if (!contextPath.endsWith("/")) {
                    contextPath += "/";
                }
            try{
                Paziente paziente = (Paziente) session.getAttribute("paziente");
                if (paziente == null) {

                ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "login.jsp"));
            }else{
                chain.doFilter(request, response);
            }
            }catch(NullPointerException e){
                ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "login.jsp"));
            }
            
        }
      
        Throwable problem = null;

        doAfterProcessing(request, response);

        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }
}