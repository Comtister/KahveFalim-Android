package com.example.kahvefalm.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kahvefalm.R;
import com.google.android.material.appbar.MaterialToolbar;

public class HakkindaActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkinda);

        toolbar = (MaterialToolbar)findViewById(R.id.toolbarHakkinda);
        toolbar.setTitle("Yazılım Hakkında");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
