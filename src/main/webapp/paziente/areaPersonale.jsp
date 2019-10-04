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

            .btnStandard{
                background: transparent;
                color: black;
                border: 1px solid #2d3036;
                padding-left: 2em;
                padding-right: 2em;
                padding-top: 0.5em;
                padding-bottom: 0.5em;  
                border-radius: 6px;
                position: initial;
            }
            
            .btnStandard:hover{
                background: #bee8c8;
                border: 1px solid #bee8c8;
                transition: background-color 0.5s ease-out, border 0.5s ease-out;               
            }
        </style>

        <script>

            function editFunction() {
                document.getElementById("email").readOnly = false;
                document.getElementById("currentPassword").readOnly = false;
                document.getElementById("newPassword").readOnly = false;
                document.getElementById("checkPassword").readOnly = false;
                document.getElementById("comandiBefore").style.display = "none";
                document.getElementById("btnSubmit").style.display = "block";
                document.getElementById("comandiAfter").style.display = "block";
            }

            function backFunction() {
                document.getElementById("email").readOnly = true;
                document.getElementById("currentPassword").readOnly = true;
                document.getElementById("newPassword").readOnly = true;
                document.getElementById("checkPassword").readOnly = true;
                document.getElementById("comandiBefore").style.display = "block";
                document.getElementById("btnSubmit").style.display = "none";
                document.getElementById("comandiAfter").style.display = "none";
                document.getElementById("msgCurrentPsw").style.display = "none";
                document.getElementById("msgCheckPsw").style.display = "none";
            }

            function showPassword(id) {
                if (document.getElementById(id).type == "password") {
                    document.getElementById(id).type = "text";
                    document.getElementById(id).classList.remove(".fa-eye-slash");
                    document.getElementById(id).classList.add(".prova");
                } else {
                    document.getElementById(id).type = "password";
                    document.getElementById(id).classList.remove(".fa-eye");
                    document.getElementById(id).classList.add(".prova");
                }
            }

            function checkField() {
                var currentPsw = document.getElementById("currentPassword").value;
                var oldPsw = '${paziente.password}';
                var newPsw = document.getElementById("newPassword").value;
                var checkPsw = document.getElementById("checkPassword").value;
                if(document.getElementById("psw").style.display == "block"){
                    if (currentPsw == oldPsw) {
                        if (newPsw == checkPsw && newPsw != '' && checkPsw != '') {
                            return true;
                        } else {
                            document.getElementById("msgCheckPsw").style.display = "block";
                            return false;
                        }
                    } else {
                        document.getElementById("msgCurrentPsw").style.display = "block";
                        return false;
                    }
                }else{
                    document.getElementById("newPassword").value = null;
                    return true;
                }
                
            }

            function changePassword() {
                if (document.getElementById("psw").style.display == "none") {
                    document.getElementById("psw").style.display = "block";
                    document.getElementById("btnSubmit").style.display = "block";
                    document.getElementById("currentPassword").readOnly = false;
                    document.getElementById("newPassword").readOnly = false;
                    document.getElementById("checkPassword").readOnly = false;
                } else {
                    document.getElementById("psw").style.display = "none";
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
                </div>
                <div id="comandiBefore">
                    <button role="button" id="btnEdit" class="btnStandard" onclick="editFunction()">Modifica</button>    
                </div>
                <div id="comandiAfter" style="display:none">
                    <button role="button" id="btnBack" class="btnStandard" onclick="backFunction()">Indietro </button>
                </div>
                <button role="button" id="btnCambiaPsw" onclick="changePassword()" class="btnStandard">Cambia Password</button>

                    <br>
                <div id="form">
                    <form class="form-horizontal" role="form"  onsubmit="return checkField()" action="https://www.google.it/">
                        <div class="form-group">
                            <label for="nome" class="col-sm-3 control-label">Nome e Cognome:</label>
                            <div class="col-sm-9">
                                <input type="text" id="nome" value="${paziente.name} ${paziente.surname}" class="form-control" readonly>
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
                        <div id="psw" style="display: none;">
                            <div class="form-group" id="divPswOld">
                                <label for="currentPassword" class="col-sm-3 control-label">Password Corrente:</label>
                                <div class="col-sm-9">
                                    <div class="input-icons">
                                        <div onclick="showPassword('currentPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                        <input type="password" id="currentPassword" class="form-control input-field" readonly>
                                        <p style="color: red; display: none;" id="msgCurrentPsw">Password errata</p>
                                    </div>                                
                                </div>
                            </div>   
                            <div class="form-group" id="divPswNew">
                                <label for="newPassword" class="col-sm-3 control-label">Nuova Password</label>
                                <div class="col-sm-9">
                                    <div class="input-icons">
                                        <div onclick="showPassword('newPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                        <input type="password" id="newPassword" class="form-control input-field" readonly>
                                    </div>
                                </div>
                            </div>   
                            <div class="form-group" id="divPswCheck">
                                <label for="checkPassword" class="col-sm-3 control-label">Conferma Password:</label>
                                <div class="col-sm-9">
                                    <div class="input-icons">
                                        <div onclick="showPassword('checkPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                        <input type="password" id="checkPassword" class="form-control input-field" readonly>
                                        <p style="color: red; display: none;" id="msgCheckPsw">Le due password non corrispondono</p>
                                    </div>
                                </div>
                            </div>  
                        </div>
                        <br>
                        <button action="submit" style="display: none" id="btnSubmit" class="btnStandard">Conferma</button>

                    </form>                       
                </div>




            </div>
            <div class="col-md-3"></div>
        </div>

    </body>
</html>
