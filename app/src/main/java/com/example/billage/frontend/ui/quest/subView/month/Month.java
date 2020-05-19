package com.example.billage.frontend.ui.quest.subView.month;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Month extends Fragment {

    public Month() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.quest_month, container, false);
        return root;
    }
}
