package com.example.seodongmin.expediturerecord;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import static com.example.seodongmin.expediturerecord.init.adapter_exc;
import static com.example.seodongmin.expediturerecord.init.adapter_iconlist;
import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.init;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class setting_exc_add  extends AppCompatActivity {

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_exc_add);

        final EditText name = (EditText) findViewById(R.id.exc_add_txt_name);

        gridview = (GridView) findViewById(R.id.exc_add_iconlist);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                String apply_name = name.getText().toString();
                if(apply_name.length() == 0)
                {
                    Toast.makeText(setting_exc_add.this, "카테고리명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_exc_add.this, "카테고리명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    for(int i = 0; i < expenditure_category.size() ; i ++)
                    {
                        if(name.getText().toString().equals(expenditure_category.get(i)))
                        {
                            Toast.makeText(setting_exc_add.this, "이미 존재하는 카테고리명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Drawable icon = adapter_iconlist.getListItem().get(position);

                    int newpos = expenditure_category.size();
                    SharedPreferences pref_str = getSharedPreferences("Expenditure_Category", MODE_PRIVATE);
                    SharedPreferences pref_ico = getSharedPreferences("Expenditure_Category_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref_str.edit();
                    editor.putString("cat_" + newpos, name.getText().toString());
                    editor.commit();
                    editor = pref_ico.edit();
                    editor.putString("caticon_" + newpos, init.makeEncodedImage(setting_exc_add.this, icon));
                    editor.commit();


                    expenditure_category.add(apply_name);
                    expenditure_category_forsearch_1.add(apply_name);
                    expenditure_category_forsearch_2.add(apply_name);
                    expenditure_category_icon.add(icon);
                    adapter_exc.addItem(setting_exc_add.this,apply_name,icon);
                    Intent intent_change = new Intent(setting_exc_add.this, setting_exc.class);
                    intent_change.putExtra("Changed", 1);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_exc_add.this, "카테고리가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        Button.OnClickListener loadicon_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting_exc_add.this, iconloadwhere.class);
                intent.putExtra("CalledType", 2);
                startActivity(intent);
            };


        };
        Button loadicon = (Button) findViewById(R.id.exc_add_btn_addicon);
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
        ImageButton back = (ImageButton) findViewById(R.id.exc_add_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();
        gridview.setAdapter(adapter_iconlist);
    }

}
