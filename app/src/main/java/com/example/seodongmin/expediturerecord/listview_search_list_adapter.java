
package com.example.seodongmin.expediturerecord;

//Created by seodongmin on 2017-04-22.

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.graphics.*;
import java.util.ArrayList;
import java.lang.*;

import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_icon;


public class listview_search_list_adapter extends BaseAdapter {



    private ArrayList<listview_search_list> ListItem = new ArrayList<listview_search_list>();

    public listview_search_list_adapter()
    {

    }

    public ArrayList<listview_search_list> getListItem()
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

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_search_list, parent, false);
        }



        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        LinearLayout sumlayout = (LinearLayout) convertView.findViewById(R.id.listview_search_list_sum);
        TextView sum = (TextView) convertView.findViewById(R.id.listview_search_list_hap_lbl_amount);
        TextView sumgone = (TextView) convertView.findViewById(R.id.listview_search_list_hap_lbl_amount_realgone);
        ImageView ieIcon = (ImageView) convertView.findViewById(R.id.listview_search_img_inex);
        TextView year = (TextView) convertView.findViewById(R.id.listview_search_list_lbl_y);
        TextView month = (TextView) convertView.findViewById(R.id.listview_search_list_lbl_m);
        TextView day = (TextView) convertView.findViewById(R.id.listview_search_list_lbl_d);
        TextView way = (TextView) convertView.findViewById(R.id.listview_search_list_lbl_way);
        ImageView emotionIcon = (ImageView) convertView.findViewById(R.id.listview_search_list_img_emotion);
        TextView memo = (TextView) convertView.findViewById(R.id.listview_search_list_lbl_memo);
        ImageView whereIcon = (ImageView) convertView.findViewById(R.id.listview_search_list_img_where);
        TextView where = (TextView) convertView.findViewById(R.id.listview_search_list_lbl_where);
        TextView amount = (TextView) convertView.findViewById(R.id.listview_search_list_lbl_amount);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        listview_search_list listViewItem = ListItem.get(position);
        if(listViewItem.getinex() == 1)
        {
            ieIcon.setImageResource(R.drawable.img_money_in);
            amount.setTextColor(Color.parseColor("#ff1e90"));
        }
        else
        {
            ieIcon.setImageResource(R.drawable.img_money_out);
            amount.setTextColor(Color.parseColor("#1e90ff"));
        }


        // 아이템 내 각 위젯에 데이터 반영
        MyIntStrMatch m1 = new MyIntStrMatch(1);
        MyIntStrMatch m2 = new MyIntStrMatch(2);
        MyIntStrMatch m3 = new MyIntStrMatch(3);

        year.setText(String.valueOf(listViewItem.getyear()));
        month.setText(String.valueOf(listViewItem.getmonth()));

        day.setText(String.valueOf(listViewItem.getday()));

        if(listViewItem.getinex() == 2) {
            way.setText(listViewItem.getway());
        }
        else
        {
            way.setText("-");
        }

        if(listViewItem.getinex() == 1)
        {
            where.setText(listViewItem.getwhere());
        }
        else
        {
            where.setText(listViewItem.getwhere());
        }

        if(listViewItem.getinex() == 1) {

            whereIcon.setImageDrawable(income_category_icon.get(income_category.indexOf(listViewItem.getwhere())));
        }
        else
        {
            whereIcon.setImageDrawable(expenditure_category_icon.get(expenditure_category.indexOf(listViewItem.getwhere())));
        }

        amount.setText(listViewItem.getamount());

        switch(listViewItem.getemonum())
        {
            case 5:
                emotionIcon.setImageResource(R.drawable.img_emotion_5);
                break;
            case 4:
                emotionIcon.setImageResource(R.drawable.img_emotion_4);
                break;
            case 3:
                emotionIcon.setImageResource(R.drawable.img_emotion_3);
                break;
            case 2:
                emotionIcon.setImageResource(R.drawable.img_emotion_2);
                break;
            case 1:
                emotionIcon.setImageResource(R.drawable.img_emotion_1);
                break;
            default:
                emotionIcon.setImageResource(R.drawable.img_empty);
        }
        memo.setText(listViewItem.getmemo());

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


    public int addItem(int inex, int year, int month, int day, String way, String where, int emonum, long amount, int includeStat, String memo) {

        listview_search_list item = new listview_search_list();
        int pos = 0;

        item.setinex(inex);
        item.setyear(year);
        item.setmonth(month);
        item.setday(day);
        item.setway(way);
        item.setwhere(where);
        item.setamount(amount);
        item.setemonum(emonum);
        item.setincludeStat(includeStat);
        item.setmemo(memo);

        for(int i=0;i<ListItem.size();i++)
        {
            if(ListItem.get(i).getyear() > year)
            {
                pos ++;
                continue;
            }
            if(ListItem.get(i).getyear() == year && ListItem.get(i).getmonth() > month)
            {
                pos ++;
                continue;
            }
            if(ListItem.get(i).getyear() == year && ListItem.get(i).getmonth() == month && ListItem.get(i).getday() > day)
            {
                pos ++;
                continue;
            }
            break;
        }
        ListItem.add(pos, item);
        return pos;
    }

    //Overloading
    public int addItem(listview_search_list item) {

        int pos = 0;

        for(int i=0;i<ListItem.size();i++)
        {
            if(ListItem.get(i).getyear() > item.getyear())
            {
                pos ++;
                continue;
            }
            if(ListItem.get(i).getyear() == item.getyear() && ListItem.get(i).getmonth() > item.getmonth())
            {
                pos ++;
                continue;
            }
            if(ListItem.get(i).getyear() == item.getyear() && ListItem.get(i).getmonth() == item.getmonth() && ListItem.get(i).getday() > item.getday())
            {
                pos ++;
                continue;
            }
            break;
        }
        ListItem.add(pos, item);
        return pos;
    }

    public void addItem_justEnd(int inex, int year, int month, int day, String way, String where, int emonum, long amount, int includeStat, String memo) {

        listview_search_list item = new listview_search_list();

        item.setinex(inex);
        item.setyear(year);
        item.setmonth(month);
        item.setday(day);
        item.setway(way);
        item.setwhere(where);
        item.setamount(amount);
        item.setemonum(emonum);
        item.setincludeStat(includeStat);
        item.setmemo(memo);

        ListItem.add(item);
    }

}
