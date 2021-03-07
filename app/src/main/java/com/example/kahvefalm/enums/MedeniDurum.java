package com.example.kahvefalm.enums;

public enum MedeniDurum {

    Bekar("Bekar"),Nisanli("Nişanlı"),Evli("Evli"),Dul("Dul"),Ayrılmış("Ayrılmış"),Sevgilisi_Var("Sevgilisi var"),Sevgilisi_Yok("Sevgilisi Yok"),Diğer("Diğer");

    private String displayName;

    MedeniDurum(String s) {
        this.displayName = s;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
