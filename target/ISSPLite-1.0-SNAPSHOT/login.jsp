<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:catch var="ex">
    <!DOCTYPE html>
    <html>
        <head>
            <title>Login</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
            <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
            <!-- Custom styles for this template -->
            <!--<link href="css/floating-labels.css" rel="stylesheet">-->
        </head>
        <body>
            <form class="form-signin" action="login.handler" method="POST">
                  <div class="text-center mb-4">
                    <img class="mb-4" src="images/unitn_logo_1024.png"  width="128" height="128">
                    <h3 class="h3 mb-3 font-weight-normal">Authentication Area</h3>
                    <p>Autenticati per poter accedere al tuo profilo personale di paziente/medico/ISSP.</p>
                </div>
                <div class="form-label-group">
                    <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
                    <label for="username">Username</label>
                </div>
                <div class="form-label-group">
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                    <label for="password">Password</label>
                </div>
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
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>

            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        </body>
    </html>
</c:catch>