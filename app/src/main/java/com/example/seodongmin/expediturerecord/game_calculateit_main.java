package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by seodongmin on 2017-05-08.
 */

public class game_calculateit_main extends AppCompatActivity {

    Button start;
    Button ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_calculateit_main);

        start = (Button) findViewById(R.id.game_calculateit_btn_start);
        ranking = (Button) findViewById(R.id.game_calculateit_btn_ranking);

        Button.OnClickListener start_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(game_calculateit_main.this, game_selmode.class);
                startActivity(intent);

            }
        };
        start.setOnClickListener(start_listener);

        Button.OnClickListener ranking_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(game_calculateit_main.this, game_ranking.class);
                startActivity(intent);

            }
        };
        ranking.setOnClickListener(ranking_listener);


        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        ImageView back = (ImageView) findViewById(R.id.game_calculateit_main_back);
        back.setOnClickListener(back_listener);

    }
}
