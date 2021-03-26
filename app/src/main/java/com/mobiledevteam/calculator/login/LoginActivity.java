package com.mobiledevteam.calculator.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobiledevteam.calculator.MainActivity;
import com.mobiledevteam.calculator.R;

public class LoginActivity extends AppCompatActivity {
    private Button _btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _btnSign = (Button)findViewById(R.id.btn_signin);
        setReady();
    }
    private void setReady(){
        _btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DiscloserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}