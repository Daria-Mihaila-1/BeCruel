package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

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
        if (firebaseAuth.getCurrentUser() != null && getIntent() == null) {
            finish();
            Intent friendsListIntent  = new Intent(getApplicationContext(), MainPageActivity.class );
            friendsListIntent.putExtra("email", firebaseAuth.getCurrentUser().getEmail());
            startActivity(friendsListIntent);
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
                        //System.out.println("can't get in");
                        //System.out.println(email +  " " + password);
                        if(task.isSuccessful()){
                            pd.dismiss();
                            finish();

                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("email", email);
                            myEdit.commit();
                            Intent friendsListIntent  = new Intent(getApplicationContext(), MainPageActivity.class );
                            startActivity(friendsListIntent);
                        }
                        else {
                            pd.dismiss();
                            Log.w(TAG, "singInWithEmailAndPassword:failure", task.getException());
                            ;}
                    }
                });

    }

}