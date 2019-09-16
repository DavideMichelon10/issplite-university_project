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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista pazienti</title>
    </head>
    <body>
        
        <form action="${pageContext.request.contextPath}/logout.handler" method="post">
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign out</button>
        </form>
        <h1>Lista pazienti:</h1>

        <table>
            <tr>
                <th>SSN</th>
                <th>Data ultimo esame fatto</th> 
                <th>Ultimo esame fatto</th>
                <th>Data ultima ricetta prescritta</th>
                <th>Ultima ricetta prescritta</th>

            </tr>
            <c:forEach var="paziente" items="${pazienti}">
                <tr>
                    <div class="card">
                            <td><a href="<c:url value="/medici/prescrizione.html?idPaziente=${paziente.idpaziente}"/>">${paziente.ssn}</a></td>
                            <td>${paziente.erogationDateEsame}</td>
                            <td>${paziente.nameEsame}</td>
                            <td>${paziente.erogationDateFarmaco}</td>
                            <td>${paziente.nameFarmaco}</td>
                    </div>
                </tr>
            </c:forEach>
                
               
        </table>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>    
    </body>
</html>
