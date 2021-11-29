package com.example.studyapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studyapp.databinding.ActivityTimerBinding;
import com.example.studyapp.taskdb.Task;

import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private ActivityTimerBinding binding;
    private EditText hourText, minuteText, secondsText;
    private String hourString, minutesString, secondsString;
    private Integer hours, minutes, seconds;
    private long startTimeInMillis, timeLeftInMillis;
    private Button startButton, pauseButton, resetButton;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        //Initialize buttons & edit texts
        startButton = binding.startButton;
        pauseButton = binding.pauseButton;
        resetButton = binding.resetButton;

        hourText = binding.hourText;
        minuteText = binding.minuteText;
        secondsText = binding.secondsText;

        //Timer button listeners
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourString = hourText.getText().toString();
                minutesString = minuteText.getText().toString();
                secondsString = secondsText.getText().toString();

                hours = Integer.parseInt(hourString);
                minutes = Integer.parseInt(minutesString);
                seconds = Integer.parseInt(secondsString);

                if(hours == 0 && minutes == 0 && seconds == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_LONG).show();
                    return;
                } else if(minutes > 60 || seconds > 60) {
                    Toast.makeText(getApplicationContext(), "Minute or seconds value is too high", Toast.LENGTH_LONG).show();
                    return;
                }

                timeLeftInMillis = calculateStartTime(hours, minutes, seconds);

                startButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);

                hourText.setEnabled(false);
                minuteText.setEnabled(false);
                secondsText.setEnabled(false);

                startTimer();
            }
        });



        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerRunning) {
                    pauseTimer();

                    pauseButton.setText("Resume");
                } else {
                    startTimer();

                    pauseButton.setText("Pause");
                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        //Task Button Listener
        ImageButton taskButton = binding.taskButton;
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TimerActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent mainIntent = getIntent();
        List<Task> spinnerList = (List<Task>) mainIntent.getSerializableExtra("SpinnerList");

        String[] spinnerTitleArray = new String[spinnerList.size()];

        for(int i = 0; i < spinnerList.size(); i++) {
            spinnerTitleArray[i] = spinnerList.get(i).getTitle();
        }

        Spinner timerSpinner = binding.spinner;

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerTitleArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timerSpinner.setAdapter(spinnerAdapter);
    }

    private void resetTimer() {
        countDownTimer.cancel();
        timerRunning = false;

        timeLeftInMillis = 0;
        hourText.setText("00");
        minuteText.setText("00");
        secondsText.setText("00");

        hourText.setEnabled(true);
        minuteText.setEnabled(true);
        secondsText.setEnabled(true);


        startButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                resetTimer();
                //TODO: Add Alarm/Notification
            }
        }.start();

        timerRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void updateCountDownText() {
        Integer uHours   = (int) ((timeLeftInMillis / (1000*60*60)) % 24);
        Integer uMinutes = (int) ((timeLeftInMillis / (1000*60)) % 60);
        Integer uSeconds = (int) (timeLeftInMillis / 1000) % 60;

        hourText.setText(String.format("%02d", uHours));
        minuteText.setText(String.format("%02d", uMinutes));
        secondsText.setText(String.format("%02d", uSeconds));
    }

    private long calculateStartTime(int hours, int minutes, int seconds) {
        int hoursInMillis, minsInMillis, secsInMillis, totalMillis;

        hoursInMillis = ((hours * 60) * 60) * 1000;
        minsInMillis = (minutes * 60) * 1000;
        secsInMillis = seconds * 1000;

        totalMillis = hoursInMillis + minsInMillis + secsInMillis;
        return totalMillis;
    }
}