package com.mobiledevteam.calculator.cell;

public class PayInfo {
    private String mId;
    private int mCategoryID;
    private String mPrice;
    private String mDate;
    private String mPaytype;

    public PayInfo(String id,int categoryid,String price,String date_string,String paytype){
        mId=id;
        mCategoryID = categoryid;
        mPrice = price;
        mPaytype = paytype;
        mDate = date_string;
    }

    public String getmId() {
        return mId;
    }
    public int getmCategoryID() {return mCategoryID; }
    public String getmPaytype() { return mPaytype; }
    public String getmPrice() { return mPrice; }

    public String getmDate() {return mDate;}
}
