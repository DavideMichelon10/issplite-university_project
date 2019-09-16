/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.issplite.persistence.entities;

/**
 *
 * @author Davide
 */
public class EsamePrescritto extends Esame {
    private String erogationDate;
    private String risultato;
    private boolean isPagato;

    public String getErogationDate() {
        return erogationDate;
    }

    public void setErogationDate(String erogationDate) {
        this.erogationDate = erogationDate;
    }

    public String getRisultato() {
        return risultato;
    }

    public void setRisultato(String risultato) {
        this.risultato = risultato;
    }

    public boolean isIsPagato() {
        return isPagato;
    }

    public void setIsPagato(boolean isPagato) {
        this.isPagato = isPagato;
    }

    @Override
    public String toString() {
        return "EsamePrescritto{" + "erogationDate=" + erogationDate + ", risultato=" + risultato + ", isPagato=" + isPagato + '}';
    }
    
}
