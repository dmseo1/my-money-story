package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import android.widget.*;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-30.
 */

public class stat_v2 extends AppCompatActivity {

    //일간 차트 - 년월일 스피너 초기화============================================================================

    int LINK_TO_SEARCH = 8888;
    int LINK_TO_ADD = 8888;

    int day_ignore_three = 0;
    int week_ignore_three = 0;
    int month_ignore_two = 0;

    int day_chartheight_inc = 90;
    int day_chartheight = 90;

    int week_chartheight_inc = 90;
    int week_chartheight = 90;

    int month_chartheight_inc = 90;
    int month_chartheight = 90;

    boolean isNeedRefresh = false;

    Spinner day_spin_y;
    Spinner day_spin_m;
    Spinner day_spin_d;

    Spinner week_spin_y;
    Spinner week_spin_m;
    Spinner week_spin_d;

    Spinner month_spin_y;
    Spinner month_spin_m;
    MyYMDSetting day_ymdsetting;

    boolean isDaySpinChanged = true;
    boolean isDaySpinChanged_inc = true;
    boolean isWeekSpinChanged = true;
    boolean isWeekSpinChanged_inc = true;
    boolean isMonthSpinChanged = true;
    boolean isMonthSpinChanged_inc = true;

    boolean isNeedYou = false;
    int year_rememberer = 0;
    int month_rememberer = 0;
    int day_rememberer = 0;

    float monthlyRating;


