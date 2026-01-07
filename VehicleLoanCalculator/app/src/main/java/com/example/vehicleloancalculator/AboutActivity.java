package com.example.vehicleloancalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView tvGithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvGithub = findViewById(R.id.tvGithub);
        tvGithub.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
