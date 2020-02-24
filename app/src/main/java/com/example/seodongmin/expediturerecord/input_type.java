package com.example.seodongmin.expediturerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.content.*;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by seodongmin on 2017-04-20.
 */

public class input_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_type);

        //수입
        OnClickListener listener_input_income = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(input_type.this, "수입 내역을 입력하세요", android.widget.Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(input_type.this, input_income.class);
                startActivity(intent);
            }
        };

        Button income = (Button) findViewById(R.id.input_type_btn_income);
        income.setOnClickListener(listener_input_income);


        //지출
        OnClickListener listener_input_expenditure = new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(input_type.this, "지출 내역을 입력하세요", android.widget.Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(input_type.this, input_expenditure.class);
                startActivity(intent);
            }
        };

        Button expenditure = (Button) findViewById(R.id.input_type_btn_expenditure);
        expenditure.setOnClickListener(listener_input_expenditure);


        //뒤로가기
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                finish();

            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.type_img_back);

        back.setOnClickListener(back_listener);
    }
}
