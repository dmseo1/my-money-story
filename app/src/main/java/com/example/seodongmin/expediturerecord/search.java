package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.content.*;
import android.widget.*;
import android.view.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.seodongmin.expediturerecord.init.adapter_exc;
import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_2;

/**
 * Created by seodongmin on 2017-04-20.
 */

public class search extends AppCompatActivity{

    Parcelable state;

    static final listview_search_list_adapter search_adapter = new listview_search_list_adapter();
    static final listview_search_list_adapter search_adapter_result = new listview_search_list_adapter();
    static final listview_search_list_adapter search_adapter_result_2 = new listview_search_list_adapter();

    static final ArrayList<String> key = new ArrayList();

    int RESULT_FBLENGTH = 8888;

    boolean[] first_inc_1 = new boolean[1];
    boolean[] first_inc_2 = new boolean[1];
    boolean[] first_exp_1 = new boolean[1];
    boolean[] first_exp_2 = new boolean[1];
    boolean[] first_expw_1 = new boolean[1];
    boolean[] first_expw_2 = new boolean[1];

    boolean isfolded = false;

    boolean isFTChanged = false;

    int last_year_f;
    int last_month_f;
    int last_day_f;

    int last_year_t;
    int last_month_t;
    int last_day_t;

    int ignore_one_listen_f = 0;
    int ignore_one_listen_t = 0;

    ListView listview;

    Spinner spinner_y;
    Spinner spinner_m;
    Spinner spinner_from_y;
    Spinner spinner_from_m;
    Spinner spinner_from_d;
    Spinner spinner_to_y;
    Spinner spinner_to_m;
    Spinner spinner_to_d;

    ImageView img_from;
    ImageView img_to;
    TextView lbl_from;
    TextView lbl_to;

    Spinner s_spin_inc_1;
    Spinner s_spin_inc_2;
    Spinner s_spin_inc_3;
    Spinner s_spin_exp_1;
    Spinner s_spin_exp_2;
    Spinner s_spin_exp_3;
    Spinner s_spin_expw_1;
    Spinner s_spin_expw_2;
    Spinner s_spin_expw_3;

    RadioButton period_by_ym;
    RadioButton period_by_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        first_inc_1[0] = false;
        first_inc_2[0] = false;
        first_exp_1[0] = false;
        first_exp_2[0] = false;





        // 연도/월 스피너 데이터 출력
        Calendar cal = Calendar.getInstance();
        int standard_y = cal.get(Calendar.YEAR);
        int standard_m = cal.get(Calendar.MONTH);

        spinner_y = (Spinner) findViewById(R.id.search_spin_y);
        ArrayAdapter adapter_y = ArrayAdapter.createFromResource(this, R.array.ie_years, android.R.layout.simple_spinner_item);
        adapter_y.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_y.setAdapter(adapter_y);
        spinner_y.setSelection(2020 - standard_y);

        spinner_m = (Spinner) findViewById(R.id.search_spin_m);
        ArrayAdapter adapter_m = ArrayAdapter.createFromResource(this, R.array.ie_months, android.R.layout.simple_spinner_item);
        adapter_m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_m.setAdapter(adapter_m);
        spinner_m.setSelection(standard_m);

        listview = (ListView) findViewById(R.id.search_list);
        listview.setAdapter(search_adapter);



        //기간지정으로 검색 - 부터에 대한 정의
        Calendar fbcalendar = Calendar.getInstance();
        fbcalendar.get(Calendar.YEAR);
        img_from = (ImageView) findViewById(R.id.search_period_by_length_img_from);
        final long img_from_id = img_from.getId();
        img_to = (ImageView) findViewById(R.id.search_period_by_length_img_to);
        final long img_to_id = img_to.getId();
        lbl_from = (TextView) findViewById(R.id.search_period_by_length_lbl_from);
        final long lbl_from_id = lbl_from.getId();
        lbl_to = (TextView) findViewById(R.id.search_period_by_length_lbl_to);
        final long lbl_to_id = lbl_to.getId();


        DecimalFormat fbdf = new DecimalFormat("00");

        last_year_f = fbcalendar.get(Calendar.YEAR);
        last_month_f = fbcalendar.get(Calendar.MONTH) + 1;
        last_day_f = fbcalendar.get(Calendar.DAY_OF_MONTH);

        last_year_t = last_year_f;
        last_month_t = last_month_f;
        last_day_t = last_day_f;

        lbl_from.setText(last_year_f + " . " + fbdf.format(last_month_f) + " . " + fbdf.format(last_day_f));
        lbl_to.setText(lbl_from.getText().toString());

