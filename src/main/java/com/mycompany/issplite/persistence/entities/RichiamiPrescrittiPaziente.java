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
public class RichiamiPrescrittiPaziente {
    
    private String dataPrescrizione;
    private String motivazione;
    private String nomeEsame;

    public String getDataPrescrizione() {
        return dataPrescrizione;
    }

    public void setDataPrescrizione(String dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getNomeEsame() {
        return nomeEsame;
    }

    public void setNomeEsame(String nomeEsame) {
        this.nomeEsame = nomeEsame;
    }
    
    
    
}
