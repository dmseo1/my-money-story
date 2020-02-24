package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.Calendar;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_icon;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-05-05.
 */

public class incexp_catchoose extends AppCompatActivity {

    int RESULT_MODIFY_YMD = 3333;
    int RESULT_MODIFY_AMOUNT = 4444;

    gridview_chooseicon_list_adapter catchoose_adapter;
    int type;
    int year;
    int month;
    int day;
    Intent getintent;
    boolean isAnimationAllowed = false;
    boolean isBackAnimationAllowed = false;
    int RESULT_EXPENDITUREOK = 2222;

    String apply_ymd;
    String apply_amount;

    String s_aaa;

    String amount;
    String s_amount;
    TextView nowymd;
    TextView nowamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incexp_catchoose);

        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        getintent = getIntent();
        type = getintent.getIntExtra("FlowType", 0);
        year = getintent.getIntExtra("now_year", 0);
        month = getintent.getIntExtra("now_month", 0);
        day = getintent.getIntExtra("now_day", 0);
        s_aaa = getintent.getStringExtra("now_aaa");
        amount = getintent.getStringExtra("now_amount");
        s_amount = String.format("%,d", Long.parseLong(amount));
        String s_year = String.valueOf(year);
        String s_month = String.valueOf(month);
        String s_day = String.valueOf(day);
        System.out.println("choose category로 넘어온 날짜: " + s_year + "년 " + s_month + "월 " + s_day + "일 " + s_aaa + "요일");

        nowymd = (TextView) findViewById(R.id.incexp_catchoose_lbl_ymd);
        nowamount = (TextView) findViewById(R.id.incexp_catchoose_lbl_amount);
        apply_ymd = s_year + "년 " + s_month + "월 " + s_day + "일 " + s_aaa + "요일";
        apply_amount = s_amount + "원";
        nowymd.setText(apply_ymd);
        nowamount.setText(apply_amount);

        TextView.OnClickListener nowymd_listener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(incexp_catchoose.this, incexp_modify_ymd.class);
                intent.putExtra("whereFrom", 1);
                intent.putExtra("now_year", year);
                intent.putExtra("now_month", month);
                intent.putExtra("now_day", day);
                startActivityForResult(intent, RESULT_MODIFY_YMD);
            }
        };
        nowymd.setOnClickListener(nowymd_listener);

        TextView.OnClickListener nowamount_listener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(incexp_catchoose.this, incexp_modify_amount.class);
                intent.putExtra("whereFrom", 1);
                intent.putExtra("now_amount", amount);
                startActivityForResult(intent, RESULT_MODIFY_AMOUNT);
            }
        };
        nowamount.setOnClickListener(nowamount_listener);


        View.OnClickListener back_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackAnimationAllowed = true;
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.incexp_catchoose_back);
        back.setOnClickListener(back_listener);

    }


    public void onResume()
    {
        super.onResume();
        catchoose_adapter = new gridview_chooseicon_list_adapter();

        apply_ymd = year + "년 " + month + "월 " + day + "일 " + s_aaa + "요일";
        nowymd.setText(apply_ymd);
        apply_amount = String.format("%,d",Long.parseLong(amount)) + "원";
        nowamount.setText(apply_amount);

        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        GridView gridview = (GridView) findViewById(R.id.incexp_catchoose_list);
        gridview.setFocusableInTouchMode(true);
        gridview.requestFocus();
        catchoose_adapter.getListItem().clear();
        switch(type) {
            case 1:
                for (int i = 0; i < income_category.size(); i++) {
                    String name = income_category.get(i);
                    Drawable icon = income_category_icon.get(i);
                    catchoose_adapter.addItem(icon, name);
                }
                catchoose_adapter.addItem(getDrawable(R.drawable.ico_balloon), "설정");
                gridview.setAdapter(catchoose_adapter);
                break;
            case 2:
                for (int i = 0; i < expenditure_category.size(); i++) {
                    String name = expenditure_category.get(i);
                    Drawable icon = expenditure_category_icon.get(i);
                    catchoose_adapter.addItem(icon, name);
                }
                catchoose_adapter.addItem(getDrawable(R.drawable.ico_balloon), "설정");
                gridview.setAdapter(catchoose_adapter);
                break;
            default:
                break;
        }



        GridView.OnItemClickListener gridview_item_listener = new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                EditText txt_memo = (EditText) findViewById(R.id.incexp_catchoose_memo);
                String cc_memo = txt_memo.getText().toString();
                switch(type)
                {
                    case 1:

                        if(position == income_category.size())
                        {
                            Intent setting_inc_intent = new Intent(incexp_catchoose.this, setting_inc.class);
                            startActivity(setting_inc_intent);
                            break;
                        }


                        //통계창에서 온 경우, 통계창을 refresh해주기 위한 인텐트 전달
                        Intent returnToStat = new Intent(incexp_catchoose.this, incexp_amount.class);
                        returnToStat.putExtra("statRefresh", true);
                        setResult(RESULT_OK, returnToStat);

                        SharedPreferences.Editor editor = inc_and_exp.edit();

                        /// addItem(int inex, int year, int month, int day, int waynum, int wherenum, int emonum, int amount,  String memo)
                        int pos = search_adapter.addItem(1,year,month,day,"",catchoose_adapter.getListItem().get(position).getname(),-1,Long.parseLong(getintent.getStringExtra("now_amount")),1,cc_memo);

                        String concatenation;
                        concatenation = 1 + ":;:" +
                                year + ":;:" +
                                month + ":;:" +
                                day + ":;:" +
                                "" + ":;:" +
                                catchoose_adapter.getListItem().get(position).getname() + ":;:" +
                                "-1" + ":;:" +
                                getintent.getStringExtra("now_amount") + ":;:" +
                                "1" + ":;:" +
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


                        Intent intent_inc = new Intent(incexp_catchoose.this, incexp_amount.class);
                        setResult(RESULT_OK, intent_inc);
                        Toast.makeText(incexp_catchoose.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;

                    case 2:
                        isAnimationAllowed = true;
                        if(position == expenditure_category.size())
                        {
                            Intent setting_exc_intent = new Intent(incexp_catchoose.this, setting_exc.class);
                            startActivity(setting_exc_intent);
                            break;
                        }

                        Intent intent_exp = new Intent(incexp_catchoose.this, incexp_waychoose.class);
                        intent_exp.putExtra("nn_year", year);
                        intent_exp.putExtra("nn_month", month);
                        intent_exp.putExtra("nn_day", day);
                        intent_exp.putExtra("nn_where", catchoose_adapter.getListItem().get(position).getname());
                        intent_exp.putExtra("nn_amount", amount);
                        intent_exp.putExtra("nn_apply_ymd", apply_ymd);
                        intent_exp.putExtra("nn_apply_amount", apply_amount);
                        intent_exp.putExtra("nn_memo", cc_memo);
                        intent_exp.putExtra("nn_aaa",s_aaa);
                        startActivityForResult(intent_exp, RESULT_EXPENDITUREOK);

                        break;

                    default:
                        break;
                }

            }
        };

        gridview.setOnItemClickListener(gridview_item_listener);
    }

    @Override
    public void onPause()
    {
        super.onPause();


        if(isAnimationAllowed)
        {
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            isAnimationAllowed = false;
        }

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

        if(requestCode == RESULT_EXPENDITUREOK) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Intent intent = new Intent(incexp_catchoose.this, incexp_amount.class);
                setResult(RESULT_OK, intent);
                finish();
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
                s_aaa = setDateAAA(cal);

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
