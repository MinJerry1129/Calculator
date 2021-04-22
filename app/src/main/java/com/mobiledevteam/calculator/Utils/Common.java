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

    private String userID;
    private String login_status="no";


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

    public String getUserID() {return userID;}
    public void setUserID(String userID) {this.userID = userID;}

    public String getLogin_status() {return login_status;}
    public void setLogin_status(String login_status) {this.login_status = login_status;}
}
