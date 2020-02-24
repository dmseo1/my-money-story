package com.example.seodongmin.expediturerecord;

import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.app.*;
import android.content.*;

/**
 * Created by seodongmin on 2017-04-20.
 */

public class setting extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);






        Button.OnClickListener add_inc_where_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting.this, setting_inc.class);
                startActivity(intent);
            }

        };

        Button.OnClickListener add_exp_where_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting.this, setting_exc.class);
                startActivity(intent);
            }

        };

        Button.OnClickListener add_exp_way_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting.this, setting_exw.class);
                startActivity(intent);
            }

        };

        Button.OnClickListener targetex_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, setting_targetex.class);
                startActivity(intent);
            }
        };

        Button.OnClickListener developerinfo_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting.this, setting_developerinfo.class);
                startActivity(intent);
            }
        };

        Button.OnClickListener appinfo_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting.this, setting_appinfo.class);
                startActivity(intent);
            }
        };

        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.setting_img_back);
        back.setOnClickListener(back_listener);

        Button add_inc_where = (Button) findViewById(R.id.setting_add_inc_where);
        add_inc_where.setOnClickListener(add_inc_where_listener);

        Button add_exp_where = (Button) findViewById(R.id.setting_add_exp_where);
        add_exp_where.setOnClickListener(add_exp_where_listener);

        Button add_exp_way = (Button) findViewById(R.id.setting_add_exp_way);
        add_exp_way.setOnClickListener(add_exp_way_listener);


        Button developerinfo = (Button) findViewById(R.id.setting_devloperinfo);
        developerinfo.setOnClickListener(developerinfo_listener);

        Button appinfo = (Button) findViewById(R.id.setting_appinfo);
        appinfo.setOnClickListener(appinfo_listener);

    }

    @Override
    public void onResume()
    {
        super.onResume();

        final SharedPreferences sf = getSharedPreferences("File_Settings", MODE_PRIVATE);
        final boolean is_pw = sf.getBoolean("isPassword", false);
        Button bt_pw = (Button) findViewById(R.id.setting_password);

        if(is_pw)
        {
            bt_pw.setText("비밀번호 변경/제거");
        }
        else
        {
            bt_pw.setText("비밀번호 생성");
        }

        Button.OnClickListener password_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(is_pw) {

                    android.content.Intent intent = new android.content.Intent(setting.this, setting_modify_password.class);
                    startActivity(intent);
                }
                else
                {
                    android.content.Intent intent = new android.content.Intent(setting.this, setting_password.class);
                    startActivity(intent);
                }
            }

        };

        Button password = (Button) findViewById(R.id.setting_password);
        password.setOnClickListener(password_listener);


    }
}