    ArrayList<String> day_exptop_category_name = new ArrayList<>();
    ArrayList<String> day_inctop_category_name = new ArrayList<>();
    ArrayList<String> week_exptop_category_name = new ArrayList<>();
    ArrayList<String> week_inctop_category_name = new ArrayList<>();
    ArrayList<String> month_exptop_category_name = new ArrayList<>();
    ArrayList<String> month_inctop_category_name = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_v2);

        //일간 스피너
        day_spin_y = (Spinner) findViewById(R.id.stat_day_spin_y);
        day_spin_m = (Spinner) findViewById(R.id.stat_day_spin_m);
        day_spin_d = (Spinner) findViewById(R.id.stat_day_spin_d);
        day_ymdsetting = new MyYMDSetting();
        day_ymdsetting.setInit(stat_v2.this, day_spin_y, day_spin_m, day_spin_d);

        // 주간 스피너
        week_spin_y = (Spinner) findViewById(R.id.stat_week_spin_y);
        week_spin_m = (Spinner) findViewById(R.id.stat_week_spin_m);
        week_spin_d = (Spinner) findViewById(R.id.stat_week_spin_d);
        day_ymdsetting.setInit(stat_v2.this, week_spin_y, week_spin_m, week_spin_d);


        // 월간 스피너
        Calendar cal = Calendar.getInstance();
        int standard_y = cal.get(Calendar.YEAR);
        int standard_m = cal.get(Calendar.MONTH);
        month_spin_y = (Spinner) findViewById(R.id.stat_month_spin_y);
        ArrayAdapter month_adapter_y = ArrayAdapter.createFromResource(this, R.array.ie_years, android.R.layout.simple_spinner_item);
        month_adapter_y.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spin_y.setAdapter(month_adapter_y);
        month_spin_y.setSelection(2020 - standard_y);
        month_spin_m = (Spinner) findViewById(R.id.stat_month_spin_m);
        ArrayAdapter month_adapter_m = ArrayAdapter.createFromResource(this, R.array.ie_months, android.R.layout.simple_spinner_item);
        month_adapter_m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spin_m.setAdapter(month_adapter_m);
        month_spin_m.setSelection(standard_m);

        //탭 버튼 설정==========================================================================================
        TabHost tabHost = (TabHost) findViewById(R.id.stat_maintabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab1").setContent(R.id.stat_tab_day)
                .setIndicator(getString(R.string.tab1));
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab2").setContent(R.id.stat_tab_week)
                .setIndicator(getString(R.string.tab2));
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab3").setContent(R.id.stat_tab_month)
                .setIndicator(getString(R.string.tab3));
        tabHost.addTab(spec3);



        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 160;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 160;
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 160;


        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout rl1 = (LinearLayout) tabHost.getTabWidget().getChildAt(0);
        ImageView img1 = (ImageView) rl1.getChildAt(0);
        img1.setImageDrawable(getDrawable(R.drawable.btn_back));
        TextView tv1 =  (TextView) rl1.getChildAt(1);
        tv1.setTextSize(20);
        LinearLayout rl2 = (LinearLayout) tabHost.getTabWidget().getChildAt(1);
        TextView tv2 =  (TextView) rl2.getChildAt(1);
        tv2.setTextSize(20);
        LinearLayout rl3 = (LinearLayout) tabHost.getTabWidget().getChildAt(2);
        TextView tv3 =  (TextView) rl3.getChildAt(1);
        tv3.setTextSize(20);
        //탭 버튼 설정 끝==========================================================================================






        //스피너의 내용이 바뀌면 통계를 다시 구한다 - 일간
        Spinner.OnItemSelectedListener day_spin_ym_listener = new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(day_ignore_three < 3) {
                    day_ignore_three ++;
                    return;
                }
                if(isNeedYou)
                {
                    System.out.println("구동되고 있잖아");
                    day_spin_y.setSelection(2020 - year_rememberer);
                    day_spin_m.setSelection(month_rememberer);
                    day_spin_d.setSelection(day_rememberer - 1);
                    isNeedYou = false;
                }
                day_ymdsetting.setListener(stat_v2.this, day_spin_y, day_spin_m, day_spin_d);

                day_chartheight = 90;
                isDaySpinChanged_inc = true;
                isDaySpinChanged = true;
                Intent intent = new Intent(stat_v2.this, nowsearching.class);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        Spinner.OnItemSelectedListener day_spin_d_listener = new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(day_ignore_three < 3) {
                    day_ignore_three ++;
                    return;
                }


                day_chartheight = 90;
                isDaySpinChanged_inc = true;
                isDaySpinChanged = true;
                Intent intent = new Intent(stat_v2.this, nowsearching.class);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        day_spin_y.setOnItemSelectedListener(day_spin_ym_listener);
        day_spin_m.setOnItemSelectedListener(day_spin_ym_listener);
        day_spin_d.setOnItemSelectedListener(day_spin_d_listener);





        //스피너의 내용이 바뀌면 통계를 다시 구한다 - 주간
        Spinner.OnItemSelectedListener week_spin_ym_listener = new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(week_ignore_three < 3) {
                    week_ignore_three ++;
                    return;
                }

                if(isNeedYou)
                {
                    System.out.println("구동되고 있잖아");
                    week_spin_y.setSelection(2020 - year_rememberer);
                    week_spin_m.setSelection(month_rememberer);
                    week_spin_d.setSelection(day_rememberer - 1);
                    isNeedYou = false;
                }
                day_ymdsetting.setListener(stat_v2.this, week_spin_y, week_spin_m, week_spin_d);




                week_chartheight = 90;
                isWeekSpinChanged_inc = true;
                isWeekSpinChanged = true;
                Intent intent = new Intent(stat_v2.this, nowsearching.class);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        Spinner.OnItemSelectedListener week_spin_d_listener = new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(week_ignore_three < 3) {
                    week_ignore_three ++;
                    return;
                }


                week_chartheight = 90;
                isWeekSpinChanged_inc = true;
                isWeekSpinChanged = true;
                Intent intent = new Intent(stat_v2.this, nowsearching.class);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        week_spin_y.setOnItemSelectedListener(week_spin_ym_listener);
        week_spin_m.setOnItemSelectedListener(week_spin_ym_listener);
        week_spin_d.setOnItemSelectedListener(week_spin_d_listener);




        //스피너의 내용이 바뀌면 통계를 다시 구한다 - 월간
        Spinner.OnItemSelectedListener month_spin_ym_listener = new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(month_ignore_two < 2) {
                    month_ignore_two ++;
                    return;
                }
                month_chartheight = 90;
                isMonthSpinChanged = true;
                isMonthSpinChanged_inc = true;
                Intent intent = new Intent(stat_v2.this, nowsearching.class);
                startActivity(intent);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        month_spin_y.setOnItemSelectedListener(month_spin_ym_listener);
        month_spin_m.setOnItemSelectedListener(month_spin_ym_listener);



        //일간 - 범위 변경 버튼 리스너
        Button.OnClickListener day_prev_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Integer.parseInt(day_spin_y.getSelectedItem().toString());
                int month = Integer.parseInt(day_spin_m.getSelectedItem().toString());
                int day = Integer.parseInt(day_spin_d.getSelectedItem().toString());
                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day);
                cal.add(Calendar.DAY_OF_YEAR, -1);
                if(month != cal.get(Calendar.MONTH) + 1) isNeedYou = true;
                if(cal.get(Calendar.YEAR) > 2020 || cal.get(Calendar.YEAR) < 2009) return;
                //day_ignore_three = 2;
                if(!isNeedYou) {
                    day_spin_y.setSelection(2020 - cal.get(Calendar.YEAR));
                    day_spin_m.setSelection(cal.get(Calendar.MONTH));
                    day_spin_d.setSelection(cal.get(Calendar.DAY_OF_MONTH) - 1);
                } else
                {
                    System.out.println(cal.get(Calendar.YEAR) + "." + (cal.get(Calendar.MONTH) + 1) + "." + cal.get(Calendar.DAY_OF_MONTH));
                    day_spin_m.setSelection(cal.get(Calendar.MONTH));
                    year_rememberer = cal.get(Calendar.YEAR);
                    month_rememberer = cal.get(Calendar.MONTH);
                    day_rememberer = cal.get(Calendar.DAY_OF_MONTH);
                }
            }
        };
        Button day_prev = (Button) findViewById(R.id.stat_day_btn_prev);
        day_prev.setOnClickListener(day_prev_listener);

        Button.OnClickListener day_next_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Integer.parseInt(day_spin_y.getSelectedItem().toString());
                int month = Integer.parseInt(day_spin_m.getSelectedItem().toString());
                int day = Integer.parseInt(day_spin_d.getSelectedItem().toString());
                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day);
                cal.add(Calendar.DAY_OF_YEAR, 1);
                if(cal.get(Calendar.YEAR) > 2020 || cal.get(Calendar.YEAR) < 2009) return;
                //day_ignore_three = 2;
                day_spin_y.setSelection(2020 - cal.get(Calendar.YEAR));
                day_spin_m.setSelection(cal.get(Calendar.MONTH));
                day_spin_d.setSelection(cal.get(Calendar.DAY_OF_MONTH) - 1);
            }
        };
        Button day_next = (Button) findViewById(R.id.stat_day_btn_next);
        day_next.setOnClickListener(day_next_listener);



        //주간 - 범위 변경 버튼 리스너
        Button.OnClickListener week_prev_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Integer.parseInt(week_spin_y.getSelectedItem().toString());
                int month = Integer.parseInt(week_spin_m.getSelectedItem().toString());
                int day = Integer.parseInt(week_spin_d.getSelectedItem().toString());
                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day);
                cal.add(Calendar.DAY_OF_YEAR, -7);
                if(cal.get(Calendar.YEAR) > 2020 || cal.get(Calendar.YEAR) < 2009) return;
                //week_ignore_three = 2;
                if(month != cal.get(Calendar.MONTH) + 1) isNeedYou = true;


                if(!isNeedYou) {
                    week_spin_y.setSelection(2020 - cal.get(Calendar.YEAR));
                    week_spin_m.setSelection(cal.get(Calendar.MONTH));
                    week_spin_d.setSelection(cal.get(Calendar.DAY_OF_MONTH) - 1);
                } else {
                    week_spin_m.setSelection(cal.get(Calendar.MONTH));
                    year_rememberer = cal.get(Calendar.YEAR);
                    month_rememberer = cal.get(Calendar.MONTH);
                    day_rememberer = cal.get(Calendar.DAY_OF_MONTH);
                }

            }
        };
        Button week_prev = (Button) findViewById(R.id.stat_week_btn_prev);
        week_prev.setOnClickListener(week_prev_listener);


        Button.OnClickListener week_next_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Integer.parseInt(week_spin_y.getSelectedItem().toString());
                int month = Integer.parseInt(week_spin_m.getSelectedItem().toString());
                int day = Integer.parseInt(week_spin_d.getSelectedItem().toString());
                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day);
                cal.add(Calendar.DAY_OF_YEAR, 7);
                if(cal.get(Calendar.YEAR) > 2020 || cal.get(Calendar.YEAR) < 2009) return;
                //week_ignore_three = 2;
                week_spin_y.setSelection(2020 - cal.get(Calendar.YEAR));
                week_spin_m.setSelection(cal.get(Calendar.MONTH));
                week_spin_d.setSelection(cal.get(Calendar.DAY_OF_MONTH) - 1);
            }
        };
        Button week_next = (Button) findViewById(R.id.stat_week_btn_next);
        week_next.setOnClickListener(week_next_listener);


        //월간 - 범위 변경 버튼 리스너
        Button.OnClickListener month_prev_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = Integer.parseInt(month_spin_y.getSelectedItem().toString());
                int month = Integer.parseInt(month_spin_m.getSelectedItem().toString());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                cal.set(year, month - 1, day);
                cal.add(Calendar.MONTH, -1);
                if(cal.get(Calendar.YEAR) > 2020 || cal.get(Calendar.YEAR) < 2009) return;
                //month_ignore_two = 1;
                month_spin_y.setSelection(2020 - cal.get(Calendar.YEAR));
                month_spin_m.setSelection(cal.get(Calendar.MONTH));
            }
        };
        Button month_prev = (Button) findViewById(R.id.stat_month_btn_prev);
        month_prev.setOnClickListener(month_prev_listener);


        Button.OnClickListener month_next_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = Integer.parseInt(month_spin_y.getSelectedItem().toString());
                int month = Integer.parseInt(month_spin_m.getSelectedItem().toString());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                cal.set(year, month - 1, day);
                cal.add(Calendar.MONTH, 1);
                if(cal.get(Calendar.YEAR) > 2020 || cal.get(Calendar.YEAR) < 2009) return;
                //month_ignore_two = 1;
                month_spin_y.setSelection(2020 - cal.get(Calendar.YEAR));
                month_spin_m.setSelection(cal.get(Calendar.MONTH));
            }
        };
        Button month_next = (Button) findViewById(R.id.stat_month_btn_next);
        month_next.setOnClickListener(month_next_listener);



        //일간 - 추가버튼, 조회버튼 리스너
        Button.OnClickListener day_btn_inc_add_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, incexp_amount.class);
                intent.putExtra("statType", 11);
                intent.putExtra("n_year",Integer.parseInt(day_spin_y.getSelectedItem().toString()));
                intent.putExtra("n_month",Integer.parseInt(day_spin_m.getSelectedItem().toString()));
                intent.putExtra("n_day",Integer.parseInt(day_spin_d.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_ADD);
            }
        };
        ImageView day_btn_inc_add = (ImageView) findViewById(R.id.stat_day_btn_inc_add);
        day_btn_inc_add.setOnClickListener(day_btn_inc_add_listener);

        Button.OnClickListener day_btn_exp_add_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, incexp_amount.class);
                intent.putExtra("statType", 12);
                intent.putExtra("n_year",Integer.parseInt(day_spin_y.getSelectedItem().toString()));
                intent.putExtra("n_month",Integer.parseInt(day_spin_m.getSelectedItem().toString()));
                intent.putExtra("n_day",Integer.parseInt(day_spin_d.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_ADD);
            }
        };
        ImageView day_btn_exp_add = (ImageView) findViewById(R.id.stat_day_btn_exp_add);
        day_btn_exp_add.setOnClickListener(day_btn_exp_add_listener);

        Button.OnClickListener day_btn_inc_search_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, search.class);
                intent.putExtra("statType", 11);
                intent.putExtra("statDay_y",Integer.parseInt(day_spin_y.getSelectedItem().toString()));
                intent.putExtra("statDay_m",Integer.parseInt(day_spin_m.getSelectedItem().toString()));
                intent.putExtra("statDay_d",Integer.parseInt(day_spin_d.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_SEARCH);
            }
        };
        ImageView day_btn_inc_search = (ImageView) findViewById(R.id.stat_day_btn_inc_search);
        day_btn_inc_search.setOnClickListener(day_btn_inc_search_listener);

        Button.OnClickListener day_btn_exp_search_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, search.class);
                intent.putExtra("statType", 12);
                intent.putExtra("statDay_y",Integer.parseInt(day_spin_y.getSelectedItem().toString()));
                intent.putExtra("statDay_m",Integer.parseInt(day_spin_m.getSelectedItem().toString()));
                intent.putExtra("statDay_d",Integer.parseInt(day_spin_d.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_SEARCH);
            }
        };
        ImageView day_btn_exp_search = (ImageView) findViewById(R.id.stat_day_btn_exp_search);
        day_btn_exp_search.setOnClickListener(day_btn_exp_search_listener);




        //주간 - 추가버튼, 조회버튼 리스너
        Button.OnClickListener week_btn_inc_add_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, incexp_amount.class);
                intent.putExtra("statType", 21);
                intent.putExtra("n_year",Integer.parseInt(week_spin_y.getSelectedItem().toString()));
                intent.putExtra("n_month",Integer.parseInt(week_spin_m.getSelectedItem().toString()));
                intent.putExtra("n_day",Integer.parseInt(week_spin_d.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_ADD);
            }
        };
        ImageView week_btn_inc_add = (ImageView) findViewById(R.id.stat_week_btn_inc_add);
        week_btn_inc_add.setOnClickListener(week_btn_inc_add_listener);

        Button.OnClickListener week_btn_exp_add_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, incexp_amount.class);
                intent.putExtra("statType", 22);
                intent.putExtra("n_year",Integer.parseInt(week_spin_y.getSelectedItem().toString()));
                intent.putExtra("n_month",Integer.parseInt(week_spin_m.getSelectedItem().toString()));
                intent.putExtra("n_day",Integer.parseInt(week_spin_d.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_ADD);
            }
        };
        ImageView week_btn_exp_add = (ImageView) findViewById(R.id.stat_week_btn_exp_add);
        week_btn_exp_add.setOnClickListener(week_btn_exp_add_listener);

        Button.OnClickListener week_btn_inc_search_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, search.class);
                intent.putExtra("statType", 21);
                int c_year = Integer.parseInt(week_spin_y.getSelectedItem().toString());
                int c_month = Integer.parseInt(week_spin_m.getSelectedItem().toString());
                int c_day = Integer.parseInt(week_spin_d.getSelectedItem().toString());
                Calendar cal = day_ymdsetting.moveToEndOfWeek(c_year, c_month, c_day);
                intent.putExtra("statWeek_y_end", cal.get(Calendar.YEAR));
                intent.putExtra("statWeek_m_end", cal.get(Calendar.MONTH) + 1);
                intent.putExtra("statWeek_d_end", cal.get(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.DAY_OF_YEAR, -6);
                intent.putExtra("statWeek_y_start", cal.get(Calendar.YEAR));
                intent.putExtra("statWeek_m_start", cal.get(Calendar.MONTH) + 1);
                intent.putExtra("statWeek_d_start", cal.get(Calendar.DAY_OF_MONTH));
                startActivityForResult(intent, LINK_TO_SEARCH);
            }
        };
        ImageView week_btn_inc_search = (ImageView) findViewById(R.id.stat_week_btn_inc_search);
        week_btn_inc_search.setOnClickListener(week_btn_inc_search_listener);

        Button.OnClickListener week_btn_exp_search_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, search.class);
                intent.putExtra("statType", 22);
                int c_year = Integer.parseInt(week_spin_y.getSelectedItem().toString());
                int c_month = Integer.parseInt(week_spin_m.getSelectedItem().toString());
                int c_day = Integer.parseInt(week_spin_d.getSelectedItem().toString());
                Calendar cal = day_ymdsetting.moveToEndOfWeek(c_year, c_month, c_day);
                intent.putExtra("statWeek_y_end", cal.get(Calendar.YEAR));
                intent.putExtra("statWeek_m_end", cal.get(Calendar.MONTH) + 1);
                intent.putExtra("statWeek_d_end", cal.get(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.DAY_OF_YEAR, -6);
                intent.putExtra("statWeek_y_start", cal.get(Calendar.YEAR));
                intent.putExtra("statWeek_m_start", cal.get(Calendar.MONTH) + 1);
                intent.putExtra("statWeek_d_start", cal.get(Calendar.DAY_OF_MONTH));
                startActivityForResult(intent, LINK_TO_SEARCH);
            }
        };
        ImageView week_btn_exp_search = (ImageView) findViewById(R.id.stat_week_btn_exp_search);
        week_btn_exp_search.setOnClickListener(week_btn_exp_search_listener);




        //월간 - 추가버튼, 조회버튼 리스너
        Button.OnClickListener month_btn_inc_add_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, incexp_amount.class);
                Calendar cal = Calendar.getInstance();
                intent.putExtra("statType", 31);
                intent.putExtra("n_year",Integer.parseInt(month_spin_y.getSelectedItem().toString()));
                intent.putExtra("n_month",Integer.parseInt(month_spin_m.getSelectedItem().toString()));
                intent.putExtra("n_day", cal.get(Calendar.DAY_OF_MONTH));
                startActivityForResult(intent, LINK_TO_ADD);
            }
        };
        ImageView month_btn_inc_add = (ImageView) findViewById(R.id.stat_month_btn_inc_add);
        month_btn_inc_add.setOnClickListener(month_btn_inc_add_listener);

        Button.OnClickListener month_btn_exp_add_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, incexp_amount.class);
                Calendar cal = Calendar.getInstance();
                intent.putExtra("statType", 32);
                intent.putExtra("n_year",Integer.parseInt(month_spin_y.getSelectedItem().toString()));
                intent.putExtra("n_month",Integer.parseInt(month_spin_m.getSelectedItem().toString()));
                intent.putExtra("n_day", cal.get(Calendar.DAY_OF_MONTH));
                startActivityForResult(intent, LINK_TO_ADD);
            }
        };
        ImageView month_btn_exp_add = (ImageView) findViewById(R.id.stat_month_btn_exp_add);
        month_btn_exp_add.setOnClickListener(month_btn_exp_add_listener);

        Button.OnClickListener month_btn_inc_search_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, search.class);
                intent.putExtra("statType", 31);
                intent.putExtra("statMonth_y",Integer.parseInt(month_spin_y.getSelectedItem().toString()));
                intent.putExtra("statMonth_m",Integer.parseInt(month_spin_m.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_SEARCH);
            }
        };
        ImageView month_btn_inc_search = (ImageView) findViewById(R.id.stat_month_btn_inc_search);
        month_btn_inc_search.setOnClickListener(month_btn_inc_search_listener);

        Button.OnClickListener month_btn_exp_search_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stat_v2.this, search.class);
                intent.putExtra("statType", 32);
                intent.putExtra("statMonth_y",Integer.parseInt(month_spin_y.getSelectedItem().toString()));
                intent.putExtra("statMonth_m",Integer.parseInt(month_spin_m.getSelectedItem().toString()));
                startActivityForResult(intent, LINK_TO_SEARCH);
            }
        };
        ImageView month_btn_exp_search = (ImageView) findViewById(R.id.stat_month_btn_exp_search);
        month_btn_exp_search.setOnClickListener(month_btn_exp_search_listener);



        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        ImageButton back = (ImageButton) findViewById(R.id.stat_back);
        back.setOnClickListener(back_listener);

    }




    @Override
    public void onResume()
    {
        super.onResume();

        //일간 - 범위를 나타내는 텍스트
        TextView day_daterange = (TextView) findViewById(R.id.stat_day_period);
        String day_daterange_year = day_spin_y.getSelectedItem().toString();
        String day_daterange_month = day_spin_m.getSelectedItem().toString();
        String day_daterange_day = day_spin_d.getSelectedItem().toString();
        day_daterange.setText(day_daterange_year + "." + day_daterange_month + "." + day_daterange_day);

        //주간 - 범위를 나타내는 텍스트
        TextView week_daterange = (TextView) findViewById(R.id.stat_week_period);
        int week_daterange_year_i = Integer.parseInt(week_spin_y.getSelectedItem().toString());
        int week_daterange_month_i = Integer.parseInt(week_spin_m.getSelectedItem().toString());
        int week_daterange_day_i = Integer.parseInt(week_spin_d.getSelectedItem().toString());
        Calendar cal = day_ymdsetting.moveToEndOfWeek(week_daterange_year_i, week_daterange_month_i, week_daterange_day_i);
        String week_r_second = String.valueOf(cal.get(Calendar.YEAR)) + "." + String.valueOf(cal.get(Calendar.MONTH) + 1) + "." + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DAY_OF_YEAR, -6);
        String week_r_first = String.valueOf(cal.get(Calendar.YEAR)) + "." + String.valueOf(cal.get(Calendar.MONTH) + 1) + "." + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + " ~ ";
        week_daterange.setText(week_r_first + week_r_second);

        //월간 - 범위를 나타내는 텍스트
        TextView month_daterange = (TextView) findViewById(R.id.stat_month_period);
        int month_daterange_year = Integer.parseInt(month_spin_y.getSelectedItem().toString());
        int month_daterange_month = Integer.parseInt(month_spin_m.getSelectedItem().toString());
        Calendar cal2 = day_ymdsetting.moveToEndOfMonth(month_daterange_year, month_daterange_month);
        String month_r_first = String.valueOf(month_daterange_year) + "." + String.valueOf(month_daterange_month) + "." + "1 ~ ";
        String month_r_second = String.valueOf(month_daterange_year) + "." + String.valueOf(month_daterange_month) + "." + String.valueOf(cal2.get(Calendar.DAY_OF_MONTH));
        month_daterange.setText(month_r_first + month_r_second);

        //일간 차트 - 수입 Top 구현=================================================================================


        if(isDaySpinChanged_inc || isNeedRefresh) {
            day_chartheight_inc = 90;
            day_inctop_category_name.clear();
            BarData day_data = new BarData(day_inctop_category_name, getDataSet_day().get(1));
            HorizontalBarChart day_chart_inctop = (HorizontalBarChart) findViewById(R.id.stat_day_chart_inctop);

            OnChartValueSelectedListener day_chart_inctop_listener = new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    Intent intent = new Intent(stat_v2.this, search.class);
                    intent.putExtra("statType", 11);
                    intent.putExtra("statDay_y",Integer.parseInt(day_spin_y.getSelectedItem().toString()));
                    intent.putExtra("statDay_m",Integer.parseInt(day_spin_m.getSelectedItem().toString()));
                    intent.putExtra("statDay_d",Integer.parseInt(day_spin_d.getSelectedItem().toString()));
                    intent.putExtra("stat_isValueSelected", true);
                    intent.putExtra("statCategory", day_inctop_category_name.get(e.getXIndex()));

                    System.out.println("나의 인덱스는: " + e.getXIndex());
                    startActivityForResult(intent, LINK_TO_SEARCH);
                }

                @Override
                public void onNothingSelected() {

                }
            };

            day_chart_inctop.setOnChartValueSelectedListener(day_chart_inctop_listener);


            day_chart_inctop.clear();
            TextView day_noinc = (TextView) findViewById(R.id.stat_day_lbl_noinc);
            if (day_chartheight_inc != 90) {
                day_noinc.setVisibility(View.GONE);
                day_chart_inctop.setVisibility(View.VISIBLE);

                day_chart_inctop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, day_chartheight_inc));
                day_chart_inctop.setData(day_data);
                day_chart_inctop.setDescription("");
                day_chart_inctop.animateY(1000);
                day_chart_inctop.invalidate();
            } else {
                day_chart_inctop.setVisibility(View.GONE);
                day_noinc.setVisibility(View.VISIBLE);
            }

            isDaySpinChanged_inc = false;
        }


        //일간 차트 - 지출 Top 구현=================================================================================


        if(isDaySpinChanged || isNeedRefresh) {
            day_chartheight = 90;
            day_exptop_category_name.clear();
            BarData day_data = new BarData(day_exptop_category_name, getDataSet_day().get(0));
            HorizontalBarChart day_chart_exptop = (HorizontalBarChart) findViewById(R.id.stat_day_chart_exptop);

            OnChartValueSelectedListener day_chart_exptop_listener = new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    Intent intent = new Intent(stat_v2.this, search.class);
                    intent.putExtra("statType", 12);
                    intent.putExtra("statDay_y",Integer.parseInt(day_spin_y.getSelectedItem().toString()));
                    intent.putExtra("statDay_m",Integer.parseInt(day_spin_m.getSelectedItem().toString()));
                    intent.putExtra("statDay_d",Integer.parseInt(day_spin_d.getSelectedItem().toString()));
                    intent.putExtra("stat_isValueSelected", true);
                    intent.putExtra("statCategory", day_exptop_category_name.get(e.getXIndex()));

                    System.out.println("나의 인덱스는: " + e.getXIndex());
                    startActivityForResult(intent, LINK_TO_SEARCH);
                }

                @Override
                public void onNothingSelected() {

                }
            };



            day_chart_exptop.setOnChartValueSelectedListener(day_chart_exptop_listener);

            day_chart_exptop.clear();
            TextView day_noexp = (TextView) findViewById(R.id.stat_day_lbl_noexp);
            if (day_chartheight != 90) {
                day_noexp.setVisibility(View.GONE);
                day_chart_exptop.setVisibility(View.VISIBLE);

                day_chart_exptop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, day_chartheight));
                day_chart_exptop.setData(day_data);
                day_chart_exptop.setDescription("");
                day_chart_exptop.animateY(1000);
                day_chart_exptop.invalidate();
            } else {
                day_chart_exptop.setVisibility(View.GONE);
                day_noexp.setVisibility(View.VISIBLE);
            }
            isDaySpinChanged = false;
        }





        //주간 차트 - 수입 Top 구현=================================================================================


        if(isWeekSpinChanged_inc || isNeedRefresh) {
            week_chartheight_inc = 90;
            week_inctop_category_name.clear();
            BarData week_data = new BarData(week_inctop_category_name, getDataSet_week().get(1));
            HorizontalBarChart week_chart_inctop = (HorizontalBarChart) findViewById(R.id.stat_week_chart_inctop);

            OnChartValueSelectedListener week_chart_inctop_listener = new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    Intent intent = new Intent(stat_v2.this, search.class);
                    intent.putExtra("statType", 21);
                    int c_year = Integer.parseInt(week_spin_y.getSelectedItem().toString());
                    int c_month = Integer.parseInt(week_spin_m.getSelectedItem().toString());
                    int c_day = Integer.parseInt(week_spin_d.getSelectedItem().toString());
                    Calendar cal = day_ymdsetting.moveToEndOfWeek(c_year, c_month, c_day);
                    intent.putExtra("statWeek_y_end", cal.get(Calendar.YEAR));
                    intent.putExtra("statWeek_m_end", cal.get(Calendar.MONTH) + 1);
                    intent.putExtra("statWeek_d_end", cal.get(Calendar.DAY_OF_MONTH));
                    cal.add(Calendar.DAY_OF_YEAR, -6);
                    intent.putExtra("statWeek_y_start", cal.get(Calendar.YEAR));
                    intent.putExtra("statWeek_m_start", cal.get(Calendar.MONTH) + 1);
                    intent.putExtra("statWeek_d_start", cal.get(Calendar.DAY_OF_MONTH));
                    intent.putExtra("stat_isValueSelected", true);
                    intent.putExtra("statCategory", week_inctop_category_name.get(e.getXIndex()));
                    startActivityForResult(intent, LINK_TO_SEARCH);
                }

                @Override
                public void onNothingSelected() {

                }
            };

            week_chart_inctop.setOnChartValueSelectedListener(week_chart_inctop_listener);


            week_chart_inctop.clear();
            TextView week_noinc = (TextView) findViewById(R.id.stat_week_lbl_noinc);
            if (week_chartheight_inc != 90) {
                week_noinc.setVisibility(View.GONE);
                week_chart_inctop.setVisibility(View.VISIBLE);

                week_chart_inctop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, week_chartheight_inc));
                week_chart_inctop.setData(week_data);
                week_chart_inctop.setDescription("");
                week_chart_inctop.animateY(1000);
                week_chart_inctop.invalidate();
            } else {
                week_chart_inctop.setVisibility(View.GONE);
                week_noinc.setVisibility(View.VISIBLE);
            }

            isWeekSpinChanged_inc = false;
        }



        //주간 차트 - 지출 Top 구현=================================================================================


        if(isWeekSpinChanged || isNeedRefresh) {
            week_chartheight = 90;
            week_exptop_category_name.clear();
            BarData week_data = new BarData(week_exptop_category_name, getDataSet_week().get(0));
            HorizontalBarChart week_chart_exptop = (HorizontalBarChart) findViewById(R.id.stat_week_chart_exptop);

            OnChartValueSelectedListener week_chart_exptop_listener = new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    Intent intent = new Intent(stat_v2.this, search.class);
                    intent.putExtra("statType", 22);
                    int c_year = Integer.parseInt(week_spin_y.getSelectedItem().toString());
                    int c_month = Integer.parseInt(week_spin_m.getSelectedItem().toString());
                    int c_day = Integer.parseInt(week_spin_d.getSelectedItem().toString());
                    Calendar cal = day_ymdsetting.moveToEndOfWeek(c_year, c_month, c_day);
                    intent.putExtra("statWeek_y_end", cal.get(Calendar.YEAR));
                    intent.putExtra("statWeek_m_end", cal.get(Calendar.MONTH) + 1);
                    intent.putExtra("statWeek_d_end", cal.get(Calendar.DAY_OF_MONTH));
                    cal.add(Calendar.DAY_OF_YEAR, -6);
                    intent.putExtra("statWeek_y_start", cal.get(Calendar.YEAR));
                    intent.putExtra("statWeek_m_start", cal.get(Calendar.MONTH) + 1);
                    intent.putExtra("statWeek_d_start", cal.get(Calendar.DAY_OF_MONTH));
                    intent.putExtra("stat_isValueSelected", true);
                    intent.putExtra("statCategory", week_exptop_category_name.get(e.getXIndex()));
                    startActivityForResult(intent, LINK_TO_SEARCH);
                }

                @Override
                public void onNothingSelected() {

                }
            };



            week_chart_exptop.setOnChartValueSelectedListener(week_chart_exptop_listener);

            week_chart_exptop.clear();
            TextView week_noexp = (TextView) findViewById(R.id.stat_week_lbl_noexp);
            if (week_chartheight != 90) {
                week_noexp.setVisibility(View.GONE);
                week_chart_exptop.setVisibility(View.VISIBLE);

                week_chart_exptop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, week_chartheight));
                week_chart_exptop.setData(week_data);
                week_chart_exptop.setDescription("");
                week_chart_exptop.animateY(1000);
                week_chart_exptop.invalidate();
            } else {
                week_chart_exptop.setVisibility(View.GONE);
                week_noexp.setVisibility(View.VISIBLE);
            }
            isWeekSpinChanged = false;
        }



        //월간 차트 - 수입 Top 구현=================================================================================

        if(isMonthSpinChanged_inc || isNeedRefresh) {
            month_chartheight_inc = 90;
            month_inctop_category_name.clear();
            BarData month_data = new BarData(month_inctop_category_name, getDataSet_month2().get(1));
            HorizontalBarChart month_chart_inctop = (HorizontalBarChart) findViewById(R.id.stat_month_chart_inctop);

            OnChartValueSelectedListener month_chart_inctop_listener = new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    Intent intent = new Intent(stat_v2.this, search.class);
                    intent.putExtra("statType", 31);
                    intent.putExtra("statMonth_y",Integer.parseInt(month_spin_y.getSelectedItem().toString()));
                    intent.putExtra("statMonth_m",Integer.parseInt(month_spin_m.getSelectedItem().toString()));
                    intent.putExtra("stat_isValueSelected", true);
                    intent.putExtra("statCategory", month_inctop_category_name.get(e.getXIndex()));
                    startActivityForResult(intent, LINK_TO_SEARCH);
                }

                @Override
                public void onNothingSelected() {

                }
            };

            month_chart_inctop.setOnChartValueSelectedListener(month_chart_inctop_listener);


            month_chart_inctop.clear();
            TextView month_noinc = (TextView) findViewById(R.id.stat_month_lbl_noinc);
            if (month_chartheight_inc != 90) {
                month_noinc.setVisibility(View.GONE);
                month_chart_inctop.setVisibility(View.VISIBLE);

                month_chart_inctop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, month_chartheight_inc));
                month_chart_inctop.setData(month_data);
                month_chart_inctop.setDescription("");
                month_chart_inctop.animateY(1000);
                month_chart_inctop.invalidate();
            } else {
                month_chart_inctop.setVisibility(View.GONE);
                month_noinc.setVisibility(View.VISIBLE);
            }

            isMonthSpinChanged_inc = false;
        }


        //월간 차트 - 지출 Top 구현=================================================================================


        if(isMonthSpinChanged || isNeedRefresh) {
            month_chartheight = 90;
            month_exptop_category_name.clear();
            BarData month_data = new BarData(month_exptop_category_name, getDataSet_month2().get(0));
            HorizontalBarChart month_chart_exptop = (HorizontalBarChart) findViewById(R.id.stat_month_chart_exptop);

            OnChartValueSelectedListener month_chart_exptop_listener = new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    Intent intent = new Intent(stat_v2.this, search.class);
                    intent.putExtra("statType", 32);
                    intent.putExtra("statMonth_y",Integer.parseInt(month_spin_y.getSelectedItem().toString()));
                    intent.putExtra("statMonth_m",Integer.parseInt(month_spin_m.getSelectedItem().toString()));
                    intent.putExtra("stat_isValueSelected", true);
                    intent.putExtra("statCategory", month_exptop_category_name.get(e.getXIndex()));
                    startActivityForResult(intent, LINK_TO_SEARCH);
                }

                @Override
                public void onNothingSelected() {

                }
            };

            month_chart_exptop.setOnChartValueSelectedListener(month_chart_exptop_listener);


            month_chart_exptop.clear();
            TextView month_noexp = (TextView) findViewById(R.id.stat_month_lbl_noexp);
            if (month_chartheight != 90) {
                month_noexp.setVisibility(View.GONE);
                month_chart_exptop.setVisibility(View.VISIBLE);

                month_chart_exptop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, month_chartheight));
                month_chart_exptop.setData(month_data);
                month_chart_exptop.setDescription("");
                month_chart_exptop.animateY(1000);
                month_chart_exptop.invalidate();
            } else {
                month_chart_exptop.setVisibility(View.GONE);
                month_noexp.setVisibility(View.VISIBLE);
            }

            isMonthSpinChanged = false;
        }

        isNeedRefresh = false;
        isNeedYou = false;


        //월간 차트 - 만족도 통계 구현=================================================================================
        final RatingBar satisStatStar = (RatingBar) findViewById(R.id.stat_month_rating_satisfaction);
        final TextView satisStatText = (TextView) findViewById(R.id.stat_month_lbl_rating_satisfaction);

        final DecimalFormat df = new DecimalFormat("0.00");
        satisStatStar.setRating(Float.parseFloat(df.format(monthlyRating)));
        satisStatText.setText(df.format(monthlyRating));




       /* if(isMonthSpinChanged || isNeedRefresh) {
            month_chartheight = 90;
            month_exptop_category_name.clear();
            BarData month_data = new BarData(month_exptop_category_name, getDataSet_month());
            HorizontalBarChart month_chart_exptop = (HorizontalBarChart) findViewById(R.id.stat_month_chart_exptop);
            TextView month_noexp = (TextView) findViewById(R.id.stat_month_lbl_noexp);
            if (month_chartheight != 90) {
                month_noexp.setVisibility(View.GONE);
                month_chart_exptop.setVisibility(View.VISIBLE);
                month_chart_exptop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, month_chartheight));
                month_chart_exptop.setData(month_data);
                month_chart_exptop.setDescription("");
                month_chart_exptop.animateY(1000);
                month_chart_exptop.invalidate();
            } else {
                month_chart_exptop.setVisibility(View.GONE);
                month_noexp.setVisibility(View.VISIBLE);
            }
            isMonthSpinChanged = false;
        }*/


    }

    public void onPause()
    {
        super.onPause();
        //overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LINK_TO_SEARCH || requestCode == LINK_TO_ADD) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                isNeedRefresh = data.getBooleanExtra("statRefresh", false);
                System.out.println("What's your value?: " + isNeedRefresh);
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }


    private ArrayList<ArrayList<BarDataSet>> getDataSet_day() {
        ArrayList<ArrayList<BarDataSet>> dataSets;

        int cat_size = expenditure_category.size();
        int cat_size_inc = income_category.size();
        long[] cat_sum = new long[cat_size];
        long[] cat_sum_inc = new long[cat_size_inc];
        Arrays.fill(cat_sum, 0);
        Arrays.fill(cat_sum_inc, 0);
        String[] cat_name = new String[cat_size];
        String[] cat_name_inc = new String[cat_size_inc];
        for(int i = 0; i < cat_size ; i ++)
        {
            cat_name[i] = expenditure_category.get(i); //카테고리 목록을 모두 불러와서 저장시킨다.
        }
        for(int i = 0; i < cat_size_inc ; i ++)
        {
            cat_name_inc[i] = income_category.get(i); //수입도 마찬가지
        }

        int nowdate = day_ymdsetting.makeYMDInt(Integer.parseInt(day_spin_y.getSelectedItem().toString()),
                                Integer.parseInt(day_spin_m.getSelectedItem().toString()),
                                Integer.parseInt(day_spin_d.getSelectedItem().toString()));
        for(int i = 0; i < search_adapter.getCount() ; i ++) {

            int comp_year = search_adapter.getListItem().get(i).getyear();
            int comp_month = search_adapter.getListItem().get(i).getmonth();
            int comp_day = search_adapter.getListItem().get(i).getday();
            int compdate = day_ymdsetting.makeYMDInt(comp_year, comp_month, comp_day);
            if(nowdate < compdate)
            {
                continue;
            }
            else {
                if (nowdate != compdate) break;
                if (search_adapter.getListItem().get(i).getinex() == 2) {
                    cat_sum[expenditure_category.indexOf(search_adapter.getListItem().get(i).getwhere())] += search_adapter.getListItem().get(i).getamount_int() * search_adapter.getListItem().get(i).getincludeStat();
                }
                else
                {
                    cat_sum_inc[income_category.indexOf(search_adapter.getListItem().get(i).getwhere())] += search_adapter.getListItem().get(i).getamount_int() * search_adapter.getListItem().get(i).getincludeStat();
                }
            }
        }

        long temp;
        String temp_s;
        for(int i = 0; i < cat_size - 1; i ++)
        {
            for(int j = 0; j < cat_size - 1 - i ; j ++)
            {
                if(cat_sum[j] > cat_sum[j+1])
                {
                    temp = cat_sum[j];
                    cat_sum[j] = cat_sum[j+1];
                    cat_sum[j+1] = temp;

                    temp_s = cat_name[j];
                    cat_name[j] = cat_name[j+1];
                    cat_name[j+1] = temp_s;
                }
            }
        }


        long temp_inc;
        String temp_s_inc;
        for(int i = 0; i < cat_size_inc - 1; i ++)
        {
            for(int j = 0; j < cat_size_inc - 1 - i ; j ++)
            {
                if(cat_sum_inc[j] > cat_sum_inc[j+1])
                {
                    temp_inc = cat_sum_inc[j];
                    cat_sum_inc[j] = cat_sum_inc[j+1];
                    cat_sum_inc[j+1] = temp_inc;

                    temp_s_inc = cat_name_inc[j];
                    cat_name_inc[j] = cat_name_inc[j+1];
                    cat_name_inc[j+1] = temp_s_inc;
                }
            }
        }

        ArrayList<BarEntry> valueSet_exp = new ArrayList<>();
        ArrayList<BarEntry> valueSet_inc = new ArrayList<>();

        BarEntry[] day_entry = new BarEntry[cat_size];
        BarEntry[] day_entry_inc = new BarEntry[cat_size_inc];

        int day_cnt = 0;
        long cat_sumup = 0;
        day_exptop_category_name.clear();
        for(int i = 0; i < cat_size; i ++)
        {
            if(cat_sum[i] == 0) continue;
            day_chartheight += 110;
            day_exptop_category_name.add(cat_name[i]);
            day_entry[day_cnt] = new BarEntry(cat_sum[i], day_cnt);
            cat_sumup += cat_sum[i];
            valueSet_exp.add(day_entry[day_cnt++]);
        }
        TextView day_sumup = (TextView) findViewById(R.id.stat_day_lbl_amount);
        String day_sumup_s = String.format("%,d", cat_sumup);
        day_sumup.setText(day_sumup_s + "원");


        int day_cnt_inc = 0;
        long cat_sumup_inc = 0;
        day_inctop_category_name.clear();
        for(int i = 0; i < cat_size_inc ; i++)
        {
            if(cat_sum_inc[i] == 0) continue;
            day_chartheight_inc += 110;
            day_inctop_category_name.add(cat_name_inc[i]);
            day_entry_inc[day_cnt_inc] = new BarEntry(cat_sum_inc[i], day_cnt_inc);
            cat_sumup_inc += cat_sum_inc[i];
            valueSet_inc.add(day_entry_inc[day_cnt_inc++]);
        }
        TextView day_sumup_inc = (TextView) findViewById(R.id.stat_day_lbl_amount_inc);
        String day_sumup_s_inc = String.format("%,d", cat_sumup_inc);
        day_sumup_inc.setText(day_sumup_s_inc + "원");


        BarDataSet barDataSet_exp = new BarDataSet(valueSet_exp, "");
        barDataSet_exp.setColor(Color.rgb(0, 155, 0));
        ArrayList<BarDataSet> dataSets_exp = new ArrayList<>();
        dataSets_exp.add(barDataSet_exp);

        BarDataSet barDataSet_inc = new BarDataSet(valueSet_inc, "");
        barDataSet_exp.setColor(Color.rgb(0, 155, 0));
        ArrayList<BarDataSet> dataSets_inc = new ArrayList<>();
        dataSets_inc.add(barDataSet_inc);

        dataSets = new ArrayList<>();
        dataSets.add(dataSets_exp);
        dataSets.add(dataSets_inc);

        return dataSets;
    }



    private ArrayList<ArrayList<BarDataSet>> getDataSet_week() {
        ArrayList<ArrayList<BarDataSet>> dataSets;

        int cat_size = expenditure_category.size();
        int cat_size_inc = income_category.size();
        long[] cat_sum = new long[cat_size];
        long[] cat_sum_inc = new long[cat_size_inc];
        Arrays.fill(cat_sum, 0);
        Arrays.fill(cat_sum_inc, 0);
        String[] cat_name = new String[cat_size];
        String[] cat_name_inc = new String[cat_size_inc];
        for(int i = 0; i < cat_size ; i ++)
        {
            cat_name[i] = expenditure_category.get(i); //카테고리 목록을 모두 불러와서 저장시킨다.
        }
        for(int i = 0; i < cat_size_inc ; i ++)
        {
            cat_name_inc[i] = income_category.get(i); //수입도 마찬가지
        }
        MyYMDSetting ymd = new MyYMDSetting();
        Calendar cal = ymd.moveToEndOfWeek(
                Integer.parseInt(week_spin_y.getSelectedItem().toString()),
                Integer.parseInt(week_spin_m.getSelectedItem().toString()),
                Integer.parseInt(week_spin_d.getSelectedItem().toString()));
        int endofweek = ymd.makeYMDInt(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DAY_OF_YEAR, -6);
        int startofweek = ymd.makeYMDInt(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));

        for(int i = 0; i < search_adapter.getCount() ; i ++) {

            int comp_year = search_adapter.getListItem().get(i).getyear();
            int comp_month = search_adapter.getListItem().get(i).getmonth();
            int comp_day = search_adapter.getListItem().get(i).getday();
            int compdate = day_ymdsetting.makeYMDInt(comp_year, comp_month, comp_day);
            if(endofweek < compdate)
            {
                continue;
            }
            else {
                if (startofweek > compdate) break;
                if (search_adapter.getListItem().get(i).getinex() == 2) {
                    cat_sum[expenditure_category.indexOf(search_adapter.getListItem().get(i).getwhere())] += search_adapter.getListItem().get(i).getamount_int() * search_adapter.getListItem().get(i).getincludeStat();
                }
                else
                {
                    cat_sum_inc[income_category.indexOf(search_adapter.getListItem().get(i).getwhere())] += search_adapter.getListItem().get(i).getamount_int() * search_adapter.getListItem().get(i).getincludeStat();
                }
            }
        }

        long temp;
        String temp_s;
        for(int i = 0; i < cat_size - 1; i ++)
        {
            for(int j = 0; j < cat_size - 1 - i ; j ++)
            {
                if(cat_sum[j] > cat_sum[j+1])
                {
                    temp = cat_sum[j];
                    cat_sum[j] = cat_sum[j+1];
                    cat_sum[j+1] = temp;

                    temp_s = cat_name[j];
                    cat_name[j] = cat_name[j+1];
                    cat_name[j+1] = temp_s;
                }
            }
        }


        long temp_inc;
        String temp_s_inc;
        for(int i = 0; i < cat_size_inc - 1; i ++)
        {
            for(int j = 0; j < cat_size_inc - 1 - i ; j ++)
            {
                if(cat_sum_inc[j] > cat_sum_inc[j+1])
                {
                    temp_inc = cat_sum_inc[j];
                    cat_sum_inc[j] = cat_sum_inc[j+1];
                    cat_sum_inc[j+1] = temp_inc;

                    temp_s_inc = cat_name_inc[j];
                    cat_name_inc[j] = cat_name_inc[j+1];
                    cat_name_inc[j+1] = temp_s_inc;
                }
            }
        }

        ArrayList<BarEntry> valueSet_exp = new ArrayList<>();
        ArrayList<BarEntry> valueSet_inc = new ArrayList<>();

        BarEntry[] week_entry = new BarEntry[cat_size];
        BarEntry[] week_entry_inc = new BarEntry[cat_size_inc];

        int week_cnt = 0;
        long cat_sumup = 0;
        week_exptop_category_name.clear();
        for(int i = 0; i < cat_size; i ++)
        {
            if(cat_sum[i] == 0) continue;
            week_chartheight += 110;
            week_exptop_category_name.add(cat_name[i]);
            week_entry[week_cnt] = new BarEntry(cat_sum[i], week_cnt);
            cat_sumup += cat_sum[i];
            valueSet_exp.add(week_entry[week_cnt++]);
        }
        TextView week_sumup = (TextView) findViewById(R.id.stat_week_lbl_amount);
        String week_sumup_s = String.format("%,d", cat_sumup);
        week_sumup.setText(week_sumup_s + "원");


        int week_cnt_inc = 0;
        long cat_sumup_inc = 0;
        week_inctop_category_name.clear();
        for(int i = 0; i < cat_size_inc ; i++)
        {
            if(cat_sum_inc[i] == 0) continue;
            week_chartheight_inc += 110;
            week_inctop_category_name.add(cat_name_inc[i]);
            week_entry_inc[week_cnt_inc] = new BarEntry(cat_sum_inc[i], week_cnt_inc);
            cat_sumup_inc += cat_sum_inc[i];
            valueSet_inc.add(week_entry_inc[week_cnt_inc++]);
        }
        TextView week_sumup_inc = (TextView) findViewById(R.id.stat_week_lbl_amount_inc);
        String week_sumup_s_inc = String.format("%,d", cat_sumup_inc);
        week_sumup_inc.setText(week_sumup_s_inc + "원");


        BarDataSet barDataSet_exp = new BarDataSet(valueSet_exp, "");
        barDataSet_exp.setColor(Color.rgb(0, 155, 0));
        ArrayList<BarDataSet> dataSets_exp = new ArrayList<>();
        dataSets_exp.add(barDataSet_exp);

        BarDataSet barDataSet_inc = new BarDataSet(valueSet_inc, "");
        barDataSet_exp.setColor(Color.rgb(0, 155, 0));
        ArrayList<BarDataSet> dataSets_inc = new ArrayList<>();
        dataSets_inc.add(barDataSet_inc);

        dataSets = new ArrayList<>();
        dataSets.add(dataSets_exp);
        dataSets.add(dataSets_inc);

        return dataSets;
    }



    private ArrayList<ArrayList<BarDataSet>> getDataSet_month2() {
        ArrayList<ArrayList<BarDataSet>> dataSets;

        int cat_size = expenditure_category.size();
        int cat_size_inc = income_category.size();
        int numOfExps = 0;
        int sumOfRating = 0;
        long[] cat_sum = new long[cat_size];
        long[] cat_sum_inc = new long[cat_size_inc];
        Arrays.fill(cat_sum, 0);
        Arrays.fill(cat_sum_inc, 0);
        String[] cat_name = new String[cat_size];
        String[] cat_name_inc = new String[cat_size_inc];
        for(int i = 0; i < cat_size ; i ++)
        {
            cat_name[i] = expenditure_category.get(i); //카테고리 목록을 모두 불러와서 저장시킨다.
        }
        for(int i = 0; i < cat_size_inc ; i ++)
        {
            cat_name_inc[i] = income_category.get(i); //수입도 마찬가지
        }

        int nowdate = day_ymdsetting.makeYMDInt(Integer.parseInt(month_spin_y.getSelectedItem().toString()),
                Integer.parseInt(month_spin_m.getSelectedItem().toString()), 0);
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
                if (search_adapter.getListItem().get(i).getinex() == 2) { //지출 데이터
                    cat_sum[expenditure_category.indexOf(search_adapter.getListItem().get(i).getwhere())] += search_adapter.getListItem().get(i).getamount_int() * search_adapter.getListItem().get(i).getincludeStat();
                    sumOfRating += search_adapter.getListItem().get(i).getemonum() * search_adapter.getListItem().get(i).getincludeStat(); //만족도 자료 만들기
                    if(search_adapter.getListItem().get(i).getincludeStat() == 1) numOfExps ++;
                }
                else //수입 데이터
                {
                    cat_sum_inc[income_category.indexOf(search_adapter.getListItem().get(i).getwhere())] += search_adapter.getListItem().get(i).getamount_int() * search_adapter.getListItem().get(i).getincludeStat();
                }
            }
        }

        if(numOfExps == 0)
        {
            monthlyRating = 0;
        }
        else
        {
            monthlyRating = (float) sumOfRating / (float) numOfExps;
        }


        long temp;
        String temp_s;
        for(int i = 0; i < cat_size - 1; i ++)
        {
            for(int j = 0; j < cat_size - 1 - i ; j ++)
            {
                if(cat_sum[j] > cat_sum[j+1])
                {
                    temp = cat_sum[j];
                    cat_sum[j] = cat_sum[j+1];
                    cat_sum[j+1] = temp;

                    temp_s = cat_name[j];
                    cat_name[j] = cat_name[j+1];
                    cat_name[j+1] = temp_s;
                }
            }
        }


        long temp_inc;
        String temp_s_inc;
        for(int i = 0; i < cat_size_inc - 1; i ++)
        {
            for(int j = 0; j < cat_size_inc - 1 - i ; j ++)
            {
                if(cat_sum_inc[j] > cat_sum_inc[j+1])
                {
                    temp_inc = cat_sum_inc[j];
                    cat_sum_inc[j] = cat_sum_inc[j+1];
                    cat_sum_inc[j+1] = temp_inc;

                    temp_s_inc = cat_name_inc[j];
                    cat_name_inc[j] = cat_name_inc[j+1];
                    cat_name_inc[j+1] = temp_s_inc;
                }
            }
        }

        ArrayList<BarEntry> valueSet_exp = new ArrayList<>();
        ArrayList<BarEntry> valueSet_inc = new ArrayList<>();

        BarEntry[] month_entry = new BarEntry[cat_size];
        BarEntry[] month_entry_inc = new BarEntry[cat_size_inc];

        int month_cnt = 0;
        long cat_sumup = 0;
        month_exptop_category_name.clear();
        for(int i = 0; i < cat_size; i ++)
        {
            if(cat_sum[i] == 0) continue;
            month_chartheight += 110;
            month_exptop_category_name.add(cat_name[i]);
            month_entry[month_cnt] = new BarEntry(cat_sum[i], month_cnt);
            cat_sumup += cat_sum[i];
            valueSet_exp.add(month_entry[month_cnt++]);
        }
        TextView month_sumup = (TextView) findViewById(R.id.stat_month_lbl_amount);
        String month_sumup_s = String.format("%,d", cat_sumup);
        month_sumup.setText(month_sumup_s + "원");


        int month_cnt_inc = 0;
        long cat_sumup_inc = 0;
        month_inctop_category_name.clear();
        for(int i = 0; i < cat_size_inc ; i++)
        {
            if(cat_sum_inc[i] == 0) continue;
            month_chartheight_inc += 110;
            month_inctop_category_name.add(cat_name_inc[i]);
            month_entry_inc[month_cnt_inc] = new BarEntry(cat_sum_inc[i], month_cnt_inc);
            cat_sumup_inc += cat_sum_inc[i];
            valueSet_inc.add(month_entry_inc[month_cnt_inc++]);
        }
        TextView month_sumup_inc = (TextView) findViewById(R.id.stat_month_lbl_amount_inc);
        String month_sumup_s_inc = String.format("%,d", cat_sumup_inc);
        month_sumup_inc.setText(month_sumup_s_inc + "원");


        BarDataSet barDataSet_exp = new BarDataSet(valueSet_exp, "");
        barDataSet_exp.setColor(Color.rgb(0, 155, 0));
        ArrayList<BarDataSet> dataSets_exp = new ArrayList<>();
        dataSets_exp.add(barDataSet_exp);

        BarDataSet barDataSet_inc = new BarDataSet(valueSet_inc, "");
        barDataSet_exp.setColor(Color.rgb(0, 155, 0));
        ArrayList<BarDataSet> dataSets_inc = new ArrayList<>();
        dataSets_inc.add(barDataSet_inc);

        dataSets = new ArrayList<>();
        dataSets.add(dataSets_exp);
        dataSets.add(dataSets_inc);

        return dataSets;
    }


    private ArrayList<BarDataSet> getDataSet_month() {
        ArrayList<BarDataSet> dataSets = null;

        int cat_size = expenditure_category.size();
        long[] cat_sum = new long[cat_size];
        Arrays.fill(cat_sum, 0);
        String[] cat_name = new String[cat_size];
        for(int i = 0; i < cat_size ; i ++)
        {
            cat_name[i] = expenditure_category.get(i); //카테고리 목록을 모두 불러와서 저장시킨다.
        }
        int nowdate = day_ymdsetting.makeYMDInt(Integer.parseInt(month_spin_y.getSelectedItem().toString()),
                Integer.parseInt(month_spin_m.getSelectedItem().toString()), 0);
        for(int i = 0; i < search_adapter.getCount() ; i ++) {

            int comp_year = search_adapter.getListItem().get(i).getyear();
            int comp_month = search_adapter.getListItem().get(i).getmonth();
            int compdate = day_ymdsetting.makeYMDInt(comp_year, comp_month, 0);
            if(nowdate < compdate)
            {
                continue;
            }
            else
            {
                if(nowdate != compdate) break;
                if(search_adapter.getListItem().get(i).getinex() == 2) {
                    cat_sum[expenditure_category.indexOf(search_adapter.getListItem().get(i).getwhere())] += search_adapter.getListItem().get(i).getamount_int();
                }
            }
        }

        //오름차순 정렬
        long temp;
        String temp_s;
        for(int i = 0; i < cat_size - 1; i ++)
        {
            for(int j = 0; j < cat_size - 1 - i ; j ++)
            {
                if(cat_sum[j] > cat_sum[j+1])
                {
                    temp = cat_sum[j];
                    cat_sum[j] = cat_sum[j+1];
                    cat_sum[j+1] = temp;

                    temp_s = cat_name[j];
                    cat_name[j] = cat_name[j+1];
                    cat_name[j+1] = temp_s;
                }
            }
        }

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        BarEntry[] day_entry = new BarEntry[cat_size];

        int day_cnt = 0;
        long cat_sumup = 0;
        for(int i = 0; i < cat_size; i ++)
        {
            if(cat_sum[i] == 0) continue;
            month_chartheight += 110;
            month_exptop_category_name.add(cat_name[i]);
            day_entry[day_cnt] = new BarEntry(cat_sum[i], day_cnt);
            cat_sumup += cat_sum[i];
            valueSet1.add(day_entry[day_cnt++]);
        }

        TextView month_sumup = (TextView) findViewById(R.id.stat_month_lbl_amount);
        String month_sumup_s = String.format("%,d", cat_sumup);
        month_sumup.setText(month_sumup_s + "원");

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "");
        barDataSet1.setColor(Color.rgb(0, 155, 0));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }
}
