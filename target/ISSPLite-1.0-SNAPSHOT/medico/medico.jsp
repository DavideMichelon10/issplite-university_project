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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Medico!</h1>
            

        <form action="${pageContext.request.contextPath}/logout.handler" method="post">

            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign out</button>
        </form>
    </body>
</html>
