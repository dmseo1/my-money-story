package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.*;

/**
 * Created by seodongmin on 2017-05-21.
 */

public class game_howto_countpeople extends AppCompatActivity {

    int RESULT_START = 8888;
    Thread thread_explanation;
    Thread thread_rotatestart;
    int motion = 0;

    int startbutton_rotate_progress = 0;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_howto_countpeople);

        thread_explanation = new Thread();
        thread_rotatestart = new Thread();

        ConstraintLayout road = (ConstraintLayout) findViewById(R.id.game_howto_peop_road);
        final ImageView[] people = new ImageView[5];
        for(int i = 0; i < 5; i ++)
        {
            people[i] = new ImageView(game_howto_countpeople.this);
            people[i].setImageDrawable(getDrawable(R.drawable.img_man));
            people[i].setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            road.addView(people[i]);
            people[i].setVisibility(View.INVISIBLE);
        }




        start = (Button) findViewById(R.id.game_howto_peop_btn_start);
        ImageView back = (ImageView) findViewById(R.id.game_howto_peop_back);

        Button.OnClickListener start_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread_explanation.interrupt();
                thread_rotatestart.interrupt();
                Intent intent = new Intent(game_howto_countpeople.this, game_countdown.class);
                intent.putExtra("gametype", 4);
                startActivityForResult(intent, RESULT_START);
            }
        };
        start.setOnClickListener(start_listener);

        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread_explanation.interrupt();
                thread_rotatestart.interrupt();
                finish();
            }
        };
        back.setOnClickListener(back_listener);

        final Handler mHandler = new Handler();

        thread_rotatestart = new Thread(new Runnable() {
            @Override
            public void run() {
                ButtonSalangSalang(mHandler);
            }
        });

        thread_rotatestart.start();

        thread_explanation = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    motion = 0;
                    try
                    {

                        Thread.sleep(500);

                        for (int i = 0; i < 2; i++) {
                            final int ai = i;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) { return;
                            }
                             final TranslateAnimation trans = translate_anim(-0.2f, 0.5f, 0f, 0f, 2000);
                            people[i].setAnimation(trans);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    people[ai].startAnimation(trans);
                                    people[ai].setVisibility(View.VISIBLE);
                                }
                            });

                        }

                        Thread.sleep(2500);


                        for (int i = 2; i < 5; i++) {
                            final int ai = i;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) { return;
                            }
                                final TranslateAnimation trans = translate_anim(0.5f, 1.2f, 0f, 0f, 2000);
                            people[i].setAnimation(trans);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    people[ai].startAnimation(trans);
                                    people[ai].setVisibility(View.VISIBLE);
                                }
                            });

                        }

                        Thread.sleep(2500);


                    } catch(InterruptedException e) { return; }
                }
            }
        });

        thread_explanation.start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


        finish();
        if(requestCode == RESULT_START) //리퀘스트 코드를 성공적으로 받았다
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

                motion ++;
                //System.out.println("end_notice가 증가- 현재: " + end_notice);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        in_anim.setAnimationListener(listen);
        return in_anim;
    }


    public RotateAnimation rotate_anim(float fromD, float toD, float pivotX, float pivotY, long duration)
    {
        RotateAnimation rotate = new RotateAnimation(fromD, toD, pivotX, pivotY);
        rotate.setFillAfter(true);
        rotate.setDuration(duration);

        Animation.AnimationListener rotate_listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startbutton_rotate_progress ++;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        rotate.setAnimationListener(rotate_listener);

        return rotate;

    }

    public void ButtonSalangSalang(Handler mHandler)
    {
        while(true)
        {
            startbutton_rotate_progress = 0;
            try
            {
                Thread.sleep(5000);
                final RotateAnimation rotate = rotate_anim(0, 10, start.getWidth()/2, start.getHeight()/2, 150);
                start.setAnimation(rotate);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        start.startAnimation(rotate);
                    }
                });
                while(true)
                {
                    Thread.sleep(16);
                    if(startbutton_rotate_progress == 1) break;
                }


                final RotateAnimation rotate2 = rotate_anim(10, -10, start.getWidth()/2, start.getHeight()/2, 300);
                start.setAnimation(rotate2);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        start.startAnimation(rotate2);
                    }
                });
                while(true)
                {
                    Thread.sleep(16);
                    if(startbutton_rotate_progress == 2) break;
                }


                final RotateAnimation rotate3 = rotate_anim(-10, 10, start.getWidth()/2, start.getHeight()/2, 300);
                start.setAnimation(rotate3);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        start.startAnimation(rotate3);
                    }
                });
                while(true)
                {
                    Thread.sleep(16);
                    if(startbutton_rotate_progress == 3) break;
                }


                final RotateAnimation rotate4 = rotate_anim(10, 0, start.getWidth()/2, start.getHeight()/2, 150);
                start.setAnimation(rotate4);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        start.startAnimation(rotate4);
                    }
                });

            } catch(InterruptedException e) { return;}
        }


    }

}
