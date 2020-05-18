package com.example.billage.frontend.ui.mypage.subView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.billage.R;
public class SetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_set_password);
    }
    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        super.onBackPressed();
    }
}
