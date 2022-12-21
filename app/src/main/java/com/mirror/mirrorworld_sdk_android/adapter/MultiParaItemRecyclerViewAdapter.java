package com.mirror.mirrorworld_sdk_android.adapter;

import static org.junit.Assert.assertEquals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
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
import com.mirror.sdk.MirrorMarket;
import com.mirror.sdk.MirrorWorld;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.marketui.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.marketui.GetCollectionInfoListener;
import com.mirror.sdk.listener.marketui.GetNFTEventsListener;
import com.mirror.sdk.listener.marketui.GetNFTRealPriceListener;
import com.mirror.sdk.listener.marketui.SearchNFTsListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.mirrorworld_sdk_android.DemoAPIID;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.FetchSingleNFTListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
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
import com.mirror.sdk.response.marketui.FilterInfo;
import com.mirror.sdk.response.marketui.GetCollectionFilterInfoRes;
import com.mirror.sdk.response.marketui.GetCollectionInfoRes;
import com.mirror.sdk.response.marketui.GetNFTRealPriceRes;
import com.mirror.sdk.response.marketui.GetNFTsRes;
import com.mirror.sdk.response.marketui.MirrorMarketNFTEvent;
import com.mirror.sdk.response.marketui.MirrorMarketSearchNFTObj;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.utils.MirrorGsonUtils;
import com.mirror.sdk.utils.MirrorStringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        if(apiId == DemoAPIID.INIT_SDK){
            MirrorWorld.initMirrorWorld(mContext,String.valueOf(holder.mEt1.getText()), MirrorEnv.StagingDevNet);
            holder.mResultView.setText("SDK has been inited!");
        }else if(apiId == DemoAPIID.SET_JWT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            MirrorSDK.getInstance().SetAccessToken(String.valueOf(holder.mEt1.getText()));
            holder.mResultView.setText("JWT has been set!");
        }else if(apiId == DemoAPIID.START_LOGIN){
            MirrorWorld.startLogin(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }else if(apiId == DemoAPIID.LOGOUT){
            MirrorWorld.logout(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    holder.mResultView.setText("Logout result:"+boolValue);
                }
            });
        }else if(apiId == DemoAPIID.OPEN_WALLET){
            MirrorWorld.openWallet(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    Log.d("MirrorSDK","Wallet logout callback runs!");
                }
            });
        }else if(apiId == DemoAPIID.OPEN_MARKET){
            MirrorWorld.openMarket();
        }else if(apiId == DemoAPIID.LOGIN_With_EMAIL){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String email = String.valueOf(holder.mEt1.getText());
            String passWord = String.valueOf(holder.mEt2.getText());

            MirrorWorld.loginWithEmail(email,passWord,new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                    MirrorSDK.getInstance().SetAccessToken(MirrorSDK.getInstance().GetAccessTokenFromResponse(s));
                    MirrorSDK.getInstance().SetRefreshToken(MirrorSDK.getInstance().GetRefreshTokenFromResponse(s));
                }
            });
        }else if(apiId == DemoAPIID.FETCH_USER){
            MirrorWorld.fetchUser(new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+" eth_address "+userResponse.wallet.eth_address+" solona_address "+userResponse.wallet.sol_address);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPIID.QUERY_USER){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String email = String.valueOf(holder.mEt1.getText());
            MirrorWorld.queryUser(email,new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    holder.mResultView.setText(userResponse.email+" sol_address "+userResponse.sol_address);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);

                }
            });
        }else if(apiId == DemoAPIID.CREATE_VERIFIED_COLLECTION){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String name = String.valueOf(holder.mEt1.getText());
            String symbol = String.valueOf(holder.mEt2.getText());
            String detailsUrl = String.valueOf(holder.mEt3.getText());

            MirrorWorld.createVerifiedCollection(name, symbol, detailsUrl, new CreateTopCollectionListener() {
                @Override
                public void onCreateSuccess(MintResponse mintResponse) {
                    holder.mResultView.setText("Creating result is:"+MirrorGsonUtils.getInstance().toJson(mintResponse));
                }

                @Override
                public void onCreateFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPIID.MINT_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String collection_mint = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String detailUrl = String.valueOf(holder.mEt4.getText());

            MirrorWorld.mintNFT(collection_mint, name, symbol, detailUrl, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    MirrorSDK.getInstance().logFlow("Mint nft result:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    holder.mResultView.setText("Mint NFT result is:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                }

                @Override
                public void onMintFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPIID.LIST_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());

            Double price = 0.0;

            try{
                 price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorWorld.listingNFT(mint_address, price, new ListNFTListener() {
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
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;

            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorWorld.updateNFT(mint_address, price, new UpdateListListener() {
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
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String priceStr = String.valueOf(holder.mEt2.getText());
            Double price = 0.0;
            try{
                price = Double.valueOf(priceStr);
            }catch (NumberFormatException e){

            }
            MirrorWorld.cancelNFTListing(mint_address, price, new CancelListListener() {
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
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            List<String>  owners = new ArrayList<>();
            owners.add(String.valueOf(holder.mEt1.getText()));

            int limit = 0;
            int offset = 0;
            try{
                limit =  Integer.valueOf(String.valueOf(holder.mEt2.getText()));
                offset = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
            }catch (NumberFormatException E){

            }
            MirrorWorld.fetchNFTsByOwnerAddresses(owners, limit, offset, new FetchByOwnerListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    int count = multipleNFTsResponse.nfts.size();
                    String countStr = "You have " + count + " NFTs";
                    holder.mResultView.setText(countStr);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPIID.FETCH_NFT_BY_MINT_ADDRESSES){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            List<String> mint_address = new ArrayList<>();
            mint_address.add(String.valueOf(holder.mEt1.getText()));
            MirrorWorld.fetchNFTsByMintAddresses(mint_address, new FetchNFTsListener() {
                @Override
                public void onFetchSuccess(MultipleNFTsResponse multipleNFTsResponse) {
                    String notice = "Fetched " + multipleNFTsResponse.nfts.size() + " NFTs";
                    holder.mResultView.setText(notice);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPIID.FETCH_NFT_BY_UPDATE_AUTHORITIES){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            List<String> update_address = new ArrayList<>();
            update_address.add(String.valueOf(holder.mEt1.getText()));

            int limit = 0;
            int offset = 0;
            try{
               limit =  Integer.valueOf(String.valueOf(holder.mEt2.getText()));
               offset = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
            }catch (NumberFormatException E){

            }
            MirrorWorld.fetchNFTsByUpdateAuthorities(update_address, limit, offset, new FetchNFTsListener() {
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
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address =String.valueOf(holder.mEt1.getText());
            MirrorWorld.getNFTDetails(mint_address, new FetchSingleNFTListener() {
                @Override
                public void onFetchSuccess(SingleNFTResponse nftObject) {
                    holder.mResultView.setText("NFT details is:"+MirrorGsonUtils.getInstance().toJson(nftObject));
                }

                @Override
                public void onFetchFailed(long code, String message) {

                }
            });
        }else if(apiId == DemoAPIID.FETCH_NFT_MARKETPLACE_ACTIVITY){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address =String.valueOf(holder.mEt1.getText());
            MirrorWorld.fetchNFTMarketplaceActivity(mint_address, new FetchSingleNFTActivityListener() {
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
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            String to_wallet_address = String.valueOf(holder.mEt2.getText());
            MirrorWorld.transferNFT(mint_address, to_wallet_address, new TransferNFTListener() {
                @Override
                public void onTransferSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText(listingResponse.mint_address);
                }

                @Override
                public void onTransferFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPIID.TRANSFER_SPL_TOKEN){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String toPublicKey = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            String tokenMint = String.valueOf(holder.mEt3.getText());
            String decimalsStr = String.valueOf(holder.mEt4.getText());

            float amount = 0.0f;
            float decimals = 0.0f;
            try{
                amount =  Float.valueOf(amountStr);
                decimals =  Float.valueOf(decimalsStr);
            }catch (NumberFormatException E){
            }

            MirrorWorld.transferSPLToken(toPublicKey, amount, tokenMint, decimals, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }
        else if(apiId == DemoAPIID.BUY_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = 0.0;
            try{
                price =  Double.valueOf(String.valueOf(holder.mEt2.getText()));
            }catch (NumberFormatException E){
            }

            MirrorWorld.buyNFT(mint_address, price, new BuyNFTListener() {
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
            MirrorWorld.getTokens(new GetWalletTokenListener() {
                @Override
                public void onSuccess(GetWalletTokenResponse walletTokenResponse) {
                    holder.mResultView.setText("Get wallet token success!");
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetWalletToken",code,message));
                }
            });
        }else if(apiId == DemoAPIID.WALLET_TRANSACTIONS){
            if(!checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int limit = 0;
            try{
                limit = Integer.valueOf(String.valueOf(holder.mEt1.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            String before = String.valueOf(holder.mEt2.getText());
            MirrorWorld.getTransactions(limit, before, new GetWalletTransactionListener() {
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
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String sig = String.valueOf(holder.mEt1.getText());
            MirrorWorld.getTransaction(sig, new GetOneWalletTransactionBySigListener() {
                @Override
                public void onSuccess(TransactionsDTO walletTransactions) {
                    holder.mResultView.setText("GetTransactionBySignature success!" + MirrorGsonUtils.getInstance().toJson(walletTransactions));
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetTransactionBySignature",code,message));
                }
            });
        }else if(apiId == DemoAPIID.TRANSFER_SQL){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String public_key = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            float amount = 0;
            try{
               amount = Integer.valueOf(amountStr);
            }catch (NumberFormatException e){

            }
            MirrorWorld.transferSOL(public_key, amount, new TransferSOLListener() {
                @Override
                public void onTransferSuccess(TransferResponse transferResponse) {
                    holder.mResultView.setText("transfer sol success!");
                }

                @Override
                public void onTransferFailed(long code, String message) {
                    holder.mResultView.setText("transfer sol failed!code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPIID.GET_COLLECTION_FILTER_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            MirrorMarket.getCollectionFilterInfo(collection, new GetCollectionFilterInfoListener() {
                @Override
                public void onSuccess(GetCollectionFilterInfoRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPIID.GET_NFT_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            MirrorMarket.getNFTInfo(mint_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText("Visiting result:"+MirrorGsonUtils.getInstance().toJson(result));
                }
            });
        }else if(apiId == DemoAPIID.GET_COLLECTION_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MirrorMarket.getCollectionInfo(collections, new GetCollectionInfoListener() {
                @Override
                public void onSuccess(List<GetCollectionInfoRes> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPIID.GET_NFT_EVENTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            int page = 0;
            int page_size = 0;
            String mint_address = String.valueOf(holder.mEt1.getText());
            String pageStr = String.valueOf(holder.mEt2.getText());
            String pageSizeStr = String.valueOf(holder.mEt3.getText());
            page = Integer.valueOf(pageStr);
            page_size = Integer.valueOf(pageSizeStr);
            MirrorMarket.getNFTEvents(mint_address,page,page_size, new GetNFTEventsListener() {
                @Override
                public void onSuccess(List<MirrorMarketNFTEvent> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPIID.SEARCH_NFTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            String searchString = String.valueOf(holder.mEt2.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MirrorMarket.searchNFTs(collections,searchString, new SearchNFTsListener() {
                @Override
                public void onSuccess(GetNFTsRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPIID.RECOMMEND_SEARCH_NFT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MirrorMarket.recommondSearchNFT(collections, new SearchNFTsListener() {
                @Override
                public void onSuccess(GetNFTsRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPIID.GET_NFTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            Double sale = Double.valueOf(String.valueOf(holder.mEt2.getText()));

            JSONObject filter = new JSONObject();
            try {
                filter.put("filter_name","Rarity");
                filter.put("filter_type","enum");
                JSONArray values = new JSONArray();
                values.put("Common");
                filter.put("filter_value",values);
                filter.put("filter_type","enum");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<JSONObject> filters = new ArrayList<>();
            filters.add(filter);
            MirrorMarket.getNFTs(collection,1,10,"price",true,sale,filters, new SearchNFTsListener() {
                @Override
                public void onSuccess(GetNFTsRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPIID.GET_NFT_REAL_PRICE){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int fee = Integer.parseInt(String.valueOf(holder.mEt2.getText()));
            String price = String.valueOf(holder.mEt1.getText());
            MirrorMarket.getNFTRealPrice(price,fee, new GetNFTRealPriceListener() {
                @Override
                public void onSuccess(GetNFTRealPriceRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }
    }

    private void showToast(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_LONG).show();
    }

    private boolean checkEt(EditText et1){
        if(et1 == null || (et1 != null && et1.getText().length() == 0)){
            Log.e("MirrorSDK","edit text is null!"+et1.getText().length());
            return false;
        }
        return true;
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
