package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seodongmin on 2017-05-08.
 */

public class incexp_modify_ymd extends AppCompatActivity {

    CalendarView calendar;
    int whereFrom = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incexp_modify_ymd);

        calendar = (CalendarView) findViewById(R.id.incexp_modify_ymd_calendar);

        Intent intent = getIntent();
        MyYMDSetting ymd = new MyYMDSetting();
        whereFrom = intent.getIntExtra("whereFrom", 0);
        int date = ymd.makeYMDInt(intent.getIntExtra("now_year", 0), intent.getIntExtra("now_month", 0), intent.getIntExtra("now_day", 0));
        String s_date = String.valueOf(date);
        System.out.println(s_date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long time = 0;
        try {
            Date d = sdf.parse(s_date);
            time = d.getTime();
        } catch(ParseException e) {}

        calendar.setDate(time);



        Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whereFrom == 1) {
                    Intent intent = new Intent(incexp_modify_ymd.this, incexp_catchoose.class);
                    intent.putExtra("ModifiedDate", calendar.getDate());
                    setResult(RESULT_OK, intent);
                }
                else if(whereFrom == 2)
                {
                    Intent intent = new Intent(incexp_modify_ymd.this, incexp_waychoose.class);
                    intent.putExtra("ModifiedDate", calendar.getDate());
                    setResult(RESULT_OK, intent);
                }
                finish();

            }
        };
        Button ok = (Button) findViewById(R.id.popup_modify_ymd_btn_ok);
        ok.setOnClickListener(ok_listener);

        Button.OnClickListener cancel_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        Button cancel = (Button) findViewById(R.id.popup_modify_ymd_btn_cancel);
        cancel.setOnClickListener(cancel_listener);




    }
}
