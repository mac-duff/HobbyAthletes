package com.hobbyathletes.hobbyathletes.Framework;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.hobbyathletes.hobbyathletes.R;

public class NavDrawerItem {

    private String title;
    private Integer icon;
    private Integer icon_height;
    private Integer icon_width;
    private Bitmap imagePic;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;

    public NavDrawerItem(){}

    public NavDrawerItem(String title){
        this.title = title;
    }

    public NavDrawerItem(String title, Integer[] icon){
        this.title = title;
        setIcon(icon);
    }

    public NavDrawerItem(String title, Bitmap imagePic){
        this.title = title;
        this.imagePic = imagePic;
    }

    public NavDrawerItem(String title, Integer[] icon, boolean isCounterVisible, String count){
        this.title = title;
        setIcon(icon);
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public String getTitle(){
        return this.title;
    }

    public Integer getIcon(){
        return this.icon;
    }

    public Integer getIconHeight(){
        return this.icon_height;
    }

    public Integer getIconWeight(){
        return this.icon_width;
    }

    public Bitmap getImagePic(){
        return this.imagePic;
    }

    public String getCount(){
        return this.count;
    }

    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(Integer[] icon){

        this.icon = icon[0];
        icon_height = icon[1];
        icon_width = icon[2];

    }

    public void setCount(String count){
        this.count = count;
    }

    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
}
