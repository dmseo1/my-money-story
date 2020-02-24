
package com.example.seodongmin.expediturerecord;

//Created by seodongmin on 2017-04-22.

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.seodongmin.expediturerecord.init.adapter_avoidpoo;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit100;
import static com.example.seodongmin.expediturerecord.init.adapter_calculateit20;
import static com.example.seodongmin.expediturerecord.init.adapter_color;
import static com.example.seodongmin.expediturerecord.init.adapter_countpeople;
import static com.example.seodongmin.expediturerecord.init.adapter_fastmemorize;
import static com.example.seodongmin.expediturerecord.init.adapter_rocksipa;
import static com.example.seodongmin.expediturerecord.init.expenditure_category;
import static com.example.seodongmin.expediturerecord.init.expenditure_category_icon;
import static com.example.seodongmin.expediturerecord.init.income_category;
import static com.example.seodongmin.expediturerecord.init.income_category_icon;


public class listview_calculateit_ranking_list_adapter extends BaseAdapter {



    private ArrayList<listview_calculateit_ranking_list> ListItem = new ArrayList<listview_calculateit_ranking_list>();

    public listview_calculateit_ranking_list_adapter()
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
        correct.setText(String.valueOf(listViewItem.getcorrect()));
        time.setText(String.valueOf((float)listViewItem.gettime()/(float)1000));

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


    public int addItem(int tw_or_hr, String name, int correct, long time, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        int ranking = 0;
        if(tw_or_hr == 20)
        {
            for(int i = 0; i < adapter_calculateit20.getCount() ; i ++) {
                if (correct < adapter_calculateit20.getListItem().get(ranking).getcorrect()) {
                    ranking++;
                } else if (correct > adapter_calculateit20.getListItem().get(ranking).getcorrect()) {
                    break;
                } else {
                    if (time >= adapter_calculateit20.getListItem().get(ranking).gettime())
                    {
                        ranking ++;
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }

        if(tw_or_hr == 100)
        {
            for(int i = 0; i < adapter_calculateit100.getCount() ; i ++) {
                if (correct < adapter_calculateit100.getListItem().get(ranking).getcorrect()) {
                    ranking++;
                } else if (correct > adapter_calculateit100.getListItem().get(ranking).getcorrect()) {
                    break;
                } else {
                    if (time >= adapter_calculateit100.getListItem().get(ranking).gettime())
                    {
                        ranking ++;
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }

        item.setranking(ranking);
        item.setname(name);
        item.setcorrect(correct);
        item.settime(time);
        item.setdate(date);

        ListItem.add(ranking, item);
        System.out.println("불러졌다");

        return ranking;
    }



    public int addItem_fastmemorize(String name, int correct, long time, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        int ranking = 0;


        for(int i = 0; i < adapter_fastmemorize.getCount() ; i ++) {
            if (correct < adapter_fastmemorize.getListItem().get(ranking).getcorrect()) {
                ranking++;
            } else if (correct > adapter_fastmemorize.getListItem().get(ranking).getcorrect()) {
                break;
            } else {
                if (time >= adapter_fastmemorize.getListItem().get(ranking).gettime())
                {
                    ranking ++;
                }
                else
                {
                    break;
                }
            }
        }


        item.setranking(ranking);
        item.setname(name);
        item.setcorrect(correct);
        item.settime(time);
        item.setdate(date);

        ListItem.add(ranking, item);
        System.out.println("불러졌다");

        return ranking;
    }


    public int addItem_color(String name, int correct, long time, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        int ranking = 0;


        for(int i = 0; i < adapter_color.getCount() ; i ++) {
            if (correct < adapter_color.getListItem().get(ranking).getcorrect()) {
                ranking++;
            } else if (correct > adapter_color.getListItem().get(ranking).getcorrect()) {
                break;
            } else {
                if (time >= adapter_color.getListItem().get(ranking).gettime())
                {
                    ranking ++;
                }
                else
                {
                    break;
                }
            }
        }

        item.setranking(ranking);
        item.setname(name);
        item.setcorrect(correct);
        item.settime(time);
        item.setdate(date);

        ListItem.add(ranking, item);
        System.out.println("불러졌다");

        return ranking;
    }


    public int addItem_rocksipa(String name, int correct, long time, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        int ranking = 0;


        for(int i = 0; i < adapter_rocksipa.getCount() ; i ++) {
            if (correct < adapter_rocksipa.getListItem().get(ranking).getcorrect()) {
                ranking++;
            } else if (correct > adapter_rocksipa.getListItem().get(ranking).getcorrect()) {
                break;
            } else {
                if (time >= adapter_rocksipa.getListItem().get(ranking).gettime())
                {
                    ranking ++;
                }
                else
                {
                    break;
                }
            }
        }


        item.setranking(ranking);
        item.setname(name);
        item.setcorrect(correct);
        item.settime(time);
        item.setdate(date);

        ListItem.add(ranking, item);
        System.out.println("불러졌다");

        return ranking;
    }


    public int addItem_countpeople(String name, int correct, long time, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        int ranking = 0;


        for(int i = 0; i < adapter_countpeople.getCount() ; i ++) {
            if (correct < adapter_countpeople.getListItem().get(ranking).getcorrect()) {
                ranking++;
            } else if (correct > adapter_countpeople.getListItem().get(ranking).getcorrect()) {
                break;
            } else {
                if (time >= adapter_countpeople.getListItem().get(ranking).gettime())
                {
                    ranking ++;
                }
                else
                {
                    break;
                }
            }
        }


        item.setranking(ranking);
        item.setname(name);
        item.setcorrect(correct);
        item.settime(time);
        item.setdate(date);

        ListItem.add(ranking, item);
        System.out.println("불러졌다");

        return ranking;
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

    public void addItem_justEnd(String name, int correct, long time, long date)
    {
        listview_calculateit_ranking_list item = new listview_calculateit_ranking_list();

        item.setname(name);
        item.setcorrect(correct);
        item.settime(time);
        item.setdate(date);

        ListItem.add(item);
    }
}
