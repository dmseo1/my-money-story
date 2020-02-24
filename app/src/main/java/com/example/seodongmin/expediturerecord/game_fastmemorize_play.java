package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.Arrays;
import java.util.Random;

import static com.example.seodongmin.expediturerecord.init.adapter_calculateit100;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit20;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.calculateit_100;
import static com.example.seodongmin.expediturerecord.init.calculateit_20;
import static com.example.seodongmin.expediturerecord.init.fastmemorize;

/**
 * Created by seodongmin on 2017-05-13.
 */

public class game_fastmemorize_play extends AppCompatActivity {


    int RESULT_ISREGISTER_AND_NAME = 8888;

    int count = 0;
    int count_correct = 0;
    int clickcount = 0;
    int clickcount_correct = 0;
    long start_time;
    long end_time;

    int numof_nums;
    int[] ans;
    int[] sort_ans;

    int[] nums_id;

    Thread thread_timer;
    Thread thread_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fastmemorize_play);


        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_result = new Intent(game_fastmemorize_play.this, game_countdown.class);
                setResult(RESULT_OK, intent_result);
                thread_timer.interrupt();
                finish();
            }
        };
        ImageView back  = (ImageView) findViewById(R.id.game_fastmemorize_play_back);
        back.setOnClickListener(back_listener);

        final ImageView img_ox = (ImageView) findViewById(R.id.game_fastmemorize_play_img_ox);
        img_ox.setVisibility(View.INVISIBLE);

        final TextView status = (TextView) findViewById(R.id.game_fastmemorize_play_lbl_status);
        status.setText("정답수/푼문제: " + count_correct + "/" + count);

        final TextView timer = (TextView) findViewById(R.id.game_fastmemorize_play_lbl_timer);
        timer.setText("0초");

        final TextView[] nums = new TextView[9];
        nums[0] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_1);
        nums[1] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_2);
        nums[2] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_3);
        nums[3] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_4);
        nums[4] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_5);
        nums[5] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_6);
        nums[6] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_7);
        nums[7] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_8);
        nums[8] = (TextView) findViewById(R.id.game_fastmemorize_play_pos_9);

        nums_id = new int[9];
        for(int i = 0; i < 9 ; i ++)
        {
            nums_id[i] = nums[i].getId();
        }


        final TextView.OnClickListener nums_listener_blank = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        final TextView.OnClickListener nums_listener = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = -1;
                for(int i = 0; i < 9 ; i ++)
                {
                    if(nums_id[i] == v.getId())
                    {
                        pos = i;
                        break;
                    }
                }

                if(ans[mapper(numof_nums, pos)] == sort_ans[clickcount])
                {
                    clickcount ++;
                    clickcount_correct ++;
                    ((TextView)v).setText(String.valueOf(ans[mapper(numof_nums, pos)]));
                    v.setOnClickListener(nums_listener_blank);
                    if(clickcount_correct == numof_nums) {
                        for(int i = 0; i < 9 ; i ++) {
                            nums[i].setOnClickListener(nums_listener_blank);
                        }
                        img_ox.setImageDrawable(getDrawable(R.drawable.img_o));
                        img_ox.setVisibility(View.VISIBLE);
                        count++;
                        count_correct ++;

                        synchronized(game_fastmemorize_play.class) {
                            game_fastmemorize_play.class.notifyAll();
                        }
                    }
                }
                else
                {
                    for(int i = 0; i < 9 ; i ++) {
                        nums[i].setOnClickListener(nums_listener_blank);
                    }
                    img_ox.setImageDrawable(getDrawable(R.drawable.img_x));
                    img_ox.setVisibility(View.VISIBLE);
                    count ++;
                    synchronized(game_fastmemorize_play.class) {
                        game_fastmemorize_play.class.notifyAll();
                    }
                }
            }
        };


        //타이머 설정
        start_time = System.currentTimeMillis();
        final Handler mHandler = new Handler();
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

        //게임 진행
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Random random = new Random(System.currentTimeMillis());


                while(count < 10) {


                    System.out.println("1번 탐색");
                    clickcount = 0;
                    clickcount_correct = 0;

                    if(count < 2)
                        numof_nums = 4;
                    else if(count < 4)
                        numof_nums = 5;
                    else if(count < 6)
                        numof_nums = 6;
                    else if(count < 8)
                        numof_nums = 7;
                    else if(count < 10)
                        numof_nums = 9;
                    else
                        numof_nums = 0;

                    System.out.println("2번 탐색");

                    ans = new int[numof_nums];
                    for(int i = 0; i < numof_nums; i ++)
                    {
                        ans[i] = random.nextInt(15);
                        for(int j = 0 ; j < i ; j ++) //중복 방지
                        {
                            if(ans[i] == ans[j]) i --;
                        }
                    }
                    sort_ans = Arrays.copyOf(ans, numof_nums);
                    Arrays.sort(sort_ans);

                    System.out.println("3번 탐색");



                    System.out.println("4번 탐색");

                    mHandler.post(new Runnable()
                    {
                        public void run()
                        {
                            for(int i = 0; i < 9 ; i ++)
                            {
                                nums[i].setVisibility(View.VISIBLE);
                            }

                            switch(numof_nums)
                            {
                                case 4:
                                    nums[0].setText(String.valueOf(ans[0]));
                                    nums[1].setVisibility(View.INVISIBLE);
                                    nums[2].setText(String.valueOf(ans[1]));
                                    nums[3].setVisibility(View.INVISIBLE);
                                    nums[4].setVisibility(View.INVISIBLE);
                                    nums[5].setVisibility(View.INVISIBLE);
                                    nums[6].setText(String.valueOf(ans[2]));
                                    nums[7].setVisibility(View.INVISIBLE);
                                    nums[8].setText(String.valueOf(ans[3]));
                                    break;
                                case 5:
                                    nums[0].setText(String.valueOf(ans[0]));
                                    nums[1].setVisibility(View.INVISIBLE);
                                    nums[2].setText(String.valueOf(ans[1]));
                                    nums[3].setVisibility(View.INVISIBLE);
                                    nums[4].setText(String.valueOf(ans[2]));
                                    nums[5].setVisibility(View.INVISIBLE);
                                    nums[6].setText(String.valueOf(ans[3]));
                                    nums[7].setVisibility(View.INVISIBLE);
                                    nums[8].setText(String.valueOf(ans[4]));
                                    break;
                                case 6:
                                    nums[0].setText(String.valueOf(ans[0]));
                                    nums[1].setVisibility(View.INVISIBLE);
                                    nums[2].setText(String.valueOf(ans[1]));
                                    nums[3].setText(String.valueOf(ans[2]));
                                    nums[4].setVisibility(View.INVISIBLE);
                                    nums[5].setText(String.valueOf(ans[3]));
                                    nums[6].setText(String.valueOf(ans[4]));
                                    nums[7].setVisibility(View.INVISIBLE);
                                    nums[8].setText(String.valueOf(ans[5]));
                                    break;
                                case 7:
                                    nums[0].setText(String.valueOf(ans[0]));
                                    nums[1].setVisibility(View.INVISIBLE);
                                    nums[2].setText(String.valueOf(ans[1]));
                                    nums[3].setText(String.valueOf(ans[2]));
                                    nums[4].setText(String.valueOf(ans[3]));
                                    nums[5].setText(String.valueOf(ans[4]));
                                    nums[6].setText(String.valueOf(ans[5]));
                                    nums[7].setVisibility(View.INVISIBLE);
                                    nums[8].setText(String.valueOf(ans[6]));
                                    break;
                                case 9:
                                    nums[0].setText(String.valueOf(ans[0]));
                                    nums[1].setText(String.valueOf(ans[1]));
                                    nums[2].setText(String.valueOf(ans[2]));
                                    nums[3].setText(String.valueOf(ans[3]));
                                    nums[4].setText(String.valueOf(ans[4]));
                                    nums[5].setText(String.valueOf(ans[5]));
                                    nums[6].setText(String.valueOf(ans[6]));
                                    nums[7].setText(String.valueOf(ans[7]));
                                    nums[8].setText(String.valueOf(ans[8]));
                                    break;
                                default:
                                    return;
                            }
                        }
                    });

                    System.out.println("numofnums: " + numof_nums);
                    System.out.println("5번 탐색");
                    try {
                        Thread.sleep(1000 + ((numof_nums - 4) * 350));
                    } catch(InterruptedException e) {}

                    System.out.println("6번 탐색");


                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            for(int i = 0; i < 9; i ++)
                            {
                                if(nums[i].getVisibility() == View.VISIBLE)
                                {
                                    nums[i].setText("□");
                                    nums[i].setOnClickListener(nums_listener);
                                }
                            }
                        }
                    });

                    System.out.println("7번 탐색");

                    synchronized(game_fastmemorize_play.class)
                    {
                        try {
                            game_fastmemorize_play.class.wait();
                        } catch (InterruptedException e) {
                        }
                    }

                    System.out.println("8번 탐색");



                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            status.setText("정답수/푼문제: " + count_correct + "/" + count);



                        }
                    });

                    try
                    {
                        Thread.sleep(700);
                    } catch(InterruptedException e) {}

                    mHandler.post(new Runnable()
                    {
                        public void run()
                        {
                            img_ox.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                end_time = System.currentTimeMillis();
                thread_timer.interrupt();
                Intent intent = new Intent(game_fastmemorize_play.this, game_fastmemorize_ranking_inputname.class);
                startActivityForResult(intent, RESULT_ISREGISTER_AND_NAME);
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
                    System.out.println("register는 불러지는지 궁금");
                    String name = data.getStringExtra("RankingName");
                    int correct = count_correct;
                    long playtime = (end_time - start_time);


                    int pos = adapter_fastmemorize.addItem_fastmemorize(name, correct, playtime, end_time);
                    SharedPreferences.Editor editor = fastmemorize.edit();
                    String concat = correct + ":;:" + playtime + ":;:" + end_time + ":;:" + name;

                    int i = pos;
                    String tempstr = "";
                    while(!fastmemorize.getString("rankfast_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr = fastmemorize.getString("rankfast_" + (i+1), "");
                        editor.putString("rankfast_" + (i+1), fastmemorize.getString("rankfast_" + i, ""));
                        i++;
                    }
                    editor.putString("rankfast_" + pos, concat);
                    editor.commit();
                    Toast.makeText(game_fastmemorize_play.this, "기록이 등록되었습니다.", Toast.LENGTH_SHORT).show();
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

    public int mapper(int all, int part)
    {
        switch(all)
        {
            case 4:
                switch(part)
                {
                    case 0:
                        return 0;
                    case 2:
                        return 1;
                    case 6:
                        return 2;
                    case 8:
                        return 3;
                }
                break;
            case 5:
                switch(part)
                {
                    case 0:
                        return 0;
                    case 2:
                        return 1;
                    case 4:
                        return 2;
                    case 6:
                        return 3;
                    case 8:
                        return 4;
                }
                break;
            case 6:
                switch(part)
                {
                    case 0:
                        return 0;
                    case 2:
                        return 1;
                    case 3:
                        return 2;
                    case 5:
                        return 3;
                    case 6:
                        return 4;
                    case 8:
                        return 5;
                }
                break;
            case 7:
                switch(part)
                {
                    case 0:
                        return 0;
                    case 2:
                        return 1;
                    case 3:
                        return 2;
                    case 4:
                        return 3;
                    case 5:
                        return 4;
                    case 6:
                        return 5;
                    case 8:
                        return 6;
                }
                break;
            case 9:
                return part;
            default:
                return -1;
        }
        return -1;
    }


}
