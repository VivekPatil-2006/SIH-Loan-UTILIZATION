package com.example.loanvalidation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    private Button captureAssetBtn, submissionsBtn, profileBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        captureAssetBtn = findViewById(R.id.captureAssetBtn);
        submissionsBtn = findViewById(R.id.submissionsBtn);
        profileBtn = findViewById(R.id.profileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        captureAssetBtn.setOnClickListener(v -> startActivity(new Intent(this, CaptureAssetActivity.class)));
        submissionsBtn.setOnClickListener(v -> startActivity(new Intent(this, SubmissionsActivity.class)));
        profileBtn.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        logoutBtn.setOnClickListener(v -> {
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
