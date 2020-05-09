package com.example.billage.frontend.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.billage.R;
import com.example.billage.backend.JSONTask_Post;


public class SignupActivity extends AppCompatActivity {

    TextView tvData;

    public void settvData(String data){
        this.tvData.setText(data);
    }

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

                try {
                    //서버에 JSON data post
                    JSONObject signupData = new JSONObject();
                    signupData.accumulate("name", name);
                    signupData.accumulate("nickname",nickname);
                    JSONTask_Post jsonTask= new JSONTask_Post(signupData);
                    jsonTask.execute("http://192.168.0.9:3000/UserBack/SignUp");

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}


