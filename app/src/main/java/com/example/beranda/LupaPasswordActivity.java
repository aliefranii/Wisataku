package com.example.beranda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LupaPasswordActivity extends AppCompatActivity {
    private Button batalButton;
    private EditText etPasswordBaru, etKonfirmasiPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        // Inisialisasi EditText
        etPasswordBaru = findViewById(R.id.Etpass_baru);
        etKonfirmasiPassword = findViewById(R.id.ETkonfirmasi_pass);

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Inisialisasi tombol batal
        batalButton = findViewById(R.id.batal_passBaru);
        batalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Menggunakan onBackPressed untuk respons cepat
            }
        });

        Button btnKonfirmasi = findViewById(R.id.btnLogin);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordBaru = etPasswordBaru.getText().toString().trim();
                String konfirmasiPassword = etKonfirmasiPassword.getText().toString().trim();

                // Validasi input
                if (passwordBaru.isEmpty()) {
                    etPasswordBaru.setError("Password baru tidak boleh kosong");
                    return;
                }

                if (!passwordBaru.equals(konfirmasiPassword)) {
                    etKonfirmasiPassword.setError("Password konfirmasi tidak sesuai");
                    return;
                }

                // Mengubah kata sandi
                updatePassword(passwordBaru);
            }
        });
    }

    private void updatePassword(String passwordBaru) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.updatePassword(passwordBaru)
                    .addOnCompleteListener(task -> {
                        Intent intent = new Intent(LupaPasswordActivity.this, SplashScreenBerhasilActivity.class);
                        startActivity(intent);
                        finish(); // Menutup aktivitas saat ini (LupaPasswordActivity)
                    });
        } else {
            // Jika user null, mungkin terjadi masalah saat pengambilan user yang sedang login
            Toast.makeText(LupaPasswordActivity.this, "Tidak dapat mengambil pengguna yang sedang login", Toast.LENGTH_SHORT).show();
        }
    }


    private void kembaliKeHalamanProfil() {
        // Kembali ke halaman profil
        Intent intent = new Intent(LupaPasswordActivity.this, Profil.class);
        startActivity(intent);
      // Menutup aktivitas saat ini (LupaPasswordActivity)
    }
}
