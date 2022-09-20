package com.mirror.sdk.ui.market.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
import com.mirror.sdk.ui.market.apis.responses.CollectionInfo;
import java.util.List;

public class MarketMainFilterDetailRecyclerViewAdapter extends RecyclerView.Adapter<MarketMainFilterDetailRecyclerViewAdapter.ViewHolder>{

    private List<CollectionFilter> mDataList;

    public MarketMainFilterDetailRecyclerViewAdapter(List<CollectionFilter> list){
        mDataList = list;
    }

    @NonNull
    @Override
    public MarketMainFilterDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_filter_recyclerview_item, parent, false);
        MarketMainFilterDetailRecyclerViewAdapter.ViewHolder vh = new MarketMainFilterDetailRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MarketMainFilterDetailRecyclerViewAdapter.ViewHolder holder, int position) {
        CollectionFilter data = mDataList.get(position);
        holder.mTextView.setText(data.filter_name);

        final int staticPosition = position;
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MirrorMarket:",data.filter_name+" clicked.");
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
        public TextView mTextView;
        public ImageFilterButton mImageFilterView;
        public View mContentView;

        public ViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.market_filter_recyclerview_item_tv);
            mImageFilterView = view.findViewById(R.id.market_filter_recyclerview_item_arrow_down);
            mContentView = view.findViewById(R.id.market_filter_recyclerview_item_contentlayout);
        }
    }
}
