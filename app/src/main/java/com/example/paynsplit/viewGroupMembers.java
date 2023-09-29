package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class viewGroupMembers extends AppCompatActivity {

    private static final String TAG = "ViewTransaction";
    private ListView listView;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    userTransaction transaction;
    String userID;
    int value=0;
    int tempadd=0;
    static int personalToatal=0;
    static int flagPersonalVisited=0;
    private TextView textpersonalTotal;

    private Button getTheBill;

    private FirebaseAuth pAuth;
    private DatabaseReference pRef;

    String finalGroupID=RecyclerViewAdapter.finalgroupID;



    FirebaseAuth firebaseAuth;

    String GroupID;

    static String fetchedGroupID ;
    static long count;

    String[] emailID=new String[1000];
    int j=0;
    String[] strArray1 = new String[1000];
    String[] strArray2 = new String[1000];



    static int personalTotal=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_members);



        listView =(ListView)findViewById(R.id.listView1);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        Button getTheBill_button = findViewById(R.id.creategroup);



        getTheBill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference(finalGroupID).child(userID);


                dbNode.setValue(null);

                toastMessage("Deleted");


                Intent intent = new Intent(viewGroupMembers.this,viewGroups.class);
                    startActivity(intent);

            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();


    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}