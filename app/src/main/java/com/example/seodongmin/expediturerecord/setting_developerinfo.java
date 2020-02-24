package com.example.seodongmin.expediturerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.*;
import android.net.Uri;
import android.content.*;

/**
 * Created by seodongmin on 2017-04-25.
 */

public class setting_developerinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_developerinfo);

        Button.OnClickListener sendmail_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Uri uri;
                uri = Uri.parse("mailto:dkfk2747@gmail.com");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
            }
        };

        Button sendmail = (Button) findViewById(R.id.setting_dinfo_sendmail);
        sendmail.setOnClickListener(sendmail_listener);

        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.setting_dinfo_back);
        back.setOnClickListener(back_listener);

        TextView.OnClickListener easteregg_listener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting_developerinfo.this, game_calculateit_main.class);
                startActivity(intent);
            }
        };

        TextView easteregg = (TextView) findViewById(R.id.setting_dinfo_name);
        easteregg.setOnClickListener(easteregg_listener);
    }
}
