package com.example.seodongmin.expediturerecord;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by seodongmin on 2017-04-28.
 */

public class gridview_addicon_list_adapter extends BaseAdapter {

    private ArrayList<Drawable> ListItem = new ArrayList();

    public gridview_addicon_list_adapter()
    {

    }

    public ArrayList<Drawable> getListItem()
    {
        return ListItem;
    }

    @Override
    public int getCount()
    {
        return ListItem.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_addicon, parent, false);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.addicon_icon);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Drawable listViewItem = ListItem.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        icon.setImageDrawable(listViewItem);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return ListItem.get(position);
    }

    public void addItem(Drawable drawable)
    {
        ListItem.add(drawable);
    }

}
