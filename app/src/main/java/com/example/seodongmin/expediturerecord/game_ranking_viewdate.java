package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Calendar;

import static com.example.seodongmin.expediturerecord.init.adapter_avoidpoo;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit100;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit20;
import static com.example.seodongmin.expediturerecord.init.adapter_color;
import static com.example.seodongmin.expediturerecord.init.adapter_countpeople;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.adapter_rocksipa;
import static com.example.seodongmin.expediturerecord.init.avoidpoo;
import static com.example.seodongmin.expediturerecord.init.calculateit_100;
import static com.example.seodongmin.expediturerecord.init.calculateit_20;
import static com.example.seodongmin.expediturerecord.init.color;
import static com.example.seodongmin.expediturerecord.init.countpeople;
import static com.example.seodongmin.expediturerecord.init.fastmemorize;
import static com.example.seodongmin.expediturerecord.init.rocksipa;

/**
 * Created by seodongmin on 2017-05-16.
 */

public class game_ranking_viewdate extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ranking_viewdate);

        final Button cancel = (Button) findViewById(R.id.popup_game_ranking_viewdate_cancel);
        Intent getintent = getIntent();
        final int gametype = getintent.getIntExtra("gametype", 0);
        final int pos = getintent.getIntExtra("pos", -1);
        TextView tv = (TextView) findViewById(R.id.popup_game_ranking_viewdate_msg);
        String msg;
        Calendar cal = Calendar.getInstance();
        DecimalFormat df = new DecimalFormat("00");
        switch(gametype)
        {
            case 1:
                cal.setTimeInMillis(adapter_calculateit20.getListItem().get(pos).getdate());
                msg = cal.get(Calendar.YEAR) + "/" +
                        df.format(cal.get(Calendar.MONTH) + 1) + "/" +
                        df.format(cal.get(Calendar.DAY_OF_MONTH)) + " " +
                        df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        df.format(cal.get(Calendar.MINUTE)) + ":" +
                        df.format(cal.get(Calendar.SECOND));
                tv.setText(msg);
                break;
            case 2:
                cal.setTimeInMillis(adapter_calculateit100.getListItem().get(pos).getdate());
                msg = cal.get(Calendar.YEAR) + "/" +
                        df.format(cal.get(Calendar.MONTH) + 1) + "/" +
                        df.format(cal.get(Calendar.DAY_OF_MONTH)) + " " +
                        df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        df.format(cal.get(Calendar.MINUTE)) + ":" +
                        df.format(cal.get(Calendar.SECOND));
                tv.setText(msg);

                break;
            case 3:

                cal.setTimeInMillis(adapter_fastmemorize.getListItem().get(pos).getdate());
                msg = cal.get(Calendar.YEAR) + "/" +
                        df.format(cal.get(Calendar.MONTH) + 1) + "/" +
                        df.format(cal.get(Calendar.DAY_OF_MONTH)) + " " +
                        df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        df.format(cal.get(Calendar.MINUTE)) + ":" +
                        df.format(cal.get(Calendar.SECOND));
                tv.setText(msg);
                break;
            case 4:
                cal.setTimeInMillis(adapter_color.getListItem().get(pos).getdate());
                msg = cal.get(Calendar.YEAR) + "/" +
                        df.format(cal.get(Calendar.MONTH) + 1) + "/" +
                        df.format(cal.get(Calendar.DAY_OF_MONTH)) + " " +
                        df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        df.format(cal.get(Calendar.MINUTE)) + ":" +
                        df.format(cal.get(Calendar.SECOND));
                tv.setText(msg);

                break;
            case 5:
                cal.setTimeInMillis(adapter_rocksipa.getListItem().get(pos).getdate());
                msg = cal.get(Calendar.YEAR) + "/" +
                        df.format(cal.get(Calendar.MONTH) + 1) + "/" +
                        df.format(cal.get(Calendar.DAY_OF_MONTH)) + " " +
                        df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        df.format(cal.get(Calendar.MINUTE)) + ":" +
                        df.format(cal.get(Calendar.SECOND));
                tv.setText(msg);
                break;
            case 6:
                cal.setTimeInMillis(adapter_countpeople.getListItem().get(pos).getdate());
                msg = cal.get(Calendar.YEAR) + "/" +
                        df.format(cal.get(Calendar.MONTH) + 1) + "/" +
                        df.format(cal.get(Calendar.DAY_OF_MONTH)) + " " +
                        df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        df.format(cal.get(Calendar.MINUTE)) + ":" +
                        df.format(cal.get(Calendar.SECOND));
                tv.setText(msg);
                break;
            case 7:
                cal.setTimeInMillis(adapter_avoidpoo.getListItem().get(pos).getdate());
                msg = cal.get(Calendar.YEAR) + "/" +
                        df.format(cal.get(Calendar.MONTH) + 1) + "/" +
                        df.format(cal.get(Calendar.DAY_OF_MONTH)) + " " +
                        df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                        df.format(cal.get(Calendar.MINUTE)) + ":" +
                        df.format(cal.get(Calendar.SECOND));
                tv.setText(msg);
                break;

        }

        System.out.println("gametype: " + gametype);
        System.out.println("pos: " + pos);




        Button.OnClickListener delete_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor;
                int i = pos;
                switch(gametype)
                {
                    case 1:
                        editor = calculateit_20.edit();
                        adapter_calculateit20.getListItem().remove(pos);
                        System.out.println("pos: " + pos);
                        while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                        {
                            if(calculateit_20.getString("rank20_" + i, "").equals(""))
                            {
                                editor.putString("rank20_" + (i-1), "");
                                break;
                            }
                            editor.putString("rank20_" + i, calculateit_20.getString("rank20_" + (i+1), ""));
                            i++;
                        }
                        editor.putString("rank20_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                        editor.commit();
                        break;
                    case 2:
                        editor = calculateit_100.edit();
                        adapter_calculateit100.getListItem().remove(pos);
                        System.out.println("pos: " + pos);
                        while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                        {
                            if(calculateit_100.getString("rank100_" + i, "").equals(""))
                            {
                                editor.putString("rank100_" + (i-1), "");
                                break;
                            }
                            editor.putString("rank100_" + i, calculateit_100.getString("rank100_" + (i+1), ""));
                            i++;
                        }
                        editor.putString("rank100_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                        editor.commit();
                        break;
                    case 3:
                        editor = fastmemorize.edit();
                        adapter_fastmemorize.getListItem().remove(pos);
                        System.out.println("pos: " + pos);
                        while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                        {
                            if(fastmemorize.getString("rankfast_" + i, "").equals(""))
                            {
                                editor.putString("rankfast_" + (i-1), "");
                                break;
                            }
                            editor.putString("rankfast_" + i, fastmemorize.getString("rankfast_" + (i+1), ""));
                            i++;
                        }
                        editor.putString("rankfast_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                        editor.commit();
                        break;
                    case 4:
                        editor = color.edit();
                        adapter_color.getListItem().remove(pos);
                        System.out.println("pos: " + pos);
                        while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                        {
                            if(color.getString("rankcolor_" + i, "").equals(""))
                            {
                                editor.putString("rankcolor_" + (i-1), "");
                                break;
                            }
                            editor.putString("rankcolor_" + i, color.getString("rankcolor_" + (i+1), ""));
                            i++;
                        }
                        editor.putString("rankcolor_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                        editor.commit();
                        break;
                    case 5:
                        editor = rocksipa.edit();
                        adapter_rocksipa.getListItem().remove(pos);
                        System.out.println("pos: " + pos);
                        while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                        {
                            if(rocksipa.getString("rankrock_" + i, "").equals(""))
                            {
                                editor.putString("rankrock_" + (i-1), "");
                                break;
                            }
                            editor.putString("rankrock_" + i, rocksipa.getString("rankrock_" + (i+1), ""));
                            i++;
                        }
                        editor.putString("rankrock_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                        editor.commit();
                        break;
                    case 6:
                        editor = countpeople.edit();
                        adapter_countpeople.getListItem().remove(pos);
                        System.out.println("pos: " + pos);
                        while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                        {
                            if(countpeople.getString("rankcount_" + i, "").equals(""))
                            {
                                editor.putString("rankcount_" + (i-1), "");
                                break;
                            }
                            editor.putString("rankcount_" + i, countpeople.getString("rankcount_" + (i+1), ""));
                            i++;
                        }
                        editor.putString("rankcount_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                        editor.commit();
                        break;
                    case 7:
                        editor = avoidpoo.edit();
                        adapter_avoidpoo.getListItem().remove(pos);
                        System.out.println("pos: " + pos);
                        while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                        {
                            if(avoidpoo.getString("rankpoo_" + i, "").equals(""))
                            {
                                editor.putString("rankpoo_" + (i-1), "");
                                break;
                            }
                            editor.putString("rankpoo_" + i, avoidpoo.getString("rankpoo_" + (i+1), ""));
                            i++;
                        }
                        editor.putString("rankpoo_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                        editor.commit();
                        break;
                }


                Intent intent_result = new Intent(game_ranking_viewdate.this, game_ranking.class);
                intent_result.putExtra("Resumer", true);
                setResult(RESULT_OK, intent_result);
                Toast.makeText(game_ranking_viewdate.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        Button delete = (Button) findViewById(R.id.popup_game_ranking_viewdate_delete);
        delete.setOnClickListener(delete_listener);






        Button.OnClickListener cancel_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
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
