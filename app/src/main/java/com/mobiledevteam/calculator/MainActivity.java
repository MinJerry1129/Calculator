package com.mobiledevteam.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.mobiledevteam.calculator.Utils.Common;
import com.mobiledevteam.calculator.cell.Category;
import com.mobiledevteam.calculator.home.HomeActivity;
import com.mobiledevteam.calculator.login.LoginActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Category> mIncomeCategory = new ArrayList<>();
    private ArrayList<Category> mLiabilityCategory = new ArrayList<>();
    private ArrayList<String> mRepeatCategort = new ArrayList<>();

    private ArrayList<String> mInvestmentCategory = new ArrayList<>();
    private String loginCheck = "no 1";
    private String login_status = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReady();
        readFile();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToLogin();
            }
        },4000);
    }
    private void setReady(){
        mIncomeCategory.add(new Category("0","Salary",R.drawable.salary));
        mIncomeCategory.add(new Category("1","Business Profit",R.drawable.business));
        mIncomeCategory.add(new Category("2","Project",R.drawable.project));
        mIncomeCategory.add(new Category("3","Royalty",R.drawable.royalty));
        mIncomeCategory.add(new Category("4","Other",R.drawable.other));

        mLiabilityCategory.add(new Category("0","Condo Fee",R.drawable.condo));
        mLiabilityCategory.add(new Category("1","Property Taxes",R.drawable.business));
        mLiabilityCategory.add(new Category("2","Mortgage Payment",R.drawable.mortgage));
        mLiabilityCategory.add(new Category("3","Cell Phone",R.drawable.phone));
        mLiabilityCategory.add(new Category("4","Car Gas",R.drawable.gas));
        mLiabilityCategory.add(new Category("5","Car Insurance",R.drawable.car));
        mLiabilityCategory.add(new Category("6","Electric Bill",R.drawable.electric));
        mLiabilityCategory.add(new Category("7","Groceries",R.drawable.grocery));
        mLiabilityCategory.add(new Category("8","Gas/Heating",R.drawable.heat));
        mLiabilityCategory.add(new Category("9","Tv/Internet",R.drawable.tv));
        mLiabilityCategory.add(new Category("10","Water",R.drawable.water));
        mLiabilityCategory.add(new Category("11","Secured Line of Credit",R.drawable.credit));

        mRepeatCategort.add("Once");
        mRepeatCategort.add("Every Day");
        mRepeatCategort.add("Every Week");
        mRepeatCategort.add("Every Month");
        mRepeatCategort.add("Semi Anually");
        mRepeatCategort.add("Anually");

        mInvestmentCategory.add("Stocks");
        mInvestmentCategory.add("Bonds");
        mInvestmentCategory.add("Investment Funds");
        mInvestmentCategory.add("Bank Products");
        mInvestmentCategory.add("Options");
        mInvestmentCategory.add("Annulties");
        mInvestmentCategory.add("Retirement");
        mInvestmentCategory.add("Saving for Education");
        mInvestmentCategory.add("Alternative and Complex Products");
        mInvestmentCategory.add("Initial Coin Offerings and Cryptocurrencies");
        mInvestmentCategory.add("Commodity Futures");
        mInvestmentCategory.add("Security Futures");
        mInvestmentCategory.add("Insurance");

        Common.getInstance().setmIncomeCategory(mIncomeCategory);
        Common.getInstance().setmLiabilityCategory(mLiabilityCategory);
        Common.getInstance().setmRepeatCategory(mRepeatCategort);
    }

    private void readFile(){
        try {
            FileInputStream fileInputStream = openFileInput("loginstatus.pdm");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while((lines = bufferedReader.readLine()) != null){
                loginCheck = lines;
            }
            setValue();
        }catch (FileNotFoundException e){
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setValue(){
        Log.d("login status:::", loginCheck);
        String[] parts = loginCheck.split(" ");
        if (parts[0].equals("no")){
            login_status = "no";
            Log.d("login status:::", loginCheck);
            Common.getInstance().setLogin_status("no");
        }else{
            login_status = "yes";
            Common.getInstance().setLogin_status("yes");
            Common.getInstance().setUserID(parts[1]);
        }
    }
    private void moveToLogin(){
        if(login_status.equals("no")){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}