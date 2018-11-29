package com.surfarrayinc.surf.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.images.ImageRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.surfarrayinc.surf.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText emailedt;
    private EditText passwordedt;
    private Button loginButton;
    private TextView forogtpswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set all attributes to the respective ids
        emailedt = findViewById(R.id.emailLoginID);
        passwordedt = findViewById(R.id.passwordLoginID);
        loginButton = findViewById(R.id.loginLoginButtonID);
        forogtpswd = findViewById(R.id.fogotLoginID);

        //intialize mAuth
        mAuth = FirebaseAuth.getInstance();

        //check if user is already logged in
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(emailedt.getText().toString())
                        && !TextUtils.isEmpty(passwordedt.getText().toString())){
                    //email and password are not empty then:
                    String email = emailedt.getText().toString();
                    String pwd = passwordedt.getText().toString();
                    login(email,pwd);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Enter the email and paasword",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Success",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, ShowListActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"Wrong Password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
