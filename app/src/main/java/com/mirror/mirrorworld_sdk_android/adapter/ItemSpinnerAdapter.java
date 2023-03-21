package com.mirror.mirrorworld_sdk_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.SpinnerBean;

import java.util.List;

public class ItemSpinnerAdapter extends BaseAdapter {
    private List<SpinnerBean> mHeroBeans;
    private Context mContext;

    public ItemSpinnerAdapter(List<SpinnerBean> heroBeans, Context context) {
        this.mHeroBeans = heroBeans;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mHeroBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mHeroBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        view = _LayoutInflater.inflate(R.layout.item_spinner_item, null);
        if(view != null)
        {
            TextView textView = (TextView)view.findViewById(R.id.item_spinner_item_textview);
            textView.setText(mHeroBeans.get(position).name);
        }
        return view;
    }
}