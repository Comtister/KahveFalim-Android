package com.example.kahvefalm.enums;

public enum MedeniDurum {

    Bekar("Bekar"),Nisanli("Nişanlı"),Evli("Evli"),Dul("Dul"),Ayri("Ayri-Boşanmış"),BelirtmekIstemiyorum("Belirtmek İstemiyorum");

    public String durum;

    MedeniDurum(String durum){

        this.durum = durum;

    }


}
