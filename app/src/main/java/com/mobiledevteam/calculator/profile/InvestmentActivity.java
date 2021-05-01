package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.ArrayList;

public class InvestmentActivity extends AppCompatActivity {
    private Spinner _mListInvest;
    private ArrayList<String> mInvestmentCategory;
    private EditText _mPrice;
    private ImageView _mCancel;
    private ImageView _mAdd;
    private String userid;
    private int selectedId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        _mListInvest = (Spinner)findViewById(R.id.list_investment);
        _mPrice = (EditText)findViewById(R.id.input_investmoney);
        _mCancel = (ImageView)findViewById(R.id.img_cancel);
        _mAdd = (ImageView)findViewById(R.id.img_add);
        mInvestmentCategory = Common.getInstance().getmInvestmentCategory();
        userid = Common.getInstance().getUserID();
        setReady();
    }
    private void setReady() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.list_period, mInvestmentCategory);
        dataAdapter.setDropDownViewResource(R.layout.list_period);
        _mListInvest.setAdapter(dataAdapter);

        _mListInvest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        String price = _mPrice.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Bright_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Setting a Investment...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // TODO: Implement your own signup logic here.
        JsonObject json = new JsonObject();
        json.addProperty("id",userid);
        json.addProperty("value",price);
        json.addProperty("type",selectedId);


        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/setinvestment")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            Log.d("result::", result.toString());
                            if (result != null) {
                                String status = result.get("status").getAsString();
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
        String price = _mPrice.getText().toString();

        if (price.isEmpty()) {
            _mPrice.setError("Input investment money value");
            valid = false;
        } else {
            if(Integer.parseInt(price) <= 0 ){
                _mPrice.setError("Input investment money value correctly");
                valid = false;
            }else{
                _mPrice.setError(null);
            }

        }
        return valid;
    }
}