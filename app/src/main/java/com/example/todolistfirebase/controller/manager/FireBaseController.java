package com.example.todolistfirebase.controller.manager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireBaseController {

    private boolean correct;
    public String msg;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    public int count = 0;
    private final CollectionReference collectionReference;
    private DatabaseReference databaseReference;
    SharedPreferencesController sharedPreferencesController;
    Context context;


    public FireBaseController(Context context) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        collectionReference = db.collection("ToDoList");
        sharedPreferencesController = new SharedPreferencesController();
        this.context = context;
    }

    public void createUserEmailPassword(String name, String email, String password, OnCompleteListener<AuthResult> onCompleteListener, OnFailureListener onFailureListener) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener).addOnFailureListener(onFailureListener);
    }

    public void login(String email, String password, OnCompleteListener<AuthResult> onCompleteListener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener);

    }

    public void saveToken(String token) {
        sharedPreferencesController.saveDateSharedPreferences(token, context);
    }

    public void getUserDataFromDataBase(String token) {
        db.collection("ToDoList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (Objects.equals(document.get("token"), token)) {
                            Log.wtf("TAG", document.getId() + " => " + document.getData());
                        }

                    }

                }
            }
        });
    }


    public void addTaskToUser(com.example.todolistfirebase.model.Task task, OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {
        DocumentReference documentReference = db.collection("ToDoList").document(getToken());
        documentReference.update("tasks", FieldValue.arrayUnion(task)).addOnCompleteListener(onCompleteListener).addOnFailureListener(onFailureListener);
    }

    public String getToken() {
        return sharedPreferencesController.loadDateSharedPreferences(context);
    }

    public void getTasksFromUser(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        CollectionReference collectionReference = db.collection("ToDoList");
        collectionReference.whereEqualTo("token",getToken()).get().addOnCompleteListener(onCompleteListener);

    }


}
