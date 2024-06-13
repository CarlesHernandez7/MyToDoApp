package com.tecnocampus.apps2324p4carleshernandez.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tecnocampus.apps2324p4carleshernandez.R;
import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tasks;
    private OnItemClickListener listener;
    Context context;

    public TaskAdapter(Context context) {
        tasks = new ArrayList<>();
        this.context = context;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tasklist, parent, false);
        return new ViewHolder(v, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.date.setText(task.getDueDate());
        holder.priority.setText(task.getPriority());

        // Set background color based on priority
        if ("Non-Urgent".equals(task.getPriority())) {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else if ("Medium".equals(task.getPriority())) {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.orange));
        } else {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView date;
        TextView priority;
        LinearLayout layout;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            this.layout = itemView.findViewById(R.id.taskItemLinearLayout);
            this.layout.setOnClickListener(this);
            this.title = itemView.findViewById(R.id.title);
            this.date = itemView.findViewById(R.id.date);
            this.priority = itemView.findViewById(R.id.priority);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(v, getAdapterPosition());
        }
    }

}
