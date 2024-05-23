package com.example.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.beranda.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment1 homeFragment1 = new HomeFragment1();
    PencarianFragment1 pencarianFragment1 = new PencarianFragment1();
    Profil profil = new Profil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment1).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homeFragment1).commit();
                        return true;
                    case R.id.pencarian:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,pencarianFragment1).commit();
                        return true;
                    case R.id.profil:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profil).commit();
                        return true;
                }

                return false;
            }
        });
    }
}