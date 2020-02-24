package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import static com.example.seodongmin.expediturerecord.AvoidPooView.AvoidPoo_isOver;
import static com.example.seodongmin.expediturerecord.AvoidPooView.AvoidPoo_score;
import static com.example.seodongmin.expediturerecord.init.adapter_avoidpoo;
import static com.example.seodongmin.expediturerecord.init.avoidpoo;

/**
 * Created by seodongmin on 2017-05-20.
 */

public class game_avoidpoo_play extends AppCompatActivity {


    int RESULT_ISREGISTER_AND_NAME = 8888;
    long end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game_avoidpoo_play);

        FrameLayout fr = (FrameLayout) findViewById(R.id.game_avoidpoo_play_main);
        AvoidPooView apv = new AvoidPooView(this);
        fr.addView(apv);

        final Handler mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                 try
                 {
                     while(!AvoidPoo_isOver) {
                         Thread.sleep(1500);
                     }
                     end_time = System.currentTimeMillis();
                     mHandler.post(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(game_avoidpoo_play.this, "획득한 점수: " + AvoidPoo_score, Toast.LENGTH_SHORT).show();
                         }
                     });
                     Intent intent = new Intent(game_avoidpoo_play.this, game_avoidpoo_ranking_inputname.class);
                     startActivityForResult(intent, RESULT_ISREGISTER_AND_NAME);
                     AvoidPoo_isOver = false;


                 } catch(InterruptedException e) {}
            }
        }).start();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_ISREGISTER_AND_NAME) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getBooleanExtra("isRegister", false))
                {
                    String name = data.getStringExtra("RankingName");
                    long score = AvoidPoo_score;


                    int pos = adapter_avoidpoo.addItem_avoidpoo(name, AvoidPoo_score, end_time);
                    SharedPreferences.Editor editor = avoidpoo.edit();
                    String concat = ":;:" + score + ":;:" + end_time + ":;:" + name;

                    int i = pos;
                    String tempstr = "";
                    while(!avoidpoo.getString("rankpoo_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr = avoidpoo.getString("rankpoo_" + (i+1), "");
                        editor.putString("rankpoo_" + (i+1), avoidpoo.getString("rankpoo_" + i, ""));
                        i++;
                    }
                    editor.putString("rankpoo_" + pos, concat);
                    editor.commit();
                    Toast.makeText(game_avoidpoo_play.this, "기록이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }

                finish();
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }
    }

}
