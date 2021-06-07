package com.mobiledevteam.calculator.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.mobiledevteam.calculator.cell.Category;
import com.mobiledevteam.calculator.cell.PayInfo;
import com.mobiledevteam.calculator.profile.SavingActivity;

import java.util.ArrayList;

public class UpdateIncomeActivity extends AppCompatActivity {
    private ImageView _mImgCategory;
    private TextView _mTxtCategory;
    private TextView _mTxtDate;
    private EditText _mTxtPrice;
    private Button _mBtnUpdate;
    private ImageView _mImgCancel;
    private ImageView _mImgDelete;

    private String sel_payid;
    private ArrayList<Category> mCategory= Common.getInstance().getmIncomeCategory();
    private int categroy_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_income);
        _mImgCategory = (ImageView)findViewById(R.id.img_category); 
        _mImgCancel = (ImageView)findViewById(R.id.img_cancel);
        _mImgDelete = (ImageView)findViewById(R.id.img_delete);
        _mTxtCategory = (TextView)findViewById(R.id.txt_category);
        _mTxtDate = (TextView)findViewById(R.id.txt_date);
        _mTxtPrice = (EditText)findViewById(R.id.txt_price);
        _mBtnUpdate = (Button)findViewById(R.id.btn_update);
        sel_payid = Common.getInstance().getSel_payinfo();
        setReady();
        getData();
    }

    private void setReady() {
        _mImgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        _mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(UpdateIncomeActivity.this);
                builder1.setMessage("Do you want delete?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Delete();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                ).show ();
            }
        });
        _mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(UpdateIncomeActivity.this);
                builder1.setMessage("Do you want update?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Update();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                ).show ();
            }
        });
    }

    private void Update() {
        if (!validate()){
            return;
        }
        String txt_price = _mTxtPrice.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Data...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject json = new JsonObject();
        json.addProperty("id", sel_payid);
        json.addProperty("price", txt_price);
        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/updatehistoryinfo")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
//                            mAllSavingList = new ArrayList<>();
                            if (result != null) {
                                Toast.makeText(getBaseContext(), "Update Success", Toast.LENGTH_LONG).show();
                            } else {

                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void Delete() {
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Get Data...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObject json = new JsonObject();
        json.addProperty("id", sel_payid);
        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/deletehistoryinfo")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
//                            mAllSavingList = new ArrayList<>();
                            if (result != null) {
                                finish();
                            } else {

                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
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
        json.addProperty("id",sel_payid);

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/getHistoryInfo")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            if (result != null) {
                                JsonObject income_object = result.getAsJsonObject("historyInfo");
                                String price = income_object.get("price").getAsString();
                                String date_string = income_object.get("date").getAsString();
                                categroy_id  = income_object.get("categoryid").getAsInt();
                                _mTxtPrice.setText(price);
                                _mTxtDate.setText(date_string);
                                _mTxtCategory.setText(mCategory.get(categroy_id).getmTitle());
                                _mImgCategory.setImageResource(mCategory.get(categroy_id).getmImageUrl());
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public boolean validate() {
        boolean valid = true;
        String value = _mTxtPrice.getText().toString();

        if (value.isEmpty()) {
            _mTxtPrice.setError("Input value");
            valid = false;
        } else {
            _mTxtPrice.setError(null);
        }

        return valid;
    }
}