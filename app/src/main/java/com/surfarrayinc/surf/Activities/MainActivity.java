package com.surfarrayinc.surf.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.surfarrayinc.surf.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButtonID);
        signupButton = findViewById(R.id.signupButtonID);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser = firebaseAuth.getCurrentUser();
                if(mUser != null){
                    startActivity(new Intent(MainActivity.this,ShowListActivity.class));
                    //Toast.makeText(MainActivity.this,"Signed in",Toast.LENGTH_LONG).show();
                }/*else if(mUser==null){
                    Toast.makeText(MainActivity.this,"Not signed in",Toast.LENGTH_LONG).show();
                }*/
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to the login page
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to the signup page
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        //passed authorization state
        mAuth.addAuthStateListener(mAuthListener);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //remove authorization state on exit
        if(mAuthListener != null){
            mAuth.signOut();
        }
    }
}
