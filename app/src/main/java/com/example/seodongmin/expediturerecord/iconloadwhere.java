package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class iconloadwhere extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iconloadwhere);

        Intent getintent = getIntent();
        final int type = getintent.getIntExtra("CalledType", 0);

        //사진찍기 버튼 리스너
        Button.OnClickListener takepicture_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(iconloadwhere.this, iconloadresult.class);
                intent.putExtra("CalledType_2", type);
                intent.putExtra("RunType", 1);
                startActivity(intent);
                finish();
            }
        };


        Button takepicture = (Button) findViewById(R.id.popup_iconloadwhere_btn_takepicture);
        takepicture.setOnClickListener(takepicture_listener);


        //갤러리 버튼 리스너
        Button.OnClickListener album_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(iconloadwhere.this, iconloadresult.class);
                intent.putExtra("CalledType_2", type);
                intent.putExtra("RunType", 2);
                startActivity(intent);
                finish();
            }
        };
        Button album = (Button) findViewById(R.id.popup_iconloadwhere_btn_album);
        album.setOnClickListener(album_listener);


        //취소 버튼 리스너
        Button.OnClickListener cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };
        Button cancel = (Button) findViewById(R.id.popup_iconloadwhere_btn_cancel);
        cancel.setOnClickListener(cancel_listener);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
