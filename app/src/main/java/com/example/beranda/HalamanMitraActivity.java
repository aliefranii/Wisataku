package com.example.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HalamanMitraActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView usernameMitraTextView;
    private Button tambahWisataButton;
    private Button wisatakuButton;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_mitra);

        // Inisialisasi Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inisialisasi TextView untuk menampilkan username
        usernameMitraTextView = findViewById(R.id.usernameMitra);

        // Inisialisasi Button Tambah Wisata
        tambahWisataButton = findViewById(R.id.tambah_wisata);

        // Menambahkan event handler untuk tombol "Tambah Wisata"
        tambahWisataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat intent untuk membuka activity inputwisata
                Intent intent = new Intent(HalamanMitraActivity.this, inputwisata.class);
                startActivity(intent);
            }
        });

        // Inisialisasi Button Wisataku
//        wisatakuButton = findViewById(R.id.wisataku);
//
//        // Menambahkan event handler untuk tombol "Wisataku"
//        wisatakuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Membuat intent untuk membuka activity ListWisataM
//                Intent intent = new Intent(HalamanMitraActivity.this, ListWisataM.class);
//                startActivity(intent);
//            }
//        });

        // Inisialisasi Button Back to Home
        backToHomeButton = findViewById(R.id.Backtohome);

        // Menambahkan event handler untuk tombol "Kembali ke halaman utama"
        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat intent untuk membuka MainActivity
                Intent intent = new Intent(HalamanMitraActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Mendapatkan user yang sedang login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            getUsernameFromDatabase(userId);
        } else {
            // Pengguna belum login
        }
    }

    private void getUsernameFromDatabase(String userId) {
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("username").getValue(String.class);
                    usernameMitraTextView.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Terjadi kesalahan saat mengambil data dari database
            }
        });
    }
}
