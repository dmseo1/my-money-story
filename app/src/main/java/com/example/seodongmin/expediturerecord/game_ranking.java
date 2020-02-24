package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import static com.example.seodongmin.expediturerecord.init.adapter_avoidpoo;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit100;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit20;
import static com.example.seodongmin.expediturerecord.init.adapter_color;
import static com.example.seodongmin.expediturerecord.init.adapter_countpeople;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.adapter_rocksipa;

/**
 * Created by seodongmin on 2017-05-08.
 */

public class game_ranking extends AppCompatActivity {

    Button start;
    Button ranking;

    int REQUEST_DELETE = 8888;

    boolean resumer = false;
    int MAX_PAGE = 3;
    int NUM_OF_GAMES = 7;
    int currentPage = 0;
    int currentShowing = 0;

    long[] GAMERANK_LISTVIEW_ID;

    TabHost[] tabHosts;

    ListView listview_20;
    ListView listview_100;
    ListView listview_fastmemorize;
    ListView listview_color;
    ListView listview_rocksipa;
    ListView listview_countpeople;
    ListView listview_avoidpoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ranking);

        tabHosts = new TabHost[MAX_PAGE];



        //이전, 다음 버튼 및 버튼 동작 정의
        Button prev = (Button) findViewById(R.id.game_ranking_btn_prev);
        Button next = (Button) findViewById(R.id.game_ranking_btn_next);

        Button.OnClickListener prev_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentShowing != 0)
                {
                    currentShowing --;
                    currentPage = currentShowing / 3;
                    Intent intent = new Intent(game_ranking.this, nowsearching.class);
                    startActivity(intent);
                }
            }
        };
        prev.setOnClickListener(prev_listener);

        Button.OnClickListener next_listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentShowing != NUM_OF_GAMES - 1)
                {
                    currentShowing ++;
                    currentPage = currentShowing / 3;
                    Intent intent = new Intent(game_ranking.this, nowsearching.class);
                    startActivity(intent);
                }
            }
        };
        next.setOnClickListener(next_listener);


        //탭 버튼 설정(1페이지)==========================================================================================
        tabHosts[0] = (TabHost) findViewById(R.id.game_calculateit_ranking_tabhost);
        tabHosts[0].setup();

        TabHost.TabSpec spec1 = tabHosts[0].newTabSpec("Tab1").setContent(R.id.game_calculateit_ranking_tab20)
                .setIndicator(getString(R.string.calculateit_20));
        tabHosts[0].addTab(spec1);

        TabHost.TabSpec spec2 = tabHosts[0].newTabSpec("Tab2").setContent(R.id.game_calculateit_ranking_tab100)
                .setIndicator(getString(R.string.calculateit_100));
        tabHosts[0].addTab(spec2);

        TabHost.TabSpec spec3 = tabHosts[0].newTabSpec("Tab3").setContent(R.id.game_fastmemorize_ranking_tab)
                .setIndicator(getString(R.string.fastmemorize));
        tabHosts[0].addTab(spec3);


        tabHosts[0].getTabWidget().getChildAt(0).getLayoutParams().height = 160;
        tabHosts[0].getTabWidget().getChildAt(1).getLayoutParams().height = 160;
        tabHosts[0].getTabWidget().getChildAt(2).getLayoutParams().height = 160;


                RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout rl1 = (LinearLayout) tabHosts[0].getTabWidget().getChildAt(0);
        ImageView img1 = (ImageView) rl1.getChildAt(0);
        img1.setImageDrawable(getDrawable(R.drawable.btn_back));
        TextView tv1 =  (TextView) rl1.getChildAt(1);
        tv1.setTextSize(20);
        LinearLayout rl2 = (LinearLayout) tabHosts[0].getTabWidget().getChildAt(1);
        TextView tv2 =  (TextView) rl2.getChildAt(1);
        tv2.setTextSize(20);
        LinearLayout rl3 = (LinearLayout) tabHosts[0].getTabWidget().getChildAt(2);
        TextView tv3 =  (TextView) rl3.getChildAt(1);
        tv3.setTextSize(20);
        //탭 버튼 설정 끝(1페이지)==========================================================================================






        //탭 버튼 설정(2페이지)=============================================================================================
        tabHosts[1] = (TabHost) findViewById(R.id.game_calculateit_ranking_tabhost_p2);
        tabHosts[1].setup();

        TabHost.TabSpec spec1_p2 = tabHosts[1].newTabSpec("Tab4").setContent(R.id.game_color_ranking_tab)
                .setIndicator(getString(R.string.color));
        tabHosts[1].addTab(spec1_p2);

        TabHost.TabSpec spec2_p2 = tabHosts[1].newTabSpec("Tab5").setContent(R.id.game_rocksipa_ranking_tab)
                .setIndicator(getString(R.string.rocksipa));
        tabHosts[1].addTab(spec2_p2);

        TabHost.TabSpec spec3_p2 = tabHosts[1].newTabSpec("Tab6").setContent(R.id.game_countpeople_ranking_tab)
                .setIndicator(getString(R.string.countpeople));
        tabHosts[1].addTab(spec3_p2);


        tabHosts[1].getTabWidget().getChildAt(0).getLayoutParams().height = 160;
        tabHosts[1].getTabWidget().getChildAt(1).getLayoutParams().height = 160;
        tabHosts[1].getTabWidget().getChildAt(2).getLayoutParams().height = 160;


        RelativeLayout.LayoutParams tvParams_p2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout rl1_p2 = (LinearLayout) tabHosts[1].getTabWidget().getChildAt(0);
        ImageView img1_p2 = (ImageView) rl1_p2.getChildAt(0);
        img1_p2.setImageDrawable(getDrawable(R.drawable.btn_back));
        TextView tv1_p2 =  (TextView) rl1_p2.getChildAt(1);
        tv1_p2.setTextSize(20);
        LinearLayout rl2_p2 = (LinearLayout) tabHosts[1].getTabWidget().getChildAt(1);
        TextView tv2_p2 =  (TextView) rl2_p2.getChildAt(1);
        tv2_p2.setTextSize(18);
        LinearLayout rl3_p2 = (LinearLayout) tabHosts[1].getTabWidget().getChildAt(2);
        TextView tv3_p2 =  (TextView) rl3_p2.getChildAt(1);
        tv3_p2.setTextSize(18);
        //탭 버튼 설정 끝(2페이지)==========================================================================================






        //탭 버튼 설정(3페이지)=============================================================================================
        tabHosts[2] = (TabHost) findViewById(R.id.game_calculateit_ranking_tabhost_p3);
        tabHosts[2].setup();

        TabHost.TabSpec spec1_p3 = tabHosts[2].newTabSpec("Tab4").setContent(R.id.game_avoidpoo_ranking_tab)
                .setIndicator(getString(R.string.avoidpoo));
        tabHosts[2].addTab(spec1_p3);

        TabHost.TabSpec spec2_p3 = tabHosts[2].newTabSpec("Tab5").setContent(R.id.game_rocksipa_ranking_tab)
                .setIndicator("");
        tabHosts[2].addTab(spec2_p3);

        TabHost.TabSpec spec3_p3 = tabHosts[2].newTabSpec("Tab6").setContent(R.id.game_countpeople_ranking_tab)
                .setIndicator("");
        tabHosts[2].addTab(spec3_p3);


        tabHosts[2].getTabWidget().getChildAt(0).getLayoutParams().height = 160;
        tabHosts[2].getTabWidget().getChildAt(1).getLayoutParams().height = 160;
        tabHosts[2].getTabWidget().getChildAt(2).getLayoutParams().height = 160;


        RelativeLayout.LayoutParams tvParams_p3 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout rl1_p3 = (LinearLayout) tabHosts[2].getTabWidget().getChildAt(0);
        ImageView img1_p3 = (ImageView) rl1_p3.getChildAt(0);
        img1_p3.setImageDrawable(getDrawable(R.drawable.btn_back));
        TextView tv1_p3 =  (TextView) rl1_p3.getChildAt(1);
        tv1_p3.setTextSize(20);
        LinearLayout rl2_p3 = (LinearLayout) tabHosts[2].getTabWidget().getChildAt(1);
        TextView tv2_p3 =  (TextView) rl2_p3.getChildAt(1);
        tv2_p3.setTextSize(18);
        LinearLayout rl3_p3 = (LinearLayout) tabHosts[2].getTabWidget().getChildAt(2);
        TextView tv3_p3 =  (TextView) rl3_p3.getChildAt(1);
        tv3_p3.setTextSize(18);
        //탭 버튼 설정 끝(3페이지)==========================================================================================




        //리스트뷰-어댑터 연결(1페이지)
        listview_20 = (ListView) findViewById(R.id.game_calculateit_ranking_tab20_list);
        listview_100 = (ListView) findViewById(R.id.game_calculateit_ranking_tab100_list);
        listview_fastmemorize = (ListView) findViewById(R.id.game_fastmemorize_ranking_tab_list);
        listview_20.setAdapter(adapter_calculateit20);
        listview_100.setAdapter(adapter_calculateit100);
        listview_fastmemorize.setAdapter(adapter_fastmemorize);



        //리스트뷰-어댑터 연결(2페이지)
        listview_color = (ListView) findViewById(R.id.game_color_ranking_tab_list);
        listview_rocksipa = (ListView) findViewById(R.id.game_rocksipa_ranking_tab_list);
        listview_countpeople = (ListView) findViewById(R.id.game_countpeople_ranking_tab_list);
        listview_color.setAdapter(adapter_color);
        listview_rocksipa.setAdapter(adapter_rocksipa);
        listview_countpeople.setAdapter(adapter_countpeople);


        //리스트뷰-어댑터 연결(3페이지)
        listview_avoidpoo = (ListView) findViewById(R.id.game_avoidpoo_ranking_tab_list);
        listview_avoidpoo.setAdapter(adapter_avoidpoo);


        //리스트뷰 아이디 획득
        GAMERANK_LISTVIEW_ID = new long[NUM_OF_GAMES];
        GAMERANK_LISTVIEW_ID[0] = listview_20.getId();
        GAMERANK_LISTVIEW_ID[1] = listview_100.getId();
        GAMERANK_LISTVIEW_ID[2] = listview_fastmemorize.getId();
        GAMERANK_LISTVIEW_ID[3] = listview_color.getId();
        GAMERANK_LISTVIEW_ID[4] = listview_rocksipa.getId();
        GAMERANK_LISTVIEW_ID[5] = listview_countpeople.getId();
        GAMERANK_LISTVIEW_ID[6] = listview_avoidpoo.getId();


        //리스트뷰 아이템 클릭 리스너
        ListView.OnItemClickListener cal20_d_listener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(game_ranking.this, game_ranking_viewdate.class);
                intent.putExtra("pos", position);
                intent.putExtra("gametype", 1);
                startActivityForResult(intent, REQUEST_DELETE);
            }
        };

        ListView.OnItemClickListener cal100_d_listener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(game_ranking.this, game_ranking_viewdate.class);
                intent.putExtra("pos", position);
                intent.putExtra("gametype", 2);
                startActivityForResult(intent, REQUEST_DELETE);
            }
        };

        ListView.OnItemClickListener fastmemorize_d_listener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(game_ranking.this, game_ranking_viewdate.class);
                intent.putExtra("pos", position);
                intent.putExtra("gametype", 3);
                startActivityForResult(intent, REQUEST_DELETE);
            }
        };

        ListView.OnItemClickListener color_d_listener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(game_ranking.this, game_ranking_viewdate.class);
                intent.putExtra("pos", position);
                intent.putExtra("gametype", 4);
                startActivityForResult(intent, REQUEST_DELETE);
            }
        };

        ListView.OnItemClickListener rocksipa_d_listener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(game_ranking.this, game_ranking_viewdate.class);
                intent.putExtra("pos", position);
                intent.putExtra("gametype", 5);
                startActivityForResult(intent, REQUEST_DELETE);
            }
        };

        ListView.OnItemClickListener countpeople_d_listener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(game_ranking.this, game_ranking_viewdate.class);
                intent.putExtra("pos", position);
                intent.putExtra("gametype", 6);
                startActivityForResult(intent, REQUEST_DELETE);
            }
        };

        ListView.OnItemClickListener avoidpoo_d_listener = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(game_ranking.this, game_ranking_viewdate.class);
                intent.putExtra("pos", position);
                intent.putExtra("gametype", 7);
                startActivityForResult(intent, REQUEST_DELETE);
            }
        };



        listview_20.setOnItemClickListener(cal20_d_listener);
        listview_100.setOnItemClickListener(cal100_d_listener);
        listview_fastmemorize.setOnItemClickListener(fastmemorize_d_listener);
        listview_color.setOnItemClickListener(color_d_listener);
        listview_rocksipa.setOnItemClickListener(rocksipa_d_listener);
        listview_countpeople.setOnItemClickListener(countpeople_d_listener);
        listview_avoidpoo.setOnItemClickListener(avoidpoo_d_listener);



        //뒤로가기 버튼
        ImageView back = (ImageView) findViewById(R.id.game_calculateit_ranking_back);
        ImageView.OnClickListener back_listener = new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();
        for(int i = 0; i < MAX_PAGE ; i ++)
        {
            if(i == currentPage)
            {
                tabHosts[i].setVisibility(View.VISIBLE);
            }
            else
            {
                tabHosts[i].setVisibility(View.GONE);
            }
        }

        tabHosts[currentPage].setCurrentTab(currentShowing % 3);


        if(resumer) {
            //리스트뷰-어댑터 연결(1페이지)
            listview_20.setAdapter(adapter_calculateit20);
            listview_100.setAdapter(adapter_calculateit100);
            listview_fastmemorize.setAdapter(adapter_fastmemorize);


            //리스트뷰-어댑터 연결(2페이지)
            listview_color.setAdapter(adapter_color);
            listview_rocksipa.setAdapter(adapter_rocksipa);
            listview_countpeople.setAdapter(adapter_rocksipa);

            //리스트뷰-어댑터 연결(3페이지)
            listview_avoidpoo.setAdapter(adapter_avoidpoo);

            resumer = false;
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_DELETE) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                resumer = true;
            }
            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }

    }

}
