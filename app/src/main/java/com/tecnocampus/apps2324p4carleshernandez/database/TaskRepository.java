package com.tecnocampus.apps2324p4carleshernandez.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.util.List;


public class TaskRepository {

    private TaskDao taskDao;
    private RoomDatabase db;
    private LiveData<List<Task>> taskList;

    TaskRepository(Application application, String mail){
        this.db = RoomDatabase.getDatabase(application);
        this.taskDao = db.taskDao();
        this.taskList = this.taskDao.getAllTasksByGmail(mail);
    }

    public LiveData<List<Task>> getAllTasksByGmail() {
        return this.taskList;
    }

    public void insert(Task task){
        RoomDatabase.databaseWriteExecutor.execute(() -> taskDao.insert(task));
    }

    public LiveData<Task> getTaskById(long id) {
        return taskDao.getTaskById(id);
    }

    public void delete(Task task){
        RoomDatabase.databaseWriteExecutor.execute(() -> taskDao.delete(task));
    }
}
