package com.mobiledevteam.calculator.cell;

public class Saving {
    private String mId;
    private String mTitle;
    private String mStartDate;
    private String mEndDate;
    private int mType;
    private String mPrice;

    public Saving(String id,String title,String startdate, String enddate,int type, String price){
        mId=id;
        mTitle = title;
        mStartDate = startdate;
        mEndDate = enddate;
        mType = type;
        mPrice = price;
    }

    public String getmPrice() {return mPrice;}
    public String getmStartDate() {return mStartDate;}
    public int getmType() { return mType;}
    public String getmEndDate() {return mEndDate;}
    public String getmId() {return mId;}
    public String getmTitle() { return mTitle;}
}
