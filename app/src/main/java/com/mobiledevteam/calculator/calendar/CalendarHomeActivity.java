package com.mobiledevteam.calculator.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.home.HomeActivity;
import com.mobiledevteam.calculator.profile.UserHomeActivity;
import com.mobiledevteam.calculator.setting.SettingHomeActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarHomeActivity extends AppCompatActivity {
    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_cal);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setReady();
    }
    private void setReady(){
//        calendarView.setOnDayClickListener(new OnDayClickListener() {
//            @Override
//            public void onDayClick(@NotNull EventDay eventDay) {
//                Toast.makeText(getApplicationContext(), eventDay.getCalendar().getTime().toString(), Toast.LENGTH_LONG).show();
//            }
//        });
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
//        Intent intent=new Intent(this, SettingActivity.class);
//        startActivity(intent);
//        finish();
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