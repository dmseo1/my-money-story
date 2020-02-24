package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.content.*;

import java.util.*;
import android.widget.*;
import android.database.sqlite.*;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-20.
 */

public class input_expenditure extends AppCompatActivity {

    int first_2_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_expenditure);


        final Spinner spinner_y = (Spinner) findViewById(R.id.expenditure_spin_y);
        final Spinner spinner_m = (Spinner) findViewById(R.id.expenditure_spin_m);
        final Spinner spinner_d = (Spinner) findViewById(R.id.expenditure_spin_d);
        final MyYMDSetting ex_ymdsetting = new MyYMDSetting();
        ex_ymdsetting.setInit(input_expenditure.this, spinner_y, spinner_m, spinner_d);


        Spinner.OnItemSelectedListener spin_ymd_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id )
            {
                ex_ymdsetting.setListener(input_expenditure.this, spinner_y, spinner_m, spinner_d);
            }


            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };

        spinner_y.setOnItemSelectedListener(spin_ymd_listener);
        spinner_m.setOnItemSelectedListener(spin_ymd_listener);


        //통계 처리
        Intent getStatIntent = getIntent();
        if(getStatIntent.getIntExtra("statType", 0) == 12)
        {
            spinner_y.setSelection(2017 - getStatIntent.getIntExtra("statDay_y", 0));
            spinner_m.setSelection(getStatIntent.getIntExtra("statDay_m", 0) - 1);
            spinner_d.setSelection(getStatIntent.getIntExtra("statDay_d", 0) - 1);
        }
        if(getStatIntent.getIntExtra("statType", 0) == 22)
        {
            spinner_y.setSelection(2017 - getStatIntent.getIntExtra("statWeek_y", 0));
            spinner_m.setSelection(getStatIntent.getIntExtra("statWeek_m", 0) - 1);
            spinner_d.setSelection(getStatIntent.getIntExtra("statWeek_d", 0) - 1);
        }
        if(getStatIntent.getIntExtra("statType", 0) == 32)
        {
            spinner_y.setSelection(2017 - getStatIntent.getIntExtra("statMonth_y", 0));
            spinner_m.setSelection(getStatIntent.getIntExtra("statMonth_m", 0) - 1);
            spinner_d.setSelection(getStatIntent.getIntExtra("statMonth_d", 0) - 1);
        }


       /* ArrayAdapter adapter_where = ArrayAdapter.createFromResource(this, R.array.expenditure_category, android.R.layout.simple_spinner_item);
        adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_where.setAdapter(adapter_where);*/


        //라디오버튼 기본 선택값
        RadioButton satis_default = (RadioButton) findViewById(R.id.expenditure_3p);
        satis_default.setChecked(true);
        RadioButton include_daymean_default = (RadioButton) findViewById(R.id.expenditure_rd_yes);
        include_daymean_default.setChecked(true);


        //카테고리, 지출수단 세팅 연결 버튼
        Button.OnClickListener setting_category_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(input_expenditure.this, setting_exc.class);
                startActivity(intent);
            }
        };
        Button setting_category = (Button) findViewById(R.id.expenditure_btn_category_setting);
        setting_category.setOnClickListener(setting_category_listener);


        Button.OnClickListener setting_way_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(input_expenditure.this, setting_exw.class);
                startActivity(intent);
            }
        };
        Button setting_way = (Button) findViewById(R.id.expenditure_btn_way_setting);
        setting_way.setOnClickListener(setting_way_listener);


        //등록 버튼
        OnClickListener listener_register = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {


                Spinner spin_y = (Spinner) findViewById(R.id.expenditure_spin_y);
                Spinner spin_m = (Spinner) findViewById(R.id.expenditure_spin_m);
                Spinner spin_d = (Spinner) findViewById(R.id.expenditure_spin_d);
                Spinner spin_way = (Spinner) findViewById(R.id.expenditure_spin_way);
                Spinner spin_where = (Spinner) findViewById(R.id.expenditure_spin_where);
                EditText txt_amount = (EditText) findViewById(R.id.expenditure_txt_amount);
                RadioButton rb_5p = (RadioButton) findViewById(R.id.expenditure_5p);
                RadioButton rb_4p = (RadioButton) findViewById(R.id.expenditure_4p);
                RadioButton rb_3p = (RadioButton) findViewById(R.id.expenditure_3p);
                RadioButton rb_2p = (RadioButton) findViewById(R.id.expenditure_2p);
                RadioButton rb_1p = (RadioButton) findViewById(R.id.expenditure_1p);
                RadioButton rb_yes = (RadioButton) findViewById(R.id.expenditure_rd_yes);
                RadioButton rb_no = (RadioButton) findViewById(R.id.expenditure_rd_no);
                EditText txt_memo = (EditText) findViewById(R.id.expenditure_txt_memo);

                int cc_spin_y = Integer.parseInt(spin_y.getSelectedItem().toString());
                int cc_spin_m = Integer.parseInt(spin_m.getSelectedItem().toString());
                int cc_spin_d = Integer.parseInt(spin_d.getSelectedItem().toString());
                String cc_spin_way = spin_way.getSelectedItem().toString();
                String cc_spin_where = spin_where.getSelectedItem().toString();
                long cc_txt_amount = 0;

                if(txt_amount.getText().toString().length() == 0) {}
                else {
                    cc_txt_amount = Long.parseLong(txt_amount.getText().toString());
                }

                int cc_satisfaction;
                if(rb_5p.isChecked())
                    cc_satisfaction = 5;
                else if(rb_4p.isChecked())
                    cc_satisfaction = 4;
                else if(rb_3p.isChecked())
                    cc_satisfaction = 3;
                else if(rb_2p.isChecked())
                    cc_satisfaction = 2;
                else if(rb_1p.isChecked())
                    cc_satisfaction = 1;
                else
                    cc_satisfaction = -1;

                int cc_includeExp;
                if(rb_yes.isChecked())
                    cc_includeExp = 1;
                else if(rb_no.isChecked())
                    cc_includeExp = 0;
                else
                    cc_includeExp = -1;

                String cc_memo = txt_memo.getText().toString();


                if(txt_amount.getText().toString().length() == 0)
                {
                    Toast.makeText(input_expenditure.this, "지출 금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {

                    try
                    {
                        Long.parseLong(txt_amount.getText().toString());
                    } catch(java.lang.NumberFormatException e) {
                        Toast.makeText(input_expenditure.this, "금액 란에 정상적인 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (cc_satisfaction == 0) {
                        Toast.makeText(input_expenditure.this, "만족도를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent returnToStat = new Intent(input_expenditure.this, stat_v2.class);
                        returnToStat.putExtra("statRefresh", true);
                        setResult(RESULT_OK, returnToStat);

                        SharedPreferences.Editor editor = inc_and_exp.edit();

                        /// addItem(int inex, int year, int month, int day, int waynum, int wherenum, int emonum, int amount,  String memo)
                        int pos = search_adapter.addItem(2,cc_spin_y,cc_spin_m,cc_spin_d,cc_spin_way,cc_spin_where,cc_satisfaction,cc_txt_amount,cc_includeExp,cc_memo);
                        String concatenation;
                        concatenation = 2 + ":;:" +
                                        cc_spin_y + ":;:" +
                                        cc_spin_m + ":;:" +
                                        cc_spin_d + ":;:" +
                                        cc_spin_way + ":;:" +
                                        cc_spin_where + ":;:" +
                                        cc_satisfaction + ":;:" +
                                        cc_txt_amount + ":;:" +
                                        cc_includeExp + ":;:" +
                                        cc_memo;

                        int i = pos;
                        String tempstr = "";
                        while(!inc_and_exp.getString("incexp_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                        {
                            tempstr = inc_and_exp.getString("incexp_" + (i+1), "");
                            editor.putString("incexp_" + (i+1), inc_and_exp.getString("incexp_" + i, ""));
                            i++;
                        }

                        editor.putString("incexp_" + pos, concatenation);
                        editor.commit();
                        Toast.makeText(input_expenditure.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();

                        System.out.println(inc_and_exp.getString("incexp_0", ""));
                        finish();
                    }
                }



            }
        };

        Button register = (Button) findViewById(R.id.expenditure_register);
        register.setOnClickListener(listener_register);


        //취소 버튼
        OnClickListener listener_cancel = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(input_expenditure.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        Button cancel = (Button) findViewById(R.id.expenditure_cancel);
        cancel.setOnClickListener(listener_cancel);


        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.expenditure_img_back);
        back.setOnClickListener(back_listener);



    }

    public void onResume()
    {
        super.onResume();

        Spinner spinner_way = (Spinner) findViewById(R.id.expenditure_spin_way);
        ArrayAdapter adapter_way = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,expenditure_way);
        adapter_way.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_way.setAdapter(adapter_way);

        Spinner spinner_where = (Spinner) findViewById(R.id.expenditure_spin_where);
        ArrayAdapter<String> adapter_where;
        adapter_where = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,expenditure_category);
        adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_where.setAdapter(adapter_where);
    }

    protected void onPause()
    {
        super.onPause();
    }

    public void onStop()
    {
        super.onStop();

    }
    public void onDestroy()
    {

        super.onDestroy();
    }
}
