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
public class PazienteUltimiEsamiFarmaci {
    private String idpaziente;
    private String ssn;
    private String nameFarmaco;
    private String erogationDateFarmaco;
    private String nameEsame;
    private String erogationDateEsame;

    public String getIdpaziente() {
        return idpaziente;
    }

    public void setIdpaziente(String idpaziente) {
        this.idpaziente = idpaziente;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getNameFarmaco() {
        return nameFarmaco;
    }

    public void setNameFarmaco(String nameFarmaco) {
        this.nameFarmaco = nameFarmaco;
    }

    public String getErogationDateFarmaco() {
        return erogationDateFarmaco;
    }

    public void setErogationDateFarmaco(String erogationDateFarmaco) {
        this.erogationDateFarmaco = erogationDateFarmaco;
    }

    public String getNameEsame() {
        return nameEsame;
    }

    public void setNameEsame(String nameEsame) {
        this.nameEsame = nameEsame;
    }

    public String getErogationDateEsame() {
        return erogationDateEsame;
    }

    public void setErogationDateEsame(String erogationDateEsame) {
        this.erogationDateEsame = erogationDateEsame;
    }
    
    @Override
    public String toString() {
        return "PazienteUltimiEsamiFarmaci{" + "idpaziente=" + idpaziente + ", ssn=" + ssn + ", nameFarmaco=" + nameFarmaco + ", erogationDateFarmaco=" + erogationDateFarmaco + ", nameEsame=" + nameEsame + ", erogationDateEsame=" + erogationDateEsame + '}';
    }
}
