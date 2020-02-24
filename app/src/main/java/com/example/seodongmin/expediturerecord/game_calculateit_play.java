package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.util.Random;

import static com.example.seodongmin.expediturerecord.init.adapter_calculateit100;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit20;
import static com.example.seodongmin.expediturerecord.init.calculateit_100;
import static com.example.seodongmin.expediturerecord.init.calculateit_20;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;

/**
 * Created by seodongmin on 2017-05-08.
 */

public class game_calculateit_play extends AppCompatActivity {

    int RESULT_ISREGISTER_AND_NAME = 8888;
    boolean keypad_exception = false;
    long start_time = 0;
    long end_time = 0;
    int howmany = 0;
    int pcount = 0;
    int pcorrect = 0;
    int current_answer = 0;
    int save_answer = 0;
    int save_answer_next = 0;

    Thread thread_ox_viewer;
    Thread thread_wrong_1second;
    Thread timer;
    TextView currentp;
    TextView nextp;
    TextView chknumcorrect;
    TextView pasttime;
    EditText inputp;
    ImageView img_ox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_calculateit_play);

        final Intent getintent = getIntent();
        howmany = getintent.getIntExtra("howmany", 0);

        currentp = (TextView) findViewById(R.id.game_calculateit_play_currentp);
        nextp = (TextView) findViewById(R.id.game_calculateit_play_nextp);
        chknumcorrect = (TextView) findViewById(R.id.game_calculateit_play_chknumcorrect);
        pasttime = (TextView) findViewById(R.id.game_calculateit_play_lbl_time);
        inputp = (EditText) findViewById(R.id.game_calculateit_play_inputp);
        img_ox = (ImageView) findViewById(R.id.game_calculateit_play_img_ox);
        img_ox.setVisibility(View.INVISIBLE);

        thread_wrong_1second = new Thread();
        thread_ox_viewer = new Thread();
        pasttime.setText("0초");
        inputp.addTextChangedListener
                (new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                        thread_wrong_1second.interrupt();

                        if(s.length() == String.valueOf(save_answer).length())
                        {
                            if(inputp.getText().toString().equals(String.valueOf(save_answer)))
                            {
                                if(pcount == howmany - 1)
                                {
                                    end_time = System.currentTimeMillis();
                                    timer.interrupt();
                                    InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                    immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                                    Intent intent_namepopup = new Intent(game_calculateit_play.this, game_calculateit_ranking_inputname.class);
                                    startActivityForResult(intent_namepopup, RESULT_ISREGISTER_AND_NAME);


                                    Toast.makeText(game_calculateit_play.this, "정답수: " + (pcorrect+1) + ", " + ((float)(end_time - start_time) / (float)1000) + "초 소요되었습니다.", Toast.LENGTH_SHORT).show();

                                }
                                pcount ++;
                                pcorrect ++;
                                keypad_exception = true;

                                final Handler mHandler = new Handler();
                                if(thread_ox_viewer.isAlive()) thread_ox_viewer.interrupt();
                                thread_ox_viewer = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                img_ox.setImageDrawable(getDrawable(R.drawable.img_o));
                                                img_ox.setVisibility(View.VISIBLE);
                                            }
                                        });

                                        try
                                        {
                                            Thread.sleep(500);
                                        } catch(InterruptedException e) { return; }

                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                img_ox.setVisibility(View.INVISIBLE);
                                            }
                                        });

                                    }
                                });
                                thread_ox_viewer.start();

                                Intent intent = new Intent(game_calculateit_play.this, nowsearching.class);
                                startActivity(intent);
                            }
                            else
                            {
                                final Handler mHandler = new Handler();
                                thread_wrong_1second = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try
                                        {
                                            Thread.sleep(1000);
                                        } catch(InterruptedException e) { return;  }

                                        if(pcount == howmany - 1)
                                        {
                                            end_time = System.currentTimeMillis();
                                            timer.interrupt();
                                            InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                            immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                                            Intent intent_namepopup = new Intent(game_calculateit_play.this, game_calculateit_ranking_inputname.class);
                                            startActivityForResult(intent_namepopup, RESULT_ISREGISTER_AND_NAME);

                                            Toast.makeText(game_calculateit_play.this, "정답수: " + pcorrect + ", " + ((float)(end_time - start_time) / (float)1000) + "초 소요되었습니다.", Toast.LENGTH_SHORT).show();

                                        }
                                        pcount ++;
                                        keypad_exception = true;





                                        if(thread_ox_viewer.isAlive()) thread_ox_viewer.interrupt();
                                        thread_ox_viewer = new Thread(new Runnable() {

                                            @Override
                                            public void run() {
                                                mHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        img_ox.setImageDrawable(getDrawable(R.drawable.img_x));
                                                        img_ox.setVisibility(View.VISIBLE);
                                                    }
                                                });

                                                try
                                                {
                                                    Thread.sleep(500);
                                                } catch(InterruptedException e) { return; }

                                                mHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        img_ox.setVisibility(View.INVISIBLE);
                                                    }
                                                });

                                            }
                                        });
                                        thread_ox_viewer.start();



                                        Intent intent = new Intent(game_calculateit_play.this, nowsearching.class);
                                        startActivity(intent);
                                    }
                                });
                                thread_wrong_1second.start();

                            }

                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                }
        );

        start_time = System.currentTimeMillis();
        final Handler mHandler = new Handler();
        timer = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true) {
                    try {
                        Thread.sleep(1000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pasttime.setText(String.valueOf((System.currentTimeMillis() - start_time) / 1000) + "초");
                            }
                        });
                    } catch (InterruptedException e) { return; }

                }
            }

        });
        timer.start();

        ImageView back = (ImageView) findViewById(R.id.game_calculateit_play_back);
        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_result = new Intent(game_calculateit_play.this, game_calculateit_countdown.class);
                setResult(RESULT_OK, intent_result);
                Intent intent_result2 = new Intent(game_calculateit_play.this, game_howto_calculateit.class);
                setResult(RESULT_OK, intent_result2);
                finish();
            }
        };
        back.setOnClickListener(back_listener);

    }


    @Override
    public void onResume() {
        super.onResume();
        Random random = new Random(System.currentTimeMillis());
        int operation = random.nextInt(4) + 1;
        int first;
        int second;
        int answer;


        if(!keypad_exception) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        if(keypad_exception)
        {
            keypad_exception = false;
        }


        chknumcorrect.setText("정답수/푼문제: " + pcorrect + " / " + pcount);
        inputp.setText("");
        if(pcount == 0) { //최초 세팅
            switch (operation) {
                case 1:
                    first = random.nextInt(30) + 1;
                    second = random.nextInt(30) + 1;
                    save_answer = first + second;
                    currentp.setText(first + "＋" + second + "＝");
                    break;
                case 2:
                    first = random.nextInt(30) + 1;
                    second = random.nextInt(first);
                    save_answer = first - second;
                    currentp.setText(first + "－" + second + "＝");
                    break;
                case 3:
                    first = random.nextInt(12) + 1;
                    second = random.nextInt(9);
                    save_answer = first * second;
                    currentp.setText(first + "×" + second + "＝");
                    break;
                case 4:
                    second = random.nextInt(10) + 1;
                    save_answer = random.nextInt(12);
                    first = second * save_answer;
                    currentp.setText(first + "÷" + second + "＝");
                    break;
                default:
                    break;
            }
        }

        if(pcount >= 1)
        {
            currentp.setText(nextp.getText().toString());
            save_answer = save_answer_next;
        }

        if(pcount != howmany - 1) {
            switch (operation) {
                case 1:
                    first = random.nextInt(30) + 1;
                    second = random.nextInt(30) + 1;
                    save_answer_next = first + second;
                    nextp.setText(first + "＋" + second + "＝");
                    break;
                case 2:
                    first = random.nextInt(30) + 1;
                    second = random.nextInt(first);
                    save_answer_next = first - second;
                    nextp.setText(first + "－" + second + "＝");
                    break;
                case 3:
                    first = random.nextInt(12) + 1;
                    second = random.nextInt(9);
                    save_answer_next = first * second;
                    nextp.setText(first + "×" + second + "＝");
                    break;
                case 4:
                    second = random.nextInt(10) + 1;
                    save_answer_next = random.nextInt(12);
                    first = second * save_answer_next;
                    nextp.setText(first + "÷" + second + "＝");
                    break;
                default:
                    break;
            }
        }
        if(pcount == howmany - 1)
        {
            nextp.setText("끝");
        }

    }

    public void onPause()
    {
        super.onPause();
        if(!keypad_exception) {
            InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
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
                    System.out.println("register는 불러지는지 궁금");
                    String name = data.getStringExtra("RankingName");
                    int correct = pcorrect;
                    long playtime = (end_time - start_time);


                    if(howmany == 20) {
                        int pos = adapter_calculateit20.addItem(20, name, correct, playtime, end_time);
                        SharedPreferences.Editor editor_20 = calculateit_20.edit();
                        String concat_20 = correct + ":;:" + playtime + ":;:" + end_time + ":;:" + name;

                        int i = pos;
                        String tempstr = "";
                        while(!calculateit_20.getString("rank20_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                        {
                            tempstr = calculateit_20.getString("rank20_" + (i+1), "");
                            editor_20.putString("rank20_" + (i+1), calculateit_20.getString("rank20_" + i, ""));
                            i++;
                        }
                        editor_20.putString("rank20_" + pos, concat_20);
                        editor_20.commit();
                        Toast.makeText(game_calculateit_play.this, "기록이 등록되었습니다.", Toast.LENGTH_SHORT).show();


                    }
                    else if(howmany == 100)
                    {
                        int pos = adapter_calculateit100.addItem(100, name, correct, playtime, end_time);

                        SharedPreferences.Editor editor_100 = calculateit_100.edit();
                        String concat_100 = correct + ":;:" + playtime + ":;:" + end_time + ":;:" + name;

                        int i = pos;
                        String tempstr = "";
                        while(!calculateit_100.getString("rank100_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                        {
                            tempstr = calculateit_100.getString("rank100_" + (i+1), "");
                            editor_100.putString("rank100_" + (i+1), calculateit_100.getString("rank100_" + i, ""));
                            i++;
                        }
                        editor_100.putString("rank100_" + pos, concat_100);
                        editor_100.commit();
                        Toast.makeText(game_calculateit_play.this, "기록이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        System.out.println("예기치 않은 오류가 발생하였습니다.");
                    }

                    finish();
                }

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
