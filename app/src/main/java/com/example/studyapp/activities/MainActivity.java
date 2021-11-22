package com.example.studyapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.studyapp.R;
import com.example.studyapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    FragmentManager fragmentManager;

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
                //Start login activity
                startActivity(new Intent(MainActivity.this, TimerActivity.class));
            }
        });

        //Create Button Listener
        ImageButton createButton = binding.createButton;
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTaskDialog();
            }
        });
    }

    private void showTaskDialog() {
        //TODO: Find a way to use view binding here
        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.task_dialog);

        EditText taskTitle = dialog.findViewById(R.id.titleEditText);
        EditText taskDescription = dialog.findViewById(R.id.descriptionEditText);
        Spinner groupSpinner = dialog.findViewById(R.id.groupSpinner);
        Button confirmButton = dialog.findViewById(R.id.dialogButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskTitleString;
                String taskDescriptionString;
                String groupSelection;

                //Check if fields have been filled and assign values
                if(taskTitle.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Title is required", Toast.LENGTH_LONG);
                    return;
                }
                taskTitleString = taskTitle.getText().toString();

                if(taskDescription.getText().toString().matches(""))
                    taskDescriptionString = null;
                else
                    taskDescriptionString = taskDescription.getText().toString();

                if(groupSpinner.getSelectedItem().toString().matches(""))
                    groupSelection = null;
                else
                    groupSelection = groupSpinner.getSelectedItem().toString();



                //TODO: Add task to ROOM

                dialog.dismiss();
            }
        });

        dialog.show();


    }
}