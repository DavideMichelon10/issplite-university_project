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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> 
        <title>Area Personale</title>

        <style>
            .imgProfilo{
                max-width: 150px;
                max-height: 150px;
            }

            .cardProfilo{
                text-align: center;
            }

            .input-icons i { 
                position: absolute; 
            } 

            .input-icons { 
                width: 100%; 
                margin-bottom: 10px;
                text-align: right;
                
            } 

            .icon { 
                padding: 10px; 
                min-width: 40px;
            } 

            .input-field { 
                width: 100%; 
                padding: 10px; 
            } 
            
            .prova{
                background-color: black;
                border: 10px solid black;
            }

        </style>

        <script>
            
            function editFunction() {
                document.getElementById("email").readOnly = false;
                document.getElementById("currentPassword").readOnly = false;
                document.getElementById("newPassword").readOnly = false;
                document.getElementById("checkPassword").readOnly = false;
                document.getElementById("comandiBefore").style.display = "none"
                document.getElementById("comandiAfter").style.display = "block";
            }

            function backFunction() {
                document.getElementById("email").readOnly = true;
                document.getElementById("currentPassword").readOnly = true;
                document.getElementById("newPassword").readOnly = true;
                document.getElementById("checkPassword").readOnly = true;
                document.getElementById("comandiBefore").style.display = "block"
                document.getElementById("comandiAfter").style.display = "none";
            }
            
            function showPassword(id) {
                if(document.getElementById(id).type == "password"){
                    document.getElementById(id).type = "text";
                    document.getElementById(id).classList.remove(".fa-eye-slash");
                    document.getElementById(id).classList.add(".prova");                    
                }else{
                    document.getElementById(id).type = "password";                    
                    document.getElementById(id).classList.remove(".fa-eye");
                    document.getElementById(id).classList.add(".prova");
                }
            }
            
            function checkField(oldPassword) {
                var currentPassword = document.getElementById("currentPassword").value;
                var oldPassword = '${paziente.password}';
                if(!currentPassword == oldPassword){
                    
                }
            }
        </script>


    </head>
    <body>    
        <c:set var = "medico" scope = "session" value = "${medico}"/>
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
                            <label for="nome" class="col-sm-3 control-label">Nome e Cognome:</label>
                            <div class="col-sm-9">
                                <input type="text" id="nome" value="${paziente.name} ${paziente.surname}" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="luogoNascita" class="col-sm-3 control-label">Luogo Nascita:</label>
                            <div class="col-sm-9">
                                <input type="text" id="luogoNascita" value="${paziente.birthPlace}" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dataNascita" class="col-sm-3 control-label">Data di Nascita:</label>
                            <div class="col-sm-9">
                                <input type="text" id="dataNascita" value="${paziente.birthDate}" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email" class="col-sm-3 control-label">Email:</label>
                            <div class="col-sm-9">
                                <input type="text" id="email" value="${paziente.email}" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="provincia" class="col-sm-3 control-label">Provincia:</label>
                            <div class="col-sm-9">
                                <input type="text" id="provincia" value="${paziente.provincia}" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="medico" class="col-sm-3 control-label">Medico:</label>
                            <div class="col-sm-9">
                                <input type="text" id="medico" value="${medico.name} ${medico.surname}" class="form-control" readonly>
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="currentPassword" class="col-sm-3 control-label">Password Corrente:</label>
                            <div class="col-sm-9">
                                <div class="input-icons">
                                    <div onclick="showPassword('currentPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                    <input type="password" id="currentPassword" class="form-control input-field" readonly>
                                </div>
                            </div>
                        </div>   
                        <div class="form-group">
                            <label for="newPassword" class="col-sm-3 control-label">Nuova Password</label>
                            <div class="col-sm-9">
                                <div class="input-icons">
                                    <div onclick="showPassword('newPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                    <input type="password" id="newPassword" class="form-control input-field" readonly>
                                </div>
                            </div>
                        </div>   
                        <div class="form-group">
                            <label for="checkPassword" class="col-sm-3 control-label">Conferma Password:</label>
                            <div class="col-sm-9">
                                <div class="input-icons">
                                    <div onclick="showPassword('checkPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                    <input type="password" id="checkPassword" class="form-control input-field" readonly>
                                </div>
                            </div>
                        </div>   
                            <button action="submit" onSubmit="checkField(${oldPassword})">Submit</button>

                    </form>                       
                </div>




            </div>
            <div class="col-md-3"></div>
        </div>

    </body>
</html>
