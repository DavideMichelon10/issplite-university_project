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

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
           
            .form-control{
                margin-bottom: 0.5%;
            }
            
            .tab-content{
                margin-top: 0.5%;
                margin-left: 5%;
                margin-right: 5%;
            }
            table {
                border-collapse: collapse;
                border-spacing: 0;
                width: 100%;
                border: 1px solid #ddd;
            }

            th, td {
                text-align: left;
                padding: 8px;
            }

            tr:nth-child(even){background-color: #f2f2f2}
        </style>
        <title>Lista pazienti</title>
    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>


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
                                <th scope="col">SSN</th>
                                <th scope="col">Data ultimo esame fatto</th> 
                                <th scope="col">Ultimo esame fatto</th>
                                <th scope="col">Data ultima ricetta prescritta</th>
                                <th scope="col">Ultima ricetta prescritta</th>

                            </tr>
                        </thead>


                        <c:forEach var="paziente" items="${pazientiConEsamiFarmaci}">
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
                </div>
            </div>
            <div class="tab-pane" id="ricette-tab">
                <br>
                <div class="tabella">
                    <input class="form-control" type="text" id="myInput" onkeyup="searchPaziente()" placeholder="Cerca un paziente tramite SSN" title="Cerca un paziente tramite SSN">
                    <table class="table table-striped" id="tabellaRicette">

                        <thead>

                            <tr>
                                <th scope="col">SSN</th>
                                <th scope="col">Nome</th> 
                                <th scope="col">Cognome</th>
                                <th scope="col">Data di nascita</th>

                            </tr>
                        </thead>
                        <c:forEach var="p" items="${pazienti}">
                            <tbody>
                                <tr>
                                    <td><a href="<c:url value="/medici/prescrizione.html?idPaziente=${p.idPaziente}"/>">${p.ssn}</a></td>
                                    <td>${p.name}</td>
                                    <td>${p.surname}</td>
                                    <td>${p.birthDate}</td>
                                </tr>
                            </tbody>
                        </c:forEach>





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
    </body>
</html>
