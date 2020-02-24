package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import static com.example.seodongmin.expediturerecord.R.id.search_list;
import static com.example.seodongmin.expediturerecord.init.adapter_exc;
import static com.example.seodongmin.expediturerecord.init.adapter_iconlist;
import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.init;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class setting_exc_mod  extends AppCompatActivity {

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_exc_mod);

        Toast.makeText(setting_exc_mod.this, "아이콘을 변경하지 않을 경우 현재 아이콘을 눌러주세요.", Toast.LENGTH_SHORT).show();

        final EditText name = (EditText) findViewById(R.id.exc_mod_txt_name);


        Intent getintent = getIntent();
        final String getname = getintent.getStringExtra("excModiStr");
        final int getpos = getintent.getIntExtra("excModiPos", -1);
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
                    Toast.makeText(setting_exc_mod.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_exc_mod.this, "카테고리명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    for (int i = 0; i < expenditure_category.size(); i++) {
                        if (name.getText().toString().equals(expenditure_category.get(i))) {
                            if(i == getpos) continue;
                            Toast.makeText(setting_exc_mod.this, "이미 존재하는 카테고리명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    expenditure_category.remove(getpos);
                    expenditure_category.add(getpos, apply_name);
                    expenditure_category_forsearch_1.remove(getpos + 1);
                    expenditure_category_forsearch_1.add(getpos + 1, apply_name);
                    expenditure_category_forsearch_2.remove(getpos + 1);
                    expenditure_category_forsearch_2.add(getpos + 1, apply_name);
                    adapter_exc.getListItem().remove(getpos);
                    listview_category_list modlist = new listview_category_list();
                    modlist.setorder(getpos);
                    modlist.setname(apply_name);
                    modlist.seticon(expenditure_category_icon.get(getpos));
                    modlist.setchecked(false);
                    adapter_exc.getListItem().add(getpos, modlist);

                    SharedPreferences pref_cat = getSharedPreferences("Expenditure_Category", MODE_PRIVATE);
                    SharedPreferences pref_cat_icon = getSharedPreferences("Expenditure_Category_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor_cat = pref_cat.edit();
                    editor_cat.putString("cat_" + getpos, apply_name);
                    editor_cat.commit();
                    editor_cat = pref_cat_icon.edit();
                    editor_cat.putString("cat_", init.makeEncodedImage(setting_exc_mod.this, expenditure_category_icon.get(getpos)));
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


                    Intent intent_change = new Intent(setting_exc_mod.this, setting_exc.class);
                    intent_change.putExtra("Changed", 1);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_exc_mod.this, "카테고리가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        ImageView currenticon = (ImageView) findViewById(R.id.exc_mod_img_current);
        currenticon.setImageDrawable(expenditure_category_icon.get(getpos));
        currenticon.setOnClickListener(currenticon_listener);



        //그리드뷰 정의와 그리드뷰 리스너
        gridview = (GridView) findViewById(R.id.exc_mod_iconlist);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                String apply_name = name.getText().toString();
                if(apply_name.length() == 0)
                {
                    Toast.makeText(setting_exc_mod.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_exc_mod.this, "카테고리명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    for(int i = 0; i < expenditure_category.size() ; i ++)
                    {
                        if(name.getText().toString().equals(expenditure_category.get(i)))
                        {
                            if(i == getpos) continue;
                            Toast.makeText(setting_exc_mod.this, "이미 존재하는 카테고리명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Drawable icon = adapter_iconlist.getListItem().get(position);
                    expenditure_category.remove(getpos);
                    expenditure_category.add(getpos, apply_name);
                    expenditure_category_forsearch_1.remove(getpos+1);
                    expenditure_category_forsearch_1.add(getpos+1, apply_name);
                    expenditure_category_forsearch_2.remove(getpos+1);
                    expenditure_category_forsearch_2.add(getpos+1, apply_name);
                    expenditure_category_icon.remove(getpos);
                    expenditure_category_icon.add(getpos, icon);
                    adapter_exc.getListItem().remove(getpos);
                    listview_category_list modlist = new listview_category_list();
                    modlist.setorder(getpos);
                    modlist.setname(apply_name);
                    modlist.seticon(icon);
                    modlist.setchecked(false);
                    adapter_exc.getListItem().add(getpos, modlist);




                    SharedPreferences pref_cat = getSharedPreferences("Expenditure_Category", MODE_PRIVATE);
                    SharedPreferences pref_cat_icon = getSharedPreferences("Expenditure_Category_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor_cat = pref_cat.edit();
                    editor_cat.putString("cat_" + getpos, apply_name);
                    editor_cat.commit();
                    editor_cat = pref_cat_icon.edit();
                    editor_cat.putString("cat_", init.makeEncodedImage(setting_exc_mod.this, icon));
                    editor_cat.commit();


                    SharedPreferences pref_incexp = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);
                    SharedPreferences.Editor editor_incexp = pref_incexp.edit();
                    String complete;





                    for(int i = 0; i < search_adapter.getCount() ; i ++)
                    {
                        if(search_adapter.getListItem().get(i).getwhere().equals(getname))
                        {

                            complete = "";
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
                    Intent intent_change = new Intent(setting_exc_mod.this, setting_exc.class);
                    intent_change.putExtra("Changed", 1);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_exc_mod.this, "카테고리가 수정되었습니다.", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(setting_exc_mod.this, iconloadwhere.class);
                intent.putExtra("CalledType", 2);
                startActivity(intent);
            };


        };
        Button loadicon = (Button) findViewById(R.id.exc_mod_btn_addicon);
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
        ImageButton back = (ImageButton) findViewById(R.id.exc_mod_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();
        gridview.setAdapter(adapter_iconlist);
    }

}
