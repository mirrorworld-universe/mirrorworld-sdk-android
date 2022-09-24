package com.mirror.sdk.ui.market.widgets.multiple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.widgets.multiple.type.ActivitiesType;
import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;
import com.mirror.sdk.ui.market.widgets.multiple.type.GamePerformance;

import java.util.List;

/*** @author Pu
 * @createTime 2022/9/23 16:43
 * @projectName mirrorworld-sdk-android
 * @className GamePerformanceAdapter.java
 * @description TODO
 */
public class GamePerformanceAdapter implements Adapter<List<BaseType>>{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_performance,parent,false);
        return new GamePerformanceAdapter.GamePerformanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<BaseType> item, @NonNull RecyclerView.ViewHolder holder, int position) {

       GamePerformance gamePerformance = (GamePerformance) item.get(position);



    }

    static class GamePerformanceViewHolder extends RecyclerView.ViewHolder{

        public GamePerformanceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
