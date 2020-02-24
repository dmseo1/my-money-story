package com.example.seodongmin.expediturerecord;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class listview_category_list extends AppCompatActivity {

    private boolean checked;
    private int order;
    private String name;
    private Drawable icon;

    public void setchecked(boolean checked) { this.checked = checked; }

    public void setorder(int order) { this.order = order; }

    public void setname(String name) { this.name = name; }

    public void seticon(Drawable icon) { this.icon = icon; }

    public boolean getchecked() { return this.checked; }

    public int getorder() { return this.order; }

    public String getname() { return this.name; }

    public Drawable geticon() { return this.icon; }

}
