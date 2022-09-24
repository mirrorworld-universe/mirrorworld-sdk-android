package com.mirror.sdk.ui.market.widgets.multiple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.widgets.multiple.type.ActivitiesType;
import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;

import java.util.List;


public class ActivitiesAdapter implements Adapter<List<BaseType>>{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mutiple_activities,parent,false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<BaseType> item, @NonNull RecyclerView.ViewHolder holder, int position) {

           ActivitiesType activities = (ActivitiesType) item.get(position);



    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder{

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}


