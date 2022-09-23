package com.mirror.sdk.ui.market.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import com.mirror.sdk.ui.market.apis.responses.NFTSellSummary;
import com.mirror.sdk.ui.market.widgets.MarketMainCollectionTabsAdapter;

import java.util.List;

public class SellRecyclerViewAdapter extends RecyclerView.Adapter<SellRecyclerViewAdapter.ViewHolder>{
    private List<NFTSellSummary> mDataList;

    public SellRecyclerViewAdapter(List<NFTSellSummary> list){
        mDataList = list;
    }

    @NonNull
    @Override
    public SellRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sell_info_item, parent, false);
        SellRecyclerViewAdapter.ViewHolder vh = new SellRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SellRecyclerViewAdapter.ViewHolder holder, int position) {
        NFTSellSummary data = mDataList.get(position);
        holder.mTextView1.setText(data.name);
        holder.mTextView2.setText(data.value);
    }

    @Override
    public int getItemCount() {
        if(mDataList != null){
            return mDataList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView1;
        public final TextView mTextView2;

        public ViewHolder(View view) {
            super(view);
            mTextView1 = (TextView) view.findViewById(R.id.sell_info_item_tv1);
            mTextView2 = (TextView) view.findViewById(R.id.sell_info_item_tv2);
        }

    }
}
