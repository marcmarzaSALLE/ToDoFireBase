package com.example.todolistfirebase.controller.manager;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesController {

    public SharedPreferencesController() {
    }

    public String loadDateSharedPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("tokens", MODE_PRIVATE);
        return sharedPref.getString("token", null);
    }

    public void saveDateSharedPreferences(String token, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("tokens", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public void deleteDateSharedPreferences(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("tokens", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("token");
        editor.apply();
    }

    public void saveCorrect(Context context,boolean correct){
        SharedPreferences sharedPref = context.getSharedPreferences("errors", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("error", correct);
        editor.apply();
    }

    public boolean loadCorrect(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("errors", MODE_PRIVATE);
        boolean correct = sharedPref.getBoolean("error", false);
        return correct;
    }

}
