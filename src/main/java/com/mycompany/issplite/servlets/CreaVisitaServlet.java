package com.mycompany.issplite.servlets;

import com.mycompany.issplite.persistence.dao.EsameDAO;
import com.mycompany.issplite.persistence.dao.MedicoDAO;
import com.mycompany.issplite.persistence.dao.PazienteDAO;
import com.mycompany.issplite.persistence.dao.factories.DAOException;
import com.mycompany.issplite.persistence.dao.factories.DAOFactory;
import com.mycompany.issplite.persistence.dao.factories.DAOFactoryException;
import com.mycompany.issplite.persistence.entities.Esame;
import com.mycompany.issplite.persistence.entities.Medico;
import com.mycompany.issplite.persistence.entities.Paziente;
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
import java.util.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class CreaVisitaServlet extends HttpServlet {

private EsameDAO esameDao;
private MedicoDAO medicoDao;
private PazienteDAO pazienteDao;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
        if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
        try {
            pazienteDao = daoFactory.getDAO(PazienteDAO.class);           
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
                Esame esame = esameDao.getById(idEsame);
                Paziente paziente = pazienteDao.getById(idPaziente);
                sendEmail(esame, paziente);
            }
            
           
        }catch (DAOException ex) {
            Logger.getLogger(CreaVisitaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        response.sendRedirect("/medici/prescrizione.html?idPaziente="+idPaziente);
    }
    
    private String getIdMedico(HttpServletRequest request){
        HttpSession session = ((HttpServletRequest)request).getSession(false);   
        
        Medico medico = (Medico) session.getAttribute("medico");
        return medico.getIdMedico();
    }

    private void sendEmail(Esame esame, Paziente paziente) {
        final String host = "smtp.gmail.com";
        final String port = "465";
        final String username = "davidemichelon10@gmail.com";
        //HttpSession session = ((HttpServletRequest) request).getSession(false);

        Properties props = System.getProperties();

        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.socketFactory.port", port);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.debug", "true");


        System.out.println("prima di session");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                
                System.out.println("IN getPasswordAuthentication");
                    String password = "DavideEbbasta";
                    return new PasswordAuthentication(username, password);
                }
            });
        
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress(username));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(paziente.getEmail(), false));
                //OGGETTO
                msg.setSubject("Prescrizione esame "+esame.getName());
                //TESTO
                msg.setText("Salve "+paziente.getName() +" "+paziente.getSurname()+" la informiamo che le è stato prescritto l' esame: "+esame.getName());
                msg.setSentDate(new Date());
                Transport.send(msg);
            } catch (MessagingException me) {
            //TODO: log the exception
                me.printStackTrace(System.err);
            }
        }

    }


