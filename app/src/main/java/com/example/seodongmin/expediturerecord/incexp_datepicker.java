package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seodongmin on 2017-05-05.
 */

public class incexp_datepicker extends AppCompatActivity {

    boolean isBackAnimationAllowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incexp_datepicker);

        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        Intent intent = getIntent();
        MyYMDSetting ymd = new MyYMDSetting();
        int date = ymd.makeYMDInt(intent.getIntExtra("now_year", 0), intent.getIntExtra("now_month", 0), intent.getIntExtra("now_day", 0));
        String s_date = String.valueOf(date);
        System.out.println(s_date);



        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long time = 0;
        try {
            Date d = sdf.parse(s_date);
            time = d.getTime();
        } catch(ParseException e) {}

        final CalendarView calendar = (CalendarView) findViewById(R.id.incexp_datepicker_calendar);
        calendar.setDate(time);


        Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackAnimationAllowed = true;
                Intent intent = new Intent(incexp_datepicker.this, incexp_amount.class);
                intent.putExtra("ModifiedDate", calendar.getDate());
                setResult(RESULT_OK, intent);
                finish();

            }
        };
        Button ok = (Button) findViewById(R.id.incexp_datepicker_btn_ok);
        ok.setOnClickListener(ok_listener);

        Button.OnClickListener cancel_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackAnimationAllowed = true;
                finish();
            }
        };
        Button cancel = (Button) findViewById(R.id.incexp_datepicker_btn_cancel);
        cancel.setOnClickListener(cancel_listener);
    }


    @Override
    public void onPause()
    {
        super.onPause();
        if(isBackAnimationAllowed)
        {
            overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
            isBackAnimationAllowed = false;
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        isBackAnimationAllowed = true;
    }
}
