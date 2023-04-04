package com.example.todolistfirebase.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtEmail, edtPassword;
    FireBaseController fireBaseController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        syncronizedWigets();
        btnLogin.setOnClickListener(v -> {
            login();
        });
    }
    private void syncronizedWigets() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.editTextEmail);
        edtPassword = (EditText) findViewById(R.id.editTextPassword);
        fireBaseController = new FireBaseController(this);

    }

    private void login() {
        fireBaseController.login(edtEmail.getText().toString(), edtPassword.getText().toString());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


}