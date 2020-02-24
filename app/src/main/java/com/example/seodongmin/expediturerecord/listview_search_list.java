package com.example.seodongmin.expediturerecord;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.icu.text.*;

/**
 * Created by seodongmin on 2017-04-22.
 */

public class listview_search_list extends AppCompatActivity {

    private int abspos;
    private long sum;
    private int inex;
    private Drawable ieIcon;
    private int year;
    private int month;
    private int day;
    private int waynum;
    private String way;
    private Drawable wayIcon;
    private int emonum;
    private Drawable emotionIcon;
    private int includeExp;
    private String memo;
    private Drawable whereIcon;
    private int wherenum;
    private String where;
    private long amount;


    public void setabspos(int abspos) { this.abspos = abspos; }

    public void setsum(long sum) { this.sum = sum; }

    public void setinex(int inex) { this.inex = inex; }

    public void setieIcon(Drawable ieIcon) { this.ieIcon = ieIcon; }

    public void setyear(int year) { this.year = year; }

    public void setmonth(int month) { this.month = month; }

    public void setday(int day) { this.day = day; }

    public void setwaynum(int waynum) { this.waynum = waynum; }

    public void setway(String way) { this.way = way; }

    public void setwayIcon(Drawable wayIcon) { this.wayIcon = wayIcon; }

    public void setemonum(int emonum) { this.emonum = emonum; }

    public void setemotionIcon(Drawable emotionIcon) { this.emotionIcon = emotionIcon; }

    public void setincludeStat(int includeExp) { this.includeExp = includeExp; }

    public void setmemo(String memo) { this.memo = memo; }

    public void setwhereIcon(Drawable whereIcon) { this.whereIcon = whereIcon; }

    public void setwherenum(int wherenum) { this.wherenum = wherenum; }

    public void setwhere(String where) { this.where = where; }

    public void setamount(long amount) { this.amount = amount; }



    public int getabspos() { return this.abspos; }

    public long getsum() { return this.sum; }

    public int getinex() { return this.inex; }

    public Drawable getieIcon()
    {
        return this.ieIcon;
    }

    public int getyear() { return this.year; }

    public int getmonth() { return this.month; }

    public int getday() { return this.day; }

    public int getwaynum() { return this.waynum; }

    public String getway() { return this.way; }

    public Drawable getwayIcon() { return this.wayIcon; }

    public int getemonum() { return this.emonum; }

    public Drawable getemotionIcon() { return this.emotionIcon; }

    public int getincludeStat() { return this.includeExp; }

    public String getmemo() { return this.memo; }

    public Drawable getwhereIcon() { return this.whereIcon; }

    public int getwherenum() { return this.wherenum; }

    public String getwhere() { return this.where; }

    public long getamount_int() { return this.amount; }

    public String getamount() { return String.format("%,d", this.amount); }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_search_list);
    }
}
