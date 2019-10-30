/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Paziente;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
        

/**
 *
 * @author Aster
 */
public class AggiornaFotoServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "C:\\Users\\lollo\\OneDrive\\Documents\\NetBeansProjects\\issplite\\src\\main\\webapp\\privateImage";
    private PazienteDAO pazienteDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for user storage system");
        }
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        allOkay(request, response);
        
        response.sendRedirect("/pazienti/areaPersonale.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        allOkay(request, response);
 
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        Paziente paziente = (Paziente) session.getAttribute("paziente");
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        if(name != null && name != ""){
                            name = "profile_picture_"+paziente.getIdPaziente()+".jpg";
                            pazienteDao.changePhotoPath(name, paziente.getIdPaziente());
                            item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                        }
                    }
                }                
                //File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }
        } else {
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }
 
        try {
            paziente = pazienteDao.getById(paziente.getIdPaziente());
        } catch (DAOException ex) {
            Logger.getLogger(AggiornaFotoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.setAttribute("paziente", paziente);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AggiornaFotoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.sendRedirect("/pazienti/areaPersonale.html");
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
