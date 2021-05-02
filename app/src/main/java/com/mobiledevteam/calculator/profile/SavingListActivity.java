package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.AllInvestmentAdapter;
import com.mobiledevteam.calculator.cell.AllSavingListAdapter;
import com.mobiledevteam.calculator.cell.Investment;
import com.mobiledevteam.calculator.cell.Saving;

import java.util.ArrayList;

public class SavingListActivity extends AppCompatActivity {
    private GridView _allGridView;
    ArrayList<Saving> mAllSavingList = new ArrayList<>();
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_list);
        _allGridView = (GridView)findViewById(R.id.grid_allsaving);
        userid = Common.getInstance().getUserID();
        _allGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SavingActivity.class).putExtra("saving_id", mAllSavingList.get(position).getmId());
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
                    .load(Common.getInstance().getBaseURL()+"api/getSavingsInfo")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
                            mAllSavingList = new ArrayList<>();
                            if (result != null) {
                                Log.d("result::", result.toString());
                                JsonArray savings_array = result.get("savingInfo").getAsJsonArray();
                                for(JsonElement savingElement : savings_array){
                                    JsonObject thesaving = savingElement.getAsJsonObject();
                                    String id = thesaving.get("id").getAsString();
                                    String name = thesaving.get("name").getAsString();
                                    String startdate = thesaving.get("startdate").getAsString();
                                    String enddate = thesaving.get("enddate").getAsString();
                                    int type = thesaving.get("type").getAsInt();
                                    String price = thesaving.get("price").getAsString();
                                    mAllSavingList.add(new Saving(id,name,startdate,enddate,type,price));
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
        AllSavingListAdapter adapter_saving = new AllSavingListAdapter(getBaseContext(), mAllSavingList);
        _allGridView.setAdapter(adapter_saving);
    }
}