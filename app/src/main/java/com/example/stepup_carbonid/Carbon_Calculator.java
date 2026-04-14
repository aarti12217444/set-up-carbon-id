package com.example.stepup_carbonid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Carbon_Calculator extends Fragment {

    Spinner select;
    EditText watt, duration;
    Button calculate;
    TextView result;

    ArrayList<String> list = new ArrayList<>();

    public Carbon_Calculator() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_carbon__calculator, container, false);

        // Safe check
        if (getActivity() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        select = v.findViewById(R.id.select);
        watt = v.findViewById(R.id.watt);
        duration = v.findViewById(R.id.duration);
        calculate = v.findViewById(R.id.cal);
        result = v.findViewById(R.id.result);

        // Spinner data
        list.add("--Select--");
        list.add("Fan");
        list.add("AC");
        list.add("Heater");
        list.add("Cooler");

        ArrayAdapter<String> ad = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                list
        );
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select.setAdapter(ad);

        calculate.setOnClickListener(view -> {

            String durStr = duration.getText().toString().trim();
            String wattStr = watt.getText().toString().trim();

            // 🔥 Input validation
            if (durStr.isEmpty() || wattStr.isEmpty()) {
                Toast.makeText(getActivity(), "Enter all values", Toast.LENGTH_SHORT).show();
                return;
            }

            int d = Integer.parseInt(durStr);
            int w = Integer.parseInt(wattStr);

            if (d <= 0 || w <= 0) {
                Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
                return;
            }

            float res = 0.82f * d * w;

            result.setText("You Saved " + res + " gm CO₂");
        });

        return v;
    }
}