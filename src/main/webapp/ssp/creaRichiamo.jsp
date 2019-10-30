<%-- 
    Document   : creaRichiamo
    Created on : 4 ott 2019, 16:33:26
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
        <title>Crea richiamo</title>

        <style>
            button{
                background-color: #087a9c !important;
                color: whitesmoke !important;
            }
        </style>


    </head>
    <body>
        <header>
            <%@ include file="../common/navbarssp.jsp" %>
        </header>
        <div class="container-fluid">
            <center>
                <div id="title">
                    <br>
                    <c:if test="${not empty sessionScope.status}">
                    <c:choose>
                        <c:when test="${sessionScope.status eq 'create'}">
                            <div class="alert alert-success alert-dismissible " role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                Richiamo eseguito correttamente!
                            </div>
                        </c:when>
                    </c:choose>
                    <c:remove var="status" scope="session" />
                </c:if>
                    <br>
                    <h3> Crea un nuovo <b>richiamo</b></h3>
                    <p>Seleziona le persone che vuoi richiamare ad un particolare esame. Ad ogni paziente richiamo verrà inviata una mail.</p>
                </div>
                <div id="form_create_richiamo">
                    <form class="form-signin" action="${pageContext.request.contextPath}/sspi/richiami.html">
                        <br>
                        <div id="date">
                            <b>Seleziona le persone nate:</b>
                            <br>
                            <b>da:&nbsp;</b>
                            <input type="date" name="from"  pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" required>
                            <br>
                            <br>
                            <b>a: &nbsp; </b>
                            <input type="date" name="to"  pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" required>
                            <br>
                            <br>

                            <ul style="margin: 0; padding: 0;" class="list-ul">
                                <input name="maschi" type="checkbox" value="Maschi"><label>&nbsp; &nbsp;Maschi</label>
                                <br>
                                <input name="femmine" type="checkbox" value="Femmine"><label>&nbsp; &nbsp;Femmine</label>
                            </ul>
                        </div>
                        <br>
                        <div id="mot_btn">
                            <div class="col-sm-9" style=" width: 90%; margin-left: 5%;" id="sceltaEsame">                                    
                                <input id="sug" list="esame" name="esame" placeholder="Seleziona l'esame da prescrivere" class="form-control" required>
                                <datalist id="esame">
                                    <c:forEach var="esame" items="${esami}">
                                        <option value="${esame.idEsame} - ${esame.name}">                                                
                                        </c:forEach>                                                
                                </datalist> 
                                <br>
                                <div  id="text_mot">
                                    <input type="text" id="mot" name="motivation" placeholder="Scrivi la motivazione" class="form-control" required><br>
                                    <button style="width: 10em;" class="btn btn-primary" id="but" type="submit">Crea richiamo</button>

                                </div>
                            </div>
                        </div>


                    </form>
                    <div class="modal" tabindex="-1" id="alert_danger" role="dialog">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title"><b>Non hai selezionato i campi necessari</b></h5>

                                </div>
                                <div class="modal-body">
                                    <p>Devi selezionare almeno un sesso da richiamare.</p>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" onclick="closeMod()" data-dismiss="modal">Chiudi</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </center>
        </div>
        <script>
            $('#but').click(function () {
                checked = $("input[type=checkbox]:checked").length;

                if (!checked) {
                    $('#alert_danger').show();
                    return false;
                }

            });

            function closeMod() {
                $('#alert_danger').modal('toggle');
            }
        </script>
    </body>
</html>
