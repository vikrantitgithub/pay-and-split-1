package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class homepage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    //view objects
    private static final String TAG = "homepage";
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth pAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference pRef;
    FirebaseDatabase database;
    static String GroupID;
    static String fetchedGroupID;
    static long count;

    String finalGroupID;


    String[] strArray1 = new String[1000];
    String[] strArray2 = new String[1000];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        pAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        pRef = mFirebaseDatabase.getReference();
        final FirebaseUser user =pAuth.getCurrentUser();

        Button pay_button = findViewById(R.id.pay);
        Button ViewTransaction_button = findViewById(R.id.ViewTransaction);
        Button AddMember_button = findViewById(R.id.Signup);
        Button SignOut_button = findViewById(R.id.signout);
        Button getgroupid_button = findViewById(R.id.getgroupid);



        finalGroupID=RecyclerViewAdapter.finalgroupID;
        toastMessage("Groupidis"+finalGroupID);




        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   GroupID=fetcheuserinformation();
                Intent intent = new Intent(homepage.this,pay.class);
                startActivity(intent);
            }
        });

        ViewTransaction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GroupID=fetcheuserinformation();
                Intent intent = new Intent(homepage.this,allTransactions.class);
                startActivity(intent);
            }
        });

        AddMember_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  GroupID=fetcheuserinformation();
                Intent intent = new Intent(homepage.this,viewGroupMembers.class);
                startActivity(intent);
            }
        });
        SignOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //upiGroupID= fetcheuserinformation();
                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(homepage.this,welcome.class);
                startActivity(intent);
            }
        });
        getgroupid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // fetcheuserinformation();

                final String userID = user.getUid();




                pRef = FirebaseDatabase.getInstance().getReference("Users");

                pRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            int j=0;
                        count = dataSnapshot.getChildrenCount();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {


                            String userInfromation=child.getValue().toString();

                            String array[] =new String[3];

                            array=userInfromation.split(",");



                            GroupID = array[0].substring(array[0].indexOf("=") + 1);
                            String getUSer=array[2].substring(array[2].indexOf("=")+1,array[2].length()-1);




                            strArray1[j]= GroupID;
                            strArray2[j]=getUSer;

                           j++;
                        }


                        //toastMessage("  "+userID+" , "+strArray2[1]+"  "+count);

                        for(int i=0;i<count;i++)
                        {

                            if(strArray2[i].equals(userID))
                            {
                                fetchedGroupID = strArray1[i];
                                toastMessage("group id is "+fetchedGroupID);
                            }
                            else
                            {
                                //toastMessage("trying");
                            }


                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

   String fetcheuserinformation()
    {

        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        pAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        pRef = mFirebaseDatabase.getReference();
        final FirebaseUser user =pAuth.getCurrentUser();

        final String userID = user.getUid();




        pRef = FirebaseDatabase.getInstance().getReference("Users");

        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int j=0;
                count = dataSnapshot.getChildrenCount();
                for (DataSnapshot child : dataSnapshot.getChildren()) {


                    String userInfromation=child.getValue().toString();

                    String array[] =new String[3];

                    array=userInfromation.split(",");



                    GroupID = array[0].substring(array[0].indexOf("=") + 1);
                    String getUSer=array[2].substring(array[2].indexOf("=")+1,array[2].length()-1);




                    strArray1[j]= GroupID;
                    strArray2[j]=getUSer;

                    j++;
                }


                //toastMessage("  "+userID+" , "+strArray2[1]+"  "+count);

                for(int i=0;i<count;i++)
                {

                    if(strArray2[i].equals(userID))
                    {
                        fetchedGroupID = strArray1[i];
                        toastMessage("group id is "+fetchedGroupID);


                    }
                    else
                    {
                        //toastMessage("trying");
                    }


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


return fetchedGroupID;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
