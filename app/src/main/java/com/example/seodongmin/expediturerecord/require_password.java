package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import android.view.*;
import android.content.*;

/**
 * Created by seodongmin on 2017-04-21.
 */

public class require_password extends AppCompatActivity {

    boolean islogin = false;
    boolean iscancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_require_password);


        Button.OnClickListener ok_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                SharedPreferences sp  = getSharedPreferences("File_Settings", Activity.MODE_PRIVATE);
                String answer = sp.getString("PasswordContext", "");

                EditText in_pw = (EditText) findViewById(R.id.popup_require_password_txt);
                if(!in_pw.getText().toString().equals(answer))
                {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    Toast.makeText(require_password.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    islogin = true;
                    Toast.makeText(require_password.this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(require_password.this, main.class);
                    Intent intent2 = new Intent(require_password.this, before_password.class);
                    intent2.putExtra("login", 1);
                    setResult(Activity.RESULT_OK, intent2);
                    startActivity(intent1);
                    finish();
                }
            }
        };

        Button.OnClickListener cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                iscancelled = true;
                Toast.makeText(require_password.this, "비밀번호를 입력하여야 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(require_password.this, before_password.class);
                intent.putExtra("cancel", 1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        };

        Button ok = (Button) findViewById(R.id.popup_require_password_btn_ok);
        ok.setOnClickListener(ok_listener);

        Button cancel = (Button) findViewById(R.id.popup_require_password_btn_cancel);
        cancel.setOnClickListener(cancel_listener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        //Toast.makeText(require_password.this, "여기야? 3", Toast.LENGTH_SHORT).show();

        /*if(!islogin && !iscancelled) { //확인, 취소 버튼을 누르지 않았는데 onStop이 된 경우

            finish();
        }*/
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
