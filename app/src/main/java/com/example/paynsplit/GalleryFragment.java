package com.example.paynsplit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {


    String username=allGroupsSelectAnyOne.finalUserName;
    String emailID=allGroupsSelectAnyOne.finalUserEmailID;

    TextView finalUserName;
    TextView finalEmailID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_gallery, container, false);;

        finalEmailID=root.findViewById(R.id.finalUserEmailID);
        finalUserName=root.findViewById(R.id.finalUserName);

        finalUserName.setText(username);
        finalEmailID.setText(emailID);

        // Inflate the layout for this fragment

        return root;
    }

}

