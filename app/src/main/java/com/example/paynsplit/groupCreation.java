package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class groupCreation extends AppCompatActivity {

    String UserName;
    String finalUserName=allGroupsSelectAnyOne.finalUserName;

    groupsinformation groupinfo;
    static String emailID;
    static String groupID;
   static String userID;
   static String Name;
   static String groupName;
   String finalgroupName;
    private FirebaseAuth pAuth;
    private DatabaseReference pRef;
    FirebaseAuth firebaseAuth;
    String finalgroupID;

   EditText groupNameEditText;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private static final String TAG = "groupCreation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);
        final EditText takegroupid = (EditText) findViewById(R.id.joingoupid);

        toastMessage("finalUserName"+finalUserName);

        Button homepage_button = findViewById(R.id.backtohomepage);
        Button joinGroup_button = findViewById(R.id.joingroup);
        Button createGroupButton=findViewById(R.id.creategroup);

        finalgroupID=takegroupid.getText().toString().trim();

        groupinfo = new groupsinformation();

        groupID= UUID.randomUUID().toString();

        groupNameEditText=findViewById(R.id.groupname);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userID=user.getUid();
                    emailID=user.getEmail();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in");

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };

        joinGroup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finalgroupID=takegroupid.getText().toString().trim();

                 //toastMessage("groupID"+finalgroupID);




                myRef = FirebaseDatabase.getInstance().getReference("AllGroups").child("GroupsInformation").child(finalgroupID);

             myRef.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // toastMessage(dataSnapshot.getValue().toString());
                     groupsinformation userTransaction = dataSnapshot.getValue(groupsinformation.class);
                     String testname= userTransaction.getGroupName();
                     finalgroupName=testname;

                     groupsinformation groupsinformation=new groupsinformation(finalgroupName,finalgroupID);


                  //   toastMessage(finalgroupID+finalgroupName);

                     FirebaseDatabase.getInstance().getReference("GroupsInformation")
                             .child(userID).child(finalgroupID)
                             .setValue(groupsinformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 // Toast.makeText(groupCreation.this, "Registration successful",Toast.LENGTH_LONG).show();
                                 //toastMessage("groupinformation Added");
                             } else {

                                 //display a failure message
                             }
                         }
                     });

                     userTransaction userInfo = new userTransaction(
                             userID,
                             finalUserName,
                             emailID
                     );


                     FirebaseDatabase.getInstance().getReference("MembersOfGroup")
                             .child(finalgroupID).child(userID)
                             .setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 toastMessage("GroupID is-"+finalgroupID+"\nGroupName is-"+finalgroupName+"\nemailID"+emailID);
                                 Intent intent = new Intent(groupCreation.this,allGroupsSelectAnyOne.class);
                                 startActivity(intent);


                             } else {

                                 toastMessage("Fail to join Group!");
                             }
                         }
                     });



                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {

                 }
             });

            }

        });
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                groupName=groupNameEditText.getText().toString();

                groupsinformation groupsinformation=new groupsinformation(groupName,groupID);

               userTransaction user = new userTransaction(
                       userID,
                       finalUserName,
                       emailID
               );



                FirebaseDatabase.getInstance().getReference("AllGroups").child("GroupsInformation").child(groupID)
                        .setValue(groupsinformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(groupCreation.this, "Registration successful",Toast.LENGTH_LONG).show();

                        } else {
                            //display a failure message
                        }
                    }
                });


                FirebaseDatabase.getInstance().getReference("GroupsInformation")
                        .child(userID).child(groupID)
                        .setValue(groupsinformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           toastMessage("GroupInfromation Added");

                        } else {
                            //display a failure message
                        }
                    }
                });

                userTransaction userTransaction = new userTransaction(
                        userID,
                        finalUserName,
                        emailID
                );


                FirebaseDatabase.getInstance().getReference("MembersOfGroup")
                        .child(groupID).child(userID)
                        .setValue(userTransaction).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                                 Intent intent = new Intent(groupCreation.this,allGroupsSelectAnyOne.class);
                            startActivity(intent);


                        } else {
                            //display a failure message
                        }
                    }
                });



            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
