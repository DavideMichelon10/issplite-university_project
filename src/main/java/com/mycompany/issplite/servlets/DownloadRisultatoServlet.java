/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.RisultatoDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.entities.Risultato;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Paziente;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lollo
 */
public class DownloadRisultatoServlet extends HttpServlet {
    private RisultatoDAO risultatoDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            risultatoDao = daoFactory.getDAO(RisultatoDAO.class);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        allOkay(request, response);
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Paziente paziente = (Paziente) session.getAttribute("paziente");

        String idRisultato = request.getParameter("idRisultato");
        String esame = request.getParameter("esame");
        String dataVisita = request.getParameter("dataVisita");
        
        Risultato risultato = null;
        try {
            risultato = risultatoDao.getRisultatoById(idRisultato);
        } catch (DAOException ex) {
            Logger.getLogger(DownloadRisultatoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph(risultato.getResultDate()));
        doc.add(new Paragraph(""));
        doc.add(new Paragraph("Gentile " + paziente.getName() + " " + paziente.getSurname() + ",\n l'esame " + esame + " da lei sostenuto in data " + dataVisita + " ha avuto il seguente esito: \n " + risultato.getDescription()));
        doc.close();

        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
          
        response.sendRedirect("/pazienti/pazienti.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        allOkay(request, response);

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(response.encodeRedirectURL("/pazienti/paziente.html"));
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void allOkay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cp = getServletContext().getContextPath();
        if (!cp.endsWith("/")) {
            cp += "/";
        }

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        Paziente paziente = (Paziente) session.getAttribute("paziente");

        if (paziente.getIdPaziente() == null) {
            response.sendRedirect(cp + "login.jsp");
        }
    }

}
