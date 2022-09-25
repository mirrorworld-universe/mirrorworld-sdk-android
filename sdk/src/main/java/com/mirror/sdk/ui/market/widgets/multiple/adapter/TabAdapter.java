package com.mirror.sdk.ui.market.widgets.multiple.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mirror.sdk.R;

import java.util.List;

public class TabAdapter extends BaseAdapter {

    private List<ActivityItem> tabs;
    private Context context;

    public TabAdapter(List<ActivityItem> list, Context context) {
        this.tabs = list;
        this.context = context;
    }

    // 返回数据条数
    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public Object getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=LayoutInflater.from(context).inflate(R.layout.activities_item,parent,false);
        TextView event = view.findViewById(R.id.multiple_activities_item_event);
        TextView price = view.findViewById(R.id.multiple_activities_item_price);
        TextView data = view.findViewById(R.id.multiple_activities_item_data);
        event.setText(tabs.get(position).getEvent());
        price.setText(tabs.get(position).getPrice());
        data.setText(tabs.get(position).getData());
        return view;

    }




}
