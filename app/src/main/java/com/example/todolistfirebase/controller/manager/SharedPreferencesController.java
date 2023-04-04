package com.example.todolistfirebase.controller.manager;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesController {

    public SharedPreferencesController() {
    }

    public String loadDateSharedPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("tokens", MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        return token;
    }

    public void saveDateSharedPreferences(String token, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("tokens", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

}
