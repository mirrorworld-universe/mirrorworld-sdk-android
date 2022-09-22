package com.mirror.sdk.ui.market.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;

import java.util.ArrayList;
import java.util.List;

class MainRecyclerViewItemType {
    public final static int HEADER = 0;
    public final static int CONTENT = 1;
}
public class MainDialogAdapter extends RecyclerView.Adapter<MainDialogItemBase> {
    Context mContext;
    List<Integer> mData;

    public MainDialogAdapter(){
        mData = new ArrayList<>();
        mData.add(MainRecyclerViewItemType.HEADER);
        mData.add(MainRecyclerViewItemType.CONTENT);
    }

    @NonNull
    @Override
    public MainDialogItemBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        switch (viewType){
            case MainRecyclerViewItemType.HEADER:{
                View v = LayoutInflater.from(mContext).inflate(R.layout.main_header, parent, false);
                MainDialogItemHeader vh = new MainDialogItemHeader(v);
                return vh;
            }
            case MainRecyclerViewItemType.CONTENT:{
                View v = LayoutInflater.from(mContext).inflate(R.layout.market_main3_contents, parent, false);
                MainDialogItemContent vh = new MainDialogItemContent(v);
                return vh;
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull MainDialogItemBase holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }
}
