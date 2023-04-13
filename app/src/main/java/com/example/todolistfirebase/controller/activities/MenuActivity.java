package com.example.todolistfirebase.controller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.adapter.ListMenuAdapter;
import com.example.todolistfirebase.controller.fragments.MenuListFragment;
import com.example.todolistfirebase.controller.fragments.UserFragment;
import com.example.todolistfirebase.model.MenuTask;
import com.example.todolistfirebase.model.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    NavigationBarView navigationView;
    public static EditText edtLocation;
    public static Button btnSearch;

    public static Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        syncronizeWidget();

        openFragment(new MenuListFragment());
        navigationView.setItemIconTintList(null);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_menu_list:
                        openFragment(new MenuListFragment());
                        return true;
                    case R.id.id_user:
                        openFragment(new UserFragment());
                        return true;
                }
                return false;
            }

        });

    }

    private void syncronizeWidget(){
        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);

    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayout, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}