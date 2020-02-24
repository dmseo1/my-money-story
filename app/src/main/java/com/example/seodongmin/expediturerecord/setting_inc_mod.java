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
import android.widget.Toast;

import static com.example.seodongmin.expediturerecord.init.adapter_iconlist;
import static com.example.seodongmin.expediturerecord.init.adapter_inc;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.income_category_icon;
import static com.example.seodongmin.expediturerecord.init.init;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class setting_inc_mod extends AppCompatActivity {

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_inc_mod);

        Toast.makeText(setting_inc_mod.this, "아이콘을 변경하지 않을 경우 현재 아이콘을 눌러주세요.", Toast.LENGTH_SHORT).show();

        final EditText name = (EditText) findViewById(R.id.inc_mod_txt_name);


        Intent getintent = getIntent();
        final String getname = getintent.getStringExtra("incModiStr");
        final int getpos = getintent.getIntExtra("incModiPos", -1);
        if(getpos == -1) return;

        name.setText(getname);
        name.setSelection(name.length());


        //현재 이미지에 대한 클릭 리스너
        ImageView.OnClickListener currenticon_listener = new ImageView.OnClickListener()
        {
            public void onClick(View view)
            {
                String apply_name = name.getText().toString();
                if(apply_name.length() == 0)
                {
                    Toast.makeText(setting_inc_mod.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_inc_mod.this, "카테고리명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    for (int i = 0; i < income_category.size(); i++) {
                        if (name.getText().toString().equals(income_category.get(i))) {
                            if(i == getpos) continue;
                            Toast.makeText(setting_inc_mod.this, "이미 존재하는 카테고리명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    income_category.remove(getpos);
                    income_category.add(getpos, apply_name);
                    income_category_forsearch_1.remove(getpos + 1);
                    income_category_forsearch_1.add(getpos + 1, apply_name);
                    income_category_forsearch_2.remove(getpos + 1);
                    income_category_forsearch_2.add(getpos + 1, apply_name);
                    adapter_inc.getListItem().remove(getpos);
                    listview_category_list modlist = new listview_category_list();
                    modlist.setorder(getpos);
                    modlist.setname(apply_name);
                    modlist.seticon(income_category_icon.get(getpos));
                    modlist.setchecked(false);
                    adapter_inc.getListItem().add(getpos, modlist);



                    SharedPreferences pref_cat = getSharedPreferences("Income_Category", MODE_PRIVATE);
                    SharedPreferences pref_cat_icon = getSharedPreferences("Income_Category_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor_cat = pref_cat.edit();
                    editor_cat.putString("cat_" + getpos, apply_name);
                    editor_cat.commit();
                    editor_cat = pref_cat_icon.edit();
                    editor_cat.putString("cat_", init.makeEncodedImage(setting_inc_mod.this, income_category_icon.get(getpos)));
                    editor_cat.commit();


                    SharedPreferences pref_incexp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                    SharedPreferences.Editor editor_incexp = pref_incexp.edit();
                    String complete;


                    for(int i = 0; i < search_adapter.getCount() ; i ++)
                    {
                        if(search_adapter.getListItem().get(i).getwhere().equals(getname))
                        {
                            complete="";
                            String[] parse = pref_incexp.getString("incexp_" + i, "").split(":;:", 10);
                            System.out.println(parse[5]);
                            parse[5] = apply_name;
                            for(int j = 0; j < 10; j ++)
                            {
                                complete = complete + parse[j];
                                if(j != 9) complete = complete + ":;:";
                            }
                            editor_incexp.putString("incexp_" + i, complete);
                            editor_incexp.commit();



                            search_adapter.getListItem().get(i).setwhere(apply_name);
                        }
                    }
                    Intent intent_change = new Intent(setting_inc_mod.this, setting_inc.class);
                    intent_change.putExtra("Changed", 1);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_inc_mod.this, "카테고리가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        ImageView currenticon = (ImageView) findViewById(R.id.inc_mod_img_current);
        currenticon.setImageDrawable(income_category_icon.get(getpos));
        currenticon.setOnClickListener(currenticon_listener);



        //그리드뷰 정의와 그리드뷰 리스너
        gridview = (GridView) findViewById(R.id.inc_mod_iconlist);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                String apply_name = name.getText().toString();
                if(apply_name.length() == 0)
                {
                    Toast.makeText(setting_inc_mod.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_inc_mod.this, "카테고리명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {

                    for(int i = 0; i < income_category.size() ; i ++)
                    {
                        if(name.getText().toString().equals(income_category.get(i)))
                        {
                            if(i == getpos) continue;
                            Toast.makeText(setting_inc_mod.this, "이미 존재하는 카테고리명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Drawable icon = adapter_iconlist.getListItem().get(position);
                    income_category.remove(getpos);
                    income_category.add(getpos, apply_name);
                    income_category_forsearch_1.remove(getpos+1);
                    income_category_forsearch_1.add(getpos+1, apply_name);
                    income_category_forsearch_2.remove(getpos+1);
                    income_category_forsearch_2.add(getpos+1, apply_name);
                    income_category_icon.remove(getpos);
                    income_category_icon.add(getpos, icon);
                    adapter_inc.getListItem().remove(getpos);
                    listview_category_list modlist = new listview_category_list();
                    modlist.setorder(getpos);
                    modlist.setname(apply_name);
                    modlist.seticon(icon);
                    modlist.setchecked(false);
                    adapter_inc.getListItem().add(getpos, modlist);


                    SharedPreferences pref_cat = getSharedPreferences("Income_Category", MODE_PRIVATE);
                    SharedPreferences pref_cat_icon = getSharedPreferences("Income_Category_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor_cat = pref_cat.edit();
                    editor_cat.putString("cat_" + getpos, apply_name);
                    editor_cat.commit();
                    editor_cat = pref_cat_icon.edit();
                    editor_cat.putString("cat_", init.makeEncodedImage(setting_inc_mod.this, icon));
                    editor_cat.commit();


                    SharedPreferences pref_incexp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                    SharedPreferences.Editor editor_incexp = pref_incexp.edit();
                    String complete;





                    for(int i = 0; i < search_adapter.getCount() ; i ++)
                    {
                        if(search_adapter.getListItem().get(i).getwhere().equals(getname))
                        {

                            complete="";
                            String[] parse = pref_incexp.getString("incexp_" + i, "").split(":;:", 10);
                            parse[5] = apply_name;
                            for(int j = 0; j < 10; j ++)
                            {
                                complete = complete + parse[j];
                                if(j != 9) complete = complete + ":;:";
                            }
                            editor_incexp.putString("incexp_" + i, complete);
                            editor_incexp.commit();


                            search_adapter.getListItem().get(i).setwhereIcon(icon);
                            search_adapter.getListItem().get(i).setwhere(apply_name);
                        }
                    }
                    Intent intent_change = new Intent(setting_inc_mod.this, setting_inc.class);
                    intent_change.putExtra("Changed", 1);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_inc_mod.this, "카테고리가 수정되었습니다.", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(setting_inc_mod.this, iconloadwhere.class);
                intent.putExtra("CalledType", 2);
                startActivity(intent);
            };


        };
        Button loadicon = (Button) findViewById(R.id.inc_mod_btn_addicon);
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
        ImageButton back = (ImageButton) findViewById(R.id.inc_mod_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();
        gridview.setAdapter(adapter_iconlist);
    }

}
