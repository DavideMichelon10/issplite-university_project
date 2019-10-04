<%-- 
    Document   : navbar
    Created on : 28 set 2019, 11:12:41
    Author     : Aster
--%>

<%@page import="com.mycompany.issplite.persistence.entities.Paziente"%>
<%@page import="com.mycompany.issplite.persistence.entities.Medico"%>
<%@page import="com.mycompany.issplite.persistence.entities.SSP"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Navbar</title>
        <link rel="stylesheet" type="text/css" href="../css/commonStyle.css">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

  
        <style>
            .navbar-default {
                background-color: #087a9c;  
                display: block;
            }
            
            .testoMenu{
                color: whitesmoke !important;
            }
            body { 
                padding-top: 55px; 
            }         
            .tastoLogout:hover{
                font-weight: bold;
            }
        </style>
        
        <%
            String userType = "";
            if(session.getAttribute("medico") != null){
                userType = "medico";
            }else if(session.getAttribute("paziente") != null){
                userType = "paziente";
            }else if(session.getAttribute("ssp") != null){
                userType = "ssp";
            }
        %>
    </head>
    <body>
        
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <%
                        if(session.getAttribute("paziente") != null){%>                    
                            <c:set var = "paziente" scope = "session" value = "${paziente}"/>
                            <a class="navbar-brand testoMenu" href="../pazienti/pazienti.html">iSSPLite</a>
                        <%}else if(session.getAttribute("medico") != null){%>                    
                            <c:set var = "medico" scope = "session" value = "${medico}"/>
                            <a class="navbar-brand testoMenu" href="../medici/medici.html">iSSPLite</a>
                        <%}else if(session.getAttribute("ssp") != null){%>                    
                            <c:set var = "ssp" scope = "session" value = "${ssp}"/>
                            <a class="navbar-brand testoMenu" href="../ssp/ssp.html">iSSPLite</a>
                        <%}else{%>                    
                        <a class="navbar-brand testoMenu" href="../index.html">iSSPLite</a>
                        <%}
                    %>              
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <%
                        if(session.getAttribute("paziente") != null){%>                    
                            <c:set var = "paziente" scope = "session" value = "${paziente}"/>
                            <li><p class="navbar-text testoMenu">Benvenuto <b><c:out value="${paziente.name}"/> <c:out value="${paziente.surname}"/></b></p></li>
                            <li><a href="${pageContext.request.contextPath}/logout.handler" class="testoMenu tastoLogout">Log out</a></li>
                        <%}else if(session.getAttribute("medico") != null){%>                    
                            <c:set var = "medico" scope = "session" value = "${medico}"/>
                            <li><p class="navbar-text testoMenu">Benvenuto <b><c:out value="${medico.name}"/> <c:out value="${medico.surname}"/></b></p></li>
                            <li><a href="${pageContext.request.contextPath}/logout.handler" class="testoMenu tastoLogout">Log out</a></li>
                        <%}else if(session.getAttribute("ssp") != null){%>                    
                            <c:set var = "ssp" scope = "session" value = "${ssp}"/>
                            <li><p class="navbar-text testoMenu">Benvenuto <b><c:out value="${ssp.provincia}"/></b></p></li>
                            <li><a href="${pageContext.request.contextPath}/logout.handler" class="testoMenu tastoLogout">Log out</a></li>
                        <%}else{%>                    
                            <li><p class="navbar-text testoMenu">Benvenuto su <b>iSSPLite</b></p></li>
                        <%}
                    %>
                </ul>                
            </div>
        </nav>
    </body>
</html>
