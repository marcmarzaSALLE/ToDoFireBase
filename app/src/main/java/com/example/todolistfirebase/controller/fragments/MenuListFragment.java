package com.example.todolistfirebase.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.adapter.ListMenuAdapter;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.MenuTask;
import com.example.todolistfirebase.model.Task;
import com.example.todolistfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuListFragment extends Fragment {


    List<MenuTask> typeTasks;
    TextView txtUserName;
    private int numTaskToday, numTaskPlanned;
    RecyclerView recyclerView;
    ListMenuAdapter menuAdapter;
    private ArrayList<Task> tasks;
    FireBaseController fireBaseController;

    private void syncronizedWigets(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewMenu);
        txtUserName = view.findViewById(R.id.txtUserName);
        fireBaseController = new FireBaseController(requireContext());
        getUserName();
    }

    private void addData() {
        typeTasks = new ArrayList<>();
        typeTasks.add(new MenuTask(1, R.drawable.baseline_calendar_month_24, getResources().getString(R.string.scheduled), this.numTaskPlanned +" "+ getResources().getString(R.string.tasks)));
        typeTasks.add(new MenuTask(2, R.drawable.icon_add, getResources().getString(R.string.newTask).toUpperCase(), getResources().getString(R.string.createNewTask)));

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

    private void getUserName(){
        fireBaseController.getTasksFromUser(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        User user = documentSnapshot.toObject(User.class);
                        MenuListFragment.this.addDataToWidget(user);
                    }
                }
            }
        });
    }

    public void addDataToWidget(User user){
        txtUserName.setText(user.getName());
        this.numTaskPlanned = user.getTasks().size();
        this.tasks = user.getTasks();
        typeTasks.set(0, new MenuTask(1, R.drawable.baseline_calendar_month_24, getResources().getString(R.string.scheduled), this.numTaskPlanned+" "+ getResources().getString(R.string.tasks)));
        menuAdapter.notifyDataSetChanged();
    }

    private void startIntent(MenuTask menuTask) {
        fireBaseController.getUserDataFromDataBase(fireBaseController.getToken());
        switch (menuTask.getId()) {
            case 1:

                if (this.numTaskPlanned == 0) {
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