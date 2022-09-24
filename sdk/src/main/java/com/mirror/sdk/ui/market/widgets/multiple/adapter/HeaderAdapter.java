package com.mirror.sdk.ui.market.widgets.multiple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;
import com.mirror.sdk.ui.market.widgets.multiple.type.GamePerformance;
import com.mirror.sdk.ui.market.widgets.multiple.type.HeaderType;

import java.util.List;

/*** @author Pu
 * @createTime 2022/9/24 8:02
 * @projectName mirrorworld-sdk-android
 * @className HeaderAdapter.java
 * @description TODO
 */
public class HeaderAdapter implements Adapter<List<BaseType>>{

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
        return new HeaderAdapter.HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<BaseType> item, @NonNull RecyclerView.ViewHolder holder, int position) {

      HeaderType header = (HeaderType) item.get(position);



    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
