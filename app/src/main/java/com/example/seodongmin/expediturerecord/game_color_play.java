package com.example.seodongmin.expediturerecord;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

import static android.speech.SpeechRecognizer.ERROR_NETWORK;
import static android.speech.tts.TextToSpeech.ERROR_NETWORK_TIMEOUT;
import static com.example.seodongmin.expediturerecord.init.adapter_color;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.color;
import static com.example.seodongmin.expediturerecord.init.fastmemorize;

/**
 * Created by seodongmin on 2017-05-14.
 */

public class game_color_play extends AppCompatActivity {

    int RESULT_ISREGISTER_AND_NAME = 8888;
    int RESULT_NOTONLINE = 4444;

    int MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE = 6666;
    int MY_PERMISSIONS_REQUEST_CHANGE_NETWORK_STATE = 7777;
    int MY_PERMISSIONS_REQUEST_INTERNET = 8888;
    int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 9999;

    ArrayList<String> arDump = new ArrayList<String>();

    Intent recognizerIntent;
    SpeechRecognizer mSpeechRecognizer;


    TextView status;
    TextView timer;
    TextView question;
    TextView recogresult;
    ImageView img_ox;
    TextView notification;

    Button hiddenButton;

    int count = 0;
    int correct_count = 0;

    long start_time;
    long end_time;

    Thread thread_timer;
    Thread thread_game;

