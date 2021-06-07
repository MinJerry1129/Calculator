package com.mobiledevteam.calculator.Utils;

import com.mobiledevteam.calculator.cell.Category;

import java.util.ArrayList;

public class Common {
    private static Common instance = new Common();
    private String baseURL = "https://pressplayfinancial.ca/";
//    private String baseURL = "http://10.0.2.2/calculator/";
    private ArrayList<Category> mIncomeCategory;
    private ArrayList<Category> mLiabilityCategory;
    private ArrayList<String> mRepeatCategory;
    private ArrayList<String> mInvestmentCategory;
    private ArrayList<String> mPeroidCategory;

    private String userID;
    private String login_status="no";

    private String sel_payinfo = "1";

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

    public ArrayList<String> getmInvestmentCategory() { return mInvestmentCategory;}
    public void setmInvestmentCategory(ArrayList<String> mInvestmentCategory) {this.mInvestmentCategory = mInvestmentCategory;}

    public ArrayList<String> getmPeroidCategory() {return mPeroidCategory;}
    public void setmPeroidCategory(ArrayList<String> mPeroidCategory) {this.mPeroidCategory = mPeroidCategory;}

    public String getUserID() {return userID;}
    public void setUserID(String userID) {this.userID = userID;}

    public String getLogin_status() {return login_status;}
    public void setLogin_status(String login_status) {this.login_status = login_status;}

    public String getSel_payinfo() {return sel_payinfo; }
    public void setSel_payinfo(String sel_payinfo) { this.sel_payinfo = sel_payinfo;}
}
