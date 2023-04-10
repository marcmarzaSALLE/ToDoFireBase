package com.example.todolistfirebase.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.SharedPreferencesController;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;

    TextView txtRegister;
    SharedPreferencesController sharedPreferencesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        syncronizedWigets();
        btnLogin.setOnClickListener(v -> {
           checkToken();
        });
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void syncronizedWigets() {
        sharedPreferencesController = new SharedPreferencesController();
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtViewRegister);
    }
    private void updateUI(FirebaseUser user) {

    }

    private void checkToken(){
        String token = sharedPreferencesController.loadDateSharedPreferences(this);
        Intent intent;
        Log.wtf("token", token);
        if(token!=null){
            Log.wtf("token", "null");
            finish();
            intent = new Intent(MainActivity.this, MenuActivity.class);

        }else{
            finish();
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }
        startActivity(intent);
    }

}