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
        <form class="form-signin" action="${pageContext.request.contextPath}/creavisita.handler" method="POST">   
                <button class="btn btn-lg btn-primary btn-block" type="submit">Crea visita</button>
        </form>
                
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

    </body>
</html>
