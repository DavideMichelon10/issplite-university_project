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
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            .list-ul{
                overflow:hidden; 
                overflow-y:scroll;
            }

            .body_div{
                margin-left: 5%;
                margin-right: 5%;
            }
            .card {
                background: rgba(255,255,255,0.5); 

                border: none;
                margin: 0 auto; /* Added */
                float: none; /* Added */
                margin-top: 20px;
                margin-bottom: 10px; /* Added */
            }
            .dati_utente{
                margin-top: 2%;
                margin-bottom: 2%;
            }
            body {

                font-family: "Poppins", sans-serif;
                height: 100vh;
            }
        </style>
        <title>Crea visita</title>
    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>
        <div class="body_div">
            <div class="dati_utente">

                <b>Nome:</b> <c:out value="${paziente.name}"/><br>
                <b>Cognome:</b> <c:out value="${paziente.surname}"/><br>
                <b>email:</b> <c:out value="${paziente.email}"/><br>
                <b>CF:</b> <c:out value="${paziente.ssn}"/><br>
            </div>


            <div class="cerca_esami">
                <form class="form-signin" action="${pageContext.request.contextPath}/medici/visita.html" method="POST">
                    <input type='text' class="form-control"placeholder='Cerca gli esami da prescrivere'><br>

                    <ul  style="height: 150px;" class="list-ul">

                        <c:forEach var="esame" items="${esami}">

                            <span class="checkbox-wrapper" id="${esame.name}">
                                <input type="checkbox" id="W${esame.idEsame}" value="${esame.name} ${esame.costo}€">${esame.name}
                            </span><br>

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
        </div>


        <script>
            $('.form-control').keyup(function submitClosure(ev) {
                $('.checkbox-wrapper').each(function inputElementClosure(index, element) {
                    element = $(element);
                    console.log("asd" + ev.target.value)
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


