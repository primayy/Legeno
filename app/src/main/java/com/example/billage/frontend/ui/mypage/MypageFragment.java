package com.example.billage.frontend.ui.mypage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.billage.R;
import com.example.billage.backend.GetSetADUserInfo;
import com.example.billage.frontend.ui.auth.AuthActivity;
import com.example.billage.frontend.ui.mypage.subView.AccountModify;
import com.example.billage.frontend.ui.mypage.subView.CurrentBank;
import com.example.billage.frontend.ui.mypage.subView.SetNotify;
import com.example.billage.frontend.ui.mypage.subView.SetPassword;

public class MypageFragment extends Fragment {

    private MypageViewModel mypageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mypageViewModel = ViewModelProviders.of(this).get(MypageViewModel.class);
        View root = inflater.inflate(R.layout.mypage_main, container, false);



        GetSetADUserInfo getSetADUserInfo = new GetSetADUserInfo();
        TextView account_info = root.findViewById(R.id.account_info);
        account_info.setText(String.format("%s (%s)", getSetADUserInfo.getUserInfo("user_name"), getSetADUserInfo.getUserInfo("nickname")));

        setTextviewClickEvent(root);



        return root;
    }

    private void setTextviewClickEvent(View root) {
        ImageView nickname_modify = root.findViewById(R.id.nickname_img);
        TextView account_modify = root.findViewById(R.id.account_modify);
        ImageView account_modify_img = root.findViewById(R.id.edit_img);
        TextView add_bank = root.findViewById(R.id.add_bank);
        TextView current_bank = root.findViewById(R.id.current_bank);
        TextView set_password = root.findViewById(R.id.app_password);
        TextView set_notify = root.findViewById(R.id.notification);

        nickname_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setNicknameDialog();
            }
        });

        account_modify_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setIntroDialog();
            }
        });

        account_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setIntroDialog();
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

    private void setIntroDialog() {

        final EditText intro_input = new EditText(getContext());

        FrameLayout container = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 100;
        params.rightMargin = 100;

        intro_input.setLayoutParams(params);
        container.addView(intro_input);

        AlertDialog.Builder intro_dialog = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
        intro_dialog.setTitle("소개글 편집").setMessage("Billage 소개글을 입력하세요").setIcon(R.drawable.edit);
        intro_dialog.setView(container);

        intro_dialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String intro = intro_input.getText().toString();//입력받은값 저장하는 point
                //To-do
                dialogInterface.dismiss();
            }
        });

        intro_dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        intro_dialog.show();
    }

    private void setNicknameDialog() {

        final EditText nickname_input = new EditText(getContext());

        FrameLayout container = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 100;
        params.rightMargin = 100;

        nickname_input.setLayoutParams(params);
        container.addView(nickname_input);

        AlertDialog.Builder nickname_dialog = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle);
        nickname_dialog.setTitle("닉네임 변경").setMessage("변경할 닉네임을 입력하세요").setIcon(R.drawable.nickname_setting);
        nickname_dialog.setView(container);

        nickname_dialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String nick_name = nickname_input.getText().toString();//입력받은값 저장하는 point
                //To-do
                dialogInterface.dismiss();
            }
        });

        nickname_dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        nickname_dialog.show();
    }


}
