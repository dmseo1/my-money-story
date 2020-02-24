package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.Calendar;

import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_icon;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_icon;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-05-05.
 */

public class incexp_waychoose extends AppCompatActivity {

    int RESULT_MODIFY_YMD = 3333;
    int RESULT_MODIFY_AMOUNT = 4444;
    int RESULT_MODIFY_CATEGORY = 5555;

    int year;
    int month;
    int day;
    String aaa;
    String where;
    int satisfaction;
    String amount;
    String memo;
    boolean isBackAnimationAllowed = false;

    String apply_ymd;
    String apply_amount;

    TextView txt_ymd;
    TextView txt_amount;
    TextView txt_where;

    RadioButton p5;
    RadioButton p4;
    RadioButton p3;
    RadioButton p2;
    RadioButton p1;


    gridview_chooseicon_list_adapter waychoose_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incexp_waychoose);

        Intent getintent = getIntent();
        year = getintent.getIntExtra("nn_year", 0);
        month = getintent.getIntExtra("nn_month", 0);
        day = getintent.getIntExtra("nn_day", 0);
        aaa = getintent.getStringExtra("nn_aaa");
        where = getintent.getStringExtra("nn_where");
        amount = getintent.getStringExtra("nn_amount");
        memo = getintent.getStringExtra("nn_memo");

        txt_ymd = (TextView) findViewById(R.id.incexp_waychoose_lbl_ymd);
        txt_amount = (TextView) findViewById(R.id.incexp_waychoose_lbl_amount);
        txt_where = (TextView) findViewById(R.id.incexp_waychoose_lbl_where);


        TextView.OnClickListener txt_ymd_listener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(incexp_waychoose.this, incexp_modify_ymd.class);
                intent.putExtra("whereFrom", 2);
                intent.putExtra("now_year", year);
                intent.putExtra("now_month", month);
                intent.putExtra("now_day", day);
                startActivityForResult(intent, RESULT_MODIFY_YMD);
            }
        };
        txt_ymd.setOnClickListener(txt_ymd_listener);

        TextView.OnClickListener txt_amount_listener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(incexp_waychoose.this, incexp_modify_amount.class);
                intent.putExtra("whereFrom", 2);
                intent.putExtra("now_amount", amount);
                startActivityForResult(intent, RESULT_MODIFY_AMOUNT);
            }
        };
        txt_amount.setOnClickListener(txt_amount_listener);

        txt_ymd.setText(getintent.getStringExtra("nn_apply_ymd"));
        txt_amount.setText(getintent.getStringExtra("nn_apply_amount"));
        txt_where.setText(where);


        p5 = (RadioButton) findViewById(R.id.incexp_expenditure_5p);
        p4 = (RadioButton) findViewById(R.id.incexp_expenditure_4p);
        p3 = (RadioButton) findViewById(R.id.incexp_expenditure_3p);
        p2 = (RadioButton) findViewById(R.id.incexp_expenditure_2p);
        p1 = (RadioButton) findViewById(R.id.incexp_expenditure_1p);

        View.OnClickListener back_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackAnimationAllowed = true;
                finish();
            }
        };
        ImageButton back = (ImageButton) findViewById(R.id.incexp_waychoose_back);
        back.setOnClickListener(back_listener);


    }

    @Override
    public void onResume()
    {
        super.onResume();

        waychoose_adapter = new gridview_chooseicon_list_adapter();

        apply_ymd = year + "년 " + month + "월 " + day + "일 " + aaa + "요일";
        txt_ymd.setText(apply_ymd);
        apply_amount = String.format("%,d", Long.parseLong(amount)) + "원";
        txt_amount.setText(apply_amount);

        GridView gridview = (GridView) findViewById(R.id.incexp_waychoose_list);
        gridview.setFocusableInTouchMode(true);
        gridview.requestFocus();
        waychoose_adapter.getListItem().clear();

        for (int i = 0; i < expenditure_way.size(); i++) {
            String name = expenditure_way.get(i);
            Drawable icon = expenditure_way_icon.get(i);
            waychoose_adapter.addItem(icon, name);
        }
        waychoose_adapter.addItem(getDrawable(R.drawable.ico_balloon), "설정");
        gridview.setAdapter(waychoose_adapter);



        GridView.OnItemClickListener gridview_item_listener = new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(position == expenditure_way.size())
                {
                    Intent setting_inc_intent = new Intent(incexp_waychoose.this, setting_exw.class);
                    startActivity(setting_inc_intent);
                    return;
                }


                //통계창에서 온 경우, 통계창을 refresh해주기 위한 인텐트 전달
                Intent returnToStat = new Intent(incexp_waychoose.this, stat_v2.class);
                returnToStat.putExtra("statRefresh", true);
                setResult(RESULT_OK, returnToStat);

                //만족도 체크
                if(p5.isChecked()) satisfaction = 5;
                else if(p4.isChecked()) satisfaction = 4;
                else if(p3.isChecked()) satisfaction = 3;
                else if(p2.isChecked()) satisfaction = 2;
                else if(p1.isChecked()) satisfaction = 1;
                else satisfaction = -1;


                SharedPreferences.Editor editor = inc_and_exp.edit();

                /// addItem(int inex, int year, int month, int day, int waynum, int wherenum, int emonum, int amount,  String memo)
                int pos = search_adapter.addItem(2,year,month,day,waychoose_adapter.getListItem().get(position).getname(),where,satisfaction,Long.parseLong(amount),1,memo);

                String concatenation;
                concatenation = 2 + ":;:" +
                        year + ":;:" +
                        month + ":;:" +
                        day + ":;:" +
                        waychoose_adapter.getListItem().get(position).getname() + ":;:" +
                        where + ":;:" +
                        satisfaction + ":;:" +
                        amount + ":;:" +
                        "1" + ":;:" +
                        memo;

                System.out.println(year +"." + month + "." + day + "." + waychoose_adapter.getListItem().get(position).getname() +"." + where + "." + satisfaction + "." + amount + "." + memo );
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


                Intent intent_exp = new Intent(incexp_waychoose.this, incexp_catchoose.class);
                setResult(RESULT_OK, intent_exp);
                Toast.makeText(incexp_waychoose.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                finish();


            }
        };

        gridview.setOnItemClickListener(gridview_item_listener);


    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(isBackAnimationAllowed)
        {
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            isBackAnimationAllowed = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isBackAnimationAllowed = true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_MODIFY_AMOUNT) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {

            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

        if(requestCode == RESULT_MODIFY_AMOUNT) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                amount = data.getStringExtra("ModifiedAmount");
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

        if(requestCode == RESULT_MODIFY_YMD) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                long longdate = data.getLongExtra("ModifiedDate", 0);
                android.text.format.Time t = new android.text.format.Time();
                t.set(longdate);
                year = t.year;
                month = t.month + 1;
                day = t.monthDay;

                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day);
                aaa = setDateAAA(cal);

            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }


    public String setDateAAA(Calendar cal)
    {
        int aaa = cal.get(Calendar.DAY_OF_WEEK);
        switch(aaa)
        {
            case 1:
                return "일";
            case 2:
                return "월";
            case 3:
                return "화";
            case 4:
                return "수";
            case 5:
                return "목";
            case 6:
                return "금";
            case 7:
                return "토";
            default:
                return "-";
        }
    }
}
