package com.example.todolistfirebase.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.adapter.ListMenuAdapter;
import com.example.todolistfirebase.model.MenuTask;
import com.example.todolistfirebase.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    List<MenuTask> typeTasks;
    private int numTaskToday, numTaskPlanned;
    RecyclerView recyclerView;
    ListMenuAdapter menuAdapter;
    private ArrayList<Task>tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        syncronizedWigets();
        addData();
    }
    private void syncronizedWigets() {
        recyclerView = findViewById(R.id.recyclerViewMenu);
    }
    private void addData(){
        typeTasks = new ArrayList<>();
        typeTasks.add(new MenuTask(1, R.drawable.icon_today, "PARA HOY", this.numTaskToday + " tareas"));
        typeTasks.add(new MenuTask(2, R.drawable.icon_planned, "PROGRAMADAS", this.numTaskPlanned + " tareas"));
        typeTasks.add(new MenuTask(3, R.drawable.icon_add, "NUEVA TAREA", "Crea una nueva tarea"));

        menuAdapter = new ListMenuAdapter(typeTasks, this, new ListMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuTask menuTask) {
                startIntent(menuTask);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(menuAdapter);

    }
    private void startIntent(MenuTask menuTask) {
        switch (menuTask.getId()) {
            case 1:
                if (this.numTaskToday == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.noTaskRegister), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "INTENT TODAY", Toast.LENGTH_SHORT).show();
                }

                break;
            case 2:
                if (this.numTaskPlanned == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.noTaskRegister), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "INTENT PLANNED", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                Toast.makeText(this, "INTENT ADD", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}