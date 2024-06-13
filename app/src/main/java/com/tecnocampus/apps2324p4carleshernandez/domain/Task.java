package com.tecnocampus.apps2324p4carleshernandez.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "due_date")
    private String dueDate;
    @ColumnInfo(name = "priority")
    private String priority;
    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;
    @ColumnInfo(name = "user_mail")
    private String userMail;

    public Task(String title, String description, String dueDate, String priority, String userMail) throws IllegalArgumentException {
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
        setPriority(priority);
        this.isCompleted = false;
        this.userMail = userMail;
    }

    // Getters and Setters with validations
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        if (dueDate == null || !isValidDate(dueDate)) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD");
        }
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        if (priority == null || (!priority.equals("Non-Urgent") && !priority.equals("Medium") && !priority.equals("High"))) {
            throw new IllegalArgumentException("Priority must be Low, Medium, or High");
        }
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    // Utility method to validate date format
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public long getId() {
        return id;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}

