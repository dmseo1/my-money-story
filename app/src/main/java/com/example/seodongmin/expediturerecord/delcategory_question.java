package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by seodongmin on 2017-04-29.
 */



public class delcategory_question  extends AppCompatActivity {

    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delcategory_question);

        Intent getintent = getIntent();
        type = getintent.getIntExtra("DelType", 0);
        if(type == 0) finish();
        System.out.println("Type 번호: " + type);
        int delcnt = getintent.getIntExtra("DelCnt", 0);
        if(delcnt == 0) finish();

        String msg = "총 " + delcnt + "개의 카테고리를 삭제합니다. 삭제 후에는 복구할 수 없으며, 삭제된 카테고리에 속한 내역은 모두 \"(분류없음)\"으로 귀속됩니다. 계속하시겠습니까?";

        TextView msgtxt = (TextView) findViewById(R.id.popup_delcategoryq_msg);
        msgtxt.setText(msg);


        Button.OnClickListener yes_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch(type)
                {
                    case 1:
                        Intent intent_inc = new Intent(delcategory_question.this, setting_inc.class);
                        intent_inc.putExtra("Hoksina", type);
                        intent_inc.putExtra("Changed", 1);
                        setResult(Activity.RESULT_OK, intent_inc);
                        finish();
                        break;
                    case 2:
                        Intent intent_exc = new Intent(delcategory_question.this, setting_exc.class);
                        intent_exc.putExtra("Hoksina", type);
                        intent_exc.putExtra("Changed", 2);
                        setResult(Activity.RESULT_OK, intent_exc);
                        finish();
                        break;
                    case 3:
                        Intent intent_exw = new Intent(delcategory_question.this, setting_exw.class);
                        intent_exw.putExtra("Hoksina", type);
                        intent_exw.putExtra("Changed", 1);
                        setResult(Activity.RESULT_OK, intent_exw);
                        finish();
                        break;
                    default:
                        break;
                }

            }
        };
        Button yes = (Button) findViewById(R.id.popup_delcategoryq_yes);
        yes.setOnClickListener(yes_listener);


        Button.OnClickListener no_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch(type)
                {
                    case 1:
                        Intent intent_no_inc = new Intent(delcategory_question.this, setting_inc.class);
                        intent_no_inc.putExtra("isDelWindow", true);
                        setResult(Activity.RESULT_OK, intent_no_inc);
                        finish();
                        break;
                    case 2:
                        Intent intent_no_exc = new Intent(delcategory_question.this, setting_exc.class);
                        intent_no_exc.putExtra("isDelWindow", true);
                        setResult(Activity.RESULT_OK, intent_no_exc);
                        finish();
                        break;
                    case 3:
                        Intent intent_no_exw = new Intent(delcategory_question.this, setting_exw.class);
                        intent_no_exw.putExtra("isDelWindow", type);
                        setResult(Activity.RESULT_OK, intent_no_exw);
                        finish();
                        break;
                    default:
                        break;
                }

            }
        };
        Button no = (Button) findViewById(R.id.popup_delcategoryq_no);
        no.setOnClickListener(no_listener);
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
