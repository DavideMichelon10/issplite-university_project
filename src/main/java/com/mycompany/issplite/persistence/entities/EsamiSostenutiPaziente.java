/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.entities;

/**
 *
 * @author Aster
 */
public class EsamiSostenutiPaziente {    
    private String dataVisita;
    private String dataEsecuzione;
    private String nomeEsame;
    private boolean pagato;
    private String dataRisultato;
    private String idRisultato;

    public String getIdRisultato() {
        return idRisultato;
    }

    public void setIdRisultato(String idRisultato) {
        this.idRisultato = idRisultato;
    }    

    public String getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(String dataVisita) {
        this.dataVisita = dataVisita;
    }

    public String getDataEsecuzione() {
        return dataEsecuzione;
    }

    public void setDataEsecuzione(String dataEsecuzione) {
        this.dataEsecuzione = dataEsecuzione;
    }

    public String getNomeEsame() {
        return nomeEsame;
    }

    public void setNomeEsame(String nomeEsame) {
        this.nomeEsame = nomeEsame;
    }

    public boolean isPagato() {
        return pagato;
    }

    public void setPagato(boolean pagato) {
        this.pagato = pagato;
    }    
    
    public String getDataRisultato() {
        return dataRisultato;
    }

    public void setDataRisultato(String dataRisultato) {
        this.dataRisultato = dataRisultato;
    }

    @Override
    public String toString() {
        return "EsamiSostenutiPaziente{" + "dataVisita=" + dataVisita + ", dataEsecuzione=" + dataEsecuzione + ", nomeEsame=" + nomeEsame + ", pagato=" + pagato + ", dataRisultato=" + dataRisultato + '}';
    }
    
    
}
