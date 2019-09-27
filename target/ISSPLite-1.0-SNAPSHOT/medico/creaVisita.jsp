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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crea visita</title>
    </head>
    <body>
        
        <h1>Crea visita</h1>
        <h3>Paziente: </h3>
        <div class="card">
            Nome: <c:out value="${paziente.name}"/><br>
            Cognome: <c:out value="${paziente.surname}"/><br>
            email: <c:out value="${paziente.email}"/><br>
            codice fiscale: <c:out value="${paziente.ssn}"/><br>

        </div>
        <div class="container">
            <h3>Esami prescrivibili:</h3>
                        
  
            
            <form class="form-signin" action="${pageContext.request.contextPath}/medici/visita.html" method="POST">
            <%--<input type="hidden" name="idPaziente" value="${param.idPaziente}">--%>
            <input type='text' placeholder='Search' class='text'><br>
            
                      <ul class="list-group list-group-flush">
          
                    <c:forEach var="esame" items="${esami}">

                                <span class="checkbox-wrapper" id="${esame.name}">
                                    <input type="checkbox" id="W${esame.idEsame}" value="${esame.name} ${esame.costo}€">${esame.name}
                                </span>
                    </c:forEach> 
                      </ul>
                        
                  
                <hr>
                <div class="container-fluid">

                    <label>
                        <input type="checkbox" name="pagato" value="true">Pagato
                    </label>
                </div>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Crea visita</button>

            </form>
                  
        </div>
            
            <script>
                $('.text').keyup(function submitClosure(ev) {
                    $('.checkbox-wrapper').each(function inputElementClosure(index, element) {
                        element = $(element);
                        console.log("asd" +ev.target.value)
                        if (element.attr('id').indexOf(ev.target.value) > -1) {
                            element.show();
                        } else {
                            element.hide();
                        }
                    });
                });
</script>
        
    </body>
</html>


