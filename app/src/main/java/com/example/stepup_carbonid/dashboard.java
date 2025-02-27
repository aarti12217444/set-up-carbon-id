package com.example.stepup_carbonid;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        replacefragment(new home_fragment());
        nav=findViewById(R.id.navbar);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid=item.getItemId();
                if(itemid==R.id.home){
                    replacefragment(new home_fragment());
                }
                else if(itemid==R.id.stepcounter){
                    replacefragment(new Carbon_Calculator());
                }
                return false;
            }
        });
    }
    public void replacefragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}