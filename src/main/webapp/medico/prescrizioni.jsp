<%-- 
    Document   : prescrizioni
    Created on : 16 set 2019, 09:17:20
    Author     : Davide
--%>

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
    </body>
</html>
