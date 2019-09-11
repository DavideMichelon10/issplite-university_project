<%-- 
    Document   : medico
    Created on : 5 set 2019, 17:19:19
    Author     : Davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Medico!</h1>
            <form class="form-signin" action="medici.handler" method="GET">
                  
                <div class="form-label-group">
                    <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
                    <label for="username">Username</label>
                </div>
                
               
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>

    </body>
</html>
