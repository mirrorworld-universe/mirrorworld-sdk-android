//package com.mirror.market.market.widgets.multiple.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.sdk.R;
//import com.mirror.sdk.ui.market.widgets.multiple.type.ActivitiesType;
//import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;
//import com.mirror.sdk.ui.market.widgets.multiple.type.Details;
//
//import java.util.List;
//
///*** @author Pu
// * @createTime 2022/9/23 16:44
// * @projectName mirrorworld-sdk-android
// * @className DetailsAdapter.java
// * @description TODO
// */
//public class DetailsAdapter implements Adapter<List<BaseType>>{
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
//
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mutiple_details,parent,false);
//        return new DetailsViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull List<BaseType> item, @NonNull RecyclerView.ViewHolder holder, int position) {
//
//        Details details = (Details) item.get(position);
//
//
//
//    }
//
//    static class DetailsViewHolder extends RecyclerView.ViewHolder{
//
//        public DetailsViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//}
