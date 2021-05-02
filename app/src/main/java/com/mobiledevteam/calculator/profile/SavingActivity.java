package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.Saving;
import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SavingActivity extends AppCompatActivity {
    private TextView _mTotalDay;
    private TextView _mSpentDay;
    private TextView _mSave;
    private TextView _mTarget;
    private CircularProgressView _mMonthProgress;
    private CircularProgressView _mSaveProgress;
    private ImageView _mDelete;
    private ImageView _mCheck;

    private String userid;
    private String savingid;
    int daysTBetween = 0;
    int daysSBetween = 0;
    int totalincome = 0;
    int liability = 0;
    int remain = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);
        userid = Common.getInstance().getUserID();
        savingid = getIntent().getStringExtra("saving_id");
        _mTotalDay = (TextView)findViewById(R.id.txt_totalday);
        _mSpentDay = (TextView)findViewById(R.id.txt_spentday);
        _mTarget = (TextView)findViewById(R.id.txt_target);
        _mSave = (TextView)findViewById(R.id.txt_save);
        _mMonthProgress = (CircularProgressView) findViewById(R.id.progress_month);
        _mSaveProgress = (CircularProgressView) findViewById(R.id.progress_money);
        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Data...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject json = new JsonObject();
        json.addProperty("id", userid);
        json.addProperty("savingid", savingid);
        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/getSavingInfo")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
//                            mAllSavingList = new ArrayList<>();
                            if (result != null) {
                                Log.d("result::", result.toString());
                                JsonObject saving_object = result.getAsJsonObject("savingInfo");
                                String startdate = saving_object.get("startdate").getAsString();
                                String enddate = saving_object.get("enddate").getAsString();
                                String price = saving_object.get("price").getAsString();
                                _mTarget.setText(price);
                                Date mstartdate;
                                Date menddate;
                                Date mcurrentdate= Calendar.getInstance().getTime();

                                SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
                                String todayString = dates.format(mcurrentdate);

                                try {
                                    mstartdate = dates.parse(startdate);
                                    menddate = dates.parse(enddate);
                                    mcurrentdate = dates.parse(todayString);
                                    long tdifference = menddate.getTime() - mstartdate.getTime();
                                    long sdifference = mcurrentdate.getTime() - mstartdate.getTime();
                                    daysTBetween = (int) (tdifference / (1000*60*60*24));
                                    daysSBetween = (int) (sdifference / (1000*60*60*24));
                                    _mTotalDay.setText(String.valueOf(daysTBetween));
                                    if(daysSBetween>=daysTBetween){
                                        daysSBetween = daysTBetween;
                                    }
                                    _mMonthProgress.setProgress((int) ((float)daysSBetween/(float) daysTBetween*100), true);
                                    _mSpentDay.setText(String.valueOf(daysSBetween));

                                } catch (ParseException parseException) {
                                    parseException.printStackTrace();
                                }


                                JsonArray payments_array = result.get("paymentinfo").getAsJsonArray();
                                for(JsonElement paymentElement : payments_array){
                                    JsonObject thepayment = paymentElement.getAsJsonObject();
                                    String type = thepayment.get("paytype").getAsString();
                                    String hprice = thepayment.get("price").getAsString();
                                    if(type.equals("income")){
                                        totalincome = totalincome + Integer.parseInt(hprice);
                                    }else{
                                        liability = liability + Integer.parseInt(hprice);
                                    }

                                }
                                remain = totalincome - liability;
                                _mSaveProgress.setProgress((int) ((float)remain/Float.parseFloat(price)*100), true);
                                Log.d("total income:",String.valueOf(totalincome) );
                                Log.d("total liability:",String.valueOf(liability) );
                                Log.d("total liability:",String.valueOf(liability) );
                                _mSave.setText(String.valueOf(remain));
                            } else {

                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}