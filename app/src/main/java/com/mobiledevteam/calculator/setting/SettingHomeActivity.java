package com.mobiledevteam.calculator.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.calendar.CalendarHomeActivity;
import com.mobiledevteam.calculator.home.HomeActivity;
import com.mobiledevteam.calculator.login.LoginActivity;
import com.mobiledevteam.calculator.profile.UserHomeActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingHomeActivity extends AppCompatActivity {
    private Button _btnUsername;
    private Button _btnPassword;
    private Button _btnDiscloser;
    private Button _btnLogout;
    private String loginStatus = "no 1";
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_setting);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        _btnUsername = (Button)findViewById(R.id.btn_username);
        _btnPassword = (Button)findViewById(R.id.btn_pass);
        _btnDiscloser = (Button)findViewById(R.id.btn_discloser);
        _btnLogout = (Button)findViewById(R.id.btn_logout);
        userId = Common.getInstance().getUserID();
        setReady();
    }

    private void setReady() {
        _btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingHomeActivity.this);
                builder.setTitle("Change Username");

                final EditText input = new EditText(SettingHomeActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Input username");
                builder.setView(input);

               builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(getBaseContext(),input.getText().toString(),Toast.LENGTH_LONG).show();
                       input.getText().toString();
                   }
               });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        _btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        _btnDiscloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), ChangePasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        _btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.getInstance().setLogin_status("no");
                writeloginFile();
            }
        });
    }
    private  void writeloginFile(){
        try {
            FileOutputStream fileOutputStream = openFileOutput("loginstatus.pdm", MODE_PRIVATE);
            fileOutputStream.write(loginStatus.getBytes());
            fileOutputStream.close();

            Intent intent=new Intent(getBaseContext(), LoginActivity.class);//LoginActivity.class);
            startActivity(intent);
            finish();
        }catch (FileNotFoundException e){
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_user:
                    moveToUser();
                    return true;
                case R.id.navigation_cal:
                    moveToCal();
                    return true;
                case R.id.navigation_home:
                    moveToHome();
                    return true;
                case R.id.navigation_setting:
                    moveToSetting();
                    return true;
            }
            return false;
        }
    };
    private void moveToUser(){
        Intent intent=new Intent(this, UserHomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void moveToCal(){
        Intent intent=new Intent(this, CalendarHomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void moveToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void moveToSetting(){
//        Intent intent=new Intent(this, SettingActivity.class);
//        startActivity(intent);
//        finish();
    }
    @Override
    public void onBackPressed() {
    }
}