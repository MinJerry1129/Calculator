package com.mobiledevteam.calculator.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.ExpandCategoryAdapter;
import com.mobiledevteam.calculator.cell.ExpandRepeatAdapter;

public class SetIncomeActivity extends AppCompatActivity {
    private ExpandableListView _expandCategory;
    private ExpandableListView _expandRepeat;
    private ExpandCategoryAdapter exCateAdapter;
    private ExpandRepeatAdapter exRepeatAdapter;
    private int categoryID=100;
    private int repeatID=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_income);
        _expandCategory = findViewById(R.id.exp_category);
        _expandRepeat = findViewById(R.id.exp_repeat);
        exCateAdapter = new ExpandCategoryAdapter(this, Common.getInstance().getmIncomeCategory());
        exRepeatAdapter = new ExpandRepeatAdapter(this, Common.getInstance().getmRepeatCategory());
        setReady();
    }
    private void setReady(){
        _expandCategory.setDivider(null);
        _expandCategory.setAdapter(exCateAdapter);
        _expandCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ViewGroup.LayoutParams params = _expandCategory.getLayoutParams();
                params.height = params.height * 6;
                _expandCategory.setLayoutParams(params);
            }
        });
        _expandCategory.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ViewGroup.LayoutParams params = _expandCategory.getLayoutParams();
                params.height = params.height / 6;
                _expandCategory.setLayoutParams(params);
            }
        });
        _expandCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                categoryID = childPosition;
                Log.d("CategoryID::", String.valueOf(categoryID));
                return true;
            }
        });

        _expandRepeat.setDivider(null);
        _expandRepeat.setAdapter(exRepeatAdapter);
        _expandRepeat.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ViewGroup.LayoutParams params = _expandRepeat.getLayoutParams();
                params.height = params.height * 7;
                _expandRepeat.setLayoutParams(params);
            }
        });
        _expandRepeat.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ViewGroup.LayoutParams params = _expandRepeat.getLayoutParams();
                params.height = params.height / 7;
                _expandRepeat.setLayoutParams(params);
            }
        });
        _expandRepeat.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                repeatID = childPosition;
                Log.d("repeatID::", String.valueOf(repeatID));
                return true;
            }
        });
    }
}