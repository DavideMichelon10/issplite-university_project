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
public class RisultatoNonEseguito {
    private int idRisultato;
    private String erogationDateVisit;
    private String examName;
    private String pazienteName;    
    private String pazienteSurname;

    public int getIdRisultato() {
        return idRisultato;
    }

    public void setIdRisultato(int idRisultato) {
        this.idRisultato = idRisultato;
    }

    public String getErogationDateVisit() {
        return erogationDateVisit;
    }

    public void setErogationDateVisit(String erogationDateVisit) {
        this.erogationDateVisit = erogationDateVisit;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getPazienteName() {
        return pazienteName;
    }

    public void setPazienteName(String pazienteName) {
        this.pazienteName = pazienteName;
    }

    public String getPazienteSurname() {
        return pazienteSurname;
    }

    public void setPazienteSurname(String pazienteSurname) {
        this.pazienteSurname = pazienteSurname;
    }

    @Override
    public String toString() {
        return "RisultatoNonEseguito{" + "idRisultato=" + idRisultato + ", erogationDateVisit=" + erogationDateVisit + ", examName=" + examName + ", pazienteName=" + pazienteName + ", pazienteSurname=" + pazienteSurname + '}';
    }
}
