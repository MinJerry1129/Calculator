package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.AllInvestmentAdapter;
import com.mobiledevteam.calculator.cell.Investment;

import java.util.ArrayList;

public class InvestmentListActivity extends AppCompatActivity {
    private GridView _allGridView;
    ArrayList<Investment> mAllInvestList = new ArrayList<>();
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_list);
        _allGridView = (GridView)findViewById(R.id.grid_allinvest);
        userid = Common.getInstance().getUserID();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InvestmentListActivity.this, InvestmentActivity.class);
                startActivity(intent);
            }
        });

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
        JsonObject json = new JsonObject();
        json.addProperty("id", userid);
        try {
            Ion.with(this)
                    .load(Common.getInstance().getBaseURL()+"api/getInvestInfo")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            mAllInvestList = new ArrayList<>();
                            if (result != null) {
                                Log.d("result::", result.toString());
                                JsonArray invests_array = result.get("investInfo").getAsJsonArray();
                                for(JsonElement investElement : invests_array){
                                    JsonObject theinvest = investElement.getAsJsonObject();
                                    String id = theinvest.get("id").getAsString();
                                    int type = theinvest.get("type").getAsInt();
                                    String date = theinvest.get("date").getAsString();
                                    String price = theinvest.get("price").getAsString();

                                    mAllInvestList.add(new Investment(id,price,type,date));
                                }
                                initView();
                            } else {

                            }
                        }
                    });
        }catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void initView(){
        AllInvestmentAdapter adapter_invest = new AllInvestmentAdapter(getBaseContext(), mAllInvestList);
        _allGridView.setAdapter(adapter_invest);
    }
}