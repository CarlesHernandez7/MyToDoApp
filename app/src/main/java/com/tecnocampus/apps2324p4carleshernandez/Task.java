package com.tecnocampus.apps2324p4carleshernandez;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private boolean isCompleted;

    public Task(String title, String description, String dueDate, String priority, boolean isCompleted) throws IllegalArgumentException {
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
        setPriority(priority);
        this.isCompleted = isCompleted;
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
        if (priority == null || (!priority.equals("Low") && !priority.equals("Medium") && !priority.equals("High"))) {
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
}

