package com.mobiledevteam.calculator.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.mobiledevteam.calculator.MainActivity;
import com.mobiledevteam.calculator.R;

public class DiscloserActivity extends AppCompatActivity {
    private CheckBox _chbAgree;
    private Button _btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discloser);
        _chbAgree = (CheckBox)findViewById(R.id.check_agree);
        _btnContinue = (Button)findViewById(R.id.btn_continue);
        setReady();
    }
    private void setReady(){
        _btnContinue.setEnabled(false);
        _chbAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    _btnContinue.setEnabled(true);
                }
                else{
                    _btnContinue.setEnabled(false);
                }
            }
        });
        _btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscloserActivity.this, TermActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}