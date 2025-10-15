package com.example.loanvalidation;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;
import java.util.List;

@Dao
public interface SubmissionDao {

    @Insert
    long insert(SubmissionEntity entity);

    @Update
    void update(SubmissionEntity entity);

    @Delete
    void delete(SubmissionEntity entity);

    @Query("SELECT * FROM submissions WHERE status = :status")
    List<SubmissionEntity> getByStatus(String status);

    @Query("SELECT * FROM submissions ORDER BY timestamp DESC")
    List<SubmissionEntity> getAll();
}
