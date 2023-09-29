package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class makePayment extends AppCompatActivity {

    static String groupID=homepage.fetchedGroupID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        //Button phonepay_button = findViewById(R.id.phonepay);
       // Button googlepay_button = findViewById(R.id.googlepay);
        Button paytm_button = findViewById(R.id.paytm);
        //Button bhim_button = findViewById(R.id.bhim);

       /* phonepay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = "com.phonepe.app";
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                if(intent != null) {
                    startActivity(intent);
                }

            }
        });

        googlepay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = "com.andriod.vending";
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                if(intent != null) {
                    startActivity(intent);
                }

            }
        });*/

        paytm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = "net.one97.paytm";
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                if(intent != null) {
                    startActivity(intent);
                }

            }
        });
      /* bhim_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageName = "com.phonepe.app";
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                if(intent != null) {
                    startActivity(intent);
                }

            }
        });*/
    }
}
