package com.example.stepup_carbonid;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodEmissionFragment extends Fragment {

    Spinner foodTypeSpinner, foodItemSpinner, quantitySpinner;
    Button calculateBtn;
    TextView resultText;

    ArrayList<String> foodTypes = new ArrayList<>();
    ArrayList<String> vegItems = new ArrayList<>();
    ArrayList<String> nonVegItems = new ArrayList<>();
    ArrayList<String> quantityList = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_food_items, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initialize views
        foodTypeSpinner = v.findViewById(R.id.food_type_spinner);
        foodItemSpinner = v.findViewById(R.id.food_item_spinner);
        quantitySpinner = v.findViewById(R.id.quantity);  // Spinner, not EditText
        calculateBtn = v.findViewById(R.id.cal);
        resultText = v.findViewById(R.id.result);

        // Populate lists
        foodTypes.add("--select--");
        foodTypes.add("Vegetarian");
        foodTypes.add("Non-Vegetarian");

        vegItems.add("--select--");
        vegItems.add("Rice");
        vegItems.add("Vegetables");
        vegItems.add("Lentils");

        nonVegItems.add("--select--");
        nonVegItems.add("Chicken");
        nonVegItems.add("Mutton");
        nonVegItems.add("Fish");

        quantityList.add("100g");
        quantityList.add("200g");
        quantityList.add("500g");
        quantityList.add("1kg");

        // Setup adapters
        ArrayAdapter<String> foodTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.selected, foodTypes);
        foodTypeAdapter.setDropDownViewResource(R.layout.dropdown);
        foodTypeSpinner.setAdapter(foodTypeAdapter);

        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(getActivity(), R.layout.selected, quantityList);
        quantityAdapter.setDropDownViewResource(R.layout.dropdown);
        quantitySpinner.setAdapter(quantityAdapter);

        // Food type spinner listener to update food items dynamically
        foodTypeSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = foodTypes.get(i);
                ArrayAdapter<String> itemAdapter;

                if (selectedType.equals("Vegetarian")) {
                    itemAdapter = new ArrayAdapter<>(getActivity(), R.layout.selected, vegItems);
                } else if (selectedType.equals("Non-Vegetarian")) {
                    itemAdapter = new ArrayAdapter<>(getActivity(), R.layout.selected, nonVegItems);
                } else {
                    itemAdapter = new ArrayAdapter<>(getActivity(), R.layout.selected, new ArrayList<>());
                }

                itemAdapter.setDropDownViewResource(R.layout.dropdown);
                foodItemSpinner.setAdapter(itemAdapter);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {}
        });

        // Calculate button logic
        calculateBtn.setOnClickListener(view -> {
            String type = foodTypeSpinner.getSelectedItem().toString();
            String item = foodItemSpinner.getSelectedItem().toString();
            String quantityInput = quantitySpinner.getSelectedItem().toString();

            if (type.equals("--select--") || item.equals("--select--") || quantityInput.equals("--select--")) {
                Toast.makeText(getActivity(), "Please select all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            float factor = 0f;

            // Sample CO₂ values in grams per 100g (example values)
            switch (item) {
                case "Rice":
                    factor = 270;
                    break;
                case "Vegetables":
                    factor = 100;
                    break;
                case "Lentils":
                    factor = 200;
                    break;
                case "Chicken":
                    factor = 650;
                    break;
                case "Mutton":
                    factor = 1200;
                    break;
                case "Fish":
                    factor = 500;
                    break;
            }

            // Convert quantity to multiplier
            float multiplier = 1;
            switch (quantityInput) {
                case "100g":
                    multiplier = 1;
                    break;
                case "200g":
                    multiplier = 2;
                    break;
                case "500g":
                    multiplier = 5;
                    break;
                case "1kg":
                    multiplier = 10;
                    break;
            }

            float result = factor * multiplier;
            resultText.setText("Carbon Emission: " + result + " gm CO₂");
        });

        return v;
    }
}
