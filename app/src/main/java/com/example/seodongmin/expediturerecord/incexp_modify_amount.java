package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.*;

/**
 * Created by seodongmin on 2017-05-08.
 */

public class incexp_modify_amount  extends AppCompatActivity {

    int whereFrom = 0;
    EditText txt_amount;
    String amount = "????";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incexp_modify_amount);

        Intent intent = getIntent();
        whereFrom = intent.getIntExtra("whereFrom", 0);
        amount = intent.getStringExtra("now_amount");
        txt_amount = (EditText) findViewById(R.id.popup_modify_amount_txt);
        txt_amount.setText(amount);
        txt_amount.setSelection(txt_amount.length());


        Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt_amount.getText().toString().length() == 0)
                {
                    Toast.makeText(incexp_modify_amount.this, "금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    try
                    {
                        Long.parseLong(txt_amount.getText().toString());
                    } catch(java.lang.NumberFormatException e) {
                        Toast.makeText(incexp_modify_amount.this, "금액 란에 정상적인 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                if(whereFrom == 1) {
                    Intent intent = new Intent(incexp_modify_amount.this, incexp_catchoose.class);
                    intent.putExtra("ModifiedAmount", txt_amount.getText().toString());
                    setResult(RESULT_OK, intent);
                }
                else if(whereFrom == 2)
                {
                    Intent intent = new Intent(incexp_modify_amount.this, incexp_waychoose.class);
                    intent.putExtra("ModifiedAmount", txt_amount.getText().toString());
                    setResult(RESULT_OK, intent);
                }
                finish();

            }
        };
        Button ok = (Button) findViewById(R.id.popup_modify_amount_btn_ok);
        ok.setOnClickListener(ok_listener);

        Button.OnClickListener cancel_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        Button cancel = (Button) findViewById(R.id.popup_modify_amount_btn_cancel);
        cancel.setOnClickListener(cancel_listener);

    }




}
