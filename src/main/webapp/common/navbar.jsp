<style>
    .bg-custom {
        background-color: #087a9c !important;
    }

    .testoMenu{
        color: whitesmoke !important;
    }
</style>
<nav class="navbar navbar-expand-lg navbar-dark bg-custom">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <%
                            if (session.getAttribute("paziente") != null) {%>                    
            <c:set var = "paziente" scope = "session" value = "${paziente}"/>
            <a class="navbar-brand" href="../pazienti/pazienti.html">iSSPLite</a>
            <%} else if (session.getAttribute("medico") != null) {%>                    
            <c:set var = "medico" scope = "session" value = "${medico}"/>
            <a class="navbar-brand testoMenu" href="../medici/medici.html">iSSPLite</a>
            <%} else if (session.getAttribute("ssp") != null) {%>                    
            <c:set var = "ssp" scope = "session" value = "${ssp}"/>
            <a class="navbar-brand testoMenu" href="../sspi/sspi.html">iSSPLite</a>
            <%} else {%>                    
            <a class="navbar-brand testoMenu" href="../index.html">iSSPLite</a>
            <%}%>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <%
                        if (session.getAttribute("paziente") != null) {%>                    
            <c:set var = "paziente" scope = "session" value = "${paziente}"/>
            <li class="nav-item"><a class="nav-link testoMenu">Benvenuto <b><c:out value="${paziente.name}"/> <c:out value="${paziente.surname}"/></b></a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/logout.handler?user=${paziente.idPaziente}" class="nav-link testoMenu">Log out</a></li>
                <%} %>
        </ul>
    </div>
</nav>