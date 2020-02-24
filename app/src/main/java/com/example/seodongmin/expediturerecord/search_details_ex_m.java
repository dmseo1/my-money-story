package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.*;
import android.view.*;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.search.search_adapter;
import static com.example.seodongmin.expediturerecord.search.search_adapter_result;
import static com.example.seodongmin.expediturerecord.search.search_adapter_result_2;

/**
 * Created by seodongmin on 2017-04-23.
 */

public class search_details_ex_m extends AppCompatActivity {

    boolean isResumeCanImplement_where = false;
    boolean isResumeCanImplement_way = false;
    int RESUMER = 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details_ex_m);

        Intent intent_m = getIntent();

        final int pos = intent_m.getIntExtra("ex_pos_m", -1);
        if(pos == -1)
        {
            return;
        }
        final String year = intent_m.getStringExtra("ex_year_m");
        final String month = intent_m.getStringExtra("ex_month_m");
        final String day = intent_m.getStringExtra("ex_day_m");
        String way = intent_m.getStringExtra("ex_way_m");
        String where = intent_m.getStringExtra("ex_where_m");
        long amount = intent_m.getLongExtra("ex_amount_m", 0);
        int satis = intent_m.getIntExtra("ex_satis_m", -1);
        int includeStat = intent_m.getIntExtra("ex_includeExp_m", -1);
        String memo = intent_m.getStringExtra("ex_memo_m");


        Button.OnClickListener way_setting_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search_details_ex_m.this, setting_exw.class);
                startActivityForResult(intent, RESUMER);
            }
        };
        Button way_setting = (Button) findViewById(R.id.details_ex_m_btn_way_setting);
        way_setting.setOnClickListener(way_setting_listener);


        Button.OnClickListener category_setting_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search_details_ex_m.this, setting_exc.class);
                startActivityForResult(intent, RESUMER);
            }
        };
        Button category_setting = (Button) findViewById(R.id.details_ex_m_btn_category_setting);
        category_setting.setOnClickListener(category_setting_listener);

        final EditText txt_memo = (EditText) findViewById(R.id.details_ex_m_txt_memo);
        Button.OnClickListener delmemo_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_memo.setText("");
            }
        };
        Button delmemo = (Button) findViewById(R.id.details_ex_m_btn_delmemo);
        delmemo.setOnClickListener(delmemo_listener);




        //레이아웃에 포함된 뷰들
        final Spinner spin_year = (Spinner) findViewById(R.id.details_ex_m_spin_y);
        final Spinner spin_month = (Spinner) findViewById(R.id.details_ex_m_spin_m);
        final Spinner spin_day = (Spinner) findViewById(R.id.details_ex_m_spin_d);
        final Spinner spin_way = (Spinner) findViewById(R.id.details_ex_m_spin_way);
        final Spinner spin_where = (Spinner) findViewById(R.id.details_ex_m_spin_where);
        final EditText txt_amount = (EditText) findViewById(R.id.details_ex_m_txt_amount);
        final RadioButton rd_5 = (RadioButton) findViewById(R.id.details_ex_m_5p);
        final RadioButton rd_4 = (RadioButton) findViewById(R.id.details_ex_m_4p);
        final RadioButton rd_3 = (RadioButton) findViewById(R.id.details_ex_m_3p);
        final RadioButton rd_2 = (RadioButton) findViewById(R.id.details_ex_m_2p);
        final RadioButton rd_1 = (RadioButton) findViewById(R.id.details_ex_m_1p);
        final ImageButton im_5 = (ImageButton) findViewById(R.id.details_ex_m_imgbtn_5p);
        final ImageButton im_4 = (ImageButton) findViewById(R.id.details_ex_m_imgbtn_4p);
        final ImageButton im_3 = (ImageButton) findViewById(R.id.details_ex_m_imgbtn_3p);
        final ImageButton im_2 = (ImageButton) findViewById(R.id.details_ex_m_imgbtn_2p);
        final ImageButton im_1 = (ImageButton) findViewById(R.id.details_ex_m_imgbtn_1p);
        final RadioButton rd_yes = (RadioButton) findViewById(R.id.details_ex_m_rd_yes);
        final RadioButton rd_no = (RadioButton) findViewById(R.id.details_ex_m_rd_no);


        //년월일 스피너 제약사항 처리
        final MyYMDSetting ex_ymdsetting = new MyYMDSetting();
        ex_ymdsetting.setInit(search_details_ex_m.this, spin_year, spin_month, spin_day);

        Spinner.OnItemSelectedListener spin_ymd_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int position, long id )
            {
                ex_ymdsetting.setListener(search_details_ex_m.this, spin_year, spin_month, spin_day);
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };
        spin_year.setOnItemSelectedListener(spin_ymd_listener);
        spin_month.setOnItemSelectedListener(spin_ymd_listener);


        //대상 날짜를 불러들임
        spin_year.setSelection(2017 - Integer.parseInt(year));
        spin_month.setSelection(Integer.parseInt(month) - 1);
        spin_day.setSelection(Integer.parseInt(day) - 1);

        ArrayAdapter adapter_way = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_way);
        adapter_way.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_way.setAdapter(adapter_way);
        spin_way.setSelection(expenditure_way.indexOf(way));

        ArrayAdapter adapter_where = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_category);
        adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_where.setAdapter(adapter_where);
        spin_where.setSelection(expenditure_category.indexOf(where));

        txt_amount.setText(String.valueOf(amount));
        txt_amount.setSelection(txt_amount.length());

        switch(satis)
        {
            case 5:
                rd_5.setChecked(true);
                break;
            case 4:
                rd_4.setChecked(true);
                break;
            case 3:
                rd_3.setChecked(true);
                break;
            case 2:
                rd_2.setChecked(true);
                break;
            case 1:
                rd_1.setChecked(true);
                break;
            default:
                break;
        }

        switch(includeStat)
        {
            case 1:
                rd_yes.setChecked(true);
                break;
            case 0:
                rd_no.setChecked(true);
                break;
            default:
                return;
        }
        txt_memo.setText(memo);


        Button.OnClickListener modify_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                int m_year = Integer.parseInt(spin_year.getSelectedItem().toString());
                int m_month = Integer.parseInt(spin_month.getSelectedItem().toString());
                int m_day = Integer.parseInt(spin_day.getSelectedItem().toString());
                String m_way = spin_way.getSelectedItem().toString();
                String m_where = spin_where.getSelectedItem().toString();
                long m_amount = Long.parseLong(txt_amount.getText().toString());
                int m_satisfaction;
                if(rd_5.isChecked()) m_satisfaction = 5;
                else if(rd_4.isChecked()) m_satisfaction = 4;
                else if(rd_3.isChecked()) m_satisfaction = 3;
                else if(rd_2.isChecked()) m_satisfaction = 2;
                else if(rd_1.isChecked()) m_satisfaction = 1;
                else    m_satisfaction = -1;
                int m_includeExp;
                if(rd_yes.isChecked()) m_includeExp = 1;
                else if(rd_no.isChecked()) m_includeExp = 0;
                else m_includeExp = -1;
                String m_memo = txt_memo.getText().toString();

                SharedPreferences.Editor editor = inc_and_exp.edit();



                listview_search_list item = new listview_search_list();
                item.setinex(2);
                item.setyear(m_year);
                item.setmonth(m_month);
                item.setday(m_day);
                item.setway(m_way);
                item.setwhere(m_where);
                item.setemonum(m_satisfaction);
                item.setamount(m_amount);
                item.setincludeStat(m_includeExp);
                item.setmemo(m_memo);

                int newpos = 0;
                if(Integer.parseInt(year) == m_year && Integer.parseInt(month) == m_month && Integer.parseInt(day) == m_day) //날짜 변동이 없을 경우 제자리에 잇으면 된다
                {
                    search_adapter.getListItem().set(pos, item);
                    newpos = pos;

                    String concatenation;
                    concatenation = 2 + ":;:" +
                            m_year + ":;:" +
                            m_month + ":;:" +
                            m_day + ":;:" +
                            m_way + ":;:" +
                            m_where + ":;:" +
                            m_satisfaction + ":;:" +
                            m_amount + ":;:" +
                            m_includeExp + ":;:" +
                            m_memo;

                    editor.putString("incexp_" + newpos, concatenation);
                    editor.commit();

                }
                else {


                    //search_adapter.getListItem().remove(search_adapter_result_2.getListItem().get(pos).getabspos()); //기존 것을 지우고
                    search_adapter.getListItem().remove(pos); //기존것을 지우고
                    int i = pos;
                    System.out.println("pos: " + pos);
                    while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                    {
                        if(inc_and_exp.getString("incexp_" + i, "").equals(""))
                        {
                            editor.putString("incexp_" + (i-1), "");
                            editor.commit();
                            break;
                        }

                        editor.putString("incexp_" + i, inc_and_exp.getString("incexp_" + (i+1), ""));
                        editor.commit();
                        i++;
                    }

                    newpos = search_adapter.addItem(item); //재삽입

                    String concatenation;
                    concatenation = 2 + ":;:" +
                            m_year + ":;:" +
                            m_month + ":;:" +
                            m_day + ":;:" +
                            m_way + ":;:" +
                            m_where + ":;:" +
                            m_satisfaction + ":;:" +
                            m_amount + ":;:" +
                            m_includeExp + ":;:" +
                            m_memo;

                    i = newpos; //삽입이 일어난 위치
                    String[] tempstr = new String[2];
                    tempstr[1] = inc_and_exp.getString("incexp_" + i, "");
                    int switching = 0;
                    while (!inc_and_exp.getString("incexp_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr[switching] = inc_and_exp.getString("incexp_" + (i + 1), "");
                        editor.putString("incexp_" + (i + 1), tempstr[(switching + 1) % 2]);
                        i++;
                        switching = (switching + 1) % 2;
                    }

                    editor.putString("incexp_" + newpos, concatenation);
                    editor.commit();
                }

                Intent modify_intent = new Intent(search_details_ex_m.this, search.class);
                modify_intent.putExtra("mdetail_ex_pos", newpos);
                setResult(RESULT_OK, modify_intent);

                Toast.makeText(search_details_ex_m.this, "수정하였습니다.", Toast.LENGTH_SHORT);
                finish();
            }
        };


        Button modify = (Button) findViewById(R.id.details_ex_m_modify);
        modify.setOnClickListener(modify_listener);


        Button.OnClickListener cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        Button cancel = (Button) findViewById(R.id.details_ex_m_cancel);
        cancel.setOnClickListener(cancel_listener);

        //뒤로가기
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.details_ex_m_img_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();
       if(isResumeCanImplement_way) {

           Spinner spinner_way = (Spinner) findViewById(R.id.details_ex_m_spin_way);
           ArrayAdapter adapter_way = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_way);
           adapter_way.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           spinner_way.setAdapter(adapter_way);
           isResumeCanImplement_way = false;
       }
        if(isResumeCanImplement_where) {
            Spinner spinner_where = (Spinner) findViewById(R.id.details_ex_m_spin_where);
            ArrayAdapter<String> adapter_where;
            adapter_where = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenditure_category);
            adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_where.setAdapter(adapter_where);
            isResumeCanImplement_where = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getIntExtra("Changed_m", 0) == 1)
            isResumeCanImplement_where = true;
        else if(data.getIntExtra("Changed_m", 0) == 2)
            isResumeCanImplement_way = true;
    }
}
