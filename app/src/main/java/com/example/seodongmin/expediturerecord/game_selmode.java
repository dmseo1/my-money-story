package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

/**
 * Created by seodongmin on 2017-05-08.
 */

public class game_selmode extends AppCompatActivity {

    Button twenty;
    Button hundred;
    Button fastmemorize;
    Button color;
    Button rocksipa;
    Button countpeople;
    Button avoidpoo;
    Switch helpswitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selmode);

        final SharedPreferences sf = getSharedPreferences("Game_Help", MODE_PRIVATE);
        final SharedPreferences.Editor sf_editor = sf.edit();



        twenty = (Button) findViewById(R.id.game_calculateit_selmode_btn_20);
        hundred = (Button) findViewById(R.id.game_calculateit_selmode_btn_100);
        fastmemorize = (Button) findViewById(R.id.game_fastmemorize_selmode_btn);
        color = (Button) findViewById(R.id.game_color_selmode_btn);
        rocksipa = (Button) findViewById(R.id.game_rocksipa_selmode_btn);
        countpeople = (Button) findViewById(R.id.game_countpeople_selmode_btn);
        avoidpoo = (Button) findViewById(R.id.game_avoidpoo_selmode_btn);
        helpswitch = (Switch) findViewById(R.id.game_selmode_helpswitch);

        helpswitch.setChecked(sf.getBoolean("HelpOn", true));

        Switch.OnCheckedChangeListener helpswitch_listener = new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    sf_editor.putBoolean("HelpOn", true);
                    sf_editor.commit();

                }
                else
                {
                    sf_editor.putBoolean("HelpOn", false);
                    sf_editor.commit();
                }
            }
        };
        helpswitch.setOnCheckedChangeListener(helpswitch_listener);

        Button.OnClickListener twenty_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpswitch.isChecked())
                {
                    Intent intent = new Intent(game_selmode.this, game_howto_calculateit.class);
                    intent.putExtra("howmany", 20);
                    startActivity(intent);
                } else
                {
                    Intent intent = new Intent(game_selmode.this, game_calculateit_countdown.class);
                    intent.putExtra("howmany", 20);
                    startActivity(intent);
                }
            }
        };

        Button.OnClickListener hundred_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpswitch.isChecked())
                {
                    Intent intent = new Intent(game_selmode.this, game_howto_calculateit.class);
                    intent.putExtra("howmany", 100);
                    startActivity(intent);
                } else
                {
                    Intent intent = new Intent(game_selmode.this, game_calculateit_countdown.class);
                    intent.putExtra("howmany", 100);
                    startActivity(intent);
                }

            }
        };

        Button.OnClickListener fastmemorize_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(helpswitch.isChecked())
                {
                    Intent intent = new Intent(game_selmode.this, game_howto_fastmemorize.class);
                    intent.putExtra("gametype", 1);
                    startActivity(intent);
                } else
                {
                    Intent intent = new Intent(game_selmode.this, game_countdown.class);
                    intent.putExtra("gametype", 1);
                    startActivity(intent);
                }

            }
        };

        Button.OnClickListener color_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpswitch.isChecked())
                {
                    Intent intent = new Intent(game_selmode.this, game_howto_color.class);
                    intent.putExtra("gametype", 2);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(game_selmode.this, game_countdown.class);
                    intent.putExtra("gametype", 2);
                    startActivity(intent);
                }

            }
        };

        Button.OnClickListener rocksipa_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(helpswitch.isChecked())
                {
                    Intent intent = new Intent(game_selmode.this, game_howto_rocksipa.class);
                    intent.putExtra("gametype", 3);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(game_selmode.this, game_countdown.class);
                    intent.putExtra("gametype", 3);
                    startActivity(intent);
                }
            }
        };

        Button.OnClickListener countpeople_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(helpswitch.isChecked())
                {
                    Intent intent = new Intent(game_selmode.this, game_howto_countpeople.class);
                    intent.putExtra("gametype", 4);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(game_selmode.this, game_countdown.class);
                    intent.putExtra("gametype", 4);
                    startActivity(intent);
                }

            }
        };

        Button.OnClickListener avoidpoo_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helpswitch.isChecked())
                {
                    Intent intent = new Intent(game_selmode.this, game_howto_avoidpoo.class);
                    intent.putExtra("gametype", 5);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(game_selmode.this, game_countdown.class);
                    intent.putExtra("gametype", 5);
                    startActivity(intent);
                }

            }
        };

        twenty.setOnClickListener(twenty_listener);
        hundred.setOnClickListener(hundred_listener);
        fastmemorize.setOnClickListener(fastmemorize_listener);
        color.setOnClickListener(color_listener);
        rocksipa.setOnClickListener(rocksipa_listener);
        countpeople.setOnClickListener(countpeople_listener);
        avoidpoo.setOnClickListener(avoidpoo_listener);

        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        ImageView back = (ImageView) findViewById(R.id.game_calculateit_selmode_back);
        back.setOnClickListener(back_listener);
    }
}
