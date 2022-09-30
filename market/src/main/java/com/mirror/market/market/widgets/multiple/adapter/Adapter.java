package com.mirror.market.market.widgets.multiple.adapter;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface Adapter<T> {

    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);


    void onBindViewHolder(@NonNull T item, @NonNull RecyclerView.ViewHolder holder, int position);


}
