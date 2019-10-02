<%-- 
    Document   : areaPersonale
    Created on : 30 set 2019, 14:57:37
    Author     : Aster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/commonStyle.css">
        <title>Area Personale</title>

        <style>
            /**{
                border: 1px solid black;
            }
            */
            .imgProfilo{
                max-width: 150px;
                max-height: 150px;
            }

            .cardProfilo{
                text-align: center;
            }

        </style>

        <script>
            function editFunction() {
                document.getElementById("email").readOnly = false;
                document.getElementById("email").style.border = "1px solid green";
                document.getElementById("nome").style.border = "1px solid red";
                document.getElementById("provincia").readOnly = false;
                document.getElementById("comandiBefore").style.display = "none"
                document.getElementById("comandiAfter").style.display = "block";
            }

            function backFunction() {
                document.getElementById("email").readOnly = true;
                document.getElementById("provincia").readOnly = true;
                document.getElementById("comandiBefore").style.display = "block"
                document.getElementById("comandiAfter").style.display = "none";
            }
        </script>


    </head>
    <body>    
        <c:set var = "medico" scope = "session" value = "${medico}"/>
        <div class="bg">
            <br><br>
            <%@ include file="../common/navbar.jsp" %>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <div class="card cardProfilo">
                        <img class="card-img-top imgProfilo" src="../images/anonProfile.png" alt="Card image cap">
                        <div class="card-body">
                            <p class="card-text"><br>Benvenuto <b><c:out value="${paziente.name}"/> <c:out value="${paziente.surname}"/></b> nella tua area personale</p>
                        </div>
                    </div>

                    <div id="comandiBefore">
                        <button role="button" id="btnEdit" onclick="editFunction()">Modifica</button>
                    </div>
                    <div id="comandiAfter" style="display:none">
                        <button role="button" id="btnBack" onclick="backFunction()">Indietro</button>
                    </div>

                    <div id="form">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="nome" class="col-sm-2 control-label">Nome:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="nome" value="${paziente.name} ${paziente.surname}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="luogoNascita" class="col-sm-2 control-label">Luogo Nascita:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="luogoNascita" value="${paziente.birthPlace}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="dataNascita" class="col-sm-2 control-label">Data di Nascita:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="dataNascita" value="${paziente.birthDate}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-sm-2 control-label">Email:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="email" value="${paziente.email}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="provincia" class="col-sm-2 control-label">Provincia:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="provincia" value="${paziente.provincia}" class="form-control" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="medico" class="col-sm-2 control-label">Medico:</label>
                                <div class="col-sm-9">
                                    <input type="text" id="medico" value="${medico.name} ${medico.surname}" class="form-control" readonly>
                                </div>
                            </div>
                        </form>                       
                    </div>




                </div>
                <div class="col-md-3"></div>
            </div>
        </div>  
    </body>
</html>
