package com.mirror.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorLoginPageMode;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorUrl;
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
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.models.NFTJsonObject;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.market.NFTObject;
import com.mirror.sdk.response.market.SingleNFTResponse;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.ui.WebViewDialog;
import com.mirror.sdk.ui.market.dialogs.MirrorMarketDialog;
import com.mirror.sdk.ui.market.model.NFTDetailData;
import com.mirror.sdk.ui.sell.SellDialog;
import com.mirror.sdk.utils.MirrorGsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MirrorSDK {
    //user custom
    private boolean debugMode = true;
    private String apiKey = "";

    //secret
    private String refreshToken = "";
    private String accessToken = "";
    private String mWalletAddress = "";

    private String appName = "MirrorWorldMobileSDK";
    private String delegateName = "mwm";
    private WebView webViewPopUp = null;
    private AlertDialog builder = null;
    private Context globalContext = null;
    private Activity mActivity = null;
    public MirrorEnv env = MirrorEnv.MainNet;

    private AlertDialog parentDialog = null;
    private WebView mLoginWebView = null;
    private String userAgent = null;

    private String localFileKey = "mirror_local_storage";
    private String localKeyRefreshToken = "mirror_refresh_token";
    private String localKeyAppId = "mirror_app_id";
    private String localKeyWalletAddress = "mirror_wallet_address";

    //logic
    private MirrorLoginPageMode loginPageMode = MirrorLoginPageMode.CloseIfLoginDone;
    private LoginListener cbLogin = null;
    private MirrorCallback cbStringLogin = null;


    private MirrorSDK(){

    }

    private static volatile MirrorSDK instance;

    public static MirrorSDK getInstance(){
        if (instance == null){
            synchronized(MirrorSDK.class){
                instance = new MirrorSDK();
            }
        }
        return instance;
    }

    public void InitSDK(Activity activityContext,MirrorEnv env){
        logFlow("Mirror SDK inited!");
        this.mActivity = activityContext;
        if(this.mActivity != null){
            this.refreshToken = getRefreshToken(this.mActivity);
        }
        this.env = env;
    }

    public String GetRefreshTokenFromResponse(String response){
        String refreshToken = null;
        try {

            JSONObject jsonObject = new JSONObject(response);
            refreshToken  = jsonObject.getJSONObject("data").getString("refresh_token");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(null == refreshToken ){
            return "error";
        }

        return refreshToken;
    }

    public String GetAccessTokenFromResponse(String response){
        String accessToken = null;
        try {

            JSONObject jsonObject = new JSONObject(response);
            accessToken = jsonObject.getJSONObject("data").getString("access_token");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(null == accessToken){
            return "error";
        }

        return accessToken;
    }

    public void SetRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void SetAccessToken(String accessToken){
        this.accessToken = accessToken;

    }

    public void StartLogin(){
//        if(apiKey == ""){
//            apiKey = getSavedString(mActivity,localKeyAppId);
//            logFlow("No apiKey use locally api key:"+ apiKey);
//        }
//        if(apiKey == ""){
//            logFlow("Must set app id first!");
//            return;
//        }
//        logFlow("Start login called.");
//        SDKFragmentDialog ddd = new SDKFragmentDialog();
//        ddd.SetParams(apiKey, mActivity,appName, mActivity.getApplicationContext());
//        ddd.show(mActivity.getFragmentManager(),"Tag");

        if(apiKey == ""){
            apiKey = getSavedString(mActivity,localKeyAppId);
            logFlow("No apiKey use locally api key:"+ apiKey);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            return;
        }
        logFlow("Start login called.");
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        AlertDialog dialog = builder.create();

        parentDialog = dialog;
        dialog.setCanceledOnTouchOutside(false);
        WebView wv = new WebView(mActivity);
        setWebView(mActivity,wv);
        dialog.setButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                wv.destroy();
                dialog.dismiss();
            }
        });

        RelativeLayout layout = getPopupWindowLayout(mActivity);
        layout.addView(wv);
        dialog.setView(layout);

        dialog.show();
        loginPageMode = MirrorLoginPageMode.CloseIfLoginDone;
//        final String finalUrl = MirrorUrl.URL_AUTH + apiKey;
//        LoginDialog dialog = new LoginDialog(mActivity, finalUrl);
//        dialog.SetParams(mActivity);
//        dialog.show();
    }

    public void StartLogin(LoginListener loginListener){
        StartLogin();
        cbLogin = loginListener;
    }

    public void StartLogin(MirrorCallback listener){
        StartLogin();
        cbStringLogin = listener;
    }

    public void SetDebug(boolean debug){
        debugMode = debug;
    }

    public void SetApiKey(String id){
        apiKey = id;
        if(mActivity != null){
            saveString(localKeyAppId, apiKey);
        }
    }

    private String GetSSORoot(){
        if(env == MirrorEnv.StagingMainNet){
            return "https://api-staging.mirrorworld.fun/v1/";
        }else if(env == MirrorEnv.StagingDevNet){
            return "https://api-staging.mirrorworld.fun/v1/";
        }else if(env == MirrorEnv.DevNet){
            return "https://api.mirrorworld.fun/v1/";
        }else if(env == MirrorEnv.MainNet){
            return "https://api.mirrorworld.fun/v1/";
        }else {
            logFlow("Unknown env:"+env);
            return "https://api-staging.mirrorworld.fun/v1/";
        }
    }

    private String GetAPIRoot(){
        if(env == MirrorEnv.StagingMainNet){
            return "https://api-staging.mirrorworld.fun/v1/mainnet/";
        }else if(env == MirrorEnv.StagingDevNet){
            return "https://api-staging.mirrorworld.fun/v1/devnet/";
        }else if(env == MirrorEnv.DevNet){
            return "https://api.mirrorworld.fun/v1/devnet/";
        }else if(env == MirrorEnv.MainNet){
            return "https://api.mirrorworld.fun/v1/mainnet/";
        }else {
            logFlow("Unknown env:"+env);
            return "https://api-staging.mirrorworld.fun/v1/devnet/";
        }
    }

    public void CheckAuthenticated(BoolListener listener){
        FetchUser(new FetchUserListener() {
            @Override
            public void onUserFetched(UserResponse userResponse) {
                listener.onBool(true);
            }

            @Override
            public void onFetchFailed(long code, String message) {
                listener.onBool(false);
            }
        });
    }

    public void LoginWithEmail(String userEmail,String password,MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", userEmail);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetSSORoot() + MirrorUrl.URL_LOGIN_WITH_EMAIL;
        LoginWithEmailPostRequest(url,data,mirrorCallback);
    }

    public void FetchUser(FetchUserListener fetchUserListener){
        Map<String,String> map = new HashMap<>();

        String url = GetSSORoot() + MirrorUrl.URL_IS_AUTHENTICATED;
        checkParamsAndGet(url, map, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<UserResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<UserResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchUserListener.onUserFetched(response.data);
                }else{
                    fetchUserListener.onFetchFailed(response.code,response.message);
                }
            }
        });
    }

    public void QueryUser(String userEmail, FetchUserListener fetchUserListener){
        Map<String,String> map = new HashMap<>();
        map.put("email",userEmail);

        String url = GetSSORoot() + MirrorUrl.URL_QUERY_USER;
        checkParamsAndGet(url, map, new MirrorCallback() {
            @Override
            public void callback(String result) {

                CommonResponse<UserResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<UserResponse>>(){}.getType());

                if(response.code == MirrorResCode.SUCCESS){

                    fetchUserListener.onUserFetched(response.data);
                }else{
                    fetchUserListener.onFetchFailed(response.code,response.message);
                }
            }
        });
    }

    public void GetNFTDetails(String mint_address, FetchSingleNFTListener fetchSingleNFT){
        String url = GetAPIRoot() + MirrorUrl.URL_QUERY_NFT_DETAIL + mint_address;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<SingleNFTResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<SingleNFTResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchSingleNFT.onFetchSuccess(response.data);
                }else{
                    fetchSingleNFT.onFetchFailed(response.code,response.message);
                }
            }
        });
    }
    public void MintNFT(String collection_mint, String name, String symbol, String detailUrl,String confirmation, MintNFTListener mintNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_mint", collection_mint);
            jsonObject.put("name", name);
            jsonObject.put("symbol", symbol);
            jsonObject.put("url", detailUrl);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_MINT_NFT_COLLECTION;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {

                CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    mintNFTListener.onMintSuccess(response.data);
                }else{
                    mintNFTListener.onMintFailed(response.code,response.message);
                }

            }
        }));
    }

    public void MintNFT(String collection_mint, String name, String symbol, String detailUrl, MintNFTListener mintNFTListener){
        MintNFT(collection_mint,name,symbol,detailUrl,MirrorConfirmation.Default,mintNFTListener);
    }

    public void CreateVerifiedCollection(String name, String symbol, String detailUrl,String confirmation, CreateTopCollectionListener createTopCollectionListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("symbol", symbol);
            jsonObject.put("url", detailUrl);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_MINT_TOP_LEVEL_COLLECTION;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    createTopCollectionListener.onCreateSuccess(response.data);
                }else{
                    createTopCollectionListener.onCreateFailed(response.code,response.message);
                }
            }
        }));
    }
    public void CreateVerifiedCollection(String name, String symbol, String detailUrl, CreateTopCollectionListener createTopCollectionListener){
        CreateVerifiedCollection(name,symbol,detailUrl,MirrorConfirmation.Default,createTopCollectionListener);
    }

    public void CreateVerifiedSubCollection(String collection_mint, String name, String symbol, String detailUrl,String confirmation, CreateSubCollectionListener createSubCollectionListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("collection_mint", collection_mint);
            jsonObject.put("symbol", symbol);
            jsonObject.put("url", detailUrl);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_MINT_LOWER_LEVEL_COLLECTION;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {

                CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    createSubCollectionListener.onCreateSuccess(response.data);
                }else{
                    createSubCollectionListener.onCreateFailed(response.code,response.message);
                }

            }
        }));

    }
    public void CreateVerifiedSubCollection(String collection_mint, String name, String symbol, String detailUrl, CreateSubCollectionListener createSubCollectionListener){
        CreateVerifiedSubCollection(collection_mint,name,symbol,detailUrl, MirrorConfirmation.Default,createSubCollectionListener);
    }

    public void TransferNFTToAnotherSolanaWallet(String mint_address, String to_wallet_address, TransferNFTListener transferNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("to_wallet_address", to_wallet_address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {

                CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    transferNFTListener.onTransferSuccess(response.data);
                }else{
                    transferNFTListener.onTransferFailed(response.code,response.message);
                }

            }
        }));
    }

    public void FetchNFTsByOwnerAddresses(List<String> owners, int limit, int offset, FetchByOwnerListener fetchByOwnerListener){

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : owners) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("owners", jsonArray);
            jsonObject.put("limit", limit);
            jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_FETCH_MULTIPLE_NFT;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {

                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchByOwnerListener.onFetchSuccess(response.data);
                }else{
                    fetchByOwnerListener.onFetchFailed(response.code,response.message);
                }

            }
        }));
    }

    public void FetchNFTMarketplaceActivity(String mint_address, FetchSingleNFTActivityListener fetchSingleNFTActivityListener){
        String url = GetAPIRoot() + MirrorUrl.URL_FETCH_ACTIVITY + mint_address;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {

                CommonResponse<ActivityOfSingleNftResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ActivityOfSingleNftResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchSingleNFTActivityListener.onFetchSuccess(response.data);
                }else{
                    fetchSingleNFTActivityListener.onFetchFailed(response.code,response.message);
                }

            }
        });
    }

    public void CancelNFTListing(String mint_address, Double price,String confirmation, CancelListListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
            jsonObject.put("confirmation",confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_CANCEL_LISTING_OF_NFT_ON_THE_MARKETPLACE;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onCancelSuccess(response.data);
                }else{
                    listener.onCancelFailed(response.code,response.message);
                }
            }
        }));
    }

    public void CancelNFTListing(String mint_address, Double price, CancelListListener listener){
        CancelNFTListing(mint_address, price,MirrorConfirmation.Default, listener);
    }

    public void UpdateNFTListing(String mint_address, Double price,String confirmation, UpdateListListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
            jsonObject.put("confirmation","finalized");
            jsonObject.put("confirmation",confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String  data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_UPDATE_LISTING_OF_NFT_ON_THE_MARKETPLACE;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onUpdateSuccess(response.data);
                }else{
                    listener.onUpdateFailed(response.code,response.message);
                }
            }
        }));
    }

    public void UpdateNFTListing(String mint_address, Double price, UpdateListListener listener){
        UpdateNFTListing(mint_address, price,MirrorConfirmation.Default, listener);
    }

    public void ListNFT(String mint_address, Double price, String confirmation,ListNFTListener listener){
        ListNFT(mint_address, price, confirmation,"", listener);
    }
    public void ListNFT(String mint_address, Double price, String confirmation,String auction_house, ListNFTListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
            jsonObject.put("confirmation",confirmation);
            if(auction_house != null && auction_house != "") jsonObject.put("auction_house",auction_house);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_LIST_NFT_ON_THE_MARKETPLACE;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onListSuccess(response.data);
                }else{
                    listener.onListFailed(response.code,response.message);
                }
            }
        }));
    }

    public void ListNFT(String mint_address, Double price, ListNFTListener listener){
        ListNFT(mint_address, price,MirrorConfirmation.Default, listener);
    }

    public void FetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : mint_addresses) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("mint_addresses", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_MINT_ADDRESS;
        checkParamsAndPost(url, data, getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    fetchByMintAddressListener.onFetchSuccess(response.data);
                }else{
                    fetchByMintAddressListener.onFetchFailed(response.code,response.message);
                }

            }
        }));
    }

    public void BuyNFT(String mint_address, Double price, BuyNFTListener buyNFTListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_BUY_NFT_ON_THE_MARKETPLACE;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {

                CommonResponse<ListingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<ListingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    buyNFTListener.onBuySuccess(response.data);
                }else{
                    buyNFTListener.onBuyFailed(response.code,response.message);
                }

            }
        }));
    }

    //Wallet
    public void OpenWallet(){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity,localKeyAppId);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            return;
        }

        if(refreshToken == ""){
            this.refreshToken = getRefreshToken(this.mActivity);
        }

        if(refreshToken == ""){
            logFlow("Please login first!");
            return;
        }

        if(accessToken == ""){
            logFlow("No access token,start get flow");
            GetAccessToken(mActivity, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    doOpenWallet();
                }
            });
        }else {
            doOpenWallet();
        }
    }

    private void doOpenWallet(){
        String url = MirrorUrl.URL_AUTH + apiKey;
        WebViewDialog dialog = new WebViewDialog(mActivity,url);
        dialog.SetParams(mActivity);
        dialog.show();

        loginPageMode = MirrorLoginPageMode.KeepIfLoginDone;
    }

    public void OpenMarket(){
        MirrorMarketDialog dialogAddGroup = new MirrorMarketDialog();
        dialogAddGroup.Init(mActivity);
        dialogAddGroup.show(mActivity.getFragmentManager(), "Add group dialog");
    }

    public void OpenSellPage(String mintAddress,NFTJsonObject nftObject){
        SellDialog dialog = new SellDialog();
        NFTDetailData uiData = new NFTDetailData();
        uiData.image = nftObject.image;
        uiData.name = nftObject.name;
        uiData.mint_address = mintAddress;
        uiData.price = 0.0;
        dialog.init(mActivity,uiData);
    }

    public void GetWallet(MirrorCallback mirrorCallback){
        if(mWalletAddress == ""){
            mWalletAddress = getSavedString(mActivity,localKeyWalletAddress);
        }
        String url = GetSSORoot() + MirrorUrl.URL_ME;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {
                String address = "";
                try {
                    JSONObject obj = new JSONObject(result);
                    address = obj.getJSONObject("data").getJSONObject("user").getJSONObject("wallet").getString("sol_address");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mirrorCallback.callback(address);
            }
        });
        return;
    }

    public void TransferSOL(String toPublickey, int amount, TransferSOLListener transferSOLListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", toPublickey);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_TRANSFER_SQL;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<TransferResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<TransferResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    transferSOLListener.onTransferSuccess(response.data);
                }else{
                    transferSOLListener.onTransferFailed(response.code,response.message);
                }
            }
        }));
    }

    public void GetWalletTokens(GetWalletTokenListener walletTokenListener){
        String url = GetAPIRoot() + MirrorUrl.URL_GET_WALLET_TOKEN;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetWalletTokenResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTokenResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    walletTokenListener.onSuccess(response.data);
                }else{
                    walletTokenListener.onFailed(response.code,response.message);
                }
            }
        });
    }

    public void Transactions(String limit, String before, GetWalletTransactionListener walletTransactionListener){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("limit",limit);
        map.put("before",before);

        String url = GetAPIRoot() + MirrorUrl.URL_GET_WALLET_TRANSACTIONS;
        checkParamsAndGet(url, map, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetWalletTransactionsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTransactionsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    walletTransactionListener.onSuccess(response.data);
                }else{
                    walletTransactionListener.onFailed(response.code,response.message);
                }
            }
        });
    }

    public void GetTransactionBySignature(String signature, GetWalletTransactionBySigListener listener){
        String url = GetAPIRoot() + MirrorUrl.URL_GET_WALLET_TRANSACTIONS + "/"+signature;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetWalletTransactionsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTransactionsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data.transactions);
                }else{
                    listener.onFailed(response.code,response.message);
                }
            }
        });
    }


    // not implement method(server)
    public void FetchNFTsByUpdateAuthorities(List<String> update_authorities, Double limit, Double offset, FetchNFTsListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : update_authorities) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("update_authorities", jsonArray);
            jsonObject.put("limit", limit);
            jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_UPDATE_AUTHORITY_ADDRESS;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onFetchSuccess(response.data);
                }else{
                    listener.onFetchFailed(response.code,response.message);
                }
            }
        }));
    }

    public void FetchNFTsByCreatorAddresses(List<String> creators, Double limit, Double offset, FetchNFTsListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : creators) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("creators", jsonArray);
            jsonObject.put("limit", limit);
            jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String  data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_CREATOR_ADDRESS;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onFetchSuccess(response.data);
                }else{
                    listener.onFetchFailed(response.code,response.message);
                }
            }
        }));
    }

    private void BuyNFT(String mint_address, Double price, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_BUY_NFT_ON_THE_MARKETPLACE;
        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
    }

    private void TransferToken(String toPublickey, int amount, String token_mint, int decimals, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", toPublickey);
            jsonObject.put("amount", amount);
            jsonObject.put("token_mint", token_mint);
            jsonObject.put("decimals", decimals);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_TRANSFER_TOKEN;
        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
    }

    private void CreateNewMarketPlace(String treasury_withdrawal_destination, String fee_withdrawal_destination, String treasury_mint, double seller_fee_basis_points, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("treasury_withdrawal_destination", treasury_withdrawal_destination);
            jsonObject.put("fee_withdrawal_destination", fee_withdrawal_destination);
            jsonObject.put("treasury_mint", treasury_mint);
            jsonObject.put("seller_fee_basis_points", seller_fee_basis_points);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_CREATE_NEW_MARKET_PLACE;
        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
    }

    private void UpdateMarketPlace(String new_authority, String treasury_mint, String treasury_withdrawal_destination, String fee_withdrawal_destination, double seller_fee_basis_points,MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("new_authority", new_authority);
            jsonObject.put("treasury_mint",treasury_mint);
            jsonObject.put("treasury_withdrawal_destination", treasury_withdrawal_destination);
            jsonObject.put("fee_withdrawal_destination", fee_withdrawal_destination);
            jsonObject.put("seller_fee_basis_points", seller_fee_basis_points);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_UPDATE_MARKET_PLACE;
        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
    }

    private void QueryMarketPlace(String name, String client_id,
                                 String authority,String treasury_mint,
                                 String auction_house_fee_account,String auction_house_treasury,
                                 String treasury_withdrawal_destination,String fee_withdrawal_destination,
                                 String seller_fee_basis_points,String requires_sign_off,String can_change_sale_price,
                                 MirrorCallback mirrorCallback){

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name",name);
        map.put("client_id",client_id);
        map.put("authority",authority);
        map.put("treasury_mint",treasury_mint);
        map.put("auction_house_fee_account",auction_house_fee_account);
        map.put("auction_house_treasury",auction_house_treasury);
        map.put("treasury_withdrawal_destination",treasury_withdrawal_destination);
        map.put("fee_withdrawal_destination",fee_withdrawal_destination);
        map.put("seller_fee_basis_points",seller_fee_basis_points);
        map.put(" requires_sign_off", requires_sign_off);
        map.put("can_change_sale_price",can_change_sale_price);

        String url = GetAPIRoot() + MirrorUrl.URL_QUERY_MARKET_PLACE;
        checkParamsAndGet(url,map, mirrorCallback);

    }

    private void LoginWithEmailPostRequest(String url, String data, MirrorCallback mirrorCallback){
        doPostRequest(url,data, mirrorCallback);
    }


    public void doPostRequest(String url, String data, MirrorCallback mirrorCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {

                post(url, data,new MirrorCallback(){
                    @Override
                    public void callback(String result) {
                        if(mActivity == null){
                            if(mirrorCallback != null) mirrorCallback.callback(result);
                        }else{
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(mirrorCallback != null) mirrorCallback.callback(result);
                                }
                            });
                        }


                    }
                });
            }
        }).start();
    }


    public void GetAccessToken(Activity activityContext, MirrorCallback mirrorCallback){
        logFlow("ready to get access token,now refreshToken is:"+refreshToken);
        if(refreshToken == ""){
            StartLogin();
            return;
        }

        if(apiKey == ""){
            apiKey = getSavedString(activityContext,localKeyAppId);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            return;
        }

        String url = GetSSORoot() + MirrorUrl.URL_REFRESH_TOKEN;
        new Thread(httpGetRunnableWithRefreshToken(url,refreshToken, new MirrorCallback() {
            @Override
            public void callback(String result) {

                if(activityContext == null){
                    JSONObject itJson = null;
                    try {
                        itJson = new JSONObject(result);
                        int code = (int) itJson.get("code");
                        if (code != 0){
                            logFlow("You have no authorization to visit api,now popup login window."+result);
                            StartLogin();
                        }else{
                            String accessToken = itJson.getJSONObject("data").getString("access_token");
                            String newRefreshToken = itJson.getJSONObject("data").getString("refresh_token");
                            String wallet = itJson.getJSONObject("data").getJSONObject("user").getString("sol_address");
                            saveRefreshToken(newRefreshToken);
                            saveString(localKeyWalletAddress,wallet);
                            accessToken = accessToken;
                            mWalletAddress = wallet;
                            logFlow("Access token success!"+accessToken);
                            logFlow("Wallet is:"+wallet);
                            if(mirrorCallback != null){
                                mirrorCallback.callback(accessToken);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    activityContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject itJson = null;
                            try {
                                itJson = new JSONObject(result);
                                int code = (int) itJson.get("code");
                                if (code != 0){
                                    logFlow("You have no authorization to visit api,now popup login window."+result);
                                    StartLogin();
                                }else{
                                    String accessToken = itJson.getJSONObject("data").getString("access_token");
                                    String newRefreshToken = itJson.getJSONObject("data").getString("refresh_token");
                                    String wallet = itJson.getJSONObject("data").getJSONObject("user").getString("sol_address");
                                    saveRefreshToken(newRefreshToken);
                                    saveString(localKeyWalletAddress,wallet);
                                    accessToken = accessToken;
                                    mWalletAddress = wallet;
                                    logFlow("Access token success!"+accessToken);
                                    logFlow("Wallet is:"+wallet);
                                    if(mirrorCallback != null){
                                        mirrorCallback.callback(accessToken);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        })).start();
    }

    public void doGet(String url, Map<String, String> params, MirrorCallback mirrorCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String resultStr = httpGetW(url,params,"UTF-8");
                if(mActivity == null){
                    mirrorCallback.callback(resultStr);
                }else{
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logFlow("Get Result is:"+resultStr);
                            mirrorCallback.callback(resultStr);
                        }
                    });
                }
            }
        }).start();
    }

    private void post(String url, String data, MirrorCallback mirrorCallback) {
        logFlow("post json:"+data);

        try {
            //HttpURLConnection
            //1.实例化一个URL对象
            URL urll = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("x-api-key", apiKey);
            conn.setRequestProperty("Authorization","Bearer "+accessToken);




            OutputStream out = conn.getOutputStream();
            //写数据
            if(null != data){
                out.write(data.getBytes(Charset.forName("UTF-8")));
            }

            //4.获取响应码
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != 201) {
                InputStream ip= conn.getErrorStream();
                InputStream inputStream = new BufferedInputStream(ip);

                StringBuilder textBuilder = new StringBuilder();
                try (Reader reader = new BufferedReader(new InputStreamReader
                        (inputStream, Charset.forName("UTF-8")))) {
                    int c = 0;
                    while ((c = reader.read()) != -1) {
                        textBuilder.append((char) c);
                    }
                }

                logFlow(String.valueOf(textBuilder));
                if(mirrorCallback != null) mirrorCallback.callback(String.valueOf(textBuilder.toString()));

            }else{

                InputStream inputStream = new BufferedInputStream(conn.getInputStream());

                StringBuilder textBuilder = new StringBuilder();
                try (Reader reader = new BufferedReader(new InputStreamReader
                        (inputStream, Charset.forName("UTF-8")))) {
                    int c = 0;
                    while ((c = reader.read()) != -1) {
                        textBuilder.append((char) c);
                    }
                }

                logFlow(String.valueOf(conn.getResponseCode()));
                if(mirrorCallback != null) mirrorCallback.callback(String.valueOf(textBuilder.toString()));
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MirrorCallback getHandlerCallback(MirrorCallback mirrorCallback){
        return new MirrorCallback() {
            @Override
            public void callback(String result) {
                
                if(mActivity == null){
                    if(mirrorCallback != null) mirrorCallback.callback(result);
                }else{
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mirrorCallback != null) mirrorCallback.callback(result);
                        }
                    });
                }
            }
        };
    }

    private void checkParamsAndGet(String url,  Map<String,String> params, MirrorCallback mirrorCallback){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity,localKeyAppId);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            return;
        }

        if(accessToken == ""){
            logFlow("No access token,start get flow");
            GetAccessToken(mActivity, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    doGet(url,params, mirrorCallback);
                }
            });
        }else{
            if(mWalletAddress == "" && null != mActivity){
                mWalletAddress = getSavedString(mActivity,localKeyWalletAddress);
                if(mWalletAddress == ""){
                    logFlow("Must get mWalletAddress first!");
                }
            }
            doGet(url,params, mirrorCallback);
        }
    }

    private void checkParamsAndPost(String url, String data, MirrorCallback mirrorCallback){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity,localKeyAppId);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            return;
        }

        if(accessToken == ""){
            logFlow("No access token,start get flow");
            GetAccessToken(mActivity, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    doPost(url,data, mirrorCallback);
                }
            });
        }else{
            if(mWalletAddress == ""&& null != mActivity){
                mWalletAddress = getSavedString(mActivity,localKeyWalletAddress);
                if(mWalletAddress == ""){
                    logFlow("Must get mWalletAddress first!");
                }
            }
            doPost(url,data, mirrorCallback);
        }
    }

    private void doPost(String url, String data, MirrorCallback mirrorCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                post(url, data,new MirrorCallback(){
                    @Override
                    public void callback(String result) {
                        if(mActivity == null){
                            if(mirrorCallback != null) mirrorCallback.callback(result);
                        }else{
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(mirrorCallback != null) mirrorCallback.callback(result);
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    private Runnable httpGetRunnableWithRefreshToken(String urlRefreshToken, String accessToken, MirrorCallback mirrorCallback) {
        return new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {

                   logFlow("appId"+ apiKey);
                    url = new URL(urlRefreshToken);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("x-refresh-token",accessToken);
                    urlConnection.setRequestProperty("x-api-key", apiKey);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    int status = urlConnection.getResponseCode();
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    StringBuilder textBuilder = new StringBuilder();
                    try (Reader reader = new BufferedReader(new InputStreamReader
                            (inputStream, Charset.forName("UTF-8")))) {
                        int c = 0;
                        while ((c = reader.read()) != -1) {
                            textBuilder.append((char) c);
                        }
                    }
                    urlConnection.disconnect();
                    mirrorCallback.callback(textBuilder.toString());
                } catch (MalformedURLException | ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Error err) {
                    mirrorCallback.callback("Network Error");
                }
            }
        };
    }

    private String httpGetW(String strUrlPath,Map<String, String> params,String encode) {
        String result = null;
        if(params != null){
            String append_url = getRequestData(params, encode).toString();
            strUrlPath = strUrlPath + "?" + append_url;
        }

        logFlow("http get:"+strUrlPath);
        logFlow("http get(x-api-key):"+ apiKey);
        logFlow("http get Authorization:"+accessToken);
        try {
            URL url = new URL(strUrlPath);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(5000);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Accept","application/json");
            urlConn.setRequestProperty("x-api-key", apiKey);
            urlConn.setRequestProperty("Authorization","Bearer "+accessToken);
            urlConn.setRequestMethod("GET");

            InputStreamReader in = new InputStreamReader(urlConn.getInputStream());

            BufferedReader buffer = new BufferedReader(in);
            String inputLine = null;

            result = "";

            while((inputLine = buffer.readLine())!=null){
                result += inputLine + "\n";
            }

            in.close();
            urlConn.disconnect();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private StringBuffer getRequestData(Map<String, String> params,String encode) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                stringBuffer.append(key)
                        .append("=")
                        .append(value)
//                    .append(URLEncoder.encode(value, encode))
                        .append("&");
            }


            if(params != null && params.size()!=0){
                stringBuffer.deleteCharAt(stringBuffer.length()-1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer;
    }

    private void setWebView(Context context,WebView webView){
        this.mLoginWebView = webView;
        webView.setWebViewClient(new WebViewClient());
        final String finalUrl = MirrorUrl.URL_AUTH + apiKey;
        logFlow("open login page with url:"+finalUrl);
        webView.loadUrl(finalUrl);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //set autofit
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // Set User Agent
        userAgent = System.getProperty("http.agent");
        webSettings.setUserAgentString(userAgent + appName);

        // Enable Cookies
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) CookieManager.getInstance()
                .setAcceptThirdPartyCookies(webView, true);

        // Handle Popups
        webView.setWebChromeClient(new MirrorChromeClient());
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        globalContext = context.getApplicationContext();

        // WebView Tweaks
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小

        webView.addJavascriptInterface(this, delegateName);
    }

    @JavascriptInterface
    public void setLoginResponse(String dataJsonStr) {
        logFlow("receive login response:"+dataJsonStr);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(dataJsonStr);
            accessToken = jsonObject.getString("access_token");
            String token = jsonObject.getString("refresh_token");
            saveRefreshToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        LoginResponse loginResponse = MirrorGsonUtils.getInstance().fromJson(dataJsonStr,LoginResponse.class);

        if(loginPageMode == MirrorLoginPageMode.CloseIfLoginDone){
            parentDialog.dismiss();
        }else if(loginPageMode == MirrorLoginPageMode.KeepIfLoginDone){
            //do nothing
        }else {
            logFlow("Unknown login page mode:"+loginPageMode);
        }

        if(cbLogin != null){
            logFlow("login success and LoginListener callback called.");
            cbLogin.onLoginSuccess();
        }
        if(cbStringLogin != null){
            logFlow("login success and MirrorCallback callback called.");
            cbStringLogin.callback(dataJsonStr);
        }
    }

    @JavascriptInterface
    public void closePage() {
        parentDialog.dismiss();
    }

    private void saveRefreshToken(String refreshToken){
        logFlow("save refresh token to local:"+refreshToken);
        this.refreshToken = refreshToken;
        SharedPreferences sp = mActivity.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(localKeyRefreshToken, refreshToken);
        //editor.apply()
        editor.commit();
    }

    private void saveString(String key,String value){
        SharedPreferences sp = mActivity.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        //editor.apply()
        editor.commit();
    }


    private String getSavedString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
        String value = sp.getString(key,"");
        return value;
    }

    private String getRefreshToken(Context context){
        SharedPreferences sp = context.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
        String refreshToken = sp.getString(localKeyRefreshToken,"");
        return refreshToken;
    }

    private RelativeLayout getPopupWindowLayout(Context context) {
        RelativeLayout relative = new RelativeLayout(context);
        addProgressBar(context,relative);
        return relative;
    }

    private void addProgressBar(Context context,RelativeLayout view ){
        ProgressBar bar = new ProgressBar(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        view.addView(bar, lp);
    }

    private void logFlow(String value){
        if(debugMode){
            Log.d("MirrorSDK",value);
        }
    }

    class MirrorChromeClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            webViewPopUp = new WebView(globalContext);
            WebSettings webViewPopupSetting = webViewPopUp.getSettings();
            webViewPopUp.setVerticalScrollBarEnabled(false);
            webViewPopUp.setHorizontalScrollBarEnabled(false);
            webViewPopUp.setWebChromeClient(new MirrorChromeClient());
            webViewPopUp.getSettings().setJavaScriptEnabled(true);
            webViewPopUp.getSettings().setSaveFormData(true);
            webViewPopUp.getSettings().setEnableSmoothTransition(true);
            webViewPopUp.getSettings().setUserAgentString(userAgent + "yourAppName");

            //To resolve the exceed on Android
            if (Build.VERSION.SDK_INT >= 19) {
                webViewPopUp.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                webViewPopUp.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            webViewPopupSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webViewPopupSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

            // pop the  webview with alert dialog
            builder = new AlertDialog.Builder(mActivity).create();
            builder.setTitle("");

//            Display display = mActivity.getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
//            webViewPopUp.setLayoutParams(new ViewGroup.LayoutParams((int) (display.getHeight() * 0.8),(int) (display.getHeight() * 0.8)));

//            RelativeLayout layout = getPopupWindowLayout(mActivity);
//            layout.addView(webViewPopUp);

            builder.setView(webViewPopUp);

            builder.setButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    webViewPopUp.destroy();
                    dialog.dismiss();
                }
            });

            builder.show();
            builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            if(Build.VERSION.SDK_INT >= 21) {
                cookieManager.setAcceptThirdPartyCookies(webViewPopUp, true);
                cookieManager.setAcceptThirdPartyCookies(mLoginWebView, true);
            }

            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(webViewPopUp);
            resultMsg.sendToTarget();

            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
            //Toast.makeText(contextPop,"onCloseWindow called",Toast.LENGTH_SHORT).show();
            try {
                webViewPopUp.destroy();
            } catch (Exception e) {
                Log.d("Destroyed with Error ", e.getStackTrace().toString());
            }

            try {
                builder.dismiss();
            } catch (Exception e) {
                Log.d("Dismissed with Error: ", e.getStackTrace().toString());
            }

        }
    }



}