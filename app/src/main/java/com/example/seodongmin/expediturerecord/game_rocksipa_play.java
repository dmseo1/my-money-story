package com.example.seodongmin.expediturerecord;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

import static android.speech.SpeechRecognizer.ERROR_NETWORK;
import static android.speech.tts.TextToSpeech.ERROR_NETWORK_TIMEOUT;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.adapter_rocksipa;
import static com.example.seodongmin.expediturerecord.init.fastmemorize;
import static com.example.seodongmin.expediturerecord.init.rocksipa;

/**
 * Created by seodongmin on 2017-05-15.
 */

public class game_rocksipa_play extends AppCompatActivity {

    int RESULT_ISREGISTER_AND_NAME = 8888;
    int RESULT_NOTONLINE = 4444;

    int MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE = 6666;
    int MY_PERMISSIONS_REQUEST_CHANGE_NETWORK_STATE = 7777;
    int MY_PERMISSIONS_REQUEST_INTERNET = 8888;
    int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 9999;

    int count = 0;
    int correct_count = 0;

    Intent recognizerIntent;
    SpeechRecognizer mSpeechRecognizer;

    TextView recogresult;
    Button hiddenButton;

    ArrayList<String> arDump = new ArrayList<>();
    boolean onErrorOnce = true;

    Thread thread_timer;
    Thread thread_game;

