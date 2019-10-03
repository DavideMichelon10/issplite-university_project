<%-- 
    Document   : ssp
    Created on : 5 set 2019, 17:19:25
    Author     : Davide
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file="../common/navbar.jsp" %>

    </head>
    <body>
    <h1>Show a date control:</h1>

 
    <form action="${pageContext.request.contextPath}/sspi/XLSServlet">
        Selezione di quale giorno scaricare il report:
            <input type="date" name="date_selected"  pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
        
        <p>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </p>
    </form>
    
    </body>
</html>
