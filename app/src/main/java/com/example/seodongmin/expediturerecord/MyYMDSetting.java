package com.example.seodongmin.expediturerecord;

/**
 * Created by seodongmin on 2017-04-25.
 */

import android.widget.*;
import android.support.v7.app.*;
import android.content.*;
import java.util.*;

import java.util.Calendar;

public class MyYMDSetting extends AppCompatActivity{

    private Calendar cal = Calendar.getInstance();
    private int standard_y = cal.get(Calendar.YEAR);
    private int standard_m = cal.get(Calendar.MONTH);
    private int standard_d = cal.get(Calendar.DAY_OF_MONTH);
    private ArrayAdapter adapter_y;
    private ArrayAdapter adapter_m;
    private ArrayAdapter adapter_d;
    private int first_2_count = 0;

    public void setInit(Context context, Spinner spinner_y, Spinner spinner_m, Spinner spinner_d)
    {
        adapter_y = ArrayAdapter.createFromResource(context, R.array.ie_years, android.R.layout.simple_spinner_item);
        adapter_y.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_y.setAdapter(adapter_y);
        spinner_y.setSelection(standard_y - cal.get(Calendar.YEAR));

        adapter_m = ArrayAdapter.createFromResource(context, R.array.ie_months, android.R.layout.simple_spinner_item);
        adapter_m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_m.setAdapter(adapter_m);
        spinner_m.setSelection(standard_m);

        switch(Integer.parseInt(spinner_m.getSelectedItem().toString()))
        {

            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                adapter_d = ArrayAdapter.createFromResource(context, R.array.ie_days, android.R.layout.simple_spinner_item);
                adapter_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_d.setAdapter(adapter_d);
                spinner_d.setSelection(standard_d - 1);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                adapter_d = ArrayAdapter.createFromResource(context, R.array.ie_days_thirty, android.R.layout.simple_spinner_item);
                adapter_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_d.setAdapter(adapter_d);
                spinner_d.setSelection(standard_d - 1);
                break;
            case 2:
                int thisyear = Integer.parseInt(spinner_y.getSelectedItem().toString());
                boolean isyun = false;
                if(thisyear % 400 == 0 || ((thisyear % 4 == 0) && (thisyear % 100 != 0)))
                {
                    isyun = true;
                }

                if(isyun)
                {
                    adapter_d = ArrayAdapter.createFromResource(context, R.array.ie_days_twentynine, android.R.layout.simple_spinner_item);
                    adapter_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_d.setAdapter(adapter_d);
                    spinner_d.setSelection(standard_d - 1);
                    break;
                }
                else
                {
                    adapter_d = ArrayAdapter.createFromResource(context, R.array.ie_days_twentyeight, android.R.layout.simple_spinner_item);
                    adapter_d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_d.setAdapter(adapter_d);
                    spinner_d.setSelection(standard_d - 1);
                    break;
                }
        }
    }

    public void setListener(Context context, Spinner spinner_y, Spinner spinner_m, Spinner spinner_d)
    {
        if(first_2_count < 2) {
            first_2_count++;
            return;
        }

        ArrayAdapter adapter_dd;
        int currentpos;
        switch(Integer.parseInt(spinner_m.getSelectedItem().toString()))
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                currentpos = spinner_d.getSelectedItemPosition();
                adapter_dd = ArrayAdapter.createFromResource(context, R.array.ie_days, android.R.layout.simple_spinner_item);
                adapter_dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_d.setAdapter(adapter_dd);
                spinner_d.setSelection(currentpos);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                currentpos = spinner_d.getSelectedItemPosition();
                adapter_dd = ArrayAdapter.createFromResource(context, R.array.ie_days_thirty, android.R.layout.simple_spinner_item);
                adapter_dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_d.setAdapter(adapter_dd);
                if(currentpos >= spinner_d.getAdapter().getCount())
                    spinner_d.setSelection(29);
                else
                    spinner_d.setSelection(currentpos);
                break;
            case 2:
                int thisyear = Integer.parseInt(spinner_y.getSelectedItem().toString());
                boolean isyun = false;
                if(thisyear % 400 == 0 || ((thisyear % 4 == 0) && (thisyear % 100 != 0)))
                {
                    isyun = true;
                }

                if(isyun)
                {
                    currentpos = spinner_d.getSelectedItemPosition();
                    adapter_dd = ArrayAdapter.createFromResource(context, R.array.ie_days_twentynine, android.R.layout.simple_spinner_item);
                    adapter_dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_d.setAdapter(adapter_dd);
                    if(currentpos >= spinner_d.getAdapter().getCount())
                        spinner_d.setSelection(28);
                    else
                        spinner_d.setSelection(currentpos);
                    break;
                }
                else
                {
                    currentpos = spinner_d.getSelectedItemPosition();
                    adapter_dd = ArrayAdapter.createFromResource(context, R.array.ie_days_twentyeight, android.R.layout.simple_spinner_item);
                    adapter_dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_d.setAdapter(adapter_dd);
                    if(currentpos >= spinner_d.getAdapter().getCount())
                        spinner_d.setSelection(27);
                    else
                        spinner_d.setSelection(currentpos);
                    break;
                }
            default:
                break;
        }
    }

    public int makeYMDInt(int year, int month, int day)
    {
        String s_year = String.valueOf(year);
        String s_month;
        if(month < 10)
        {
            s_month = String.valueOf("0" + String.valueOf(month));
        }
        else
        {
            s_month = String.valueOf(month);
        }

        String s_day;
        if(day < 10)
        {
            s_day = String.valueOf("0" + String.valueOf(day));
        }
        else
        {
            s_day = String.valueOf(day);
        }

        String s_hap = s_year + s_month + s_day;
        return Integer.parseInt(s_hap);
    }

    public Calendar moveToEndOfWeek(int year, int month, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        int what = cal.get(Calendar.DAY_OF_WEEK);


        switch(what) {
            case 1:
                cal.add(Calendar.DAY_OF_YEAR, 0);
                break;
            case 2:
                cal.add(Calendar.DAY_OF_YEAR, 6);
                break;
            case 3:
                cal.add(Calendar.DAY_OF_YEAR, 5);
                break;
            case 4:
                cal.add(Calendar.DAY_OF_YEAR, 4);
                break;
            case 5:
                cal.add(Calendar.DAY_OF_YEAR, 3);
                break;
            case 6:
                cal.add(Calendar.DAY_OF_YEAR, 2);
                break;
            case 7:
                cal.add(Calendar.DAY_OF_YEAR, 1);
                break;
        }



        return cal;
    }

    /*public Calendar moveToStartOfWeek(int year, int month, int day)
    {
        Calendar cal = moveToEndOfWeek(year, month, day);
        cal.add(Calendar.DAY_OF_YEAR, -7);
        return cal;
    }*/

    public Calendar moveToEndOfMonth(int year, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return cal;
    }
}
