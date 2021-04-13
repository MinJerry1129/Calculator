package com.mobiledevteam.calculator.Utils;

import com.mobiledevteam.calculator.cell.Category;

import java.util.ArrayList;

public class Common {
    private static Common instance = new Common();
//    private String baseURL = "http://phpstack-362651-1717329.cloudwaysapps.com/";
    private String baseURL = "http://10.0.2.2/proderma/";
    private ArrayList<Category> mCategory;

    public static Common getInstance() {
        return instance;
    }
    public String getBaseURL() {return baseURL;}
}
