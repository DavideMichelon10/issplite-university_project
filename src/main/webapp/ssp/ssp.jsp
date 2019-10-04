
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Lista prescrizioni</title>
    </head>
    <body>
        <style>
            #div_tab{
                margin-left: 5%;
                margin-right: 5%;
            }
           
            
            .form_search{
                text-align: center ;

                margin-left: 5%;
                margin-right: 5%;
            }
        </style>
        <%@ include file="../common/navbar.jsp" %>

        <br>
        <br>
        <form class="form_search" action="${pageContext.request.contextPath}/sspi/sspi.html">
            <b>Selezione di quale giorno scaricare il report:</b>
            <input type="date" name="date_selected"  pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
            <br>
            <br>
            <p>    
                <button style="width: 250px;" class="btn btn-primary" type="submit">Cerca</button>
            <p>
        </form>


        <div class="tabella" id="div_tab">
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
                table.buttons().container()
        .appendTo( '#example_wrapper .col-md-6:eq(0)' );
            });
        </script>
    </body>
</html>
