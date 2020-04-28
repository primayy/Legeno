package com.example.billage.ui.quest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.billage.R;

public class QuestFragment extends Fragment {

    private QuestViewModel questViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questViewModel =
                ViewModelProviders.of(this).get(QuestViewModel.class);
        View root = inflater.inflate(R.layout.quest, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        questViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
