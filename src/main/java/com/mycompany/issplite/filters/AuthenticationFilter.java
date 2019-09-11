/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.filters;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.issplite.servlets.LoginServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/** Con questo filtro controllo per prima cosa che non sia presente una sessione. Se non è presente controllo
 *  che non vi siano cookie, se trovo cookie creo la sessione e inoltro la richiesta, se non vi sono ne cookie ne sessioni reinoltro al login.
 * 
 */
public class AuthenticationFilter implements Filter {
    
    private static final boolean DEBUG = true;

    private final FilterConfig filterConfig = null;
    
   
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpSession session = ((HttpServletRequest)request).getSession(false);            

        if (DEBUG) {
            log("AuthenticationFilter:doFilter()");
        }
        
        if (request instanceof HttpServletRequest) {
            
            String contextPath = request.getServletContext().getContextPath();
            if (!contextPath.endsWith("/")) {
                contextPath += "/";
            }            
            

            if(session == null){                
                String userId = getUserId(request);
                if(userId != null){
                    createSession(request, userId);
                    session = ((HttpServletRequest)request).getSession(false);
                }
            }
            
            if(session != null){
                chain.doFilter(request, response);  
            }else{
                ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(contextPath + "login.jsp"));
            }  
        }
    }
    
    private String getUserId(ServletRequest request){
        String jsessionid;
        Cookie[] cookies = ((HttpServletRequest)request).getCookies();
        if(cookies != null){             
            for (Cookie cookie : cookies) {                     
                if(cookie.getName().equals("ISSPLiteId")){
                    jsessionid = cookie.getValue();
                    return LoginServlet.retrieveId(jsessionid);
                }
            }    
        }        
        return null;
    }
    
    private void createSession(ServletRequest request, String userId){
        switch (userId.charAt(0)){
            case 'P':
                ((HttpServletRequest)request).getSession().setAttribute("paziente", userId);
                break;
            case 'M':
                ((HttpServletRequest)request).getSession().setAttribute("medico", userId);
                break;
            case 'S':
                ((HttpServletRequest)request).getSession().setAttribute("ssp", userId);
                break;
            default:
                break;
        }        
    }


   
    @Override
    public void destroy() { 
    }

    @Override
    public void init(FilterConfig filterConfig) {        
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (IOException | RuntimeException ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        if (filterConfig != null) {
            filterConfig.getServletContext().log(msg);
        } else {
            Logger.getLogger(msg);
        }
    }
}