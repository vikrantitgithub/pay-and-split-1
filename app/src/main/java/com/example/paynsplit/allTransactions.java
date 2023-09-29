package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class allTransactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);


        Button personal_button = findViewById(R.id.personal);
        Button group_button = findViewById(R.id.group);



       personal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(allTransactions.this,ViewTransaction.class);
                startActivity(intent);
            }
        });

        group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(allTransactions.this,groupTransactions.class);
                startActivity(intent);
            }
        });
    }
}
