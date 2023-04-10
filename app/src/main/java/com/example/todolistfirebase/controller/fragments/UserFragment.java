package com.example.todolistfirebase.controller.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.activities.MainActivity;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.controller.manager.SharedPreferencesController;
import com.example.todolistfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserFragment extends Fragment {
    TextView txtEmail, txtNumberTask, txtName;
    Button btnLogOut;
    FirebaseUser firebaseAuth;
    SharedPreferencesController sharedPreferencesController;
    private FireBaseController fireBaseController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        syncronizeWidget(view);
        loadData();

        btnLogOut.setOnClickListener(v -> logOut());
        return view;
    }

    private void syncronizeWidget(View view) {
        sharedPreferencesController = new SharedPreferencesController();
        txtEmail = view.findViewById(R.id.tvUserEmail);
        txtName = view.findViewById(R.id.tvUserName);
        txtNumberTask = view.findViewById(R.id.tvUserNumberOfTask);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        fireBaseController = new FireBaseController(requireContext());
    }

    private void loadData() {
        fireBaseController.getTasksFromUser(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        User user = documentSnapshot.toObject(User.class);
                        UserFragment.this.addDataToWidget(user);
                    }
                }
            }
        });
    }

    public void addDataToWidget(User user) {
        txtEmail.setText(user.getEmail());
        txtName.setText(user.getName());
        if (user.getTasks() != null) {
            txtNumberTask.setText(String.valueOf(user.getTasks().size()));
        } else {
            txtNumberTask.setText("0");
        }
    }

    private void logOut() {
        sharedPreferencesController.deleteDateSharedPreferences(requireContext());
        FirebaseAuth.getInstance().signOut();
        requireActivity().finish();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }


}