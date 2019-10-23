<%-- 
    Document   : creaVisita
    Created on : 16 set 2019, 15:15:24
    Author     : Davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            .list-ul{
                overflow:hidden; 
                overflow-y:scroll;
            }


            .btn{
                background-color: #087a9c !important;
                color: whitesmoke !important;
            }
            .dati_utente{
                margin-top: 2%;
                margin-bottom: 2%;
            }

            .card-horizontal {
                display: flex;
                flex: 1 1 auto;
            }


            .immagine{
                margin-top: 1em;
                max-height: 180px;
                max-width: 180px;
            }


            .infoPaziente {
                display: inline-block;
                text-align: left;
                margin-left: 0px !important;
            }

            @media 
            only screen and (max-width: 760px),
            (min-device-width: 768px) and (max-device-width: 1024px) { 
                main{
                    overflow: scroll;
                }
            } 
        </style>
        <title>Crea visita</title>
    </head>
    <body>
        <%@ include file="../common/navbarmedico.jsp" %>
        <div class ="container-fluid">
            <center>

                <img src="../privateImage/${paziente.photoPath}" class="immagine">            
                <br>
                <br>

                <ul class="infoPaziente" style="list-style-type: none; margin-left: 2em;">
                    <li><b>Nome:</b> ${paziente.name} ${paziente.surname}</li>
                    <li><b>Email:</b> ${paziente.email}</li>
                    <li><b>CF:</b> ${paziente.ssn}</li>
                </ul>
            </center>
            <br>
            <div class="container">
                <div class="cerca_esami">
                    <form class="form-signin" action="${pageContext.request.contextPath}/medici/visita.html" method="POST">
                        <input type='text' class="form-control" id="formEsami" placeholder='Cerca gli esami da prescrivere'><br>
                        <input type="hidden" name="idPaziente" value="${param.idPaziente}">
                        <ul style="height: 150px;" class="list-ul">
                            <c:forEach var="esame" items="${esami}">
                                <span class="checkbox-wrapper" id="${esame.name}">
                                    <input type="checkbox" name="${esame.idEsame}">  ${esame.name} ${esame.costo}€ 
                                </span>
                                <br>
                            </c:forEach> 
                        </ul>


                        <input id="sug" list="farmaco" name="farmaco" placeholder="Seleziona il farmaco da prescrivere" class="form-control">
                        <datalist id="farmaco">
                            <c:forEach var="farmaco" items="${farmaci}">
                                <option value="${farmaco.idFarmaco} - ${farmaco.name}">                                                
                                </c:forEach>                                                
                        </datalist> 


                        <div class="container-fluid">

                            <label>
                                <input type="checkbox" name="pagato" value="true">Pagato
                            </label>
                        </div>

                        <button class="btn btn-lg btn-primary btn-block" type="submit">Crea visita</button>

                    </form>
                </div>
            </div>

        </div>


        <script>
            $('#formEsami').keyup(function submitClosure(ev) {
                $('.checkbox-wrapper').each(function inputElementClosure(index, element) {
                    element = $(element);
                    if (element.attr('id').indexOf(ev.target.value) > -1) {
                        element.show();
                    } else {
                        element.hide();
                    }
                });
            });
        </script>

    </body>
</html>


