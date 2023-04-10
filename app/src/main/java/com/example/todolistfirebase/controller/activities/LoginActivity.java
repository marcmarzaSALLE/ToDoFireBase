package com.example.todolistfirebase.controller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtEmail, edtPassword;
    FireBaseController fireBaseController;

    FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.editTextEmail);
        edtPassword = (EditText) findViewById(R.id.editTextPassword);
        fireBaseController = new FireBaseController(this);

    }

    private void login() {

        if (checkLogin()){
            fireBaseController.login(edtEmail.getText().toString(), edtPassword.getText().toString(),new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String token = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        fireBaseController.saveToken(token);
                        finish();
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean checkLogin() {
        if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
            if(edtEmail.getText().toString().isEmpty()){
                edtEmail.setError("Email is empty");
            }
            if(edtPassword.getText().toString().isEmpty()){
                edtPassword.setError("Password is empty");
            }
            return false;
        }
        return true;
    }


}