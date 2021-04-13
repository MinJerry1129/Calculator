package com.mobiledevteam.calculator.Utils;

public class Common {
    private static Common instance = new Common();
    private String baseURL = "http://phpstack-362651-1717329.cloudwaysapps.com/";//http://phpstack-362651-1717329.cloudwaysapps.com/ , http://10.0.2.2/proderma/

    public static Common getInstance() {
        return instance;
    }
    public String getBaseURL() {return baseURL;}
}
