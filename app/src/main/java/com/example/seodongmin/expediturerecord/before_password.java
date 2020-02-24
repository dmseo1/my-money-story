package com.example.seodongmin.expediturerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.*;
import android.widget.*;
import android.app.*;

/**
 * Created by seodongmin on 2017-04-21.
 */

public class before_password extends AppCompatActivity {

    int return_cancel = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_password);

        Intent intent2 = new Intent(before_password.this, require_password.class);
        startActivityForResult(intent2, 8888); //8888은 requestCode이다

    }

    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8888) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                int cancelok = data.getIntExtra("cancel", 0);
                int loginok = data.getIntExtra("login", 0);
                if(cancelok == 1)
                    finish();
                if(loginok == 1)
                    finish();
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }


    public void onStop()
    {
        super.onStop();
    }

}
