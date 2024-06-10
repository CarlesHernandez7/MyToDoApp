package com.tecnocampus.apps2324p4carleshernandez;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private View view;
    private Button buttonLogout;
    private Button buttonGoBack;
    private TextView tvUsername;
    private TextView tvEmail;
    private FirebaseUser user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.profile, container, false);
        initializeAllAttributes();

        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireContext(), Login.class);
            startActivity(intent);
            requireActivity().finish();
        });

        buttonGoBack.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return this.view;
    }

    private void initializeAllAttributes() {
        this.tvUsername = view.findViewById(R.id.tv_username);
        this.tvEmail = view.findViewById(R.id.tv_email);
        this.buttonLogout = view.findViewById(R.id.btn_logout);
        this.buttonGoBack = view.findViewById(R.id.btn_back);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            tvEmail.setText(user.getEmail());
            tvUsername.setText(user.getEmail().split("@")[0]);

        }
    }


}
