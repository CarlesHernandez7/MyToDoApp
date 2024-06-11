package com.tecnocampus.apps2324p4carleshernandez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tecnocampus.apps2324p4carleshernandez.auth.Login;
import com.tecnocampus.apps2324p4carleshernandez.database.TaskViewModel;
import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

public class DetailActivity extends AppCompatActivity {


    private TextView tvTitle, tvDescription, tvDueDate, tvPriority;
    private Switch switchIsCompleted;
    private Task task;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle(getString(R.string.app_title));

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvDueDate = findViewById(R.id.tvDueDate);
        tvPriority = findViewById(R.id.tvPriority);
        switchIsCompleted = findViewById(R.id.switchIsCompleted);
        Button btnShare = findViewById(R.id.btnShare);
        Button btnClose = findViewById(R.id.btnClose);

        task = getTask();

        tvTitle.setText(task.getTitle());
        tvDescription.setText(task.getDescription());
        tvDueDate.setText(task.getDueDate());
        tvPriority.setText(task.getPriority());
        switchIsCompleted.setChecked(task.isCompleted());

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTaskDetails();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCompleted = switchIsCompleted.isChecked();
                Log.d("DetailActivity", "Task completed: " + isCompleted);
                Log.d("WEEEEEEEEP", "Task completed: " + isCompleted);
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Task getTask() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String dueDate = intent.getStringExtra("dueDate");
        String priority = intent.getStringExtra("priority");

        return new Task(title, description, dueDate, priority, user.getEmail());
    }

    private void shareTaskDetails() {
        String completedText = switchIsCompleted.isChecked() ? "Yes" : "No";
        String shareContent = "Task Details:\n\n" +
                "Title: " + task.getTitle() + "\n" +
                "Description: " + task.getDescription() + "\n" +
                "Due Date: " + task.getDueDate() + "\n" +
                "Priority: " + task.getPriority() + "\n" +
                "Completed: " + completedText;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
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