package com.example.todolistfirebase.controller.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class AddTaskFragment extends Fragment {
    Button btnAddTask;
    EditText edtTaskName, edtTaskDescription, edtTaskDate, edtTaskTime;

    FireBaseController fireBaseController;

    Spinner spinnerTypeTask;
    List<String> typeTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        syncronizedWidget(view);
        adaptateSpinner();
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = edtTaskName.getText().toString();
                String taskDescription = edtTaskDescription.getText().toString();
                if (taskName.isEmpty() || taskDescription.isEmpty()) {
                    if (taskName.isEmpty())
                        edtTaskName.setError(getResources().getString(R.string.fillField));
                    if (taskDescription.isEmpty())
                        edtTaskDescription.setError(getResources().getString(R.string.fillField));
                } else {
                    checkDateTime();
                }
            }
        });

        edtTaskDate.setOnClickListener(v -> {
            showDateDialog();
        });
        edtTaskTime.setOnClickListener(v -> {
            showTimeDialog();
        });
        return view;
    }

    private void syncronizedWidget(View view) {
        fireBaseController = new FireBaseController(requireContext());
        btnAddTask = view.findViewById(R.id.btnSaveTask);
        edtTaskName = view.findViewById(R.id.edtTextTitleTask);
        edtTaskDescription = view.findViewById(R.id.edtDescriptionTask);
        edtTaskDate = view.findViewById(R.id.edtDateTask);
        edtTaskTime = view.findViewById(R.id.edtTimeTask);
        spinnerTypeTask = view.findViewById(R.id.spnCategory);
        typeTask = Arrays.asList(getResources().getStringArray(R.array.typeTask));
    }

    private void adaptateSpinner() {
        spinnerTypeTask.setAdapter(new ArrayAdapter<String>(requireContext(), R.layout.spinner_layout, typeTask));
    }

    private void showDateDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate;
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                String format = "dd/MM/yyyy";
                selectedDate = new SimpleDateFormat(format).format(cal.getTime());
                edtTaskDate.setText(selectedDate);
            }
        });

        newFragment.show(getParentFragmentManager(), "datePicker");
    }

    private void showTimeDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedTime = hourOfDay + ":" + minute;
                edtTaskTime.setText(selectedTime);
            }
        });
        newFragment.show(getParentFragmentManager(), "timePicker");

    }

    private void checkDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar dateTimeCal = Calendar.getInstance();
        String dateText = edtTaskDate.getText().toString();
        String timeText = edtTaskTime.getText().toString();
        String finalDate = dateText + " " + timeText;
        try {
            dateTimeCal.setTime(dateFormat.parse(finalDate));
            if (dateTimeCal.compareTo(calendar) > 0) {
                getDataWidget();
            } else {
                Toast.makeText(requireContext(), getResources().getString(R.string.errorDate), Toast.LENGTH_SHORT).show();

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getDataWidget() {
        String taskName = edtTaskName.getText().toString();
        String taskDescription = edtTaskDescription.getText().toString();
        String taskDate = edtTaskDate.getText().toString();
        String taskTime = edtTaskTime.getText().toString();
        String taskType = spinnerTypeTask.getSelectedItem().toString();
        Task task = new Task(taskType,taskName, taskDescription, taskDate, taskTime);
        fireBaseController.addTaskToUser(task, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(com.google.android.gms.tasks.Task<AuthResult> t) {
                if (t.isSuccessful()) {
                    Toast.makeText(requireContext(), getResources().getString(R.string.taskAddedSuccessfully), Toast.LENGTH_SHORT).show();
                    Fragment fragment = new MenuListFragment();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment).addToBackStack(null).commit();
                } else {
                    Toast.makeText(requireContext(), getResources().getString(R.string.taskNotAdded), Toast.LENGTH_SHORT).show();
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(requireContext(), getResources().getString(R.string.taskNotAdded), Toast.LENGTH_SHORT).show();
            }
        });
    }


}