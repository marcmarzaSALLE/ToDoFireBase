package com.example.todolistfirebase.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.adapter.ListTaskAdapter;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.Task;
import com.example.todolistfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskListFragment extends Fragment {
    private FireBaseController fireBaseController;
    private ArrayList<Task> tasks;
    RecyclerView recyclerView;


    private ListTaskAdapter listTaskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        syncronizeData(view);
        loadDataFirebaseController();
        // Inflate the layout for this fragment
        return view;
    }

    private void addDataToAdapter(ArrayList<Task> tasks) {
        listTaskAdapter = new ListTaskAdapter(tasks, requireContext(), new ListTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Log.wtf("TAG", "onItemClick: " + task.getName());
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(listTaskAdapter);
    }

    private void syncronizeData(View view) {
        fireBaseController = new FireBaseController(requireContext());
        recyclerView = view.findViewById(R.id.recyclerViewTaskList);
        tasks = new ArrayList<>();

    }

    private void loadDataFirebaseController() {
        fireBaseController.getTasksFromUser(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        ArrayList<Task> tasks1 = documentSnapshot.toObject(User.class).getTasks();
                        TaskListFragment.this.logData(tasks1);
                    }
                }
            }
        });
    }

    private void logData(ArrayList<Task> taskList) {
        this.tasks = taskList;
        for (Task t : taskList) {
            Log.wtf("TAG", "logData: " + t.getName());
        }
        addDataToAdapter(this.tasks);
    }

}
