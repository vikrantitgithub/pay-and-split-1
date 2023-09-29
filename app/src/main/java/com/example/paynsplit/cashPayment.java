package com.example.paynsplit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.UUID;

public class cashPayment extends AppCompatActivity {

    EditText amountEt, noteEt, nameEt, upiIdEt;
    Button send;
    int x=0;
    final int UPI_PAYMENT = 0;

    String finalGroupID=RecyclerViewAdapter.finalgroupID;


    private static final String TAG = "cashPayment";

    String GroupID;


    static String fetchedGroupID;

    static long count;
    String[] strArray1 = new String[1000];
    String[] strArray2 = new String[1000];


    String upiid ;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseAuth pAuth;
    private DatabaseReference pRef;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_payment);


        toastMessage("Groupid is"+finalGroupID);


        fetcheuserinformation();
        GroupID=fetchedGroupID;
        //   toastMessage("Grouoid"+GroupID+"    "+fetchedGroupID);
        amountEt = findViewById(R.id.cash_Ammount);
        noteEt = findViewById(R.id.Note);
        nameEt = findViewById(R.id.Name);
        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();



        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        send = findViewById(R.id.addcash);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = mAuth.getCurrentUser();
                final String userID = user.getUid();



                //Getting the values from the EditTexts
                String amount = amountEt.getText().toString();
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
              //  String upiId = upiIdEt.getText().toString();

                String  upiId="cash";

                addCash(amount, upiId, name, note);




            }
        });

    }

    void fetcheuserinformation()
    {
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
                        // toastMessage("group id is "+fetchedGroupID);
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


        void addCash(String amount, String upiId, String name, String note) {



                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();

                String upiid= "Cash";
                String value= amountEt.getText().toString();
                String Note= noteEt.getText().toString();

                int finalValue=Integer.parseInt(value);

                String uniqueID = UUID.randomUUID().toString();

                fetcheuserinformation();
                GroupID=fetchedGroupID;

                toastMessage("Group " +finalGroupID);
                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("Ammount").setValue(finalValue);
                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("Note").setValue(Note);
                //myRef.child(userID).child("Payment Logs").child(uniqueID).child("Payment_ID").setValue(uniqueID);
                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("UPI_ID").setValue(upiid);
                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("Name").setValue(name);

                toastMessage("Adding " + finalGroupID + " to database...");

                Intent intent = new Intent(cashPayment.this,paynsplitnavi.class);
                startActivity(intent);
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
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
