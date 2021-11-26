package com.example.studyapp.taskdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyapp.R;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{

    private final LayoutInflater mInflater;
    private List<Task> mTasks; // Cached copy of words

    public TaskListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public TaskListAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.TaskViewHolder holder, int position) {
        if (mTasks != null) {
            Task current = mTasks.get(position);
            holder.TaskItemView.setText(current.getTitle());
        } else {
            holder.TaskItemView.setText("No Tasks");
        }
    }

    public void setTasks(List<Task> Tasks) {
        mTasks = Tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else
            return 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private final TextView TaskItemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            TaskItemView = itemView.findViewById(R.id.titleText);
        }
    }
}
