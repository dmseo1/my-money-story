package com.example.seodongmin.expediturerecord;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.Random;

import static com.example.seodongmin.expediturerecord.init.adapter_countpeople;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.countpeople;
import static com.example.seodongmin.expediturerecord.init.fastmemorize;

/**
 * Created by seodongmin on 2017-05-18.
 */

public class game_countpeople_play extends AppCompatActivity {

    int RESULT_ISREGISTER_AND_NAME = 8888;
    boolean keypad_exception = false;

    int count = 0;
    int correct_count = 0;
    Thread thread_timer;
    Thread thread_game;
    Thread thread_game_in;
    Thread thread_game_out;
    Thread thread_game_chimney;
    Thread thread_notifier;
    long start_time;
    long end_time;

    int people_init = 0;
    int people_in = 0;
    int people_out = 0;
    int people_chimney_in = 0;
    int people_chimney_out = 0;
    int people_current = 0;

    int complexity = 5;

    int duration = 2000;

    int end_notice = 0;

    ImageView img_initp_1;
    ImageView img_initp_2;
    ImageView img_initp_3;
    ImageView img_initp_4;
    ImageView img_initp_5;
    ImageView img_initp_6;
    ImageView img_initp_7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_countpeople_play);

        thread_timer = new Thread();
        thread_game = new Thread();
        thread_game_in = new Thread();
        thread_game_out = new Thread();
        thread_game_chimney = new Thread();

        //뷰 선언
        final TextView timer = (TextView) findViewById(R.id.game_countpeople_play_lbl_timer);
        timer.setText("0초");
        final TextView status = (TextView) findViewById(R.id.game_countpeople_play_lbl_status);
        status.setText("정답수/푼문제: 0/0");
        final TextView question = (TextView) findViewById(R.id.game_countpeople_play_lbl_question);
        question.setVisibility(View.GONE);
        final EditText answer = (EditText) findViewById(R.id.game_countpeople_play_txt_answer);
        answer.setVisibility(View.GONE);
        final TextView myung = (TextView) findViewById(R.id.game_countpeople_play_lbl_myung);
        myung.setVisibility(View.GONE);
        final Button ok = (Button) findViewById(R.id.game_countpeople_play_btn_ok);
        ok.setVisibility(View.GONE);
        final ImageView img_ox = (ImageView) findViewById(R.id.game_countpeople_play_img_ox);
        img_ox.setVisibility(View.GONE);

        //집과 초기 인원 이미지뷰
        final ImageView img_house = (ImageView) findViewById(R.id.game_countpeople_img_house);
        img_house.setVisibility(View.INVISIBLE);
        img_initp_1 = (ImageView) findViewById(R.id.game_countpeople_play_img_initpeople_1);
        img_initp_2 = (ImageView) findViewById(R.id.game_countpeople_play_img_initpeople_2);
        img_initp_3 = (ImageView) findViewById(R.id.game_countpeople_play_img_initpeople_3);
        img_initp_4 = (ImageView) findViewById(R.id.game_countpeople_play_img_initpeople_4);
        img_initp_5 = (ImageView) findViewById(R.id.game_countpeople_play_img_initpeople_5);
        img_initp_6 = (ImageView) findViewById(R.id.game_countpeople_play_img_initpeople_6);
        img_initp_7 = (ImageView) findViewById(R.id.game_countpeople_play_img_initpeople_7);



        final Button.OnClickListener ok_listener_empty = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        ok.setOnClickListener(ok_listener_empty);





        thread_notifier = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    if (!thread_game_in.isAlive() && !thread_game_out.isAlive() && !thread_game_chimney.isAlive() && end_notice >= 9)
                    {
                        try {
                            synchronized(thread_game) {
                                thread_game.notifyAll();
                            }
                            System.out.println("여기야");
                        } catch(IllegalMonitorStateException e) { return; }
                    }
                }
            }
        });
        //thread_notifier.start();




        final Handler mHandler = new Handler();



        //사람 이미지뷰 정의
        final ImageView[] img_people_in = new ImageView[10];
        final ImageView[] img_people_out = new ImageView[10];
        final ImageView[] img_people_chimney_in = new ImageView[10];
        final ImageView[] img_people_chimney_out = new ImageView[10];

        ConstraintLayout road = (ConstraintLayout) findViewById(R.id.game_countpeople_play_pload);
        ConstraintLayout road_v = (ConstraintLayout) findViewById(R.id.game_countpeople_play_pchimney);
        for(int i = 0; i < 10 ; i ++)
        {
            img_people_in[i] = new ImageView(game_countpeople_play.this);
            img_people_in[i].setImageDrawable(getDrawable(R.drawable.img_man));
            road.addView(img_people_in[i]);
            img_people_in[i].setVisibility(View.INVISIBLE);

            img_people_out[i] = new ImageView(game_countpeople_play.this);
            img_people_out[i].setImageDrawable(getDrawable(R.drawable.img_man));
            road.addView(img_people_out[i]);
            img_people_out[i].setVisibility(View.INVISIBLE);

            img_people_chimney_in[i] = new ImageView(game_countpeople_play.this);
            img_people_chimney_in[i].setImageDrawable(getDrawable(R.drawable.img_man));
            img_people_chimney_in[i].setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 230));
            img_people_chimney_in[i].setScaleType(ImageView.ScaleType.FIT_XY);
            road_v.addView(img_people_chimney_in[i]);
            img_people_chimney_in[i].setVisibility(View.INVISIBLE);

            img_people_chimney_out[i] = new ImageView(game_countpeople_play.this);
            img_people_chimney_out[i].setImageDrawable(getDrawable(R.drawable.img_man));
            img_people_chimney_out[i].setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 230));
            img_people_chimney_out[i].setScaleType(ImageView.ScaleType.FIT_XY);
            road_v.addView(img_people_chimney_out[i]);
            img_people_chimney_out[i].setVisibility(View.INVISIBLE);
        }



        start_time = System.currentTimeMillis();
        thread_timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!thread_timer.isInterrupted())
                {
                    try
                    {
                        Thread.sleep(1000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                timer.setText(String.valueOf((System.currentTimeMillis() - start_time)/1000) + "초");
                            }
                        });
                    } catch (InterruptedException e) { return; }
                }
            }
        });
        thread_timer.start();


        final Button.OnClickListener ok_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(answer.getText().toString().equals(""))
                {
                    Toast.makeText(game_countpeople_play.this, "답을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try
                {
                    Integer.parseInt(answer.getText().toString());
                } catch(NumberFormatException e) {
                    Toast.makeText(game_countpeople_play.this, "정상적인 값을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(answer.getText().toString()) == people_current)
                {
                    img_ox.setImageDrawable(getDrawable(R.drawable.img_o));
                    img_ox.setVisibility(View.VISIBLE);
                    correct_count ++;
                }
                else
                {
                    img_ox.setImageDrawable(getDrawable(R.drawable.img_x));
                    img_ox.setVisibility(View.VISIBLE);
                }

                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                ok.setOnClickListener(ok_listener_empty);
                synchronized(thread_game)
                {
                    thread_game.notifyAll();
                }
            }
        };

        thread_game = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random(System.currentTimeMillis());


                //초기 인원 확인 및 집 낙하

                while(count < 7)
                {
                    people_init = random.nextInt(7) + 1;
                    people_current = people_init;
                    System.out.println("초기인원: " + people_current);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            img_house.setVisibility(View.INVISIBLE);
                        }
                    });

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            initp_viewer(people_init);
                        }
                    });
                    try
                    {
                        Thread.sleep(800);
                    } catch(InterruptedException e) { return; }


                    final TranslateAnimation house_animation = translate_anim_just(0.01f, 0.01f, -0.3f, 0.20f, 1000);
                    img_house.setAnimation(house_animation);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            img_house.startAnimation(house_animation);
                            img_house.setVisibility(View.VISIBLE);
                        }
                    });

                    try
                    {
                        Thread.sleep(2000);
                    } catch(InterruptedException e) { return; }



                    int complex = 0;
                    switch(count)
                    {
                        case 0:
                        case 2:
                        case 5:
                            complexity = 4;
                            break;
                        case 1:
                        case 3:
                        case 4:
                        case 6:
                            complexity = 5;
                            break;
                    }

                    while(complex < complexity) {
                        boolean justin = false;
                        boolean justout = false;
                        if (count < 2) {
                            int justone = random.nextInt(2);
                            if (justone == 0)
                                justin = true;
                            else
                                justout = true;
                        }
                        people_in = random.nextInt(5);

                        if (people_current >= 5) {
                            people_out = random.nextInt(5);
                        } else {
                            people_out = random.nextInt(people_current + 1);
                        }


                        if(justin) people_out = 0;
                        if(justout) people_in = 0;

                        System.out.println("입장: " + people_in);
                        System.out.println("퇴장: " + people_out);

                        //문으로 들어가기
                        thread_game_in = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                for (int i = 0; i < people_in; i++) {
                                    final int ai = i;
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) { return;
                                    }
                                    //final TranslateAnimation trans = translate_anim(-0.2f + (-0.05f) * (float)(i+1-(people_current)), 0.5f, 0f, 0f, duration + (120) * (i-(people_current)));
                                    final TranslateAnimation trans = translate_anim(-0.2f, 0.5f, 0f, 0f, duration);
                                    img_people_in[i].setAnimation(trans);
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            img_people_in[ai].startAnimation(trans);
                                            img_people_in[ai].setVisibility(View.VISIBLE);
                                        }
                                    });

                                }
                            }
                        });
                        if (count < 2 && justout) {
                        } else {
                            thread_game_in.start();
                        }

                        //문으로 나오기
                        thread_game_out = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                for (int i = 0; i < people_out; i++) {
                                    final int ai = i;
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) { return;
                                    }
                                    //final TranslateAnimation trans = translate_anim(0.5f + (-0.05f) * (float)(i+1) , 1.2f, 0f, 0f, duration + (120) * (i));
                                    final TranslateAnimation trans = translate_anim(0.5f, 1.2f, 0f, 0f, duration);
                                    img_people_out[i].setAnimation(trans);
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            img_people_out[ai].startAnimation(trans);
                                            img_people_out[ai].setVisibility(View.VISIBLE);
                                        }
                                    });

                                }
                            }
                        });
                        if (count < 2 && justin) {
                        } else {
                            thread_game_out.start();
                        }


                        //굴뚝 출입
                        thread_game_chimney = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Random random2 = new Random(System.currentTimeMillis());
                                int mode = random2.nextInt(2);

                                switch(mode)
                                {
                                    case 0: //굴뚝을 통해 들어옴
                                        people_chimney_in = random2.nextInt(4);
                                        System.out.println("굴뚝입장: " + people_chimney_in);
                                        for(int i = 0 ; i < people_chimney_in ; i ++)
                                        {

                                            final int ai = i;
                                            try
                                            {
                                                Thread.sleep(350);
                                            } catch(InterruptedException e) { return; }
                                            final TranslateAnimation trans = translate_anim(0f, 0f, -0.1f, 0.6f, duration);
                                            img_people_chimney_in[i].setAnimation(trans);
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {

                                                    img_people_chimney_in[ai].startAnimation(trans);
                                                    img_people_chimney_in[ai].setVisibility(View.VISIBLE);
                                                }
                                            });

                                        }

                                        break;
                                    case 1: //굴뚝을 통해 나감
                                        if(people_current - people_out >= 4)
                                        {
                                            people_chimney_out = random2.nextInt(4);
                                        }
                                        else
                                        {
                                            people_chimney_out = random2.nextInt(people_current - people_out + 1);
                                        }
                                        System.out.println("굴뚝퇴장: " + people_chimney_out);
                                        for(int i = 0; i < people_chimney_out ; i ++)
                                        {
                                            final int ai = i;
                                            try
                                            {
                                                Thread.sleep(350);
                                            } catch(InterruptedException e) { return; }
                                            //final TranslateAnimation trans = translate_anim(0f , 0f, 0.6f + (0.1f) * (float)(i+1-(people_out)), -0.2f, duration + 400 + (360) * (i-(people_out)));
                                            final TranslateAnimation trans = translate_anim(0f, 0f, 0.6f, -0.1f, duration);
                                            img_people_chimney_out[i].setAnimation(trans);
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {

                                                    img_people_chimney_out[ai].startAnimation(trans);
                                                    img_people_chimney_out[ai].setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                        break;
                                }
                            }
                        });
                        if(count >= 5) {
                            thread_game_chimney.start();
                        }

                        while(true) {
                            try
                            {
                                Thread.sleep(500);
                            } catch(InterruptedException e) { return; }
                            if(end_notice >= people_in + people_out + people_chimney_in + people_chimney_out)
                            {
                                end_notice = 0;
                                break;
                            }
                        }





                        people_current = people_current + people_in + people_chimney_in - people_out - people_chimney_out;
                        System.out.println("현재 집에 있는 사람 수: " + people_current);
                        people_in = 0;
                        people_out = 0;
                        people_chimney_in = 0;
                        people_chimney_out = 0;
                        complex++;

                    }


                    //질문창 보이기
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            answer.setText("");
                            question.setVisibility(View.VISIBLE);
                            answer.setVisibility(View.VISIBLE);
                            myung.setVisibility(View.VISIBLE);
                            ok.setVisibility(View.VISIBLE);

                            answer.setFocusableInTouchMode(true);
                            answer.requestFocus();
                        }
                    });
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                    ok.setOnClickListener(ok_listener);
                    //사용자 응답이 있을 때까지 대기
                    synchronized(thread_game) {
                        try {
                            thread_game.wait();
                        } catch (InterruptedException e) { return;
                        }
                    }


                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            question.setVisibility(View.GONE);
                            answer.setVisibility(View.GONE);
                            myung.setVisibility(View.GONE);
                            ok.setVisibility(View.GONE);
                            for(int i = 0; i < 10; i ++)
                            {
                                img_people_in[i].setVisibility(View.INVISIBLE);
                                img_people_out[i].setVisibility(View.INVISIBLE);
                                img_people_chimney_in[i].setVisibility(View.INVISIBLE);
                                img_people_chimney_out[i].setVisibility(View.INVISIBLE);
                            }
                        }
                    });





                    thread_game_in.interrupt();
                    thread_game_out.interrupt();
                    thread_game_chimney.interrupt();

                    count++;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("정답수/푼문제: " + correct_count + "/" + count);
                        }
                    });


                    mHandler.post(new Runnable() { //setFillAfter에 대한 착각에 의한 삽질 - Animation을 한번더 적용해서 아예 안보이는 곳으로 보내버리자.
                        @Override
                        public void run() {

                            TranslateAnimation trans = translate_anim_just(1.0f, 1.0f, 1.0f, 1.0f, 1);
                            end_notice = 0;
                            for(int i = 0; i < 10; i ++)
                            {

                                img_people_in[i].setAnimation(trans);
                                img_people_out[i].setAnimation(trans);
                                img_people_chimney_in[i].setAnimation(trans);
                                img_people_chimney_out[i].setAnimation(trans);

                                img_people_in[i].startAnimation(trans);
                                img_people_out[i].startAnimation(trans);
                                img_people_chimney_in[i].startAnimation(trans);
                                img_people_chimney_out[i].startAnimation(trans);
                            }
                        }
                    });


                    try
                    {
                        Thread.sleep(800);
                    } catch(InterruptedException e) { return; }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            img_house.setVisibility(View.INVISIBLE);
                            img_ox.setVisibility(View.GONE);

                        }
                    });
                }



                end_time = System.currentTimeMillis();
                thread_timer.interrupt();
                thread_game_in.interrupt();
                thread_game_out.interrupt();
                thread_game_chimney.interrupt();
                thread_game.interrupt();
                Intent intent = new Intent(game_countpeople_play.this, game_countpeople_ranking_inputname.class);
                startActivityForResult(intent, RESULT_ISREGISTER_AND_NAME);

            }


        });

        thread_game.start();


        //뒤로가기 버튼
        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread_game.interrupt();
                thread_game_in.interrupt();
                thread_game_out.interrupt();
                thread_game_chimney.interrupt();
                thread_timer.interrupt();
                finish();
            }
        };

        ImageView back = (ImageView) findViewById(R.id.game_countpeople_play_back);
        back.setOnClickListener(back_listener);
    }

    @Override
    public void onResume()
    {
        super.onResume();
 /*       if(!keypad_exception) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        if(keypad_exception)
        {
            keypad_exception = false;
        }*/
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(!keypad_exception) {
            InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
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
                    int correct = correct_count;
                    long playtime = (end_time - start_time);


                    int pos = adapter_countpeople.addItem_countpeople(name, correct, playtime, end_time);
                    SharedPreferences.Editor editor = countpeople.edit();
                    String concat = correct + ":;:" + playtime + ":;:" + end_time + ":;:" + name;

                    int i = pos;
                    String tempstr = "";
                    while(!countpeople.getString("rankcount_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr = countpeople.getString("rankcount_" + (i+1), "");
                        editor.putString("rankcount_" + (i+1), countpeople.getString("rankcount_" + i, ""));
                        i++;
                    }
                    editor.putString("rankcount_" + pos, concat);
                    editor.commit();
                    Toast.makeText(game_countpeople_play.this, "기록이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }

                finish();
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }


    TranslateAnimation translate_anim(float fromX, float toX, float fromY, float toY, int duration) {

        final TranslateAnimation in_anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, fromX, Animation.RELATIVE_TO_PARENT, toX,
                Animation.RELATIVE_TO_PARENT, fromY, Animation.RELATIVE_TO_PARENT, toY);
        in_anim.setFillAfter(true);
        in_anim.setDuration(duration);
        Animation.AnimationListener listen = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    end_notice ++;
                    //System.out.println("end_notice가 증가- 현재: " + end_notice);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            };
        in_anim.setAnimationListener(listen);
        return in_anim;
    }



    TranslateAnimation translate_anim_just(float fromX, float toX, float fromY, float toY, int duration) {

        final TranslateAnimation in_anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, fromX, Animation.RELATIVE_TO_PARENT, toX,
                Animation.RELATIVE_TO_PARENT, fromY, Animation.RELATIVE_TO_PARENT, toY);
        in_anim.setFillAfter(true);
        in_anim.setDuration(duration);
        Animation.AnimationListener listen = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        in_anim.setAnimationListener(listen);
        return in_anim;
    }

    public void initp_viewer(int num)
    {
        switch(num)
        {
            case 1:
                img_initp_1.setVisibility(View.INVISIBLE);
                img_initp_2.setVisibility(View.INVISIBLE);
                img_initp_3.setVisibility(View.INVISIBLE);
                img_initp_4.setVisibility(View.VISIBLE);
                img_initp_5.setVisibility(View.INVISIBLE);
                img_initp_6.setVisibility(View.INVISIBLE);
                img_initp_7.setVisibility(View.INVISIBLE);
                break;
            case 2:
                img_initp_1.setVisibility(View.INVISIBLE);
                img_initp_2.setVisibility(View.INVISIBLE);
                img_initp_3.setVisibility(View.VISIBLE);
                img_initp_4.setVisibility(View.INVISIBLE);
                img_initp_5.setVisibility(View.VISIBLE);
                img_initp_6.setVisibility(View.INVISIBLE);
                img_initp_7.setVisibility(View.INVISIBLE);
                break;
            case 3:
                img_initp_1.setVisibility(View.INVISIBLE);
                img_initp_2.setVisibility(View.INVISIBLE);
                img_initp_3.setVisibility(View.VISIBLE);
                img_initp_4.setVisibility(View.VISIBLE);
                img_initp_5.setVisibility(View.VISIBLE);
                img_initp_6.setVisibility(View.INVISIBLE);
                img_initp_7.setVisibility(View.INVISIBLE);
                break;
            case 4:
                img_initp_1.setVisibility(View.INVISIBLE);
                img_initp_2.setVisibility(View.VISIBLE);
                img_initp_3.setVisibility(View.VISIBLE);
                img_initp_4.setVisibility(View.INVISIBLE);
                img_initp_5.setVisibility(View.VISIBLE);
                img_initp_6.setVisibility(View.VISIBLE);
                img_initp_7.setVisibility(View.INVISIBLE);
                break;
            case 5:
                img_initp_1.setVisibility(View.INVISIBLE);
                img_initp_2.setVisibility(View.VISIBLE);
                img_initp_3.setVisibility(View.VISIBLE);
                img_initp_4.setVisibility(View.VISIBLE);
                img_initp_5.setVisibility(View.VISIBLE);
                img_initp_6.setVisibility(View.VISIBLE);
                img_initp_7.setVisibility(View.INVISIBLE);
                break;
            case 6:
                img_initp_1.setVisibility(View.VISIBLE);
                img_initp_2.setVisibility(View.VISIBLE);
                img_initp_3.setVisibility(View.VISIBLE);
                img_initp_4.setVisibility(View.INVISIBLE);
                img_initp_5.setVisibility(View.VISIBLE);
                img_initp_6.setVisibility(View.VISIBLE);
                img_initp_7.setVisibility(View.VISIBLE);
                break;
            case 7:
                img_initp_1.setVisibility(View.VISIBLE);
                img_initp_2.setVisibility(View.VISIBLE);
                img_initp_3.setVisibility(View.VISIBLE);
                img_initp_4.setVisibility(View.VISIBLE);
                img_initp_5.setVisibility(View.VISIBLE);
                img_initp_6.setVisibility(View.VISIBLE);
                img_initp_7.setVisibility(View.VISIBLE);
                break;
            default:
                System.out.println("예기치 못한 오류가 발생하였습니다.");
                break;
        }
    }
}
