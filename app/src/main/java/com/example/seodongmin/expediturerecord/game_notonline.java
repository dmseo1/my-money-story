package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

/**
 * Created by seodongmin on 2017-05-15.
 */

public class game_notonline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_notonline);

        Intent get_intent = getIntent();
        final int game_num = get_intent.getIntExtra("GameNum", 0);

        Button ok = (Button) findViewById(R.id.popup_game_notonline_btn_ok);
        Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent result_intent;
                switch(game_num) {
                    case 1:
                        result_intent = new Intent(game_notonline.this, game_color_play.class);
                        setResult(RESULT_OK, result_intent);
                        finish();

                    case 2:
                        result_intent = new Intent(game_notonline.this, game_rocksipa_play.class);
                        setResult(RESULT_OK, result_intent);
                        finish();

                    default:
                        finish();
                }

            }
        };
        ok.setOnClickListener(ok_listener);


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
