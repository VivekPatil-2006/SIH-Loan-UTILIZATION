package com.example.loanvalidation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Simple network check utility
 */
public class NetworkUtils {

    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
