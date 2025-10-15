package com.example.loanvalidation;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterBeneficiaryActivity extends AppCompatActivity {
    private EditText name, phone, password;
    private Button registerBtn;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_beneficiary);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registerBtn.setOnClickListener(v -> {
            String n = name.getText().toString();
            String p = phone.getText().toString();
            String pass = password.getText().toString();

            auth.createUserWithEmailAndPassword(p + "@loanapp.com", pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    db.collection("beneficiaries").document(p).set(new Beneficiary(n, p));
                    Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error registering", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
