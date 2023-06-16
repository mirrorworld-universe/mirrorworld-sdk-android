package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mirror.mirrorworld_sdk_android.enums.DemoAPI;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.MirrorWorld;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.request.ReqEVMFetchNFTsToken;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.metadata.GetCollectionFilterInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionSummaryRes;
import com.mirror.sdk.response.metadata.GetNFTRealPriceRes;
import com.mirror.sdk.utils.MirrorGsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ClickHandlerSUI extends ClickHandlerBase{

    ClickHandlerSUI(Activity context) {
        super(context);
    }

    public void handleClick(Activity returnActivity, DemoAPI apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder, View view){
        if(apiId == DemoAPI.GET_ENVIRONMENT){
            String r = ("Environment is:" + MirrorWorld.getEnvironment());
            runInUIThread(holder,r);
        }else if(apiId == DemoAPI.SET_JWT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            MirrorSDK.getInstance().SetAccessToken(String.valueOf(holder.mEt1.getText()));
            String r = ("JWT has been set!");
            runInUIThread(holder,r);
        }else if(apiId == DemoAPI.START_LOGIN){
            MirrorWorld.startLogin(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    //todo: Show result
                    String r = (result);
                    runInUIThread(holder,r);
                }
            }, mActivity);
        }else if(apiId == DemoAPI.IS_LOGGED){
            MirrorWorld.isLoggedIn(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    String r = ("Current user login state:"+boolValue);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.GUEST_LOGIN){
            MirrorWorld.guestLogin(new LoginListener() {
                @Override
                public void onLoginSuccess() {
                    String r = ("Guest login success!");
                    runInUIThread(holder,r);
                }

                @Override
                public void onLoginFail() {
                    String r = ("Guest login failed!");
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.LOGOUT){
            MirrorWorld.logout(new BoolListener() {
                @Override
                public void onBool(boolean boolValue) {
                    String r = ("Logout result:"+boolValue);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.OPEN_WALLET){
            MirrorWorld.openWallet(mActivity,new MirrorCallback() {
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
                Toast.makeText(mActivity,"No market on DevNet",Toast.LENGTH_SHORT).show();
                return;
            }else if(env == MirrorEnv.MainNet){
                marketRoot = "https://jump.mirrorworld.fun/";
            }else {
                MirrorSDK.getInstance().logFlow("Unknown env:"+env);
                marketRoot = "https://jump-devnet.mirrorworld.fun";
            }

            //Call API:openMarket
            MirrorWorld.openMarket(marketRoot,mActivity);
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
                    String r = (s);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.FETCH_USER){
            MirrorWorld.fetchUser(new FetchUserListener() {
                @Override
                public void onUserFetched(UserResponse userResponse) {
                    String r = (userResponse.email+" eth_address "+userResponse.wallet.eth_address+" solona_address "+userResponse.wallet.sol_address);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    String r = (message);
                    runInUIThread(holder,r);
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
                    String r = "Result is:" + MirrorGsonUtils.getInstance().toJson(userResponse);
                    runInUIThread(holder,r);
                }

                @Override
                public void onFetchFailed(long code, String message) {
                    String r = (message);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_GET_MINTED_COLLECTIONS){
            MirrorWorld.SUI.Asset.getMintedCollections(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_GET_NFT_ON_COLLECTION){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collectionAddress = String.valueOf(holder.mEt1.getText());
            MirrorWorld.SUI.Asset.getNFTOnCollection(collectionAddress, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_MINT_COLLECTION){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String name = String.valueOf(holder.mEt1.getText());
            String symbol = String.valueOf(holder.mEt2.getText());
            String description = String.valueOf(holder.mEt3.getText());
            MirrorWorld.SUI.Asset.mintCollection(name, symbol, description,null, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_MINT_NFT){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3) || !checkEt(holder.mEt4)){
                showToast("Please input!");
                return;
            }
            String collection_address = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String description = String.valueOf(holder.mEt3.getText());
            String image_url = String.valueOf(holder.mEt4.getText());
            String to_wallet_address = String.valueOf(holder.mEt5.getText());
            MirrorWorld.SUI.Asset.mintNFT(collection_address,name, description, image_url, null,to_wallet_address,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_QUERY_NFT){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection_address = String.valueOf(holder.mEt1.getText());
            MirrorWorld.SUI.Asset.queryNFT(collection_address,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_SEARCH_NFTS_BY_OWNER){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String collection_address = String.valueOf(holder.mEt1.getText());
            MirrorWorld.SUI.Asset.searchNFTsByOwner(collection_address,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_SEARCH_NFTS){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String collection_address1 = String.valueOf(holder.mEt1.getText());
            String collection_address2 = String.valueOf(holder.mEt2.getText());
            List<String> ids = new ArrayList<>();
            if(collection_address1 != "") ids.add(collection_address1);
            if(collection_address2 != "") ids.add(collection_address2);
            MirrorWorld.SUI.Asset.searchNFTs(ids,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_GET_TRANSACTIONS){
            if(!checkEt(holder.mEt1)){
                showToast("Please input!");
                return;
            }
            String digest = String.valueOf(holder.mEt1.getText());
            MirrorWorld.SUI.Wallet.getTransactions(digest,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_TRANSFER_SUI){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2)){
                showToast("Please input!");
                return;
            }
            String to_publickey = String.valueOf(holder.mEt1.getText());
            int amount = Integer.valueOf(holder.mEt2.getText().toString());
            MirrorWorld.SUI.Wallet.transferSUI(to_publickey,amount,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_TRANSFER_TOKEN){
            if(!checkEt(holder.mEt1) || !checkEt(holder.mEt2) || !checkEt(holder.mEt3)){
                showToast("Please input!");
                return;
            }
            String to_publickey = String.valueOf(holder.mEt1.getText());
            int amount = Integer.valueOf(holder.mEt2.getText().toString());
            String token = String.valueOf(holder.mEt3.getText());
            MirrorWorld.SUI.Wallet.transferToken(to_publickey,amount,token,new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }else if(apiId == DemoAPI.SUI_GET_TOKENS){
            MirrorWorld.SUI.Wallet.getTokens(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    String r = ("result is:"+ result);
                    runInUIThread(holder,r);
                }
            });
        }
    }
}
