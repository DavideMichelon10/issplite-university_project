package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.PazienteUltimiEsamiFarmaci;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MediciServlet extends HttpServlet {

    private MedicoDAO medicoDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            medicoDao = daoFactory.getDAO(MedicoDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("In DO GET MEDICISERVLET");
        
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }

        Set<String> paramNames = request.getParameterMap().keySet();
        
        // iterating over parameter names and get its value
        for (String name : paramNames) {
            String value = request.getParameter(name);
            System.out.println("NAME: "+name+ "  VALUE: "+value);
        }
        
        String idMedico = request.getParameter("idMedico");
        
        if (idMedico == null) {
            System.out.println("ID MEDICO NULL");
            response.sendRedirect(cp + "login.jsp");
            return;
        }
        List<PazienteUltimiEsamiFarmaci> pazienti = new ArrayList<>();
        try {
            pazienti = medicoDao.getLastEsameFarmacoDate(idMedico);
            /*for (PazienteUltimiEsamiFarmaci paz : pazienti) {
                System.out.println("NAMEESAME:  "+paz.getNameEsame());
            }*/
        } catch (DAOException ex) {
            response.sendError(500, ex.getMessage());
            return;
        }
        
        request.setAttribute("pazienti", pazienti);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/medici/medico.html?idMedico="+idMedico));
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
