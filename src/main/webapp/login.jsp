<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:catch var="ex">
    <!DOCTYPE html>
    <html>
        <head>
            <title>Login</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <link rel="stylesheet" type="text/css" href="../css/commonStyle.css">

            <!-- Bootstrap 4.1.1 -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.min.css">

            <!-- cookiealert styles -->
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/Wruczek/Bootstrap-Cookie-Alert@gh-pages/cookiealert.css">
            <style>

                /* BASIC */

                html {
                    background-color: #56baed;
                }

                body {
                    background-image: url("images/background.jpg");
                    font-family: "Poppins", sans-serif;
                    height: 100vh;
                }

                a {
                    color: #92badd;
                    display:inline-block;
                    text-decoration: none;
                    font-weight: 400;
                }

                h2 {
                    text-align: center;
                    font-size: 16px;
                    font-weight: 600;
                    text-transform: uppercase;
                    display:inline-block;
                    margin: 40px 8px 10px 8px; 
                    color: #cccccc;
                }



                /* STRUCTURE */

                .wrapper {
                    display: flex;
                    align-items: center;
                    flex-direction: column; 
                    justify-content: center;
                    width: 100%;
                    min-height: 100%;
                    padding: 20px;
                }

                #formContent {
                    -webkit-border-radius: 10px 10px 10px 10px;
                    border-radius: 10px 10px 10px 10px;
                    background: #fff;
                    padding: 30px;
                    width: 90%;
                    max-width: 450px;
                    position: relative;
                    padding: 0px;
                    -webkit-box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
                    box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
                    text-align: center;
                }

                #formFooter {
                    background-color: #f6f6f6;
                    border-top: 1px solid #dce8f1;
                    padding: 25px;
                    text-align: center;
                    -webkit-border-radius: 0 0 10px 10px;
                    border-radius: 0 0 10px 10px;
                }



                /* TABS */

                h2.inactive {
                    color: #cccccc;
                }

                h2.active {
                    color: #0d0d0d;
                    border-bottom: 2px solid #5fbae9;
                }



                /* FORM TYPOGRAPHY*/

                input[type=button], input[type=submit], input[type=reset]  {
                    background-color: #56baed;
                    border: none;
                    color: white;
                    padding: 15px 80px;
                    text-align: center;
                    text-decoration: none;
                    display: inline-block;
                    text-transform: uppercase;
                    font-size: 13px;
                    -webkit-box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
                    box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
                    -webkit-border-radius: 5px 5px 5px 5px;
                    border-radius: 5px 5px 5px 5px;
                    margin: 5px 20px 40px 20px;
                    -webkit-transition: all 0.3s ease-in-out;
                    -moz-transition: all 0.3s ease-in-out;
                    -ms-transition: all 0.3s ease-in-out;
                    -o-transition: all 0.3s ease-in-out;
                    transition: all 0.3s ease-in-out;
                }

                input[type=button]:hover, input[type=submit]:hover, input[type=reset]:hover  {
                    background-color: #39ace7;
                }

                input[type=button]:active, input[type=submit]:active, input[type=reset]:active  {
                    -moz-transform: scale(0.95);
                    -webkit-transform: scale(0.95);
                    -o-transform: scale(0.95);
                    -ms-transform: scale(0.95);
                    transform: scale(0.95);
                }

                input[type=text] {
                    background-color: #f6f6f6;
                    border: none;
                    color: #0d0d0d;
                    padding: 15px 32px;
                    text-align: center;
                    text-decoration: none;
                    display: inline-block;
                    font-size: 16px;
                    margin: 5px;
                    width: 85%;
                    border: 2px solid #f6f6f6;
                    -webkit-transition: all 0.5s ease-in-out;
                    -moz-transition: all 0.5s ease-in-out;
                    -ms-transition: all 0.5s ease-in-out;
                    -o-transition: all 0.5s ease-in-out;
                    transition: all 0.5s ease-in-out;
                    -webkit-border-radius: 5px 5px 5px 5px;
                    border-radius: 5px 5px 5px 5px;
                }

                input[type=password] {
                    background-color: #f6f6f6;
                    border: none;
                    color: #0d0d0d;
                    padding: 15px 32px;
                    text-align: center;
                    text-decoration: none;
                    display: inline-block;
                    font-size: 16px;
                    margin: 5px;
                    width: 85%;
                    border: 2px solid #f6f6f6;
                    -webkit-transition: all 0.5s ease-in-out;
                    -moz-transition: all 0.5s ease-in-out;
                    -ms-transition: all 0.5s ease-in-out;
                    -o-transition: all 0.5s ease-in-out;
                    transition: all 0.5s ease-in-out;
                    -webkit-border-radius: 5px 5px 5px 5px;
                    border-radius: 5px 5px 5px 5px;
                }

                input[type=text]:focus {
                    background-color: #fff;
                    border-bottom: 2px solid #5fbae9;
                }

                input[type=text]:placeholder {
                    color: #cccccc;
                }



                /* ANIMATIONS */

                /* Simple CSS3 Fade-in-down Animation */
                .fadeInDown {
                    -webkit-animation-name: fadeInDown;
                    animation-name: fadeInDown;
                    -webkit-animation-duration: 1s;
                    animation-duration: 1s;
                    -webkit-animation-fill-mode: both;
                    animation-fill-mode: both;
                }

                @-webkit-keyframes fadeInDown {
                    0% {
                        opacity: 0;
                        -webkit-transform: translate3d(0, -100%, 0);
                        transform: translate3d(0, -100%, 0);
                    }
                    100% {
                        opacity: 1;
                        -webkit-transform: none;
                        transform: none;
                    }
                }

                @keyframes fadeInDown {
                    0% {
                        opacity: 0;
                        -webkit-transform: translate3d(0, -100%, 0);
                        transform: translate3d(0, -100%, 0);
                    }
                    100% {
                        opacity: 1;
                        -webkit-transform: none;
                        transform: none;
                    }
                }

                /* Simple CSS3 Fade-in Animation */
                @-webkit-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
                @-moz-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
                @keyframes fadeIn { from { opacity:0; } to { opacity:1; } }

                .fadeIn {
                    opacity:0;
                    -webkit-animation:fadeIn ease-in 1;
                    -moz-animation:fadeIn ease-in 1;
                    animation:fadeIn ease-in 1;

                    -webkit-animation-fill-mode:forwards;
                    -moz-animation-fill-mode:forwards;
                    animation-fill-mode:forwards;

                    -webkit-animation-duration:1s;
                    -moz-animation-duration:1s;
                    animation-duration:1s;
                }

                .fadeIn.first {
                    -webkit-animation-delay: 0.4s;
                    -moz-animation-delay: 0.4s;
                    animation-delay: 0.4s;
                }

                .fadeIn.second {
                    -webkit-animation-delay: 0.6s;
                    -moz-animation-delay: 0.6s;
                    animation-delay: 0.6s;
                }

                .fadeIn.third {
                    -webkit-animation-delay: 0.8s;
                    -moz-animation-delay: 0.8s;
                    animation-delay: 0.8s;
                }

                .fadeIn.fourth {
                    -webkit-animation-delay: 1s;
                    -moz-animation-delay: 1s;
                    animation-delay: 1s;
                }

                /* Simple CSS3 Fade-in Animation */
                .underlineHover:after {
                    display: block;
                    left: 0;
                    bottom: -10px;
                    width: 0;
                    height: 2px;
                    background-color: #56baed;
                    content: "";
                    transition: width 0.2s;
                }

                .underlineHover:hover {
                    color: #0d0d0d;
                }

                .underlineHover:hover:after{
                    width: 100%;
                }



                /* OTHERS */

                *:focus {
                    outline: none;
                } 

                #icon {
                    width:60%;
                }

                .alertCookieClass{
                    display: none;
                }

            </style>


        </head>
        <body>        
            <div class="wrapper fadeInDown">
                <div id="formContent">
                    <div class="text-center mb-4">
                        <p>Autenticati per poter accedere al tuo profilo personale di paziente/medico/ISSP.</p>
                    </div>
                    <form action="login.handler" method="POST" onsubmit="cryptPassword()">
                        <input type="text" id="login" class="fadeIn second" name="username" placeholder="Username" required>
                        <input type="password" id="password" class="fadeIn third" name="password" placeholder="Password" required>
                        <div class="checkbox mb-3">
                            <label>
                                <input type="checkbox" name="rememberMe" value="true"> Remember me
                            </label>
                        </div>

                        <div class="container-fluid">
                            <c:if test="${not empty sessionScope.status}">
                                <c:choose>
                                    <c:when test="${sessionScope.status eq 'not_found'}">
                                        <div class="alert alert-danger alert-dismissible" role="alert">
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            Le credenziali <strong>NON </strong> corrispondono! Riprova!
                                        </div>
                                    </c:when>
                                </c:choose>
                                <c:remove var="status" scope="session" />
                            </c:if>

                        </div>
                        <input class="fadeIn fourth" value="login" type="submit">
                    </form>
                </div>
            </div>



            <!-- START Bootstrap-Cookie-Alert -->
            <div class="alert alert-dismissible text-center cookiealert" role="alert" id="alertCookie">
                <div class="cookiealert-container">
                    &#x1F36A Questo sito usa i <b>cookies</b> per garantirti un'esperienza d'uso migliore <a href="informativaPrivacy.jsp" target="_blank">Maggiori informazioni</a>

                    <button type="button" class="btn btn-primary btn-sm" onclick="hideCookieAlert()" aria-label="Close">
                        Accetto
                    </button>
                </div>
            </div>
            <!-- END Bootstrap-Cookie-Alert -->

            <!-- JAVASCRIPT -->

            <script>
                function hideCookieAlert() {
                    var element = document.getElementById("alertCookie");
                    element.classList.add("alertCookieClass");
                }
            </script>
            <script>
                function cryptPassword(password){
                    console.log('ciao' + password)
                    return true
                }
            </script>
            <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/2.2.0/js.cookie.min.js"></script>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/Wruczek/Bootstrap-Cookie-Alert@gh-pages/cookiealert.css">
            <script src="https://cdn.jsdelivr.net/gh/Wruczek/Bootstrap-Cookie-Alert@gh-pages/cookiealert.js"></script>
        </body>
    </html>
</c:catch>