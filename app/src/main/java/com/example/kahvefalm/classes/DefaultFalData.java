package com.example.kahvefalm.classes;

import android.net.Uri;

import com.example.kahvefalm.enums.FalTipi;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;


public class DefaultFalData implements Serializable {

    public AccountProfile accountProfile;
    public ArrayList<Uri> imageDataURL;
    public String message;
    public String falTipi;

    public DefaultFalData(AccountProfile accountProfile, ArrayList<Uri> imageDataURL, String message, String falTipi) {
        this.accountProfile = accountProfile;
        this.imageDataURL = imageDataURL;
        this.message = message;
        this.falTipi = falTipi;
    }
}
