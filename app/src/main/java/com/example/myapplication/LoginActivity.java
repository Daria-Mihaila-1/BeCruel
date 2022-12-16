package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button goToRegisterBtn;
    private TextView usernameInput;
    private TextView passwordInput;
    ProgressDialog pd;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Button loginButton;

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            //todo redirect to profile or some shit like that
            startActivity(new Intent(getApplicationContext(), FeedActivity.class));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput =  findViewById(R.id.editTextTextPersonName);
        loginButton = findViewById(R.id.login_button);

        goToRegisterBtn = findViewById(R.id.goToRegister_button);
        passwordInput = findViewById(R.id.editTextTextPassword);

        firebaseAuth = FirebaseAuth.getInstance();


        //start the login
        pd = new ProgressDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                userLogin();

            }
        });
        goToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    public void userLogin(){

        String email = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter pass", Toast.LENGTH_SHORT).show();
            return;
        }
        pd.setMessage("wait");
        pd.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        System.out.println("can't get in");
                        System.out.println(email +  " " + password);
                        if(task.isSuccessful()){
                            pd.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), FeedActivity.class));

                        }
                        else {
                            Log.w(TAG, "singInWithEmailAndPassword:failure", task.getException());
                            ;}
                    }
                });

    }

}