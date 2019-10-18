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

        <title>Prescrizioni</title>
        <style>


            table {
                border: 1px solid #ddd;
            }

            .imgProfilo{
                margin-top: 5px;
                max-height: 200px;
                max-width: 200px;
            }


            .imgPagamento{
                max-width: 20px;
                max-height: 20px;
            }

            .cardStyle{
                margin-top: 20px;
            }
            .immagine{
                margin-left: 5%;
                max-height: 180px;
                max-width: 180px;
            }

            a.active{
                background-color: #087a9c !important;
                color: whitesmoke !important;
            }

            .btn{
                background-color: #087a9c !important;
                color: whitesmoke !important;
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
    </head>
    <body>
        <%@ include file="../common/navbarmedico.jsp" %>

        <div class="container-fluid">
            <center>
                <div class="card cardStyle">
                    <img src="../privateImage/${paziente.photoPath}" class="card-img-top imgProfilo">
                    <div class="card-body">
                        <h5 class="card-title"><b>${paziente.name} ${paziente.surname}</b></h5>
                        <a href="${pageContext.request.contextPath}/medici/visita.html?idPaziente=${param["idPaziente"]}" class="btn btn-primary">Crea visita</a>
                    </div>
                </div>
            </center>
            <br>
            <div class="row justify-content-center">
                <div class="col-sm-2">
                    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                        <a class="nav-link active" id="v-pills-esami-tab" data-toggle="pill" href="#v-pills-esami" role="tab" aria-controls="v-pills-esami" aria-selected="true">Esami prescritti</a>
                        <a class="nav-link" id="v-pills-ricette-tab" data-toggle="pill" href="#v-pills-ricette" role="tab" aria-controls="v-pills-ricette" aria-selected="false">Ricette prescritte</a>
                        <br>
                    </div>
                </div>

                <div class="col-sm-10">
                    <div class="tab-content" id="v-pills-tabContent">
                        <div class="tab-pane fade show active" id="v-pills-esami" role="tabpanel" aria-labelledby="v-pills-esami-tab">
                            <input class="form-control" type="text" id="myInput_" onkeyup="searchEsami()" placeholder="Cerca un esame" title ="Cerca un esame">
                            <main>
                                <table class="table table-striped" id="tabellaEsami">
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
                                                <c:if test="${empty esame.risultato}">
                                                    <td><b>Risultato non disponibile</b></td>
                                                </c:if>
                                                <c:if test="${not empty esame.risultato}">
                                                    <td>${esame.risultato}</td>
                                                </c:if>
                                        <td>
                                            <c:choose>
                                                <c:when test="${esame.isPagato}">
                                                    <p>Effettuato    <img src='../images/Tick.png' class="imgPagamento"> </p> 
                                                    </c:when>
                                                    <c:otherwise>
                                                    <p>Non pervenuto    <img src='../images/Cross.png' class="imgPagamento"> </p>
                                                    </c:otherwise>
                                                </c:choose>
                                        </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </main>
                        </div>
                        <div class="tab-pane fade" id="v-pills-ricette" role="tabpanel" aria-labelledby="v-pills-ricette-tab">
                            <input class="form-control" type="text" id="myInput" onkeyup="searchFarmaci()" placeholder="Cerca un farmaco" title="Cerca un farmaco">
                            <main>
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
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${farmaco.isPagato}">
                                                            <p>Effettuato    <img src='../images/Tick.png' class="imgPagamento"> </p> 
                                                            </c:when>
                                                            <c:otherwise>
                                                            <p>Non pervenuto    <img src='../images/Cross.png' class="imgPagamento"> </p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                </td>
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

            function searchFarmaci() {
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

            function searchEsami() {
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
