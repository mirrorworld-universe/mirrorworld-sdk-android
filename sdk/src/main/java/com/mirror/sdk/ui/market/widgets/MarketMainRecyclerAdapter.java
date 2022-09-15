package com.mirror.sdk.ui.market.widgets;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.model.NFTDetailData;

import java.util.ArrayList;
import java.util.List;

public class MarketMainRecyclerAdapter  extends RecyclerView.Adapter<MarketMainRecyclerAdapter.InnerHolder>{
    //构造方法 得到activity那边传递过来的数据
    List<NFTDetailData> iconbeans = new ArrayList<NFTDetailData>();
    OnNFTItemClickListener mCardViewClickListener = null;

    public MarketMainRecyclerAdapter(List<NFTDetailData> iconbeans){
        this.iconbeans = iconbeans;
    }

    @NonNull
    @Override
    //设置view
    public MarketMainRecyclerAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //给item的布局文件添加进我们设置View里面
        View view = View.inflate(parent.getContext(), R.layout.market_main_recyclerview_item,null);
        return new InnerHolder(view);//并且将这个传递给InnerHol这个内部类
    }

    //设置item
    @Override
    public void onBindViewHolder(@NonNull MarketMainRecyclerAdapter.InnerHolder holder, int position) {
        holder.setData(iconbeans.get(position));
    }


    @Override
    //返回item数目
    public int getItemCount() {
        if(iconbeans != null){
            return iconbeans.size();
        }
        return 0;
    }

    public void setCardViewOnClickListener(OnNFTItemClickListener listener){
        mCardViewClickListener = listener;
    }

    public interface OnNFTItemClickListener{
        public void onClicked(View view, NFTDetailData data);
    }
    //设置数据
    public class InnerHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textViewtitle,textViewEnglish;
        ImageView imageView;
        NFTDetailData mData = null;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.market_main_nft_item);
            if(mCardViewClickListener != null) cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCardViewClickListener.onClicked(v,mData);
                }
            });
//            textViewtitle=itemView.findViewById(R.id.item_text1);
//            textViewEnglish=itemView.findViewById(R.id.item_text2);
//            imageView=itemView.findViewById(R.id.icon);

        }
        //给每个控件赋值
        public void setData(NFTDetailData iconbean) {
            mData = iconbean;
//            textViewtitle.setText(iconbean.getTitle());
//            textViewEnglish.setText(iconbean.getEngtitle());
//            imageView.setImageResource(iconbean.getImageicon());
//            cardView.setCardBackgroundColor(Color.parseColor(iconbean.color));
        }
    }
}
