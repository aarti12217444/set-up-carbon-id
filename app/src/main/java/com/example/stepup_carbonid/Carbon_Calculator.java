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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Carbon_Calculator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Carbon_Calculator extends Fragment {
    Spinner select;
    EditText watt,duration;
    Button calculate;
    ArrayList<String> list=new ArrayList<>();
    TextView result;
    public Carbon_Calculator() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_carbon__calculator, container, false);
        //View v=inflater.inflate(R.layout.fragment_home_fragment, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        select=v.findViewById(R.id.select);
        watt=v.findViewById(R.id.watt);
        duration=v.findViewById(R.id.duration);
        calculate=v.findViewById(R.id.cal);
        result=v.findViewById(R.id.result);
        list.add("--select--");list.add("Fan");list.add("AC");list.add("Heater");list.add("Cooler");
        ArrayAdapter ad=new ArrayAdapter(getActivity(),R.layout.selected,list);
        ad.setDropDownViewResource(R.layout.dropdown);
        select.setAdapter(ad);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d=Integer.parseInt(duration.getText().toString());
                int w=Integer.parseInt(watt.getText().toString());
                float res=0.82f*d*w;
                result.setText("You Saved "+String.valueOf(res)+"gm CO₂");
                //Toast.makeText(getActivity(),String.valueOf(res), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}