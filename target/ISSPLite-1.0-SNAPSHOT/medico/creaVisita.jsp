<%-- 
    Document   : creaVisita
    Created on : 16 set 2019, 15:15:24
    Author     : Davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crea visita</title>
    </head>
    <body>
        <h1>Crea visita</h1>
        <h6>Paziente: </h6>
        <h5>Esami prescritti:</h5>
        
        
        <form class="form-signin" action="${pageContext.request.contextPath}/medici/visita.html" method="POST">
        <input type="hidden" name="idPaziente" value="${param.idPaziente}">

            <c:forEach var="esame" items="${esami}">
                <tr>
                    <div class="card">
                            <input type="checkbox" name="${esame.idEsame}">  ${esame.name} ${esame.costo}€ 
                    </div>
                </tr>
            </c:forEach>
                
            <hr>
            <div class="container-fluid">

                <label>
                    <input type="checkbox" name="pagato" value="true">Pagato
                </label>
            </div>
            
            <button class="btn btn-lg btn-primary btn-block" type="submit">Crea visita</button>

        </form>
        
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
    </body>
</html>
