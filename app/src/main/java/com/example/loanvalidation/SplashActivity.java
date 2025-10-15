package com.example.loanvalidation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                startActivity(new Intent(this, DashboardActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}
