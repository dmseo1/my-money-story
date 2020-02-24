package com.example.seodongmin.expediturerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.*;
import android.widget.*;
import android.view.*;

/**
 * Created by seodongmin on 2017-04-21.
 */

public class setting_modify_password extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_modify_password);

        final SharedPreferences sf = getSharedPreferences("File_Settings", MODE_PRIVATE);
        final String my_pw = sf.getString("PasswordContext", "");

        final EditText txt_current = (EditText) findViewById(R.id.popup_mfpassword_txt_current);
        final EditText txt_one = (EditText) findViewById(R.id.popup_mfpassword_txt_one);
        final EditText txt_two = (EditText) findViewById(R.id.popup_mfpassword_txt_two);



        Toast.makeText(setting_modify_password.this, "비밀번호 삭제를 원할 경우 현재 비밀번호만 입력하세요.", Toast.LENGTH_SHORT).show();


        //수정 버튼을 누를 경우
        Button.OnClickListener bt_modify_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(txt_current.getText().toString().length() == 0)
                {
                    Toast.makeText(setting_modify_password.this, "현재 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else if(txt_one.getText().toString().length() == 0)
                {
                    Toast.makeText(setting_modify_password.this, "새 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else if(txt_two.getText().toString().length() == 0)
                {
                    Toast.makeText(setting_modify_password.this, "비밀번호 확인란에 내용을 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else if(txt_one.getText().toString().length() < 4)
                {
                    Toast.makeText(setting_modify_password.this, "새 비밀번호는 4자리 이상으로 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else if(!txt_one.getText().toString().equals(txt_two.getText().toString()))
                {
                    Toast.makeText(setting_modify_password.this, "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
                else if(!txt_current.getText().toString().equals(my_pw))
                {
                    Toast.makeText(setting_modify_password.this, "현재 비밀번호를 잘못 입력하였습니다.", Toast.LENGTH_LONG).show();
                }
                else if(my_pw.equals(txt_one.getText().toString()))
                {
                    Toast.makeText(setting_modify_password.this, "현재 비밀번호와는 다른 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    SharedPreferences.Editor editor = sf.edit();
                    editor.putString("PasswordContext", txt_one.getText().toString());
                    editor.commit();
                    editor.putBoolean("isPassword", true);
                    editor.commit();
                    Toast.makeText(setting_modify_password.this, "비밀번호를 성공적으로 변경하였습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };

        //취소 버튼을 눌렀을 때
        Button.OnClickListener bt_cancel_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        Button.OnClickListener bt_delete_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(txt_current.getText().toString().length() == 0)
                {
                    Toast.makeText(setting_modify_password.this, "현재 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!txt_current.getText().toString().equals(my_pw))
                {
                    Toast.makeText(setting_modify_password.this, "현재 비밀번호를 잘못 입력하였습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences.Editor editor = sf.edit();
                    editor.putBoolean("isPassword", false);
                    editor.commit();
                    Toast.makeText(setting_modify_password.this, "비밀번호를 성공적으로 제거하였습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };


        Button bt_modify = (Button) findViewById(R.id.popup_mfpassword_btn_modify);
        bt_modify.setOnClickListener(bt_modify_listener);

        Button bt_cancel = (Button) findViewById(R.id.popup_mfpassword_btn_cancel);
        bt_cancel.setOnClickListener(bt_cancel_listener);

        Button bt_delete = (Button) findViewById(R.id.popup_mfpassword_btn_delete);
        bt_delete.setOnClickListener(bt_delete_listener);


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
