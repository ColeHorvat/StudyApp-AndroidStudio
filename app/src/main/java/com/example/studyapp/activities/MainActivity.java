package com.example.studyapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyapp.R;
import com.example.studyapp.databinding.ActivityMainBinding;
import com.example.studyapp.taskdb.Task;
import com.example.studyapp.taskdb.TaskListAdapter;
import com.example.studyapp.taskdb.TaskViewModel;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskListAdapter.OnTaskClickListener{

    private ActivityMainBinding binding;
    private TaskViewModel mTaskViewModel;
    FragmentManager fragmentManager;
    TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        fragmentManager = getSupportFragmentManager();

        //Timer Button Listener
        ImageButton timerButton = binding.timerButton;
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start timer activity
                Intent timerIntent = new Intent(MainActivity.this, TimerActivity.class);
                timerIntent.putExtra("SpinnerList", (Serializable) adapter.mTasks);
                startActivity(timerIntent);
            }
        });

        //Create Button Listener
        ImageButton createButton = binding.createButton;
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateTaskDialog();
                //Task testTask = new Task("Test Task", "", "", 0.00f);
                //mTaskViewModel.insert(testTask);
            }
        });

        //Recycler View Adapter
        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);


        RecyclerView recyclerView = binding.taskRecycler;
        adapter = new TaskListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mTaskViewModel.getUncompletedTasks().observe(MainActivity.this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

        Switch completedSwitch = binding.completedSwitch;

        completedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mTaskViewModel.getAllTasks().observe(MainActivity.this, new Observer<List<Task>>() {
                        @Override
                        public void onChanged(List<Task> tasks) {
                            adapter.setTasks(tasks);
                        }
                    });
                } else {
                    mTaskViewModel.getUncompletedTasks().observe(MainActivity.this, new Observer<List<Task>>() {
                        @Override
                        public void onChanged(List<Task> tasks) {
                            adapter.setTasks(tasks);
                        }
                    });
                }
            }
        });



    }

    private void showCreateTaskDialog() {
        //TODO: Find a way to use view binding here
        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.task_create_dialog);

        EditText taskTitle = dialog.findViewById(R.id.titleEditText);
        EditText taskDescription = dialog.findViewById(R.id.descriptionEditText);
        Button confirmButton = dialog.findViewById(R.id.dialogButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskTitleString;
                String taskDescriptionString;

                //Check if fields have been filled and assign values
                if(taskTitle.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Title is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                taskTitleString = taskTitle.getText().toString();

                taskDescriptionString = taskDescription.getText().toString();

                Task newTask = new Task(taskTitleString, taskDescriptionString, 0.00f, 0);
                mTaskViewModel.insert(newTask);
                dialog.dismiss();
            }
        });



        dialog.show();


    }

    private void showTaskDescriptionDialog(Task current) {
        //TODO: Find a way to use view binding here
        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.task_description_dialog);

        EditText taskTitle = dialog.findViewById(R.id.titleEditText);
        EditText taskDescription = dialog.findViewById(R.id.descriptionEditText);
        Button confirmButton = dialog.findViewById(R.id.dialogButton);
        ImageButton deleteButton = dialog.findViewById(R.id.deleteButton);

        taskTitle.setText(current.getTitle());
        taskDescription.setText(current.getDescription());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskTitleString;
                String taskDescriptionString;

                //Check if fields have been filled and assign values
                if(taskTitle.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Title is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                taskTitleString = taskTitle.getText().toString();

                taskDescriptionString = taskDescription.getText().toString();

                Task updatedTask = new Task(current.getId(), taskTitleString, taskDescriptionString, current.getTimeProgress(), current.getIsComplete());
                mTaskViewModel.update(updatedTask);
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("title", "Title: " + current.getTitle());
                mTaskViewModel.delete(current);
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    @Override
    public void onTaskClick(Task current) {
        showTaskDescriptionDialog(current);
    }

    @Override
    public void onCheckClick(Task current, int newCompleted) {
        Task newCheckTask = new Task(current.getId(), current.getTitle(), current.getDescription(), current.getTimeProgress(), newCompleted);
        mTaskViewModel.update(newCheckTask);
    }

}