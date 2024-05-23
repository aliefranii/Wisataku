package com.example.beranda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaftarMitraActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet_daftar_mitra);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button btnDaftarMitra = findViewById(R.id.btnDaftar_mitra);
        Button btnBatalMitra = findViewById(R.id.btnBatal_mitra);

        btnDaftarMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahkanMitraKeUser();
            }
        });

        btnBatalMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void tambahkanMitraKeUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            final String uid = currentUser.getUid();
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dataSnapshot.getRef().child("mitra").setValue(1)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Tampilkan splash screen
                                            Intent intent = new Intent(DaftarMitraActivity.this, SplashScreenMitraActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(DaftarMitraActivity.this, "Gagal menambahkan mitra.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(DaftarMitraActivity.this, "Data pengguna tidak ditemukan.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DaftarMitraActivity.this, "Operasi dibatalkan: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(DaftarMitraActivity.this, "Silakan login terlebih dahulu.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DaftarMitraActivity.this, halamanlogin.class);
            startActivity(intent);
        }
    }
}
