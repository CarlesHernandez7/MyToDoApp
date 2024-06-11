package com.tecnocampus.apps2324p4carleshernandez;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tecnocampus.apps2324p4carleshernandez.auth.Login;
import com.tecnocampus.apps2324p4carleshernandez.database.TaskViewModel;
import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDueDate;
    private Spinner spinnerPriority;
    private Button btnSave, btnCancel;
    private TaskViewModel taskViewModel;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createtask);
        getSupportActionBar().setTitle(getString(R.string.app_title));

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDueDate = findViewById(R.id.etDueDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.init(user.getEmail());

        // Set up the spinner for priority
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveTask());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveTask() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String dueDate = etDueDate.getText().toString();
        String priority = spinnerPriority.getSelectedItem().toString();

        if (title.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (description.isEmpty()) {
            description = "null";
        }

        if (!isValidDate(dueDate)) {
            Toast.makeText(this, "Please enter a valid due date in the format YYYY-MM-DD", Toast.LENGTH_SHORT).show();
            return;
        }

        Task newTask = new Task(title, description, dueDate, priority, user.getEmail());

        taskViewModel.insert(newTask);

        Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isValidDateFormat(String date) {
        // Regular expression to check if the date is in yyyy-MM-dd format
        String regex = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        return date.matches(regex);
    }

    private boolean isValidDate(String date) {
        if (!isValidDateFormat(date)) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Disable lenient parsing

        try {
            sdf.parse(date); // Attempt to parse the date
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}