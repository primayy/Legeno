package com.example.billage.frontend.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.billage.R;
import com.example.billage.backend.GetADUserInfo;
import com.example.billage.frontend.ui.auth.AuthActivity;
import com.example.billage.frontend.ui.mypage.subView.AccountModify;
import com.example.billage.frontend.ui.mypage.subView.AddBank;
import com.example.billage.frontend.ui.mypage.subView.CurrentBank;
import com.example.billage.frontend.ui.mypage.subView.SetNotify;
import com.example.billage.frontend.ui.mypage.subView.SetPassword;
import com.example.billage.frontend.ui.signup.SignupActivity;

import org.w3c.dom.Text;

public class MypageFragment extends Fragment {

    private MypageViewModel mypageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mypageViewModel = ViewModelProviders.of(this).get(MypageViewModel.class);
        View root = inflater.inflate(R.layout.mypage_main, container, false);



        GetADUserInfo getADUserInfo = new GetADUserInfo();
        TextView account_info = root.findViewById(R.id.account_info);
        account_info.setText(String.format("%s (%s)", getADUserInfo.getUserInfo("user_name"), getADUserInfo.getUserInfo("nickname")));

        setTextviewClickEvent(root);

        return root;
    }

    private void setTextviewClickEvent(View root) {
        TextView account_modify = root.findViewById(R.id.account_modify);
        TextView add_bank = root.findViewById(R.id.add_bank);
        TextView current_bank = root.findViewById(R.id.current_bank);
        TextView set_password = root.findViewById(R.id.app_password);
        TextView set_notify = root.findViewById(R.id.notification);

        account_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AccountModify.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AuthActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        current_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CurrentBank.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        set_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SetPassword.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        set_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SetNotify.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }


}
