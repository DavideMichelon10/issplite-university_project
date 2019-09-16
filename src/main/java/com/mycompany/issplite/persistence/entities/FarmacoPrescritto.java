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
public class FarmacoPrescritto extends Farmaco {
    private String prescriptionDate;
    private boolean isPagato;

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public boolean isIsPagato() {
        return isPagato;
    }

    public void setIsPagato(boolean isPagato) {
        this.isPagato = isPagato;
    }

    @Override
    public String toString() {
        return "FarmacoPrescritto{" + "prescriptionDate=" + prescriptionDate + ", isPagato=" + isPagato + '}';
    }
}
