package com.example.stepup_carbonid;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.core.Context;


public class home_fragment extends Fragment implements SensorEventListener {



    public home_fragment() {
        // Required empty public constructor
    }
    TextView stepcounter,stepdetector,footprintsave;
    public SensorManager sensorManager;
    public Sensor mstepcounter,mstepdetector;
    public boolean isCounterSensorPresent,isDetectorsensorPresent;
    int stepcount=0,stepdetect=0,previousstep=0;
    ProgressBar progressBar;
    Button savesteps;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home_fragment, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        footprintsave=v.findViewById(R.id.footprintsave);
        stepdetector=v.findViewById(R.id.stepdetector);
        progressBar=v.findViewById(R.id.progressbar);
        savesteps=v.findViewById(R.id.savesteps);

        sensorManager=(SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        savedata();
        //sensorManager=(SensorManager) ContextCompat.getSystemService(SENSOR_SERVICE);
        // Step Counter Sensor Check
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mstepcounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        } else {
            stepcounter.setText("Counter sensor is not present");
            isCounterSensorPresent = false;
        }

        // Step Detector Sensor Check
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mstepdetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isDetectorsensorPresent = true;
        } else {
            stepdetector.setText("Detector sensor is not present");
            isDetectorsensorPresent = false;
        }
        savesteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stepdetector.getText().toString()=="0"){
                    Toast.makeText(getActivity(), "step count not available", Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences sp=getContext().getSharedPreferences("first",MODE_PRIVATE);
                    float cal=((stepdetect*0.75f)/1000)*100;
                    if(sp.contains("stepcount")){
                        float f=sp.getFloat("stepcount",0);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putFloat("stepcount", cal+f);

                        ed.apply();
                        footprintsave.setText("Your previous footprint saved : "+String.valueOf(cal+f));
                        Toast.makeText(getActivity(), "saved ", Toast.LENGTH_SHORT).show();
                        stepdetect=0;
                        stepdetector.setText("0");
                        progressBar.setProgress(stepdetect);
                    }
                    //SharedPreferences sp = getContext().getSharedPreferences("first", MODE_PRIVATE);
                    else {
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putFloat("stepcount", cal);
                        ed.putInt("footsteps",stepdetect);
                        ed.apply();
                        footprintsave.setText("Your previous footprint saved : "+String.valueOf(cal));
                        Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                        stepdetect=0;
                        stepdetector.setText("0");
                    }

                }
            }
        });
        return v;
    }
public void savedata(){
    SharedPreferences sp=getContext().getSharedPreferences("first",MODE_PRIVATE);
    float cal=((stepdetect*0.75f)/1000)*100;
    if(sp.contains("stepcount")){
        float f=sp.getFloat("stepcount",0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putFloat("stepcount", cal+f);

        ed.apply();
        footprintsave.setText("Your previous footprint saved : "+String.valueOf(cal+f)+"gm CO₂");
        //Toast.makeText(getActivity(), "saved ", Toast.LENGTH_SHORT).show();
    }
}
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mstepcounter) {
            //stepcount = (int) event.values[0];
            //stepcounter.setText(String.valueOf(stepcount));
        } else if (event.sensor == mstepdetector) {
            stepdetect = (int) (stepdetect + event.values[0]);
            stepdetector.setText(String.valueOf(stepdetect));
            progressBar.setProgress(stepdetect);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onResume() {
        super.onResume();
        // Register sensor listeners when the fragment is visible
        if (isCounterSensorPresent) {
            sensorManager.registerListener(this, mstepcounter, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (isDetectorsensorPresent) {
            sensorManager.registerListener(this, mstepdetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister sensor listeners when the fragment is paused
        if (isCounterSensorPresent) {
            sensorManager.unregisterListener(this, mstepcounter);
        }
        if (isDetectorsensorPresent) {
            sensorManager.unregisterListener(this, mstepdetector);
        }
    }

}