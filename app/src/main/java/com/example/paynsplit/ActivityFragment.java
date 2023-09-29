package com.example.paynsplit;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent= new Intent(getActivity(), allGroupsSelectAnyOne.class);
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_notice, container, false);
    }

}
