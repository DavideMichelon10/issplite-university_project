package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.PazienteUltimiEsamiFarmaci;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
        
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }       
        
        
        HttpSession session = ((HttpServletRequest)request).getSession(false);            
        //Medico medico = (Medico) session.getAttribute("medico");
        //String idMedico = medico.getIdMedico();
        String idMedico ="M123";
        if (idMedico == null) {
            response.sendRedirect(cp + "login.jsp");
            return;
        }
        List<PazienteUltimiEsamiFarmaci> pazienti = new ArrayList<>();
        try {
            pazienti = medicoDao.getLastEsameFarmacoDate(idMedico);
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
