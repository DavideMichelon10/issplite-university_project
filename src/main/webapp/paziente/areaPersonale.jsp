<%-- 
    Document   : areaPersonale
    Created on : 30 set 2019, 14:57:37
    Author     : Aster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Area Personale</title>
        
        <style>
            /**{
                border: 1px solid black;
            }
            */
            .imgProfilo{
                max-width: 150px;
                max-height: 150px;
            }
            
            .cardProfilo{
                text-align: center;
            }
            
        </style>
    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="card cardProfilo">
                    <img class="card-img-top imgProfilo" src="../images/anonProfile.png" alt="Card image cap">
                    <div class="card-body">
                        <p class="card-text"><br>Benvenuto <b><c:out value="${paziente.name}"/> <c:out value="${paziente.surname}"/></b> nella tua area personale</p>
                    </div>
                </div>
                <ul class="list-group">
                    <li class="list-group-item"><b>NOME:</b> ${paziente.name} ${paziente.surname} </li>
                    <li class="list-group-item"><b>DATA DI NASCITA:</b> ${paziente.birthDate}</li>
                    <li class="list-group-item"><b>EMAIL:</b> ${paziente.email}</li>
                    <li class="list-group-item"><b>NOME:</b></li>
                    <li class="list-group-item"><b>NOME:</b></li>
                </ul>
            </div>
            <div class="col-md-3"></div>
        </div>
    </body>
</html>
