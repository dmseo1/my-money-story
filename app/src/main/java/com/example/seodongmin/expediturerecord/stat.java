package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.*;
import android.widget.*;
import android.view.ViewGroup.*;

import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Created by seodongmin on 2017-04-23.
 */

public class stat  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        // 년/월 스피너 데이터 출력
        Calendar cal = Calendar.getInstance();
        int standard_y = cal.get(Calendar.YEAR);
        int standard_m = cal.get(Calendar.MONTH);

        final Spinner spinner_y = (Spinner) findViewById(R.id.stat_spin_y);
        ArrayAdapter adapter_y = ArrayAdapter.createFromResource(this, R.array.ie_years, android.R.layout.simple_spinner_item);
        adapter_y.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_y.setAdapter(adapter_y);
        spinner_y.setSelection(standard_y - 2017);
        String init_yearinfo = spinner_y.getSelectedItem().toString();



        final Spinner spinner_m = (Spinner) findViewById(R.id.stat_spin_m);
        ArrayAdapter adapter_m = ArrayAdapter.createFromResource(this, R.array.ie_months, android.R.layout.simple_spinner_item);
        adapter_m.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_m.setAdapter(adapter_m);
        spinner_m.setSelection(standard_m);
        String init_monthinfo = spinner_m.getSelectedItem().toString();

        TextView init_goalex = (TextView) findViewById(R.id.stat_lbl_goal_amount);
        TextView init_goalex_g = (TextView) findViewById(R.id.stat_lbl_goal_amount_g);

        SharedPreferences init_ref = getSharedPreferences("goal" + init_yearinfo + init_monthinfo, MODE_PRIVATE);
        long init_goal = init_ref.getLong("goalex_" + init_yearinfo + init_monthinfo, 0);
        init_goalex.setText(String.format("%,d", init_goal));
        init_goalex_g.setText(String.valueOf(init_goal));

        Button.OnClickListener wow_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(stat.this, stat_v2.class);
                startActivity(intent);
            }


        };

        Button wow = (Button) findViewById(R.id.wowbutton);
        wow.setOnClickListener(wow_listener);




        Spinner.OnItemSelectedListener spinner_ym_listener = new Spinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView< ? > adapterView, View view, int position, long id )
            {
                TextView goalex = (TextView) findViewById(R.id.stat_lbl_goal_amount);
                TextView goalex_g = (TextView) findViewById(R.id.stat_lbl_goal_amount_g);
                String yearinfo = spinner_y.getSelectedItem().toString();
                String monthinfo = spinner_m.getSelectedItem().toString();
                SharedPreferences ref = getSharedPreferences("goalex_" + yearinfo + monthinfo, MODE_PRIVATE);
                long goal = ref.getLong("goalex_" + yearinfo + monthinfo, 0);
                goalex.setText(String.format("%,d", goal));
                goalex_g.setText(String.valueOf(goal));



                //달리는 위치 수정
                TextView txt_all = (TextView) findViewById(R.id.stat_lbl_goal_amount_g);
                int all = Integer.parseInt(txt_all.getText().toString());
                TextView txt_part = (TextView) findViewById(R.id.stat_lbl_expenditure_g);
                int part = Integer.parseInt(txt_part.getText().toString());
                float percent = (float)part / (float)all;
                float percent_comp = 1 - percent;
                LinearLayout lin = (LinearLayout) findViewById(R.id.stat_runningtrack);

                if(percent >= 0.92)
                {
                    percent = 0.92f;
                    percent_comp = 0.08f;
                }

                View runningman_left = (View) findViewById(R.id.stat_runningman_left);
                runningman_left.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1, percent_comp));
                View runningman_right = (View) findViewById(R.id.stat_runningman_right);
                runningman_right.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1, percent));

                //프로그레스 바 수정
                ProgressBar pgbar = (ProgressBar) findViewById(R.id.stat_prg_goal_expenditure);
                if(part > all)
                {
                    pgbar.setMax(1);
                    pgbar.setProgress(1);
                }
                else {
                    pgbar.setMax(all);
                    pgbar.setProgress(part);
                }


            }

            @Override
            public void onNothingSelected( AdapterView< ? > view )
            {
            }
        };
        spinner_y.setOnItemSelectedListener(spinner_ym_listener);
        spinner_m.setOnItemSelectedListener(spinner_ym_listener);



        //목표지출액 정하기 버튼
        Button.OnClickListener set_goal_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(stat.this, setting_targetex.class);
                intent.putExtra("stat_year", ((Spinner)findViewById(R.id.stat_spin_y)).getSelectedItem().toString());
                intent.putExtra("stat_month", ((Spinner)findViewById(R.id.stat_spin_m)).getSelectedItem().toString());
                intent.putExtra("remaining", ((TextView)findViewById(R.id.stat_lbl_goal_amount_g)).getText().toString());
                startActivityForResult(intent, 8888);
            }

        };
        Button set_goal = (Button) findViewById(R.id.stat_btn_setgoal);
        set_goal.setOnClickListener(set_goal_listener);

        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        };

        ImageButton back = (ImageButton) findViewById(R.id.stat_img_back);
        back.setOnClickListener(back_listener);
    }

    public void onResume()
    {
        super.onResume();


        //달리는 위치 수정
        TextView txt_all = (TextView) findViewById(R.id.stat_lbl_goal_amount_g);
        int all = Integer.parseInt(txt_all.getText().toString());
        TextView txt_part = (TextView) findViewById(R.id.stat_lbl_expenditure_g);
        int part = Integer.parseInt(txt_part.getText().toString());
        float percent = (float)part / (float)all;
        float percent_comp = 1 - percent;
        LinearLayout lin = (LinearLayout) findViewById(R.id.stat_runningtrack);

        if(percent >= 0.92)
        {
            percent = 0.92f;
            percent_comp = 0.08f;
        }

        View runningman_left = (View) findViewById(R.id.stat_runningman_left);
        runningman_left.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1, percent_comp));
        View runningman_right = (View) findViewById(R.id.stat_runningman_right);
        runningman_right.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1, percent));

        //프로그레스 바 수정
        ProgressBar pgbar = (ProgressBar) findViewById(R.id.stat_prg_goal_expenditure);
        if(part > all)
        {
            pgbar.setMax(1);
            pgbar.setProgress(1);
        }
        else {
            pgbar.setMax(all);
            pgbar.setProgress(part);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8888) //리퀘스트 코드를 성공적으로 받았다
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String wow = data.getStringExtra("targetex_return");
                TextView goal = (TextView) findViewById(R.id.stat_lbl_goal_amount);
                TextView goal_g = (TextView) findViewById(R.id.stat_lbl_goal_amount_g);
                goal.setText(String.format("%,d", parseInt(wow)));
                goal_g.setText(wow);
            }

            if(resultCode == Activity.RESULT_CANCELED)
            {

            }
        }
    }
}
