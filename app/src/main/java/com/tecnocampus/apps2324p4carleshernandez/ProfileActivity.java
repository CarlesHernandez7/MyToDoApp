package com.tecnocampus.apps2324p4carleshernandez;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tecnocampus.apps2324p4carleshernandez.auth.Login;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonLogout;
    private ImageView profile_image;
    private TextView tvUsername;
    private TextView tvEmail;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_title));
        initializeAllAttributes();

        String imageUrl = "https://i.pinimg.com/736x/70/85/54/7085548f3d0372a08aea0291ddcee895.jpg";

        Glide.with(this)
                .load(imageUrl)
                .circleCrop()
                .error(R.drawable.profile_icon_design_free_vector)
                .into(profile_image);

        buttonLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ProfileActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });

    }

    private void initializeAllAttributes() {
        this.profile_image = findViewById(R.id.profile_image);
        this.tvUsername = findViewById(R.id.tv_username);
        this.tvEmail = findViewById(R.id.tv_email);
        this.buttonLogout = findViewById(R.id.btn_logout);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            tvEmail.setText(user.getEmail());
            tvUsername.setText(user.getEmail().split("@")[0]);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
