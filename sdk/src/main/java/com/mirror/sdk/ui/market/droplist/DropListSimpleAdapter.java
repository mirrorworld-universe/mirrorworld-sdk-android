package com.mirror.sdk.ui.market.droplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.apis.responses.CollectionOrder;
import java.util.List;

public class DropListSimpleAdapter extends RecyclerView.Adapter<DropListSimpleAdapter.InnerHolder> {

    List<CollectionOrder> mData;
    DropListSimpleAdapter.OnOrderItemClickListener mCardViewClickListener = null;

    public DropListSimpleAdapter(List<CollectionOrder> data){
        this.mData = data;
    }

    @NonNull
    @Override
    public DropListSimpleAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drop_list_simple_item, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        DropListSimpleAdapter.InnerHolder vh = new DropListSimpleAdapter.InnerHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DropListSimpleAdapter.InnerHolder holder, int position) {
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

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.drop_list_simple_item_tv);
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
        }
    }
}
