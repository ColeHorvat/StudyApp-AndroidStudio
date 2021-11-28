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
    public List<Task> mTasks; // Cached copy of tasks
    private OnTaskClickListener onTaskClickListener;

    public TaskListAdapter(Context context, OnTaskClickListener onTaskClickListener) {
        mInflater = LayoutInflater.from(context);
        this.onTaskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public TaskListAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.TaskViewHolder holder, int position) {
        if (mTasks != null) {
            Task current = mTasks.get(position);
            String taskTitleString;
            String taskDescriptionString;

            taskTitleString = current.getTitle().toString();
            taskDescriptionString = current.getDescription().toString();

            if(taskTitleString.length() > 10)
                taskTitleString = taskTitleString.substring(0,9) + "...";
            if(taskDescriptionString.length() > 10)
                taskDescriptionString = taskDescriptionString.substring(0,9) + "...";


            holder.taskTitleText.setText(taskTitleString);
            holder.taskDescriptionText.setText(taskDescriptionString);

        } else {
            holder.taskTitleText.setText("No Tasks");
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

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView taskTitleText;
        private final TextView taskDescriptionText;
        OnTaskClickListener onTaskClickListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            taskTitleText = itemView.findViewById(R.id.titleText);
            taskDescriptionText = itemView.findViewById(R.id.descriptionText);
            this.onTaskClickListener = onTaskClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTaskClickListener.onTaskClick(mTasks.get(getAdapterPosition()));
        }
    }

    public interface OnTaskClickListener {
        void onTaskClick(Task current);
    }

}
