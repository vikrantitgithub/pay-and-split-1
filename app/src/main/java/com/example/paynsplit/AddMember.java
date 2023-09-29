package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMember extends AppCompatActivity {

    String finalGroupID=RecyclerViewAdapter.finalgroupID;

    static String fetchedGroupID ;
    static long count;


    String[] strArray1 = new String[1000];
    String[] strArray2 = new String[1000];

    DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth pAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference pRef;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Button sharebutton = (Button) findViewById(R.id.share);

        Button bakctohomepage = (Button) findViewById(R.id.backtohomepage);

        toastMessage("group id is "+finalGroupID);


        sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fetcheuserinformation();
            }
        });

        bakctohomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMember.this,paynsplitnavi.class);
                startActivity(intent);
            }
        });


    }

    void fetcheuserinformation()
    {

        toastMessage("group id is "+finalGroupID);




                        Intent shareintent = new Intent(Intent.ACTION_SEND);

                        shareintent.setType("text/pain");
                        shareintent.putExtra(Intent.EXTRA_TEXT,"Use This Group_ID And Join My Group ON Pay N Split App  \n\n"+"*"+finalGroupID+"*");

                        startActivity(shareintent.createChooser(shareintent,"Share"));


    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }




}