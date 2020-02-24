package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.*;

/**
 * Created by seodongmin on 2017-05-21.
 */

public class game_howto_fastmemorize extends AppCompatActivity {

    int RESULT_START = 8888;
    Thread thread_explanation;
    Thread thread_rotatestart;
    int motion = 0;
    int gametype;

    int startbutton_rotate_progress = 0;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_howto_fastmemorize);

        thread_rotatestart = new Thread();

        Intent getintent = getIntent();
        gametype = getintent.getIntExtra("gametype", 0);

        final TextView[] pos = new TextView[4];
        pos[0] = (TextView) findViewById(R.id.game_howto_fast_pos1);
        pos[1] = (TextView) findViewById(R.id.game_howto_fast_pos2);
        pos[2] = (TextView) findViewById(R.id.game_howto_fast_pos3);
        pos[3] = (TextView) findViewById(R.id.game_howto_fast_pos4);
        ImageView back = (ImageView) findViewById(R.id.game_howto_fast_back);
        final ImageView ox = (ImageView) findViewById(R.id.game_howto_fast_img_ox);
        ox.setVisibility(View.GONE);
        final ImageView finger = (ImageView) findViewById(R.id.game_howto_fast_img_finger);
        start = (Button) findViewById(R.id.game_howto_fast_btn_start);

        Button.OnClickListener start_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread_explanation.interrupt();
                thread_rotatestart.interrupt();
                Intent intent = new Intent(game_howto_fastmemorize.this, game_countdown.class);
                intent.putExtra("gametype", 1);
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




        thread_explanation = new Thread();





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
                while(true) {
                    motion = 0;
                    try {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ox.setVisibility(View.INVISIBLE);
                                finger.setVisibility(View.INVISIBLE);
                            }
                        });
                        Thread.sleep(2500);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                for(int i = 0 ; i < 4 ; i++)
                                {
                                    pos[i].setText("□");
                                }
                            }
                        });
                        Thread.sleep(500);

                        //첫 모션
                        final TranslateAnimation trans1 = translate_anim(0.5f, 0.2f, 0.5f, 0.22f, 700);
                        finger.setAnimation(trans1);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                finger.startAnimation(trans1);
                                finger.setVisibility(View.VISIBLE);
                            }
                        });
                        while(true)
                        {
                            try {
                                Thread.sleep(140);
                                if(motion == 1) break;
                            } catch(InterruptedException e) { return; }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pos[0].setText("1");
                            }
                        });
                        Thread.sleep(500);



                        //둘 모션
                        final TranslateAnimation trans2 = translate_anim(0.2f, 0.8f, 0.22f, 0.53f, 1400);
                        finger.setAnimation(trans1);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                finger.startAnimation(trans2);
                            }
                        });
                        while(true)
                        {
                            try {
                                Thread.sleep(350);
                                if(motion == 2) break;
                            } catch(InterruptedException e) { return; }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pos[3].setText("2");
                            }
                        });
                        Thread.sleep(500);




                        //셋 모션
                        final TranslateAnimation trans3 = translate_anim(0.8f, 0.2f, 0.53f, 0.53f, 1000);
                        finger.setAnimation(trans3);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                finger.startAnimation(trans3);
                            }
                        });
                        while(true)
                        {
                            try {
                                Thread.sleep(200);
                                if(motion == 3) break;
                            } catch(InterruptedException e) { return; }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pos[2].setText("3");
                            }
                        });
                        Thread.sleep(500);


                        //넷 모션
                        final TranslateAnimation trans4 = translate_anim(0.2f, 0.8f, 0.53f, 0.22f, 1400);
                        finger.setAnimation(trans4);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                finger.startAnimation(trans4);
                            }
                        });
                        while(true)
                        {
                            try {
                                Thread.sleep(350);
                                if(motion == 4) break;
                            } catch(InterruptedException e) { return; }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pos[1].setText("4");
                                ox.setVisibility(View.VISIBLE);
                            }
                        });

                        Thread.sleep(500);


                    } catch (InterruptedException e) {
                        return;
                    }
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

    @Override
    public void onBackPressed()
    {
        return;
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
