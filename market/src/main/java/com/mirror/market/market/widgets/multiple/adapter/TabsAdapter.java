package com.mirror.market.market.widgets.multiple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;

import java.util.List;

public class TabsAdapter extends RecyclerView.Adapter<TabsAdapter.ViewHolder>{

    private List<String> tabs;
    private int resource;
    private RecyclerView recyclerView;

    public TabsAdapter(List<String> tabs,RecyclerView recyclerView) {
        this.tabs = tabs;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mutiple_position_tab_item,parent,
                false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       String content = tabs.get(position);
       holder.index = String.valueOf(position);
       holder.content.setText(content);


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              int index = Integer.valueOf(holder.index);
              JumpToPosition(index);
           }
       });


    }


    private void JumpToPosition(int index){
        int childLayoutPosition = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(index));

            recyclerView.smoothScrollToPosition(100);

    }



    @Override
    public int getItemCount() {
        return tabs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        String index;
        public ViewHolder(View itemView){
            super(itemView);
            content= itemView.findViewById(R.id.multiple_tabs_item_content);
        }
    }

}
