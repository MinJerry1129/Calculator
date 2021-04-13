package com.mobiledevteam.calculator.Utils;

import com.mobiledevteam.calculator.cell.Category;

import java.util.ArrayList;

public class Common {
    private static Common instance = new Common();
//    private String baseURL = "http://phpstack-362651-1717329.cloudwaysapps.com/";
    private String baseURL = "http://10.0.2.2/proderma/";
    private ArrayList<Category> mIncomeCategory;
    private ArrayList<Category> mLiabilityCategory;
    private ArrayList<String> mRepeatCategory;


    public static Common getInstance() {
        return instance;
    }
    public String getBaseURL() {return baseURL;}

    public ArrayList<Category> getmIncomeCategory() { return mIncomeCategory;}
    public void setmIncomeCategory(ArrayList<Category> mIncomeCategory) { this.mIncomeCategory = mIncomeCategory; }

    public ArrayList<Category> getmLiabilityCategory() { return mLiabilityCategory;}
    public void setmLiabilityCategory(ArrayList<Category> mLiabilityCategory) { this.mLiabilityCategory = mLiabilityCategory; }

    public ArrayList<String> getmRepeatCategory() { return mRepeatCategory;}
    public void setmRepeatCategory(ArrayList<String> mRepeatCategory) { this.mRepeatCategory = mRepeatCategory;}
}
