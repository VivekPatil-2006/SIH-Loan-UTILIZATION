package com.example.loanvalidation;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SubmissionsActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> submissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions);

        listView = findViewById(R.id.submissionList);
        submissions.add("Asset - Tractor - Verified");
        submissions.add("Asset - Water Pump - Pending");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, submissions));
    }
}
