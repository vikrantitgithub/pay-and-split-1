package com.example.paynsplit;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class welcome extends AppCompatActivity implements View.OnClickListener {

    private EditText Name;
    private EditText Password;


    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(),allGroupsSelectAnyOne.class));
        }


        Name = (EditText)findViewById(R.id.user);
        Password = (EditText)findViewById(R.id.password);
        Button Login = (Button)findViewById(R.id.ViewTransaction);
        Button signup_button = (Button)findViewById(R.id.Signup);

        Button forgotPassword_button = (Button)findViewById(R.id.forgotpassword);


        progressDialog = new ProgressDialog(this);

        Login.setOnClickListener(this);
        signup_button.setOnClickListener(this);

        forgotPassword_button.setOnClickListener(this);

       /* Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(welcome.this,signup.class);
                startActivity(intent);
            }
        });*/
    }
    private void userLogin(){
        String email = Name.getText().toString().trim();
        String password  = Password.getText().toString().trim();


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

        progressDialog.setMessage("Loging In..... Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), allGroupsSelectAnyOne.class));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Email and Password is Not matching ! ",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ViewTransaction){
            userLogin();
        }

        if(v.getId()==R.id.Signup ){
            finish();
            startActivity(new Intent(this, signup.class));

        }
        if(v.getId()==R.id.forgotpassword)
        {
            finish();
            startActivity(new Intent(this,forgotPasswordReset.class));
        }


    }
}





/*public class welcome extends AppCompatActivity implements View.OnClickListener {


    //defining views
    private Button buttonSignIn;
   private Button buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
   // private TextView textViewSignup;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            //startActivity(new Intent(getApplicationContext(), homepage.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.user);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonSignIn = (Button) findViewById(R.id.ViewTransaction);
        buttonSignUp = (Button) findViewById(R.id.Signup);

        progressDialog = new ProgressDialog(this);

        //attaching click listener
        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
    }

    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


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

        progressDialog.setMessage("Loging In..... Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), homepage.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }

        if(view == buttonSignUp){
            finish();
            startActivity(new Intent(this, signup.class));
        }
    }
}
*/
