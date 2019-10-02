<%-- 
    Document   : prescrizioni
    Created on : 16 set 2019, 09:17:20
    Author     : Davide
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prescrizioni</title>
    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>

        <h1>Prescrizioni</h1>
        <div class="container">
            <div class="row">
                <div class="col">
                    <h5>Lista esami prescritti al paziente:</h5>
                </div>

                <div class="col">
                    <a  class="btn btn-primary" href="${pageContext.request.contextPath}/medici/visita.html?idPaziente=${param["idPaziente"]}">Crea visita</a>
                </div>
            </div>
        </div>



        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">Esame</th>
                    <th scope="col">Data</th>
                    <th scope="col">Risultato</th>
                    <th scope="col">Pagato</th>
                <tr>
            </thead>    
        </tr>
        <tbody>

            <c:forEach var="esame" items="${esamiPrescritti}">
                <tr>
                    <td>${esame.name}</td>
                    <td>${esame.erogationDate}</td>
                    <td>${esame.risultato}</td>
                    <td>${esame.isPagato}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h5>Lista farmaci prescritti al paziente:</h5>

    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">Nome</th>
                <th scope="col">Descrizione</th> 
                <th scope="col">Data di prescrizione</th>
                <th scope="col">Pagato</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="farmaco" items="${farmaciPrescritti}">
                <tr>
                    <td>${farmaco.name}</td>
                    <td>${farmaco.description}</td>
                    <td>${farmaco.prescriptionDate}</td>
                    <td>${farmaco.isPagato}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
