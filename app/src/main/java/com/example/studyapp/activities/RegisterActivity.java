package com.example.studyapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studyapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        //Set footer link
        SpannableString footerString = new SpannableString("Already have an account? Sign in");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Log.d("Press", "--- SIGN IN PRESSED ---");
                //Start login activity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        };
        footerString.setSpan(clickableSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView footerText = binding.registerFooter;
        footerText.setText(footerString);
        footerText.setMovementMethod(LinkMovementMethod.getInstance());
        footerText.setHighlightColor(Color.TRANSPARENT);
    }

}