package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.login.LoginActivity;
import com.mobiledevteam.calculator.login.SignupActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetGoalActivity extends AppCompatActivity {
    private Spinner _mListPeroid;
    private ArrayList<String> mPeroidCategory;
    private EditText _mName;
    private EditText _mPrice;
    private ImageView _mCancel;
    private ImageView _mAdd;
    private String userid;
    private int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);
        _mListPeroid = (Spinner)findViewById(R.id.list_period);
        _mName = (EditText)findViewById(R.id.input_name);
        _mPrice = (EditText)findViewById(R.id.input_savemoney);
        _mCancel = (ImageView)findViewById(R.id.img_cancel);
        _mAdd = (ImageView)findViewById(R.id.img_add);
        mPeroidCategory = Common.getInstance().getmPeroidCategory();
        userid = Common.getInstance().getUserID();
        setReady();
    }

    private void setReady() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.list_period, mPeroidCategory);
        dataAdapter.setDropDownViewResource(R.layout.list_period);
        _mListPeroid.setAdapter(dataAdapter);

        _mListPeroid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedId = position;
                Log.d("selectedId: ", String.valueOf(selectedId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        _mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        _mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddSaving();
            }
        });
    }
    private void onAddSaving(){
        if(!validate()){
            return;
        }
        String name = _mName.getText().toString();
        String price = _mPrice.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Setting a Goal...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // TODO: Implement your own signup logic here.
        JsonObject json = new JsonObject();
        json.addProperty("id",userid);
        json.addProperty("name",name);
        json.addProperty("value",price);
        json.addProperty("type",selectedId);

        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/setgoal")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            if (result != null) {
                                String status = result.get("status").getAsString();
                                Log.d("log::", "yes");
                                Log.d("log123::", status);
                                if (status.equals("ok")) {
                                    Log.d("log1::", "no");
                                    Toast.makeText(getBaseContext(),"Set a goal Success!", Toast.LENGTH_LONG).show();

                                } else{
                                    Toast.makeText(getBaseContext(),"Fail set a goal!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public boolean validate() {
        boolean valid = true;
        String name = _mName.getText().toString();
        String price = _mPrice.getText().toString();
        if (name.isEmpty()) {
            _mName.setError("Input Name");
            valid = false;
        } else {
            _mName.setError(null);
        }

        if (price.isEmpty()) {
            _mPrice.setError("Input Saving money value");
            valid = false;
        } else {
            if(Integer.parseInt(price) <= 0 ){
                _mPrice.setError("Input Saving money value correctly");
                valid = false;
            }else{
                _mPrice.setError(null);
            }

        }
        return valid;
    }

}