
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
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

        <!--DATATABLE-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css">

        <title>Informativa Password</title>

        <style>
            .testo{
                font-size: 18px;
                text-align: justify;
            }

            .testoLista{
                font-size: 23px;
            }
            
            button{
                color: #087a9c !important;
            }
            
            a{
                color: #087a9c !important;
            }
        </style>
    </head>    
    <body>
        <header>
            <%@ include file="common/navbar.jsp" %>
        </header>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3"></div>
                <div class="col-sm-6">
                    <br>
                    <p class="testo">Per <b>iSSPLite</b> la privacy dei propri clienti è di primaria importanza. 
                        La presente Informativa sulla privacy definisce quali dati vengono raccolti e il modo in cui gli stessi vengono 
                        utilizzati, divulgati, trasferiti e/o archiviati dall'azienda.
                    </p>
                    <br>
                    <div id="accordion">

                        <!--PRIMO PUNTO NORMATIVA PRIVACY-->
                        <div class="card">
                            <div class="card-header" id="headingOne">
                                <h3 class="mb-0">
                                    <button class="btn btn-link collapsed testoLista" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                        <b>1.</b> Identità di iSSPLite
                                    </button>
                                </h3>
                            </div>

                            <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                                <div class="card-body">
                                    <p>In caso di domande relative alla presente politica di privacy è possibile contattarci utilizzando le informazioni riportate di seguito.</br></p>

                                    <a href="mailto:lorenzo.cavada@studenti.unitn.it">lorenzo.cavada@studenti.unitn.it</a><br>
                                    <a href="mailto:davide.michelon-1@studenti.unitn.it">davide.michelon-1@studenti.unitn.it</a>
                                </div>
                            </div>
                        </div>

                        <!--SECONDO PUNTO NORMATIVA PRIVACY-->
                        <div class="card">
                            <div class="card-header" id="headingTwo">
                                <h5 class="mb-0">
                                    <button class="btn btn-link collapsed testoLista" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                        <b>2.</b> Quali dati raccoglie l’azienda?
                                    </button>
                                </h5>
                            </div>
                            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                                <div class="card-body">                                     
                                    I dati presenti nel sito sono gestiti dall'ente sanitario nazionale.
                                    Sono forniti su concessione a iSSPLite che ha la possibilità di visualizzarli al fine di fornire un servizio di semplice accesso a tutti gli utenti registrati.
                                    iSSPLite non ha in alcun modo un accesso diretto ai dati. L'accesso agli stessi avviene attraverso prcedure e interfacce mirate al garantire in ogni istante la privacy dell'utente.
                                    Per eventuali maggiori informazioni fare <a href="http://www.salute.gov.it/portale/salute/p1_3.jsp?lingua=italiano&tema=Tu_e_il_Servizio_Sanitario_Nazionale">riferimento</a> direttamente al sito statale.
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header" id="headingThree">
                                <h5 class="mb-0">
                                    <button class="btn btn-link collapsed testoLista" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                        <b>3.</b> Dichiarazione dei Cookie
                                    </button>
                                </h5>
                            </div>
                            <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
                                <div class="card-body">
                                    Questo sito web utilizza i cookie. Utilizziamo i cookie per peremttere un'esperienza di navigazione migliore. <br>
                                    I cookie vengono salvati solo se l'utente seleziona la casella "<i>remember me</i>" in fase di login.<br>
                                    I cookie sono piccoli file di testo che possono essere utilizzati dai siti web per rendere più efficiente l'esperienza per l'utente.<br>
                                    La legge afferma che possiamo memorizzare i cookie sul suo dispositivo se sono strettamente necessari per il funzionamento di questo sito. <br>
                                    Per tutti gli altri tipi di cookie abbiamo bisogno del suo permesso.<br>
                                    Questo sito utilizza diversi tipi di cookie. Alcuni cookie sono collocate da servizi di terzi che compaiono sulle nostre pagine.</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </div>
    </body>
</html>
