package com.example.loanvalidation;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Worker that syncs local PENDING submissions to Firebase Storage and Firestore.
 *
 * Note: This worker runs on a background thread provided by WorkManager.
 */
public class SyncWorker extends Worker {
    private static final String TAG = "SyncWorker";

    public SyncWorker(@NonNull Context ctx, @NonNull WorkerParameters params) {
        super(ctx, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context ctx = getApplicationContext();
        AppDatabase db = AppDatabase.getInstance(ctx);
        SubmissionDao dao = db.submissionDao();
        List<SubmissionEntity> pending = dao.getByStatus("PENDING");

        if (pending == null || pending.isEmpty()) {
            Log.i(TAG, "No pending submissions");
            return Result.success();
        }

        if (!NetworkUtils.isOnline(ctx)) {
            Log.i(TAG, "No network. Will retry later.");
            return Result.retry();
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRoot = storage.getReference().child("submissions");

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        boolean allOk = true;

        for (SubmissionEntity s : pending) {
            try {
                File f = new File(s.imagePath);
                if (!f.exists()) {
                    s.status = "FAILED";
                    dao.update(s);
                    continue;
                }

                String remotePath = s.phone + "/" + s.timestamp + "_" + f.getName();
                StorageReference ref = storageRoot.child(remotePath);

                Uri fileUri = Uri.fromFile(f);
                CountDownLatch latch = new CountDownLatch(1);

                UploadTask uploadTask = ref.putFile(fileUri);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Prepare Firestore doc
                        Map<String, Object> doc = new HashMap<>();
                        doc.put("phone", s.phone);
                        doc.put("imageUrl", uri.toString());
                        Map<String, Object> geo = new HashMap<>();
                        geo.put("lat", s.latitude);
                        geo.put("lon", s.longitude);
                        doc.put("geoLocation", geo);
                        doc.put("timestamp", s.timestamp);
                        doc.put("ai_result", s.aiResult != null ? s.aiResult : "UNKNOWN");
                        doc.put("status", "Pending");

                        // Write to Firestore (collection: submissions)
                        firestore.collection("submissions")
                                .add(doc)
                                .addOnSuccessListener(documentReference -> {
                                    s.status = "SYNCED";
                                    dao.update(s);
                                    latch.countDown();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Firestore write failed: " + e.getMessage());
                                    s.status = "FAILED";
                                    dao.update(s);
                                    latch.countDown();
                                });

                    }).addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to get download URL: " + e.getMessage());
                        s.status = "FAILED";
                        dao.update(s);
                        latch.countDown();
                    });

                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Upload failed: " + e.getMessage());
                    s.status = "FAILED";
                    dao.update(s);
                    latch.countDown();
                });

                // Wait for upload+firestore completion for this item (keeps sequential behavior)
                latch.await();

            } catch (Exception e) {
                Log.e(TAG, "Exception syncing submission id=" + s.id + " : " + e.getMessage());
                s.status = "FAILED";
                dao.update(s);
                allOk = false;
            }
        }

        return allOk ? Result.success() : Result.retry();
    }
}
