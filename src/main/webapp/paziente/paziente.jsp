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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista prescrizioni</title>
        
        <style>
            .imgPagamento{
                max-width: 20px;
                max-height: 20px;
            }
            
            .imgProfilo{
                max-width: 150px;
                max-height: 150px;

            }
            
            .tabella{
                margin-right: 5%;
                margin-left: 5%;
            }
        </style>
    </head>
    <body>
        
        <br><br>
            
        <div class="card mb-3" style="max-width: 540px;">
            <div class="row no-gutters">
                <div class="col-md-4">
                    <div>
                        <img src="../images/anonProfile.png" class="card-img imgProfilo">
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <c:set var = "paziente" scope = "session" value = "${datiPaziente}"/>                        
                        <h3 class="card-title">Ciao ${paziente.name} ${paziente.surname}.</h3>
                        <h5>Benvenuto nella tua area privata</h5>
                        <form action="${pageContext.request.contextPath}/logout.handler" method="post">
                            <button class="btn btn-sm btn-primary btn-block" type="submit">Sign out</button>
                        </form>
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
                    <table class="table table-striped table-bordered">
                         <thead>
                             <tr>
                                 <th>Data prescrizione esame</th>
                                 <th>Data esecuzione esame</th> 
                                 <th>Nome esame</th>
                                 <th>Stato pagamento</th>
                                 <th>Data pubblicazione risultato</th>
                             </tr>
                         </thead>
                         <c:forEach var="esame" items="${esamiSostenuti}">
                         <tbody>
                             <tr>
                                 <div class="card">
                                         <td>${esame.dataVisita}</td>
                                         <td>${esame.dataEsecuzione}</td>
                                         <td>${esame.nomeEsame}</td>
                                         <td>
                                             <c:choose>
                                                 <c:when test="${esame.pagato}">
                                                     <img src='../images/Tick.png' class="imgPagamento">
                                                 </c:when>
                                                 <c:otherwise>
                                                     <img src='../images/Cross.png' class="imgPagamento">
                                                 </c:otherwise>
                                             </c:choose>
                                         </td>
                                         <td>${esame.dataRisultato}</td>
                                 </div>
                             </tr>
                         </tbody>
                         </c:forEach>
                    </table>
               </div>
           </div>
            
           <div class="tab-pane" id="ricette-tab">
               <br>
               <div class="tabella">
                    <table class="table table-striped table-bordered">
                         <thead>
                             <tr>
                                 <th>Data Visita</th>
                                 <th>Data prescrizione ricetta</th> 
                                 <th>Stato pagamento</th>
                                 <th>Nome farmaco</th>
                             </tr>
                         </thead>
                         <c:forEach var="ricetta" items="${ricettePrescritte}">
                         <tbody>
                             <tr>
                                 <div class="card">
                                         <td>${ricetta.dataVisita}</td>
                                         <td>${ricetta.dataPrescrizione}</td>
                                         <td>
                                             <c:choose>
                                                 <c:when test="${ricetta.ticketPagato}">
                                                     <img src='../images/Tick.png' class="imgPagamento">
                                                 </c:when>
                                                 <c:otherwise>
                                                     <img src='../images/Cross.png' class="imgPagamento">
                                                 </c:otherwise>
                                             </c:choose>
                                         </td>
                                         <td>${ricetta.nomeFarmaco}</td>
                                 </div>
                             </tr>
                         </tbody>
                         </c:forEach>
                    </table>
                </div>
           </div>
            
           <div class="tab-pane" id="richiami-tab">
               <br>
               <div class="tabella">
                    <table class="table table-striped table-bordered">
                         <thead>
                             <tr>
                                 <th>Data prescrizione richiamo</th>
                                 <th>Motivazione richiamo</th> 
                                 <th>Nome esame</th>
                             </tr>
                         </thead>
                         <c:forEach var="richiamo" items="${richiamiPrescritti}">
                         <tbody>
                             <tr>
                                 <div class="card">
                                         <td>${richiamo.dataPrescrizione}</td>
                                         <td>${richiamo.motivazione}</td>
                                         <td>${richiamo.nomeEsame}</td>
                                 </div>
                             </tr>
                         </tbody>
                         </c:forEach>
                    </table>
               </div>
           </div>
            
           <div class="tab-pane" id="all-tab">
              <br>
               <div class="tabella">
                    <table class="table table-striped table-bordered">
                         <thead>
                             <tr>
                                 <th>Data Visita</th>
                                 <th>Costo ticket</th> 
                                 <th>Stato pagamento</th>
                             </tr>
                         </thead>
                         <c:forEach var="visita" items="${visitePrescritte}">
                         <tbody>
                             <tr>
                                 <div class="card">
                                        <td>${visita.dataVisita}</td>
                                        <td>${visita.costoVisita}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${visita.ticketPagato}">
                                                    <img src='../images/Tick.png' class="imgPagamento">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src='../images/Cross.png' class="imgPagamento">
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                 </div>
                             </tr>
                         </tbody>
                         </c:forEach>
                    </table>
                </div>
           </div>
        </div>
            
    </body>
</html>