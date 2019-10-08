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
        <link rel="stylesheet" type="text/css" href="../css/commonStyle.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
        <!-- DATATABLE-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> 

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista prescrizioni</title>
        <style>
            .imgPagamento{
                max-width: 20px;
                max-height: 20px;
            }
            .imgSetting{
                max-width: 20px;
                max-height: 20px;
                margin-top: -1px;
            }
            .imgProfilo{
                max-width: 150px;
                max-height: 150px;
            }
            .tabella{
                margin-right: 5%;
                margin-left: 5%;
            }            
            table{
                border: 1px solid black !important;
            }
        </style>

    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>
        <div class="card mb-3" style="max-width: 540px;">
            <div class="row no-gutters">
                <div class="col-md-4">
                    <div>
                        <img src="../privateImage/profile_picture_${paziente.idPaziente}.jpg" class="card-img imgProfilo">
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <c:set var = "paziente" scope = "session" value = "${datiPaziente}"/>
                        <h3 class="card-title">Ciao ${paziente.name} ${paziente.surname}.</h3>
                        <h5>Benvenuto nella tua area privata</h5>
                        <div class="row no-gutters">
                            <div class="col-md-6">
                                <form action="${pageContext.request.contextPath}/logout.handler" method="post">
                                    <button class="btn btn-sm btn-primary btn-block" type="submit">Sign out</button>                            
                                </form>
                            </div>
                            <div class="col-md-6">
                                <a href="../paziente/areaPersonale.html" class="btn btn-sm btn-primary btn-block" role="button"><img src="../images/setting.ico" class="imgSetting"> Area Personale</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <br><br>

        <ul class="nav nav-tabs">
            <li><a href="#esami-tab" data-toggle="tab">Esami</a></li>
            <li><a href="#ricette-tab" data-toggle="tab">Ricette</a></li>
            <li><a href="#richiami-tab" data-toggle="tab">Richiami</a></li>
            <li><a href="#all-tab" data-toggle="tab">Visite</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="esami-tab">
                <!-- Esami -->
                <br>
                <div class="tabella">
                    <table class="table table-striped" style="width:100%" id="tabellaEsami">
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
                                    <td>${esame.dataRisultato}</td>
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
                </div>
            </div>
            <div class="tab-pane" id="ricette-tab">
                <br>
                <div class="tabella">
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
                </div>
            </div>
            <div class="tab-pane" id="richiami-tab">
                <br>
                <div class="tabella">
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
                </div>
            </div>
            <div class="tab-pane" id="all-tab">
                <br>
                <div class="tabella">
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
                </div>
            </div>
        </div>
        <script type="text/javascript">
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
        </script>
    </body>
</html>