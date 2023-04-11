package com.example.todolistfirebase.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.adapter.ListMenuAdapter;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.MenuTask;
import com.example.todolistfirebase.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MenuListFragment extends Fragment {


    List<MenuTask> typeTasks;
    private int numTaskToday, numTaskPlanned;
    RecyclerView recyclerView;
    ListMenuAdapter menuAdapter;
    private ArrayList<Task> tasks;
    FireBaseController fireBaseController;

    private void syncronizedWigets(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewMenu);
        fireBaseController = new FireBaseController(requireContext());
    }

    private void addData() {
        typeTasks = new ArrayList<>();
        typeTasks.add(new MenuTask(1, R.drawable.icon_planned, getResources().getString(R.string.scheduled), this.numTaskPlanned + " tareas"));
        typeTasks.add(new MenuTask(2, R.drawable.icon_add, "NUEVA TAREA", "Crea una nueva tarea"));

        menuAdapter = new ListMenuAdapter(typeTasks, requireContext(), new ListMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuTask menuTask) {
                startIntent(menuTask);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(menuAdapter);

    }

    private void startIntent(MenuTask menuTask) {
        fireBaseController.getUserDataFromDataBase(fireBaseController.getToken());
        switch (menuTask.getId()) {
            case 1:

                if (this.numTaskPlanned != 0) {
                    Toast.makeText(requireContext(), getResources().getString(R.string.noTaskRegister), Toast.LENGTH_SHORT).show();
                } else {
                    Fragment scheduledTaskFragment = new TaskListFragment();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, scheduledTaskFragment).commit();
                }
                break;
            case 2:
                Fragment addTaskFragment = new AddTaskFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, addTaskFragment).commit();
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        syncronizedWigets(view);
        addData();
        return view;
    }


}