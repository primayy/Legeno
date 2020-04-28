package com.example.billage.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.billage.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = new Intent(this.getIntent());
        String tmp = intent.getStringExtra("tmp");


        Button confirm = (Button) findViewById(R.id.confirm_btn);

        // 입력완료 버튼 클릭 이벤트 처리
        confirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // input 객체 선언
                EditText input_text1 = (EditText) findViewById(R.id.name);
                EditText input_text2 = (EditText) findViewById(R.id.nickname);

                //객체에서 입력값 가져오기
                String name = input_text1.getText().toString();
                String nickname = input_text2.getText().toString();
                Log.d("test",name);
                Log.d("test",nickname);
            }
        });
    }
}
