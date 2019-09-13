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
        <jsp:scriptlet>
            int index = 1;
        </jsp:scriptlet>
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
                        <div class="card-header" id="heading<%=index%>">
                                    <td>${paziente.ssn}</td>
                                    <td>${paziente.erogationDateEsame}</td>
                                    <td>${paziente.nameEsame}</td>
                                    <td>${paziente.erogationDateFarmaco}</td>
                                    <td>${paziente.nameFarmaco}</td>

                        </div>
                    </div>
                </tr>
            </c:forEach>
        </table>

        
    </body>
</html>
