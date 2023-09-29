package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPasswordReset extends AppCompatActivity {

    private EditText enteredEmail;
    private Button resetPassword_button;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_reset);

        enteredEmail=(EditText)findViewById(R.id.email);

        resetPassword_button=(Button)findViewById(R.id.reset);

        firebaseAuth=FirebaseAuth.getInstance();


        resetPassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmailID=enteredEmail.getText().toString().trim();

                if(userEmailID.equals(""))
                {
                    toastMessage("Please Enter Email ");
                }
                else
                {

                    firebaseAuth.sendPasswordResetEmail(userEmailID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                toastMessage("Email Sent Successfully !");

                      finish();

                      startActivity(new Intent(forgotPasswordReset.this,welcome.class) );
                            }
                            else{
                                toastMessage("Email Not Sent !   Try Again  !");
                            }


                        }
                    });
                }
            }
        });



    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
