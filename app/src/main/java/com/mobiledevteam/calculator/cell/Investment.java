package com.mobiledevteam.calculator.cell;

public class Investment {
    private String mId;
    private String mPrice;
    private String mType;
    private String mDate;

    public Investment(String id,String price,String type, String mdate){
        mId=id;
        mPrice=price;
        mType = type;
        mDate = mdate;
    }

    public String getmId() {
        return mId;
    }
    public String getmPrice() { return mPrice; }
    public String getmDate() { return mDate;}
    public String getmType() { return mType;}
}
