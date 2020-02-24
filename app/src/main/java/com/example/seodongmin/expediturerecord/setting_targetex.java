package com.example.seodongmin.expediturerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.*;
import android.content.*;

import java.util.Calendar;

/**
 * Created by seodongmin on 2017-04-24.
 */

public class setting_targetex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_targetex);

  /*      Intent intent = getIntent();
        String remaining = intent.getStringExtra("remaining");
        final String stat_year = intent.getStringExtra("stat_year");
        final String stat_month = intent.getStringExtra("stat_month");*/
        final EditText target_ex = (EditText) findViewById(R.id.popup_stat_targetex_txt);
        final Calendar cal = Calendar.getInstance();
        SharedPreferences sf = getSharedPreferences("goalex_" + String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH)+1), MODE_PRIVATE);
        target_ex.setText(String.valueOf(sf.getLong("goalex_" + String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH)+1), 0)));
        target_ex.setSelection(target_ex.length());
        Button.OnClickListener ok_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                if(target_ex.getText().toString().length() == 0)
                {
                    Toast.makeText(setting_targetex.this, "금액을 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    try
                    {
                        Long.parseLong(target_ex.getText().toString());
                    }catch(java.lang.NumberFormatException e)
                    {
                        Toast.makeText(setting_targetex.this, "정상적인 값을 입력하세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Long.parseLong(target_ex.getText().toString()) < 0) {
                        Toast.makeText(setting_targetex.this, "음수는 입력할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        makeGoalExpenditureData("goalex_" + String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH)+1), Long.parseLong(target_ex.getText().toString()));
                        /*Intent apply_intent = new Intent(setting_targetex.this, stat.class);
                        apply_intent.putExtra("targetex_return", target_ex.getText().toString());
                        setResult(RESULT_OK, apply_intent);*/
                        finish();
                    }

                }
            }
        };
        Button ok = (Button) findViewById(R.id.popup_stat_targetex_btn_ok);
        ok.setOnClickListener(ok_listener);


        Button.OnClickListener cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };
        Button cancel = (Button) findViewById(R.id.popup_stat_targetex_btn_cancel);
        cancel.setOnClickListener(cancel_listener);

    }

    public void makeGoalExpenditureData(String name, long data)
    {
        SharedPreferences sf = getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.putLong(name, data);
        editor.commit();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
