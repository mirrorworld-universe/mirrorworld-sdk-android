package com.mirror.sdk;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;

import com.google.gson.reflect.TypeToken;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorConstant;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorLoginPageMode;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorUrl;
import com.mirror.sdk.listener.universal.MSimpleCallback;
import com.mirror.sdk.listener.universal.OnCheckSDKUseable;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
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
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.listener.wallet.GetOneWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTokenListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionBySigListener;
import com.mirror.sdk.listener.wallet.GetWalletTransactionListener;
import com.mirror.sdk.listener.wallet.TransactionsDTO;
import com.mirror.sdk.listener.wallet.TransferSOLListener;
import com.mirror.sdk.response.CommonResponse;
import com.mirror.sdk.response.auth.LoginResponse;
import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.ActivityOfSingleNftResponse;
import com.mirror.sdk.response.market.ListingResponse;
import com.mirror.sdk.response.market.MintResponse;
import com.mirror.sdk.response.market.MultipleNFTsResponse;
import com.mirror.sdk.response.market.SingleNFTResponse;
import com.mirror.sdk.response.wallet.GetWalletTokenResponse;
import com.mirror.sdk.response.wallet.GetWalletTransactionsResponse;
import com.mirror.sdk.response.wallet.TransferResponse;
import com.mirror.sdk.ui.MainWebView;
import com.mirror.sdk.ui.MirrorDialog;
import com.mirror.sdk.utils.MirrorGsonUtils;
import com.mirror.sdk.utils.MirrorThreadUtils;
import com.mirror.sdk.utils.MirrorWebviewUtils;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MirrorSDK {
    //user custom
    private boolean debugMode = true;
    private String apiKey = "";
    private String mWebviewNotice = MirrorConstant.DefaultWebviewNotice;

    //secret
    private String refreshToken = "";
    private String accessToken = "";
    private String xAuthToken = "";
    private String mWalletAddress = "";

    //run time
    private WebView webViewPopUp = null;
    private AlertDialog builder = null;
    private Context globalContext = null;
    public Activity mActivity = null;
    public MirrorEnv env = MirrorEnv.MainNet;

    private MirrorDialog parentDialog = null;
    private WebView mLoginWebView = null;
    private String userAgent = null;

    //keys
    private String localFileKey = "mirror_local_storage";
    private String localKeyRefreshToken = "mirror_refresh_token";
    private String localKeyAPIKey = "mirror_api_key";
    private String localKeyWalletAddress = "mirror_wallet_address";

    //logic
    private MirrorLoginPageMode loginPageMode = MirrorLoginPageMode.CloseIfLoginDone;
    private LoginListener cbLogin = null;
    private MirrorCallback cbStringLogin = null;
    private MirrorCallback cbWalletLoginPassivity = null;
    public MirrorCallback safeFlowCb = null;

    //ui
    private WebView mLoginMainWebView = null;

    //temp

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
        Log.d("MirrorSDK Version",MirrorConstant.Version);
        this.mActivity = activityContext;
        if(this.mActivity != null){
            this.refreshToken = getRefreshToken(this.mActivity);
        }
        this.env = env;
        launchTab(mActivity);
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

    public void SetXAuthToken(String xAuthToken){
        logFlow("Set x auth token to:"+xAuthToken);
        this.xAuthToken = xAuthToken;
    }

    private void openInnerUrlOnUIThread(String url){
        if(mActivity == null){
            logFlow("openInnerUrlOnUIThread failed,need inite first.");
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openInnerUrl(url);
            }
        });
    }

    public boolean openInnerUrl(String url){
        logFlow("try to open url:"+url);
        MirrorDialog dialog = new MirrorDialog(mActivity);
        dialog.setCanceledOnTouchOutside(false);
        mLoginMainWebView = new CustomWebView(mActivity);
        mLoginMainWebView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        mLoginMainWebView.setFocusable(true);
        mLoginMainWebView.setFocusableInTouchMode(true);

        if(!MirrorWebviewUtils.isWebviewSupport(mActivity,mLoginMainWebView,mWebviewNotice,debugMode)){
            logFlow("Webview not support,update it please.");
            return false;
        }

        setWebView(mActivity,mLoginMainWebView,url);

        RelativeLayout layout = getPopupWindowLayout(mActivity);
        layout.addView(mLoginMainWebView);
        dialog.setContentView(layout);
//        dialog.init(mActivity,layout);

        parentDialog = dialog;
        dialog.show();

        return true;
    }

    private void openStartPage(){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            return;
        }

        String finalUrl = getAuthRoot() + apiKey + "?useSchemeRedirect=false";
        openInnerUrlOnUIThread(finalUrl);
        loginPageMode = MirrorLoginPageMode.CloseIfLoginDone;
    }

    public class CustomWebView extends WebView {
        public CustomWebView(Context context) {
            super(context);

            init();
        }

        public CustomWebView(Context context, AttributeSet attrs) {
            super(context, attrs);

            init();
        }

        public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            init();
        }

        protected void init() {
            setFocusable(true);
            setFocusableInTouchMode(true);
        }

        @Override
        public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
            BaseInputConnection baseInputConnection = new BaseInputConnection(this, false);
            outAttrs.imeOptions = IME_ACTION_DONE;
            outAttrs.inputType = TYPE_CLASS_TEXT;
            return baseInputConnection;
        }

        @Override
        public boolean onCheckIsTextEditor() {
            return true;
        }
    }

    public void StartLogin(LoginListener loginListener){
        checkSDKInited(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                CheckAuthenticated(new BoolListener() {
                    @Override
                    public void onBool(boolean boolValue) {
                        if(boolValue){
                            loginListener.onLoginSuccess();
                        }else {
                            openStartPage();
                            cbLogin = loginListener;
                        }
                    }
                });
            }

            @Override
            public void OnUnUsable() {
                openStartPage();
                cbLogin = loginListener;
            }
        });
    }

    /**
     * Open login page with Custom Tab
     */
    public void openLoginPage(MirrorCallback loginCb){
        sdkSimpleCheck(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                String urlPre = getAuthRoot() + apiKey + "?useSchemeRedirect=";
                if(MirrorWebviewUtils.isSupportCustomTab(mActivity)){
                    urlPre += "true";
                }else {
                    urlPre += "false";
                }
                openUrl(urlPre);
                cbStringLogin = loginCb;
            }

            @Override
            public void OnUnUsable() {
                logFlow("SDK not inited!");
            }
        });
    }
    public void openLoginPage(LoginListener loginCb){
        sdkSimpleCheck(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                String urlPre = getAuthRoot() + apiKey + "?useSchemeRedirect=";
                if(MirrorWebviewUtils.isSupportCustomTab(mActivity)){
                    urlPre += "true";
                }else {
                    urlPre += "false";
                }
                openUrl(urlPre);
                cbLogin = loginCb;
            }

            @Override
            public void OnUnUsable() {
                logFlow("SDK not inited!");
            }
        });
    }
    private void autoOpenLogin(MirrorCallback jwtCb){
        openLoginPage(new MirrorCallback() {
            @Override
            public void callback(String result) {
                LoginResponse res = MirrorGsonUtils.getInstance().fromJson(result,new TypeToken<LoginResponse>(){}.getType());
                if(jwtCb != null){
                    jwtCb.callback(res.access_token);
                }
            }
        });
    }

    public void openUrl(String url){
        logFlow("Try to open url with custom tab:"+url);
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            return;
        }

        ArrayList<ResolveInfo> infos = MirrorWebviewUtils.getCustomTabsPackages(mActivity);
        if(infos.size() == 0){
            openInnerUrlOnUIThread(url);
        }else {
            openWebPageWithCustomTab(url);
//            launchTab(mActivity, Uri.parse("mwsdk://userinfo?data=userInfoJsonString&access_token=accesstokentest&refresh_token=refreshtokentest"));
        }
    }

    void launchTab(Context context){
        final CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient client) {
//                final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                final CustomTabsIntent intent = builder.build();
                client.warmup(0L); // This prevents backgrounding after redirection
//                intent.launchUrl(context, uri);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };
        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome", connection);
    }

    private void openWebPageWithCustomTab(String url){
        final Activity activity = mActivity;
        sdkSimpleCheck(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                String packageName = MirrorWebviewUtils.getPackageNameToUse(activity);
                customTabsIntent.intent.setPackage(packageName);
//                customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                customTabsIntent.launchUrl(activity, Uri.parse(url));
            }

            @Override
            public void OnUnUsable() {
                logFlow("SDK not inited.");
            }
        });
    }

    public void StartLogin(MirrorCallback listener){
        //not use Gson
        openStartPage();
        cbStringLogin = listener;
        return;
        //use Gson
//        checkSDKInited(new OnCheckSDKUseable() {
//            @Override
//            public void OnChecked() {
//                CheckAuthenticated(new BoolListener() {
//                    @Override
//                    public void onBool(boolean boolValue) {
//                        if(boolValue){
//                            LoginResponse fakeRes = new LoginResponse();
//                            fakeRes.access_token = accessToken;
//                            fakeRes.refresh_token = refreshToken;
//                            fakeRes.user = new UserResponse();
//                            fakeRes.user.sol_address = mWalletAddress;
//                            fakeRes.user.id = mUserId;
//
//                            listener.callback(MirrorGsonUtils.getInstance().toJson(fakeRes));
//                        }else {
//                            openStartPage();
//                            cbStringLogin = listener;
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void OnUnUsable() {
//                openStartPage();
//                cbStringLogin = listener;
//            }
//        });
    }

    public void SetDebug(boolean debug){
        Log.d("MirrorSDK","Set debug to:"+debug);
        debugMode = debug;
    }

    public void setWebviewNotice(String notice){
        mWebviewNotice = notice;
    }

    public void SetApiKey(String id){
        apiKey = id.trim();
        if(mActivity != null){
            saveString(localKeyAPIKey, apiKey);
        }
    }

    public String getAuthRoot(){
        if(env == MirrorEnv.StagingMainNet){
            return "https://auth-staging.mirrorworld.fun/";
        }else if(env == MirrorEnv.StagingDevNet){
            return "https://auth-staging.mirrorworld.fun/";
        }else if(env == MirrorEnv.DevNet){
            return "https://auth.mirrorworld.fun/";
        }else if(env == MirrorEnv.MainNet){
            return "https://auth.mirrorworld.fun/";
        }else {
            logFlow("Unknown env:"+env);
            return "https://auth-staging.mirrorworld.fun/";
        }
    }

    private String getMarketRoot(){
        if(env == MirrorEnv.StagingMainNet){
            return "";//no url yet
        }else if(env == MirrorEnv.StagingDevNet){
            return "https://jump-devnet.mirrorworld.fun";
        }else if(env == MirrorEnv.DevNet){
            return "";//No url yet
        }else if(env == MirrorEnv.MainNet){
            return "https://jump.mirrorworld.fun/";
        }else {
            logFlow("Unknown env:"+env);
            return "https://jump-devnet.mirrorworld.fun";
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

    public String getActionRoot(){
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
            return "https://api.mirrorworld.fun/v1/";
        }
    }

    public String getActionRootWithoutVersion(){
        if(env == MirrorEnv.StagingMainNet){
            return "https://auth-staging.mirrorworld.fun/";
        }else if(env == MirrorEnv.StagingDevNet){
            return "https://auth-staging.mirrorworld.fun/";
        }else if(env == MirrorEnv.DevNet){
            return "https://auth.mirrorworld.fun/";
        }else if(env == MirrorEnv.MainNet){
            return "https://auth.mirrorworld.fun/";
        }else {
            logFlow("Unknown env:"+env);
            return "https://auth.mirrorworld.fun/";
        }
    }

    public String GetAPIRoot(){
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
                mWalletAddress = userResponse.sol_address;
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
                logFlow("FetchUser result"+result);
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

    public void FetchNFTsByOwnerAddresses(List<String> owners, FetchByOwnerListener fetchByOwnerListener){
        FetchNFTsByOwnerAddresses(owners,0,0,fetchByOwnerListener);
    }

    public void FetchNFTsByOwnerAddresses(List<String> owners, int limit, int offset, FetchByOwnerListener fetchByOwnerListener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : owners) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("owners", jsonArray);
            if(limit != 0) jsonObject.put("limit", limit);
            if(offset != 0) jsonObject.put("offset", offset);
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
                logFlow("ListResult:"+result);
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

    public void openMarket(){
        checkSDKInited(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                String urlPre = getMarketRoot()+ "?auth=" + accessToken + "&useSchemeRedirect=";
                if(MirrorWebviewUtils.isSupportCustomTab(mActivity)){
                    urlPre += "true";
                }else {
                    urlPre += "false";
                }
                logFlow("marekt url:"+urlPre);
                openUrl(urlPre);
            }

            @Override
            public void OnUnUsable() {
                logFlow("openMarket:sdk not prepared.");
            }
        });
    }

    public void OpenWallet(MirrorCallback loginCb){
        OpenWallet();
        cbWalletLoginPassivity = loginCb;
    }
    //Wallet
    public void OpenWallet(){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
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

        //Use this if login page updated
//        doOpenWallet();
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
        String finalUrlPre = getAuthRoot() + "jwt?key=" + accessToken + "&useSchemeRedirect=";
        if(MirrorWebviewUtils.isSupportCustomTab(mActivity)){
            finalUrlPre += "true";
        }else {
            finalUrlPre += "false";
        }
        logFlow("wallet url:"+finalUrlPre);
        openUrl(finalUrlPre);

        loginPageMode = MirrorLoginPageMode.KeepIfLoginDone;
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

    public void TransferSOL(String toPublickey, float amount, TransferSOLListener transferSOLListener){
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

    public void Transactions(int limit, String before, GetWalletTransactionListener walletTransactionListener){
        HashMap<String,String> map = new HashMap<String,String>();
        if(limit != 0) map.put("limit", String.valueOf(limit));
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

    public void GetTransactionBySignature(String signature, GetOneWalletTransactionBySigListener listener){
        String url = GetAPIRoot() + MirrorUrl.URL_GET_WALLET_TRANSACTIONS + "/"+signature;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<TransactionsDTO> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<TransactionsDTO>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else if(response.code == MirrorResCode.NO_RESOURCES){
                    listener.onFailed(response.code,"No this transaction.");
                }else{
                    listener.onFailed(response.code,response.message);
                }
            }
        });
    }


    public void FetchNFTsByUpdateAuthorities(List<String> update_authorities, FetchNFTsListener listener){
        FetchNFTsByUpdateAuthorities(update_authorities,0,0,listener);
    }
    public void FetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, FetchNFTsListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : update_authorities) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("update_authorities", jsonArray);
            if(limit != 0) jsonObject.put("limit", limit);
            if(offset != 0) jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = GetAPIRoot() + MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_UPDATE_AUTHORITY_ADDRESS;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                logFlow("FetchNFTsByUpdateAuthorities result"+result);
                CommonResponse<MultipleNFTsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MultipleNFTsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onFetchSuccess(response.data);
                }else{
                    listener.onFetchFailed(response.code,response.message);
                }
            }
        }));
    }

    public void FetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, FetchNFTsListener listener){
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

    public void TransferToken(String toPublickey, float amount, String token_mint, float decimals, MirrorCallback mirrorCallback){
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

    public void sdkSimpleCheck(OnCheckSDKUseable callback){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                callback.OnUnUsable();
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey == ""){
            logFlow("Must set app id first!");
            callback.OnUnUsable();
            return;
        }
        callback.OnChecked();
    }

    public void checkSDKInited(OnCheckSDKUseable callback){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
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
                    callback.OnChecked();
                }
            });
            return;
        }else{
            callback.OnChecked();
        }
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
            logFlow("No refresh token,jump to login page...");
            autoOpenLogin(mirrorCallback);
            return;
        }

        if(apiKey == ""){
            apiKey = getSavedString(activityContext, localKeyAPIKey);
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
                            autoOpenLogin(mirrorCallback);
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
                                    autoOpenLogin(mirrorCallback);
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
        logFlow("accessToken:"+accessToken);
        logFlow("apiKey:"+apiKey);
        logFlow("xAuthKey:"+xAuthToken);

        try {
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
            if(xAuthToken != "") conn.setRequestProperty("x-authorization-token",xAuthToken);

            OutputStream out = conn.getOutputStream();
            if(null != data){
                out.write(data.getBytes(Charset.forName("UTF-8")));
            }

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

    public MirrorCallback getHandlerCallback(MirrorCallback mirrorCallback){
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
            apiKey = getSavedString(mActivity, localKeyAPIKey);
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

    public void checkParamsAndPost(String url, String data, MirrorCallback mirrorCallback){
        if(apiKey == ""){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
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

            logFlow("http get code:"+urlConn.getResponseCode());
            if(urlConn.getResponseCode() == MirrorResCode.NO_RESOURCES){
                CommonResponse res = new CommonResponse();
                res.code = MirrorResCode.NO_RESOURCES;
                urlConn.disconnect();
                return MirrorGsonUtils.getInstance().toJson(res);
            }else {
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream());

                BufferedReader buffer = new BufferedReader(in);
                String inputLine = null;

                result = "";

                while((inputLine = buffer.readLine())!=null){
                    result += inputLine + "\n";
                }
                in.close();
                urlConn.disconnect();
            }
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

    private void setWebView(Context context,WebView webView,String url){
        this.mLoginWebView = webView;
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                mLoginMainWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                if(url.contains("discord.com/")){
//                }else {
//
//                }
                return super.shouldOverrideUrlLoading(view, url);
            }
             }
        );

        //尝试解决跨域问题
//        try {
//            Class<?> clazz = webView.getSettings().getClass();
//            Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
//            if (method != null) {
//                method.invoke(webView.getSettings(), true);
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

        WebSettings webSettings = webView.getSettings();
        //set autofit
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // Set User Agent
        userAgent = System.getProperty("http.agent");
        webSettings.setUserAgentString(userAgent + MirrorConstant.AppName);

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
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
//        webSettings.setPluginState(WebSettings.PluginState.ON);

        webView.addJavascriptInterface(this, MirrorConstant.JsDelegateName);

        logFlow("open login page with url:"+url);
        webView.loadUrl(url);
    }

    @JavascriptInterface
    public void setActionApprovalToken(String xAuthToken){
        Log.d("MirrorSDK world",xAuthToken);
        SetXAuthToken(xAuthToken);
        if(safeFlowCb != null){
            safeFlowCb.callback("");
            safeFlowCb = null;
        }
    }

    @JavascriptInterface
    public void walletLogout(){
        clearCache();
        if(cbWalletLoginPassivity != null){
            logFlow("wallet passivity login success.");
            cbWalletLoginPassivity.callback("");
            cbWalletLoginPassivity = null;
        }
    }

    @JavascriptInterface
    public void setLoginResponse(String dataJsonStr) {
        logFlow("receive login response:"+dataJsonStr);
        JSONObject jsonObject = null;
        MirrorThreadUtils.runLogicInUIThread(mActivity, new MSimpleCallback() {
            @Override
            public void callback() {
                try {
                    LoginResponse aaa = MirrorGsonUtils.getInstance().fromJson(dataJsonStr,new TypeToken<LoginResponse>(){}.getType());
                    saveRefreshToken(aaa.refresh_token);
                    accessToken = aaa.access_token;
                    mWalletAddress = aaa.user.sol_address;

//            jsonObject = new JSONObject(dataJsonStr);
//            String token = jsonObject.getString("refresh_token");
//            saveRefreshToken(token);
//            accessToken = jsonObject.getString("access_token");
//            mWalletAddress = jsonObject.getJSONObject("user").getString("sol_address");
//            mUserId = Long.parseLong(jsonObject.getJSONObject("user").getString("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(loginPageMode == MirrorLoginPageMode.CloseIfLoginDone){
                    if(parentDialog!= null) parentDialog.dismiss();
                }else if(loginPageMode == MirrorLoginPageMode.KeepIfLoginDone){
                    //do nothing
                }else {
                    logFlow("Unknown login page mode:"+loginPageMode);
                }

                logFlow("cbLogin:"+cbLogin+"  cbStringLogin:"+cbStringLogin);
                if(cbLogin != null){
                    logFlow("login success and LoginListener callback called.");
                    cbLogin.onLoginSuccess();
                    cbLogin = null;
                }
                if(cbStringLogin != null){
                    logFlow("login success and MirrorCallback callback called.");
                    cbStringLogin.callback(dataJsonStr);
                    cbStringLogin = null;
                }
                if(cbWalletLoginPassivity != null){
                    logFlow("wallet passivity login success.");
                    cbWalletLoginPassivity.callback(dataJsonStr);
                    cbWalletLoginPassivity = null;
                }
            }
        });
    }

    @JavascriptInterface
    public void closePage() {
        parentDialog.dismiss();
    }

    public void saveRefreshToken(String refreshToken){
        if(refreshToken == "") return;

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

    private void clearCache(){
        accessToken = "";
        refreshToken = "";
        SharedPreferences sp = mActivity.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(localKeyRefreshToken, "");
        editor.putString(localKeyAPIKey, "");
        editor.putString(localKeyWalletAddress, "");
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
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relative.setLayoutParams(lp);
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

    public void logFlow(String value){
        if(debugMode){
            Log.d("MirrorSDK"+MirrorConstant.Version,value);
            if(mActivity != null) {
//                Toast.makeText(mActivity, value, Toast.LENGTH_LONG).show();
            }
        }
    }

    final public static void logError(String value){
        Log.e("MirrorSDK Error"+MirrorConstant.Version,value);
    }

    class MirrorChromeClient extends WebChromeClient {
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            webViewPopUp = new MainWebView(globalContext);
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
            webViewPopupSetting.setCacheMode(WebSettings.LOAD_DEFAULT);

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