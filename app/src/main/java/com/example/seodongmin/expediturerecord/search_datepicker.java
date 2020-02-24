package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by seodongmin on 2017-05-23.
 */

public class search_datepicker extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_datepicker);

        final CalendarView dp = (CalendarView) findViewById(R.id.popup_search_datepicker_dp);
        final Button ok = (Button) findViewById(R.id.popup_search_datepicker_btn_ok);
        final Button cancel = (Button) findViewById(R.id.popup_search_datepicker_btn_cancel);
        final TextView title = (TextView) findViewById(R.id.popup_search_datepicker_lbl_title);

        Intent getintent= getIntent();
        final int fromto = getintent.getIntExtra("FromTo", 0);
        final int year = getintent.getIntExtra("Year", 0);
        final int month = getintent.getIntExtra("Month", 0);
        final int day = getintent.getIntExtra("Day", 0);
        final int comp_year = getintent.getIntExtra("CompYear", 0);
        final int comp_month = getintent.getIntExtra("CompMonth", 0);
        final int comp_day = getintent.getIntExtra("CompDay", 0);
        System.out.println("Type: " + fromto + ", " + year + "." + month + "." + day);

        if(fromto == 1)
        {
            title.setText("날짜 선택(부터)");
        }
        else if(fromto == 2)
        {
            title.setText("날짜 선택(까지)");
        }

        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        dp.setDate(cal.getTimeInMillis());


        Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fromto == 1)
                {
                    System.out.println("여기가 실행되나?");
                    Calendar f_cal = Calendar.getInstance();
                    Calendar t_cal = Calendar.getInstance();
                    f_cal.setTimeInMillis(dp.getDate());
                    t_cal.set(comp_year, comp_month - 1, comp_day);
                    if(f_cal.after(t_cal))
                    {
                        Toast.makeText(search_datepicker.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if(fromto == 2)
                {
                    System.out.println("여기가 실행되나?");
                    Calendar f_cal = Calendar.getInstance();
                    Calendar t_cal = Calendar.getInstance();
                    f_cal.set(comp_year, comp_month - 1, comp_day);
                    t_cal.setTimeInMillis(dp.getDate());
                    if(t_cal.before(f_cal))
                    {
                        Toast.makeText(search_datepicker.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Calendar returnCal = Calendar.getInstance();
                returnCal.setTimeInMillis(dp.getDate());
                Intent intent_result = new Intent(search_datepicker.this, search.class);
                intent_result.putExtra("FromTo", fromto);
                intent_result.putExtra("Year", returnCal.get(Calendar.YEAR));
                intent_result.putExtra("Month", returnCal.get(Calendar.MONTH));
                intent_result.putExtra("Day", returnCal.get(Calendar.DAY_OF_MONTH));
                System.out.println(returnCal.get(Calendar.YEAR) + "." + returnCal.get(Calendar.MONTH) + "." + returnCal.get(Calendar.DAY_OF_MONTH));
                setResult(RESULT_OK, intent_result);
                finish();
            }
        };
        ok.setOnClickListener(ok_listener);

        Button.OnClickListener cancel_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_result = new Intent(search_datepicker.this, search.class);
                setResult(RESULT_CANCELED, intent_result);
                finish();
            }
        };
        cancel.setOnClickListener(cancel_listener);

    }
}
