package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button goToRegisterBtn;
    private TextView usernameInput;
    private TextView passwordInput;
    ProgressDialog pd;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            //todo redirect to profile or some shit like that
            //startActivity(new Intent(getApplicationContext(), Dashboard.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //start the login
        goToRegisterBtn = findViewById(R.id.goToRegister_button);
        pd = new ProgressDialog(this);
        usernameInput =  findViewById(R.id.editTextTextPersonName);
        passwordInput = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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
                        if(task.isSuccessful()){
                            finish();
                            //todo redirect to the next activity
                            //startActivity(new Intent(getApplicationContext(), ));
                        }
                    }
                });
    }

}