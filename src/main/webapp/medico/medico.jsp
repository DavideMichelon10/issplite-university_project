<%-- 
    Document   : medico
    Created on : 5 set 2019, 17:19:19
    Author     : Davide
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/nav.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}
</style>
        <title>Lista pazienti</title>
    </head>
    <body>
         <%@ include file="../common/navbar.jsp" %>

        <h1>Lista pazienti:</h1>

        <table class="table table-striped">
            <thead>

                <tr>
                    <th scope="col">SSN</th>
                    <th scope="col">Nome</th> 
                    <th scope="col">Cognome</th>
                    <th scope="col">Data di nascita</th>

                </tr>
            </thead>
            <c:forEach var="p" items="${pazienti}">
                <tbody>
                    <tr>
                                <td><a href="<c:url value="/medici/prescrizione.html?idPaziente=${p.idPaziente}"/>">${p.ssn}</a></td>
                                <td>${p.name}</td>
                                <td>${p.surname}</td>
                                <td>${p.birthDate}</td>
                    </tr>
                </tbody>
            </c:forEach>

                
                
                
               
        </table>
                  
                  
        <h1>Lista pazienti con ultimi esami e farmaci prescritti:</h1>

        <table class="table table-striped">
            <thead>

                <tr>
                    <th scope="col">SSN</th>
                    <th scope="col">Data ultimo esame fatto</th> 
                    <th scope="col">Ultimo esame fatto</th>
                    <th scope="col">Data ultima ricetta prescritta</th>
                    <th scope="col">Ultima ricetta prescritta</th>

                </tr>
            </thead>
            
            
            <c:forEach var="paziente" items="${pazientiConEsamiFarmaci}">
                <tbody>
                    <tr>
                                <td><a href="<c:url value="/medici/prescrizione.html?idPaziente=${paziente.idpaziente}"/>">${paziente.ssn}</a></td>
                                <td>${paziente.erogationDateEsame}</td>
                                <td>${paziente.nameEsame}</td>
                                <td>${paziente.erogationDateFarmaco}</td>
                                <td>${paziente.nameFarmaco}</td>
                    </tr>
                </tbody>
            </c:forEach>
                
                
                
               
        </table>

    </body>
</html>
