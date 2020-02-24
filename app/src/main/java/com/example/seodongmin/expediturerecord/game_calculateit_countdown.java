package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by seodongmin on 2017-05-13.
 */

public class game_calculateit_countdown extends AppCompatActivity {

    boolean gogame = false;
    int RESULT_SUDDENCLOSE = 8888;
    Thread thread_countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_calculateit_countdown);

        final Handler mHandler = new Handler();
        final TextView tv = (TextView) findViewById(R.id.game_calculateit_lbl_countdown);

        Intent getintent = getIntent();
        final int howmany = getintent.getIntExtra("howmany", 0);
        thread_countdown = new Thread();

        thread_countdown = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tv.setText("3");
                        }
                    });
                    Thread.sleep(800);
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tv.setText("2");
                        }
                    });
                    Thread.sleep(800);
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tv.setText("1");
                        }
                    });
                    Thread.sleep(800);
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tv.setText("시작");
                        }
                    });
                    gogame = true;
                    Intent intent = new Intent(game_calculateit_countdown.this, game_calculateit_play.class);
                    intent.putExtra("howmany", howmany);
                    startActivityForResult(intent, RESULT_SUDDENCLOSE);
                    try
                    {
                        Thread.sleep(500);
                    } catch(InterruptedException e)  { return; }
                    finish();
                }
                catch(InterruptedException e) { return; }
            }
        });
        thread_countdown.start();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        thread_countdown.interrupt();
        finish();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_SUDDENCLOSE) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                finish();
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
