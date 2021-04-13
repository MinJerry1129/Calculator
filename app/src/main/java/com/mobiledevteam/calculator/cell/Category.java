package com.mobiledevteam.calculator.cell;

public class Category {
    private String mId;
    private String mTitle;
    private String mImageUrl;

    public Category(String id,String title,String imageUrl){
        mId=id;
        mTitle=title;
        mImageUrl = imageUrl;
    }

    public String getmId() {
        return mId;
    }
    public String getmTitle() {return mTitle;}
    public String getmImageUrl() { return mImageUrl; }
}

