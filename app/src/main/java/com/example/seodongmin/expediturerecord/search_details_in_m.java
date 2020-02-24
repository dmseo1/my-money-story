package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.search.search_adapter;
import static com.example.seodongmin.expediturerecord.search.search_adapter_result;
import static com.example.seodongmin.expediturerecord.search.search_adapter_result_2;

/**
 * Created by seodongmin on 2017-04-23.
 */

public class search_details_in_m extends AppCompatActivity {

    boolean isResumeCanImplement = false;
    int RESUMER = 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details_in_m);

        Intent intent_m = getIntent();

        final int pos = intent_m.getIntExtra("in_pos_m", - 1);
        if(pos == -1)
        {
            return;
        }
        final String year = intent_m.getStringExtra("in_year_m");
        final String month = intent_m.getStringExtra("in_month_m");
        final String day = intent_m.getStringExtra("in_day_m");
        String where = intent_m.getStringExtra("in_where_m");
        long amount_int = intent_m.getLongExtra("in_amount_m", 0);
        int includeStat = intent_m.getIntExtra("in_includeStat_m", -1);
        String memo = intent_m.getStringExtra("in_memo_m");




        Button.OnClickListener category_setting_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(search_details_in_m.this, setting_inc.class);
                startActivityForResult(intent, RESUMER);
            }
        };
        Button category_setting = (Button) findViewById(R.id.details_in_m_btn_category_setting);
        category_setting.setOnClickListener(category_setting_listener);


        final EditText txt_memo = (EditText) findViewById(R.id.details_in_m_txt_memo);
        Button.OnClickListener delmemo_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_memo.setText("");
            }
        };
        Button delmemo = (Button) findViewById(R.id.details_in_m_btn_delmemo);
        delmemo.setOnClickListener(delmemo_listener);


        final Spinner spin_year = (Spinner) findViewById(R.id.details_in_m_spin_y);
        final Spinner spin_month = (Spinner) findViewById(R.id.details_in_m_spin_m);
        final Spinner spin_day = (Spinner) findViewById(R.id.details_in_m_spin_d);
        final Spinner spin_where = (Spinner) findViewById(R.id.details_in_m_spin_where);
        final EditText txt_amount = (EditText) findViewById(R.id.details_in_m_txt_amount);
        final RadioButton rd_yes = (RadioButton) findViewById(R.id.details_in_m_rd_yes);
        final RadioButton rd_no = (RadioButton) findViewById(R.id.details_in_m_rd_no);


        final MyYMDSetting in_ymdsetting = new MyYMDSetting();
        in_ymdsetting.setInit(search_details_in_m.this, spin_year, spin_month, spin_day);


        Spinner.OnItemSelectedListener spin_ymd_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int position, long id )
            {
                in_ymdsetting.setListener(search_details_in_m.this, spin_year, spin_month, spin_day);
            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {

            }
        };

        spin_year.setOnItemSelectedListener(spin_ymd_listener);
        spin_month.setOnItemSelectedListener(spin_ymd_listener);

        spin_year.setSelection(2017 - Integer.parseInt(year));
        spin_month.setSelection(Integer.parseInt(month) - 1);
        spin_day.setSelection(Integer.parseInt(day) - 1);


        ArrayAdapter adapter_where = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, income_category);
        adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_where.setAdapter(adapter_where);
        spin_where.setSelection(income_category.indexOf(where));

        txt_amount.setText(String.valueOf(amount_int));
        txt_amount.setSelection(txt_amount.length());
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
                String m_where = spin_where.getSelectedItem().toString();
                long m_amount = Long.parseLong(txt_amount.getText().toString());
                int m_includeStat;
                if(rd_yes.isChecked()) m_includeStat = 1;
                else if(rd_no.isChecked()) m_includeStat = 0;
                else m_includeStat = -1;
                String m_memo = txt_memo.getText().toString();

                SharedPreferences.Editor editor = inc_and_exp.edit();

                listview_search_list item = new listview_search_list();
                item.setinex(1);
                item.setyear(m_year);
                item.setmonth(m_month);
                item.setday(m_day);
                item.setway("");
                item.setwhere(m_where);
                item.setemonum(-1);
                item.setamount(m_amount);
                item.setincludeStat(m_includeStat);
                item.setmemo(m_memo);

                int newpos = 0;
                if(Integer.parseInt(year) == m_year && Integer.parseInt(month) == m_month && Integer.parseInt(day) == m_day)
                {
                    search_adapter.getListItem().set(pos, item);
                    newpos = pos;

                    String concatenation;
                    concatenation = 1 + ":;:" +
                            m_year + ":;:" +
                            m_month + ":;:" +
                            m_day + ":;:" +
                            "" + ":;:" +
                            m_where + ":;:" +
                            "-1" + ":;:" +
                            m_amount + ":;:" +
                            m_includeStat + ":;:" +
                            m_memo;

                    editor.putString("incexp_" + newpos, concatenation);
                    editor.commit();

                }
                else {


                    //search_adapter.getListItem().remove(search_adapter_result_2.getListItem().get(pos).getabspos()); //기존 것을 지우고
                    search_adapter.getListItem().remove(pos);
                    int i = pos;
                    System.out.println("pos: " + pos);
                    while(true)
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
                    concatenation = 1 + ":;:" +
                            m_year + ":;:" +
                            m_month + ":;:" +
                            m_day + ":;:" +
                            "" + ":;:" +
                            m_where + ":;:" +
                            "-1" + ":;:" +
                            m_amount + ":;:" +
                            m_includeStat + ":;:" +
                            m_memo;

                    i = newpos;
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


                Intent modify_intent = new Intent(search_details_in_m.this, search_details_ex.class);
                modify_intent.putExtra("mdetail_in_pos", newpos);
                setResult(RESULT_OK, modify_intent);

                Toast.makeText(search_details_in_m.this, "수정하였습니다.", Toast.LENGTH_SHORT);
                finish();
            }
        };

        Button modify = (Button) findViewById(R.id.details_in_m_modify);
        modify.setOnClickListener(modify_listener);


        Button.OnClickListener cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

    Button cancel = (Button) findViewById(R.id.details_in_m_cancel);
        cancel.setOnClickListener(cancel_listener);


    ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            finish();
        }
    };

    ImageButton back = (ImageButton) findViewById(R.id.details_in_m_img_back);
        back.setOnClickListener(back_listener);
}

    @Override
    public void onResume()
    {
        super.onResume();
        if(isResumeCanImplement) {
            Spinner spinner_where = (Spinner) findViewById(R.id.details_in_m_spin_where);
            ArrayAdapter<String> adapter_where;
            adapter_where = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, income_category);
            adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_where.setAdapter(adapter_where);
            isResumeCanImplement = false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESUMER)
        {
            if(resultCode == RESULT_OK)
            {
                if(data.getIntExtra("Changed_m", 0) == 1)
                    isResumeCanImplement = true;
            }
        }


    }
}
