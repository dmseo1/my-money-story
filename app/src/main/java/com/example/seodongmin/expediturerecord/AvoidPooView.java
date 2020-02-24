package com.example.seodongmin.expediturerecord;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by seodongmin on 2017-05-20.
 */

public class AvoidPooView extends SurfaceView implements SurfaceHolder.Callback {

    static boolean AvoidPoo_isOver = false;
    static long AvoidPoo_score = 0;
    Resources res;
    GameThread mThread;
    int Width, Height;
    Bitmap imgPoo, imgPooHard, imgPooGolden, imgPooSulsa, imgJewelry, imgCharacter;
    int life = 10;
    int difficulty = 3;
    Context context;
    Canvas canvas;
    boolean canMove = true;
    boolean startedOnce = false;
    int pWidth, pHeight, pcWidth, pcHeight; //똥 상수값
    int charWidth, charHeight, charcWidth, charcHeight; //캐릭터 상수값
    int charX, charY; // 캐릭터 위치값

    boolean intermediateStarted = false;
    boolean advancedStarted = false;

    Thread sulsaThread;

    public AvoidPooView(Context context)
    {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        this.context = context;



        Display display = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        Width = p.x;
        Height = p.y;






        imgPoo = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_poo);
        imgPoo = Bitmap.createScaledBitmap(imgPoo, 96, 96, true);
        imgPooHard = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_poo_hard);
        imgPooHard = Bitmap.createScaledBitmap(imgPooHard, 96, 96, true);
        imgPooGolden = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_poo_golden);
        imgPooGolden = Bitmap.createScaledBitmap(imgPooGolden, 96, 96, true);
        imgPooSulsa = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_poo_sulsa);
        imgPooSulsa = Bitmap.createScaledBitmap(imgPooSulsa, 96, 96, true);
        imgJewelry = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_jewelry);
        imgJewelry = Bitmap.createScaledBitmap(imgJewelry, 96, 96, true);
        imgCharacter = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_lion);
        imgCharacter = Bitmap.createScaledBitmap(imgCharacter, 120, 120, true);

        charWidth = imgCharacter.getWidth();
        charHeight = imgCharacter.getHeight();
        charcWidth = charWidth / 2;
        charcHeight = charHeight / 2;

        charX = (Width/2) - charcWidth;
        charY = Height - charcHeight;

        res = context.getResources();
        mThread = new GameThread(context);
        sulsaThread = new Thread();


    }


    @Override
    public void surfaceCreated(SurfaceHolder arg0)
    {
        if(!startedOnce) {
            mThread.start();
            startedOnce = true;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0)
    {
        boolean done = true;
        while(done)
        {
            mThread.interrupt();
            done = false;
           /* System.out.println("여기가 불러져?");
            try {
                mThread.join();
                done = false;
            } catch(InterruptedException e) {}*/
        } //while
    }




    class GameThread extends Thread {


        SurfaceHolder mHolder;

        float[] x, y;
        long[] count;
        boolean[] chkBottom;
        Rect src_character = new Rect();
        Rect dst_character = new Rect();
        ArrayList<PooElement> Poos = new ArrayList<>();


        public GameThread(Context context)
        {
            SurfaceHolder holder = getHolder();
            holder.addCallback(AvoidPooView.this);
            mHolder = holder;
            x = new float[100];
            y = new float[100];
            count = new long[100];
            chkBottom = new boolean[100];
            Arrays.fill(count, 0);
            Arrays.fill(chkBottom, false);

            pWidth = imgPoo.getWidth();
            pHeight = imgPoo.getHeight();
            pcWidth = pWidth / 2;
            pcHeight = pHeight / 2;
      ;
        }


        @Override
        public void run()
        {
            AvoidPoo_score = 0;
            AvoidPoo_isOver = false;

            //까먹기 전에 적어두는 중요한 사실
            //src는 이미지의 처음부터 끝까지 중 어디를 표시할 것인가
            //dst는 어느 사각형에 표시할 것인가를 나타내는 것이다.
            Rect src = new Rect();



            src.set(0, 0, pWidth, pHeight);
            src_character.set(0,0,120,120);
            final Random random = new Random(System.currentTimeMillis());

            Thread[] PooThread = new Thread[6];


            PooThread[0] = new Thread(new PooRunnable(Poos, 400, difficulty));
            PooThread[0].start();
            PooThread[1] = new Thread(new PooRunnable(Poos, 600, difficulty));
            PooThread[1].start();
            PooThread[2] = new Thread(new PooRunnable(Poos, 800, difficulty));
            PooThread[2].start();
            PooThread[3] = new Thread(new PooRunnable(Poos, 1000, difficulty));
            PooThread[3].start();
            PooThread[4] = new Thread(new PooRunnable(Poos, 630, difficulty));
            PooThread[5] = new Thread(new PooRunnable(Poos, 660, difficulty));



            //똥과 캐릭터의 생애
            while(life > 0)
            {
                //난이도 변경
                if(!advancedStarted && AvoidPoo_score >= 12000) {

                    PooThread[5].start();
                    difficulty = 1;
                    advancedStarted = true;

                }else if(!intermediateStarted && AvoidPoo_score >= 5000) {

                    PooThread[4].start();
                    difficulty = 2;
                    intermediateStarted = true;
                }

                try {
                    canvas = mHolder.lockCanvas();
                   // canvas.drawRGB(255,255,255);
                    switch(difficulty) //난이도에 따른 배경색 조정
                    {
                        case 3:
                            canvas.drawRGB(149,241,158);
                            break;
                        case 2:
                        canvas.drawRGB(237,242,165);
                            break;
                        case 1:
                            canvas.drawRGB(237,162,158);
                            break;
                    }

                    try {
                        synchronized(mHolder)
                        {
                            //캐릭터 그리기
                            dst_character.set(charX - charcWidth, charY - charcHeight, charX + charcWidth, charY + charcHeight);
                            canvas.drawBitmap(imgCharacter, src_character, dst_character, null);




                            //생명 그리기
                            Paint rp = new Paint();
                            Paint p = new Paint();
                            p.setTextSize(60);


                            if(life >= 7)
                            {
                                rp.setColor(Color.argb(255, 36, 133, 10));
                            }
                            else if(life >= 4)
                            {
                                rp.setColor(Color.argb(255, 195, 180, 14));
                            }
                            else
                            {
                                rp.setColor(Color.argb(255, 195, 35, 14));
                            }

                            rp.setStyle(Paint.Style.FILL);
                            canvas.drawText("LIFE:", (Width / 2) - 90, 70, p);
                            canvas.drawRect(new Rect((Width/2) + 50, 20, (Width / 2) + 50 + (life * 40), 80), rp);

                            //똥 그리기
                            for(int i = 0; i < Poos.size() ; i ++)
                            {
                                canvas.drawBitmap(getPooImage(Poos.get(i).type), src, Poos.get(i).getRect(), null);
                            }


                            //점수 그리기
                            canvas.drawText("Score: " + AvoidPoo_score, 10, 70, p);
                        }
                    } finally {
                        mHolder.unlockCanvasAndPost(canvas);
                    }

                   switch(difficulty)
                   {
                       case 3:
                           Thread.sleep(25);
                           break;
                       case 2:
                           Thread.sleep(18);
                           break;
                       case 1:
                           Thread.sleep(10);
                           break;
                   }

                    for(int i = 0; i < Poos.size(); i ++)
                    {
                        if(imgMove(Poos.get(i), difficulty) == 1) {
                            Poos.remove(i);
                        }
                        if(Poos.get(i).isArrivedAtBottom()) Poos.remove(i);
                    }
                    if(difficulty == 1)
                    {
                        AvoidPoo_score += 2;
                    }
                    else {
                        AvoidPoo_score++;
                    }
                } catch(InterruptedException e) {return; }
            }

            AvoidPoo_isOver = true;

        } //run



        private Bitmap getPooImage(int type) {
            switch(type)
            {
                case 0:
                    return imgPoo;
                case 1:
                    return imgPooHard;
                case 2:
                    return imgPooSulsa;
                case 3:
                    return imgPooGolden;
                default:
                    return imgJewelry;
            }
        }

        private int imgMove(PooElement pElement, int difficulty) {

                pElement.changeY((1f/2f) * (5.0f) * (4.0f)/(5.0f + (float)difficulty) * (pElement.getCount()) * (pElement.getCount()));


              if(pElement.getY() > Height - charHeight && pElement.getRect().intersect(dst_character))
            {
                switch(pElement.type)
                {
                    case 0:
                        life -= 1;
                        break;
                    case 1:
                        life -= 2;
                        break;
                    case 2:
                        canMove = false;
                        if(sulsaThread.isAlive()) sulsaThread.interrupt();
                        sulsaThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    canMove = true;
                                } catch(InterruptedException e) { return; }
                            }
                        });
                        sulsaThread.start();
                        break;
                    case 3:
                        if(difficulty == 1)
                        {
                            if(life < 8)
                                life += 2;
                            else
                                life = 10;
                        }
                        else
                        {
                            if(life < 9)
                                life += 1;
                            else
                                life = 10;
                        }

                        break;
                    case 4:
                        if(difficulty == 1) {
                            AvoidPoo_score += 1000;
                        }
                        else
                        {
                            AvoidPoo_score += 500;
                        }
                }
                return 1;
            }
            if(pElement.getY() > (1.1f) * (float) Height) pElement.ArrivedAtBottom();
            else pElement.addCount();
            return 0;
        }
    }


    // ---------------------------------
    //        onTouchEvent
    // ---------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(canMove) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Rect temp = new Rect();
                int x = (int) event.getX();  // 클릭한 위치를 Rect()로 만듦
                charX = x - charcWidth;
            }
            if(event.getAction() == MotionEvent.ACTION_MOVE)
            {
                int x = (int) event.getX();
                charX = x - charcWidth;
            }
        }
        return true;
    } // touch


    public class PooRunnable implements Runnable{

        int default_time;
        int difficulty;
        Random random;
        ArrayList<PooElement> Poos;

        PooRunnable(ArrayList<PooElement> Poos, int default_time, int difficulty)
        {
            this.default_time = default_time;
            this.difficulty = difficulty;
            this.Poos = Poos;
            random = new Random(System.currentTimeMillis());
        }

        public void run()
        {
            while(life != 0) {
                try {
                    Thread.sleep(default_time + random.nextInt(200));
                    Poos.add(new PooElement((float) random.nextInt(Width / 20) * 20, 0f));
                    Poos.get(Poos.size() - 1).DecideType(random.nextInt(100), difficulty); //타입 결정
                } catch (InterruptedException e) { return; }
            }
        }
    }

    private class PooElement
    {
        private float x;
        private float y;
        private long count = 0;
        public int type;
        private boolean chkBottom;
        private Rect area;

        PooElement(float x, float y)
        {
            this.x = x;
            this.y = y;
            count = 0;
            chkBottom = false;
            area = new Rect();
            area.set((int)x - pcWidth, (int)y - pcHeight, (int)x + pcWidth, (int)y + pcHeight);
        }



        public void changeY(float y)
        {
            this.y = y;
            area.set((int)x - pcWidth, (int)y - pcHeight, (int)x + pcWidth, (int)y + pcHeight);
        }

        public float getY()
        {
            return y;
        }

        public void DecideType(int seed, int difficulty)
        {
            if(seed % 100 <= 65)
            {
                this.type = 0;
            }
            else if(seed % 100 <= 80)
            {
                this.type = 1;
            }
            else if(seed % 100 <= 92)
            {
                this.type = 2;
            }
            else if(seed % 100 <= 96)
            {
                this.type = 3;
            }
            else
            {
                this.type = 4;
            }
        }

        public Rect getRect()
        {

            return area;
        }

        public long getCount()
        {
            return count;
        }
        public void addCount()
        {
            this.count ++;
        }

        public void ArrivedAtBottom()
        {
            chkBottom = true;
        }

        public boolean isArrivedAtBottom()
        {
            return chkBottom;
        }
    }
}
