
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
        <title>Report giornalieri</title>

        <style>
            @media 
            only screen and (max-width: 760px),
            (min-device-width: 768px) and (max-device-width: 1024px) { 
                main{
                    overflow: scroll;
                }
            }

            #div_tab{
                margin-left: 5%;
                margin-right: 5%;
            }


            .form_search{
                text-align: center ;

                margin-left: 5%;
                margin-right: 5%;
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
        </style>
    </head>
    <body>
        <header>
            <%@ include file="../common/navbarssp.jsp" %>
        </header>
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
                                    <h3 class="card-text">Benvenuto <b>${ssp.provincia}</b></h3>
                                </center>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div style="text-align: center;">
                <h3> Stampa il <b>report giornaliero</b></h3>
            </div>
            <br>
            <form class="form_search" action="${pageContext.request.contextPath}/sspi/sspi.html">
                <p>Selezione il <b>giorno</b> di cui si vuole il report:
                    <input type="date" name="date_selected"  pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" required="true"></p>
                <br>
                <button style="width: 8em;" class="btn btn-primary" type="submit">Cerca</button>
            </form>
            <br>

            <div class="tabella" id="div_tab">
                <main>
                    <table class="table table-striped table-bordered"  id="tabellaEsami">
                        <thead>
                            <tr>
                                <th>Nome medico</th>
                                <th>Cognome medico</th>
                                <th>Nome farmaco</th>
                                <th>SSN paziente</th>
                                <th>Ticket</th>
                                <th>Data prescrizione</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${ricette}">
                                <tr>
                                    <td>${r.medicoName}</td>
                                    <td>${r.medicoSurname}</td>
                                    <td>${r.farmacoName}</td>
                                    <td>${r.pazienteSSN}</td>
                                    <td>${r.ticket}</td>
                                    <td>${r.date}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </main>
            </div>
        </div>
        <script type="text/javascript">

            $(document).ready(function () {

                $('#tabellaEsami').DataTable({
                    dom: 'Bfrtip',
                    buttons: [{
                            extend: 'excel',
                            text: 'XLS',
                            exportOptions: {
                                modifier: {
                                    page: 'current'
                                }
                            }
                        }, {
                            extend: 'csv',

                        }, {
                            extend: 'pdf',

                        }
                    ]
                });
            });

        </script>
    </body>
</html>
