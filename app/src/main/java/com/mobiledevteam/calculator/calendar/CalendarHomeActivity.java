package com.mobiledevteam.calculator.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.IncomeListAdapter;
import com.mobiledevteam.calculator.cell.LiabilityListAdapter;
import com.mobiledevteam.calculator.cell.PayInfo;
import com.mobiledevteam.calculator.home.HomeActivity;
import com.mobiledevteam.calculator.profile.UserHomeActivity;
import com.mobiledevteam.calculator.setting.SettingHomeActivity;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarHomeActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private ListView _listIncome;
    private ListView _listLiability;
    private ListView _listStatus;
    ViewGroup.LayoutParams params;
    private String userid;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    ArrayList<PayInfo> mAllIncomeInfo = new ArrayList<>();
    ArrayList<PayInfo> mAllLiabilityInfo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_home);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_cal);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        _listIncome = (ListView) findViewById(R.id.list_income);
        _listStatus = (ListView) findViewById(R.id.list_status);
        _listLiability = (ListView) findViewById(R.id.list_liability);
        userid = Common.getInstance().getUserID();
        params = _listStatus.getLayoutParams();
        setReady();
        getData(dateFormat.format(new Date()));
    }
    private void getData(String date_string) {

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Data...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // TODO: Implement your own signup logic here.
        JsonObject json = new JsonObject();
        json.addProperty("id",userid);
        json.addProperty("date",date_string);

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/getCaldata")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            mAllIncomeInfo = new ArrayList<>();
                            mAllLiabilityInfo = new ArrayList<>();

                            if (result != null) {
                                JsonArray payinfo_array = result.get("payinfo").getAsJsonArray();
                                for(JsonElement payinfoElement : payinfo_array){
                                    JsonObject thepayinfo = payinfoElement.getAsJsonObject();

                                    String id = thepayinfo.get("id").getAsString();
                                    int categoryid = thepayinfo.get("categoryid").getAsInt();
                                    String price = thepayinfo.get("price").getAsString();
                                    String date_string = thepayinfo.get("date").getAsString();
                                    String paytype = thepayinfo.get("paytype").getAsString();
                                    if (paytype.equals("income")){
                                        mAllIncomeInfo.add(new PayInfo(id,categoryid,price,date_string,paytype));
                                    }else{
                                        mAllLiabilityInfo.add(new PayInfo(id,categoryid,price,date_string,paytype));
                                    }
                                }
                            } else {
                                Toast.makeText(getBaseContext(),"No info", Toast.LENGTH_LONG).show();
                            }
                            setListData();
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setReady(){
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(@NotNull EventDay eventDay) {

                String date_string = dateFormat.format(eventDay.getCalendar().getTime());
                getData(date_string);
            }
        });
    }

    private void setListData(){
        IncomeListAdapter adapter=new IncomeListAdapter(this, mAllIncomeInfo);
        _listIncome.setAdapter(adapter);
        ViewGroup.LayoutParams params_income = _listIncome.getLayoutParams();
        params_income.height = params.height * mAllIncomeInfo.size();
        _listIncome.setLayoutParams(params_income);

        LiabilityListAdapter adapter_liability=new LiabilityListAdapter(this, mAllLiabilityInfo);
        _listLiability.setAdapter(adapter_liability);
        ViewGroup.LayoutParams params_liability = _listLiability.getLayoutParams();
        params_liability.height = params.height * mAllLiabilityInfo.size();
        _listLiability.setLayoutParams(params_liability);
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