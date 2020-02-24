package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by seodongmin on 2017-05-21.
 */

public class game_howto_avoidpoo extends AppCompatActivity {

    int RESULT_START = 8888;

    Thread thread_rotatestart;

    int startbutton_rotate_progress = 0;
    Button start;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_howto_avoidpoo);

        thread_rotatestart = new Thread();

        start = (Button) findViewById(R.id.game_howto_poop_button_start);
        Button.OnClickListener start_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread_rotatestart.interrupt();
                Intent intent = new Intent(game_howto_avoidpoo.this, game_countdown.class);
                intent.putExtra("gametype", 5);
                startActivityForResult(intent, RESULT_START);
            }
        };
        start.setOnClickListener(start_listener);


        ImageView back = (ImageView) findViewById(R.id.game_howto_poop_back);
        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
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
