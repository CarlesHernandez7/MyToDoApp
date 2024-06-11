package com.tecnocampus.apps2324p4carleshernandez;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private List<Task> tasks;
    private AdapterView.OnItemClickListener listener;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }


    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklistitem, parent, false);
        return new ViewHolder(v, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.date.setText(task.getDueDate());
        holder.priority.setText(task.getPriority());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView date;
        TextView priority;
        AdapterView.OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, AdapterView.OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            this.title = itemView.findViewById(R.id.title);
            this.date = itemView.findViewById(R.id.date);
            this.priority = itemView.findViewById(R.id.priority);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(null, v, getAdapterPosition(), v.getId());
        }
    }

}
