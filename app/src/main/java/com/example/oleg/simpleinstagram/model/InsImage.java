package com.example.oleg.simpleinstagram.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by oleg on 20.05.16.
 */
public class InsImage {

    private String mLowResolutionUrl;
    private int mLowResolutionHeight = 320;
    private int mLowResolutionWidth = 320;

    private String mTumbnailResolutionUrl;
    private int mTumbnailResolutionHeight = 150;
    private int mTumbnailLowResolutionWidth = 150;
    private String mStandardResolutionUrl;
    private int mStandardResolutionHeight = 640;
    private int mStandardLowResolutionWidth = 640;

    private String mCaption = "";
    private String mCreatedTime;
    private int mLikesCount;

    private String mType;

     boolean isChecked = false;

    public InsImage(JSONObject jsonObject) throws JSONException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        mType = jsonObject.getString("type");
        JSONObject likes = jsonObject.getJSONObject("likes");
        mLikesCount = likes != null ? likes.optInt("count") : 0;
        mStandardResolutionUrl = jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        mLowResolutionUrl =      String.valueOf((jsonObject.getJSONObject("images").getJSONObject("low_resolution").getString("url")));
        mTumbnailResolutionUrl = String.valueOf((jsonObject.getJSONObject("images").getJSONObject("thumbnail").getString("url")));
        mCreatedTime = simpleDateFormat.format(
                new Date(Integer.parseInt(
                        jsonObject.getJSONObject("caption")
                        .getString("created_time"))*1000L
                )
        );
        mCaption = String.valueOf(jsonObject.getJSONObject("caption").get("text"));
        isChecked = false;
    }

    public String getmCaption() {
        return mCaption;
    }

    public void setmCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getmCreatedTime() {
        return mCreatedTime;
    }

    public void setmCreatedTime(String mCreatedTime) {
        this.mCreatedTime = mCreatedTime;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setmLikesCount(int mLikesCount) {
        this.mLikesCount = mLikesCount;
    }


    public String getTumbnailUrl() {
        return mTumbnailResolutionUrl;
    }

    public void setmTumbnailResolutionUrl(String mTumbnailResolutionUrl) {
        this.mTumbnailResolutionUrl = mTumbnailResolutionUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }


    public int getStandardWidth() {
        return mStandardLowResolutionWidth;
    }

    public int getStandardHeight() {
        return mStandardResolutionHeight;
    }

    public String getStandardUrl() {
        return mStandardResolutionUrl;
    }
}
