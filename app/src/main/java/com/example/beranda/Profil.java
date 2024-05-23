package com.example.beranda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profil extends Fragment {

    // Parameter arguments
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Parameters
    private String mParam1;
    private String mParam2;

    // UI elements
    private Button btnEditProfil, btnLogout, btnMitra, btnLupaPassword;
    private TextView usernameTextView;

    // Firebase Authentication and Realtime Database reference
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public Profil() {
        // Required empty public constructor
    }

    public static Profil newInstance(String param1, String param2) {
        Profil fragment = new Profil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Initialize FirebaseAuth and DatabaseReference
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);

        // Initialize UI elements
        btnEditProfil = rootView.findViewById(R.id.btnEditProfil);
        btnLogout = rootView.findViewById(R.id.button_logout);
        btnLupaPassword = rootView.findViewById(R.id.btnlupapassword);
        btnMitra = rootView.findViewById(R.id.button3);
        usernameTextView = rootView.findViewById(R.id.username1);

        // Load user data from Firebase
        String currentUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserRef = mDatabase.child("users").child(currentUserId);

        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue(String.class);
                if (username != null) {
                    usernameTextView.setText(username);
                } else {
                    usernameTextView.setText("Username Tidak Ditemukan");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });

        // Set OnClickListener for Edit Profile button
        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfilIntent = new Intent(requireContext(), EditProfil.class);
                startActivity(editProfilIntent);
            }
        });

        // Set OnClickListener for lupa password button
        btnLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfilIntent = new Intent(requireContext(), LupaPasswordActivity.class);
                startActivity(editProfilIntent);
            }
        });


        // Set OnClickListener for Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loginIntent = new Intent(requireContext(), halamanlogin.class);
                startActivity(loginIntent);
                requireActivity().finish(); // Close the current activity
            }
        });

        // Set OnClickListener for Mitra button
        btnMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    final String uid = currentUser.getUid();
                    mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.hasChild("mitra")) {
                                // Jika pengguna memiliki kunci mitra dalam data pengguna
                                Intent intent = new Intent(requireContext(), HalamanMitraActivity.class);
                                startActivity(intent);
                            } else {
                                // Jika pengguna tidak memiliki kunci mitra dalam data pengguna
                                Intent intent = new Intent(requireContext(), DaftarMitraActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Penanganan kesalahan jika pembatalan terjadi
                        }
                    });
                } else {
                    // Jika pengguna tidak login
                    // Mungkin tampilkan pesan atau arahkan pengguna untuk login
                }
            }
        });

        return rootView;
    }
}
