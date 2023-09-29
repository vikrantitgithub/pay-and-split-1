package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class getTheBillAndSplitTheBill extends AppCompatActivity {
    String finalGroupID=RecyclerViewAdapter.finalgroupID;
    String UserID=NoticeFragment.finalUserID;
    long perMemberContribution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_the_bill_and_split_the_bill);
        TextView billview = (TextView)findViewById(R.id.bill);
        Button backToHomepage =(Button)findViewById(R.id.backtohomepage);
        Button clearBill =(Button)findViewById(R.id.clearTheBill);
        finalGroupID=RecyclerViewAdapter.finalgroupID;
       UserID=NoticeFragment.finalUserID;

        int GroupTotal=groupTransactions.groupTotal;
        int PersonalTotal=ViewTransaction.personalTotal;
        long groupMemberCount=groupTransactions.groupMemberCount;

        if(groupMemberCount!=0) {
            perMemberContribution = GroupTotal / groupMemberCount;
        }
        else
        {
           perMemberContribution =0;
        }

        Toast.makeText(getApplicationContext(),"Number Of Group Members"+groupMemberCount,Toast.LENGTH_LONG).show();



//        Toast.makeText(getApplicationContext(),"Hey mayur here"+PersonalTotal,Toast.LENGTH_LONG).show();


        if(PersonalTotal<perMemberContribution)
        {
            long  ammountToPayed=(perMemberContribution-PersonalTotal);
            billview.setText("Group Total Is :-  "+GroupTotal+"\n\nPersonal Contribution  :-  "+PersonalTotal+"\n\nPer Mermber\nContribution is "+perMemberContribution+"\n\nYou Have To pay "+ammountToPayed);

        }
        else if(perMemberContribution<PersonalTotal) {

            long  ammountToRecived=(PersonalTotal-perMemberContribution);
            billview.setText("Group Total Is :-  "+GroupTotal+"\n\nPersonal Contribution  :-  "+PersonalTotal+"\n\nPer Mermber\nContribution is "+perMemberContribution+"\n\nYou Have To Recive "+ammountToRecived);

        }
        else if(perMemberContribution==PersonalTotal)
        {
            billview.setText("Group Total Is :-  "+GroupTotal+"\n\nPersonal Contribution  :-  "+PersonalTotal+"\n\nPer Mermber\nContribution is "+perMemberContribution+"\n\n Great Job ! \nNothing to pay Or Recived !");

        }

        backToHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getTheBillAndSplitTheBill.this,paynsplitnavi.class);
                startActivity(intent);
            }
        });

        clearBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference(finalGroupID);


                dbNode.setValue(null);

                Intent intent =new Intent(getTheBillAndSplitTheBill.this,paynsplitnavi.class);
                startActivity(intent);

            }
        });

    }
}
