//package com.mirror.market.market.widgets;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.sdk.R;
//import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
//
//import java.util.List;
//
//public class MarketMainFilterDetailRecyclerViewAdapter extends RecyclerView.Adapter<MarketMainFilterDetailRecyclerViewAdapter.ViewHolder>{
//
//    private OnFilterTabClicked mListener;
//    private List<CollectionFilter> mDataList;
//
//    public MarketMainFilterDetailRecyclerViewAdapter(List<CollectionFilter> list){
//        mDataList = list;
//    }
//
//    @NonNull
//    @Override
//    public MarketMainFilterDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_line3_tabitem, parent, false);
//        MarketMainFilterDetailRecyclerViewAdapter.ViewHolder vh = new MarketMainFilterDetailRecyclerViewAdapter.ViewHolder(view);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MarketMainFilterDetailRecyclerViewAdapter.ViewHolder holder, int position) {
//        CollectionFilter data = mDataList.get(position);
//        holder.TabExpandButton.setText(data.filter_name);
//        holder.setData(data);
//    }
//
//    public void setOnExpandedListener(OnFilterTabClicked listener){
//        mListener = listener;
//    }
//
//    public interface OnFilterTabClicked {
//        void OnExpand(ViewHolder tabLayout,CollectionFilter filter);
//        void OnFold(ViewHolder tabLayout);
//    }
//
//    @Override
//    public int getItemCount() {
//        if(mDataList != null){
//            return mDataList.size();
//        }
//        return 0;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TabExpandButton TabExpandButton;
//        public CollectionFilter mFilter;
//
//        public ViewHolder(View view) {
//            super(view);
//            TabExpandButton = (TabExpandButton) view;
//            ViewHolder vh = this;
//            TabExpandButton.setExpandListener(new ExpandButtonBase.OnExpandListener() {
//                @Override
//                public void onExpand() {
//                    mListener.OnExpand(vh,mFilter);
//                }
//
//                @Override
//                public void onFold() {
//                    mListener.OnFold(vh);
//                }
//            });
//        }
//        public void setData(CollectionFilter filter){
//            mFilter = filter;
//        }
//    }
//}
