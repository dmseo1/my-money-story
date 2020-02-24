package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-20.
 */

public class main extends AppCompatActivity {

    int RESULT_NOKEYPAD = 8888;
    boolean nokeypad = false;
    Calendar registered_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();
/*
        for(int i = 0; i < 3 ; i ++)
        {
            new AlarmHATT(getApplicationContext()).Alarm(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE) + 1, 20 * i);
        }*/

        System.out.println("지금이 몇 시야: " + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("지금이 몇 분이야: " + cal.get(Calendar.MINUTE));


        //수입/지출 입력
        View.OnClickListener listener_input = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(main.this, incexp_amount.class);
                Time t = new Time();
                t.set(System.currentTimeMillis());
                intent.putExtra("n_year", t.year);
                intent.putExtra("n_month", t.month + 1);
                intent.putExtra("n_day", t.monthDay);
                startActivityForResult(intent, RESULT_NOKEYPAD);
            }
        };

        ImageButton input = (ImageButton) findViewById(R.id.main_btn_input);
        input.setOnClickListener(listener_input);


                //통계
                View.OnClickListener listener_stat = new android.view.View.OnClickListener()
                {
                    @Override
                    public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(main.this, stat_v2.class);
                startActivityForResult(intent, RESULT_NOKEYPAD);
            }
        };

        ImageButton stat = (ImageButton) findViewById(R.id.main_btn_stat);
        stat.setOnClickListener(listener_stat);


        //조회
        View.OnClickListener listener_search = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(main.this, search.class);
                startActivity(intent);
            }
        };

        ImageButton search = (ImageButton) findViewById(R.id.main_btn_search);
        search.setOnClickListener(listener_search);



        //설정
        View.OnClickListener listener_setting = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(main.this, setting.class);
                startActivity(intent);
            }
        };

        ImageButton setting = (ImageButton) findViewById(R.id.main_btn_setting);
        setting.setOnClickListener(listener_setting);

        //목표지출 설정
        Button.OnClickListener setgoal_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main.this, setting_targetex.class);
                startActivity(intent);
            }
        };
        Button setgoal = (Button) findViewById(R.id.main_btn_setgoal);
        setgoal.setOnClickListener(setgoal_listener);
    }


    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }


        public void Alarm(int year, int month, int day, int hour, int minute, int second) {

            //알람시간 calendar에 set해주기
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, hour, minute, second);
            registered_time = calendar;

            if(System.currentTimeMillis() < registered_time.getTimeInMillis()) { //이미 지난 알람을 더 이상 등록하지 않는다.
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(main.this, Broadcast_InputAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(main.this, 0, intent, 0);

                //알람 예약
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            }
        }


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour >= 6 && hour <= 11)
        {
            Toast.makeText(main.this, "오늘도 활기찬 하루 되세요!", Toast.LENGTH_SHORT).show();
        }
        else if(hour >= 12 && hour <= 16)
        {
            Toast.makeText(main.this, "나른한 오후 힘내세요!", Toast.LENGTH_SHORT).show();
        }
        else if(hour >= 17 && hour <= 21)
        {
            Toast.makeText(main.this, "오늘도 즐거운 하루 되셨나요?", Toast.LENGTH_SHORT).show();
        }
        else if(hour >= 22 && hour <= 23)
        {
            Toast.makeText(main.this, "편안한 밤 되세요.", Toast.LENGTH_SHORT).show();
        }
        else if(hour == 5)
        {
            Toast.makeText(main.this, "부지런한 당신에게 행운이..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(main.this, "좋은 꿈 꾸세요.", Toast.LENGTH_SHORT).show();
        }
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        FrameLayout fr = (FrameLayout) findViewById(R.id.main_fr_lock);
        fr.setVisibility(View.VISIBLE);
    }*/

    @Override
    public void onResume()
    {
        super.onResume();

        if(nokeypad) {
            InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            nokeypad = false;
        }

        final Calendar cal = Calendar.getInstance();
        SharedPreferences sf = getSharedPreferences("goalex_" + String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH)+1), MODE_PRIVATE);
        TextView txt_lbl = (TextView) findViewById(R.id.main_lbl_goal_amount);
        txt_lbl.setText(String.format("%,d", sf.getLong("goalex_" + String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH)+1), 0)));


        //달리는 위치 수정

        long all = sf.getLong("goalex_" + String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH)+1), 0);


        long part = getThisMonthExp();

        all = all / 100;
        part = part / 100;
        float percent = (float)part / (float)all;
        float percent_comp = 1 - percent;
        LinearLayout lin = (LinearLayout) findViewById(R.id.stat_runningtrack);

        if(percent >= 0.92)
        {
            percent = 0.92f;
            percent_comp = 0.08f;
        }

        View runningman_left = findViewById(R.id.main_runningman_left);
        runningman_left.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1, percent_comp));
        View runningman_right = findViewById(R.id.main_runningman_right);
        runningman_right.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1, percent));

        //프로그레스 바 수정
        ProgressBar pgbar = (ProgressBar) findViewById(R.id.main_prg_goal_expenditure);
        if(part > all)
        {
            pgbar.setMax(1);
            pgbar.setProgress(1);
        }
        else {
            pgbar.setMax((int)all);
            pgbar.setProgress((int)part);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_NOKEYPAD) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                nokeypad = data.getBooleanExtra("nokeypad", false);
                System.out.println("State: " + nokeypad);
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }


    private long getThisMonthExp() {
        MyYMDSetting day_ymdsetting = new MyYMDSetting();
        Calendar cal = Calendar.getInstance();

        long sum = 0;
        int nowdate = day_ymdsetting.makeYMDInt(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 0);
        for(int i = 0; i < search_adapter.getCount() ; i ++) {

            int comp_year = search_adapter.getListItem().get(i).getyear();
            int comp_month = search_adapter.getListItem().get(i).getmonth();
            int compdate = day_ymdsetting.makeYMDInt(comp_year, comp_month, 0);

            if(nowdate < compdate)
            {
                continue;
            }
            else {
                if (nowdate != compdate) break;
                if (search_adapter.getListItem().get(i).getinex() == 2) {
                    sum += search_adapter.getListItem().get(i).getamount_int() * search_adapter.getListItem().get(i).getincludeStat();
                }
            }
        }
        return sum;
    }
}