package com.example.stepup_carbonid;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Electric_calculator extends AppCompatActivity {

    Spinner transport, fuel;
    EditText watt;   // duration hata diya (kyunki XML me nahi hai)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_electric_calculator);

        transport = findViewById(R.id.transport);
        fuel = findViewById(R.id.fuel);
        watt = findViewById(R.id.watt);
    }
}