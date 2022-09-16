package com.mirror.sdk.ui.market.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.mirror.sdk.ui.market.MarketUtils;
import com.mirror.sdk.ui.market.MirrorMarketConfig;
import com.mirror.sdk.ui.market.model.NFTDetailData;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
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
        void onClicked(View view, NFTDetailData data);
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView mTvPrice, mTvNumber;
        ImageView imageView;
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
        }

        public void setData(NFTDetailData data) {
            mData = data;
            startLoadImage(data.image,imageView);
            mTvNumber.setText(data.name);

            BigDecimal bg = new BigDecimal(data.price);
            double f1 = bg.setScale(MirrorMarketConfig.NFT_PRICE_MAX_DECIMAL_PLACE, BigDecimal.ROUND_HALF_UP).doubleValue();
            mTvPrice.setText(String.valueOf(f1));
        }

        public Bitmap getURLimage(String url) {
            Bitmap bmp = null;
            try {
                URL myurl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
                conn.setConnectTimeout(6000);//设置超时
                conn.setDoInput(true);
                conn.setUseCaches(false);//不缓存
                conn.connect();
                InputStream is = conn.getInputStream();//获得图片的数据流
                bmp = BitmapFactory.decodeStream(is);//读取图像数据
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bmp;
        }

        class IvUrl{
            public ImageView imageView;
            public Bitmap bitmap;
        }
        public void startLoadImage(String url,ImageView imageView){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bmp = getURLimage(url);
                    Message msg = new Message();
                    msg.what = 0;
                    IvUrl ivUrl = new IvUrl();
                    ivUrl.imageView = imageView;
                    ivUrl.bitmap = bmp;
                    msg.obj = ivUrl;
                    System.out.println("000");
                    handle.sendMessage(msg);
                }
            }).start();
        }

        private Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        System.out.println("111");
                        IvUrl ivUrl=(IvUrl)msg.obj;
                        ivUrl.imageView.setImageBitmap(ivUrl.bitmap);
                        break;
                }
            };
        };
    }
}
