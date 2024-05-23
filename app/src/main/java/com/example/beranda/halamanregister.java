package com.example.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class halamanregister extends AppCompatActivity {

    private EditText etEmail, etPassword, etUsername, etNotelepon;
    private Button btnRegister;
    private TextView login, syaratKetentuan;
    private FirebaseAuth auth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.emailRegEt);
        etUsername = findViewById(R.id.usernameRegEt);
        etNotelepon = findViewById(R.id.NoTeleponRegEt);
        etPassword = findViewById(R.id.passwordEtReg);
        btnRegister = findViewById(R.id.btnRegister);
        login = findViewById(R.id.logintv);
        syaratKetentuan = findViewById(R.id.sk2);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(); // Inisialisasi variabel database

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = etEmail.getText().toString().trim();
                final String pass = etPassword.getText().toString().trim();
                final String username = etUsername.getText().toString().trim();
                final String notelepon = etNotelepon.getText().toString().trim();

                if (user.isEmpty()) {
                    etEmail.setError("Email Tidak Boleh Kosong ");
                    return;
                }
                if (pass.isEmpty()) {
                    etPassword.setError("Password Tidak Boleh Kosong");
                    return;
                }

                auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registrasi berhasil, simpan data pengguna ke Realtime Database
                            DatabaseReference userRef = database.getReference("users").child(auth.getCurrentUser().getUid());
                            userRef.child("email").setValue(user);
                            userRef.child("username").setValue(username);
                            userRef.child("notelepon").setValue(notelepon);
                            // Anda dapat menambahkan item data lainnya sesuai kebutuhan, misalnya nama pengguna, dll.

                            Toast.makeText(getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();
                            Intent register = new Intent(getApplicationContext(), halamanlogin.class);
                            startActivity(register);
                        } else {
                            Toast.makeText(getApplicationContext(), "Register gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        syaratKetentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skIntent = new Intent(halamanregister.this, SyaratKetentuanActivity.class);
                startActivity(skIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), halamanlogin.class);
                startActivity(login);
            }
        });
    }
}
