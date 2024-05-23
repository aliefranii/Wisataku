package com.example.beranda;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenInputActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_TIMEOUT = 2000; // Durasi splash screen (2 detik)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashcreen_input_wisata); // Mengatur layout untuk splash screen

        // Handler untuk menunda pemanggilan finish() setelah 2 detik
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Memulai aktivitas HalamanMitraActivity setelah splash screen selesai
                Intent intent = new Intent(SplashScreenInputActivity.this, HalamanMitraActivity.class);
                startActivity(intent);
                finish(); // Menutup aktivitas SplashScreenInputActivity
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}
