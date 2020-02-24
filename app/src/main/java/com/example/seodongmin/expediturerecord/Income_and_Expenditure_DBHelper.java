package com.example.seodongmin.expediturerecord;

/**
 * Created by seodongmin on 2017-04-21.
 */

import android.database.sqlite.*;
import android.content.*;

public class Income_and_Expenditure_DBHelper extends SQLiteOpenHelper {



    public Income_and_Expenditure_DBHelper(Context context)
    {
        super(context, "Income_and_Expenditure.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {

    }

}
