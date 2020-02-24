package com.example.seodongmin.expediturerecord;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import static com.example.seodongmin.expediturerecord.init.adapter_avoidpoo;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit100;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit20;
import static com.example.seodongmin.expediturerecord.init.adapter_color;
import static com.example.seodongmin.expediturerecord.init.adapter_countpeople;
import static com.example.seodongmin.expediturerecord.init.adapter_exc;
import static com.example.seodongmin.expediturerecord.init.adapter_exw;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.adapter_iconlist;
import static com.example.seodongmin.expediturerecord.init.adapter_inc;
import static com.example.seodongmin.expediturerecord.init.adapter_rocksipa;
import static com.example.seodongmin.expediturerecord.init.avoidpoo;
import static com.example.seodongmin.expediturerecord.init.calculateit_100;
import static com.example.seodongmin.expediturerecord.init.calculateit_20;
import static com.example.seodongmin.expediturerecord.init.color;
import static com.example.seodongmin.expediturerecord.init.countpeople;
import static com.example.seodongmin.expediturerecord.init.exc_category;
import static com.example.seodongmin.expediturerecord.init.exc_category_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_icon;
import static com.example.seodongmin.expediturerecord.init.expenditure_way_type;
import static com.example.seodongmin.expediturerecord.init.exw_category;
import static com.example.seodongmin.expediturerecord.init.exw_category_icon;
import static com.example.seodongmin.expediturerecord.init.fastmemorize;
import static com.example.seodongmin.expediturerecord.init.inc_and_exp;
import static com.example.seodongmin.expediturerecord.init.inc_category;
import static com.example.seodongmin.expediturerecord.init.inc_category_icon;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_1;
import static com.example.seodongmin.expediturerecord.init.income_category_forsearch_2;
import static com.example.seodongmin.expediturerecord.init.income_category_icon;
import static com.example.seodongmin.expediturerecord.init.rocksipa;
import static com.example.seodongmin.expediturerecord.search.search_adapter;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class Initializer extends AppCompatActivity {


    public void init_income_and_expenditure()
    {
        search_adapter.getListItem().clear();
        //SharedPreferences pref = getSharedPreferences("Income_and_Expenditure", MODE_PRIVATE);

        int inex;
        int year;
        int month;
        int day;
        String way;
        String where;
        int satisfaction;
        long amount;
        int includeStat;
        String memo;
        int i = 0;

        String getData;
        while(true)
        {
            getData = inc_and_exp.getString("incexp_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            inex = Integer.parseInt(result[0]);
            year = Integer.parseInt(result[1]);
            month = Integer.parseInt(result[2]);
            day = Integer.parseInt(result[3]);
            way = result[4];
            where = result[5];
            satisfaction = Integer.parseInt(result[6]);
            amount = Long.parseLong(result[7]);
            includeStat = Integer.parseInt(result[8]);
            int j = 9;
            memo = "";
            try {
                while (true)
                {
                    memo = memo + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }
            search_adapter.addItem_justEnd(inex, year, month, day, way, where, satisfaction, amount, includeStat, memo);
            i++;
        }
    }



    public void init_income_category_1()
    {
        int i = 0;
        income_category.clear();
        while(!inc_category.getString("cat_" + i, "").equals(""))
        {
            income_category.add(inc_category.getString("cat_" + i, ""));
            i++;
        }
       /* income_category.clear();
        income_category.add("(분류없음)");
        income_category.add("월급");
        income_category.add("보너스");
        income_category.add("용돈");
        income_category.add("이자");
        income_category.add("기타");*/
    }

    public void init_income_category_2()
    {
        int i = 0;
        income_category_forsearch_1.clear();
        income_category_forsearch_1.add("모두");
        while(!inc_category.getString("cat_" + i, "").equals(""))
        {
            income_category_forsearch_1.add(inc_category.getString("cat_" + i, ""));
            i++;
        }
        /*income_category_forsearch_1.clear();
        income_category_forsearch_1.add("모두");
        income_category_forsearch_1.add("(분류없음)");
        income_category_forsearch_1.add("월급");
        income_category_forsearch_1.add("보너스");
        income_category_forsearch_1.add("용돈");
        income_category_forsearch_1.add("이자");
        income_category_forsearch_1.add("기타");*/
    }

    public void init_income_category_3()
    {
        int i = 0;
        income_category_forsearch_2.clear();
        income_category_forsearch_2.add("(선택없음)");
        while(!inc_category.getString("cat_" + i, "").equals(""))
        {
            income_category_forsearch_2.add(inc_category.getString("cat_" + i, ""));
            i++;
        }

       /* income_category_forsearch_2.clear();
        income_category_forsearch_2.add("(선택없음)");
        income_category_forsearch_2.add("(분류없음)");
        income_category_forsearch_2.add("월급");
        income_category_forsearch_2.add("보너스");
        income_category_forsearch_2.add("용돈");
        income_category_forsearch_2.add("이자");
        income_category_forsearch_2.add("기타");*/
    }


    public void init_expenditure_category_1()
    {

        int i = 0;
        expenditure_category.clear();
        while(!exc_category.getString("cat_" + i, "").equals(""))
        {
            expenditure_category.add(exc_category.getString("cat_" + i, ""));
            i++;
        }

     /*   expenditure_category.clear();
        expenditure_category.add("(분류없음)");
        expenditure_category.add("식료품");
        expenditure_category.add("외식");
        expenditure_category.add("교통비");
        expenditure_category.add("통신비");
        expenditure_category.add("공납금");
        expenditure_category.add("수수료");
        expenditure_category.add("저축");
        expenditure_category.add("여가");
        expenditure_category.add("기부");
        expenditure_category.add("기타");*/
    }


    public void init_expenditure_category_2()
    {
        int i = 0;
        expenditure_category_forsearch_1.clear();
        expenditure_category_forsearch_1.add("모두");
        while(!exc_category.getString("cat_" + i, "").equals(""))
        {
            expenditure_category_forsearch_1.add(exc_category.getString("cat_" + i, ""));
            i++;
        }

       /* expenditure_category_forsearch_1.clear();
        expenditure_category_forsearch_1.add("모두");
        expenditure_category_forsearch_1.add("(분류없음)");
        expenditure_category_forsearch_1.add("식료품");
        expenditure_category_forsearch_1.add("외식");
        expenditure_category_forsearch_1.add("교통비");
        expenditure_category_forsearch_1.add("통신비");
        expenditure_category_forsearch_1.add("공납금");
        expenditure_category_forsearch_1.add("수수료");
        expenditure_category_forsearch_1.add("저축");
        expenditure_category_forsearch_1.add("여가");
        expenditure_category_forsearch_1.add("기부");
        expenditure_category_forsearch_1.add("기타");*/
    }

    public void init_expenditure_category_3()
    {

        int i = 0;
        expenditure_category_forsearch_2.clear();
        expenditure_category_forsearch_2.add("(선택없음)");
        while(!exc_category.getString("cat_" + i, "").equals(""))
        {
            expenditure_category_forsearch_2.add(exc_category.getString("cat_" + i, ""));
            i++;
        }

/*        expenditure_category_forsearch_2.clear();
        expenditure_category_forsearch_2.add("(선택없음)");
        expenditure_category_forsearch_2.add("(분류없음)");
        expenditure_category_forsearch_2.add("식료품");
        expenditure_category_forsearch_2.add("외식");
        expenditure_category_forsearch_2.add("교통비");
        expenditure_category_forsearch_2.add("통신비");
        expenditure_category_forsearch_2.add("공납금");
        expenditure_category_forsearch_2.add("수수료");
        expenditure_category_forsearch_2.add("저축");
        expenditure_category_forsearch_2.add("여가");
        expenditure_category_forsearch_2.add("기부");
        expenditure_category_forsearch_2.add("기타");*/
    }

    public void init_expenditure_way()
    {

        int i = 0;
        expenditure_way.clear();
        expenditure_way_type.clear();
        while(!exw_category.getString("cat_" + i, "").equals(""))
        {
            String s = exw_category.getString("cat_" + i, "");
            String[] sa = s.split(":;:");
            expenditure_way.add(sa[0]);
            expenditure_way_type.add(sa[1]);
            i++;
        }

       /* expenditure_way.clear();

        expenditure_way.add("(분류없음)");
        expenditure_way.add("현금");
        expenditure_way.add("체크카드");
        expenditure_way.add("신용카드");*/

    }

    public void init_expenditure_way_forsearch_1()
    {

        int i = 0;
        expenditure_way_forsearch_1.clear();
        expenditure_way_forsearch_1.add("모두");
        while(!exw_category.getString("cat_" + i, "").equals(""))
        {
            String[] parse = exw_category.getString("cat_" + i, "").split(":;:");
            expenditure_way_forsearch_1.add(parse[0]);
            i++;
        }
       /* expenditure_way_forsearch_1.clear();
        expenditure_way_forsearch_1.add("모두");
        expenditure_way_forsearch_1.add("(분류없음)");
        expenditure_way_forsearch_1.add("현금");
        expenditure_way_forsearch_1.add("체크카드");
        expenditure_way_forsearch_1.add("신용카드");*/

    }

    public void init_expenditure_way_forsearch_2()
    {

        int i = 0;
        expenditure_way_forsearch_2.clear();
        expenditure_way_forsearch_2.add("(선택없음)");
        while(!exw_category.getString("cat_" + i, "").equals(""))
        {
            String[] parse = exw_category.getString("cat_" + i, "").split(":;:");
            expenditure_way_forsearch_2.add(parse[0]);
            i++;
        }

       /* expenditure_way_forsearch_2.clear();
        expenditure_way_forsearch_2.add("(선택없음)");
        expenditure_way_forsearch_2.add("(분류없음)");
        expenditure_way_forsearch_2.add("현금");
        expenditure_way_forsearch_2.add("체크카드");
        expenditure_way_forsearch_2.add("신용카드");*/

    }

    @Deprecated
    public void init_expenditure_way_type()
    {
        expenditure_way_type.clear();
        expenditure_way_type.add("1");
        expenditure_way_type.add("1");
        expenditure_way_type.add("1");
        expenditure_way_type.add("2");
    }

    public void init_expenditure_category_icon(Context context)
    {

        int i = 0;
        expenditure_category_icon.clear();
        while(!exc_category_icon.getString("caticon_" + i, "").equals(""))
        {
            expenditure_category_icon.add(makeDecodedImage(context, exc_category_icon.getString("caticon_" + i, "")));
            i++;
        }

       /* expenditure_category_icon.clear();
        Drawable[] dr = new Drawable[11];
        dr[0] = ContextCompat.getDrawable(context, R.drawable.ico_noname);
        dr[1] = ContextCompat.getDrawable(context, R.drawable.ico_grocery);
        dr[2] = ContextCompat.getDrawable(context, R.drawable.ico_eatout);
        dr[3] = ContextCompat.getDrawable(context, R.drawable.ico_transport);
        dr[4] = ContextCompat.getDrawable(context, R.drawable.ico_commu);
        dr[5] = ContextCompat.getDrawable(context, R.drawable.ico_sae);
        dr[6] = ContextCompat.getDrawable(context, R.drawable.ico_susu);
        dr[7] = ContextCompat.getDrawable(context, R.drawable.ico_bank);
        dr[8] = ContextCompat.getDrawable(context, R.drawable.ico_leasure);
        dr[9] = ContextCompat.getDrawable(context, R.drawable.ico_donation);
        dr[10] = ContextCompat.getDrawable(context, R.drawable.ico_etc);

        for(int i = 0; i < 11; i ++ )
        {
            expenditure_category_icon.add(dr[i]);
        }*/
    }


    public void init_income_category_icon(Context context)
    {

        int i = 0;
        income_category_icon.clear();
        while(!inc_category_icon.getString("caticon_" + i, "").equals(""))
        {
            income_category_icon.add(makeDecodedImage(context, inc_category_icon.getString("caticon_" + i, "")));
            i++;
        }

       /* income_category_icon.clear();
        Drawable[] dr = new Drawable[6];
        dr[0] = ContextCompat.getDrawable(context, R.drawable.ico_noname);
        dr[1] = ContextCompat.getDrawable(context, R.drawable.ico_pay);
        dr[2] = ContextCompat.getDrawable(context, R.drawable.ico_bonus);
        dr[3] = ContextCompat.getDrawable(context, R.drawable.ico_dragon);
        dr[4] = ContextCompat.getDrawable(context, R.drawable.ico_iza);
        dr[5] = ContextCompat.getDrawable(context, R.drawable.ico_sun);

        for(int i = 0; i < 6; i ++ )
        {
            income_category_icon.add(dr[i]);
        }*/
    }


    public void init_expenditure_way_icon(Context context)
    {
        int i = 0;
        expenditure_way_icon.clear();
        while(!exw_category_icon.getString("caticon_" + i, "").equals(""))
        {
            expenditure_way_icon.add(makeDecodedImage(context, exw_category_icon.getString("caticon_" + i, "")));
            i++;
        }


        /*expenditure_way_icon.clear();
        Drawable[] dr = new Drawable[6];
        dr[0] = ContextCompat.getDrawable(context, R.drawable.ico_noname);
        dr[1] = ContextCompat.getDrawable(context, R.drawable.ico_cash);
        dr[2] = ContextCompat.getDrawable(context, R.drawable.ico_checkcard);
        dr[3] = ContextCompat.getDrawable(context, R.drawable.ico_creditcard);

        for(int i = 0; i < 4; i ++ )
        {
            expenditure_way_icon.add(dr[i]);
        }*/
    }

    public void init_categoty_adapter(Context context)
    {

        adapter_inc.getListItem().clear();
        for(int i = 0; i < income_category.size() ; i ++)
        {
            listview_category_list listitem = new listview_category_list();
            listitem.setorder(i);
            listitem.setname(income_category.get(i));
            listitem.seticon(income_category_icon.get(i));
            adapter_inc.addItem(context, listitem);
        }

        adapter_exc.getListItem().clear();
        for(int i = 0; i < expenditure_category.size() ; i ++)
        {
            listview_category_list listitem = new listview_category_list();
            listitem.setorder(i);
            listitem.setname(expenditure_category.get(i));
            System.out.println(expenditure_category.get(i));
            listitem.seticon(expenditure_category_icon.get(i));
            adapter_exc.addItem(context, listitem);
        }

        adapter_exw.getListItem().clear();
        for(int i = 0; i < expenditure_way.size() ; i ++)
        {
            listview_category_list listitem = new listview_category_list();
            listitem.setorder(i);
            listitem.setname(expenditure_way.get(i));
            listitem.seticon(expenditure_way_icon.get(i));
            adapter_exw.addItem(context, listitem);
        }
    }


    public void init_iconlist_adapter(Context context)
    {
        adapter_iconlist.getListItem().clear();
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_bank));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_bonus));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_commu));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_donation));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_dragon));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_eatout));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_book));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_coffee));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_sun));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_grocery));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_leasure));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_pay));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_cash));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_sae));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_balloon));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_susu));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_transport));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_checkcard));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_creditcard));
        adapter_iconlist.addItem(ContextCompat.getDrawable(context, R.drawable.ico_noname));
    }

    public void init_calculateit_20_adapter(Context context)
    {

        adapter_calculateit20.getListItem().clear();

        int correct;
        long time;
        long date;
        String name;

        int i = 0;

        String getData;
        while(true)
        {
            getData = calculateit_20.getString("rank20_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            correct = Integer.parseInt(result[0]);
            time = Long.parseLong(result[1]);
            date = Long.parseLong(result[2]);
            int j = 3;
            name = "";
            try {
                while (true)
                {
                    name = name + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }

            adapter_calculateit20.addItem_justEnd(name, correct, time, date);
            i++;
        }

    }

    public void init_calculateit_100_adapter(Context context)
    {

        adapter_calculateit100.getListItem().clear();

        int correct;
        long time;
        long date;
        String name;

        int i = 0;

        String getData;
        while(true)
        {
            getData = calculateit_100.getString("rank100_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            correct = Integer.parseInt(result[0]);
            time = Long.parseLong(result[1]);
            date = Long.parseLong(result[2]);
            int j = 3;
            name = "";
            try {
                while (true)
                {
                    name = name + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }

            adapter_calculateit100.addItem_justEnd(name, correct, time, date);
            i++;
        }

    }


    public void init_fastmemorize_adapter(Context context)
    {

        adapter_fastmemorize.getListItem().clear();

        int correct;
        long time;
        long date;
        String name;

        int i = 0;

        String getData;
        while(true)
        {
            getData = fastmemorize.getString("rankfast_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            correct = Integer.parseInt(result[0]);
            time = Long.parseLong(result[1]);
            date = Long.parseLong(result[2]);
            int j = 3;
            name = "";
            try {
                while (true)
                {
                    name = name + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }

            adapter_fastmemorize.addItem_justEnd(name, correct, time, date);
            i++;
        }

    }


    public void init_color_adapter(Context context)
    {

        adapter_color.getListItem().clear();

        int correct;
        long time;
        long date;
        String name;

        int i = 0;

        String getData;
        while(true)
        {
            getData = color.getString("rankcolor_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            correct = Integer.parseInt(result[0]);
            time = Long.parseLong(result[1]);
            date = Long.parseLong(result[2]);
            int j = 3;
            name = "";
            try {
                while (true)
                {
                    name = name + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }

            adapter_color.addItem_justEnd(name, correct, time, date);
            i++;
        }

    }


    public void init_rocksipa_adapter(Context context)
    {

        adapter_rocksipa.getListItem().clear();

        int correct;
        long time;
        long date;
        String name;

        int i = 0;

        String getData;
        while(true)
        {

            getData = rocksipa.getString("rankrock_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            correct = Integer.parseInt(result[0]);
            time = Long.parseLong(result[1]);
            date = Long.parseLong(result[2]);
            int j = 3;
            name = "";
            try {
                while (true)
                {
                    name = name + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }

            adapter_rocksipa.addItem_justEnd(name, correct, time, date);
            i++;
        }

    }



    public void init_countpeople_adapter(Context context)
    {

        adapter_countpeople.getListItem().clear();

        int correct;
        long time;
        long date;
        String name;

        int i = 0;

        String getData;
        while(true)
        {

            getData = countpeople.getString("rankcount_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            correct = Integer.parseInt(result[0]);
            time = Long.parseLong(result[1]);
            date = Long.parseLong(result[2]);
            int j = 3;
            name = "";
            try {
                while (true)
                {
                    name = name + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }

            adapter_countpeople.addItem_justEnd(name, correct, time, date);
            i++;
        }

    }


    public void init_avoidpoo_adapter(Context context)
    {

        adapter_avoidpoo.getListItem().clear();


        long time;
        long date;
        String name;

        int i = 0;

        String getData;
        while(true)
        {

            getData = avoidpoo.getString("rankpoo_" + i, "");
            if(getData.equals("")) break;
            System.out.println("getData:" + getData);
            String[] result = getData.split(":;:");
            time = Long.parseLong(result[1]);
            date = Long.parseLong(result[2]);
            int j = 3;
            name = "";
            try {
                while (true)
                {
                    name = name + result[j];
                    j++;
                }

            } catch(IndexOutOfBoundsException e)
            {

            }

            adapter_avoidpoo.addItem_justEnd(name, time, date);
            i++;
        }

    }


    public void init_categoryPref_Initializer() //앱 최초 설치시 기본 카테고리를 저장하는 메서드이다.
    {
        SharedPreferences.Editor editor = inc_category.edit();
        editor.putString("cat_0", "(분류없음)");
        editor.putString("cat_1", "월급");
        editor.putString("cat_2", "보너스");
        editor.putString("cat_3", "용돈");
        editor.putString("cat_4", "이자");
        editor.putString("cat_5", "기타");
        editor.commit();

        editor = exc_category.edit();
        editor.putString("cat_0", "(분류없음)");
        editor.putString("cat_1", "식료품");
        editor.putString("cat_2", "외식");
        editor.putString("cat_3", "교통비");
        editor.putString("cat_4", "통신비");
        editor.putString("cat_5", "공납금");
        editor.putString("cat_6", "수수료");
        editor.putString("cat_7", "저축");
        editor.putString("cat_8", "여가");
        editor.putString("cat_9", "기부");
        editor.putString("cat_10", "기타");
        editor.commit();

        editor = exw_category.edit();
        editor.putString("cat_0", "(분류없음):;:1");
        editor.putString("cat_1", "현금:;:1");
        editor.putString("cat_2", "체크카드:;:1");
        editor.putString("cat_3", "신용카드:;:2");
        editor.commit();
    }

    public void init_categoryIconPref_Initializer(Context context)
    {
        SharedPreferences.Editor editor = inc_category_icon.edit();

        editor.putString("caticon_0", makeEncodedImage(context, R.drawable.ico_noname));
        editor.putString("caticon_1", makeEncodedImage(context, R.drawable.ico_pay));
        editor.putString("caticon_2", makeEncodedImage(context, R.drawable.ico_bonus));
        editor.putString("caticon_3", makeEncodedImage(context, R.drawable.ico_dragon));
        editor.putString("caticon_4", makeEncodedImage(context, R.drawable.ico_iza));
        editor.putString("caticon_5", makeEncodedImage(context, R.drawable.ico_sun));
        editor.commit();

        editor = exc_category_icon.edit();
        editor.putString("caticon_0", makeEncodedImage(context, R.drawable.ico_noname));
        editor.putString("caticon_1", makeEncodedImage(context, R.drawable.ico_grocery));
        editor.putString("caticon_2", makeEncodedImage(context, R.drawable.ico_eatout));
        editor.putString("caticon_3", makeEncodedImage(context, R.drawable.ico_transport));
        editor.putString("caticon_4", makeEncodedImage(context, R.drawable.ico_commu));
        editor.putString("caticon_5", makeEncodedImage(context, R.drawable.ico_sae));
        editor.putString("caticon_6", makeEncodedImage(context, R.drawable.ico_susu));
        editor.putString("caticon_7", makeEncodedImage(context, R.drawable.ico_bank));
        editor.putString("caticon_8", makeEncodedImage(context, R.drawable.ico_leasure));
        editor.putString("caticon_9", makeEncodedImage(context, R.drawable.ico_donation));
        editor.putString("caticon_10", makeEncodedImage(context, R.drawable.ico_etc));
        editor.commit();

        editor = exw_category_icon.edit();
        editor.putString("caticon_0", makeEncodedImage(context, R.drawable.ico_noname));
        editor.putString("caticon_1", makeEncodedImage(context, R.drawable.ico_cash));
        editor.putString("caticon_2", makeEncodedImage(context, R.drawable.ico_checkcard));
        editor.putString("caticon_3", makeEncodedImage(context, R.drawable.ico_creditcard));
        editor.commit();

    }

    public String makeEncodedImage(Context context, int id_drawable)
    {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), id_drawable);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        try{ baos.close(); } catch(IOException e) {}
        baos = null;
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    //오버로딩
    public String makeEncodedImage(Context context, Drawable drawable)
    {
        Bitmap icon = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        try{ baos.close(); } catch(IOException e) {}
        baos = null;
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public Drawable makeDecodedImage(Context context, String string)
    {
        byte[] b = Base64.decode(string, Base64.DEFAULT);
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        Bitmap.createScaledBitmap(bitmap, 90, 90, true);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }



}
