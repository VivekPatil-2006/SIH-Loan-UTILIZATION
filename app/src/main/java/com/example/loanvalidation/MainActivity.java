package com.example.loanvalidation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity; // Or android.app.Activity

public class MainActivity extends AppCompatActivity { // <- must extend Activity or AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
