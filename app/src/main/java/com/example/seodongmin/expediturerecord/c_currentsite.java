package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import static com.example.seodongmin.expediturerecord.init.mCurrentsite;


/**
 * Created by seodongmin on 2017-05-22.
 */

public class c_currentsite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentsite);


        Button ok = (Button) findViewById(R.id.popup_currentsite_btn_ok);
        TextView site = (TextView) findViewById(R.id.popup_currentsite_lbl_body);


        site.setText(mCurrentsite);

        Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        ok.setOnClickListener(ok_listener);

    }
}
