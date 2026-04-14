package com.example.stepup_carbonid;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class dashboard extends AppCompatActivity {

    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nav = findViewById(R.id.navbar);

        // Default fragment
        replaceFragment(new home_fragment());

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                int itemid = item.getItemId();

                if (itemid == R.id.home) {
                    selectedFragment = new home_fragment();
                } else if (itemid == R.id.stepcounter) {
                    selectedFragment = new Carbon_Calculator();
                } else if (itemid == R.id.vehicle) {
                    selectedFragment = new vehical_calculator();
                } else if (itemid == R.id.food) {
                    selectedFragment = new FoodEmissionFragment();
                }
                else if (itemid == R.id.chat) {
                    selectedFragment = new ChatFragment();
                }

                if (selectedFragment != null) {
                    replaceFragment(selectedFragment);
                }

                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commitAllowingStateLoss(); // safer
    }
}