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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.mirrorworld_sdk_android.DemoAPIID;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateSubCollectionListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.FetchSingleNFTListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransactionsDTO;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.market.SingleNFTResponse;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.utils.MirrorStringUtils;

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

        if(apiId == DemoAPIID.SET_APP_ID){
            MirrorSDK.getInstance().SetApiKey(String.valueOf(holder.mEt1.getText()));
            holder.mResultView.setText(String.valueOf(holder.mEt1.getText()));

        }else if(apiId == DemoAPIID.START_LOGIN){
            MirrorSDK.getInstance().StartLogin(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }else if(apiId == DemoAPIID.OPEN_WALLET){
            MirrorSDK.getInstance().OpenWallet();
        }else if(apiId == DemoAPIID.OPEN_MARKET){

        }else if(apiId == DemoAPIID.LOGIN_With_EMAIL){

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

        }else if(apiId == DemoAPIID.FETCH_USER){

            MirrorSDK.getInstance().FetchUser(new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+userResponse.eth_address);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });

        }else if(apiId == DemoAPIID.QUERY_USER){

            String email = String.valueOf(holder.mEt1.getText());
            MirrorSDK.getInstance().QueryUser(email, new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+userResponse.eth_address);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);

                }
            });

        }else if(apiId == DemoAPIID.CREATE_VERIFIED_COLLECTION){
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
        }else if(apiId == DemoAPIID.CREATE_VERIFIED_SUB_COLLECTION){
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
        }else if(apiId == DemoAPIID.MINT_NFT){
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
        }else if(apiId == DemoAPIID.LIST_NFT){
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());

            Double price = 0.0;

            try{
                 price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorSDK.getInstance().ListNFT(mint_address, price, MirrorConfirmation.Finalized, new ListNFTListener() {
                @Override
                public void onListSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("ListNFT success! price is:"+listingResponse.price);
                }

                @Override
                public void onListFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("ListNFT",code,message));
                }
            });
        }else if(apiId == DemoAPIID.UPDATE_NFT_LISTING){
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;

            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorSDK.getInstance().UpdateNFTListing(mint_address, price, new UpdateListListener() {
                @Override
                public void onUpdateSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("UpdateNFTListing success! New price:"+listingResponse.price);
                }

                @Override
                public void onUpdateFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("UpdateNFTListing",code,message));
                }
            });


        }else if(apiId == DemoAPIID.CANCEL_NFT_LISTING){
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;
            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorSDK.getInstance().CancelNFTListing(mint_address, price, new CancelListListener() {
                @Override
                public void onCancelSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("CancelNFTListing success! Mint address is "+listingResponse.mint_address);
                }

                @Override
                public void onCancelFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("CancelNFTListing",code,message));
                }
            });

        }else if(apiId == DemoAPIID.FETCH_NFT_BY_OWNER_ADDRESSES){
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

        }else if(apiId == DemoAPIID.FETCH_NFT_BY_MINT_ADDRESSES){
            List<String> mint_address = new ArrayList<>();
            mint_address.add(String.valueOf(holder.mEt1.getText()));

            MirrorSDK.getInstance().FetchNFTsByMintAddresses(mint_address, new FetchNFTsListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    holder.mResultView.setText(multipleNFTsResponse.nfts.size());
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });

        }else if(apiId == DemoAPIID.FETCH_NFT_BY_UPDATE_AUTHORITIES){
            List<String> update_address = new ArrayList<>();
            update_address.add(String.valueOf(holder.mEt1.getText()));

            Double limit = 0.0;
            Double offset = 0.0;

            try{
               limit =  Double.valueOf(String.valueOf(holder.mEt2.getText()));
               offset = Double.valueOf(String.valueOf(holder.mEt3.getText()));

            }catch (NumberFormatException E){

            }

            MirrorSDK.getInstance().FetchNFTsByUpdateAuthorities(update_address, limit, offset, new FetchNFTsListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    holder.mResultView.setText("FetchNFTsByUpdateAuthorities success!nft count is:"+multipleNFTsResponse.nfts.size());
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("FetchNFTsByUpdateAuthorities",code,message));
                }
            });

        }else if(apiId == DemoAPIID.FETCH_SINGLE_NFT_DETAILS){
            String mint_address =String.valueOf(holder.mEt1.getText());

            MirrorSDK.getInstance().GetNFTDetails(mint_address, new FetchSingleNFTListener() {
                @Override
                public void onFetchSuccess(SingleNFTResponse nftObject) {
                    holder.mResultView.setText(nftObject.nft.name+nftObject.nft.description);
                }

                @Override
                public void onFetchFailed(long code, String message) {

                }
            });

        }else if(apiId == DemoAPIID.FETCH_NFT_MARKETPLACE_ACTIVITY){
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

        }else if(apiId == DemoAPIID.TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET){

            String mint_address = String.valueOf(holder.mEt1.getText());
            String to_wallet_address = String.valueOf(holder.mEt2.getText());

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
        }else if(apiId == DemoAPIID.BUY_NFT){

            String mint_address = String.valueOf(holder.mEt1.getText());

            Double price = 0.0;

            try{
                price =  Double.valueOf(String.valueOf(holder.mEt2.getText()));

            }catch (NumberFormatException E){

            }

            MirrorSDK.getInstance().BuyNFT(mint_address, price, new BuyNFTListener() {
                @Override
                public void onBuySuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText(listingResponse.mint_address);
                }

                @Override
                public void onBuyFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });

        }else if(apiId == DemoAPIID.GET_WALLET_TOKEN){
            MirrorSDK.getInstance().GetWalletTokens(new GetWalletTokenListener() {
                @Override
                public void onSuccess(GetWalletTokenResponse walletTokenResponse) {
                    holder.mResultView.setText("Get wallet token success! Token count is:"+walletTokenResponse.tokens.size());
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetWalletToken",code,message));
                }
            });


        }else if(apiId == DemoAPIID.WALLET_TRANSACTIONS){
            String limit = String.valueOf(holder.mEt1.getText());
            String before = String.valueOf(holder.mEt2.getText());
            MirrorSDK.getInstance().Transactions(limit, before, new GetWalletTransactionListener() {
                @Override
                public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
                    holder.mResultView.setText("GetTransactions success! count is "+walletTransactionsResponse.count);
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("Transactions",code,message));
                }
            });


        }else if(apiId == DemoAPIID.WALLET_TRANSACTIONS_BY_SIGNATURE){
            String sig = String.valueOf(holder.mEt1.getText());

            MirrorSDK.getInstance().GetTransactionBySignature(sig, new GetWalletTransactionBySigListener() {
                @Override
                public void onSuccess(List<TransactionsDTO> walletTransactions) {
                    holder.mResultView.setText("GetTransactionBySignature success! transaction count is " + walletTransactions.size());
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetTransactionBySignature",code,message));
                }
            });


        }else if(apiId == DemoAPIID.TRANSFER_SQL){
            String public_key = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            int amount = 0;
            try{
               amount = Integer.valueOf(amountStr);
            }catch (NumberFormatException e){

            }


            MirrorSDK.getInstance().TransferSOL(public_key, amount, new TransferSOLListener() {
                @Override
                public void onTransferSuccess(TransferResponse transferResponse) {
                    holder.mResultView.setText("transfer sol success!");
                }

                @Override
                public void onTransferFailed(long code, String message) {
                    holder.mResultView.setText("transfer sol failed!code:"+code+" message:"+message);
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
