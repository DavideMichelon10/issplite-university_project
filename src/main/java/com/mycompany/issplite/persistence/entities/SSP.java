package com.mycompany.issplite.persistence.entities;

public class SSP {
    
    private String idSSP;
    private String provincia;
    private String password;

    public String getIdSSP() {
        return idSSP;
    }

    public void setIdSSP(String idSSP) {
        this.idSSP = idSSP;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    @Override
    public String toString() {
        return "SSP{" + "idSSP=" + idSSP + ", provincia=" + provincia + ", password=" + password + '}';
    }
}
