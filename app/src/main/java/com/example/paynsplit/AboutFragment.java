package com.example.paynsplit;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    private FirebaseAuth firebaseAuth;

    //view objects
    private static final String TAG = "homepage";
    private TextView textViewUserEmail;
    private Button buttonLogout;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth pAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference pRef;
    FirebaseDatabase database;
    static String GroupID;
    static String fetchedGroupID;
    static long count;

    String finalGroupID;


    String[] strArray1 = new String[1000];
    String[] strArray2 = new String[1000];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root=inflater.inflate(R.layout.fragment_notice, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        pAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        pRef = mFirebaseDatabase.getReference();
        final FirebaseUser user =pAuth.getCurrentUser();

        Button pay_button = root.findViewById(R.id.pay);
        Button ViewTransaction_button = root.findViewById(R.id.ViewTransaction);
        Button AddMember_button = root.findViewById(R.id.Signup);
        Button SignOut_button = root.findViewById(R.id.signout);
        Button getgroupid_button = root.findViewById(R.id.getgroupid);



        finalGroupID=RecyclerViewAdapter.finalgroupID;


        firebaseAuth.signOut();
        getActivity().finish();
        Intent intent = new Intent(getActivity(),welcome.class);
        startActivity(intent);


        return root;
    }

}
