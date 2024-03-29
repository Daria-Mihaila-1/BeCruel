package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Entities.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class RegisterActivity extends AppCompatActivity {
    private TextView emailInput;
    private TextView usernameInput;
    private TextView passwordInput;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        registerButton = findViewById(R.id.register_button);
        emailInput = findViewById(R.id.editTextTextEmailAddress);
        usernameInput = findViewById(R.id.editTextTextPersonName2);
        passwordInput = findViewById(R.id.editTextTextPassword);
        registerButton.setOnClickListener(view -> onRegister());
        progressDialog = new ProgressDialog(this);
    }

    private void onRegister(){
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
            //finish();
            //startActivity(new Intent(getApplication(),Dashboard.class));
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("wait");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).

                addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        //TODO : insert into users
                        System.out.println(email + " " + password);

                        db = FirebaseFirestore.getInstance();

                        Map<String, String> user = new HashMap<>();

                        user.put("email", email);
                        user.put("username", username);



                        db.collection("Users").document(email)
                                .set(user)
                                .addOnSuccessListener(aVoid -> System.out.println("User added"))
                                .addOnFailureListener(e -> System.out.println("Failed to add user"));

                        Toast.makeText(RegisterActivity.this,"Reg Success",Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
                        loginIntent.putExtra("register", "got registered");
                        startActivity(loginIntent);
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"register failed",Toast.LENGTH_SHORT).show();

                    }
                });
    }
}