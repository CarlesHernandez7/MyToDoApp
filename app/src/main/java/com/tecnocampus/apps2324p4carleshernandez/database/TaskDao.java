package com.tecnocampus.apps2324p4carleshernandez.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(long id);

    @Query("SELECT *  FROM tasks WHERE user_mail = :mail ORDER BY priority ASC")
    LiveData<List<Task>> getAllTasksByGmail(String mail);

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<Task> getTaskById(long id);
}

