package com.example.paynsplit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.UUID;

public class upipayment extends AppCompatActivity {

    EditText amountEt, noteEt, nameEt, upiIdEt;
    Button send;
    int x=0;
    final int UPI_PAYMENT = 0;

    private static final String TAG = "upipayment";

    String GroupID;

    String finalGroupID=RecyclerViewAdapter.finalgroupID;


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
        setContentView(R.layout.activity_upipayment);

        toastMessage("GroupID "+finalGroupID);

        initializeViews();

        Button Qrscan = findViewById(R.id.qrscan);
        Qrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(upipayment.this,scannigQRcode.class);
                startActivity(intent);

            }
        });


        Intent intent =getIntent();
        upiid=intent.getStringExtra("UPI ID");
           upiIdEt.setText(upiid);
     //   Toast.makeText(upipayment.this,"Scanned upi id is  "+upiid,Toast.LENGTH_LONG).show();

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


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pRef = FirebaseDatabase.getInstance().getReference("Users");

                pRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            toastMessage("Child count:--"+dataSnapshot.getChildrenCount());


                            toastMessage("Your User ID is :--"+child.getValue());


                            String userInfromation=child.getValue().toString();

                             GroupID = userInfromation.substring(userInfromation.indexOf("=") + 1, userInfromation.indexOf(","));

                            //toastMessage("GroupID   "+GroupID);


                        }




                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                FirebaseUser user = mAuth.getCurrentUser();
                final String userID = user.getUid();



                //Getting the values from the EditTexts
                String amount = amountEt.getText().toString();
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
                String upiId = upiIdEt.getText().toString();

                //String userID = user.getUid();

                payUsingUpi(amount, upiId, name, note);




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


    void initializeViews() {
        fetcheuserinformation();
        GroupID=fetchedGroupID;
     //   toastMessage("Grouoid"+GroupID+"    "+fetchedGroupID);
        send = findViewById(R.id.addcash);
        amountEt = findViewById(R.id.cash_Ammount);
        noteEt = findViewById(R.id.Note);
        nameEt = findViewById(R.id.Name);
        upiIdEt = findViewById(R.id.cashpay);
    }
    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(upipayment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(upipayment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(upipayment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);


                Log.d(TAG, "onClick: Attempting to add object to database.");
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();

                String upiid= upiIdEt.getText().toString();
                String value= amountEt.getText().toString();
                String Note= noteEt.getText().toString();
                String Name= nameEt.getText().toString();

                int finalValue=Integer.parseInt(value);

                String uniqueID = UUID.randomUUID().toString();

                fetcheuserinformation();
                GroupID=fetchedGroupID;

                toastMessage("Group " +finalGroupID);
                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("Name").setValue(Name);

                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("Ammount").setValue(finalValue);
                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("Note").setValue(Note);
                //myRef.child(userID).child("Payment Logs").child(uniqueID).child("Payment_ID").setValue(uniqueID);
                myRef.child(finalGroupID).child(userID).child("Payment Logs").child(uniqueID).child("UPI_ID").setValue(upiid);


                toastMessage("Adding " + finalGroupID + " to database...");

                Intent intent = new Intent(upipayment.this,paynsplitnavi.class);
                startActivity(intent);

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(upipayment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Toast.makeText(upipayment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);



                Log.d(TAG, "onClick: Attempting to add object to database.");
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();

                String upiid= upiIdEt.getText().toString();
                String value= amountEt.getText().toString();
                String Note= noteEt.getText().toString();

                int finalValue=Integer.parseInt(value);
                fetcheuserinformation();
                GroupID=fetchedGroupID;

               toastMessage("Payment Cancelled  !");

//             toastMessage("Group " + GroupID);

                Intent intent = new Intent(upipayment.this,paynsplitnavi.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(upipayment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(upipayment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
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


/*

class upipayment extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upipayment);

        initializeViews();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                String amount = amountEt.getText().toString();
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
                String upiId = upiIdEt.getText().toString();
                payUsingUpi(amount, upiId, name, note);
            }
        });
    }

    void initializeViews() {
        send = findViewById(R.id.send);
        amountEt = findViewById(R.id.amount_et);
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upiIdEt = findViewById(R.id.upi_id);
    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(upipayment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(upipayment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(upipayment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(upipayment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(upipayment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(upipayment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
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


}*/