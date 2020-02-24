package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by seodongmin on 2017-05-13.
 */

public class game_countdown extends AppCompatActivity {

    int RESULT_SUDDENCLOSE = 8888;
    TextView tv;
    boolean gogame = false;
    ATask at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_calculateit_countdown);

        final Handler mHandler = new Handler();
        tv = (TextView) findViewById(R.id.game_calculateit_lbl_countdown);

        Intent getintent = getIntent();
        //final int gametype = getintent.getIntExtra("gametype", 0);


        at = new ATask();
        at.execute("3", "2", "1", "시작");
       /* new Thread(new Runnable()
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
                    Intent intent;
                    switch(gametype) {
                        case 1:
                            intent = new Intent(game_countdown.this, game_fastmemorize_play.class);
                            break;
                        case 2:
                            intent = new Intent(game_countdown.this, game_color_play.class);
                            break;
                        case 3:
                            intent = new Intent(game_countdown.this, game_rocksipa_play.class);
                            break;
                        default:
                            intent = new Intent();
                            break;
                    }

                    startActivityForResult(intent, RESULT_SUDDENCLOSE);
                    try
                    {
                        Thread.sleep(500);
                    } catch(InterruptedException e) {}
                    finish();
                }
                catch(InterruptedException e) {}
            }
        }).start();*/
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


    public class ATask extends AsyncTask<String, Integer, String> {

        Handler mHandler = new Handler();
        @Override   //메인스레드
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(game_countdown.this, "시작", Toast.LENGTH_SHORT).show();
        }

        @Override   //서브스레드
        protected String doInBackground(String... params) {


            for (int i = 0; i < params.length; i++) {
                try {

                    final String paramcopy = params[i];
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(paramcopy);
                        }
                    });
                    if(i != params.length - 1) {
                        Thread.sleep(800);
                    }
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return "";
                }
            }
            gogame = true;
            try
            {
                Thread.sleep(500);
            } catch(InterruptedException e) { return ""; }
            finish();
            return "끝";
        }

        @Override   //메인스레드
        protected void onPostExecute(String s) { //받아 온 s는 백그라운드에서 실행된 것
            super.onPostExecute(s);
            Intent getintent = getIntent();
            final int gametype = getintent.getIntExtra("gametype", 0);

            Intent intent;
            switch(gametype) {
                case 1:
                    intent = new Intent(game_countdown.this, game_fastmemorize_play.class);
                    break;
                case 2:
                    intent = new Intent(game_countdown.this, game_color_play.class);
                    break;
                case 3:
                    intent = new Intent(game_countdown.this, game_rocksipa_play.class);
                    break;
                case 4:
                    intent = new Intent(game_countdown.this, game_countpeople_play.class);
                    break;
                case 5:
                    intent = new Intent(game_countdown.this, game_avoidpoo_play.class);
                    break;
                default:
                    intent = new Intent();
                    break;
            }
            startActivityForResult(intent, RESULT_SUDDENCLOSE);

            //Toast.makeText(game_countdown.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override   //메인스레드
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
           // Toast.makeText(game_countdown.this, "" + values[0], Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(!gogame) {
            if (at.getStatus() == AsyncTask.Status.RUNNING)
                at.cancel(true);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(at.getStatus() != AsyncTask.Status.RUNNING)
        {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
