package com.task.login.presentation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.task.login.R;

public class MainActivity extends AppCompatActivity {

    TextView tokenTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenTextView = findViewById(R.id.tokenTextView);
        String token = getIntent().getStringExtra("token");
        tokenTextView.setText(token);

    }
}
