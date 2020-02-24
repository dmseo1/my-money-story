package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


import static com.example.seodongmin.expediturerecord.init.adapter_exw;
import static com.example.seodongmin.expediturerecord.init.adapter_iconlist;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_type;
import static com.example.seodongmin.expediturerecord.init.init;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class setting_exw_mod extends AppCompatActivity {

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_exw_mod);

        Toast.makeText(setting_exw_mod.this, "아이콘을 변경하지 않을 경우 현재 아이콘을 눌러주세요.", Toast.LENGTH_SHORT).show();

        final EditText name = (EditText) findViewById(R.id.exw_mod_txt_name);
        final RadioButton type_now = (RadioButton) findViewById(R.id.exw_mod_rd_now);
        final RadioButton type_later = (RadioButton) findViewById(R.id.exw_mod_rd_later);


        Intent getintent = getIntent();
        final String getname = getintent.getStringExtra("exwModiStr");
        final int getpos = getintent.getIntExtra("exwModiPos", -1);
        final String type = getintent.getStringExtra("exwModiType");
        if(getpos == -1) return;


        name.setText(getname);
        name.setSelection(name.length());
        if(type.equals("1")) type_now.setChecked(true);
        else if(type.equals("2")) type_later.setChecked(true);
        else return;

        //현재 이미지에 대한 클릭 리스너
        ImageView.OnClickListener currenticon_listener = new ImageView.OnClickListener()
        {
            public void onClick(View view)
            {
                String apply_name = name.getText().toString();
                String apply_type;
                if(type_now.isChecked()) apply_type = "1";
                else if(type_later.isChecked()) apply_type = "2";
                else return;
                if(apply_name.length() == 0)
                {
                    Toast.makeText(setting_exw_mod.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_exw_mod.this, "카테고리명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    for (int i = 0; i < expenditure_way.size(); i++) {
                        if (name.getText().toString().equals(expenditure_way.get(i))) {
                            if(i == getpos) continue;
                            Toast.makeText(setting_exw_mod.this, "이미 존재하는 카테고리명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    expenditure_way.remove(getpos);
                    expenditure_way.add(getpos, apply_name);
                    expenditure_way_forsearch_1.remove(getpos + 1);
                    expenditure_way_forsearch_1.add(getpos + 1, apply_name);
                    expenditure_way_forsearch_2.remove(getpos + 1);
                    expenditure_way_forsearch_2.add(getpos + 1, apply_name);
                    expenditure_way_type.remove(getpos);
                    expenditure_way_type.add(getpos, apply_type);
                    adapter_exw.getListItem().remove(getpos);
                    listview_category_list modlist = new listview_category_list();
                    modlist.setorder(getpos);
                    modlist.setname(apply_name);
                    modlist.seticon(expenditure_way_icon.get(getpos));
                    modlist.setchecked(false);
                    adapter_exw.getListItem().add(getpos, modlist);



                    SharedPreferences pref_cat = getSharedPreferences("Expenditure_Way", MODE_PRIVATE);
                    SharedPreferences pref_cat_icon = getSharedPreferences("Expenditure_Way_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor_cat = pref_cat.edit();
                    editor_cat.putString("cat_" + getpos, apply_name + ":;:" + apply_type);
                    editor_cat.commit();
                    editor_cat = pref_cat_icon.edit();
                    editor_cat.putString("cat_", init.makeEncodedImage(setting_exw_mod.this, expenditure_way_icon.get(getpos)));
                    editor_cat.commit();


                    SharedPreferences pref_incexp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                    SharedPreferences.Editor editor_incexp = pref_incexp.edit();
                    String complete;


                    for(int i = 0; i < search_adapter.getCount() ; i ++)
                    {
                        if(search_adapter.getListItem().get(i).getway().equals(getname))
                        {

                            complete = "";
                            String[] parse = pref_incexp.getString("incexp_" + i, "").split(":;:", 10);
                            System.out.println(parse[5]);
                            parse[4] = apply_name;
                            for(int j = 0; j < 10; j ++)
                            {
                                complete = complete + parse[j];
                                if(j != 9) complete = complete + ":;:";
                            }
                            editor_incexp.putString("incexp_" + i, complete);
                            editor_incexp.commit();




                            search_adapter.getListItem().get(i).setway(apply_name);
                        }
                    }
                    Intent intent_change = new Intent(setting_exw_mod.this, setting_exw.class);
                    intent_change.putExtra("Changed", 2);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_exw_mod.this, "카테고리가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        ImageView currenticon = (ImageView) findViewById(R.id.exw_mod_img_current);
        currenticon.setImageDrawable(expenditure_way_icon.get(getpos));
        currenticon.setOnClickListener(currenticon_listener);



        //그리드뷰 정의와 그리드뷰 리스너
        gridview = (GridView) findViewById(R.id.exw_mod_iconlist);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                String apply_name = name.getText().toString();
                String apply_type;
                if(type_now.isChecked()) apply_type = "1";
                else if(type_later.isChecked()) apply_type = "2";
                else return;
                if(apply_name.length() == 0)
                {
                    Toast.makeText(setting_exw_mod.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_exw_mod.this, "카테고리명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    for(int i = 0; i < expenditure_way.size() ; i ++)
                    {
                        if(name.getText().toString().equals(expenditure_way.get(i)))
                        {
                            if(i == getpos) continue;
                            Toast.makeText(setting_exw_mod.this, "이미 존재하는 카테고리명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Drawable icon = adapter_iconlist.getListItem().get(position);
                    expenditure_way.remove(getpos);
                    expenditure_way.add(getpos, apply_name);
                    expenditure_way_forsearch_1.remove(getpos+1);
                    expenditure_way_forsearch_1.add(getpos+1, apply_name);
                    expenditure_way_forsearch_2.remove(getpos+1);
                    expenditure_way_forsearch_2.add(getpos+1, apply_name);
                    expenditure_way_icon.remove(getpos);
                    expenditure_way_icon.add(getpos, icon);
                    adapter_exw.getListItem().remove(getpos);
                    listview_category_list modlist = new listview_category_list();
                    modlist.setorder(getpos);
                    modlist.setname(apply_name);
                    modlist.seticon(icon);
                    modlist.setchecked(false);
                    adapter_exw.getListItem().add(getpos, modlist);


                    SharedPreferences pref_cat = getSharedPreferences("Expenditure_Way", MODE_PRIVATE);
                    SharedPreferences pref_cat_icon = getSharedPreferences("Expenditure_Way_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor_cat = pref_cat.edit();
                    editor_cat.putString("cat_" + getpos, apply_name + ":;:" + apply_type);
                    editor_cat.commit();
                    editor_cat = pref_cat_icon.edit();
                    editor_cat.putString("cat_", init.makeEncodedImage(setting_exw_mod.this, icon));
                    editor_cat.commit();


                    SharedPreferences pref_incexp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                    SharedPreferences.Editor editor_incexp = pref_incexp.edit();
                    String complete;



                    for(int i = 0; i < search_adapter.getCount() ; i ++)
                    {
                        if(search_adapter.getListItem().get(i).getway().equals(getname))
                        {
                            complete="";
                            String[] parse = pref_incexp.getString("incexp_" + i, "").split(":;:", 10);
                            System.out.println(parse[4]);
                            parse[4] = apply_name;
                            for(int j = 0; j < 10; j ++)
                            {
                                complete = complete + parse[j];
                                if(j != 9) complete = complete + ":;:";
                            }
                            editor_incexp.putString("incexp_" + i, complete);
                            System.out.println("완성: " + complete);
                            editor_incexp.commit();


                            search_adapter.getListItem().get(i).setwayIcon(icon);
                            search_adapter.getListItem().get(i).setway(apply_name);
                        }
                    }
                    Intent intent_change = new Intent(setting_exw_mod.this, setting_exw.class);
                    intent_change.putExtra("Changed", 2);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_exw_mod.this, "카테고리가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        //갤러리에서 아이콘 불러오기 리스너
        Button.OnClickListener loadicon_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting_exw_mod.this, iconloadwhere.class);
                intent.putExtra("CalledType", 2);
                startActivity(intent);
            };


        };
        Button loadicon = (Button) findViewById(R.id.exw_mod_btn_addicon);
        loadicon.setOnClickListener(loadicon_listener);


        //뒤로가기 버튼
        ImageButton.OnClickListener back_listener = new ImageButton.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }

        };
        ImageButton back = (ImageButton) findViewById(R.id.exw_mod_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();
        gridview.setAdapter(adapter_iconlist);
    }

}
