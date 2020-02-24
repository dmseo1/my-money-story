package com.example.seodongmin.expediturerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.textservice.TextInfo;
import android.widget.*;
import android.content.*;
import android.view.*;

/**
 * Created by seodongmin on 2017-04-25.
 */

public class setting_appinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_appinfo);

       /* //게임으로 연결되는 Easter Egg~
        TextView.OnClickListener txt_listener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting_appinfo.this, game_calculateit_main.class);
                startActivity(intent);
            }
        };

        TextView txt = (TextView) findViewById(R.id.setting_appinfo_version);
        txt.setOnClickListener(txt_listener);*/


        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }

        };

        ImageButton back = (ImageButton) findViewById(R.id.setting_appinfo_back);
        back.setOnClickListener(back_listener);
    }
}
