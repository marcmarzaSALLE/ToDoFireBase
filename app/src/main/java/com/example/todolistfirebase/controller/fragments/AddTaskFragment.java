package com.example.todolistfirebase.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;


public class AddTaskFragment extends Fragment {
    Button btnAddTask;
    EditText edtTaskName, edtTaskDescription;

    FireBaseController fireBaseController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        syncronizedWidget(view);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = edtTaskName.getText().toString();
                String taskDescription = edtTaskDescription.getText().toString();
                if (taskName.isEmpty() || taskDescription.isEmpty()) {
                    if (taskName.isEmpty())
                        edtTaskName.setError("Please fill this field");
                    if (taskDescription.isEmpty())
                        edtTaskDescription.setError("Please fill this field");
                } else {
                    getDataWidget();
                }
            }
        });
        return view;
    }

    private void syncronizedWidget(View view) {
        fireBaseController = new FireBaseController(requireContext());
        btnAddTask = view.findViewById(R.id.btnSaveTask);
        edtTaskName = view.findViewById(R.id.edtTextTitleTask);
        edtTaskDescription = view.findViewById(R.id.edtDescriptionTask);
    }

    private void getDataWidget() {
        String taskName = edtTaskName.getText().toString();
        String taskDescription = edtTaskDescription.getText().toString();
        Task task = new Task(taskName, taskDescription);
        fireBaseController.addTaskToUser(task, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(com.google.android.gms.tasks.Task<AuthResult> t) {
                if (t.isSuccessful()) {
                    Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new MenuListFragment();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment).addToBackStack(null).commit();
                } else {
                    Toast.makeText(requireContext(), "Task not added", Toast.LENGTH_SHORT).show();
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(requireContext(), "Task not added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}