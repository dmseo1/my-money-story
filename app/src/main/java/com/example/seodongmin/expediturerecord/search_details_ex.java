package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.search.search_adapter;
import static com.example.seodongmin.expediturerecord.search.search_adapter_result;
import static com.example.seodongmin.expediturerecord.search.search_adapter_result_2;

/**
 * Created by seodongmin on 2017-04-23.
 */

public class search_details_ex extends AppCompatActivity {

    int pos = -1;
    boolean useupper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details_ex);


    }

    public void onResume()
    {
        super.onResume();
        MyIntStrMatch mway = new MyIntStrMatch(3);
        MyIntStrMatch mwhere = new MyIntStrMatch(2);

        final Intent intent = getIntent();
        if(pos == -1) {
            pos = intent.getIntExtra("ex_pos", -1);
            if (pos == -1) {
                finish();
            }
            useupper = true;
        }
        else {
            useupper = false;
        }
        /*final String year = intent.getStringExtra("ex_year");
        final String month = intent.getStringExtra("ex_month");
        final String day = intent.getStringExtra("ex_day");
        final int way = intent.getIntExtra("ex_way", -1);
        final int where = intent.getIntExtra("ex_where", -1);
        final String amount = intent.getStringExtra("ex_amount");
        final long amount_int = intent.getLongExtra("ex_amount_int", 0);
        final int satis = intent.getIntExtra("ex_satis", 0);
        final String memo = intent.getStringExtra("ex_memo");*/


        final String year;
        final String month;
        final String day;
        final String way;
        final String where;
        final long amount;
        final int satis;
        final int includeStat;
        final String memo;

       /* if(useupper) {
            System.out.println("여기라고?");
            year = String.valueOf(search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getyear());
            month = String.valueOf(search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getmonth());
            day = String.valueOf(search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getday());
            way = search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getway();
            where = search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getwhere();
            amount = search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getamount_int();
            satis = search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getemonum();
            includeStat = search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getincludeStat();
            memo = search_adapter.getListItem().get(search_adapter_result_2.getListItem().get(pos).getabspos()).getmemo();
        }*/
       /* else
        {*/
            System.out.println("여기가 실행됐을 거잖아");
            year = String.valueOf(search_adapter.getListItem().get(pos).getyear());
            month = String.valueOf(search_adapter.getListItem().get(pos).getmonth());
            day = String.valueOf(search_adapter.getListItem().get(pos).getday());
            way = search_adapter.getListItem().get(pos).getway();
            where = search_adapter.getListItem().get(pos).getwhere();
            amount = search_adapter.getListItem().get(pos).getamount_int();
            satis = search_adapter.getListItem().get(pos).getemonum();
            includeStat = search_adapter.getListItem().get(pos).getincludeStat();
            memo = search_adapter.getListItem().get(pos).getmemo();
      /* }*/

        TextView txt_year = (TextView) findViewById(R.id.detail_expenditure_lbl_y);
        TextView txt_month = (TextView) findViewById(R.id.detail_expenditure_lbl_m);
        TextView txt_day = (TextView) findViewById(R.id.detail_expenditure_lbl_d);
        TextView txt_way = (TextView) findViewById(R.id.detail_expenditure_lbl_way_h);
        TextView txt_where = (TextView) findViewById(R.id.detail_expenditure_lbl_where_h);
        TextView txt_amount = (TextView) findViewById(R.id.detail_expenditure_lbl_amount_h);
        ImageView img_satis = (ImageView) findViewById(R.id.detail_expenditure_emotion);
        TextView txt_includeExp = (TextView) findViewById(R.id.detail_expenditure_lbl_includeExp_h);
        TextView txt_memo = (TextView) findViewById(R.id.detail_expenditure_lbl_memo_h);
        ImageView img_search = (ImageView) findViewById(R.id.detail_expenditure_img_search);

        txt_year.setText(year);
        txt_month.setText(month);
        txt_day.setText(day);
        txt_way.setText(way);
        txt_where.setText(where);
        txt_amount.setText(String.format("%,d", amount));

        switch(satis)
        {
            case 5:
                img_satis.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_emotion_5));
                break;
            case 4:
                img_satis.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_emotion_4));
                break;
            case 3:
                img_satis.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_emotion_3));
                break;
            case 2:
                img_satis.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_emotion_2));
                break;
            case 1:
                img_satis.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_emotion_1));
                break;
            default:
                img_satis.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_empty));
                break;
        }

        switch(includeStat)
        {
            case 1:
                txt_includeExp.setText("예");
                break;
            case 0:
                txt_includeExp.setText("아니오");
                break;
            default:
                finish();
        }

        txt_memo.setText(memo);

        //메모 검색 버튼
        Button.OnClickListener img_search_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, memo);
                startActivity(intent);
            }
        };
        img_search.setOnClickListener(img_search_listener);


        //수정 버튼
        Button.OnClickListener modify_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent_modify = new Intent(search_details_ex.this, search_details_ex_m.class);
                intent_modify.putExtra("ex_pos_m", pos);
                intent_modify.putExtra("ex_year_m", year);
                intent_modify.putExtra("ex_month_m", month);
                intent_modify.putExtra("ex_day_m", day);
                System.out.println(way);
                intent_modify.putExtra("ex_way_m", way);
                intent_modify.putExtra("ex_memo_m", memo);
                intent_modify.putExtra("ex_satis_m", satis);
                intent_modify.putExtra("ex_where_m", where);
                intent_modify.putExtra("ex_amount_m", amount);
                intent_modify.putExtra("ex_includeExp_m", includeStat);
                pos = -1;
                startActivityForResult(intent_modify, 8888);
            }
        };
        Button modify = (Button) findViewById(R.id.detail_expenditure_modify);
        modify.setOnClickListener(modify_listener);


        //삭제 버튼
        Button.OnClickListener delete_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                SharedPreferences.Editor editor = inc_and_exp.edit();
                //search_adapter.getListItem().remove(search_adapter_result_2.getListItem().get(pos).getabspos());
                search_adapter.getListItem().remove(pos);
                int i = pos;
                System.out.println("pos: " + pos);
                while(true) //데이터베이스 리스트를 당긴다(삭제의미)
                {
                    if(inc_and_exp.getString("incexp_" + i, "").equals(""))
                    {
                        editor.putString("incexp_" + (i-1), "");
                        break;
                    }
                    editor.putString("incexp_" + i, inc_and_exp.getString("incexp_" + (i+1), ""));
                    i++;
                }
                editor.putString("incexp_" + (i-1), ""); //당긴 후에 제일 뒤에 남은 데이터는 있으면 안되는 데이터
                editor.commit();

                Toast.makeText(search_details_ex.this, "삭제하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        Button delete = (Button) findViewById(R.id.detail_expenditure_delete);
        delete.setOnClickListener(delete_listener);




        //뒤로가기
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.detail_ex_img_back);
        back.setOnClickListener(back_listener);
    }

 @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8888) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                pos = data.getIntExtra("mdetail_ex_pos", -1);
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }
}
