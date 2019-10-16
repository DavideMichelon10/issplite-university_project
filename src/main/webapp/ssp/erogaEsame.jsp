<%-- 
    Document   : erogaEsame
    Created on : 9 ott 2019, 21:49:00
    Author     : Davide
--%>

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
+
        <title>Eroga esame</title><style>
            #div_tab{
                margin-left: 5%;
                margin-right: 5%;
            }


            .form_search{
                text-align: center ;

                margin-left: 5%;
                margin-right: 5%;
            }
            #title { text-align: center; }


            #date{
                align-content: center;
            }

            #mot_btn{
                margin-bottom: 50px;
            }



        </style>
    </head>
    <body>
        <%@ include file="../common/navbarssp.jsp" %>
        <div id="title">
            <h3> Seleziona su quale esame vuoi inserire <b>l'esito </b></h3>
            <p>Seleziona la visita per poter inserire l'esito dell'esame effettuato in passato.</p>
        </div>
        <br>

        <div class="tabella" id="div_tab">
            <table class="table table-striped table-bordered"  id="tabellaEsami">
                <thead>
                    <tr>
                        <th>Identificativo risultato</th>
                        <th>Data prescrizione esame</th>
                        <th>Nome esame</th>
                        <th>Paziente</th>
                        <th>Eroga visita</th>

                    </tr>
                </thead>
                <tbody>
                <c:forEach var="r" items="${esami}">
                    <tr>
                        <td>${r.idRisultato}</td>
                        <td>${r.erogationDateVisit}</td>
                        <td>${r.examName}</td>
                        <td>${r.pazienteName} ${r.pazienteSurname}</td>
                        <td><a href="<c:url value="/sspi/erogaesamedettagli.html?idRisultato=${r.idRisultato}"/>">eroga</a></td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
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
