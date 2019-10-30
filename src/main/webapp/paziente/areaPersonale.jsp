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

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

        <title>Area Personale</title>

        <style>
            .imgProfilo{
                max-width: 215px;
                max-height: 215px;
            }

            .cardProfilo{
                text-align: center;
            }

            .input-icons i { 
                position: absolute; 
            } 

            .input-icons { 
                width: 93%; 
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

            .input-field-password { 
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

            .inputStandart{
                background: transparent;
                color: black;
                border: 1px solid #2d3036;
                padding-left: 1em;
                padding-right: 1em;
                padding-top: 0.5em;
                padding-bottom: 0.5em;  
                border-radius: 6px;
                position: initial;
            }

            .inputStandart:hover{
                background: #bee8c8;
                border: 1px solid #bee8c8;
                transition: background-color 0.5s ease-out, border 0.5s ease-out;               
            }

            .btnChange{
                float: right;
            }

            .btnModifica{
                float: left;
            }

            .btnConferma{
                margin:0 auto;                
            }

            .custom-file-upload {
                border: 1px solid #ccc;
                display: inline-block;
                padding: 6px 12px;
                cursor: pointer;
            }
        </style>

        <script>

            function editFunction() {
                document.getElementById("formDati").style.display = "block";
                document.getElementById("formPassword").style.display = "none";
                document.getElementById("email").readOnly = false;
                document.getElementById("mediciSuggestionBox").style.display = "block";
                document.getElementById("visualizzazioneMedico").style.display = "none";
                document.getElementById("btnSubmit").style.display = "block";
                document.getElementById("comandiBefore").style.display = "none";
                document.getElementById("comandiAfter").style.display = "block";
            }

            function backFunction() {
                document.getElementById("visualizzazioneMedico").style.display = "block";
                document.getElementById("visualizzazioneMedico").value = "${paziente.medico}";
                document.getElementById("mediciSuggestionBox").value = "${paziente.medico}";
                document.getElementById("mediciSuggestionBox").style.display = "none";
                document.getElementById("email").readOnly = true;
                document.getElementById("email").value = '${paziente.email}';
                document.getElementById("comandiBefore").style.display = "block";
                document.getElementById("btnSubmit").style.display = "none";
                document.getElementById("comandiAfter").style.display = "none";

            }

            function showPassword(id) {
                if (document.getElementById(id).type == "password") {
                    document.getElementById(id).type = "text";
                    document.getElementById(id).classList.remove(".fa-eye-slash");
                } else {
                    document.getElementById(id).type = "password";
                    document.getElementById(id).classList.remove(".fa-eye");
                }
            }

            function changePassword() {
                if (document.getElementById("formPassword").style.display == "none") {
                    document.getElementById("formDati").style.display = "none";
                    document.getElementById("comandiBefore").style.display = "block";
                    document.getElementById("comandiAfter").style.display = "none";
                    document.getElementById("btnSubmit").style.display = "none";
                    document.getElementById("formPassword").style.display = "block";
                    document.getElementById("btnSubmitPsw").style.display = "block";
                    document.getElementById("currentPassword").readOnly = false;
                    document.getElementById("newPassword").readOnly = false;
                    document.getElementById("checkPassword").readOnly = false;
                } else {
                    document.getElementById("formDati").style.display = "block";
                    document.getElementById("btnSubmit").style.display = "block";
                    document.getElementById("btnSubmitPsw").style.display = "none";
                    document.getElementById("formPassword").style.display = "none";
                }

            }

            function checkField() {
                if (document.getElementById("newPassword").value == document.getElementById("checkPassword").value) {
                    return true;
                } else {
                    document.getElementById("msgCheckPsw").style.display = "block";
                    return false;
                }
            }


        </script>

    </head>
    <body>    
        <header>
            <%@ include file="../common/navbar.jsp" %>
        </header>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <center style="margin-top:20px;">

                        <img src="../privateImage/${paziente.photoPath}" class="card-img-top imgProfilo">

                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/pazienti/aggiornaFoto.html"  method="post" enctype="multipart/form-data" class="center">                                                                                 
                                <label class="custom-file-upload">
                                    <input type="file" name="file1" style="display: none;" onchange="this.form.submit()"/>
                                    <i class="fa fa-cloud-upload"></i> Scegli file
                                </label>        
                            </form>                                    
                        </div>
                    </center>        

                    <c:if test="${not empty sessionScope.status}">
                        <c:choose>
                            <c:when test="${sessionScope.status eq 'not_found'}">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    La password inserita <strong>NON </strong> corrisponde a quella attuale! Riprova!
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.status eq 'found'}">
                                <div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    Password modificata correttamente!
                                </div>
                            </c:when>
                        </c:choose>
                        <c:remove var="status" scope="session" />
                    </c:if>


                    <!--COMANDI FORM-->
                    <div id="comandiBefore">
                        <button role="button" id="btnEdit" class="btnStandard btnModifica" onclick="editFunction()">Modifica</button>    
                    </div>
                    <div id="comandiAfter" style="display:none">
                        <button role="button" id="btnBack" class="btnStandard btnModifica" onclick="backFunction()">Annulla</button>
                    </div>
                    <button role="button" id="btnCambiaPsw" onclick="changePassword()" class="btnStandard btnChange">Cambia Password</button>


                    <br><br><br>

                    <!--FORM DATI-->
                    <div id="formDati">
                        <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/pazienti/areaPersonale.html" method="POST">
                            <div id="modifica">
                                <div class="form-group row">
                                    <label for="nome" class="col-sm-3 control-label">Nome e Cognome:</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="nome" id="nome" value="${paziente.name} ${paziente.surname}" class="form-control" readonly>
                                    </div>
                                </div>                        
                                <div class="form-group row">
                                    <label for="email" class="col-sm-3 control-label">Email:</label>
                                    <div class="col-sm-9">
                                        <input type="email" name="email" id="email" value="${paziente.email}" class="form-control" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="provincia" class="col-sm-3 control-label">Provincia:</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="provincia" id="provincia" value="${paziente.provincia}" class="form-control" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="medico" class="col-sm-3 control-label">Medico:</label>
                                    <div class="col-sm-9" id="visualizzazioneMedico">
                                        <input type="text" id="medico" value="${paziente.medico}" class="form-control" readonly>
                                    </div>
                                    <div class="col-sm-9" id="sceltaMedico">                                    
                                        <input id="mediciSuggestionBox" list="medici" name="newMedico" class="form-control" value="${paziente.medico}" style="display: none;">
                                        <datalist id="medici">
                                            <c:forEach var="mediciDisponibili" items="${mediciDisponibili}">
                                                <option value="${mediciDisponibili.idMedico}">                                                
                                                </c:forEach>                                                
                                        </datalist>                                   
                                    </div>
                                </div>
                            </div>
                            <button action="submit" id="btnSubmit" class="btnStandard btnConferma" style="display: none;">Conferma</button>
                        </form>                                        
                    </div>

                    <div id="formPassword" style="display:none;" onSubmit="return checkField()">
                        <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/pazienti/AggiornaPassword.html" method="POST">

                            <div class="form-group row"id="divPswOld">
                                <label for="currentPassword" class="col-sm-3 control-label">Password Corrente:</label>
                                <div class="col-sm-9">
                                    <div class="input-icons">
                                        <div onclick="showPassword('currentPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                        <input type="password" id="currentPassword" name="oldPassword" class="form-control input-field-password" readonly>
                                        <p style="color: red; display: none;" id="msgCurrentPsw">Password errata</p>
                                    </div>                                
                                </div>
                            </div>   
                            <div class="form-group row" id="divPswNew">
                                <label for="newPassword" class="col-sm-3 control-label">Nuova Password</label>
                                <div class="col-sm-9">
                                    <div class="input-icons">
                                        <div onclick="showPassword('newPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                        <input type="password" id="newPassword" name="newPassword" class="form-control input-field-password" readonly>
                                    </div>
                                </div>
                            </div>   
                            <div class="form-group row" id="divPswCheck">
                                <label for="checkPassword" class="col-sm-3 control-label">Conferma Password:</label>
                                <div class="col-sm-9">
                                    <div class="input-icons">
                                        <div onclick="showPassword('checkPassword')"><i class="fa fa-eye-slash icon"></i></div>
                                        <input type="password" id="checkPassword" class="form-control input-field-password" readonly>
                                        <p style="color: red; display: none;" id="msgCheckPsw">Le due password non corrispondono</p>
                                    </div>
                                </div>
                            </div> 
                            <button action="submit" style="display: none" id="btnSubmitPsw" class="btnStandard btnConferma">Conferma password</button>
                        </form>
                    </div>

                </div>
                <div class="col-md-3"></div>
            </div>
        </div>
        <footer>
            <script>
                if (window.history.replaceState) {
                    window.history.replaceState(null, null, window.location.href);
                }
            </script>
        </footer>

    </body>
</html>
