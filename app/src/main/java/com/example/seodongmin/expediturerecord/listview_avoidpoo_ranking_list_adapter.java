
package com.example.seodongmin.expediturerecord;

//Created by seodongmin on 2017-04-22.

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.seodongmin.expediturerecord.init.adapter_avoidpoo;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit100;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit20;
import static com.example.seodongmin.expediturerecord.init.adapter_color;
import static com.example.seodongmin.expediturerecord.init.adapter_countpeople;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.adapter_rocksipa;


public class listview_avoidpoo_ranking_list_adapter extends BaseAdapter {



    private ArrayList<listview_calculateit_ranking_list> ListItem = new ArrayList<listview_calculateit_ranking_list>();

    public listview_avoidpoo_ranking_list_adapter()
    {

    }

    public ArrayList<listview_calculateit_ranking_list> getListItem()
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
            convertView = inflater.inflate(R.layout.listview_calculateit_ranking, parent, false);
        }



        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView ranking = (TextView) convertView.findViewById(R.id.calculateit_ranking_number);
        TextView name = (TextView) convertView.findViewById(R.id.calculateit_ranking_name);
        TextView correct = (TextView) convertView.findViewById(R.id.calculateit_ranking_correct);
        TextView time = (TextView) convertView.findViewById(R.id.calculateit_ranking_time);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        listview_calculateit_ranking_list listViewItem = ListItem.get(position);


        // 아이템 내 각 위젯에 데이터 반영

        ranking.setText(String.valueOf(pos+1));
        name.setText(listViewItem.getname());
        correct.setText("");
        time.setText(String.valueOf(listViewItem.gettime()));

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


    public int addItem_avoidpoo(String name, long score, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        int ranking = 0;


        for(int i = 0; i < adapter_avoidpoo.getCount() ; i ++) {
            if(score < adapter_avoidpoo.getListItem().get(ranking).gettime())
            {
                ranking++;
            }
        }

        item.setranking(ranking);
        item.setname(name);
        item.setcorrect(0);
        item.settime(score);
        item.setdate(date);

        ListItem.add(ranking, item);
        System.out.println("불러졌다");

        return ranking;
    }

    //Overloading
    public void addItem(listview_calculateit_ranking_list item) {

        ListItem.add(item);
    }

    public void addItem_justEnd(String name, long time, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        item.setname(name);
        item.settime(time);
        item.setdate(date);

        ListItem.add(item);
    }
}
