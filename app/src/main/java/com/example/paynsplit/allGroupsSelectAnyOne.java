package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allGroupsSelectAnyOne extends AppCompatActivity {


    private static final String TAG = "allGroupsSelectAnyOne";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

static String finalUserName;
static String finalUserEmailID;
static String finalUserID;

    private ListView listView1;
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


    static int personalTotal=0;


    @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_all_groups_select_any_one);
            Log.d(TAG, "onCreate: started.");
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

        myRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userTransaction userTransaction = dataSnapshot.getValue(userTransaction.class);

                finalUserName= userTransaction.getUserName();
                finalUserEmailID=userTransaction.getEmailID();
                finalUserID=userTransaction.getUserID();

                toastMessage("UserName "+finalUserName+"\nfinalUserEmailID"+finalUserEmailID+"finalUserID"+finalUserID);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






















        myRef = FirebaseDatabase.getInstance().getReference("GroupsInformation").child(userID);

        list=new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,R.layout.listviewforgroup,R.id.groupname,list);
        //  toastMessage("Your User ID is :--"+user.getUid().toString());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getValue() != null) {
                            String myResult = child.getValue().toString();
                            String requiredString1 = myResult.substring(myResult.indexOf("{") + 1, myResult.indexOf("}"));

                            groupsinformation groupsinformation = child.getValue(com.example.paynsplit.groupsinformation.class);
                            String groupName = groupsinformation.getGroupName();
                            String groupID = groupsinformation.getGroupID();

                            initImageBitmaps(groupName + "\n," + groupID);

                        }
                        else
                        {
                            Toast.makeText(allGroupsSelectAnyOne.this, " Error GroupID Is Null", Toast.LENGTH_SHORT).show();
                        }


                    }





                }

                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button createnewgroup_button = findViewById(R.id.createnewgroup);

        createnewgroup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(allGroupsSelectAnyOne.this, groupCreation.class);
                startActivity(intent);


            }
        });


    }
    public void onBackPressed(){
        Intent intent= new Intent(this,paynsplitnavi.class);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        Intent intent= new Intent(this,paynsplitnavi.class);
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }

        private void initImageBitmaps(String groupnamenid){
            Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

            mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
            mNames.add(groupnamenid);


        }

        private void initRecyclerView(){
            Log.d(TAG, "initRecyclerView: init recyclerview.");
            RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    private void toastMessage(String message){
        Toast.makeText(allGroupsSelectAnyOne.this,message,Toast.LENGTH_SHORT).show();
    }
    }