    boolean onErrorOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_color_play);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(mRecognitionListener);

        thread_game = new Thread();
        thread_timer = new Thread();


        //권한 획득 코드=================================================================================
        if (ContextCompat.checkSelfPermission(game_color_play.this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_color_play.this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_color_play.this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        if (ContextCompat.checkSelfPermission(game_color_play.this,
                Manifest.permission.CHANGE_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_color_play.this,
                    Manifest.permission.CHANGE_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_color_play.this,
                        new String[]{Manifest.permission.CHANGE_NETWORK_STATE},
                        MY_PERMISSIONS_REQUEST_CHANGE_NETWORK_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }



        if (ContextCompat.checkSelfPermission(game_color_play.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_color_play.this,
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_color_play.this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        if (ContextCompat.checkSelfPermission(game_color_play.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_color_play.this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_color_play.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //권한 획득 코드 끝=================================================================================


        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi.isConnected() || mobile.isConnected()) {


        } else {
            thread_timer.interrupt();
            thread_game.interrupt();
            Intent intent = new Intent(game_color_play.this, game_notonline.class);
            intent.putExtra("GameNum", 1);
            startActivityForResult(intent, RESULT_NOTONLINE);
            finish();
        }

        //음성 인식을 위한 코드 준비
        recognizerIntent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);


        //기본 데이터 정의
        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_result = new Intent(game_color_play.this, game_countdown.class);
                setResult(RESULT_OK, intent_result);
                thread_game.interrupt();
                thread_timer.interrupt();
                finish();
            }
        };
        ImageView back = (ImageView) findViewById(R.id.game_color_play_back);
        back.setOnClickListener(back_listener);

        status = (TextView) findViewById(R.id.game_color_play_lbl_status);
        status.setText("정답수/푼문제: " + correct_count + "/" + count);
        timer = (TextView) findViewById(R.id.game_color_play_lbl_time);
        timer.setText("0초");
        question = (TextView) findViewById(R.id.game_color_play_lbl_question);
        recogresult = (TextView) findViewById(R.id.game_color_play_lbl_answer_hidden);
        hiddenButton = (Button) findViewById(R.id.game_color_play_btn_answer_hidden);
        img_ox = (ImageView) findViewById(R.id.game_color_play_img_ox);
        img_ox.setVisibility(View.INVISIBLE);
        notification = (TextView) findViewById(R.id.game_color_play_lbl_notification);
        notification.setVisibility(View.INVISIBLE);


        Button.OnClickListener button_listener = new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                mSpeechRecognizer.startListening(recognizerIntent);
                //arDump.clear();
                recogresult.setText("");
            }
        };
        hiddenButton.setOnClickListener(button_listener);

        final int[] colorarr = new int[5];
        colorarr[0] = Color.parseColor("#000000"); //검정
        colorarr[1] = Color.parseColor("#ffff00"); //노랑
        colorarr[2] = Color.parseColor("#ff0000"); //빨강
        colorarr[3] = Color.parseColor("#0000ff"); //파랑
        colorarr[4] = Color.parseColor("#00ff00"); //초록

        final String[] colorname = new String[] {"검정", "노랑", "빨강", "파랑", "초록"};

        final Handler mHandler = new Handler();


        start_time = System.currentTimeMillis();
        //시간 변경 스레드
        thread_timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(1000);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                timer.setText(String.valueOf((System.currentTimeMillis() - start_time) / 1000) + "초");
                            }
                        });
                    }
                } catch(InterruptedException e) { return; }
            }
        });

        thread_timer.start();




        //게임 진행 스레드
        thread_game = new Thread(new Runnable() {
            @Override
            public void run() {

                    while (count < 15 && !thread_game.isInterrupted()) {
                      //  try {
                        Random random = new Random(System.currentTimeMillis());
                        final int ans_color = random.nextInt(5);
                        final int fakename = random.nextInt(5);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                question.setTextColor(colorarr[ans_color]);
                                question.setText(colorname[fakename]);
                            }
                        });

                        while (!thread_game.isInterrupted()) {

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    hiddenButton.performClick();
                                }
                            });

                            int num = -1;


                            synchronized (game_color_play.class) {
                                try {
                                    System.out.println("wait: 기다림");
                                    game_color_play.class.wait();
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }

                            num = Judgement(ans_color, recogresult.getText().toString());

                            System.out.println("num: " + num);

                            if (num == 1) {
                                count++;
                                correct_count++;
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        img_ox.setImageDrawable(getDrawable(R.drawable.img_o));
                                        img_ox.setVisibility(View.VISIBLE);
                                        status.setText("정답수/푼문제: " + correct_count + "/" + count);
                                    }
                                });

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    return;
                                }
                                break;
                            } else if (num == 2) {
                                count++;
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        img_ox.setImageDrawable(getDrawable(R.drawable.img_x));
                                        img_ox.setVisibility(View.VISIBLE);
                                        status.setText("정답수/푼문제: " + correct_count + "/" + count);
                                    }
                                });

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    return;
                                }
                                break;
                            } else if (num == 0) {
                                if (onErrorOnce) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            notification.setText("인식 실패. 다시 시도.");
                                            notification.setVisibility(View.VISIBLE);

                                        }
                                    });

                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                    }

                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            notification.setVisibility(View.INVISIBLE);
                                        }
                                    });


                                    System.out.println("인식 실패");
                                    onErrorOnce = false;
                                }
                            }

                        }

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                img_ox.setVisibility(View.INVISIBLE);
                                notification.setVisibility(View.INVISIBLE);
                            }
                        });

                      //  } catch(InterruptedException e) { return; }

                    }

                end_time = System.currentTimeMillis();
                thread_timer.interrupt();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(game_color_play.this, "정답수: " + (correct_count) + ", " + ((float)(end_time - start_time) / (float)1000) + "초 소요되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });


                Intent intent = new Intent(game_color_play.this, game_color_ranking_inputname.class);
                startActivityForResult(intent, RESULT_ISREGISTER_AND_NAME);
            }
        });
        thread_game.start();
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(mRecognitionListener);
    }



    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //mSpeechRecognizer.destroy();
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
                    int correct = correct_count;
                    long playtime = (end_time - start_time);


                    int pos = adapter_color.addItem_color(name, correct, playtime, end_time);
                    SharedPreferences.Editor editor = color.edit();
                    String concat = correct + ":;:" + playtime + ":;:" + end_time + ":;:" + name;

                    int i = pos;
                    String tempstr = "";
                    while(!color.getString("rankcolor_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr = color.getString("rankcolor_" + (i+1), "");
                        editor.putString("rankcolor_" + (i+1), color.getString("rankcolor_" + i, ""));
                        i++;
                    }
                    editor.putString("rankcolor_" + pos, concat);
                    editor.commit();
                    Toast.makeText(game_color_play.this, "기록이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }

                finish();
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

        if(requestCode == RESULT_NOTONLINE && resultCode == RESULT_OK)
        {
            finish();
        }
    }



    int Judgement(int colornum, String key)
    {

        if(key.equals("")) {
            System.out.println("Judgement: 인식실패");
            return 0; //에러 상황 대처
        }

        System.out.println("Judgement(음성인식결과): " + key);

        int MAX = 100;
        String[][] map = new String[][]
                {
                        {"검정", "검 정", "금정", "금 정", "검사", "음성", "검색", "엄", "함", "검", "컴", "검증", "감정", "감상", "강정", "은정", "삼성", "이상범", "남정", "음정", "음 성", "감성"},
                        {"노랑", "노란", "노 랑", "노 란", "의왕", "너란", "로랑", "오랑", "11", "누랑", "모", "뭐", "노량", "모랑", "놀아", "노망", "너랑", "노", "머랑", "나랑", "누구랑"},
                        {"빨강", "빨간", "빨 간", "빨 강", "애니팡", "매장", "메롱", "아니 강", "하이 방", "아이 삼", "나일 강", "나의 당", "1강", "마이 갓", "나의 등", "알람", "달 감", "r 강", "달감", "알 간", "일강", "알간", "8강", "딸 감", "딸감", "맑음", "빨", "쌀", "말", "배당", "달", "발", "백암", "i3", "아리랑", "딸", "나의 방", "아이가", "알"},
                        {"파랑", "파란", "바란", "팔아", "파 랑", "가랑", "다람", "바 란", "파 란", "사람", "사 람", "다른", "다랑", "바랑", "바람", "하랑", "화랑", "자", "차", "파", "카", "아랑", "타", "사랑"},
                        {"초록", "도록", "초 록", "추석", "촬영", "부럽", "처럼", "천호", "충북", "도 록", "초롱", "소름", "손 욱", "손욱", "풀업", "설악", "틀어", "불혹", "도둑", "서랍", "졸업", "소독", "토론", "졸음", "철학", "서로", "줘", "쳐", "도로", "초", "토", "사동", "록", "선옥", "저도", "소득", "천 옥", "천옥", "서로", "프로", "탈옥", "옥", "트럭", "포도", "저녁"}
                };

        for(int i = 0; i < 5; i ++)
        {
            if(i == colornum) continue;
            for(int j = 0; j < MAX; j ++)
            {
                try {
                    if(key.contains(map[i][j]))
                        return 2;
                } catch(ArrayIndexOutOfBoundsException e) { break; }
            }
        }

        for(int i = 0; i < MAX; i ++)
        {
            try {
                if (key.contains(map[colornum][i]))
                    return 1;
            } catch(ArrayIndexOutOfBoundsException e) { break; }
        }



        return 0;
    }





    RecognitionListener mRecognitionListener = new RecognitionListener() {

        @Override
        public void onBeginningOfSpeech() {
            // TODO Auto-generated method stub
            AppendText("onBeginningOfSpeech");
            System.out.println("onBeginningOfSpeech");

        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // TODO Auto-generated method stub
            AppendText("onBufferReceived "+buffer[0]);
            System.out.println("onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            // TODO Auto-generated method stub
            AppendText("onEndOfSpeech");
            System.out.println("onEndOfSpeech");

        }


        @Override
        public void onError(int error) {
            // TODO Auto-generated method stub
            AppendText("on‌Error "+error);
            System.out.println("onError");
            onErrorOnce = true;

            if(error == ERROR_NETWORK_TIMEOUT || error == ERROR_NETWORK)
            {
                thread_timer.interrupt();
                thread_game.interrupt();
                Intent intent = new Intent(game_color_play.this, game_notonline.class);
                intent.putExtra("GameNum", 1);
                startActivityForResult(intent, RESULT_NOTONLINE);
            }

            ConnectivityManager manager = (ConnectivityManager) game_color_play.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


            // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
            if (wifi.isConnected() || mobile.isConnected()) {


            } else {
                thread_timer.interrupt();
                thread_game.interrupt();
                Intent intent = new Intent(game_color_play.this, game_notonline.class);
                intent.putExtra("GameNum", 1);
                startActivityForResult(intent, RESULT_NOTONLINE);
                finish();
            }

            synchronized(game_color_play.class)
            {
                game_color_play.class.notifyAll();
            }

        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // TODO Auto-generated method stub
            AppendText("onEvent");
            System.out.println("onEvent");

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // TODO Auto-generated method stub
            AppendText("onPartialResults");
            System.out.println("onPartialResults");

        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            // TODO Auto-generated method stub
            AppendText("onReadyForSpeech");
            System.out.println("onReadyForSpeech");

        }

        @SuppressWarnings("unchecked")
        @Override
        public void onResults(Bundle results) {
            // TODO Auto-generated method stub
            AppendText("onResults");
            System.out.println("onResults");
            recogresult.setText("");
            ArrayList<String> rsts = (ArrayList<String>) results.get(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i=0;i<rsts.size();i++)
                recogresult.setText(rsts.get(i)+"\n");

            synchronized(game_color_play.class)
            {
                    game_color_play.class.notifyAll();
            }
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // TODO Auto-generated method stub
            //System.out.println("onRmsChanged");
        }
    };

    void AppendText(String text) {
        if(arDump.size()>17)
            arDump.remove(0);
        arDump.add(text);

        StringBuilder result = new StringBuilder();
        for(String s : arDump)
            result.append(s+"\n");

        //statusView.setText(result.toString());
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
