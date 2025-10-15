package com.example.loanvalidation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginBtn, registerBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email); // rename your EditText in XML if you want
        passwordEditText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();

            // Demo credentials
            if(email.equals("demo@loanapp.com") && pass.equals("1234")){
                // Directly open DashboardActivity without Firebase
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
                return;
            }

            // Normal Firebase login
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        registerBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterBeneficiaryActivity.class)));
    }
}
