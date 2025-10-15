package com.example.loanvalidation;

public class Submission {
    public String phone;
    public String imageUrl;
    public double lat;
    public double lon;
    public long timestamp;
    public String status;
    public String aiResult;

    public Submission() { }

    public Submission(String phone, String imageUrl, double lat, double lon, long timestamp, String status, String aiResult) {
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lon = lon;
        this.timestamp = timestamp;
        this.status = status;
        this.aiResult = aiResult;
    }
}
