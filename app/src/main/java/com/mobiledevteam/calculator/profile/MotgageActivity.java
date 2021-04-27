package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiledevteam.calculator.R;

public class MotgageActivity extends AppCompatActivity {
    private EditText _mAmount;
    private EditText _mRate;
    private EditText _mPeriod;
    private TextView _mTotal;
    private TextView _mMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motgage);
        _mAmount = (EditText)findViewById(R.id.input_amount);
        _mRate = (EditText)findViewById(R.id.input_rate);
        _mPeriod = (EditText)findViewById(R.id.input_period);
        _mTotal = (TextView)findViewById(R.id.txt_totalcost);
        _mMonthly = (TextView)findViewById(R.id.txt_monthlycost);
        setReady();
    }

    private void setReady() {
        _mAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                cal_Mortgage();
            }
        });
        _mPeriod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                cal_Mortgage();
            }
        });
        _mRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                cal_Mortgage();
            }
        });
    }

    private void cal_Mortgage(){
        if (_mAmount.getText().toString().equals("")){
            _mTotal.setText("0");
            _mMonthly.setText("0");
            return;
        }
        if (_mPeriod.getText().toString().equals("")){
            _mTotal.setText("0");
            _mMonthly.setText("0");
            return;
        }
        if (_mRate.getText().toString().equals("")){
            _mTotal.setText("0");
            _mMonthly.setText("0");
            return;
        }
        Double amount = Double.parseDouble(_mAmount.getText().toString());
        Double rate = Double.parseDouble(_mRate.getText().toString());
        Double period =Double.parseDouble( _mPeriod.getText().toString());
        Double interst_rate = rate / 1200.0;

        Log.d("result_rate::", String.format("%.9f",interst_rate));

        Double total_month = period * 12.0;
        Double value_c = Math.pow(interst_rate + 1 , total_month);
        Log.d("result_pw::", String.format("%.2f",value_c));

        Double value_monthly;
        value_monthly = amount *(interst_rate*value_c)/(value_c-1);
        Log.d("result::", String.format("%.2f",value_monthly));
        _mTotal.setText(String.format("%.2f",value_monthly*total_month));
        _mMonthly.setText(String.format("%.2f",value_monthly));

    }
}