package com.example.paynsplit;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {


    private FirebaseAuth firebaseAuth;

    //view objects
    private static final String TAG = "homepage";
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth pAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference pRef;
    private DatabaseReference myRef;
    FirebaseDatabase database;
    static String GroupID;
    static String fetchedGroupID;
    static long count;


    static String finalUserName=allGroupsSelectAnyOne.finalUserName;
    static String finalUserEmailID=allGroupsSelectAnyOne.finalUserEmailID;
    static String finalUserID=allGroupsSelectAnyOne.finalUserID;


    String[] strArray1 = new String[1000];
    String[] strArray2 = new String[1000];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        pAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        pRef = mFirebaseDatabase.getReference();

        myRef = mFirebaseDatabase.getReference();

        final FirebaseUser user =pAuth.getCurrentUser();

        View root = inflater.inflate(R.layout.fragment_notice, container, false);


        Button pay_button = root.findViewById(R.id.pay);
        Button ViewTransaction_button = root.findViewById(R.id.ViewTransaction);
        Button AddMember_button =root.findViewById(R.id.Signup);
        Button SignOut_button = root.findViewById(R.id.signout);
        Button getgroupid_button =root.findViewById(R.id.getgroupid);


        toastMessage("Hiiii");

        final String userID = user.getUid();

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





        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carmode = new Intent(getContext(), pay.class);
                startActivity(carmode);
            }
        });



        ViewTransaction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GroupID=fetcheuserinformation();
                Intent intent = new Intent(getActivity(),allTransactions.class);
                startActivity(intent);
            }
        });

        AddMember_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  GroupID=fetcheuserinformation();
                Intent intent = new Intent(getActivity(),viewGroups.class);
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



        // Inflate the layout for this fragment
        return root;


    }
    private void toastMessage(String message){
     Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
