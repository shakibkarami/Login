package com.task.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.task.login.presentation.viewmodel.LoginViewModel;
import com.task.login.util.Encryption;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ConstraintLayout mainLayout;
    private LoginViewModel viewModel;
    private LinearProgressIndicator progressIndicator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        mainLayout = findViewById(R.id.mainLayout);
        progressIndicator = findViewById(R.id.progressIndicator);

        loginButton.setOnClickListener(view -> {
            hideKeyboard();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            viewModel.login(username, password);
        });

        viewModel.getLoginState().observe(this, loginState -> {
            if (loginState.isSuccess()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (!loginState.isLoading()) {
                    Snackbar.make(mainLayout, loginState.getErrorMessage(), Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
            if (loginState.isLoading()){
                progressIndicator.setVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
            } else {
                progressIndicator.setVisibility(View.INVISIBLE);
                loginButton.setEnabled(true);
            }
        });
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }

}
