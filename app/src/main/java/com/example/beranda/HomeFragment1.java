package com.example.beranda;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment1 extends Fragment {

    private RecyclerView recyclerView;

    private HomeAdapter homeAdapter;

    private String kabupatenId;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment1 newInstance(String param1, String param2) {
        HomeFragment1 fragment = new HomeFragment1();
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

        // Inisialisasi FirebaseAuth dan FirebaseUser
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home1, container, false);
        recyclerView = view.findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference wisataRef = FirebaseDatabase.getInstance().getReference().child("Wisata");
        Query query = wisataRef.orderByKey().limitToFirst(5);

        // Konfigurasi FirebaseRecyclerOptions
        FirebaseRecyclerOptions<ModelWisata> options = new FirebaseRecyclerOptions.Builder<ModelWisata>()
                .setQuery(query, ModelWisata.class)
                .build();

        // Inisialisasi adapter dan set ke RecyclerView
        homeAdapter = new HomeAdapter(options, getContext());
        recyclerView.setAdapter(homeAdapter);

        TextView usernameTextView = view.findViewById(R.id.nameUsername);

        // Set username based on user authentication status
        setUsernameText(usernameTextView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (homeAdapter != null) {
            homeAdapter.startListening();
        }

        // Set username based on user authentication status
        TextView usernameTextView = getView().findViewById(R.id.nameUsername);
        setUsernameText(usernameTextView);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (homeAdapter != null) {
            homeAdapter.stopListening();
        }
    }

    private void setUsernameText(TextView usernameTextView) {
        if (currentUser != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("username").getValue(String.class);
                        if (username != null) {
                            usernameTextView.setText("Heyy, " + username);
                        } else {
                            usernameTextView.setText("hallooow, Guest");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        } else {
            usernameTextView.setText("Hi, Guest");
        }
    }
}