package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;

/**
 * Created by seodongmin on 2017-05-21.
 */

public class game_howto_color extends AppCompatActivity {

    int RESULT_START = 8888;
    Thread thread_explanation;
    Thread thread_rotatestart;
    int gametype;

    int startbutton_rotate_progress = 0;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_howto_color);

        thread_rotatestart = new Thread();

        Intent getintent = getIntent();
        gametype = getintent.getIntExtra("gametype", 0);
        thread_explanation = new Thread();

        final TextView question = (TextView) findViewById(R.id.game_howto_colo_lbl_question);
        final TextView answer = (TextView) findViewById(R.id.game_howto_colo_lbl_answer);
        answer.setVisibility(View.INVISIBLE);
        final ImageView ox = (ImageView) findViewById(R.id.game_howto_colo_img_ox);
        ox.setVisibility(View.INVISIBLE);
        final ImageView speechbubble = (ImageView) findViewById(R.id.game_howto_colo_img_speechbubble);
        speechbubble.setVisibility(View.INVISIBLE);

        start = (Button) findViewById(R.id.game_howto_colo_btn_start);
        ImageView back = (ImageView) findViewById(R.id.game_howto_colo_back);

        Button.OnClickListener start_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread_explanation.interrupt();
                thread_rotatestart.interrupt();
                Intent intent = new Intent(game_howto_color.this, game_countdown.class);
                intent.putExtra("gametype", gametype);
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
                    try{

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ox.setVisibility(View.INVISIBLE);
                                speechbubble.setVisibility(View.INVISIBLE);
                                answer.setVisibility(View.INVISIBLE);
                                question.setTextColor(Color.parseColor("#000000"));
                                question.setText("파랑");
                            }});
                        Thread.sleep(1000);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                speechbubble.setVisibility(View.VISIBLE);
                                answer.setVisibility(View.VISIBLE);
                                answer.setText("검정");
                            }
                        });

                        Thread.sleep(1500);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ox.setImageDrawable(getDrawable(R.drawable.img_o));
                                ox.setVisibility(View.VISIBLE);
                            }
                        });

                        Thread.sleep(500);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ox.setVisibility(View.INVISIBLE);
                                speechbubble.setVisibility(View.INVISIBLE);
                                answer.setVisibility(View.INVISIBLE);
                                question.setTextColor(Color.parseColor("#ff0000"));
                                question.setText("노랑");
                            }
                        });

                        Thread.sleep(1000);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                speechbubble.setVisibility(View.VISIBLE);
                                answer.setVisibility(View.VISIBLE);
                                answer.setText("노랑");
                            }
                        });

                        Thread.sleep(1500);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ox.setImageDrawable(getDrawable(R.drawable.img_x));
                                ox.setVisibility(View.VISIBLE);
                            }
                        });

                        Thread.sleep(500);

                    } catch(InterruptedException e) {}
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
