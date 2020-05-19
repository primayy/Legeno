package com.example.billage.frontend.ui.quest.subView.weekend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Weekend extends Fragment {

    public Weekend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quest_weekend, container, false);
    }
}
