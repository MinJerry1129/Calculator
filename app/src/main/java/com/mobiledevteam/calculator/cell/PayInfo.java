package com.mobiledevteam.calculator.cell;

public class PayInfo {
    private String mId;
    private int mCategoryID;
    private String mPrice;
    private String mPaytype;

    public PayInfo(String id,int categoryid,String price,String paytype){
        mId=id;
        mCategoryID = categoryid;
        mPrice = price;
        mPaytype = paytype;
    }

    public String getmId() {
        return mId;
    }
    public int getmCategoryID() {return mCategoryID; }
    public String getmPaytype() { return mPaytype; }
    public String getmPrice() { return mPrice; }
}
