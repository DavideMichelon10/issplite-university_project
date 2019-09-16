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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prescrizioni</title>
    </head>
    <body>
        <h1>Prescrizioni</h1>
        <a href="${pageContext.request.contextPath}/medici/visita.html?idPaziente=${param["idPaziente"]}">Crea visita</a>

                
        <h3>Lista esami prescritti al paziente:</h3>
        <table>
            <tr>
                <th>Esame</th>
                <th>Data</th>
                <th>Risultato</th>
                <th>UPagato</th>

            </tr>
            <c:forEach var="esame" items="${esamiPrescritti}">
                <tr>
                    <div class="card">
                            <td>${esame.name}</td>
                            <td>${esame.erogationDate}</td>
                            <td>${esame.risultato}</td>
                            <td>${esame.isPagato}</td>
                    </div>
                </tr>
            </c:forEach>
        </table>

        <h3>Lista farmaci prescritti al paziente:</h3>
        
        <table>
            <tr>
                <th>Nome</th>
                <th>Descrizione</th> 
                <th>Data di prescrizione</th>
                <th>Pagato</th>

            </tr>
            <c:forEach var="farmaco" items="${farmaciPrescritti}">
                <tr>
                    <div class="card">
                            <td>${farmaco.name}</td>
                            <td>${farmaco.description}</td>
                            <td>${farmaco.prescriptionDate}</td>
                            <td>${farmaco.isPagato}</td>
                    </div>
                </tr>
            </c:forEach>
        </table>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
