package com.mirror.market.market.widgets.multiple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;

import com.mirror.sdk.ui.market.widgets.multiple.type.AttributeType;
import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;

import java.util.List;

/*** @author Pu
 * @createTime 2022/9/23 16:43
 * @projectName mirrorworld-sdk-android
 * @className AttributeAdapter.java
 * @description TODO
 */
public class AttributeAdapter implements Adapter<List<BaseType>>{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mutiple_attribute,parent,false);
        return new AttributeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<BaseType> item, @NonNull RecyclerView.ViewHolder holder, int position) {

        AttributeType attribute = (AttributeType) item.get(position);

    }

    static class AttributeViewHolder extends RecyclerView.ViewHolder{

        public AttributeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

}
