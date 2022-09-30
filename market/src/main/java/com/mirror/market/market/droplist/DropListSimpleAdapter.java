package com.mirror.market.market.droplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
import java.util.List;

public class DropListSimpleAdapter extends RecyclerView.Adapter<DropListSimpleAdapter.InnerHolder> {

    Context mContext;
    List<CollectionOrder> mData;
    CollectionOrder mNowOrder;
    DropListSimpleAdapter.OnOrderItemClickListener mCardViewClickListener = null;

    public DropListSimpleAdapter(List<CollectionOrder> data,CollectionOrder order){
        mData = data;
        mNowOrder = order;
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

    public void setOrderSelectListener(DropListSimpleAdapter.OnOrderItemClickListener listener){
        mCardViewClickListener = listener;
    }

    public interface OnOrderItemClickListener{
        void onClicked(CollectionOrder data);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        CollectionOrder mOrder;
        TextView mTextView;
        ImageView mCheckIcon;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
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

        public void setData(CollectionOrder data) {
            mOrder = data;
            mTextView.setText(data.order_desc);

            if(data == mNowOrder){
                mCheckIcon.setVisibility(View.VISIBLE);
                mTextView.setTextColor(mContext.getResources().getColor(R.color.mirror_font_blue));
            }else {
                mCheckIcon.setVisibility(View.GONE);
                mTextView.setTextColor(mContext.getResources().getColor(R.color.mirror_black));
            }
        }
    }
}
