package com.example.paynsplit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    Button buttonlogin;
    private ProgressDialog progressDialog;

    static String groupID;
    static String userID;
    static String emailID;

    EditText editTextUserName;
  static String userName;


    private static final String TAG = "signup";

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName=(EditText)findViewById(R.id.userName);




        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        buttonlogin = (Button) findViewById(R.id.login);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        buttonlogin.setOnClickListener(this);
     //   btn.setOnClickListener(this);


    }


    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        userName=editTextUserName.getText().toString().trim();


        //checking if email and passwords are empty

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        toastMessage(userName);
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success


                        FirebaseUser fuser = firebaseAuth.getCurrentUser();
                        if (fuser != null) {
                            userID=fuser.getUid();
                            emailID=fuser.getEmail();
                            Log.d(TAG, "onAuthStateChanged:signed_in:" + fuser.getUid());
                           // toastMessage("Successfully signed in");

                        } else {
                            // User is signed out
                            Log.d(TAG, "onAuthStateChanged:signed_out");

                        }

                        if(task.isSuccessful()){
                            userTransaction user = new userTransaction(
                                    userID,
                                    userName,
                                    emailID
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signup.this, "Registration successful",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(signup.this,groupCreation.class);
                                        startActivity(intent);

                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        }else{
                            //display some message here

                            Toast.makeText(signup.this,"User Already Exits !",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });



    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup){
            registerUser();


        }
        if(view.getId()==R.id.login) {
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, allGroupsSelectAnyOne.class));
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}