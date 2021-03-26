package com.mobiledevteam.calculator.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.calendar.CalendarHomeActivity;
import com.mobiledevteam.calculator.home.HomeActivity;
import com.mobiledevteam.calculator.setting.SettingHomeActivity;

public class UserHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_user);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
//        Intent intent=new Intent(this, UserHomeActivity.class);
//        startActivity(intent);
//        finish();
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
        Intent intent=new Intent(this, SettingHomeActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
    }
}