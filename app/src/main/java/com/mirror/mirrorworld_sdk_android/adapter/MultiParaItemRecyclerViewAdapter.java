package com.mirror.mirrorworld_sdk_android.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.listener.MirrorListener;
import com.mirror.sdk.response.market.NFTObject;
import com.mirror.sdk.utils.MirrorGsonUtils;

import java.util.ArrayList;
import java.util.List;

/*** @author Pu
 * @createTime 2022/8/17 17:12
 * @projectName mirrorworld-sdk-android
 * @className MultiParaItemRecyclerViewAdapter.java
 * @description TODO
 */
public class MultiParaItemRecyclerViewAdapter extends RecyclerView.Adapter<MultiParaItemRecyclerViewAdapter.ViewHolder>{



    private  List<MultiItemData.MultiItem> mValues;
    private Activity mContext;

    public MultiParaItemRecyclerViewAdapter(List<MultiItemData.MultiItem> items) {
        if(null == mValues){
            this.mValues = new ArrayList<MultiItemData.MultiItem>();
        }
        this.mValues.clear();
        this.mValues.addAll(items);
    }

    public void SetContext(Activity context){
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String hintPre = "Param:";
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("No."+mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).name);
        holder.mButton.setText(mValues.get(position).buttonText);
        holder.mResultView.setText(mValues.get(position).resultDefault);
        if(mValues.get(position).et1Hint == null){
            holder.mEt1.setVisibility(View.GONE);
        }else {
            holder.mEt1.setHint(hintPre+mValues.get(position).et1Hint);
        }
        if(mValues.get(position).et2Hint == null){
            holder.mEt2.setVisibility(View.GONE);
        }else {
            holder.mEt2.setHint(hintPre+mValues.get(position).et2Hint);
        }
        if(mValues.get(position).et3Hint == null){
            holder.mEt3.setVisibility(View.GONE);
        }else {
            holder.mEt3.setHint(hintPre+mValues.get(position).et3Hint);
        }
        if(mValues.get(position).et4Hint == null){
            holder.mEt4.setVisibility(View.GONE);
        }else {
            holder.mEt4.setHint(hintPre+mValues.get(position).et4Hint);
        }
        if(mValues.get(position).et5Hint == null){
            holder.mEt5.setVisibility(View.GONE);
        }else {
            holder.mEt5.setHint(hintPre+mValues.get(position).et5Hint);
        }
        if(mValues.get(position).et6Hint == null){
            holder.mEt6.setVisibility(View.GONE);
        }else {
            holder.mEt6.setHint(hintPre+mValues.get(position).et6Hint);
        }

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(mValues.get(position).id,holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    private void handleClick(int apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder){
        if(apiId == 12){
            String mint_address = String.valueOf(holder.mEt1.getText());
            MirrorSDK.getInstance().FetchSingleNFTDetails(mint_address, new MirrorListener.FetchSingleNFT() {
                @Override
                public void onNFTFetched(NFTObject nftObject) {
                    String s = MirrorGsonUtils.getInstance().toJson(nftObject);
                    holder.mResultView.setText(s);
                }

                @Override
                public void onNFTFetchFailed(long code, String message) {
                    holder.mResultView.setText("error:"+code+" "+message);
                }
            });
        }else if(apiId == 32){
            String name = String.valueOf(holder.mEt1.getText());
            String symbol = String.valueOf(holder.mEt2.getText());
            String url = String.valueOf(holder.mEt3.getText());
            MirrorSDK.getInstance().CreateVerifiedCollection(name, symbol, url, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 31){
            String collection = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String url = String.valueOf(holder.mEt3.getText());
            MirrorSDK.getInstance().MintNFT(collection,name, symbol, url, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 33){
            String collection_mint = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String url = String.valueOf(holder.mEt4.getText());
            String confirmation = String.valueOf(holder.mEt5.getText());
            MirrorSDK.getInstance().CreateVerifiedSubCollection(collection_mint,name, symbol, url, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 34){
            String ad1 = String.valueOf(holder.mEt1.getText());
            String ad2 = String.valueOf(holder.mEt2.getText());
            List<String> list = new ArrayList<>();
            list.add(ad1);
            list.add(ad2);
            MirrorSDK.getInstance().FetchNFTsByMintAddresses(list, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 35){
            String creator1 = String.valueOf(holder.mEt1.getText());
            Double limit = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            Double offset = Double.valueOf(String.valueOf(holder.mEt3.getText()));
            List<String> list = new ArrayList<>();
            list.add(creator1);
            MirrorSDK.getInstance().FetchNFTsByCreatorAddresses(list,limit,offset, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 36){
            String update_authority1 = String.valueOf(holder.mEt1.getText());
            Double limit = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            Double offset = Double.valueOf(String.valueOf(holder.mEt3.getText()));
            List<String> list = new ArrayList<>();
            list.add(update_authority1);
            MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(list,limit,offset, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 37){
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            MirrorSDK.getInstance().ListNFT(mint_address,price, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 38){
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            MirrorSDK.getInstance().UpdateNFTListing(mint_address,price, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 39){
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            MirrorSDK.getInstance().BuyNFT(mint_address,price, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 40){
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            MirrorSDK.getInstance().CancelNFTListing(mint_address,price, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 41){
            String mint_address = String.valueOf(holder.mEt1.getText());
            String to_wallet_address = String.valueOf(holder.mEt2.getText());
            MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet(mint_address,to_wallet_address, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }else if(apiId == 42){
            String owners = String.valueOf(holder.mEt1.getText());
            int limit = Integer.valueOf(String.valueOf(holder.mEt2.getText()));
            int offset = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
            List<String> params = new ArrayList<>();
            params.add(owners);

            MirrorSDK.getInstance().FetchNFTsByOwnerAddresses(params, limit, offset, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });


        }else if(apiId == 43){

            String mint_address = String.valueOf(holder.mEt1.getText());
            MirrorSDK.getInstance().FetchNFTMarketplaceActivity(mint_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });

        } else if(apiId == 101){
//            MirrorSDKJava.getInstance().APIGetWalletAddress(new MirrorCallback() {
//                @Override
//                public void callback(String s) {
//                    holder.mResultView.setText(s);
//                }
//            });
        }else if(apiId == 102){

            //  to_publickey","amount"

            String to_publickey = String.valueOf(holder.mEt1.getText());
            int amount = Integer.valueOf(holder.mEt2.getText().toString());

            MirrorSDK.getInstance().APIPostTransferSQL(to_publickey, amount, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }else if(apiId == 103){

            // "to_publickey","amount",
            //                        "token_mint","decimals",
            int amount = 0;
            int decimals = 0;
            String to_publickey = String.valueOf(holder.mEt1.getText());

            String token_mint = String.valueOf(holder.mEt3.getText());

            try{
                decimals = Integer.valueOf(holder.mEt4.getText().toString());
                amount = Integer.valueOf(holder.mEt2.getText().toString());

            }catch (Exception e){

            }


            MirrorSDK.getInstance().PostTransferToken(to_publickey, amount, token_mint, decimals, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });

        }else if(apiId == 104){

            MirrorSDK.getInstance().GetWalletToken(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }else if(apiId == 105){
            String limit = String.valueOf(holder.mEt1.getText());
            String before = holder.mEt2.getText().toString();
            MirrorSDK.getInstance().Transactions(limit, before, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });

        }

        // TODO: get request params from user input

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button mButton;
        public final TextView mResultView;
        public final EditText mEt1;
        public final EditText mEt2;
        public final EditText mEt3;
        public final EditText mEt4;
        public final EditText mEt5;
        public final EditText mEt6;
        public MultiItemData.MultiItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.multiapi_item_number);
            mContentView = (TextView) view.findViewById(R.id.multiapi_item_name);
            mButton = (Button) view.findViewById(R.id.multiapi_item_button);
            mResultView = (TextView) view.findViewById(R.id.multiapi_item_result);
            mEt1 = (EditText) view.findViewById(R.id.multiapi_item_paramet1);
            mEt2 = (EditText) view.findViewById(R.id.multiapi_item_paramet2);
            mEt3 = (EditText) view.findViewById(R.id.multiapi_item_paramet3);
            mEt4 = (EditText) view.findViewById(R.id.multiapi_item_paramet4);
            mEt5 = (EditText) view.findViewById(R.id.multiapi_item_paramet5);
            mEt6 = (EditText) view.findViewById(R.id.multiapi_item_paramet6);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
