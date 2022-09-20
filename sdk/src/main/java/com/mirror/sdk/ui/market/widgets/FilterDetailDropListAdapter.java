package com.mirror.sdk.ui.market.widgets;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
import java.util.List;

public class FilterDetailDropListAdapter extends RecyclerView.Adapter<FilterDetailDropListAdapter.InnerHolder> {

    Context mContext;
    List<String> mData;
    CollectionFilter mFilter;

    FilterDetailDropListAdapter.OnOrderItemClickListener mCardViewClickListener = null;

    public FilterDetailDropListAdapter(CollectionFilter data){
        mData = data.filter_value;
        mFilter = data;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.drop_list_simple_item, parent, false);
        InnerHolder vh = new InnerHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(mData.get(position));
    }


    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setOrderSelectListener(FilterDetailDropListAdapter.OnOrderItemClickListener listener){
        mCardViewClickListener = listener;
    }

    public interface OnOrderItemClickListener{
        void onClicked(String data);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        String mOrder;
        View mTotalView;
        TextView mTextView;
        ImageView mCheckIcon;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTotalView = itemView;
            mTextView = itemView.findViewById(R.id.drop_list_simple_item_tv);
            mCheckIcon = itemView.findViewById(R.id.drop_list_simple_item_iv);
            if (mCardViewClickListener != null)
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCardViewClickListener.onClicked(mOrder);
                    }
                });
        }

        public void setData(String data) {
            mOrder = data;
            mTextView.setText(data);

            mCheckIcon.setVisibility(View.GONE);
            mTextView.setTextColor(mContext.getResources().getColor(R.color.mirror_black));
        }
    }
}
