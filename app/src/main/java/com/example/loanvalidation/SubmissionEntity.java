package com.example.loanvalidation;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "submissions")
public class SubmissionEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String phone;            // Beneficiary phone (document id)
    public String imagePath;        // Local file path to image (or URI string)
    public double latitude;
    public double longitude;
    public long timestamp;          // epoch millis
    public String status;           // "PENDING", "SYNCED", "FAILED"
    public String aiResult;         // AI result returned from backend (optional)

    public SubmissionEntity(@NonNull String phone, String imagePath,
                            double latitude, double longitude, long timestamp,
                            String status, String aiResult) {
        this.phone = phone;
        this.imagePath = imagePath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.status = status;
        this.aiResult = aiResult;
    }
}
