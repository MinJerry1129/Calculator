package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;

import java.util.ArrayList;

public class SetGoalActivity extends AppCompatActivity {
    private Spinner _mListPeroid;
    private ArrayList<String> mPeroidCategory;
    private EditText _mName;
    private EditText _mPrice;
    private ImageView _mCancel;
    private ImageView _mAdd;
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

    }

}