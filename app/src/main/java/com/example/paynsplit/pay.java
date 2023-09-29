package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class pay extends AppCompatActivity {

String finalGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);



        finalGroupId=RecyclerViewAdapter.finalgroupID;
        Toast.makeText(this, "groupid "+ finalGroupId, Toast.LENGTH_SHORT).show();

        Button makepayment_button = findViewById(R.id.makepayment);

        Button addcash_button = findViewById(R.id.cash);


        Button upipay_button = findViewById(R.id.upi);

        makepayment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pay.this,makePayment.class);
                startActivity(intent);
            }
        });
        upipay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pay.this,upipayment.class);
                startActivity(intent);
            }
        });


        addcash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pay.this,cashPayment.class);
                startActivity(intent);
            }
        });

    }
}
