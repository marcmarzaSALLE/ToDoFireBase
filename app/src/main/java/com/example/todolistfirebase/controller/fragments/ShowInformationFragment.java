package com.example.todolistfirebase.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.model.Task;


public class ShowInformationFragment extends Fragment {
    private Task task;
    private TextView txtTaskName, txtTaskDescription, txtTaskDate, txtTaskTime;

    private ImageView imgTypeTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            task = (Task) bundle.getSerializable("task");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_information, container, false);
        syncronizedWidget(view);
        getDataWidget();

        return view;
    }

    private void syncronizedWidget(View view) {
        txtTaskName = view.findViewById(R.id.txtNameTask);
        txtTaskDescription = view.findViewById(R.id.txtDescription);
        txtTaskDate = view.findViewById(R.id.txtEndDate);
        txtTaskTime = view.findViewById(R.id.txtHours);
        imgTypeTask = view.findViewById(R.id.imgIconType);
    }

    private void getDataWidget() {
        txtTaskName.setText(task.getName());
        txtTaskDescription.setText(task.getDescription());
        txtTaskDate.setText(task.getDate());
        txtTaskTime.setText(task.getTime());
        if(task.getTypeTask().equals("")||task.getTypeTask()==null){
            imgTypeTask.setImageResource(R.drawable.baseline_more_horiz_64);
        }else {
            imgTypeTask.setImageResource(task.getIconOfTypeTask());
        }
    }
}