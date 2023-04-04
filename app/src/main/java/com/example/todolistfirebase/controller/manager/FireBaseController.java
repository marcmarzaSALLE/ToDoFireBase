package com.example.todolistfirebase.controller.manager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.todolistfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class FireBaseController {

    private boolean correct = false;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
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

    public void createUserEmailPassword(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String token = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                if (task.isSuccessful()) {
                    collectionReference.document(token).set(new User(name, email, token, password));
                    FireBaseController.this.saveToken(token);
                }
            }
        }).addOnFailureListener(e -> {
            Log.wtf("WTF", e.getMessage());

        });
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String token = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    FireBaseController.this.saveToken(token);
                }
            }
        });
    }

    public void saveToken(String token) {
        sharedPreferencesController.saveDateSharedPreferences(token, context);
    }
}
