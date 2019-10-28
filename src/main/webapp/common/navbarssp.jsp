<style>
    .bg-custom {
        background-color: #087a9c !important;
    }

    .testoMenu{
        color: whitesmoke !important;
    }

    .testoMenu:hover span{
        font-weight:bold;
    }
</style>
<nav class="navbar navbar-expand-lg navbar-dark bg-custom">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">                
            <a class="navbar-brand testoMenu" href="../sspi/sspi.html">iSSPLite</a>
            </li>
            <li class="nav-item">
                <a class="navbar-text testoMenu" style="text-decoration: none" href="${pageContext.request.contextPath}/sspi/sspi.html"><span>Stampa report</span> &nbsp;</a>
            </li>
            <li class="nav-item">
                <a class="navbar-text testoMenu" style="text-decoration: none; " href="${pageContext.request.contextPath}/sspi/richiami.html"><span>Crea richiamo</span> &nbsp;</a>
            </li>

        </ul>
        <ul class="navbar-nav ml-auto">  
            <c:set var = "ssp" scope = "session" value = "${ssp}"/>
            <li class="nav-item"><a class="navbar-text testoMenu">Benvenuto <b><c:out value="${ssp.provincia}"/></b></a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/logout.handler?user=${ssp.idSSP}" class="nav-link testoMenu">Log out</a></li>
        </ul>
    </div>
</nav>