<%-- 
    Document   : erogaEsameSingolo
    Created on : 10 ott 2019, 21:18:18
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


        <link rel="stylesheet" type="text/css" href="../css/commonStyle.css">

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

        <title>Eroga esame</title>
        <style>
            
            .btn{
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
            <%@ include file="../common/navbarmedico.jsp" %>
        </header>
        <div class="container-fluid">
            <center>
                <div class="container-fluid">
                    <form class="form-signin form_search_asd" method="POST" action="${pageContext.request.contextPath}/medici/erogaesamedettagli.html?idRisultato=${risultato.idRisultato}">
                        <br>   
                        <h3> Eroga il <b> risultato&nbsp; </b>dell'esame</h3>
                        <p>Inserisci il risultato dell'esame e conferma.</p>
                        <br>
                        <div class="tabella" id="div_tab">
                            <main>
                                <table class="table table-striped table-bordered"  id="tabellaEsami">
                                    <thead>
                                        <tr>
                                            <th>idRisultato:</th>
                                            <th>Data erogazione visita:</th>
                                            <th>Esame:</th>
                                            <th>SSN:</th>
                                            <th>Paziente:</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td> <c:out value="${risultato.idRisultato}"/></td>
                                            <td> <c:out value="${risultato.erogationDateVisit}"/></td>
                                            <td><c:out value="${risultato.examName}"/></td>
                                            <td><c:out value="${risultato.ssn}"/><br></td>
                                            <td><c:out value="${risultato.pazienteName} ${risultato.pazienteSurname}"/></td>

                                        </tr>
                                    </tbody>
                                </table>
                            </main>
                        </div>

                        <div id="mot_btn">
                            <div class="col-sm-9" id="sceltaEsame">                                     
                                <br>
                                <input type="text" id="mot" name="description" placeholder="Scrivi il risultato" class="form-control" required>
                                <br>
                                <button class="btn btn-primary" style="width: 17em;" id="but" type="submit">Eroga risultati esame</button>
                            </div>
                        </div>
                    </form>
                </div>
            </center>
        </div>
    </body>
</html>
