package com.tecnocampus.apps2324p4carleshernandez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tecnocampus.apps2324p4carleshernandez.auth.Login;
import com.tecnocampus.apps2324p4carleshernandez.database.TaskViewModel;
import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListener {

    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private List<Task> taskList;
    private RecyclerView.LayoutManager layoutManager;
    private Button buttonCreateTask;
    TaskViewModel taskViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_title));

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.init(user.getEmail());

        this.buttonCreateTask = findViewById(R.id.btn_add_task);
        this.buttonCreateTask.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateTaskActivity.class);
            startActivity(intent);
        });

        this.taskList = new ArrayList<>();
        this.recyclerView = findViewById(R.id.rv_tasks);
        this.layoutManager = new GridLayoutManager(this, 2);
        this.recyclerView.setLayoutManager(layoutManager);

        this.adapter = new TaskAdapter(this);
        this.adapter.setClickListener(this);

        this.activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                o -> {
                    Log.d("BBBBBBBBB", "Task completed: " + o.getResultCode());
                    if (o.getResultCode() == RESULT_OK) {
                        String isCompleted = o.getData().getStringExtra("isCompleted");
                        Log.d("AAAAAAAAAAAA", "Task completed: " + isCompleted);
                        if (isCompleted.equals("true")) {
                            long id = o.getData().getLongExtra("id", -1);
                            Log.d("AAAAAAAAAAAA", "Task completed: " + id);
                            Task task = getTask(id);
                            Log.d("AAAAAAAAAAAA", "Task completed: " + task.getId());
                            taskList.remove(task);
                            this.adapter.notifyDataSetChanged();
                        }
                    }
                });

        this.taskViewModel.getAllTasks().observe(this, tasks -> {
            taskList = tasks;
            adapter.setTasks(taskList);
            recyclerView.setAdapter(adapter);
        });
    }

    public Task getTask(long id) {
        for (Task t: this.taskList){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }

    @Override
    public void onClick(View view, int position) {
        Task task = taskList.get(position);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", task.getId());
        intent.putExtra("title", task.getTitle());
        intent.putExtra("description", task.getDescription());
        intent.putExtra("dueDate", task.getDueDate());
        intent.putExtra("priority", task.getPriority());
        this.activityResultLauncher.launch(intent);
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
            Intent intent;
            if (user != null) {
                intent = new Intent(this, ProfileActivity.class);
            } else {
                intent = new Intent(this, Login.class);
            }
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}