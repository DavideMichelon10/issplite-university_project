<%-- 
    Document   : prescrizioni
    Created on : 16 set 2019, 09:17:20
    Author     : Davide
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">

        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prescrizioni</title>
    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>

        <h1>Prescrizioni</h1>
        <div class="container">
            <div class="row">

                <div class="col">
                    <a  class="btn btn-primary" href="${pageContext.request.contextPath}/medici/visita.html?idPaziente=${param["idPaziente"]}">Crea visita</a>
                </div>
            </div>
        </div>

        <ul class="nav nav-tabs">
            <li><a href="#ricette-tab" class="tablinks" data-toggle="tab">Pazienti</a></li>
            <li><a href="#esami-tab" class="tablinks" data-toggle="tab">Pazienti con ultimi esami esami e farmaci</a></li>
        </ul>
                
        <div class="tab-content">
            <div class="tab-pane active" id="esami-tab">
                <!-- Esami -->
                <br>
                <div class="tabella">
                    <input class="form-control" type="text" id="myInput_" onkeyup="searchPazienteUltimo()" placeholder="Cerca un paziente tramite SSN per vedere ultimi esami e famaci prescritti" title ="Cerca un paziente tramite SSN">

                    <table class="table table-striped" style="width:100%" id="tabellaEsami">
                        <thead>
                            <tr>
                                <th scope="col">Esame</th>
                                <th scope="col">Data</th>
                                <th scope="col">Risultato</th>
                                <th scope="col">Pagato</th>
                            <tr>
                        </thead>    
                        </tr>
                        <tbody>

                            <c:forEach var="esame" items="${esamiPrescritti}">
                                <tr>
                                    <td>${esame.name}</td>
                                    <td>${esame.erogationDate}</td>
                                    <td>${esame.risultato}</td>
                                    <td>${esame.isPagato}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="tab-pane" id="ricette-tab">
                <br>
                <div class="tabella">
                    <input class="form-control" type="text" id="myInput" onkeyup="searchPaziente()" placeholder="Cerca un paziente tramite SSN" title="Cerca un paziente tramite SSN">
                    <table class="table table-striped" id="tabellaRicette">

                        <thead>
                            <tr>
                                <th scope="col">Nome</th>
                                <th scope="col">Descrizione</th> 
                                <th scope="col">Data di prescrizione</th>
                                <th scope="col">Pagato</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="farmaco" items="${farmaciPrescritti}">
                                <tr>
                                    <td>${farmaco.name}</td>
                                    <td>${farmaco.description}</td>
                                    <td>${farmaco.prescriptionDate}</td>
                                    <td>${farmaco.isPagato}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <script type="text/javascript">

            function searchPaziente() {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput");
                filter = input.value.toUpperCase();
                table = document.getElementById("tabellaRicette");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[0];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }

            function searchPazienteUltimo() {
                var input, filter, table, tr, td, i, txtValue;
                input = document.getElementById("myInput_");
                filter = input.value.toUpperCase();
                table = document.getElementById("tabellaEsami");
                tr = table.getElementsByTagName("tr");
                for (i = 0; i < tr.length; i++) {
                    td = tr[i].getElementsByTagName("td")[0];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }

            $(document).ready(function () {
                document.getElementsByClassName('tablinks')[0].click();
            });

        </script>        



        <h5>Lista farmaci prescritti al paziente:</h5>



        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
