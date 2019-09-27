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
        <title>Lista pazienti</title>
    </head>
    <body>
        
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">ISSPLite</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item active">
              <a class="nav-link" href="#">Lista pazienti <span class="sr-only">(current)</span></a>
            </li>

            <li>
            <form action="${pageContext.request.contextPath}/logout.handler" method="post">
                  <button class="btn btn-lg btn-primary btn-block" type="submit">Sign out</button>
              </form>
            </li>
          </ul>
        </div>
      </nav>
        
        
        <h1>Lista pazienti:</h1>

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
            <c:forEach var="paziente" items="${pazienti}">
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
