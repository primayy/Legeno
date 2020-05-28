package com.example.billage.frontend.ui.mypage.subView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.billage.R;
import com.example.billage.backend.api.data.UserAccount;
import com.example.billage.backend.common.Utils;
import com.example.billage.frontend.adapter.BankListAdapter;

import java.util.ArrayList;

public class CurrentBank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_current_bank);

        ListView listview = (ListView) this.findViewById(R.id.bank_icon_listview);
        ArrayList<UserAccount> user_accounts = Utils.getUserAccount();

        BankListAdapter adapter = new BankListAdapter(this, user_accounts, listview);
        listview.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        super.onBackPressed();
    }
}
