package com.mirror.mirrorworld_sdk_android.adapter;

import static org.junit.Assert.assertEquals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConstant;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.MirrorListener;
import com.mirror.sdk.listener.market.CreateSubCollectionListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchByMintAddressListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
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

                handleClick(mValues.get(position).id,holder,view);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void handleClick(int apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder,View view){

        if(apiId == MirrorConstant.SET_APP_ID){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            MirrorSDK.getInstance().SetAppID(String.valueOf(holder.mEt1.getText()));
            holder.mResultView.setText(String.valueOf(holder.mEt1.getText()));

        }else if(apiId == MirrorConstant.LOGIN_With_EMAIL){

            String email = String.valueOf(holder.mEt1.getText());
            String passWord = String.valueOf(holder.mEt2.getText());

            MirrorSDK.getInstance().LoginWithEmail(email,passWord, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                    MirrorSDK.getInstance().SetAccessToken(MirrorSDK.getInstance().GetAccessTokenFromResponse(s));
                    MirrorSDK.getInstance().SetRefreshToken(MirrorSDK.getInstance().GetRefreshTokenFromResponse(s));
                }
            });

        }else if(apiId == MirrorConstant.FETCH_USER){

            MirrorSDK.getInstance().FetchUser(new MirrorListener.FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+userResponse.eth_address);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });

        }else if(apiId == MirrorConstant.QUERY_USER){

            String email = String.valueOf(holder.mEt1.getText());
            MirrorSDK.getInstance().QueryUser(email, new MirrorListener.FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+userResponse.eth_address);

                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);

                }
            });

        }else if(apiId == MirrorConstant.CREATE_VERIFIED_COLLECTION){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Unknown);

            String name = String.valueOf(holder.mEt1.getText());
            String symbol = String.valueOf(holder.mEt2.getText());
            String detailsUrl = String.valueOf(holder.mEt3.getText());

            MirrorSDK.getInstance().CreateVerifiedCollection(name, symbol, detailsUrl, new CreateTopCollectionListener() {
                @Override
                public void onCreateSuccess(MintResponse mintResponse) {
                    holder.mResultView.setText(mintResponse.name+mintResponse.mint_address);
                }

                @Override
                public void onCreateFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == MirrorConstant.CREATE_VERIFIED_SUB_COLLECTION){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Unknown);

            String collection_mint = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String detailUrl = String.valueOf(holder.mEt4.getText());

            MirrorSDK.getInstance().CreateVerifiedSubCollection(collection_mint, name, symbol, detailUrl, new CreateSubCollectionListener() {
                @Override
                public void onCreateSuccess(MintResponse userResponse) {
                    holder.mResultView.setText(userResponse.name+userResponse.mint_address);
                }

                @Override
                public void onCreateFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == MirrorConstant.MINT_NFT){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Unknown);

            String collection_mint = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String detailUrl = String.valueOf(holder.mEt4.getText());


            MirrorSDK.getInstance().MintNFT(collection_mint, name, symbol, detailUrl, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    holder.mResultView.setText(userResponse.name+userResponse.mint_address);
                }

                @Override
                public void onMintFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == MirrorConstant.LIST_NFT){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);

            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());

            Double price = 0.0;

            try{
                 price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorSDK.getInstance().ListNFT(mint_address, price, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }else if(apiId == MirrorConstant.UPDATE_NFT_LISTING){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;

            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });


        }else if(apiId == MirrorConstant.CANCEL_NFT_LISTING){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;
            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorSDK.getInstance().CancelNFTListing(mint_address, price, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });

        }else if(apiId == MirrorConstant.FETCH_NFT_BY_OWNER_ADDRESSES){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            List<String>  owners = new ArrayList<>();
            owners.add(String.valueOf(holder.mEt1.getText()));

            int limit = 0;
            int offset = 0;

            try{
                limit =  Integer.valueOf(String.valueOf(holder.mEt2.getText()));
                offset = Integer.valueOf(String.valueOf(holder.mEt3.getText()));

            }catch (NumberFormatException E){

            }


            MirrorSDK.getInstance().FetchNFTsByOwnerAddresses(owners, limit, offset, new FetchByOwnerListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    holder.mResultView.setText(multipleNFTsResponse.nfts.size());
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });

        }else if(apiId == MirrorConstant.FETCH_NFT_BY_MINT_ADDRESSES){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            List<String> mint_address = new ArrayList<>();
            mint_address.add(String.valueOf(holder.mEt1.getText()));

            MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_address, new FetchByMintAddressListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    holder.mResultView.setText(multipleNFTsResponse.nfts.size());
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });

        }else if(apiId == MirrorConstant.FETCH_NFT_BY_UPDATE_AUTHORITIES){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            List<String> update_address = new ArrayList<>();
            update_address.add(String.valueOf(holder.mEt1.getText()));

            Double limit = 0.0;
            Double offset = 0.0;

            try{
               limit =  Double.valueOf(String.valueOf(holder.mEt2.getText()));
               offset = Double.valueOf(String.valueOf(holder.mEt3.getText()));

            }catch (NumberFormatException E){

            }

            MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(update_address,limit,offset,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });

        }else if(apiId == MirrorConstant.FETCH_SINGLE_NFT_DETAILS){
            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            String mint_address =String.valueOf(holder.mEt1.getText());

            MirrorSDK.getInstance().FetchSingleNFTDetails(mint_address, new MirrorListener.FetchSingleNFT() {
                @Override
                public void onNFTFetched(NFTObject nftObject) {

                    holder.mResultView.setText(nftObject.name+nftObject.description);
                }

                @Override
                public void onNFTFetchFailed(long code, String message) {

                }
            });

        }else if(apiId == MirrorConstant.FETCH_NFT_MARKETPLACE_ACTIVITY){
            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            String mint_address =String.valueOf(holder.mEt1.getText());

            MirrorSDK.getInstance().FetchNFTMarketplaceActivity(mint_address, new FetchSingleNFTActivityListener() {
                @Override
                public void onFetchSuccess(ActivityOfSingleNftResponse activityOfSingleNftResponse) {
                    holder.mResultView.setText(activityOfSingleNftResponse.mintAddress);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });

        }else if(apiId == MirrorConstant.TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET){

            String mint_address = String.valueOf(holder.mEt1.getText());
            String to_wallet_address = String.valueOf(holder.mEt2.getText());

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            MirrorSDK.getInstance().TransferNFTToAnotherSolanaWallet(mint_address, to_wallet_address, new TransferNFTListener() {
                @Override
                public void onTransferSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText(listingResponse.mint_address);
                }

                @Override
                public void onTransferFailed(long code, String message) {
                   holder.mResultView.setText(message);
                }
            });
        }else if(apiId == MirrorConstant.GET_WALLET_TOKEN){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);

            MirrorSDK.getInstance().GetWalletToken(new MirrorCallback() {
                @Override
                public void callback(String result) {

                    holder.mResultView.setText(result);

                }
            });


        }else if(apiId == MirrorConstant.WALLET_TRANSACTIONS){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            String limit = String.valueOf(holder.mEt1.getText());
            String before = String.valueOf(holder.mEt2.getText());
            MirrorSDK.getInstance().Transactions(limit, before, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });


        }else if(apiId == MirrorConstant.WALLET_TRANSACTIONS_BY_SIGNATURE){

            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            String sig = String.valueOf(holder.mEt1.getText());

            MirrorSDK.getInstance().GetTransactionBySignature(sig, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });


        }else if(apiId == MirrorConstant.TRANSFER_SQL){


            MirrorSDK.getInstance().InitSDK(mContext, MirrorEnv.Staging);
            String public_key = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            int amount = 0;
            try{
               amount = Integer.valueOf(amountStr);
            }catch (NumberFormatException e){

            }


            MirrorSDK.getInstance().PostTransferSQL(public_key, amount, new MirrorCallback() {
                @Override
                public void callback(String result) {

                    holder.mResultView.setText(result);
                }

            });


        }


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
