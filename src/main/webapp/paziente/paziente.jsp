<%-- 
    Document   : medico
    Created on : 5 set 2019, 17:19:19
    Author     : Davide
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="it">
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


        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

        <title>Area personale</title>
        <style>
            .imgProfilo{
                max-height: 200px;
                max-width: 200px;
            }

            .imgPagamento{
                max-height: 20px;
                max-width: 20px;
            }
            
            table {
                border: 1px solid #ddd;
            }

            a.active{
                background-color: #087a9c !important;
                color: whitesmoke !important;
            }
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
        <header>
            <%@ include file="../common/navbar.jsp" %>
        </header>

        <div class="container-fluid">
            <br>
            <div class="row justify-content-center">
                <div class="col-sm-9">
                    <div class="row justify-content-center">
                        <img src="../privateImage/${paziente.photoPath}" class="imgProfilo">
                        <a href="../paziente/areaPersonale.html" class="mt-auto" style="color: black; text-decoration: none;">&nbsp&nbsp<i class="fas fa-cog fa-2x"></i></a>
                    </div>
                    <br>
                    <div class="row justify-content-center">                            
                        <p class="text-center">Benvenuto <b>${paziente.name} ${paziente.surname}</b> nel tuo profilo privato.</p>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-sm-2">
                    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                        <a class="nav-link active" id="v-pills-esami-tab" data-toggle="pill" href="#v-pills-esami" role="tab" aria-controls="v-pills-esami" aria-selected="true">Esami</a>
                        <a class="nav-link" id="v-pills-ricette-tab" data-toggle="pill" href="#v-pills-ricette" role="tab" aria-controls="v-pills-ricette" aria-selected="false">Ricette</a>
                        <a class="nav-link" id="v-pills-richiami-tab" data-toggle="pill" href="#v-pills-richiami" role="tab" aria-controls="v-pills-richiami" aria-selected="false">Richiami</a>
                        <a class="nav-link" id="v-pills-visite-tab" data-toggle="pill" href="#v-pills-visite" role="tab" aria-controls="v-pills-visite" aria-selected="false">Visite</a>
                        <a class="nav-link" id="v-pills-ticket-tab" data-toggle="pill" href="#v-pills-ticket" role="tab" aria-controls="v-pills-ticket" aria-selected="false">Ticket Pagati</a>
                    </div>
                </div>
                <div class="col-sm-10">
                    <div class="tab-content" id="v-pills-tabContent">
                        <div class="tab-pane fade show active" id="v-pills-esami" role="tabpanel" aria-labelledby="v-pills-esami-tab">
                            <br>
                            <main>
                                <table class="table table-striped" id="tabellaEsami">
                                    <thead>
                                        <tr>
                                            <th>Nome esame</th>
                                            <th>Data prescrizione esame</th>
                                            <th>Data pubblicazione risultato</th>
                                            <th>Stato pagamento</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="esame" items="${esamiSostenuti}">
                                            <tr>
                                                <td>${esame.nomeEsame}</td>
                                                <td>${esame.dataVisita}</td>
                                                <td><a href="downloadRisultato.html?idRisultato=${esame.idRisultato}&esame=${esame.nomeEsame}&dataVisita=${esame.dataVisita}"><i class="fa fa-download"></i> ${esame.dataRisultato}</a></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${esame.pagato}">
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
                            <br>
                            <main>
                                <table class="table table-striped" id="tabellaRicette">
                                    <thead>
                                        <tr>
                                            <th>Nome farmaco</th>
                                            <th>Data prescrizione ricetta</th>
                                            <th>Stato pagamento</th>
                                            <th>Download</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="ricetta" items="${ricettePrescritte}">
                                            <tr>
                                                <td>${ricetta.nomeFarmaco}</td>
                                                <td>${ricetta.dataPrescrizione}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${ricetta.ticketPagato}">
                                                            <p>Effettuato    <img src='../images/Tick.png' class="imgPagamento"> </p> 
                                                            </c:when>
                                                            <c:otherwise>
                                                            <p>Non pervenuto    <img src='../images/Cross.png' class="imgPagamento"> </p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                </td>
                                                <td>
                                                    <a href="downloadRicetta.html?idPrescrizione=${ricetta.idPrescrizione}&dataPrescrizione=${ricetta.dataPrescrizione}&nomeFarmaco=${ricetta.nomeFarmaco}"><i class="fa fa-download"></i> Scarica</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </main>
                        </div>
                        <div class="tab-pane fade" id="v-pills-richiami" role="tabpanel" aria-labelledby="v-pills-richiami-tab">
                            <br>
                            <main>
                                <table class="table table-striped" id="tabellaRichiami">
                                    <thead>
                                        <tr>
                                            <th>Nome esame</th>
                                            <th>Data prescrizione richiamo</th>
                                            <th>Motivazione richiamo</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="richiamo" items="${richiamiPrescritti}">
                                            <tr>
                                                <td>${richiamo.nomeEsame}</td>
                                                <td>${richiamo.dataPrescrizione}</td>
                                                <td>${richiamo.motivazione}</td>                                   
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </main>
                        </div>
                        <div class="tab-pane fade" id="v-pills-visite" role="tabpanel" aria-labelledby="v-pills-visite-tab">
                            <br>
                            <main>
                                <table class="table table-striped" id="tabellaVisite">
                                    <thead>
                                        <tr>
                                            <th>Data Visita</th>
                                            <th>Costo ticket</th>
                                            <th>Stato pagamento</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="visita" items="${visitePrescritte}">
                                            <tr>
                                                <td>${visita.dataVisita}</td>
                                                <td>${visita.costoVisita}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${visita.ticketPagato}">
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
                        <div class="tab-pane fade" id="v-pills-ticket" role="tabpanel" aria-labelledby="v-pills-ticket-tab">
                            <br>
                            <main>
                                <table class="table table-striped" id="tabellaTicket">
                                    <thead>
                                        <tr>
                                            <th>Motivazione</th>
                                            <th>Costo</th>
                                            <th>Data Pagamento</th>
                                            <th>Download</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="ticket" items="${ticketPagati}">
                                            <tr>
                                                <td>${ticket.motivazione}</td>
                                                <td>${ticket.costo} €</td>
                                                <td>${ticket.dataPagamento}</td>
                                                <td>
                                                    <a href="downloadTicket.html?motivazione=${ticket.motivazione}&costo=${ticket.costo}&dataPagamento=${ticket.dataPagamento}"><i class="fa fa-download"></i> Scarica</a>
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

        <script>
            $(document).ready(function () {
                $('#tabellaEsami').DataTable();
            });
            $(document).ready(function () {
                $('#tabellaRicette').DataTable();
            });
            $(document).ready(function () {
                $('#tabellaRichiami').DataTable();
            });
            $(document).ready(function () {
                $('#tabellaVisite').DataTable();
            });
            $(document).ready(function () {
                $('#tabellaTicket').DataTable();
            });
        </script>

    </body>
</html>