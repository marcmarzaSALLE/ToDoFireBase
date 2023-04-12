package com.example.todolistfirebase.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistfirebase.R;
import com.example.todolistfirebase.controller.manager.FireBaseController;
import com.example.todolistfirebase.model.User;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    ImageButton btnGoogle;

    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    DocumentReference documentReference;

    CollectionReference collectionReference;


    DatabaseReference databaseReference;

    FireBaseController fireBaseController;

    private SignInClient onTapClient;
    private BeginSignInRequest signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        syncronizeFirebase();
        syncronizeWidget();
        btnGoogle.setOnClickListener(v -> {
            onTapClient = Identity.getSignInClient(this);
            signInRequest = new BeginSignInRequest.Builder()
                    .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                            .setSupported(true)
                            .build())
                    .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                            .setSupported(true)
                            .setServerClientId(getString(R.string.default_web_client_id))
                            .build()).setAutoSelectEnabled(true).build();


        });
        btnRegister.setOnClickListener(v -> {
            writeNewUser(editTxtName.getText().toString(), editTxtEmail.getText().toString(), editTxtPassword.getText().toString());
        });
    }

    private void autenticacionGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
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
        btnGoogle = findViewById(R.id.btnGoogle);
    }

    private void writeNewUser(String name, String email, String password) {
        if (validateInputs(name, email, password, editTxtConfirmPassword.getText().toString()) == 0) {
            fireBaseController.createUserEmailPassword(name, email, password, new OnCompleteListener<AuthResult>() {
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
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    editTxtEmail.setError(getResources().getString(R.string.emailExist));
                }
            });
            //TODO: Hacer un if para comprobar si se ha creado el usuario, si es que si se guarda en sharedpreferences el token y se redirige a la actividad principal

        }
    }


    private int validateInputs(String txtName, String txtEmail, String txtPassword, String txtConfirmPassword) {
        if (txtName.isEmpty() || txtEmail.isEmpty() || txtPassword.isEmpty() || txtConfirmPassword.isEmpty()) {
            if (txtName.isEmpty())
                this.editTxtName.setError(getResources().getString(R.string.nameRequired));

            if (txtEmail.isEmpty())
                this.editTxtEmail.setError(getResources().getString(R.string.emailRequired));

            if (txtPassword.isEmpty())
                this.editTxtPassword.setError(getResources().getString(R.string.passwordRequired));

            if (txtConfirmPassword.isEmpty())
                this.editTxtConfirmPassword.setError(getResources().getString(R.string.confirmPasswordRequired));

            return 1;
        }
        if (!txtPassword.equals(txtConfirmPassword)) {
            this.editTxtPassword.setError(getResources().getString(R.string.passwordNotMatch));
            this.editTxtConfirmPassword.setError(getResources().getString(R.string.passwordNotMatch));
            return 1;
        }
        return 0;
    }
}