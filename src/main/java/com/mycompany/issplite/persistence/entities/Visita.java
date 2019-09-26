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
public class Visita {
    private String dataVisita;
    private int costoVisita;
    private boolean ticketPagato;

    public String getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(String dataVisita) {
        this.dataVisita = dataVisita;
    }

    public int getCostoVisita() {
        return costoVisita;
    }

    public void setCostoVisita(int costoVisita) {
        this.costoVisita = costoVisita;
    }

    public boolean isTicketPagato() {
        return ticketPagato;
    }

    public void setTicketPagato(boolean ticketPagato) {
        this.ticketPagato = ticketPagato;
    }
    
    
    
}
