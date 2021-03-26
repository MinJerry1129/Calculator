package com.mobiledevteam.calculator.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.home.HomeActivity;

public class TermActivity extends AppCompatActivity {
    private Button _btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        _btnContinue = (Button)findViewById(R.id.btn_continue);
        setReady();
    }
    private void setReady(){
        _btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}