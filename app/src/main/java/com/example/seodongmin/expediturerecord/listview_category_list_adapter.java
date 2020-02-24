package com.example.seodongmin.expediturerecord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_type;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.income_category_icon;
import static com.example.seodongmin.expediturerecord.setting_exc.exc_modiMode;
import static com.example.seodongmin.expediturerecord.setting_exw.exw_modiMode;
import static com.example.seodongmin.expediturerecord.setting_inc.inc_modiMode;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class listview_category_list_adapter extends BaseAdapter {

    int type = 0;
    private ArrayList<listview_category_list> ListItem = new ArrayList<listview_category_list>();

    public listview_category_list_adapter(int type)
    {
        this.type = type;
    }

    public ArrayList<listview_category_list> getListItem()
    {
        return ListItem;
    }


    @Override
    public int getCount()
    {
        return ListItem.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int pos = position;
        final Context context = parent.getContext();
        final Activity activity = (Activity) parent.getContext();

        // Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_category_list, parent, false);
        }



        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final CheckBox chk = (CheckBox) convertView.findViewById(R.id.clist_chk);
        final Button btn_modify = (Button) convertView.findViewById(R.id.clist_btn_modify);
        final Button btn_up = (Button) convertView.findViewById(R.id.clist_btn_up);
        final Button btn_down = (Button) convertView.findViewById(R.id.clist_btn_down);
        final ImageView icon = (ImageView) convertView.findViewById(R.id.clist_icon);
        final TextView name = (TextView) convertView.findViewById(R.id.clist_lbl_name);

        if(inc_modiMode || exc_modiMode || exw_modiMode)
        {
            btn_modify.setVisibility(View.VISIBLE);
            btn_up.setVisibility(View.VISIBLE);
            btn_down.setVisibility(View.VISIBLE);
        }
        else {
            btn_modify.setVisibility(View.GONE);
            btn_up.setVisibility(View.GONE);
            btn_down.setVisibility(View.GONE);
        }

        if(ListItem.get(pos).getchecked())
        {
            chk.setChecked(true);
        }
        else
        {
            chk.setChecked(false);
        }

        //수정모드 - 수정 버튼을 클릭했을 때
        Button.OnClickListener btn_modify_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(pos == 0) return;
                switch(type)
                {
                    case 1:
                        Intent inc_intent = new Intent(context, setting_inc_mod.class);
                        inc_intent.putExtra("incModiStr", ListItem.get(pos).getname());
                        inc_intent.putExtra("incModiPos", pos);
                        activity.startActivityForResult(inc_intent, 9999);

                        break;
                    case 2:
                        Intent exc_intent = new Intent(context, setting_exc_mod.class);
                        exc_intent.putExtra("excModiStr", ListItem.get(pos).getname());
                        exc_intent.putExtra("excModiPos", pos);
                        activity.startActivityForResult(exc_intent, 9999);
                        break;
                    case 3:
                        Intent exw_intent = new Intent(context, setting_exw_mod.class);
                        exw_intent.putExtra("exwModiStr", ListItem.get(pos).getname());
                        exw_intent.putExtra("exwModiPos", pos);
                        exw_intent.putExtra("exwModiType", expenditure_way_type.get(pos));
                        activity.startActivityForResult(exw_intent, 9999);
                        break;
                    default:
                        break;
                }


            }
        };
        btn_modify.setOnClickListener(btn_modify_listener);


        //수정모드 - 위로 옮기기 버튼을 클릭했을 때
        Button.OnClickListener btn_up_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listview_category_list previous; //임시 저장

                if(pos == 1) return;

                try {
                    previous = ListItem.get(pos - 1);
                } catch(java.lang.IndexOutOfBoundsException e) { return; }


                ListItem.remove(pos - 1);
                ListItem.add(pos, previous); // 당장 보고 있는 리스트 변경

                switch(type) {

                    case 1:
                        String previous_string_inc = income_category.get(pos - 1);
                        income_category.remove(pos - 1);
                        income_category.add(pos, previous_string_inc);
                        income_category_forsearch_1.remove(pos);
                        income_category_forsearch_1.add(pos + 1, previous_string_inc);
                        income_category_forsearch_2.remove(pos);
                        income_category_forsearch_2.add(pos + 1, previous_string_inc);

                        Drawable previous_icon_inc = income_category_icon.get(pos - 1);
                        income_category_icon.remove(pos - 1);
                        income_category_icon.add(pos, previous_icon_inc); //기존 리스트들 모두 변경

                        //DB의 경우, 그냥 swap만 해주면 되네? 개꿀ㅇㅈ?? ㅇ ㅇㅈ
                        SharedPreferences sf_inc_str = context.getSharedPreferences("Income_Category", context.MODE_PRIVATE);
                        SharedPreferences sf_inc_ico = context.getSharedPreferences("Income_Category_Icon", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_inc = sf_inc_str.edit();
                        editor_inc.putString("cat_" + (pos -1), income_category.get(pos-1));
                        editor_inc.putString("cat_" + (pos), income_category.get(pos));
                        editor_inc.commit();
                        editor_inc = sf_inc_ico.edit();
                        String inc_ico_temp = sf_inc_ico.getString("caticon_" +(pos-1), "");
                        editor_inc.putString("caticon_" + (pos-1), sf_inc_ico.getString("caticon_" +(pos), ""));
                        editor_inc.putString("caticon_" + (pos), inc_ico_temp);
                        editor_inc.commit();
                        break;
                    case 2:
                        String previous_string_exc = expenditure_category.get(pos - 1);
                        expenditure_category.remove(pos - 1);
                        expenditure_category.add(pos, previous_string_exc);
                        expenditure_category_forsearch_1.remove(pos);
                        expenditure_category_forsearch_1.add(pos + 1, previous_string_exc);
                        expenditure_category_forsearch_2.remove(pos);
                        expenditure_category_forsearch_2.add(pos + 1, previous_string_exc);

                        Drawable previous_icon_exc = expenditure_category_icon.get(pos - 1);
                        expenditure_category_icon.remove(pos - 1);
                        expenditure_category_icon.add(pos, previous_icon_exc); //기존 리스트들 모두 변경


                        //DB의 경우, 그냥 swap만 해주면 되네? 개꿀ㅇㅈ?? ㅇ ㅇㅈ
                        SharedPreferences sf_exc_str = context.getSharedPreferences("Expenditure_Category", context.MODE_PRIVATE);
                        SharedPreferences sf_exc_ico = context.getSharedPreferences("Expenditure_Category_Icon", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_exc = sf_exc_str.edit();
                        editor_exc.putString("cat_" + (pos -1), expenditure_category.get(pos-1));
                        editor_exc.putString("cat_" + (pos), expenditure_category.get(pos));
                        editor_exc.commit();
                        editor_exc = sf_exc_ico.edit();
                        String exc_ico_temp = sf_exc_ico.getString("caticon_" +(pos-1), "");
                        editor_exc.putString("caticon_" + (pos-1), sf_exc_ico.getString("caticon_" +(pos), ""));
                        editor_exc.putString("caticon_" + (pos), exc_ico_temp);
                        editor_exc.commit();

                        break;
                    case 3:
                        String previous_string_exw = expenditure_way.get(pos - 1);
                        String previous_exw_type = expenditure_way_type.get(pos - 1);
                        expenditure_way.remove(pos - 1);
                        expenditure_way.add(pos, previous_string_exw);
                        expenditure_way_forsearch_1.remove(pos);
                        expenditure_way_forsearch_1.add(pos + 1, previous_string_exw);
                        expenditure_way_forsearch_2.remove(pos);
                        expenditure_way_forsearch_2.add(pos + 1, previous_string_exw);
                        expenditure_way_type.remove(pos - 1);
                        expenditure_way_type.add(pos, previous_exw_type);

                        Drawable previous_icon_exw = expenditure_way_icon.get(pos - 1);
                        expenditure_way_icon.remove(pos - 1);
                        expenditure_way_icon.add(pos, previous_icon_exw);


                        //DB의 경우, 그냥 swap만 해주면 되네? 개꿀ㅇㅈ?? ㅇ ㅇㅈ
                        SharedPreferences sf_exw_str = context.getSharedPreferences("Expenditure_Way", context.MODE_PRIVATE);
                        SharedPreferences sf_exw_ico = context.getSharedPreferences("Expenditure_Way_Icon", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_exw = sf_exw_str.edit();
                        editor_exw.putString("cat_" + (pos -1), expenditure_way.get(pos-1) + ":;:" + expenditure_way_type.get(pos-1));
                        editor_exw.putString("cat_" + (pos), expenditure_way.get(pos) + ":;:" + expenditure_way_type.get(pos));
                        editor_exw.commit();
                        editor_exw = sf_exw_ico.edit();
                        String exw_ico_temp = sf_exw_ico.getString("caticon_" +(pos-1), "");
                        editor_exw.putString("caticon_" + (pos-1), sf_exw_ico.getString("caticon_" +(pos), ""));
                        editor_exw.putString("caticon_" + (pos), exw_ico_temp);
                        editor_exw.commit();
                        break;
                    default:
                        break;
                }

                Intent intent = new Intent(context, nowsearching.class);
                context.startActivity(intent);
            }
        };
        btn_up.setOnClickListener(btn_up_listener);


        Button.OnClickListener btn_down_listener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listview_category_list me;

                if(pos == 0) return;

                try {
                    me = ListItem.get(pos);
                    ListItem.get(pos+1);
                } catch(java.lang.IndexOutOfBoundsException e) { return; }

                ListItem.remove(pos);
                ListItem.add(pos+1, me);

                switch(type)
                {
                    case 1:
                        String me_string_inc = income_category.get(pos);
                        income_category.remove(pos);
                        income_category.add(pos+1, me_string_inc);
                        income_category_forsearch_1.remove(pos+1);
                        income_category_forsearch_1.add(pos+2, me_string_inc);
                        income_category_forsearch_2.remove(pos+1);
                        income_category_forsearch_2.add(pos+2, me_string_inc);

                        Drawable me_icon_inc = income_category_icon.get(pos);
                        income_category_icon.remove(pos);
                        income_category_icon.add(pos+1, me_icon_inc);

                        //DB의 경우, 그냥 swap만 해주면 되네? 개꿀ㅇㅈ?? ㅇ ㅇㅈ
                        SharedPreferences sf_inc_str = context.getSharedPreferences("Income_Category", context.MODE_PRIVATE);
                        SharedPreferences sf_inc_ico = context.getSharedPreferences("Income_Category_Icon", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_inc = sf_inc_str.edit();
                        editor_inc.putString("cat_" + (pos), income_category.get(pos));
                        editor_inc.putString("cat_" + (pos+1), income_category.get(pos+1));
                        editor_inc.commit();
                        editor_inc = sf_inc_ico.edit();
                        String inc_ico_temp = sf_inc_ico.getString("caticon_" +(pos), "");
                        editor_inc.putString("caticon_" + (pos), sf_inc_ico.getString("caticon_" +(pos+1), ""));
                        editor_inc.putString("caticon_" + (pos+1), inc_ico_temp);
                        editor_inc.commit();


                        break;
                    case 2:
                        String me_string_exc = expenditure_category.get(pos);
                        expenditure_category.remove(pos);
                        expenditure_category.add(pos+1, me_string_exc);
                        expenditure_category_forsearch_1.remove(pos+1);
                        expenditure_category_forsearch_1.add(pos+2, me_string_exc);
                        expenditure_category_forsearch_2.remove(pos+1);
                        expenditure_category_forsearch_2.add(pos+2, me_string_exc);

                        Drawable me_icon_exc = expenditure_category_icon.get(pos);
                        expenditure_category_icon.remove(pos);
                        expenditure_category_icon.add(pos+1, me_icon_exc);

                        //DB의 경우, 그냥 swap만 해주면 되네? 개꿀ㅇㅈ?? ㅇ ㅇㅈ
                        SharedPreferences sf_exc_str = context.getSharedPreferences("Expenditure_Category", context.MODE_PRIVATE);
                        SharedPreferences sf_exc_ico = context.getSharedPreferences("Expenditure_Category_Icon", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_exc = sf_exc_str.edit();
                        editor_exc.putString("cat_" + (pos), expenditure_category.get(pos));
                        editor_exc.putString("cat_" + (pos+1), expenditure_category.get(pos+1));
                        editor_exc.commit();
                        editor_exc = sf_exc_ico.edit();
                        String exc_ico_temp = sf_exc_ico.getString("caticon_" +(pos), "");
                        editor_exc.putString("caticon_" + (pos), sf_exc_ico.getString("caticon_" +(pos+1), ""));
                        editor_exc.putString("caticon_" + (pos+1), exc_ico_temp);
                        editor_exc.commit();
                        break;
                    case 3:
                        String me_string_exw = expenditure_way.get(pos);
                        String me_exw_type = expenditure_way_type.get(pos);
                        expenditure_way.remove(pos);
                        expenditure_way.add(pos+1, me_string_exw);
                        expenditure_way_forsearch_1.remove(pos+1);
                        expenditure_way_forsearch_1.add(pos+2, me_string_exw);
                        expenditure_way_forsearch_2.remove(pos+1);
                        expenditure_way_forsearch_2.add(pos+2, me_string_exw);
                        expenditure_way_type.remove(pos);
                        expenditure_way_type.add(pos+1, me_exw_type);


                        Drawable me_icon_exw = expenditure_way_icon.get(pos);
                        expenditure_way_icon.remove(pos);
                        expenditure_way_icon.add(pos+1, me_icon_exw);

                        //DB의 경우, 그냥 swap만 해주면 되네? 개꿀ㅇㅈ?? ㅇ ㅇㅈ
                        SharedPreferences sf_exw_str = context.getSharedPreferences("Expenditure_Way", context.MODE_PRIVATE);
                        SharedPreferences sf_exw_ico = context.getSharedPreferences("Expenditure_Way_Icon", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_exw = sf_exw_str.edit();
                        editor_exw.putString("cat_" + (pos), expenditure_way.get(pos) + ":;:" + expenditure_way_type.get(pos));
                        editor_exw.putString("cat_" + (pos+1), expenditure_way.get(pos+1) + ":;:" + expenditure_way_type.get(pos+1));
                        editor_exw.commit();
                        editor_exw = sf_exw_ico.edit();
                        String exw_ico_temp = sf_exw_ico.getString("caticon_" +(pos), "");
                        editor_exw.putString("caticon_" + (pos), sf_exw_ico.getString("caticon_" +(pos+1), ""));
                        editor_exw.putString("caticon_" + (pos+1), exw_ico_temp);
                        editor_exw.commit();
                        break;
                    default:
                        break;
                }

                Intent intent = new Intent(context, nowsearching.class);
                context.startActivity(intent);
            }
        };
        btn_down.setOnClickListener(btn_down_listener);

        CheckBox.OnClickListener chkbox_listener_2 = new CheckBox.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(chk.isChecked())
                {
                    chk.setChecked(true);
                    ListItem.get(pos).setchecked(true);
                }
                else
                {
                    chk.setChecked(false);
                    ListItem.get(pos).setchecked(false);

                }
            }
        };

        chk.setOnClickListener(chkbox_listener_2);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        listview_category_list listViewItem = ListItem.get(position);


        // 아이템 내 각 위젯에 데이터 반영
        name.setText(listViewItem.getname());
        icon.setImageDrawable(listViewItem.geticon());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return ListItem.get(position) ;
    }


    public void addItem(Context context, String name, Drawable icon)
    {
        listview_category_list item = new listview_category_list();
        item.setorder(this.getCount());
        item.setname(name);
        item.seticon(icon);

        ListItem.add(item);
    }

    public void addItem(Context context, listview_category_list item)
    {
        ListItem.add(item);
    }

}
