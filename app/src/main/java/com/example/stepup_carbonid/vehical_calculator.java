package com.example.stepup_carbonid;

import static android.app.PendingIntent.getActivity;

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

public class vehical_calculator extends Fragment {
    Spinner transport,fuel;
    EditText dist;
    Button calculate;
    TextView result;
    ArrayList<String> transList=new ArrayList<>();
    ArrayList<String> fuelList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_vehical_calculator, container, false);
        //View v=inflater.inflate(R.layout.fragment_home_fragment, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        transport=v.findViewById(R.id.transport);
        fuel=v.findViewById(R.id.fuel);
        dist=v.findViewById(R.id.dist);
        calculate=v.findViewById(R.id.cal);
        result=v.findViewById(R.id.result);
        transList.add("--select--");
        transList.add("Bike");
        transList.add("Car");
        transList.add("Truck/Bus");
        transList.add("Train");
        transList.add("Aeroplane");
        fuelList.add("--select--");
        fuelList.add("Petrol");
        fuelList.add("Diesel");
        fuelList.add("CNG");
        fuelList.add("EV");
        ArrayAdapter ad=new ArrayAdapter(getActivity(),R.layout.selected,transList);
        ad.setDropDownViewResource(R.layout.dropdown);
        transport.setAdapter(ad);

        ArrayAdapter ad1=new ArrayAdapter(getActivity(),R.layout.selected,fuelList);
        ad.setDropDownViewResource(R.layout.dropdown);
        fuel.setAdapter(ad1);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = transport.getSelectedItem().toString();
                String selectedItem2 = fuel.getSelectedItem().toString();
                float d= Float.parseFloat(dist.getText().toString());
                float res=0;
                if (selectedItem.equals("--select--") || selectedItem2.equals("--select--")) {
                    Toast.makeText(getActivity(), "Please select both transport and fuel types", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Bike") && selectedItem2.equals("Petrol")) {
                    res=75*d;
                    //Toast.makeText(getActivity(), "Bike running on Petrol", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Bike") && selectedItem2.equals("EV")) {
                    //Toast.makeText(getActivity(), "Electric Bike selected", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Car") && selectedItem2.equals("Petrol")) {
                    res=180*d;
                    //Toast.makeText(getActivity(), "Car with Petrol", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Car") && selectedItem2.equals("Diesel")) {
                    res=130*d;
                    //Toast.makeText(getActivity(), "Car with Diesel", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Car") && selectedItem2.equals("CNG")) {
                    res=100*d;
                    // Toast.makeText(getActivity(), "CNG Car", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Car") && selectedItem2.equals("EV")) {
                    res=50*d;
                    //Toast.makeText(getActivity(), "Electric Car", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Truck/Bus") && selectedItem2.equals("Diesel")) {
                    //res=600*d;
                    //Toast.makeText(getActivity(), "Heavy vehicle on Diesel", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Train") && selectedItem2.equals("Diesel")) {
                    res=600*d;
                    //Toast.makeText(getActivity(), "Diesel Train", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Train") && selectedItem2.equals("EV")) {
                    res=500*d;
                    //Toast.makeText(getActivity(), "Electric Train", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Aeroplane") && selectedItem2.equals("Petrol")) {
                    res=300*d;
                    //Toast.makeText(getActivity(), "Aeroplane using Aviation fuel (Petrol)", Toast.LENGTH_SHORT).show();
                } else if (selectedItem.equals("Train") ) {
                    res=40*d;
                    //Toast.makeText(getActivity(), "Aeroplane using Aviation fuel (Petrol)", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Invalid combination", Toast.LENGTH_SHORT).show();
                }
                result.setText("You Saved "+String.valueOf(res)+"gm CO₂");
            }
        });
        return v;
    }
}