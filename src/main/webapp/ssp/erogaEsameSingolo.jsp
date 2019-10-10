<%-- 
    Document   : erogaEsameSingolo
    Created on : 10 ott 2019, 21:18:18
    Author     : Davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <link rel="stylesheet" type="text/css" href="../css/commonStyle.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.6.0/js/dataTables.buttons.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.6.0/js/buttons.bootstrap4.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.6.0/js/buttons.flash.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.6.0/js/buttons.html5.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.6.0/js/buttons.print.min.js"></script>

        <!-- DATATABLE-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eroga esame</title>
        <style>

           
            #date{
                align-content: center;
            }
            
          
            body {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <%@ include file="../common/navbarssp.jsp" %>

        
        <form class="form-signin form_search_asd" method="POST" action="${pageContext.request.contextPath}/sspi/erogaesamedettagli.html?idRisultato=${risultato.idRisultato}">
            <div id="title">
                <h3> Eroga il <b> risultato&nbsp; </b>dell'esame</h3>
                <p>Inserisci il risultato dell'esame e conferma.</p>
            </div>
            <br>
            <div id="dati_risultato">
            <b>idRisultato: </b> <c:out value="${risultato.idRisultato}"/><br>
            <b>Data erogazione visita:</b> <c:out value="${risultato.erogationDateVisit}"/><br>
            <b>Esame: </b> <c:out value="${risultato.examName}"/><br>
            <b>Paziente: </b> <c:out value="${risultato.ssn} - ${risultato.pazienteName} ${risultato.pazienteSurname}"/><br>
        </div>
            <br>

            <br>
            <div id="mot_btn">
                <div class="col-sm-9" style=" width: 90%; margin-left: 5%;" id="sceltaEsame">                                     
                    <div  id="text_mot">
                        <input type="text" id="mot" name="description" placeholder="Scrivi la motivazione" class="form-control" required>
                        <button style="margin-top: 1%; width: 350px;" class="btn btn-primary" id="but" type="submit">Eroga risultati esame</button>

                    </div>
                </div>
            </div>


        </form>
    </body>
</html>
