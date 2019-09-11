package com.mycompany.issplite.persistence.entities;

import java.util.Date;

public class Paziente {
    
    private String idPaziente;
    private String name;  
    private String surname;
    private String birthPlace;
    private String ssn;
    private Date birthDate;
    private boolean sex; // 1 Femmina, 0 maschio
    private String email;
    private String provincia;
    private String password;
    private String photoPath;
    private Medico medico;

    public String getIdPaziente() {
        return idPaziente;
    }

    public void setIdPaziente(String idPaziente) {
        this.idPaziente = idPaziente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    
    @Override
    public String toString() {
        return "Paziente{" + "idPaziente=" + idPaziente + ", name=" + name + ", surname=" + surname + ", birthPlace=" + birthPlace + ", ssn=" + ssn + ", birthDate=" + birthDate + ", sex=" + sex + ", email=" + email + ", provincia=" + provincia + ", password=" + password + ", photoPath=" + photoPath + ", medico=" + medico + '}';
    }
 
}
