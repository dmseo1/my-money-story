package com.example.seodongmin.expediturerecord;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by seodongmin on 2017-04-22.
 */

public class listview_calculateit_ranking_list extends AppCompatActivity {

    private int ranking;
    private String name;
    private int correct;
    private long time;
    private long date;


    public void setranking(int ranking) { this.ranking = ranking; }

    public void setname(String name) { this.name = name; }

    public void setcorrect(int correct) { this.correct = correct; }

    public void settime(long time) { this.time = time; }

    public void setdate(long date) { this.date = date; }



    public int getranking() { return this.ranking; }

    public String getname() { return this.name; }

    public int getcorrect() { return this.correct; }

    public long gettime()
    {
        return this.time;
    }

    public long getdate() { return this.date; }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_calculateit_ranking);
    }
}
