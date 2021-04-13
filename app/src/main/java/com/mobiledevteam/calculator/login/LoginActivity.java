package com.mobiledevteam.calculator.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobiledevteam.calculator.MainActivity;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.Category;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button _btnSign;
    private ArrayList<Category> mIncomeCategory = new ArrayList<>();
    private ArrayList<Category> mLiabilityCategory = new ArrayList<>();
    private ArrayList<String> mRepeatCategort = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _btnSign = (Button)findViewById(R.id.btn_signin);
        setReady();
    }
    private void setReady(){

        mIncomeCategory.add(new Category("0","Salary","salary"));
        mIncomeCategory.add(new Category("1","Business Profit","business"));
        mIncomeCategory.add(new Category("2","Project","project"));
        mIncomeCategory.add(new Category("3","Royalty","royalty"));
        mIncomeCategory.add(new Category("4","Other","other"));

        mLiabilityCategory.add(new Category("0","Condo Fee","condo"));
        mLiabilityCategory.add(new Category("1","Property Taxes","property"));
        mLiabilityCategory.add(new Category("2","Mortgage Payment","mortgage"));
        mLiabilityCategory.add(new Category("3","Cell Phone","phone"));
        mLiabilityCategory.add(new Category("4","Car Gas","gas"));
        mLiabilityCategory.add(new Category("5","Car Insurance","insurance"));
        mLiabilityCategory.add(new Category("6","Electric Bill","electric"));
        mLiabilityCategory.add(new Category("7","Groceries","groceries"));
        mLiabilityCategory.add(new Category("8","Gas/Heating","heating"));
        mLiabilityCategory.add(new Category("9","Tv/Internet","tv"));
        mLiabilityCategory.add(new Category("10","Water","water"));
        mLiabilityCategory.add(new Category("11","Secured Line of Credit","secured"));

        mRepeatCategort.add("Once");
        mRepeatCategort.add("Every Day");
        mRepeatCategort.add("Every Week");
        mRepeatCategort.add("Every Month");
        mRepeatCategort.add("Semi Anually");
        mRepeatCategort.add("Anually");

        Common.getInstance().setmIncomeCategory(mIncomeCategory);
        Common.getInstance().setmLiabilityCategory(mLiabilityCategory);
        Common.getInstance().setmRepeatCategory(mRepeatCategort);

        _btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DiscloserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}