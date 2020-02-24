package com.example.seodongmin.expediturerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.app.*;

/**
 * Created by seodongmin on 2017-04-20.
 */

public class setting_password  extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount  = 0.7f;
        getWindow().setAttributes(layoutParams);

        this.setFinishOnTouchOutside(false);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting_password);

        Button.OnClickListener register_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText txt_one = (EditText) findViewById(R.id.popup_password_txt_one);
                EditText txt_two = (EditText) findViewById(R.id.popup_password_txt_two);
                if(txt_one.getText().toString().length() == 0)
                {
                    Toast.makeText(setting_password.this, "비밀번호 입력 란에 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(txt_two.getText().toString().length() == 0)
                {
                    Toast.makeText(setting_password.this, "비밀번호 확인 란에 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(txt_two.getText().toString().length() < 4)
                {
                    Toast.makeText(setting_password.this, "비밀번호는 최소 4자리 이상으로 설정하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!txt_one.getText().toString().equals(txt_two.getText().toString()))
                {
                    Toast.makeText(setting_password.this, "두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences sp  = getSharedPreferences("File_Settings", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("PasswordContext", txt_one.getText().toString());
                    editor.commit();
                    editor.putBoolean("isPassword", true);
                    editor.commit();
                    Toast.makeText(setting_password.this, "비밀번호가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

        };

        Button.OnClickListener cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(setting_password.this, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }

        };

        Button register = (Button) findViewById(R.id.popup_password_btn_register);
        register.setOnClickListener(register_listener);

        Button cancel = (Button) findViewById(R.id.popup_password_btn_cancel);
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
