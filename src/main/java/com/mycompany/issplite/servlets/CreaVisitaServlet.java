package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.EsameDAO;
import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Esame;
import com.mycompany.issplite.persistence.entities.Medico;
import java.util.List;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CreaVisitaServlet extends HttpServlet {

private EsameDAO esameDao;
private MedicoDAO medicoDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            
            esameDao = daoFactory.getDAO(EsameDAO.class);
            medicoDao = daoFactory.getDAO(MedicoDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory", ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }

        List<Esame> esami = new ArrayList<>();        
   
        try {
            esami = esameDao.getAll();

        } catch (DAOException ex) {
            response.sendError(500, ex.getMessage());
            return;
        }
        
        request.setAttribute("esami", esami);
        
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/medici/visite.html"));
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //contiene id di esami checked
        List<String> parameterNamesList = Collections.list(request.getParameterNames());
        List<Integer> idEsamiPrescritti = new ArrayList<>();
        String idPaziente = request.getParameter("idPaziente");

        boolean isPagato = false;
        
        for(String s : parameterNamesList){
            
            if(s.equals("pagato")){
                isPagato = Boolean.valueOf(request.getParameter(s));
            }else if(s.equals("idPaziente")){
            }else{
                idEsamiPrescritti.add(Integer.valueOf(s));
            }
        }
        
        
        
        try {
            int idVisita = medicoDao.insertVisita(isPagato);
            medicoDao.insertEroga(getIdMedico(request), idPaziente, idVisita);
            for(int idEsame : idEsamiPrescritti){
                medicoDao.insertPrescrizione(idEsame, idVisita);
            }
            
            
        }catch (DAOException ex) {
            Logger.getLogger(CreaVisitaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("IN CERA INFONDo");
        response.sendRedirect("/medici/prescrizione.html?idPaziente="+idPaziente);
    }
    
    private String getIdMedico(HttpServletRequest request){
        HttpSession session = ((HttpServletRequest)request).getSession(false);            
        Medico medico = (Medico) session.getAttribute("medico");
        return medico.getIdMedico();
    }

}
