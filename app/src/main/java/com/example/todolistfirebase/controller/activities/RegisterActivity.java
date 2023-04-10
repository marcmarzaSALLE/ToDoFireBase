package com.example.todolistfirebase.controller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText editTxtName, editTxtEmail, editTxtPassword, editTxtConfirmPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
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
            writeNewUser(editTxtName.getText().toString(), editTxtEmail.getText().toString(), editTxtPassword.getText().toString());
        });
    }

    private void syncronizeFirebase() {
        fireBaseController = new FireBaseController(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        collectionReference = firebaseFirestore.collection("ToDoList");
    }

    private void syncronizeWidget() {
        editTxtName = findViewById(R.id.txtName);
        editTxtEmail = findViewById(R.id.txtEmail);
        editTxtPassword = findViewById(R.id.txtPassword);
        editTxtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void writeNewUser(String name, String email, String password) {
        if (validateInputs(name, email, password, editTxtConfirmPassword.getText().toString()) == 0) {
            fireBaseController.createUserEmailPassword(name, email, password,  new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String token = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        collectionReference.document(token).set(new User(name, email, token, password));
                        fireBaseController.saveToken(token);
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }

                }
            },new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    editTxtEmail.setError("El email ya existe");
                    Toast.makeText(RegisterActivity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                }
            });
            //TODO: Hacer un if para comprobar si se ha creado el usuario, si es que si se guarda en sharedpreferences el token y se redirige a la actividad principal

        }
    }


    private int validateInputs(String txtName, String txtEmail, String txtPassword, String txtConfirmPassword) {
        if (txtName.isEmpty() || txtEmail.isEmpty() || txtPassword.isEmpty() || txtConfirmPassword.isEmpty()) {
            if (txtName.isEmpty())
                this.editTxtName.setError("El nombre es obligatorio");

            if (txtEmail.isEmpty())
                this.editTxtEmail.setError("El email es obligatorio");

            if (txtPassword.isEmpty())
                this.editTxtPassword.setError("La contraseña es obligatoria");

            if (txtConfirmPassword.isEmpty())
                this.editTxtConfirmPassword.setError("La confirmación de la contraseña es obligatoria");

            return 1;
        }
        if (!txtPassword.equals(txtConfirmPassword)) {
            this.editTxtPassword.setError("Las contraseñas no coinciden");
            this.editTxtConfirmPassword.setError("Las contraseñas no coinciden");
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return 1;
        }
        return 0;
    }
}