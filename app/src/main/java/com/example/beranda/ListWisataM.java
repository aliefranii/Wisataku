package com.example.beranda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ListWisataM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listwisata_mitra);

        // Find the backWisataku button by its ID
        ImageView backWisatakuButton = findViewById(R.id.backWisataku);

        // Set an OnClickListener to the backWisataku button
        backWisatakuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to HalamanMitraActivity
                Intent intent = new Intent(ListWisataM.this, HalamanMitraActivity.class);
                startActivity(intent);
                // Optional: finish the current activity if you want to remove it from the back stack
                finish();
            }
        });
    }
}
