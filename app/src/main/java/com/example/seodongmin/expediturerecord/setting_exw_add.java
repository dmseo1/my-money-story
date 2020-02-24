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
import android.widget.RadioButton;
import android.widget.Toast;

import static com.example.seodongmin.expediturerecord.init.adapter_exw;
import static com.example.seodongmin.expediturerecord.init.adapter_iconlist;
import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_type;
import static com.example.seodongmin.expediturerecord.init.init;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class setting_exw_add extends AppCompatActivity {

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_exw_add);

        final EditText name = (EditText) findViewById(R.id.exw_add_txt_name);
        final RadioButton type_now = (RadioButton) findViewById(R.id.exw_add_rd_now);
        final RadioButton type_later = (RadioButton) findViewById(R.id.exw_add_rd_later);




        gridview = (GridView) findViewById(R.id.exw_add_iconlist);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                String apply_name = name.getText().toString();
                if(apply_name.length() == 0)
                {
                    Toast.makeText(setting_exw_add.this, "지출수단명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(apply_name.length() > 5)
                {
                    Toast.makeText(setting_exw_add.this, "지출수단명은 5자 이하로 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    final String type;
                    if(type_now.isChecked()) type = "1";
                    else if(type_later.isChecked()) type = "2";
                    else return;

                    for(int i = 0; i < expenditure_way.size() ; i ++)
                    {
                        if(name.getText().toString().equals(expenditure_way.get(i)))
                        {
                            Toast.makeText(setting_exw_add.this, "이미 존재하는 지출수단명입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Drawable icon = adapter_iconlist.getListItem().get(position);

                    int newpos = expenditure_way.size();
                    SharedPreferences pref_str = getSharedPreferences("Expenditure_Way", MODE_PRIVATE);
                    SharedPreferences pref_ico = getSharedPreferences("Expenditure_Way_Icon", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref_str.edit();
                    editor.putString("cat_" + newpos, name.getText().toString() + ":;:" + type);
                    editor.commit();
                    editor = pref_ico.edit();
                    editor.putString("caticon_" + newpos, init.makeEncodedImage(setting_exw_add.this, icon));
                    editor.commit();


                    expenditure_way.add(apply_name);
                    expenditure_way_forsearch_1.add(apply_name);
                    expenditure_way_forsearch_2.add(apply_name);
                    expenditure_way_icon.add(icon);
                    expenditure_way_type.add(type);
                    adapter_exw.addItem(setting_exw_add.this,apply_name,icon);
                    Intent intent_change = new Intent(setting_exw_add.this, setting_exw.class);
                    intent_change.putExtra("Changed", 2);
                    setResult(RESULT_OK, intent_change);
                    Toast.makeText(setting_exw_add.this, "지출수단이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        Button.OnClickListener loadicon_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(setting_exw_add.this, iconloadwhere.class);
                intent.putExtra("CalledType", 2);
                startActivity(intent);
            };


        };
        Button loadicon = (Button) findViewById(R.id.exw_add_btn_addicon);
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
        ImageButton back = (ImageButton) findViewById(R.id.exw_add_back);
        back.setOnClickListener(back_listener);

    }

    public void onResume()
    {
        super.onResume();
        gridview.setAdapter(adapter_iconlist);
    }

}
