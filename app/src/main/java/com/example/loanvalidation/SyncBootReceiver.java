package com.example.loanvalidation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

/**
 * Receives BOOT_COMPLETED and re-schedules periodic sync work.
 */
public class SyncBootReceiver extends BroadcastReceiver {
    private static final String WORK_TAG = "LoanSyncPeriodic";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action) || Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action)) {
            schedulePeriodicSync(context);
        }
    }

    public static void schedulePeriodicSync(Context ctx) {
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(SyncWorker.class, 15, TimeUnit.MINUTES)
                .addTag(WORK_TAG)
                .build();

        WorkManager.getInstance(ctx)
                .enqueueUniquePeriodicWork(WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, request);
    }
}
