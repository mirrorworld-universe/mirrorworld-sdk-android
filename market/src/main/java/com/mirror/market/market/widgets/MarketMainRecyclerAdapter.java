package com.mirror.market.market.widgets;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.market.utils.GiveBitmap;
import com.mirror.sdk.ui.market.utils.MarketUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MarketMainRecyclerAdapter  extends RecyclerView.Adapter<MarketMainRecyclerAdapter.InnerHolder>{
    List<NFTDetailData> iconbeans = new ArrayList<NFTDetailData>();
    OnNFTItemClickListener mCardViewClickListener = null;

    public MarketMainRecyclerAdapter(List<NFTDetailData> iconbeans){
        this.iconbeans = iconbeans;
    }

    @NonNull
    @Override
    public MarketMainRecyclerAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.market_main_recyclerview_item,null);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketMainRecyclerAdapter.InnerHolder holder, int position) {
        holder.setData(iconbeans.get(position));
    }


    @Override
    public int getItemCount() {
        if(iconbeans != null){
            return iconbeans.size();
        }
        return 0;
    }

    public void setCardViewOnClickListener(OnNFTItemClickListener listener){
        mCardViewClickListener = listener;
    }

    public void addData(NFTDetailData newNFT) {
        int position = iconbeans.size();
        iconbeans.add(newNFT);
        notifyItemInserted(position);
    }

    public interface OnNFTItemClickListener{
        void onClicked(View view, NFTDetailData data);
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView mTvPrice, mTvNumber;
        ImageView imageView;
        View imageProgress;
        NFTDetailData mData = null;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.market_main_nft_item);
            if(mCardViewClickListener != null) cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCardViewClickListener.onClicked(v,mData);
                }
            });
            mTvPrice = itemView.findViewById(R.id.main_nft_price);
            mTvNumber =itemView.findViewById(R.id.main_nft_number);
            imageView = itemView.findViewById(R.id.main_nft_image);
            imageProgress = itemView.findViewById(R.id.main_nft_progress);
        }

        public void setData(NFTDetailData data) {
            mData = data;
            MarketUtils.startLoadImage(handle,data.image,imageProgress,imageView);
            mTvNumber.setText(data.name);

            BigDecimal bg = new BigDecimal(data.price);
            double f1 = bg.setScale(MirrorMarketConfig.NFT_PRICE_MAX_DECIMAL_PLACE, BigDecimal.ROUND_HALF_UP).doubleValue();
            mTvPrice.setText(String.valueOf(f1));
        }

        private Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        GiveBitmap ivUrl=(GiveBitmap)msg.obj;
                        ivUrl.imageView.setImageBitmap(ivUrl.bitmap);

                        imageProgress.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        break;
                }
            };
        };
    }
}
