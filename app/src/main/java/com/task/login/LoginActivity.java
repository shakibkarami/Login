package com.task.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.task.login.presentation.viewmodel.LoginViewModel;
import com.task.login.util.Encryption;

public class LoginActivity  extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private LoginViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            hideKeyboard();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            viewModel.login(username, password);
        });

        viewModel.getLoginState().observe(this, loginState -> {
            if (loginState.isSuccess()) {
                // TODO: implement onSuccess
                Toast.makeText(this,"Success.",
                        Toast.LENGTH_LONG).show();

            } else {
//                Snackbar.make(getCurrentFocus(), loginState.getErrorMessage(), Snackbar.LENGTH_SHORT)
//                        .show();
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
