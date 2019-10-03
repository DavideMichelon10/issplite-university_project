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
import com.mycompany.issplite.persistence.entities.RicetteErogatePerGiorno;
import com.mycompany.issplite.persistence.entities.SSP;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Davide
 */
public class XLSServlet extends HttpServlet {

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
        String date = request.getParameter("date_selected");
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        SSP ssp = (SSP) session.getAttribute("ssp");

        String provincia = ssp.getProvincia();
        
        System.out.println("DATE: " + date + " provincia:" + provincia);

        try {
            List<RicetteErogatePerGiorno> ricette = sspDao.getRicettePerDay(provincia, date);
        } catch (DAOException ex) {
            Logger.getLogger(XLSServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
