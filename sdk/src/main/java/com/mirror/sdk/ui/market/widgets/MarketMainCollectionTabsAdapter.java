package com.mirror.sdk.ui.market.widgets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;

import java.util.List;

public class MarketMainCollectionTabsAdapter extends RecyclerView.Adapter<MarketMainCollectionTabsAdapter.ViewHolder> {
    private List<CollectionInfo> mDataList;

    public MarketMainCollectionTabsAdapter(List<CollectionInfo> list){
        mDataList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_main_collectiontab_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CollectionInfo data = mDataList.get(position);
        holder.mTextView.setText(data.collection_name);

        final int staticPosition = position;
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MirrorMarket:",data.collection_name+" clicked.");
                //todo: scroll to middle
                MarketUIController.getInstance().mainCollectionTabScrollTo(staticPosition);

                //todo: refresh page

            }
        });
    }

    @Override
    public int getItemCount() {
        if(mDataList != null){
            return mDataList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.market_main_type_tabitem_tv);
        }

    }
}