    long start_time;
    long end_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rocksipa_play);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(mRecognitionListener);

        thread_game = new Thread();
        thread_timer = new Thread();


        //권한 획득 코드=================================================================================

        if (ContextCompat.checkSelfPermission(game_rocksipa_play.this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_rocksipa_play.this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_rocksipa_play.this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        if (ContextCompat.checkSelfPermission(game_rocksipa_play.this,
                Manifest.permission.CHANGE_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_rocksipa_play.this,
                    Manifest.permission.CHANGE_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_rocksipa_play.this,
                        new String[]{Manifest.permission.CHANGE_NETWORK_STATE},
                        MY_PERMISSIONS_REQUEST_CHANGE_NETWORK_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(game_rocksipa_play.this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_rocksipa_play.this,
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_rocksipa_play.this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        if (ContextCompat.checkSelfPermission(game_rocksipa_play.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(game_rocksipa_play.this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(game_rocksipa_play.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //권한 획득 코드 끝=================================================================================


        ConnectivityManager manager = (ConnectivityManager) game_rocksipa_play.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi.isConnected() || mobile.isConnected()) {


        } else {
            thread_timer.interrupt();
            thread_game.interrupt();
            Intent intent = new Intent(game_rocksipa_play.this, game_notonline.class);
            intent.putExtra("GameNum", 1);
            startActivityForResult(intent, RESULT_NOTONLINE);
            finish();
        }

        //음성 인식을 위한 코드 준비
        recognizerIntent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);


        //기본 뷰 정의
        final TextView timer = (TextView) findViewById(R.id.game_rocksipa_play_lbl_timer);
        timer.setText("0초");
        final TextView status = (TextView) findViewById(R.id.game_rocksipa_play_lbl_status);
        status.setText("정답수/푼문제: " + correct_count + "/" + count);
        final TextView faker = (TextView) findViewById(R.id.game_rocksipa_play_lbl_handname);
        final TextView direction = (TextView) findViewById(R.id.game_rocksipa_play_lbl_direction);
        final ImageView img_ox = (ImageView) findViewById(R.id.game_rocksipa_play_img_ox);
        img_ox.setVisibility(View.INVISIBLE);
        final ImageView img_question = (ImageView) findViewById(R.id.game_rocksipa_play_img_question);
        final TextView notification = (TextView) findViewById(R.id.game_rocksipa_play_lbl_notification);
        notification.setVisibility(View.INVISIBLE);


        //숨김 뷰 정의
        hiddenButton = (Button) findViewById(R.id.game_rocksipa_play_btn_hidden);
        Button.OnClickListener button_listener = new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                mSpeechRecognizer.startListening(recognizerIntent);
                //arDump.clear();
                recogresult.setText("");
            }
        };
        hiddenButton.setOnClickListener(button_listener);


        recogresult = (TextView) findViewById(R.id.game_rocksipa_play_lbl_hidden);

        start_time = System.currentTimeMillis();
        final Handler mHandler = new Handler();


        //시간 스레드
        thread_timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!thread_timer.isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText(String.valueOf((System.currentTimeMillis() - start_time)/1000) + "초");
                        }
                    });
                }
            }

        });
        thread_timer.start();


        //게임 진행 준비물
        final String[] rsp = new String[] {"바위", "가위", "보"};
        final String[] mjb = new String[] {"묵", "찌", "빠"};
        final String[] dir = new String[] {"이기세요", "지세요", "비기세요", "이기지 마세요", "지지 마세요", "비기지 마세요"};


        //게임 진행 스레드
        thread_game = new Thread(new Runnable() {
            @Override
            public void run() {

                while(count < 15 && !thread_game.isInterrupted())
                {
                    Random random = new Random(System.currentTimeMillis());
                    final int num_faker = random.nextInt(3);
                    final int num_direction = random.nextInt(6);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            faker.setText(rsp[num_faker]);
                            direction.setText(dir[num_direction]);
                            switch(num_faker)
                            {
                                case 0:
                                    img_question.setImageDrawable(getDrawable(R.drawable.img_muk));
                                    break;
                                case 1:
                                    img_question.setImageDrawable(getDrawable(R.drawable.img_jji));
                                    break;
                                case 2:
                                    img_question.setImageDrawable(getDrawable(R.drawable.img_bba));
                                    break;
                            }
                        }
                    });


                    while(!thread_game.isInterrupted())
                    {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                hiddenButton.performClick();
                            }
                        });

                        int num = -1;


                        synchronized (game_rocksipa_play.class) {
                            try {
                                System.out.println("wait: 기다림");
                                game_rocksipa_play.class.wait();
                            } catch (InterruptedException e) {
                                return;
                            }
                        }


                        num = Judgement(num_faker, num_direction, recogresult.getText().toString());


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
                }


                end_time = System.currentTimeMillis();
                thread_timer.interrupt();
                Intent intent = new Intent(game_rocksipa_play.this, game_rocksipa_ranking_inputname.class);
                startActivityForResult(intent, RESULT_ISREGISTER_AND_NAME);
            }
        });
        thread_game.start();


        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_result = new Intent(game_rocksipa_play.this, game_countdown.class);
                setResult(RESULT_OK, intent_result);
                thread_timer.interrupt();
                thread_game.interrupt();
                finish();
            }
        };
        ImageView back = (ImageView) findViewById(R.id.game_rocksipa_play_back);
        back.setOnClickListener(back_listener);
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


                    int pos = adapter_rocksipa.addItem_rocksipa(name, correct, playtime, end_time);
                    SharedPreferences.Editor editor = rocksipa.edit();
                    String concat = correct + ":;:" + playtime + ":;:" + end_time + ":;:" + name;

                    int i = pos;
                    String tempstr = "";
                    while(!rocksipa.getString("rankrock_" + i, "").equals("")) // pos 이후로 한칸씩 밀기
                    {
                        tempstr = rocksipa.getString("rankrock_" + (i+1), "");
                        editor.putString("rankrock_" + (i+1), rocksipa.getString("rankrock_" + i, ""));
                        i++;
                    }
                    editor.putString("rankrock_" + pos, concat);
                    editor.commit();
                    Toast.makeText(game_rocksipa_play.this, "기록이 등록되었습니다.", Toast.LENGTH_SHORT).show();
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



    private int Judgement(int handnum, int direction, String key)
    {

        if(key.equals("")) {
            System.out.println("Judgement: 인식실패");
            return 0; //에러 상황 대처
        }

        System.out.println("Judgement(음성인식결과): " + key);

        int MAX = 100;
        String[][] map = new String[][]
                {
                        {"바위", "사위", "까비", "자위", "자 위", "가 위", "다위", "4위", "하이", "바 위", "아 위", "아위", "다윈", "답이"},
                        {"가위", "하위"},
                        {"보", "뽀", "보기", "복"},
                        {"묵", "무", "모", "뭐", "못", "북", "녹", "룩", "응", "늪", "6",},
                        {"찌", "지", "집", "띠", "7", "지", "t", "T", "키", "c", "C", "티", "끼", "씨", "g", "d", "귀", "쥐", "게임", "디"},
                        {"빠", "파", "딱", "바", "마", "빰", "밥", "따", "아", "나", "땅", "딸", "야", "빵", "땀", "박", "빱", "방", "어", "와", "봐", "8"}
                };

        int answer = Answer(handnum, direction);

        //가위, 바위, 보 응답에 대한 오답 처리
        for(int i = 0; i < 3 ; i ++)
        {
            for(int j = 0; j < MAX ; j ++) {
                try {
                    if (key.contains(map[i][j]))
                        return 2;
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
            }
        }

        if(direction < 3)
        {

            //묵, 찌, 빠 - 오답 처리
            for(int i = 3; i < 6; i ++)
            {
                if(i == answer) continue;
                for(int j = 0; j < MAX; j ++)
                {
                    try
                    {
                        if(key.contains(map[i][j]))
                            return 2;
                    } catch(ArrayIndexOutOfBoundsException e) { break; }
                }
            }

            //묵, 찌, 빠 - 정답 처리
            for(int i = 0; i < MAX; i ++)
            {
                try
                {
                    if(key.contains(map[answer][i]))
                        return 1;
                } catch(ArrayIndexOutOfBoundsException e) { break; }
            }
        }
        else
        {
            //묵, 찌, 빠 - 오답 처리
            for(int i = 3; i < 6; i ++)
            {
                if(i != answer) continue;
                for(int j = 0; j < MAX; j ++)
                {
                    try
                    {
                        if(key.contains(map[i][j]))
                            return 2;
                    } catch(ArrayIndexOutOfBoundsException e) { break; }
                }
            }

            //묵, 찌, 빠 - 정답 처리
            for(int i = 3; i < 6; i ++)
            {
                if(i == answer) continue;
                for(int j = 0; j < MAX; j ++)
                {
                    try
                    {
                        if(key.contains(map[i][j]))
                            return 1;
                    } catch(ArrayIndexOutOfBoundsException e) { break; }
                }
            }


        }


        return 0;
    }

    private int Answer(int handnum, int direction)
    {
        switch(handnum)
        {
            case 0:
                switch(direction % 3)
                {
                    case 0:
                        return 5;
                    case 1:
                        return 4;
                    case 2:
                        return 3;
                }
            case 1:
                switch(direction % 3)
                {
                    case 0:
                        return 3;
                    case 1:
                        return 5;
                    case 2:
                        return 4;
                }
            case 2:
                switch(direction % 3)
                {
                    case 0:
                        return 4;
                    case 1:
                        return 3;
                    case 2:
                        return 5;
                }
            default:
                return 0;
        }
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
                Intent intent = new Intent(game_rocksipa_play.this, game_notonline.class);
                intent.putExtra("GameNum", 2);
                startActivityForResult(intent, RESULT_NOTONLINE);
            }


            ConnectivityManager manager = (ConnectivityManager) game_rocksipa_play.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


            // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
            if (wifi.isConnected() || mobile.isConnected()) {


            } else {
                thread_timer.interrupt();
                thread_game.interrupt();
                Intent intent = new Intent(game_rocksipa_play.this, game_notonline.class);
                intent.putExtra("GameNum", 1);
                startActivityForResult(intent, RESULT_NOTONLINE);
                finish();
            }

            synchronized(game_rocksipa_play.class)
            {
                game_rocksipa_play.class.notifyAll();
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

            synchronized(game_rocksipa_play.class)
            {
                game_rocksipa_play.class.notifyAll();
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
