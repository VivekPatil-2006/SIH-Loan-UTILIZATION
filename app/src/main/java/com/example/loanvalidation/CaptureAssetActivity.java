package com.example.loanvalidation;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class CaptureAssetActivity extends AppCompatActivity {
    private ImageView preview;
    private Button captureBtn, uploadBtn;
    private Bitmap capturedImage;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_asset);

        preview = findViewById(R.id.preview);
        captureBtn = findViewById(R.id.captureBtn);
        uploadBtn = findViewById(R.id.uploadBtn);

        captureBtn.setOnClickListener(v -> openCamera());
        uploadBtn.setOnClickListener(v -> Toast.makeText(this, "Uploaded with location tag", Toast.LENGTH_SHORT).show());
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    capturedImage = (Bitmap) result.getData().getExtras().get("data");
                    preview.setImageBitmap(capturedImage);
                }
            });
}
