package com.example.loanvalidation;

import android.annotation.SuppressLint;
import android.content.Context;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import android.location.Location;

/**
 * Simple helper to get last known location as a Task<Location>.
 * Caller must ensure location permissions are granted before calling.
 */
public class LocationUtils {

    private final FusedLocationProviderClient fused;

    public LocationUtils(Context ctx) {
        fused = LocationServices.getFusedLocationProviderClient(ctx);
    }

    @SuppressLint("MissingPermission")
    public Task<Location> getLastLocation() {
        // Caller must check runtime permissions for ACCESS_FINE_LOCATION
        return fused.getLastLocation();
    }
}
