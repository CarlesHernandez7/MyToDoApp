package com.tecnocampus.apps2324p4carleshernandez.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;
    private LiveData<List<Task>> taskList;

    public TaskViewModel(Application application) {
        super(application);
    }

    public void init(String mail) {
        if (this.taskRepository == null) {
            this.taskRepository = new TaskRepository(getApplication(), mail);
            this.taskList = this.taskRepository.getAllTasksByGmail();
        }

    }

    public LiveData<List<Task>> getAllTasks() {
        return this.taskList;
    }

    public void insert(Task task) {
        taskRepository.insert(task);
    }

    public LiveData<Task> getTaskById(long id) {
        return taskRepository.getTaskById(id);
    }

    public void delete(long id) {
        taskRepository.delete(id);
    }
}
