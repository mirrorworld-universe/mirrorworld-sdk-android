package com.mirror.mirrorworld_sdk_android.adapter;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.mirrorworld_sdk_android.DemoAPI;
import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.mirrorworld_sdk_android.data.SpinnerBean;
import com.mirror.sdk.MirrorWorld;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingResponse;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetNFTEventsListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.metadata.GetNFTsListener;
import com.mirror.sdk.listener.metadata.SearchNFTsListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.MirrorSDK;
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
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransactionsDTO;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.market.SingleNFTResponse;
import com.mirror.sdk.response.metadata.GetCollectionFilterInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionInfoRes;
import com.mirror.sdk.response.metadata.GetNFTEventsRes;
import com.mirror.sdk.response.metadata.GetNFTRealPriceRes;
import com.mirror.sdk.response.metadata.GetNFTsRes;
import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.utils.MirrorGsonUtils;
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

    private List<MultiItemData.MultiItem> mValues;
    private Activity mContext;
    private MultiItemData.MultiItemSpinnerItem mSelectedSpinnerItem;
    private MultiItemData.MultiItemSpinnerItem mSelectedSpinnerItem2;

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
//        holder.mContentView.setText(mValues.get(position).name);
        holder.mButton.setText(mValues.get(position).buttonText);
        holder.mResultView.setText(mValues.get(position).resultDefault);
        if(mValues.get(position).et1Hint == null){
            holder.mEt1.setVisibility(View.GONE);
        }else {
            holder.mEt1.setVisibility(View.VISIBLE);
            holder.mEt1.setHint(hintPre+mValues.get(position).et1Hint);
        }
        if(mValues.get(position).et2Hint == null){
            holder.mEt2.setVisibility(View.GONE);
        }else {
            holder.mEt2.setVisibility(View.VISIBLE);
            holder.mEt2.setHint(hintPre+mValues.get(position).et2Hint);
        }
        if(mValues.get(position).et3Hint == null){
            holder.mEt3.setVisibility(View.GONE);
        }else {
            holder.mEt3.setVisibility(View.VISIBLE);
            holder.mEt3.setHint(hintPre+mValues.get(position).et3Hint);
        }
        if(mValues.get(position).et4Hint == null){
            holder.mEt4.setVisibility(View.GONE);
        }else {
            holder.mEt4.setVisibility(View.VISIBLE);
            holder.mEt4.setHint(hintPre+mValues.get(position).et4Hint);
        }
        if(mValues.get(position).et5Hint == null){
            holder.mEt5.setVisibility(View.GONE);
        }else {
            holder.mEt5.setVisibility(View.VISIBLE);
            holder.mEt5.setHint(hintPre+mValues.get(position).et5Hint);
        }
        if(mValues.get(position).et6Hint == null){
            holder.mEt6.setVisibility(View.GONE);
        }else {
            holder.mEt6.setVisibility(View.VISIBLE);
            holder.mEt6.setHint(hintPre+mValues.get(position).et6Hint);
        }
        if(mValues.get(position).spinnerData == null){
            holder.mSpinner.setVisibility(View.GONE);
        }else {
            holder.mSpinner.setVisibility(View.VISIBLE);
            initAPIItemSpinner(holder.mSpinner,holder.mItem);
        }
        if(mValues.get(position).spinnerData2 == null){
            holder.mSpinner2.setVisibility(View.GONE);
        }else {
            holder.mSpinner2.setVisibility(View.VISIBLE);
            initAPIItemSpinner2(holder.mSpinner2,holder.mItem);
        }

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(mValues.get(position).id,holder,view);
            }
        });
    }

    private void initAPIItemSpinner(ConstraintLayout layout,MultiItemData.MultiItem mItem){
        List<SpinnerBean> mData = new ArrayList<>();
        MultiItemData.MultiItemSpinnerData data = mItem.spinnerData;
        for(int i=0;i<data.items.size();i++){
            MultiItemData.MultiItemSpinnerItem item = data.items.get(i);
            mData.add(new SpinnerBean(item.number,item.ItemName));
        }
        mSelectedSpinnerItem = data.items.get(0);
        ItemSpinnerAdapter adapter = new ItemSpinnerAdapter(mData, mContext);//实例化适配器

        AdapterView adapterView = layout.findViewById(R.id.item_spinner_spinner);
        adapterView.setAdapter(adapter);


        //给选择英雄的spinner添加监听
        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override //选中的时候执行的方法
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(mContext, mData.get(i).name, Toast.LENGTH_SHORT).show();
                mSelectedSpinnerItem = data.items.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initAPIItemSpinner2(ConstraintLayout layout,MultiItemData.MultiItem mItem){
        List<SpinnerBean> mData = new ArrayList<>();
        MultiItemData.MultiItemSpinnerData data = mItem.spinnerData2;
        for(int i=0;i<data.items.size();i++){
            MultiItemData.MultiItemSpinnerItem item = data.items.get(i);
            mData.add(new SpinnerBean(item.number,item.ItemName));
        }
        mSelectedSpinnerItem2 = data.items.get(0);
        ItemSpinnerAdapter adapter = new ItemSpinnerAdapter(mData, mContext);//实例化适配器

        AdapterView adapterView = layout.findViewById(R.id.item_spinner2_spinner);
        adapterView.setAdapter(adapter);


        //给选择英雄的spinner添加监听
        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override //选中的时候执行的方法
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(mContext, mData.get(i).name, Toast.LENGTH_SHORT).show();
                mSelectedSpinnerItem2 = data.items.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void handleClick(DemoAPI apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder,View view){

        if(apiId == DemoAPI.INIT_SDK){
            //Prepare params
            String APIKey = String.valueOf(holder.mEt1.getText());
            Activity context = mContext;
            MirrorEnv environment = MirrorEnv.MainNet;
            if(mSelectedSpinnerItem == null){
                environment = MirrorEnv.MainNet;
            }else if(mSelectedSpinnerItem.number == 1){
                environment = MirrorEnv.StagingDevNet;
            }else if(mSelectedSpinnerItem.number == 2){
                environment = MirrorEnv.StagingMainNet;
            }else if(mSelectedSpinnerItem.number == 3){
                environment = MirrorEnv.MainNet;
            }else if(mSelectedSpinnerItem.number == 4){
                environment = MirrorEnv.DevNet;
            }else {
                Log.e("MirrorSDK","Unknwon select environment:"+environment);
            }
            MirrorChains chain = MirrorChains.SOLANA;
            if(mSelectedSpinnerItem2 == null){
                chain = MirrorChains.SOLANA;
            }else if(mSelectedSpinnerItem2.number == 1){
                chain = MirrorChains.SOLANA;
            }else if(mSelectedSpinnerItem2.number == 2){
                chain = MirrorChains.EVM;
            }else {
                Log.e("MirrorSDK","Unknwon select chain:"+chain);
            }


            //Call API:initMirrorWorld
            MirrorWorld.initMirrorWorld(context, APIKey, chain, environment);

            //Show result
            holder.mResultView.setText("SDK has been inited\napikey:"+APIKey+"\n"+"chain:"+chain+"\n"+"environment:"+MirrorWorld.getEnvironment());
        }else if(apiId == DemoAPI.GET_ENVIRONMENT){
            holder.mResultView.setText("Environment is:" + MirrorWorld.getEnvironment());
        }else if(apiId == DemoAPI.SET_JWT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            MirrorSDK.getInstance().SetAccessToken(String.valueOf(holder.mEt1.getText()));
            holder.mResultView.setText("JWT has been set!");
        }else if(apiId == DemoAPI.START_LOGIN){
            //Call API:startLogin
            MirrorWorld.startLogin(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    //todo: Show result
                    holder.mResultView.setText(result);
                }
            });
        }else if(apiId == DemoAPI.IS_LOGGED){
            //Call API:startLogin
            MirrorWorld.isLoggedIn(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    holder.mResultView.setText("Current user login state:"+boolValue);
                }
            });
        }else if(apiId == DemoAPI.GUEST_LOGIN){
            MirrorWorld.guestLogin(new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    holder.mResultView.setText("Guest login success!");
                }

                @Override
                public void onLoginFail() {
                    holder.mResultView.setText("Guest login failed!");
                }
            });
        }else if(apiId == DemoAPI.LOGOUT){
            MirrorWorld.logout(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    holder.mResultView.setText("Logout result:"+boolValue);
                }
            });
        }else if(apiId == DemoAPI.OPEN_WALLET){
            MirrorWorld.openWallet(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    Log.d("MirrorSDK","Wallet logout callback runs!");
                }
            });
        }else if(apiId == DemoAPI.OPEN_MARKET){
            //Choice market url by environment
            MirrorEnv env = MirrorWorld.getEnvironment();
            String marketRoot;
            if(MirrorSDK.getInstance().env == MirrorEnv.StagingMainNet){
                marketRoot = "";//no url yet
            }else if(env == MirrorEnv.StagingDevNet){
                marketRoot = "https://jump-devnet.mirrorworld.fun";
            }else if(env == MirrorEnv.DevNet){
                marketRoot = "";//No url yet
            }else if(env == MirrorEnv.MainNet){
                marketRoot = "https://jump.mirrorworld.fun/";
            }else {
                MirrorSDK.getInstance().logFlow("Unknown env:"+env);
                marketRoot = "https://jump-devnet.mirrorworld.fun";
            }

            //Call API:openMarket
            MirrorWorld.openMarket(marketRoot);
        }else if(apiId == DemoAPI.LOGIN_With_EMAIL){
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
                }
            });
        }else if(apiId == DemoAPI.FETCH_USER){
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
        }else if(apiId == DemoAPI.QUERY_USER){
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
        }else if(apiId == DemoAPI.CREATE_VERIFIED_COLLECTION){
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
        }else if(apiId == DemoAPI.MINT_NFT){
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
        }else if(apiId == DemoAPI.UPDATE_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4) || !checkEt(holder.mEt5) || !checkEt(holder.mEt6)){
                showToast("Please input all params!");
                return;
            }
            String mintAddress = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String updateAuthority = String.valueOf(holder.mEt4.getText());
            String NFTJsonUrl = String.valueOf(holder.mEt5.getText());
            int seller_fee_basis_points = Integer.parseInt(String.valueOf(holder.mEt6.getText()));

            MirrorWorld.updateNFTProperties(mintAddress, name, symbol, updateAuthority,NFTJsonUrl,seller_fee_basis_points, new MintNFTListener() {
                @Override
                public void onMintSuccess(MintResponse userResponse) {
                    MirrorSDK.getInstance().logFlow("Update NFT result:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                    holder.mResultView.setText("Update NFT result is:"+MirrorGsonUtils.getInstance().toJson(userResponse));
                }

                @Override
                public void onMintFailed(long code, String message) {
                    holder.mResultView.setText(message);
                }
            });
        }else if(apiId == DemoAPI.CHECK_STATUS_OFMINTING){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input all params!");
                return;
            }
            String mintAddress1 = String.valueOf(holder.mEt1.getText());
            String mintAddress2 = String.valueOf(holder.mEt2.getText());

            List<String> addresses = new ArrayList<>();
            if(!mintAddress1.isEmpty()) addresses.add(mintAddress1);
            if(!mintAddress2.isEmpty()) addresses.add(mintAddress2);

            MirrorWorld.checkStatusOfMinting(addresses,new CheckStatusOfMintingListener() {

                @Override
                public void onSuccess(CheckStatusOfMintingResponse response) {
                    holder.mResultView.setText("result is:" + MirrorGsonUtils.getInstance().toJson(response));
                }

                @Override
                public void onCheckFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("CHECK_STATUS_OFMINTING",code,message));
                }
            });
        }else if(apiId == DemoAPI.LIST_NFT){
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
            MirrorWorld.listNFT(mint_address, price, new ListNFTListener() {
                @Override
                public void onListSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("ListNFT success! price is:"+listingResponse.price);
                }

                @Override
                public void onListFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("ListNFT",code,message));
                }
            });
        }else if(apiId == DemoAPI.UPDATE_NFT_LISTING){
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
            MirrorWorld.updateNFTListing(mint_address, price, new UpdateListListener() {
                @Override
                public void onUpdateSuccess(ListingResponse listingResponse) {
                    holder.mResultView.setText("UpdateNFTListing success! New price:"+listingResponse.price);
                }

                @Override
                public void onUpdateFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("UpdateNFTListing",code,message));
                }
            });
        }else if(apiId == DemoAPI.CANCEL_NFT_LISTING){
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
        }else if(apiId == DemoAPI.FETCH_NFT_BY_OWNER_ADDRESSES){
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
        }else if(apiId == DemoAPI.FETCH_NFT_BY_CREATOR){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
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
            MirrorWorld.fetchNFTsByCreatorAddresses(owners, limit, offset, new FetchNFTsListener() {
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
        }else if(apiId == DemoAPI.FETCH_NFT_BY_MINT_ADDRESSES){
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
        }else if(apiId == DemoAPI.FETCH_NFT_BY_UPDATE_AUTHORITIES){
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
        }else if(apiId == DemoAPI.FETCH_SINGLE_NFT_DETAILS){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address =String.valueOf(holder.mEt1.getText());
            MirrorWorld.getNFTDetails(mint_address,"temp_id", new FetchSingleNFTListener() {
                @Override
                public void onFetchSuccess(SingleNFTResponse nftObject) {
                    holder.mResultView.setText("NFT details is:"+MirrorGsonUtils.getInstance().toJson(nftObject));
                }

                @Override
                public void onFetchFailed(long code, String message) {

                }
            });
        }else if(apiId == DemoAPI.FETCH_NFT_MARKETPLACE_ACTIVITY){
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
        }else if(apiId == DemoAPI.TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET){
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
        }else if(apiId == DemoAPI.TRANSFER_SPL_TOKEN){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String toPublicKey = String.valueOf(holder.mEt1.getText());
            String amountStr = String.valueOf(holder.mEt2.getText());
            String tokenMint = String.valueOf(holder.mEt3.getText());
            String decimalsStr = String.valueOf(holder.mEt4.getText());

            float amount = 0.0f;
            int decimals = 0;
            try{
                amount =  Float.valueOf(amountStr);
                decimals =  Integer.valueOf(decimalsStr);
            }catch (NumberFormatException E){
            }

            MirrorWorld.transferSPLToken(toPublicKey, amount, tokenMint, decimals, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSFER_ETH){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4) || !checkEt(holder.mEt5)){
                showToast("Please input!");
                return;
            }
            String nonce = String.valueOf(holder.mEt1.getText());
            String gasPrice = String.valueOf(holder.mEt2.getText());
            String gasLimit = String.valueOf(holder.mEt3.getText());
            String to = String.valueOf(holder.mEt4.getText());
            String amountStr = String.valueOf(holder.mEt5.getText());

            int amount = 0;
            try{
                amount =  Integer.valueOf(amountStr);
            }catch (NumberFormatException E){
            }

            MirrorWorld.transferETH(nonce, gasPrice, gasLimit, to, amount, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText(result);
                }
            });
        }
        else if(apiId == DemoAPI.BUY_NFT){
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
        }else if(apiId == DemoAPI.GET_WALLET_TOKEN){
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
        }else if(apiId == DemoAPI.GET_TOKENS_BY_WALLET){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String walletAddress = getStringFromEditText(holder.mEt1);
            MirrorWorld.getTokensByWallet(walletAddress, new GetWalletTokenListener() {
                @Override
                public void onSuccess(GetWalletTokenResponse walletTokenResponse) {
                    holder.mResultView.setText("Get wallet token success!");
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetWalletToken",code,message));
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS){
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
            MirrorWorld.getTransactionsOfLoggedUser(limit, before, new GetWalletTransactionListener() {
                @Override
                public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
                    holder.mResultView.setText("GetTransactions success! count is "+walletTransactionsResponse.count);
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("Transactions",code,message));
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS_BY_WALLET){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int limit = 0;
            try{
                limit = Integer.valueOf(String.valueOf(holder.mEt2.getText()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            String walletAddress = getStringFromEditText(holder.mEt1);
            MirrorWorld.getTransactionsByWallet(walletAddress, limit, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText("GetTransactions success! result is "+result);
                }
            });
        }else if(apiId == DemoAPI.WALLET_TRANSACTIONS_BY_SIGNATURE){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String sig = String.valueOf(holder.mEt1.getText());
            MirrorWorld.getTransactionBySignature(sig, new GetOneWalletTransactionBySigListener() {
                @Override
                public void onSuccess(TransactionsDTO walletTransactions) {
                    holder.mResultView.setText("GetTransactionBySignature success!" + MirrorGsonUtils.getInstance().toJson(walletTransactions));
                }

                @Override
                public void onFailed(long code, String message) {
                    holder.mResultView.setText(MirrorStringUtils.GetFailedNotice("GetTransactionBySignature",code,message));
                }
            });
        }else if(apiId == DemoAPI.CHECK_STATUS_TRANSACTION){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String sig1 = String.valueOf(holder.mEt1.getText());
            String sig2 = String.valueOf(holder.mEt2.getText());
            List<String> signatures = new ArrayList<>();
            if(!sig1.isEmpty()) signatures.add(sig1);
            if(!sig2.isEmpty()) signatures.add(sig2);
            MirrorWorld.checkStatusOfTransactions(signatures, new CheckStatusOfMintingListener() {

                @Override
                public void onSuccess(CheckStatusOfMintingResponse response) {
                    holder.mResultView.setText("checkStatusOfTransactions success!" + MirrorGsonUtils.getInstance().toJson(response));
                }

                @Override
                public void onCheckFailed(long code, String message) {
                    holder.mResultView.setText("checkStatusOfTransactions failed!code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.TRANSFER_SOL){
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
        }
//        else if(apiId == DemoAPI.WALLET_GET_TRANSACTION_OF_TRANSFER_SOL){
//            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
//                showToast("Please input!");
//                return;
//            }
//            String public_key = String.valueOf(holder.mEt1.getText());
//            String amountStr = String.valueOf(holder.mEt2.getText());
//            float amount = 0;
//            try{
//                amount = Integer.valueOf(amountStr);
//            }catch (NumberFormatException e){
//
//            }
//            MirrorWorld.getTransactionOfTransferSOL(public_key, amount, new GetWalletTransactionListener() {
//                @Override
//                public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
//                    holder.mResultView.setText("getTransactionOfTransferSOL success!" + MirrorGsonUtils.getInstance().toJson(walletTransactionsResponse));
//                }
//
//                @Override
//                public void onFailed(long code, String message) {
//                    holder.mResultView.setText("transfer sol failed!code:"+code+" message:"+message);
//                }
//            });
//        }else if(apiId == DemoAPI.WALLET_GET_TRANSACTION_OF_TRANSFER_TOKEN){
//            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
//                showToast("Please input!");
//                return;
//            }
//            String public_key = String.valueOf(holder.mEt1.getText());
//            String amountStr = String.valueOf(holder.mEt2.getText());
//            float amount = 0;
//            try{
//                amount = Integer.valueOf(amountStr);
//            }catch (NumberFormatException e){
//
//            }
//            String tokenMint = getStringFromEditText(holder.mEt3);
//            int decimals = 0;
//            try{
//                decimals = Integer.valueOf(getStringFromEditText(holder.mEt4));
//            }catch (NumberFormatException e){
//
//            }
//            MirrorWorld.getTransactionsOfTransferToken(public_key, amount,tokenMint,decimals, new GetWalletTransactionListener() {
//                @Override
//                public void onSuccess(GetWalletTransactionsResponse walletTransactionsResponse) {
//                    holder.mResultView.setText("getTransactionOfTransferSOL success!" + MirrorGsonUtils.getInstance().toJson(walletTransactionsResponse));
//                }
//
//                @Override
//                public void onFailed(long code, String message) {
//                    holder.mResultView.setText("transfer sol failed!code:"+code+" message:"+message);
//                }
//            });
//        }
        else if(apiId == DemoAPI.GET_COLLECTION_FILTER_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            MirrorWorld.getCollectionFilterInfo(collection, new GetCollectionFilterInfoListener() {
                @Override
                public void onSuccess(GetCollectionFilterInfoRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String mint_address = String.valueOf(holder.mEt1.getText());
            MirrorWorld.getNFTInfoOnSolana(mint_address, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    holder.mResultView.setText("Visiting result:"+MirrorGsonUtils.getInstance().toJson(result));
                }
            });
        }else if(apiId == DemoAPI.GET_COLLECTION_INFO){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MirrorWorld.getCollectionInfo(collections, new GetCollectionInfoListener() {
                @Override
                public void onSuccess(List<GetCollectionInfoRes> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_EVENTS){
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
            MirrorWorld.getNFTEventsOnSolana(mint_address,page,page_size, new GetNFTEventsListener() {
                @Override
                public void onSuccess(GetNFTEventsRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.SEARCH_NFTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            String searchString = String.valueOf(holder.mEt2.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MirrorWorld.searchNFTs(collections,searchString, new SearchNFTsListener() {
                @Override
                public void onSuccess(List<MirrorMarketSearchNFTObj> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.RECOMMEND_SEARCH_NFT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            List<String> collections = new ArrayList<>();
            collections.add(collection);
            MirrorWorld.recommendSearchNFT(collections, new SearchNFTsListener() {
                @Override
                public void onSuccess(List<MirrorMarketSearchNFTObj> result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)
                    || !checkEt(holder.mEt4) || !checkEt(holder.mEt5) || !checkEt(holder.mEt6)){
                showToast("Please input!");
                return;
            }
            String collection = String.valueOf(holder.mEt1.getText());
            int page = 1;
            int page_size = 10;
            String order_by = String.valueOf(holder.mEt4.getText());
            boolean desc = false;
            Double sale = Double.valueOf(1);
            try{
                page = Integer.valueOf(String.valueOf(holder.mEt2.getText()));
                page_size = Integer.valueOf(String.valueOf(holder.mEt3.getText()));
                desc = Boolean.valueOf(String.valueOf(holder.mEt5.getText()));
                sale = Double.valueOf(String.valueOf(holder.mEt6.getText()));
            }catch(Exception e){

            }

//            JSONObject filter = new JSONObject();
//            try {
//                filter.put("filter_name","Rarity");
//                filter.put("filter_type","enum");
//                JSONArray values = new JSONArray();
//                values.put("Common");
//                filter.put("filter_value",values);
//                filter.put("filter_type","enum");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            List<JSONObject> filters = new ArrayList<>();
//            filters.add(filter);
            MirrorWorld.getNFTsByUnabridgedParamsOnSolana(collection,page,page_size,order_by,desc,sale,null, new GetNFTsListener() {
                @Override
                public void onSuccess(GetNFTsRes result) {
                    holder.mResultView.setText("Visiting success:"+MirrorGsonUtils.getInstance().toJson(result));
                }

                @Override
                public void onFail(long code, String message) {
                    holder.mResultView.setText("Visit Failed! code:"+code+" message:"+message);
                }
            });
        }else if(apiId == DemoAPI.GET_NFT_REAL_PRICE){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            int fee = Integer.parseInt(String.valueOf(holder.mEt2.getText()));
            String price = String.valueOf(holder.mEt1.getText());
            MirrorWorld.getNFTRealPrice(price,fee, new GetNFTRealPriceListener() {
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
//        public final TextView mContentView;
        public final Button mButton;
        public final TextView mResultView;
        public final EditText mEt1;
        public final EditText mEt2;
        public final EditText mEt3;
        public final EditText mEt4;
        public final EditText mEt5;
        public final EditText mEt6;
        public ConstraintLayout mSpinner;
        public ConstraintLayout mSpinner2;
        public MultiItemData.MultiItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.multiapi_item_number);
//            mContentView = (TextView) view.findViewById(R.id.multiapi_item_name);
            mButton = (Button) view.findViewById(R.id.multiapi_item_button);
            mResultView = (TextView) view.findViewById(R.id.multiapi_item_result);
            mEt1 = (EditText) view.findViewById(R.id.multiapi_item_paramet1);
            mEt2 = (EditText) view.findViewById(R.id.multiapi_item_paramet2);
            mEt3 = (EditText) view.findViewById(R.id.multiapi_item_paramet3);
            mEt4 = (EditText) view.findViewById(R.id.multiapi_item_paramet4);
            mEt5 = (EditText) view.findViewById(R.id.multiapi_item_paramet5);
            mEt6 = (EditText) view.findViewById(R.id.multiapi_item_paramet6);
            mSpinner = (ConstraintLayout) view.findViewById(R.id.api_item_spinner);
            mSpinner2 = (ConstraintLayout) view.findViewById(R.id.api_item_spinner2);
        }
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }

    private String getStringFromEditText(EditText editText){
        return String.valueOf(editText.getText());
    }

//    private void saveSDKInitParams(String APIKey,MirrorEnv env,MirrorChains chain){
//        SharedPreferences.Editor edt = mContext.getSharedPreferences("initdata",MODE_PRIVATE).edit();
//        edt.putString("apikey",APIKey);
//        edt.putInt("env", Integer.valueOf(String.valueOf(env)));
//        edt.putInt("chain", Integer.parseInt(String.valueOf(chain)));
//        edt.commit();
//    }
//
//    private void getSDKInitParams(){
//        SharedPreferences pref = mContext.getSharedPreferences("initdata",MODE_PRIVATE);
//        String imei = pref.getString("apikey",null);
//        MirrorEnv env = (MirrorEnv) pref.getInt("env",0);
//        boolean isMarried = pref.getString("chain");
//    }
}
