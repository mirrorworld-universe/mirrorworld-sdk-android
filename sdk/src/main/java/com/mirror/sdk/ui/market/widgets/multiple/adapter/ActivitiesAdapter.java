package com.mirror.sdk.ui.market.widgets.multiple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.widgets.multiple.type.ActivitiesType;
import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;

import java.util.ArrayList;
import java.util.List;


public class ActivitiesAdapter implements Adapter<List<BaseType>>{


    private ListView listview;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mutiple_activities,parent,false);
        listview = view.findViewById(R.id.mutiple_activities_list);
        return new ActivityViewHolder(view,listview);
    }

    @Override
    public void onBindViewHolder(@NonNull List<BaseType> item, @NonNull RecyclerView.ViewHolder holder, int position) {
           ActivitiesType activities = (ActivitiesType) item.get(position);
           listview.setAdapter(new TabAdapter(activitiesMockData(),holder.itemView.getContext()));
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder{
        public ActivityViewHolder(@NonNull View itemView,ListView listView) {
            super(itemView);
        }
    }


    private List<ActivityItem> activitiesMockData(){
        List<ActivityItem> activityItems = new ArrayList<>();
        ActivityItem item1 = new ActivityItem("Cancel","","4 Hours ago");
        ActivityItem item2 = new ActivityItem("List","100","4 Hours ago");
        ActivityItem item3 = new ActivityItem("Transfer","","4 Month ago");
        activityItems.add(item1);
        activityItems.add(item2);
        activityItems.add(item3);
        return activityItems;
    }

}


