package com.mirror.market.market.widgets.multiple.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.ui.market.widgets.multiple.type.ActivitiesType;
import com.mirror.sdk.ui.market.widgets.multiple.type.AttributeType;
import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;
import com.mirror.sdk.ui.market.widgets.multiple.type.Details;
import com.mirror.sdk.ui.market.widgets.multiple.type.GamePerformance;
import com.mirror.sdk.ui.market.widgets.multiple.type.HeaderType;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter {


    private static final int ACTIVITIES=4;
    private static final int HEADER =0;
    private static final int ATTRIBUTE=2;
    private static final int DETAILS=3;
    private static final int GAMEPERFORMANCE=1;


    SparseArrayCompat<Adapter> mCompat;
    private List<BaseType> mDataSource;


    public MainAdapter(List<BaseType> lists) {
        this.mDataSource=lists;
        this.mCompat=new SparseArrayCompat<Adapter>();
        this.mCompat.put(ACTIVITIES,new ActivitiesAdapter());
        this.mCompat.put(ATTRIBUTE,new AttributeAdapter());
        this.mCompat.put(DETAILS,new DetailsAdapter());
        this.mCompat.put(GAMEPERFORMANCE,new GamePerformanceAdapter());
        this.mCompat.put(HEADER,new HeaderAdapter());

    }


    public List<BaseType> getMDataSource() {
        return mDataSource;
    }

    public void setDataSource(List<BaseType> DataSource) {
        this.mDataSource = DataSource;
    }



    @Override
    public int getItemViewType(int position) {

        if(mDataSource.get(position) instanceof ActivitiesType){

            return ACTIVITIES;

        }else if(mDataSource.get(position) instanceof AttributeType){

            return ATTRIBUTE;

        }else if(mDataSource.get(position) instanceof Details){

            return DETAILS;

        }else if(mDataSource.get(position) instanceof GamePerformance) {

            return GAMEPERFORMANCE;
        }else if(mDataSource.get(position) instanceof HeaderType){

            return HEADER;
        }

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int a = 1;
        parent.clearFocus();
        Adapter<BaseType> adapter = mCompat.get(viewType);

        return mCompat.get(viewType).onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Adapter adapter = mCompat.get(holder.getItemViewType());
        adapter.onBindViewHolder(mDataSource, holder, position);

    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }





}























