package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewGroups extends AppCompatActivity {
    private static final String TAG = "ViewTransaction";
    private ListView listView1;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ArrayList<String> list;
    ArrayAdapter<String>adapter;
    userTransaction transaction;
    String userID;
    int value=0;
    int tempadd=0;
    static int personalToatal=0;
    static int flagPersonalVisited=0;
    private TextView textpersonalTotal;

    private Button getTheBill;


    static int personalTotal=0;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);


        textpersonalTotal = (TextView)findViewById(R.id.showTransactionToatal);

        flagPersonalVisited=1;

        transaction=new userTransaction();

        listView1=(ListView)findViewById(R.id.listView1);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        //toastMessage("user iD is "+userID);

        String finalGroupID=RecyclerViewAdapter.finalgroupID;

     //   toastMessage(finalGroupID);



        list=new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,R.layout.listviewforgroup,R.id.groupname,list);
        //  toastMessage("Your User ID is :--"+user.getUid().toString());
        myRef = FirebaseDatabase.getInstance().getReference("MembersOfGroup")
                .child(finalGroupID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot child:dataSnapshot.getChildren())
                {

                    userTransaction userTransaction=child.getValue(com.example.paynsplit.userTransaction.class);
                    String testing=userTransaction.userName;
                    list.add(testing);
                    toastMessage(testing);
                }
                listView1.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button createnewgroup_button = findViewById(R.id.creategroup);

        createnewgroup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(viewGroups.this, AddMember.class);
                    startActivity(intent);


            }
        });


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(viewGroups.this, list.get(position),Toast.LENGTH_SHORT).show();

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
