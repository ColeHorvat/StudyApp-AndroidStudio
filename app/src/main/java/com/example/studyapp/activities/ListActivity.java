package com.example.studyapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyapp.databinding.ActivityListBinding;
import com.example.studyapp.taskdb.Task;
import com.example.studyapp.taskdb.TaskListAdapter;
import com.example.studyapp.taskdb.TaskViewModel;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private TaskViewModel mTaskViewModel;
    private ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        RecyclerView recyclerView = binding.taskRecycler;
        final TaskListAdapter adapter = new TaskListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });
    }
}