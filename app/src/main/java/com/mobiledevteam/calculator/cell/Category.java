package com.mobiledevteam.calculator.cell;

public class Category {
    private String mId;
    private String mTitle;
    private int mImageUrl;

    public Category(String id,String title,int imageUrl){
        mId=id;
        mTitle=title;
        mImageUrl = imageUrl;
    }

    public String getmId() {
        return mId;
    }
    public String getmTitle() {return mTitle;}
    public int getmImageUrl() { return mImageUrl; }
}

