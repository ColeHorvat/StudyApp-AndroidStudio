package com.example.studyapp.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

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
    }
}