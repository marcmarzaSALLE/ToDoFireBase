package com.example.todolistfirebase.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    EditText txtName, txtEmail, txtPassword, txtConfirmPassword;
    Button btnRegister;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    DocumentReference documentReference;

    CollectionReference collectionReference;

    DatabaseReference databaseReference;

    FireBaseController fireBaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        syncronizeFirebase();
        syncronizeWidget();
        btnRegister.setOnClickListener(v -> {
            writeNewUser( txtName.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString());
        });
    }

    private void syncronizeFirebase() {
        fireBaseController = new FireBaseController(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        collectionReference = firebaseFirestore.collection("ToDoList");
    }

    private void syncronizeWidget() {
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void writeNewUser(String name, String email, String password) {
        if (validateInputs(name, email, password, txtConfirmPassword.getText().toString()) == 0) {
            //TODO: Comprobar q no exista el usuario dentro de la funcion createUserEmailPassword
            fireBaseController.createUserEmailPassword(name, email, password);
            //TODO: Hacer un if para comprobar si se ha creado el usuario, si es que si se guarda en sharedpreferences el token y se redirige a la actividad principal
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }


    private int validateInputs(String txtName, String txtEmail, String txtPassword, String txtConfirmPassword) {
        if (txtName.isEmpty() || txtEmail.isEmpty() || txtPassword.isEmpty() || txtConfirmPassword.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if (!txtPassword.equals(txtConfirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return 1;
        }
        return 0;
    }
}