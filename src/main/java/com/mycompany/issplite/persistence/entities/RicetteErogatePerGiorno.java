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
public class RicetteErogatePerGiorno {
    private String medicoName;
    private String medicoSurname;
    private String farmacoName;
    private String pazienteSSN;
    private int ticket;
    private String date;

    /**
     * @return the medicoName
     */
    public String getMedicoName() {
        return medicoName;
    }

    /**
     * @param medicoName the medicoName to set
     */
    public void setMedicoName(String medicoName) {
        this.medicoName = medicoName;
    }

    /**
     * @return the medicoSurname
     */
    public String getMedicoSurname() {
        return medicoSurname;
    }

    /**
     * @param medicoSurname the medicoSurname to set
     */
    public void setMedicoSurname(String medicoSurname) {
        this.medicoSurname = medicoSurname;
    }

    /**
     * @return the farmacoName
     */
    public String getFarmacoName() {
        return farmacoName;
    }

    /**
     * @param farmacoName the farmacoName to set
     */
    public void setFarmacoName(String farmacoName) {
        this.farmacoName = farmacoName;
    }

    /**
     * @return the pazienteSSN
     */
    public String getPazienteSSN() {
        return pazienteSSN;
    }

    /**
     * @param pazienteSSN the pazienteSSN to set
     */
    public void setPazienteSSN(String pazienteSSN) {
        this.pazienteSSN = pazienteSSN;
    }

    /**
     * @return the ticket
     */
    public int getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "RicetteErogatePerGiorno{" + "medicoName=" + medicoName + ", medicoSurname=" + medicoSurname + ", farmacoName=" + farmacoName + ", pazienteSSN=" + pazienteSSN + ", ticket=" + ticket + ", date=" + date + '}';
    }
}
