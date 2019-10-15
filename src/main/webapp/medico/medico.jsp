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

        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">        
        <!--JQUERY-->
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

        <!--DATATABLE-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>

        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>

        <style>

            a.active{
                background-color: #087a9c !important;
                color: whitesmoke !important;
            }
            
            table {
                border: 1px solid #ddd;
            }

            .card-horizontal {
                display: flex;
                flex: 1 1 auto;
            }

            .immagine{
                margin-left: 5%;
                max-height: 180px;
                max-width: 180px;
            }

            tr:nth-child(even){background-color: #f2f2f2}

            @media 
            only screen and (max-width: 760px),
            (min-device-width: 768px) and (max-device-width: 1024px) { 
                main{
                    overflow: scroll;
                }
            }
        </style>



        <title>Lista pazienti</title>
    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 mt-3">
                    <div class="card">
                        <div class="card-horizontal">
                            <div class="img-square-wrapper">
                                <img class="immagine" src="../images/ministeroDellaSalute.png" alt="Card image cap">
                            </div>
                            <div class="card-body">
                                <center>
                                    <h4 class="card-title" id="date"></h4>
                                    <script>
                                        n = new Date();
                                        y = n.getFullYear();
                                        m = n.getMonth() + 1;
                                        d = n.getDate();
                                        document.getElementById("date").innerHTML = d + "/" + m + "/" + y;
                                    </script>
                                    <p class="card-text">La cosa che più ti aiuta a guarire è il buon umore.</p>
                                </center>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>





            <div class="row justify-content-center">
                <div class="col-sm-2">
                    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                        <a class="nav-link active" id="v-pills-pazienti-tab" data-toggle="pill" href="#v-pills-pazienti" role="tab" aria-controls="v-pills-pazienti" aria-selected="true">Pazienti</a>
                        <a class="nav-link" id="v-pills-esami-tab" data-toggle="pill" href="#v-pills-esami" role="tab" aria-controls="v-pills-esami" aria-selected="false">Pazienti con ultimi esami e farmaci</a>
                    </div>
                </div>
                <div class="col-sm-10">
                    <div class="tab-content" id="v-pills-tabContent">
                        <div class="tab-pane fade show active" id="v-pills-pazienti" role="tabpanel" aria-labelledby="v-pills-pazienti-tab">
                            <input class="form-control" type="text" id="myInput" onkeyup="searchPaziente()" placeholder="Cerca un paziente tramite SSN" title="Cerca un paziente tramite SSN">
                            <main>
                                <table class="table table-striped" id="tabellaRicette">
                                    <thead>
                                        <tr>
                                            <th scope="col">SSN</th>
                                            <th scope="col">Nome</th> 
                                            <th scope="col">Cognome</th>
                                            <th scope="col">Data di nascita</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="p" items="${pazienti}">
                                            <tr>
                                                <td><a href="<c:url value="/medici/prescrizione.html?idPaziente=${p.idPaziente}"/>">${p.ssn}</a></td>
                                                <td>${p.name}</td>
                                                <td>${p.surname}</td>
                                                <td>${p.birthDate}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </main>
                        </div>
                        <div class="tab-pane fade" id="v-pills-esami" role="tabpanel" aria-labelledby="v-pills-esami-tab">
                            <input class="form-control" type="text" id="myInput_" onkeyup="searchPazienteUltimo()" placeholder="Cerca un paziente tramite SSN per vedere ultimi esami e famaci prescritti" title ="Cerca un paziente tramite SSN">
                            <main>
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
                                    <tbody>
                                        <c:forEach var="paziente" items="${pazientiConEsamiFarmaci}">
                                            <tr>
                                                <td><a href="<c:url value="/medici/prescrizione.html?idPaziente=${paziente.idpaziente}"/>">${paziente.ssn}</a></td>
                                                <td>${paziente.erogationDateEsame}</td>
                                                <td>${paziente.nameEsame}</td>
                                                <td>${paziente.erogationDateFarmaco}</td>
                                                <td>${paziente.nameFarmaco}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </main>
                        </div>
                    </div>
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


        </script>
    </body>
</html>
