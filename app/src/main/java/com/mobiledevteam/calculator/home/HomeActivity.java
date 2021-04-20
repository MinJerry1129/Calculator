package com.mobiledevteam.calculator.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.calendar.CalendarHomeActivity;
import com.mobiledevteam.calculator.cell.PayInfo;
import com.mobiledevteam.calculator.login.DiscloserActivity;
import com.mobiledevteam.calculator.login.LoginActivity;
import com.mobiledevteam.calculator.profile.UserHomeActivity;
import com.mobiledevteam.calculator.setting.SettingHomeActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private PieChart _pieMoney;
    private ImageView _dateList;
    private Button _setIncome;
    private Button _setLiability;
    private TextView _txtIncome;
    private TextView _txtLiability;
    private TextView _txtRemaining;
    private int mIncomeprice = 0;
    private int mLiabilityprice = 0;
    private int mRemainingPrice = 0;
    ArrayList<PayInfo> mAllIncomeInfo = new ArrayList<>();
    ArrayList<PayInfo> mAllLiabilityInfo = new ArrayList<>();
    private String[] parties = new String[] {
            "Remaining", "Liabilities"
    };
    private int sel_type = 8;
    private String userid;
    private int[] COLORS = {
            Color.rgb(2, 195, 154), Color.rgb(5, 102, 141)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        _pieMoney = (PieChart)findViewById(R.id.chart_Money);
        _dateList = (ImageView)findViewById(R.id.img_list);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        _setIncome = (Button)findViewById(R.id.btn_income);
        _setLiability = (Button)findViewById(R.id.btn_liabilities);
        _txtIncome = (TextView)findViewById(R.id.txt_income);
        _txtLiability = (TextView)findViewById(R.id.txt_liability);
        _txtRemaining = (TextView)findViewById(R.id.txt_remaining);
        userid = Common.getInstance().getUserID();

        _dateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        setReady();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Data...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // TODO: Implement your own signup logic here.
        JsonObject json = new JsonObject();
        json.addProperty("id",userid);
        json.addProperty("type",sel_type);

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/gethomedata")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            mAllIncomeInfo = new ArrayList<>();
                            mAllLiabilityInfo = new ArrayList<>();
                            mIncomeprice = 0;
                            mLiabilityprice = 0;

                            if (result != null) {
                                JsonArray payinfo_array = result.get("payinfo").getAsJsonArray();
                                for(JsonElement payinfoElement : payinfo_array){
                                    JsonObject thepayinfo = payinfoElement.getAsJsonObject();

                                    String id = thepayinfo.get("id").getAsString();
                                    int categoryid = thepayinfo.get("categoryid").getAsInt();
                                    String price = thepayinfo.get("price").getAsString();
                                    String paytype = thepayinfo.get("paytype").getAsString();
                                    if (paytype.equals("income")){
                                        mIncomeprice = Integer.parseInt(price) + mIncomeprice;
                                        mAllIncomeInfo.add(new PayInfo(id,categoryid,price,paytype));
                                    }else{
                                        mLiabilityprice = Integer.parseInt(price) + mLiabilityprice;
                                        mAllLiabilityInfo.add(new PayInfo(id,categoryid,price,paytype));
                                    }
//                                    mAllClinicList.add(new HomeClinic(id,name,firstname + " " +secondname ,image,description,phone,whatsapp,doctor,clinic_location));
                                }
                            } else {
                                Toast.makeText(getBaseContext(),"No info", Toast.LENGTH_LONG).show();
                            }
                            _txtIncome.setText("  " + String.valueOf(mIncomeprice) + " $");
                            _txtLiability.setText(String.valueOf(mLiabilityprice) + " $");
                            mRemainingPrice = mIncomeprice - mLiabilityprice;
                            _txtRemaining.setText(String.valueOf(mRemainingPrice) + " $");
                            if(mRemainingPrice < 0){
                                mRemainingPrice = 0;
                            }
                            setPieData();
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setReady(){
        _setIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SetIncomeActivity.class);
                startActivity(intent);
            }
        });

        _setLiability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SetLiabilityActivity.class);
                startActivity(intent);
            }
        });

    }
    private void setPieData(){
        _pieMoney.setDragDecelerationFrictionCoef(0.95f);
        _pieMoney.getDescription().setEnabled(false);
        _pieMoney.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        _pieMoney.setHoleRadius(58f);
        _pieMoney.setTransparentCircleRadius(61f);
        _pieMoney.setRotationAngle(0);
        _pieMoney.setRotationEnabled(true);
        _pieMoney.setHighlightPerTapEnabled(true);

        _pieMoney.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = _pieMoney.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        setData(2, 2);
    }
    private void showDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(HomeActivity.this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Daily");
        arrayAdapter.add("Weekly");
        arrayAdapter.add("Bi-weekly");
        arrayAdapter.add("Monthly");
        arrayAdapter.add("Semi Annually");
        arrayAdapter.add("Anually");
        arrayAdapter.add("Custom");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                sel_type = which;
                getData();
            }
        });
        builderSingle.show();
    }
    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(mRemainingPrice, parties[0]));
        entries.add(new PieEntry(mLiabilityprice, parties[1]));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : COLORS)
            colors.add(c);
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.rgb(2, 195, 154));
        _pieMoney.setData(data);

        // undo all highlights
        _pieMoney.highlightValues(null);
        _pieMoney.invalidate();
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
//        Intent intent = new Intent(this, SettingActivity.class);
//        startActivity(intent);
//        finish();
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