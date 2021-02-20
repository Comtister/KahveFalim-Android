package com.example.kahvefalm.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kahvefalm.R;
import com.example.kahvefalm.view.FalGosterView;

public class FalGosterActivity extends AppCompatActivity {

    FalGosterView falGosterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        falGosterView = new FalGosterView(this);
        falGosterView.initViews();
        setContentView(falGosterView.getRootView());
    }
}
