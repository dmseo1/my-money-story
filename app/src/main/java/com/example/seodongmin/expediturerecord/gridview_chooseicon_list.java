package com.example.seodongmin.expediturerecord;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class gridview_chooseicon_list extends AppCompatActivity {

    private Drawable icon;
    private String name;


    public void seticon(Drawable icon) { this.icon = icon; }

    public void setname(String name) { this.name = name; }

    public Drawable geticon() { return this.icon; }

    public String getname() { return this.name; }
}
