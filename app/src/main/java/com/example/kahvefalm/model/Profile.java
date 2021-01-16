package com.example.kahvefalm.model;

import com.example.kahvefalm.enums.Cinsiyet;
import com.example.kahvefalm.enums.MedeniDurum;

import java.io.Serializable;

public class Profile implements Serializable {

    private String name;
    private String mail;
    private String id;
    private Cinsiyet cinsiyet;
    private int yas;
    private MedeniDurum medeniDurum;

    public Profile(String name, String mail, String id) {
        this.name = name;
        this.mail = mail;
        this.id = id;


    }

    public Profile(String name, String mail, String id, Cinsiyet cinsiyet, int yas,MedeniDurum medeniDurum) {
        this.name = name;
        this.mail = mail;
        this.id = id;
        this.cinsiyet = cinsiyet;
        this.yas = yas;
        this.medeniDurum = medeniDurum;
    }



    public MedeniDurum getMedeniDurum() {
        return medeniDurum;
    }

    public void setMedeniDurum(MedeniDurum medeniDurum) {
        this.medeniDurum = medeniDurum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cinsiyet getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(Cinsiyet cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }
}
