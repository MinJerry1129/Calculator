package com.mobiledevteam.calculator.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiledevteam.calculator.R;

public class InvestmentListActivity extends AppCompatActivity {
    private GridView _allGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_list);
        _allGridView = (GridView)findViewById(R.id.grid_allinvest);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InvestmentListActivity.this, InvestmentActivity.class);
                startActivity(intent);
            }
        });
    }
}