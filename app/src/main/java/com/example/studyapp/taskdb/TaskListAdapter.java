package com.example.studyapp.taskdb;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyapp.R;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{

    private final LayoutInflater mInflater;
    public List<Task> mTasks; // Cached copy of tasks
    private OnTaskClickListener onTaskClickListener;
    boolean isChecked;

    private SparseBooleanArray mCheckedItems = new SparseBooleanArray();

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

           // if(taskTitleString.length() > 10)
                //taskTitleString = taskTitleString.substring(0,9) + "...";
            if(taskDescriptionString.length() > 10)
                taskDescriptionString = taskDescriptionString.substring(0,10) + "...";

            holder.taskTitleText.setText(taskTitleString);
            holder.taskDescriptionText.setText(taskDescriptionString);

            isChecked = current.getIsComplete() == 1 ? true : false;

            holder.completedCheck.setChecked(isChecked);
/*
            holder.completedCheck.setChecked(mCheckedItems.get(position));

            holder.completedCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int p = holder.getAdapterPosition();
                    final boolean newValue = !holder.completedCheck.isChecked();

                    mCheckedItems.put(p, newValue);
                }
            });
*/
            holder.completedCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newValue = current.getIsComplete() == 1 ? 0 : 1;

                    isChecked = !isChecked;
                    holder.completedCheck.setChecked(isChecked);

                    onTaskClickListener.onCheckClick(current, newValue);
                }
            });


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
        private final CheckBox completedCheck;
        OnTaskClickListener onTaskClickListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            taskTitleText = itemView.findViewById(R.id.titleText);
            taskDescriptionText = itemView.findViewById(R.id.descriptionText);
            completedCheck = itemView.findViewById(R.id.checkBox);
            this.onTaskClickListener = onTaskClickListener;

            if(getAdapterPosition() > 0) {
                if(mTasks.get(getAdapterPosition()).getIsComplete() == 1) {
                    completedCheck.setChecked(true);
                }
            }


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTaskClickListener.onTaskClick(mTasks.get(getAdapterPosition()));
        }


    }

    public interface OnTaskClickListener {
        void onTaskClick(Task current);

        void onCheckClick(Task current, int newCompleted);
    }

}
