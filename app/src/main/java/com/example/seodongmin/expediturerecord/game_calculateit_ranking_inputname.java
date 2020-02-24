package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.*;

/**
 * Created by seodongmin on 2017-05-08.
 */

public class game_calculateit_ranking_inputname extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_calculateit_ranking_inputname);

        final EditText name = (EditText) findViewById(R.id.popup_rankingname_txt);
        Button ok = (Button) findViewById(R.id.popup_rankingname_btn_ok);
        Button cancel = (Button) findViewById(R.id.popup_rankingname_btn_cancel);

        Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result_intent = new Intent(game_calculateit_ranking_inputname.this, game_calculateit_play.class);
                result_intent.putExtra("isRegister", true);
                result_intent.putExtra("RankingName", name.getText().toString());
                setResult(RESULT_OK, result_intent);
                finish();
            }
        };
        ok.setOnClickListener(ok_listener);

        Button.OnClickListener cancel_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result_intent = new Intent(game_calculateit_ranking_inputname.this, game_calculateit_play.class);
                result_intent.putExtra("isRegister", false);
                setResult(RESULT_OK, result_intent);
                finish();
            }
        };
        cancel.setOnClickListener(cancel_listener);
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