        View.OnClickListener bylength_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search.this, search_datepicker.class);

                if(v.getId() == img_from_id || v.getId() == lbl_from_id)
                {
                    intent.putExtra("FromTo", 1);
                    intent.putExtra("Year", last_year_f);
                    intent.putExtra("Month", last_month_f);
                    intent.putExtra("Day", last_day_f);
                    intent.putExtra("CompYear", last_year_t);
                    intent.putExtra("CompMonth", last_month_t);
                    intent.putExtra("CompDay", last_day_t);
                }
                else if(v.getId() == img_to_id || v.getId() == lbl_to_id)
                {
                    intent.putExtra("FromTo", 2);
                    intent.putExtra("Year", last_year_t);
                    intent.putExtra("Month", last_month_t);
                    intent.putExtra("Day", last_day_t);
                    intent.putExtra("CompYear", last_year_f);
                    intent.putExtra("CompMonth", last_month_f);
                    intent.putExtra("CompDay", last_day_f);

                }

                startActivityForResult(intent, RESULT_FBLENGTH);

            }
        };

        lbl_from.setOnClickListener(bylength_listener);
        lbl_to.setOnClickListener(bylength_listener);
        img_from.setOnClickListener(bylength_listener);
        img_to.setOnClickListener(bylength_listener);




        spinner_from_y = (Spinner) findViewById(R.id.search_spin_from_y);
        spinner_from_m = (Spinner) findViewById(R.id.search_spin_from_m);
        spinner_from_d = (Spinner) findViewById(R.id.search_spin_from_d);
        spinner_to_y = (Spinner) findViewById(R.id.search_spin_to_y);
        spinner_to_m = (Spinner) findViewById(R.id.search_spin_to_m);
        spinner_to_d = (Spinner) findViewById(R.id.search_spin_to_d);

        final MyYMDSetting in_ymdsetting_from = new MyYMDSetting();
        in_ymdsetting_from.setInit(search.this, spinner_from_y, spinner_from_m, spinner_from_d);
        last_year_f = Integer.parseInt(spinner_from_y.getSelectedItem().toString());
        last_month_f = Integer.parseInt(spinner_from_m.getSelectedItem().toString());
        last_day_f = Integer.parseInt(spinner_from_d.getSelectedItem().toString());

        Spinner.OnItemSelectedListener spin_from_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int position, long id )
            {
                int year_f = Integer.parseInt(spinner_from_y.getSelectedItem().toString());
                int month_f = Integer.parseInt(spinner_from_m.getSelectedItem().toString());
                int day_f = Integer.parseInt(spinner_from_d.getSelectedItem().toString());
                int year_t = Integer.parseInt(spinner_to_y.getSelectedItem().toString());
                int month_t = Integer.parseInt(spinner_to_m.getSelectedItem().toString());
                int day_t = Integer.parseInt(spinner_to_d.getSelectedItem().toString());
                if(year_f > year_t)
                {
                    Toast.makeText(search.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_from_y.setSelection(2020 - last_year_f);
                    return;
                }
                if(year_f == year_t && month_f > month_t)
                {
                    Toast.makeText(search.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_from_m.setSelection(last_month_f - 1);
                    return;
                }
                if(year_f == year_t && month_f == month_t && day_f > day_t)
                {
                    Toast.makeText(search.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_from_d.setSelection(last_day_f - 1);
                    return;
                }

                in_ymdsetting_from.setListener(search.this, spinner_from_y, spinner_from_m, spinner_from_d);
                last_year_f = year_f;
                last_month_f = month_f;
                last_day_f = day_f;


            }
            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };

        spinner_from_y.setOnItemSelectedListener(spin_from_listener);
        spinner_from_m.setOnItemSelectedListener(spin_from_listener);

        //까지에 대한 정의
        final MyYMDSetting in_ymdsetting_to = new MyYMDSetting();
        in_ymdsetting_to.setInit(search.this, spinner_to_y, spinner_to_m, spinner_to_d);
        last_year_t = Integer.parseInt(spinner_to_y.getSelectedItem().toString());
        last_month_t = Integer.parseInt(spinner_to_m.getSelectedItem().toString());
        last_day_t = Integer.parseInt(spinner_to_d.getSelectedItem().toString());


        Spinner.OnItemSelectedListener spin_to_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int position, long id )
            {
                int year_f = Integer.parseInt(spinner_from_y.getSelectedItem().toString());
                int month_f = Integer.parseInt(spinner_from_m.getSelectedItem().toString());
                int day_f = Integer.parseInt(spinner_from_d.getSelectedItem().toString());
                int year_t = Integer.parseInt(spinner_to_y.getSelectedItem().toString());
                int month_t = Integer.parseInt(spinner_to_m.getSelectedItem().toString());
                int day_t = Integer.parseInt(spinner_to_d.getSelectedItem().toString());
                if(year_f > year_t)
                {
                    Toast.makeText(search.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_to_y.setSelection(2020 - last_year_t);
                    return;
                }
                if(year_f == year_t && month_f > month_t)
                {
                    Toast.makeText(search.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_to_m.setSelection(last_month_t - 1);
                    return;
                }
                if(year_f == year_t && month_f == month_t && day_f > day_t)
                {
                    Toast.makeText(search.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_to_d.setSelection(last_day_t - 1);
                    return;
                }

                in_ymdsetting_to.setListener(search.this, spinner_to_y, spinner_to_m, spinner_to_d);
                last_year_t = year_t;
                last_month_t = month_t;
                last_day_t = day_t;
            }
            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };
        spinner_to_y.setOnItemSelectedListener(spin_to_listener);
        spinner_to_m.setOnItemSelectedListener(spin_to_listener);


        //일자에 대한 선후관계 처리 정의
        Spinner.OnItemSelectedListener spin_day_listener_f = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int position, long id )
            {
                if(ignore_one_listen_f < 1)
                {
                    ignore_one_listen_f ++;
                    return;
                }
                int year_f = Integer.parseInt(spinner_from_y.getSelectedItem().toString());
                int month_f = Integer.parseInt(spinner_from_m.getSelectedItem().toString());
                int day_f = Integer.parseInt(spinner_from_d.getSelectedItem().toString());
                int year_t = Integer.parseInt(spinner_to_y.getSelectedItem().toString());
                int month_t = Integer.parseInt(spinner_to_m.getSelectedItem().toString());
                int day_t = Integer.parseInt(spinner_to_d.getSelectedItem().toString());
                if(year_f > year_t)
                {
                    Toast.makeText(search.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_from_y.setSelection(2020 - last_year_f);
                    return;
                }
                if(year_f == year_t && month_f > month_t)
                {
                    Toast.makeText(search.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_from_m.setSelection(last_month_f - 1);
                    return;
                }
                if(year_f == year_t && month_f == month_t && day_f > day_t)
                {
                    Toast.makeText(search.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_from_d.setSelection(last_day_f - 1);
                    return;
                }
                last_year_f = year_f;
                last_month_f = month_f;
                last_day_f = day_f;
            }
            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };


        Spinner.OnItemSelectedListener spin_day_listener_t = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int position, long id )
            {
                if(ignore_one_listen_t < 1)
                {
                    ignore_one_listen_t ++;
                    return;
                }
                int year_f = Integer.parseInt(spinner_from_y.getSelectedItem().toString());
                int month_f = Integer.parseInt(spinner_from_m.getSelectedItem().toString());
                int day_f = Integer.parseInt(spinner_from_d.getSelectedItem().toString());
                int year_t = Integer.parseInt(spinner_to_y.getSelectedItem().toString());
                int month_t = Integer.parseInt(spinner_to_m.getSelectedItem().toString());
                int day_t = Integer.parseInt(spinner_to_d.getSelectedItem().toString());
                if(year_f > year_t)
                {
                    Toast.makeText(search.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_to_y.setSelection(2020 - last_year_t);
                    return;
                }
                if(year_f == year_t && month_f > month_t)
                {
                    Toast.makeText(search.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_to_m.setSelection(last_month_t - 1);
                    return;
                }
                if(year_f == year_t && month_f == month_t && day_f > day_t)
                {
                    Toast.makeText(search.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                    spinner_to_d.setSelection(last_day_t - 1);
                    return;
                }
                last_year_t = year_t;
                last_month_t = month_t;
                last_day_t = day_t;
            }
            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };
        spinner_from_d.setOnItemSelectedListener(spin_day_listener_f);
        spinner_to_d.setOnItemSelectedListener(spin_day_listener_t);
        //일자에 대한 선후관계 정의 끝



        // 라디오버튼 선택에 따른 동작(기간선택 옵션)
        RadioButton.OnClickListener period_by_ym_listener = new RadioButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                findViewById(R.id.search_period_by_ym).setVisibility(View.VISIBLE);
                findViewById(R.id.search_period_by_length).setVisibility(View.GONE);
            }

        };

        RadioButton.OnClickListener period_by_length_listener = new RadioButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                findViewById(R.id.search_period_by_ym).setVisibility(View.GONE);
                findViewById(R.id.search_period_by_length).setVisibility(View.VISIBLE);
            }
        };


        // 라디오버튼 선택에 따른 동작(카테고리)
        RadioButton.OnClickListener only_inc_listener = new RadioButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(((RadioButton)findViewById(R.id.search_radio_only_income)).isChecked())
                {
                    ((LinearLayout)findViewById(R.id.search_income_category)).setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.search_expenditure_category)).setVisibility(View.GONE);
                    ((LinearLayout)findViewById(R.id.search_expenditure_way)).setVisibility(View.GONE);
                }
                else
                {
                    ((LinearLayout)findViewById(R.id.search_income_category)).setVisibility(View.VISIBLE);
                }
            }
        };

        RadioButton.OnClickListener only_exp_listener = new RadioButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(((RadioButton)findViewById(R.id.search_radio_only_expenditure)).isChecked())
                {
                    ((LinearLayout)findViewById(R.id.search_income_category)).setVisibility(View.GONE);
                    ((LinearLayout)findViewById(R.id.search_expenditure_category)).setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.search_expenditure_way)).setVisibility(View.VISIBLE);
                }
                else
                {
                    ((LinearLayout)findViewById(R.id.search_expenditure_category)).setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.search_expenditure_way)).setVisibility(View.VISIBLE);
                }
            }
        };


        RadioButton.OnClickListener both_listener = new RadioButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(((RadioButton)findViewById(R.id.search_radio_both)).isChecked())
                {
                    ((LinearLayout)findViewById(R.id.search_income_category)).setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.search_expenditure_category)).setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.search_expenditure_way)).setVisibility(View.VISIBLE);
                }
                else
                {
                }
            }
        };


        //선택 라디오버튼 정의 영역 ~ 리스너 연결
        period_by_ym = (RadioButton) findViewById(R.id.search_radio_by_ym);
        period_by_ym.setOnClickListener(period_by_ym_listener);

        period_by_length = (RadioButton) findViewById(R.id.search_radio_by_length);
        period_by_length.setOnClickListener(period_by_length_listener);

        RadioButton only_inc = (RadioButton) findViewById(R.id.search_radio_only_income);
        only_inc.setOnClickListener(only_inc_listener);

        RadioButton only_exp = (RadioButton) findViewById(R.id.search_radio_only_expenditure);
        only_exp.setOnClickListener(only_exp_listener);

        RadioButton both = (RadioButton) findViewById(R.id.search_radio_both);
        both.setOnClickListener(both_listener);






        //수입 카테고리 스피너 정의 영역
        s_spin_inc_1 = (Spinner) findViewById(R.id.search_spin_income_1);
        ArrayAdapter adapter_spin1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, income_category_forsearch_1);
        adapter_spin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_inc_1.setAdapter(adapter_spin1);

        s_spin_inc_2 = (Spinner) findViewById(R.id.search_spin_income_2);
        ArrayAdapter adapter_spin2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, income_category_forsearch_2);
        adapter_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_inc_2.setAdapter(adapter_spin2);

        s_spin_inc_3 = (Spinner) findViewById(R.id.search_spin_income_3);
        ArrayAdapter adapter_spin3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, income_category_forsearch_2);
        adapter_spin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_inc_3.setAdapter(adapter_spin3);


        //지출 카테고리 스피너 정의 영역
        s_spin_exp_1 = (Spinner) findViewById(R.id.search_spin_expenditure_1);
        ArrayAdapter exp_adapter_spin1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_category_forsearch_1);
        exp_adapter_spin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_exp_1.setAdapter(exp_adapter_spin1);

        s_spin_exp_2 = (Spinner) findViewById(R.id.search_spin_expenditure_2);
        ArrayAdapter exp_adapter_spin2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_category_forsearch_2);
        exp_adapter_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_exp_2.setAdapter(exp_adapter_spin2);

        s_spin_exp_3 = (Spinner) findViewById(R.id.search_spin_expenditure_3);
        ArrayAdapter exp_adapter_spin3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_category_forsearch_2);
        exp_adapter_spin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_exp_3.setAdapter(exp_adapter_spin3);

        //지출 수단 스피너 정의 영역

        s_spin_expw_1 = (Spinner) findViewById(R.id.search_spin_way_1);
        ArrayAdapter expw_adapter_spin1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_way_forsearch_1);
        expw_adapter_spin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_expw_1.setAdapter(expw_adapter_spin1);

        s_spin_expw_2 = (Spinner) findViewById(R.id.search_spin_way_2);
        ArrayAdapter expw_adapter_spin2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_way_forsearch_2);
        expw_adapter_spin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_expw_2.setAdapter(expw_adapter_spin2);

        s_spin_expw_3 = (Spinner) findViewById(R.id.search_spin_way_3);
        ArrayAdapter expw_adapter_spin3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_way_forsearch_2);
        expw_adapter_spin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spin_expw_3.setAdapter(expw_adapter_spin3);


        ///////////////////////income category 리스너 정의 영역/////////////////////////////

        Spinner.OnItemSelectedListener inc1_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id ) {
                if (first_inc_1[0]) {

                    ((Spinner) findViewById(R.id.search_spin_income_2)).setVisibility(View.VISIBLE);

                    if(s_spin_inc_1.getSelectedItemId() == 0)
                    {
                        s_spin_inc_2.setVisibility(View.GONE);
                        s_spin_inc_3.setVisibility(View.GONE);
                    }
                }
                first_inc_1[0] = true;
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };



        Spinner.OnItemSelectedListener inc2_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id )
            {
                if(first_inc_2[0]) {
                    ((Spinner) findViewById(R.id.search_spin_income_3)).setVisibility(View.VISIBLE);
                }
                first_inc_2[0] = true;
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {
            }
        };



        ///////////////////////expenditure category 리스너 정의 영역/////////////////////////////
        Spinner.OnItemSelectedListener exp1_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id )
            {
                if(first_exp_1[0]) {
                    ((Spinner) findViewById(R.id.search_spin_expenditure_2)).setVisibility(View.VISIBLE);

                    if(s_spin_exp_1.getSelectedItemId() == 0)
                    {
                        s_spin_exp_2.setVisibility(View.GONE);
                        s_spin_exp_3.setVisibility(View.GONE);
                    }
                }
                first_exp_1[0] = true;
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {
            }
        };

        Spinner.OnItemSelectedListener exp2_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id )
            {
                if(first_exp_2[0]) {
                    ((Spinner) findViewById(R.id.search_spin_expenditure_3)).setVisibility(View.VISIBLE);
                }
                first_exp_2[0] = true;
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {
            }
        };




        ///////////////////////expenditure way 리스너 정의 영역/////////////////////////////

        Spinner.OnItemSelectedListener expw1_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id )
            {
                if(first_expw_1[0]) {
                    ((Spinner) findViewById(R.id.search_spin_way_2)).setVisibility(View.VISIBLE);

                    if(s_spin_expw_1.getSelectedItemId() == 0)
                    {
                        s_spin_expw_2.setVisibility(View.GONE);
                        s_spin_expw_3.setVisibility(View.GONE);

                    }
                }
                first_expw_1[0] = true;
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {
            }
        };

        Spinner.OnItemSelectedListener expw2_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id )
            {
                if(first_expw_2[0]) {
                    ((Spinner) findViewById(R.id.search_spin_way_3)).setVisibility(View.VISIBLE);
                }
                first_expw_2[0] = true;
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {
            }
        };

        s_spin_inc_1 = (Spinner) findViewById(R.id.search_spin_income_1);
        s_spin_inc_2 = (Spinner) findViewById(R.id.search_spin_income_2);
        s_spin_inc_3 = (Spinner) findViewById(R.id.search_spin_income_3);


        s_spin_exp_1 = (Spinner) findViewById(R.id.search_spin_expenditure_1);
        s_spin_exp_2 = (Spinner) findViewById(R.id.search_spin_expenditure_2);
        s_spin_exp_3 = (Spinner) findViewById(R.id.search_spin_expenditure_3);


        s_spin_expw_1 = (Spinner) findViewById(R.id.search_spin_way_1);
        s_spin_expw_2 = (Spinner) findViewById(R.id.search_spin_way_2);
        s_spin_expw_3 = (Spinner) findViewById(R.id.search_spin_way_3);


        s_spin_inc_1.setOnItemSelectedListener(inc1_listener);
        s_spin_inc_2.setOnItemSelectedListener(inc2_listener);
        s_spin_exp_1.setOnItemSelectedListener(exp1_listener);
        s_spin_exp_2.setOnItemSelectedListener(exp2_listener);
        s_spin_expw_1.setOnItemSelectedListener(expw1_listener);
        s_spin_expw_2.setOnItemSelectedListener(expw2_listener);


        //조회 버튼 클릭
        Button.OnClickListener lets_search_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(search.this, nowsearching.class);
                startActivity(intent);
            }
        };
        Button lets_search = (Button) findViewById(R.id.search_inquiry);
        lets_search.setOnClickListener(lets_search_listener);

        //통계-상세보기에서 넘어온 데이터 처리
        Intent getStatIntent = getIntent();
        DecimalFormat statdf = new DecimalFormat("00");
        switch(getStatIntent.getIntExtra("statType", 0))
        {
            case 11: //일간 수입
                int day_y_inc = getStatIntent.getIntExtra("statDay_y", 0);
                int day_m_inc = getStatIntent.getIntExtra("statDay_m", 0);
                int day_d_inc = getStatIntent.getIntExtra("statDay_d", 0);
                period_by_length.setChecked(true);
                spinner_from_y.setSelection(2020 - day_y_inc);
                spinner_from_m.setSelection(day_m_inc - 1);
                spinner_from_d.setSelection(day_d_inc - 1);
                spinner_to_y.setSelection(2020 - day_y_inc);
                spinner_to_m.setSelection(day_m_inc - 1);
                spinner_to_d.setSelection(day_d_inc - 1);
                last_year_f = day_y_inc;
                last_month_f = day_m_inc;
                last_day_f = day_d_inc;
                last_year_t = day_y_inc;
                last_month_t = day_m_inc;
                last_day_t = day_d_inc;
                lbl_from.setText(last_year_f + " . " + statdf.format(last_month_f) + " . " + statdf.format(last_day_f));
                lbl_to.setText(last_year_t + " . " + statdf.format(last_month_t) + " . " + statdf.format(last_day_t));
                only_inc.setChecked(true);
                if(getStatIntent.getBooleanExtra("stat_isValueSelected", false))
                {
                    String category = getStatIntent.getStringExtra("statCategory");
                    s_spin_inc_1.setSelection(income_category_forsearch_1.indexOf(category));
                }
                lets_search.performClick();
                menuFold();
                break;

            case 12: //일간 지출
                int day_y = getStatIntent.getIntExtra("statDay_y", 0);
                int day_m = getStatIntent.getIntExtra("statDay_m", 0);
                int day_d = getStatIntent.getIntExtra("statDay_d", 0);
                period_by_length.setChecked(true);
                spinner_from_y.setSelection(2020 - day_y);
                spinner_from_m.setSelection(day_m - 1);
                spinner_from_d.setSelection(day_d - 1);
                spinner_to_y.setSelection(2020 - day_y);
                spinner_to_m.setSelection(day_m - 1);
                spinner_to_d.setSelection(day_d - 1);
                last_year_f = day_y;
                last_month_f = day_m;
                last_day_f = day_d;
                last_year_t = day_y;
                last_month_t = day_m;
                last_day_t = day_d;
                lbl_from.setText(last_year_f + " . " + statdf.format(last_month_f) + " . " + statdf.format(last_day_f));
                lbl_to.setText(last_year_t + " . " + statdf.format(last_month_t) + " . " + statdf.format(last_day_t));
                only_exp.setChecked(true);
                if(getStatIntent.getBooleanExtra("stat_isValueSelected", false))
                {
                    String category = getStatIntent.getStringExtra("statCategory");
                    s_spin_exp_1.setSelection(expenditure_category_forsearch_1.indexOf(category));
                }
                lets_search.performClick();
                menuFold();
                break;

            case 21: //주간 수입
                int week_y_inc_end = getStatIntent.getIntExtra("statWeek_y_end", 0);
                int week_m_inc_end = getStatIntent.getIntExtra("statWeek_m_end", 0);
                int week_d_inc_end = getStatIntent.getIntExtra("statWeek_d_end", 0);
                int week_y_inc_start = getStatIntent.getIntExtra("statWeek_y_start", 0);
                int week_m_inc_start = getStatIntent.getIntExtra("statWeek_m_start", 0);
                int week_d_inc_start = getStatIntent.getIntExtra("statWeek_d_start", 0);
                period_by_length.setChecked(true);
                spinner_from_y.setSelection(2020 - week_y_inc_start);
                spinner_from_m.setSelection(week_m_inc_start - 1);
                spinner_from_d.setSelection(week_d_inc_start - 1);
                spinner_to_y.setSelection(2020 - week_y_inc_end);
                spinner_to_m.setSelection(week_m_inc_end - 1);
                spinner_to_d.setSelection(week_d_inc_end - 1);
                last_year_f = week_y_inc_start;
                last_month_f = week_m_inc_start;
                last_day_f = week_d_inc_start;
                last_year_t = week_y_inc_end;
                last_month_t = week_m_inc_end;
                last_day_t = week_d_inc_end;
                lbl_from.setText(last_year_f + " . " + statdf.format(last_month_f) + " . " + statdf.format(last_day_f));
                lbl_to.setText(last_year_t + " . " + statdf.format(last_month_t) + " . " + statdf.format(last_day_t));
                only_inc.setChecked(true);
                if(getStatIntent.getBooleanExtra("stat_isValueSelected", false))
                {
                    String category = getStatIntent.getStringExtra("statCategory");
                    s_spin_inc_1.setSelection(income_category_forsearch_1.indexOf(category));
                }
                lets_search.performClick();
                menuFold();

                break;
            case 22: //주간 지출
                int week_y_exp_end = getStatIntent.getIntExtra("statWeek_y_end", 0);
                int week_m_exp_end = getStatIntent.getIntExtra("statWeek_m_end", 0);
                int week_d_exp_end = getStatIntent.getIntExtra("statWeek_d_end", 0);
                int week_y_exp_start = getStatIntent.getIntExtra("statWeek_y_start", 0);
                int week_m_exp_start = getStatIntent.getIntExtra("statWeek_m_start", 0);
                int week_d_exp_start = getStatIntent.getIntExtra("statWeek_d_start", 0);
                period_by_length.setChecked(true);
                spinner_from_y.setSelection(2020 - week_y_exp_start);
                spinner_from_m.setSelection(week_m_exp_start - 1);
                spinner_from_d.setSelection(week_d_exp_start - 1);
                spinner_to_y.setSelection(2020 - week_y_exp_end);
                spinner_to_m.setSelection(week_m_exp_end - 1);
                spinner_to_d.setSelection(week_d_exp_end - 1);
                last_year_f = week_y_exp_start;
                last_month_f = week_m_exp_start;
                last_day_f = week_d_exp_start;
                last_year_t = week_y_exp_end;
                last_month_t = week_m_exp_end;
                last_day_t = week_d_exp_end;
                lbl_from.setText(last_year_f + " . " + statdf.format(last_month_f) + " . " + statdf.format(last_day_f));
                lbl_to.setText(last_year_t + " . " + statdf.format(last_month_t) + " . " + statdf.format(last_day_t));
                only_exp.setChecked(true);
                if(getStatIntent.getBooleanExtra("stat_isValueSelected", false))
                {
                    String category = getStatIntent.getStringExtra("statCategory");
                    s_spin_exp_1.setSelection(expenditure_category_forsearch_1.indexOf(category));
                }
                lets_search.performClick();
                menuFold();

                break;
            case 31: //월간 수입
                int month_y_inc = getStatIntent.getIntExtra("statMonth_y", 0);
                int month_m_inc = getStatIntent.getIntExtra("statMonth_m", 0);
                period_by_ym.setChecked(true);
                spinner_y.setSelection(2020 - month_y_inc);
                spinner_m.setSelection(month_m_inc - 1);
                only_inc.setChecked(true);
                if(getStatIntent.getBooleanExtra("stat_isValueSelected", false))
                {
                    String category = getStatIntent.getStringExtra("statCategory");
                    s_spin_inc_1.setSelection(income_category_forsearch_1.indexOf(category));
                }
                lets_search.performClick();
                menuFold();
                break;
            case 32: //월간 지출
                int month_y_exp = getStatIntent.getIntExtra("statMonth_y", 0);
                int month_m_exp = getStatIntent.getIntExtra("statMonth_m", 0);
                period_by_ym.setChecked(true);
                spinner_y.setSelection(2020 - month_y_exp);
                spinner_m.setSelection(month_m_exp - 1);
                only_exp.setChecked(true);
                if(getStatIntent.getBooleanExtra("stat_isValueSelected", false))
                {
                    String category = getStatIntent.getStringExtra("statCategory");
                    s_spin_exp_1.setSelection(expenditure_category_forsearch_1.indexOf(category));
                }
                lets_search.performClick();
                menuFold();
                break;
            default:
                break;
        }


        //펴기와 접기
        Button.OnClickListener fold_and_spread_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!isfolded) {
                    menuFold();
                }
                else
                {
                    menuSpread();
                }
            }
        };

        Button fold_and_spread = (Button) findViewById(R.id.search_btn_fold_and_spread);
        fold_and_spread.setOnClickListener(fold_and_spread_listener);



        ////////////////// 상세조회 및 수정 기능(리스트뷰 클릭 이벤트) ////////////////////////
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {

                Intent expend_intent = new Intent(search.this, search_details_ex.class);
                Intent income_intent = new Intent(search.this, search_details_in.class);

                if(((listview_search_list)search_adapter_result_2.getItem(position)).getinex() == 1) //수입
                {
                    income_intent.putExtra("in_pos", ((listview_search_list)search_adapter_result_2.getItem(position)).getabspos());
                    income_intent.putExtra("in_year", ((listview_search_list)search_adapter_result_2.getItem(position)).getyear());
                    income_intent.putExtra("in_month", ((listview_search_list)search_adapter_result_2.getItem(position)).getmonth());
                    income_intent.putExtra("in_day", ((listview_search_list)search_adapter_result_2.getItem(position)).getday());
                    income_intent.putExtra("in_memo", ((listview_search_list)search_adapter_result_2.getItem(position)).getmemo());
                    income_intent.putExtra("in_where", ((listview_search_list)search_adapter_result_2.getItem(position)).getwhere());
                    income_intent.putExtra("in_amount", ((listview_search_list)search_adapter_result_2.getItem(position)).getamount());
                    income_intent.putExtra("in_amount_int", ((listview_search_list)search_adapter_result_2.getItem(position)).getamount_int());

                    startActivity(income_intent);
                }
                else if(((listview_search_list)search_adapter_result_2.getItem(position)).getinex() == 2) //지출
                {
                    expend_intent.putExtra("ex_pos", ((listview_search_list)search_adapter_result_2.getItem(position)).getabspos());
                    expend_intent.putExtra("ex_year", ((listview_search_list)search_adapter_result_2.getItem(position)).getyear());
                    expend_intent.putExtra("ex_month", ((listview_search_list)search_adapter_result_2.getItem(position)).getmonth());
                    expend_intent.putExtra("ex_day", ((listview_search_list)search_adapter_result_2.getItem(position)).getday());
                    expend_intent.putExtra("ex_way", ((listview_search_list)search_adapter_result_2.getItem(position)).getway());
                    expend_intent.putExtra("ex_memo", ((listview_search_list)search_adapter_result_2.getItem(position)).getmemo());
                    expend_intent.putExtra("ex_satis", ((listview_search_list)search_adapter_result_2.getItem(position)).getemonum());
                    expend_intent.putExtra("ex_where", ((listview_search_list)search_adapter_result_2.getItem(position)).getwhere());
                    expend_intent.putExtra("ex_includeExp", ((listview_search_list)search_adapter_result_2.getItem(position)).getincludeStat());
                    expend_intent.putExtra("ex_amount", ((listview_search_list)search_adapter_result_2.getItem(position)).getamount());
                    expend_intent.putExtra("ex_amount_int", ((listview_search_list)search_adapter_result_2.getItem(position)).getamount_int());

                    startActivity(expend_intent);

                }
            }
        });







        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent returnToStat = new Intent(search.this, stat_v2.class);
                returnToStat.putExtra("statRefresh", true);
                setResult(RESULT_OK, returnToStat);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                finish();
            }
        };
        ImageButton back = (ImageButton) findViewById(R.id.search_img_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();

        final ImageView alphablack = (ImageView) findViewById(R.id.search_img_alphablack);
        final ProgressBar search_pgbar = (ProgressBar) findViewById(R.id.search_pgbar_nowsearching);
        final TextView search_tv = (TextView) findViewById(R.id.search_lbl_nowsearching);



        if (state != null) { // 리스트뷰 상태가 있는 경우
            listview.onRestoreInstanceState(state); // 리스트뷰 스크롤 위치 복구
        }

        if(isFTChanged)
        {
            isFTChanged = false;
            return;
        }

        alphablack.setVisibility(View.VISIBLE);
        search_pgbar.setVisibility(View.VISIBLE);
        search_tv.setVisibility(View.VISIBLE);

        ///===============================================1차 필터 시작 (기간)=======================================================///
        ///1. 연도/월별
        RadioButton s_rd_ym = (RadioButton) findViewById(R.id.search_radio_by_ym);
        Spinner s_spinner_y = (Spinner) findViewById(R.id.search_spin_y);
        Spinner s_spinner_m = (Spinner) findViewById(R.id.search_spin_m);
        int s_year = Integer.parseInt(s_spinner_y.getSelectedItem().toString());
        int s_month = Integer.parseInt(s_spinner_m.getSelectedItem().toString());
        if (s_rd_ym.isChecked()) {
            boolean first_detected = false;
            search_adapter_result.getListItem().clear();
            for (int i = 0; i < search_adapter.getListItem().size(); i++) {
                if (search_adapter.getListItem().get(i).getyear() == s_year && search_adapter.getListItem().get(i).getmonth() == s_month) {
                    first_detected = true;
                    search_adapter.getListItem().get(i).setabspos(i);
                    search_adapter_result.addItem(search_adapter.getListItem().get(i));
                } else if (first_detected) {
                    break;
                }
            }
            listview.setAdapter(search_adapter_result);

        }

        ///2. 기간지정
        Calendar fromCal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();
        fromCal.set(last_year_f, last_month_f - 1, last_day_f);
        toCal.set(last_year_t, last_month_t - 1, last_day_t);


        RadioButton s_rd_length = (RadioButton) findViewById(R.id.search_radio_by_length);
              /*  Spinner s_spinner_y_f = (Spinner) findViewById(R.id.search_spin_from_y);
                Spinner s_spinner_m_f = (Spinner) findViewById(R.id.search_spin_from_m);
                Spinner s_spinner_d_f = (Spinner) findViewById(R.id.search_spin_from_d);
                Spinner s_spinner_y_t = (Spinner) findViewById(R.id.search_spin_to_y);
                Spinner s_spinner_m_t = (Spinner) findViewById(R.id.search_spin_to_m);
                Spinner s_spinner_d_t = (Spinner) findViewById(R.id.search_spin_to_d);

                String s_year_f = s_spinner_y_f.getSelectedItem().toString();
                int s_month_f = Integer.parseInt(s_spinner_m_f.getSelectedItem().toString());
                int s_day_f = Integer.parseInt(s_spinner_d_f.getSelectedItem().toString());
                String s_month_f_s;
                String s_day_f_s;
                String s_f_ymd;
                int s_f_ymd_int;
                if(s_month_f < 10) {
                    s_month_f_s = "0" + String.valueOf(s_month_f);
                } else { s_month_f_s = String.valueOf(s_month_f); }
                if(s_day_f < 10) {
                    s_day_f_s = "0" + String.valueOf(s_day_f);
                } else { s_day_f_s = String.valueOf(s_day_f); }
                s_f_ymd = s_year_f + s_month_f_s + s_day_f_s;
                s_f_ymd_int = Integer.parseInt(s_f_ymd);
                System.out.println("시작일: " + s_f_ymd_int);

                String s_year_t = s_spinner_y_t.getSelectedItem().toString();
                int s_month_t = Integer.parseInt(s_spinner_m_t.getSelectedItem().toString());
                int s_day_t = Integer.parseInt(s_spinner_d_t.getSelectedItem().toString());
                String s_month_t_s;
                String s_day_t_s;
                String s_t_ymd;
                int s_t_ymd_int;
                if(s_month_t < 10) {
                    s_month_t_s = "0" + String.valueOf(s_month_t);
                } else { s_month_t_s = String.valueOf(s_month_t); }
                if(s_day_t < 10) {
                    s_day_t_s = "0" + String.valueOf(s_day_t);
                } else { s_day_t_s = String.valueOf(s_day_t); }
                s_t_ymd = s_year_t + s_month_t_s + s_day_t_s;
                s_t_ymd_int = Integer.parseInt(s_t_ymd);
                System.out.println("종료일: " + s_t_ymd_int);*/


        if (s_rd_length.isChecked()) {
            boolean first_detected = false;
            boolean second_detected = false;
            search_adapter_result.getListItem().clear();



            for (int i = 0; i < search_adapter.getListItem().size(); i++) {
                Calendar compCal = Calendar.getInstance();
                int compYear = search_adapter.getListItem().get(i).getyear();
                int compMonth = search_adapter.getListItem().get(i).getmonth();
                int compDay = search_adapter.getListItem().get(i).getday();
                compCal.set(compYear, compMonth - 1, compDay);
                System.out.println("Comp: " + compYear + "." + compMonth + "." + compDay);
                       /* String year = String.valueOf(search_adapter.getListItem().get(i).getyear());
                        int month = search_adapter.getListItem().get(i).getmonth();
                        String month_s;
                        int day = search_adapter.getListItem().get(i).getday();
                        String day_s;
                        String ymd;
                        int ymd_int;
                        if(month < 10) {
                            month_s = "0" + String.valueOf(month);
                        } else { month_s = String.valueOf(month); }
                        if(day < 10) {
                            day_s = "0" + String.valueOf(day);
                        } else { day_s = String.valueOf(day); }
                        ymd = year + month_s + day_s;
                        ymd_int = Integer.parseInt(ymd);
                        System.out.println(ymd_int);*/


                System.out.println("From: " + last_year_f + "." + last_month_f + "." + last_day_f);
                System.out.println("To  : " + last_year_t + "." + last_month_t + "." + last_day_t);
                MyYMDSetting mys = new MyYMDSetting();
                int intFrom = mys.makeYMDInt(last_year_f, last_month_f, last_day_f);
                int intTo = mys.makeYMDInt(last_year_t, last_month_t, last_day_t);
                int intComp = mys.makeYMDInt(compYear, compMonth, compDay);
                if(intFrom > intComp) break;
                else if(intTo < intComp) continue;
                else
                {
                    search_adapter.getListItem().get(i).setabspos(i);
                    search_adapter_result.addItem(search_adapter.getListItem().get(i));
                }

            }

            listview.setAdapter(search_adapter_result);

        }


        ///===============================================1차 필터 끝 (기간)=======================================================///



        ///===============================================2차 필터 시작 (카테고리)=======================================================///

        int s_spin_inc_1_pos = s_spin_inc_1.getSelectedItemPosition();
        int s_spin_inc_2_pos = s_spin_inc_2.getSelectedItemPosition();
        int s_spin_inc_3_pos = s_spin_inc_3.getSelectedItemPosition();
        int s_spin_exp_1_pos = s_spin_exp_1.getSelectedItemPosition();
        int s_spin_exp_2_pos = s_spin_exp_2.getSelectedItemPosition();
        int s_spin_exp_3_pos = s_spin_exp_3.getSelectedItemPosition();
        int s_spin_expw_1_pos = s_spin_expw_1.getSelectedItemPosition();
        int s_spin_expw_2_pos = s_spin_expw_2.getSelectedItemPosition();
        int s_spin_expw_3_pos = s_spin_expw_3.getSelectedItemPosition();


        // 1. 수입만
        RadioButton s_rd_onlyinc = (RadioButton) findViewById(R.id.search_radio_only_income);


        if(s_rd_onlyinc.isChecked()) {
            search_adapter_result_2.getListItem().clear();
            for (int i = 0; i < search_adapter_result.getListItem().size(); i++) {
                String where = search_adapter_result.getListItem().get(i).getwhere();
                if (search_adapter_result.getListItem().get(i).getinex() == 1) {
                    if (s_spin_inc_1_pos == 0) {
                        search_adapter.getListItem().get(i).setabspos(i);
                        search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                    } else {
                        if (where.equals(s_spin_inc_1.getSelectedItem().toString()) ||
                                where.equals(s_spin_inc_2.getSelectedItem().toString()) ||
                                where.equals(s_spin_inc_3.getSelectedItem().toString())) {
                            search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                            search_adapter.getListItem().get(i).setabspos(i);
                        }
                    }
                }
            }

            listview.setAdapter(search_adapter_result_2);

        }

        // 2. 지출만
        RadioButton s_rd_onlyexp = (RadioButton) findViewById(R.id.search_radio_only_expenditure);
        if(s_rd_onlyexp.isChecked()) {
            search_adapter_result_2.getListItem().clear();
            for (int i = 0; i < search_adapter_result.getListItem().size(); i++) {
                String where = search_adapter_result.getListItem().get(i).getwhere();
                String way = search_adapter_result.getListItem().get(i).getway();
                if (search_adapter_result.getListItem().get(i).getinex() == 2) { //지출인 것에 대해서
                    if (s_spin_exp_1_pos == 0) {
                        if(s_spin_expw_1_pos == 0) {
                            search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                            search_adapter.getListItem().get(i).setabspos(i);
                        }
                        else
                        {
                            if(way.equals(s_spin_expw_1.getSelectedItem().toString()) ||
                                    way.equals(s_spin_expw_2.getSelectedItem().toString()) ||
                                    way.equals(s_spin_expw_3.getSelectedItem().toString())) {
                                search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                                search_adapter.getListItem().get(i).setabspos(i);
                            }
                        }
                    } else {

                        if(s_spin_expw_1_pos == 0)
                        {
                            if (
                                    (where.equals(s_spin_exp_1.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_2.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_3.getSelectedItem().toString()))
                                    ) {
                                search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                                search_adapter.getListItem().get(i).setabspos(i);
                            }
                        }
                        else {
                            if (
                                    (where.equals(s_spin_exp_1.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_2.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_3.getSelectedItem().toString())) &&
                                            (way.equals(s_spin_expw_1.getSelectedItem().toString()) ||
                                                    way.equals(s_spin_expw_2.getSelectedItem().toString()) ||
                                                    way.equals(s_spin_expw_3.getSelectedItem().toString()))
                                    ) {
                                search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                                search_adapter.getListItem().get(i).setabspos(i);
                            }
                        }
                    }
                }
            }

            listview.setAdapter(search_adapter_result_2);
        }

        // 3. 둘다
        RadioButton s_rd_both = (RadioButton) findViewById(R.id.search_radio_both);
        if(s_rd_both.isChecked())
        {
            search_adapter_result_2.getListItem().clear();
            for (int i = 0; i < search_adapter_result.getListItem().size(); i++) {
                String where = search_adapter_result.getListItem().get(i).getwhere();
                String way = search_adapter_result.getListItem().get(i).getway();

                if (search_adapter_result.getListItem().get(i).getinex() == 1) {
                    if (s_spin_inc_1_pos == 0) {
                        search_adapter.getListItem().get(i).setabspos(i);
                        search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                    } else {
                        if (where.equals(s_spin_inc_1.getSelectedItem().toString()) ||
                                where.equals(s_spin_inc_2.getSelectedItem().toString()) ||
                                where.equals(s_spin_inc_3.getSelectedItem().toString())) {
                            search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                            search_adapter.getListItem().get(i).setabspos(i);
                        }
                    }
                }


                if (search_adapter_result.getListItem().get(i).getinex() == 2) { //지출인 것에 대해서
                    if (s_spin_exp_1_pos == 0) {
                        if(s_spin_expw_1_pos == 0) {
                            search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                            search_adapter.getListItem().get(i).setabspos(i);
                        }
                        else
                        {
                            if(way.equals(s_spin_expw_1.getSelectedItem().toString()) ||
                                    way.equals(s_spin_expw_2.getSelectedItem().toString()) ||
                                    way.equals(s_spin_expw_3.getSelectedItem().toString())) {
                                search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                                search_adapter.getListItem().get(i).setabspos(i);
                            }
                        }
                    } else {

                        if(s_spin_expw_1_pos == 0)
                        {
                            if(
                                    (where.equals(s_spin_exp_1.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_2.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_3.getSelectedItem().toString()))
                                    ) {
                                search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                                search_adapter.getListItem().get(i).setabspos(i);
                            }
                        }
                        else {
                            if (
                                    (where.equals(s_spin_exp_1.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_2.getSelectedItem().toString()) ||
                                            where.equals(s_spin_exp_3.getSelectedItem().toString())) &&
                                            (way.equals(s_spin_expw_1.getSelectedItem().toString()) ||
                                                    way.equals(s_spin_expw_2.getSelectedItem().toString()) ||
                                                    way.equals(s_spin_expw_3.getSelectedItem().toString()))
                                    ) {
                                search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                                search_adapter.getListItem().get(i).setabspos(i);
                            }
                        }
                    }
                }
                /*if (search_adapter_result.getListItem().get(i).getinex() == 2) {
                    if (s_spin_exp_1_pos == 0) {
                        search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                        search_adapter.getListItem().get(i).setabspos(i);
                    } else {
                        if (where.equals(s_spin_exp_1.getSelectedItem().toString()) ||
                                where.equals(s_spin_exp_2.getSelectedItem().toString()) ||
                                where.equals(s_spin_exp_3.getSelectedItem().toString())) {
                            search_adapter_result_2.addItem(search_adapter_result.getListItem().get(i));
                            search_adapter.getListItem().get(i).setabspos(i);
                        }
                    }
                }*/
            }


            listview.setAdapter(search_adapter_result_2);

        }
        ///===============================================2차 필터 끝 (카테고리)=======================================================///


        alphablack.setVisibility(View.GONE);
        search_pgbar.setVisibility(View.GONE);
        search_tv.setVisibility(View.GONE);




        ////////////////// 임시 리스트뷰 이용을 위한 addItem 메서드 이용 ////////////////////////
        /// addItem(int inex, int year, int month, int day, int waynum, int wherenum, int emonum, int amount,  String memo)
       /* search_adapter.addItem(2,2017,4,25, 1,8,5, 10000, "우양재단 기부");
        search_adapter.addItem(2,2017,4,24, 1,3,3, 14240, "휴대폰 요금");
        search_adapter.addItem(1,2017,4,23,-1,3,0,   695, "널 만나면 말없이 있어도 또 하나의 나처럼 편안했던 거야~ 널 만나면 순수한 네 모습에 철없는 아이처럼 잊었던 거야~ 내겐 너무 소중한 너~ 내겐 너무 행복한 너~");
        search_adapter.addItem(2,2017,4,23, 1,1,3,  7000, "학식-점심(비빔밥), 저녁(떡만두국)");
        search_adapter.addItem(2,2017,4,22, 1,1,4,  7000, "학식-점심(함박스테이크), 저녁(부대찌개)");
        search_adapter.addItem(2,2017,4,22, 1,9,3,  4460, "폰 착불 택배비");
        search_adapter.addItem(2,2017,4,22, 1,0,4,  2800, "서흥하이퍼마켓 식료품 구입");
        search_adapter.addItem(2,2017,4,21, 0,7,5,  2000, "코인노래방");
        search_adapter.addItem(2,2017,4,21, 1,1,3,  6500, "학식-점심(소시지오므라이스), 저녁(냉면)");
        search_adapter.addItem(2,2017,4,21, 1,1,3,  2800, "식권구입");
        search_adapter.addItem(2,2017,4,20, 0,1,4,  5000, "저녁(키친-제육비빔밥)");
        search_adapter.addItem(2,2017,4,20, 1,5,3,   700, "출금수수료");
        search_adapter.addItem(2,2017,4,20, 1,1,3,  2800, "점심(함박스테이크)");
        search_adapter.addItem(2,2017,4,20, 0,7,5,  2000, "코인노래방");
        search_adapter.addItem(2,2017,4,19, 0,7,5,  1000, "코인노래방");
        search_adapter.addItem(2,2017,4,19, 1,1,4,  6300, "학식-점심(폭찹돈까스), 저녁(칼국수만두)");*/
    }

    @Override
    public void onPause()
    {
        super.onPause();
        //overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        state = listview.onSaveInstanceState();
    }


    @Override
    public void onStop()
    {
        super.onStop();

    }

    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_FBLENGTH) //리퀘스트 코드를 성공적으로 받았다
        {


            if(resultCode == Activity.RESULT_OK)
            {
                DecimalFormat df = new DecimalFormat("00");
                if(data.getIntExtra("FromTo", 0) == 1)
                {
                    Calendar cal_from = Calendar.getInstance();
                    Calendar cal_to = Calendar.getInstance();
                    cal_from.set(data.getIntExtra("Year", 0), data.getIntExtra("Month", 0), data.getIntExtra("Day", 0));
                    cal_to.set(last_year_t, last_month_t - 1, last_day_t);
                    if(cal_from.after(cal_to))
                    {
                        Toast.makeText(search.this, "시작일은 종료일 뒤에 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        last_year_f = data.getIntExtra("Year", 0);
                        last_month_f = data.getIntExtra("Month", 0) + 1;
                        last_day_f = data.getIntExtra("Day", 0);
                        lbl_from.setText(last_year_f + " . " + df.format(last_month_f) + " . " + df.format(last_day_f));
                    }

                }
                else if(data.getIntExtra("FromTo", 0) == 2)
                {
                    Calendar cal_from = Calendar.getInstance();
                    Calendar cal_to = Calendar.getInstance();
                    cal_from.set(last_year_f, last_month_f - 1, last_day_f);
                    cal_to.set(data.getIntExtra("Year", 0), data.getIntExtra("Month", 0), data.getIntExtra("Day", 0));
                    if(cal_to.before(cal_from))
                    {
                        Toast.makeText(search.this, "종료일은 시작일을 앞설 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        last_year_t = data.getIntExtra("Year", 0);
                        last_month_t = data.getIntExtra("Month", 0) + 1;
                        last_day_t = data.getIntExtra("Day", 0);
                        lbl_to.setText(last_year_t + " . " + df.format(last_month_t) + " . " + df.format(last_day_t));
                    }
                }

                isFTChanged = true;
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {
                isFTChanged = true;
            }
        }

    }

    public void menuFold()
    {
        findViewById(R.id.search_period).setVisibility(View.GONE);
        findViewById(R.id.search_period_by_ym).setVisibility(View.GONE);
        findViewById(R.id.search_period_by_length).setVisibility(View.GONE);
        findViewById(R.id.search_first_view).setVisibility(View.GONE);
        findViewById(R.id.search_option).setVisibility(View.GONE);
        findViewById(R.id.search_income_category).setVisibility(View.GONE);
        findViewById(R.id.search_expenditure_category).setVisibility(View.GONE);
        findViewById(R.id.search_expenditure_way).setVisibility(View.GONE);
        findViewById(R.id.search_second_view).setVisibility(View.GONE);
        findViewById(R.id.search_inquiry).setVisibility(View.GONE);
        ((Button) findViewById(R.id.search_btn_fold_and_spread)).setText("▼   펴   기");
        isfolded = true;
    }

    public void menuSpread()
    {
        findViewById(R.id.search_period).setVisibility(View.VISIBLE);
        if(((RadioButton)findViewById(R.id.search_radio_by_ym)).isChecked())
            findViewById(R.id.search_period_by_ym).setVisibility(View.VISIBLE);
        if(((RadioButton)findViewById(R.id.search_radio_by_length)).isChecked())
            findViewById(R.id.search_period_by_length).setVisibility(View.VISIBLE);
        findViewById(R.id.search_first_view).setVisibility(View.VISIBLE);
        findViewById(R.id.search_option).setVisibility(View.VISIBLE);
        if(((RadioButton)findViewById(R.id.search_radio_only_income)).isChecked() || ((RadioButton)findViewById(R.id.search_radio_both)).isChecked()) {
            findViewById(R.id.search_income_category).setVisibility(View.VISIBLE);
        }
        if(((RadioButton)findViewById(R.id.search_radio_only_expenditure)).isChecked() || ((RadioButton)findViewById(R.id.search_radio_both)).isChecked()) {
            findViewById(R.id.search_expenditure_category).setVisibility(View.VISIBLE);
            findViewById(R.id.search_expenditure_way).setVisibility(View.VISIBLE);
        }
        findViewById(R.id.search_second_view).setVisibility(View.VISIBLE);
        findViewById(R.id.search_inquiry).setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.search_btn_fold_and_spread)).setText("▲   접   기");
        isfolded = false;

    }



}