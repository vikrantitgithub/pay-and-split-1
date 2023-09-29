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

public class ViewTransaction extends AppCompatActivity {

    private static final String TAG = "ViewTransaction";
    private ListView listView;
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
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);


        textpersonalTotal = (TextView)findViewById(R.id.showTransactionToatal);

        flagPersonalVisited=1;

        toastMessage("GroupID is"+finalGroupID);

        transaction=new userTransaction();

        listView =(ListView)findViewById(R.id.listView1);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        database=FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

       toastMessage("user iD is "+userID);

        myRef = FirebaseDatabase.getInstance().getReference(userID);

        list=new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,R.layout.list_view,R.id.groupname,list);
      //  toastMessage("Your User ID is :--"+user.getUid().toString());


        fetcheuserinformation();

            Button getTheBill_button = findViewById(R.id.creategroup);

            getTheBill_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int flag = groupTransactions.flagGoupVisited;

                    if (flag != 0) {

                        Intent intent = new Intent(ViewTransaction.this, getTheBillAndSplitTheBill.class);
                        startActivity(intent);
                    } else {
                        toastMessage("Visit Group Transactions");
                    }
                }
            });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewTransaction.this, list.get(position),Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void viewpersonalTransactions() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        for (DataSnapshot child1 : child.getChildren()) {
                            String myResult = child1.getValue().toString();
                            String requiredString1 = myResult.substring(myResult.indexOf("{") + 1, myResult.indexOf("}"));
                            list.add(requiredString1);
                            String array[] = new String[3];

                            array = requiredString1.split(",");


                            String array1[] = new String[3];


                            array1 = array[0].split("=");

                            value = Integer.parseInt((array1[1]));

                            tempadd = tempadd + value;


                        }
                    }
                    listView.setAdapter(adapter);


                    personalTotal = tempadd;
                    tempadd = 0;
                    textpersonalTotal.setText("personal total is " + personalTotal);
                }


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
                        myRef = FirebaseDatabase.getInstance().getReference(finalGroupID).child(userID);
                        viewpersonalTransactions();

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