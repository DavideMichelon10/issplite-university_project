<%-- 
    Document   : creaRichiamo
    Created on : 4 ott 2019, 16:33:26
    Author     : Davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../css/commonStyle.css">
        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> 

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="../common/navbar.jsp" %>
        <style>
            body { text-align: center; }
            

            #date{
                align-content: center;
            }
            #mot{
                
            }
            #mot_btn{
                margin-bottom: 50px;
            }
        </style>
        <div id="form_create_richiamo">
            <form class="form-signin" action="${pageContext.request.contextPath}/sspi/richiami.html">
                <div id="date">
                    Seleziona le persone nate:
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
                        <input name="maschi" type="checkbox" value="Maschi"><label>Maschi</label>
                        <br>
                        <input name="femmine" type="checkbox" value="Femmine"><label>Femmine</label>
                    </ul>
                </div>
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
                            <input type="text" id="mot" name="motivation" placeholder="Scrivi la motivazione" class="form-control" required>
                            <button style="margin-top: 1%; width: 350px;" class="btn btn-primary" id="but" type="submit">Crea richiamo</button>

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
