package com.example.studyapp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studyapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        //Set footer link
        SpannableString footerString = new SpannableString("Forgot Password?");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                //Start login activity
                startActivity(new Intent(LoginActivity.this, ListActivity.class));
            }
        };
        footerString.setSpan(clickableSpan, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView footerText = binding.loginFooter;
        footerText.setText(footerString);
        footerText.setMovementMethod(LinkMovementMethod.getInstance());
        footerText.setHighlightColor(Color.TRANSPARENT);

        //Login OnClick
        Button loginButton = binding.loginButton;
        EditText emailText = binding.loginEmailInput;
        EditText passText = binding.loginPassInput;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verify user credentials and transition to main activity
                Intent listIntent = new Intent(LoginActivity.this, ListActivity.class);
                listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String sEmail = emailText.getText().toString();
                String sPass = passText.getText().toString();

                //Fields are not empty
                if(!sEmail.matches("") && !sPass.matches("")) {
                    Log.d("pass", "User: " + sEmail + " --- Pass: " + sPass);
                    startActivity(listIntent);
                } else {
                    Log.d("pass", "User: " + sEmail + " --- Pass: " + sPass);
                    Toast.makeText(getApplicationContext(), "Please enter an email and password", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}