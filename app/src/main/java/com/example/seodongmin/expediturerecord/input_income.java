package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.*;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.icu.text.*;
import java.util.*;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-20.
 */

public class input_income extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_input_income);

        final Spinner spinner_y = (Spinner) findViewById(R.id.income_spin_y);
        final Spinner spinner_m = (Spinner) findViewById(R.id.income_spin_m);
        final Spinner spinner_d = (Spinner) findViewById(R.id.income_spin_d);
        final MyYMDSetting in_ymdsetting = new MyYMDSetting();
        in_ymdsetting.setInit(input_income.this, spinner_y, spinner_m, spinner_d);

        Spinner.OnItemSelectedListener spin_ymd_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView< ? > adapterView, View view, int position, long id )
            {
                in_ymdsetting.setListener(input_income.this, spinner_y, spinner_m, spinner_d);
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
        if(getStatIntent.getIntExtra("statType", 0) == 11)
        {
            spinner_y.setSelection(2017 - getStatIntent.getIntExtra("statDay_y", 0));
            spinner_m.setSelection(getStatIntent.getIntExtra("statDay_m", 0) - 1);
            spinner_d.setSelection(getStatIntent.getIntExtra("statDay_d", 0) - 1);
        }
        if(getStatIntent.getIntExtra("statType", 0) == 21)
        {
            spinner_y.setSelection(2017 - getStatIntent.getIntExtra("statWeek_y", 0));
            spinner_m.setSelection(getStatIntent.getIntExtra("statWeek_m", 0) - 1);
            spinner_d.setSelection(getStatIntent.getIntExtra("statWeek_d", 0) - 1);
        }
        if(getStatIntent.getIntExtra("statType", 0) == 31)
        {
            spinner_y.setSelection(2017 - getStatIntent.getIntExtra("statMonth_y", 0));
            spinner_m.setSelection(getStatIntent.getIntExtra("statMonth_m", 0) - 1);
            spinner_d.setSelection(getStatIntent.getIntExtra("statMonth_d", 0) - 1);
        }


        /*ArrayAdapter adapter_where = ArrayAdapter.createFromResource(this, R.array.income_category, android.R.layout.simple_spinner_item);
        adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_where.setAdapter(adapter_where);*/


        Button.OnClickListener setting_category_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(input_income.this, setting_inc.class);
                startActivity(intent);
            }
        };
        Button setting_category = (Button) findViewById(R.id.income_btn_category_setting);
        setting_category.setOnClickListener(setting_category_listener);









        OnClickListener listener_register = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {


                Spinner spin_y = (Spinner) findViewById(R.id.income_spin_y);
                Spinner spin_m = (Spinner) findViewById(R.id.income_spin_m);
                Spinner spin_d = (Spinner) findViewById(R.id.income_spin_d);
                Spinner spin_where = (Spinner) findViewById(R.id.income_spin_where);
                EditText txt_amount = (EditText) findViewById(R.id.income_txt_amount);
                RadioButton rd_yes = (RadioButton) findViewById(R.id.income_rd_yes);
                RadioButton rd_no  = (RadioButton) findViewById(R.id.income_rd_no);
                EditText txt_memo = (EditText) findViewById(R.id.income_txt_memo);

                int cc_spin_y = Integer.parseInt(spin_y.getSelectedItem().toString());
                int cc_spin_m = Integer.parseInt(spin_m.getSelectedItem().toString());
                int cc_spin_d = Integer.parseInt(spin_d.getSelectedItem().toString());
                String cc_spin_where = spin_where.getSelectedItem().toString();
                long cc_txt_amount = 0;
                int cc_includeStat;
                if(rd_yes.isChecked())
                    cc_includeStat = 1;
                else if(rd_no.isChecked())
                    cc_includeStat = 0;
                else cc_includeStat = -1;
                if(txt_amount.getText().toString().length() == 0) {}
                else {
                    cc_txt_amount = Long.parseLong(txt_amount.getText().toString());
                }
                String cc_memo = txt_memo.getText().toString();



                if(txt_amount.getText().toString().length() == 0)
                {
                    Toast.makeText(input_income.this, "수입 금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try
                    {
                        Long.parseLong(txt_amount.getText().toString());
                    } catch(java.lang.NumberFormatException e) {
                        Toast.makeText(input_income.this, "금액 란에 정상적인 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }



                    //통계창에서 온 경우, 통계창을 refresh해주기 위한 인텐트 전달
                    Intent returnToStat = new Intent(input_income.this, stat_v2.class);
                    returnToStat.putExtra("statRefresh", true);
                    setResult(RESULT_OK, returnToStat);

                    SharedPreferences.Editor editor = inc_and_exp.edit();

                    /// addItem(int inex, int year, int month, int day, int waynum, int wherenum, int emonum, int amount,  String memo)
                    int pos = search_adapter.addItem(1,cc_spin_y,cc_spin_m,cc_spin_d,"",cc_spin_where,-1,cc_txt_amount,cc_includeStat,cc_memo);

                    String concatenation;
                    concatenation = 1 + ":;:" +
                            cc_spin_y + ":;:" +
                            cc_spin_m + ":;:" +
                            cc_spin_d + ":;:" +
                            "" + ":;:" +
                            cc_spin_where + ":;:" +
                            "-1" + ":;:" +
                            cc_txt_amount + ":;:" +
                            cc_includeStat + ":;:" +
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

                    Toast.makeText(input_income.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };

        Button register = (Button) findViewById(R.id.income_register);
        register.setOnClickListener(listener_register);


        OnClickListener listener_cancel = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(input_income.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        Button cancel = (Button) findViewById(R.id.income_cancel);
        cancel.setOnClickListener(listener_cancel);


        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.income_img_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();

        Spinner spinner_where = (Spinner) findViewById(R.id.income_spin_where);
        ArrayAdapter<String> adapter_where;
        adapter_where = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,income_category);
        adapter_where.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_where.setAdapter(adapter_where);
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
