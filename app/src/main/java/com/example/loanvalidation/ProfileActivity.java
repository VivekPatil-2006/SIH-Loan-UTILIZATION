package com.example.loanvalidation;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private TextView profileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileInfo = findViewById(R.id.profileInfo);
        profileInfo.setText("User: " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}
