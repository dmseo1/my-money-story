package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.seodongmin.expediturerecord.init.adapter_exw;
import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_type;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-24.
 */

public class setting_exw extends AppCompatActivity {

    Parcelable state;
    ListView listview;
    CheckBox selectall;
    int changed;
    int MODE_CHANGE = 9999;
    static boolean exw_modiMode = false; //복붙시 수정대상
    boolean isIgnoreCheck = false;
    boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_exw);

        for(int i = 0; i < adapter_exw.getListItem().size(); i++)
        {
            if(adapter_exw.getListItem().get(i).getchecked())
            {
                System.out.println(i + "번이 체크된 상태임");
            }
        }

        CheckBox.OnCheckedChangeListener selectall_listener = new CheckBox.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    for(int i = 0; i < adapter_exw.getListItem().size() ; i ++)
                    {
                        adapter_exw.getListItem().get(i).setchecked(true);
                        Intent intent = new Intent(setting_exw.this, nowsearching.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    for(int i = 0; i < adapter_exw.getListItem().size() ; i ++)
                    {
                        adapter_exw.getListItem().get(i).setchecked(false);
                        Intent intent = new Intent(setting_exw.this, nowsearching.class);
                        startActivity(intent);
                    }
                }
            }
        };
        selectall = (CheckBox) findViewById(R.id.exw_chk_selectall);
        selectall.setOnCheckedChangeListener(selectall_listener);


        listview = (ListView) findViewById(R.id.exw_list);

        final Button modifymode = (Button) findViewById(R.id.exw_btn_modimode);
        //수정모드 버튼 초기화
        if(exw_modiMode) //수
        {
            modifymode.setText("수정모드해제");
        }
        else
        {
            modifymode.setText("수정모드");
        }



        //수정모드 토글 버튼
        Button.OnClickListener modifymode_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!exw_modiMode) {
                    exw_modiMode = true;
                    Intent intent = new Intent(setting_exw.this, nowsearching.class);
                    modifymode.setText("수정모드해제");
                    startActivity(intent);
                }
                else {
                    exw_modiMode = false;
                    Intent intent = new Intent(setting_exw.this, nowsearching.class);
                    modifymode.setText("수정모드");
                    startActivity(intent);
                }
            }
        };
        modifymode.setOnClickListener(modifymode_listener);


        //카테고리 삭제 버튼
        Button.OnClickListener delete_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int cnt = 0;
                for(int i = 0; i < adapter_exw.getListItem().size() ; i ++)
                {
                    if(adapter_exw.getListItem().get(i).getchecked())
                    {
                        if(i == 0) continue;
                        cnt ++;
                    }
                }

                if (cnt == 0) return;
                Intent verify = new Intent(setting_exw.this, delcategory_question.class);
                verify.putExtra("DelType", 3);
                verify.putExtra("DelCnt", cnt);
                startActivityForResult(verify, 8888);
            }
        };

        Button delete = (Button) findViewById(R.id.exw_btn_chkremove);
        delete.setOnClickListener(delete_listener);


        //카테고리 추가 버튼
        ImageView.OnClickListener add_listener = new ImageView.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting_exw.this, setting_exw_add.class);
                startActivityForResult(intent, MODE_CHANGE);
            }
        };
        ImageView add = (ImageView) findViewById(R.id.exw_imgbtn_add);
        add.setOnClickListener(add_listener);



        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent_changed = new Intent(setting_exw.this, search_details_ex_m.class);
                intent_changed.putExtra("Changed_m", changed);
                setResult(RESULT_OK, intent_changed);
                finish();
            }
        };
        ImageButton back = (ImageButton) findViewById(R.id.setting_exw_back);
        back.setOnClickListener(back_listener);
    }

    public void onResume()
    {
        super.onResume();

        //이전에 체크해뒀던 상태 모두 해제시키기
        if(!isIgnoreCheck) {
            if (!selectall.isChecked()) {
                for (int i = 0; i < adapter_exw.getListItem().size(); i++) {
                    if (adapter_exw.getListItem().get(i).getchecked()) {
                        System.out.println(i + "번이 체크되어 있어서 해제시킴");
                        adapter_exw.getListItem().get(i).setchecked(false);
                    }
                }
            }
        }

        listview.setAdapter(adapter_exw);

        if (state != null) { // 리스트뷰 상태가 있는 경우
            listview.onRestoreInstanceState(state); // 리스트뷰 스크롤 위치 복구
        }

    }

    public void onPause()
    {
        super.onPause();
        state = listview.onSaveInstanceState();
        if(isBackPressed)
        {
            Intent intent_changed = new Intent(setting_exw.this, search_details_ex_m.class);
            intent_changed.putExtra("Changed_m", changed);
            setResult(RESULT_OK, intent_changed);
            isBackPressed = false;
            finish();
        }
    }

    public void onDestroy()
    {
        Intent intent = new Intent(setting_exw.this, search_details_ex_m.class);
        setResult(RESULT_OK, intent);
        super.onDestroy();
        exw_modiMode = false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8888) //리퀘스트 코드를 성공적으로 받았다
        {
            int returnType;
            if(resultCode == Activity.RESULT_OK)
            {
                changed = data.getIntExtra("Changed", 0);
                returnType = data.getIntExtra("Hoksina", 0);
                if(returnType == 3) {

                    //해당 지출 수단을 삭제
                    ArrayList<String> delname = new ArrayList();


                    SharedPreferences pref_str = getSharedPreferences("Expenditure_Way", MODE_PRIVATE);
                    SharedPreferences pref_ico = getSharedPreferences("Expenditure_Way_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor_str = pref_str.edit();
                    SharedPreferences.Editor editor_ico = pref_ico.edit();


                    int length = adapter_exw.getListItem().size();
                    for (int i = 0; i < length; i++) {
                        if (adapter_exw.getListItem().get(i).getchecked()) {
                            if (i == 0) continue;
                            delname.add(expenditure_way.get(i));
                            adapter_exw.getListItem().remove(i);
                            expenditure_way.remove(i);
                            expenditure_way_forsearch_1.remove(i + 1);
                            expenditure_way_forsearch_2.remove(i + 1);
                            expenditure_way_icon.remove(i);
                            expenditure_way_type.remove(i);

                            int j;
                            for(j = i; j < length; j++)
                            {
                                editor_str.putString("cat_" + j, pref_str.getString("cat_" + (j+1), ""));
                                editor_ico.putString("caticon_" + j, pref_ico.getString("caticon_" + (j+1), ""));
                            }
                            editor_str.putString("cat_" + (j-1), "");
                            editor_ico.putString("caticon_" + (j-1), "");
                            editor_str.commit();
                            editor_ico.commit();



                            length--;
                            i--;
                        }
                    }

                    //해당 지출 수단에 연관된 자료를 '분류없음'으로 넘김

                    SharedPreferences pref_incexp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                    SharedPreferences.Editor editor_incexp = pref_incexp.edit();
                    String complete;

                    for(int i = 0; i < search_adapter.getCount(); i++)
                    {
                        for(int j = 0; j < delname.size(); j ++)
                        {
                            if(search_adapter.getListItem().get(i).getway().equals(delname.get(j)))
                            {


                                String[] parse = pref_incexp.getString("incexp_" + i, "").split(":;:", 10);
                                parse[4] = expenditure_category.get(0);
                                System.out.println("parse[5]: " + parse[4]);
                                complete = "";
                                for(int k = 0; k < 10; k ++)
                                {
                                    complete = complete + parse[k];
                                    if(k != 9) complete = complete + ":;:";
                                }
                                editor_incexp.putString("incexp_" + i, complete);
                                editor_incexp.commit();


                                search_adapter.getListItem().get(i).setway(expenditure_way.get(0));
                                search_adapter.getListItem().get(i).setwayIcon(expenditure_way_icon.get(0));
                            }
                        }
                    }
                    Toast.makeText(setting_exw.this, "삭제되었습니다.", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(setting_exw.this, nowsearching.class);
                    startActivity(intent);
                }

                else if(data.getBooleanExtra("isDelWindow", false))
                {
                    isIgnoreCheck = true;
                }
            }
        }

        if(requestCode == MODE_CHANGE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                changed = data.getIntExtra("Changed", 0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        return;
       /* super.onBackPressed();
        isBackPressed = true;*/
    }
}
