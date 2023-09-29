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

public class groupTransactions extends AppCompatActivity {

    private ListView listView;
    FirebaseDatabase database;

    String finalGroupID=RecyclerViewAdapter.finalgroupID;
    DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth pAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference pRef;
    private FirebaseAuth mAuth;

    FirebaseAuth firebaseAuth;
    static int flagGoupVisited=0;
    static int groupTotal=0;
    static long groupMemberCount=0;
   TextView showGroupTransactions;

    ArrayList<String> list;
    ArrayAdapter<String>adapter;
    userTransaction transaction;
    String userID;
    String GroupID;

    private Button getTheBill;
    static String fetchedGroupID ;
    static long count;
    int value=0;
    int tempadd;
    String[] emailID=new String[1000];
    int j=0;
    String[] strArray1 = new String[1000];
    String[] strArray2 = new String[1000];



    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);


        toastMessage("GroupID id "+finalGroupID);
        flagGoupVisited=1;
        showGroupTransactions=(TextView)findViewById(R.id.showTransactionToatal);
        transaction=new userTransaction();

        listView =(ListView)findViewById(R.id.listView1);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        pAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        pRef = mFirebaseDatabase.getReference();
        final FirebaseUser groupuser =pAuth.getCurrentUser();

        final String userID = groupuser.getUid();

        Button getTheBill_button = findViewById(R.id.creategroup);
        fetcheuserinformation();
        getTheBill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag=ViewTransaction.flagPersonalVisited;
                if(flag!=0) {

                    Intent intent = new Intent(groupTransactions.this, getTheBillAndSplitTheBill.class);
                    startActivity(intent);
                }
                else
                {
                    toastMessage("Visit Personal Transactions");
                }

            }
        });




    }

    void getGroupTranscation ()
    {

        list=new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,R.layout.list_view,R.id.groupname,list);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               groupMemberCount= dataSnapshot.getChildrenCount();

              //  toastMessage("Number Of Members In your Group "+groupMemberCount);
j=0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    // toastMessage("Your User ID is :--" + child.getValue());


                    for (DataSnapshot child1 : child.getChildren()) {


                        for (DataSnapshot child2 : child1.getChildren()) {
                            String myResult = child2.getValue().toString();

                            String userInfromation=child2.getValue().toString();


                            String requiredString1 = myResult.substring(myResult.indexOf("{") + 1, myResult.indexOf("}"));

                            //list.add(emailID[j]+requiredString1);
                            list.add(requiredString1);

                            j++;
                            String array[] =new String[3];

                            array=requiredString1.split(",");


                            String array1[] =new String[3];


                            array1=array[0].split("=");

                             value = Integer.parseInt((array1[1]));
                            tempadd=tempadd+value;


                        }

                    }


                }
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        if(position==0)
                        {
                            Intent intent= new Intent(groupTransactions.this,homepage.class);
                        }


                    }
                });

                groupTotal=tempadd;
                tempadd=0;
                showGroupTransactions.setText("Group Total is  "+groupTotal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    void fetcheuserinformation()
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
                int k=0;
                j=0;
                count = dataSnapshot.getChildrenCount();
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    userTransaction userTransaction = child.getValue(com.example.paynsplit.userTransaction.class);
                    emailID[j]= userTransaction.getEmailID();
                    j++;
                  //  toastMessage("email id is "+ab);
                    String userInfromation=child.getValue().toString();

                    String array[] =new String[3];

                    array=userInfromation.split(",");



                    GroupID = array[0].substring(array[0].indexOf("=") + 1);
                    String getUSer=array[2].substring(array[2].indexOf("=")+1,array[2].length()-1);




                    strArray1[k]= GroupID;
                    strArray2[k]=getUSer;

                    k++;
                }


                //toastMessage("  "+userID+" , "+strArray2[1]+"  "+count);

                for(int i=0;i<count;i++)
                {

                    if(strArray2[i].equals(userID))
                    {
                        fetchedGroupID = strArray1[i];
                        myRef = FirebaseDatabase.getInstance().getReference(finalGroupID);
                        getGroupTranscation ();

                        //toastMessage("group id is "+fetchedGroupID);
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



    @Override
    protected void onStart() {

        super.onStart();



    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}