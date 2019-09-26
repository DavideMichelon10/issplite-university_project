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
public class RicettePrescrittePaziente {
    
    private String dataVisita;
    private String dataPrescrizione;
    private boolean ticketPagato;
    private String nomeFarmaco;

    public String getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(String dataVisita) {
        this.dataVisita = dataVisita;
    }

    public String getDataPrescrizione() {
        return dataPrescrizione;
    }

    public void setDataPrescrizione(String dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    public boolean isTicketPagato() {
        return ticketPagato;
    }

    public void setTicketPagato(boolean ticketPagato) {
        this.ticketPagato = ticketPagato;
    }

    public String getNomeFarmaco() {
        return nomeFarmaco;
    }

    public void setNomeFarmaco(String nomeFarmaco) {
        this.nomeFarmaco = nomeFarmaco;
    }
    
    
    
}
