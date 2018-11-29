package com.surfarrayinc.surf.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.surfarrayinc.surf.R;
import com.surfarrayinc.surf.model.user;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText location;
    private EditText paswd;
    private CheckBox checkBox;
    private Button submitButton;

    private ProgressDialog dialog;

    private FirebaseAuth mAuth;

    private DatabaseReference mPostReference;
    private FirebaseUser mUser;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.nameSignupID);
        email = findViewById(R.id.emailSignupID);
        phone = findViewById(R.id.phoneSignupID);
        location = findViewById(R.id.locationSignupID);
        paswd = findViewById(R.id.passwordSignupID);
        checkBox = findViewById(R.id.termsSignupID);
        submitButton = findViewById(R.id.submitSignupID);

        dialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mPostReference = mDatabase.getReference().child("User");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uemail = email.getText().toString();
                String upaswd = paswd.getText().toString();

                if (!uemail.equals("") && !upaswd.equals("")) {
                    mAuth.createUserWithEmailAndPassword(uemail, upaswd).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //store the data into user and go to login page
                                        saveToDB();
                                        Toast.makeText(SignupActivity.this, "success",
                                                Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(SignupActivity.this, "failed", Toast.LENGTH_LONG).show();
                                        //show toast message
                                    }
                                }
                            });
                }
            }
        });
    }


    private void saveToDB() {
        dialog.setMessage("Saving...");
        dialog.show();

        String uname = name.getText().toString();
        String uemail = email.getText().toString();
        String uphone = phone.getText().toString();
        String ulocation = location.getText().toString();

        mUser = mAuth.getCurrentUser();

        if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(uemail)
                && !TextUtils.isEmpty(uphone) && !TextUtils.isEmpty(ulocation)){

            DatabaseReference newPost = mPostReference.push();

            Map<String,String> data = new HashMap<>();

            data.put("Name",uname);
            data.put("Email",uemail);
            data.put("Phone",uphone);
            data.put("Location",ulocation);
            data.put("UserID",mUser.getUid());

            newPost.setValue(data);
            dialog.dismiss();
        }
    }
}
