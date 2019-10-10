/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.SSPDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.RisultatoNonEseguito;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Davide
 */
public class ErogaEsameDettaglioServlet extends HttpServlet {

    private SSPDAO sspDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            sspDao = daoFactory.getDAO(SSPDAO.class);
        } catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for user storage system", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRisultatoStr = request.getParameter("idRisultato");
        if (idRisultatoStr != null) {
            int idRisultato = Integer.parseInt(idRisultatoStr);
            RisultatoNonEseguito risultato = new RisultatoNonEseguito();

            try {
                risultato = sspDao.getRisultatoNonEseguitoById(idRisultato);
            } catch (DAOException ex) {
                Logger.getLogger(ErogaEsameServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("risultato", risultato);

        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/sspi/erogaesamedettaglio.html"));
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRisultatoStr = request.getParameter("idRisultato");
        String description = request.getParameter("description");

        if (idRisultatoStr != null) {

            try {
                int idRisultato = Integer.parseInt(idRisultatoStr);
                RisultatoNonEseguito risultato = new RisultatoNonEseguito();
                sspDao.insertDescription(idRisultato, description);
                request.getSession().setAttribute("status", "200");

            } catch (DAOException e){
                Logger.getLogger(ErogaEsameServlet.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NumberFormatException e) {
            }

        }
        request.getSession().setAttribute("status", "400");

        response.sendRedirect("/sspi/sspi.html");

    }

}
