package com.mirror.sdk;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

import static com.mirror.sdk.constant.MirrorUrl.URL_GET_COLLECTION_FILTER_INFO;
import static com.mirror.sdk.constant.MirrorUrl.URL_GET_COLLECTION_INFO;
import static com.mirror.sdk.constant.MirrorUrl.URL_GET_NFTS;
import static com.mirror.sdk.constant.MirrorUrl.URL_GET_NFT_EVENTS;
import static com.mirror.sdk.constant.MirrorUrl.URL_GET_NFT_INFO;
import static com.mirror.sdk.constant.MirrorUrl.URL_GET_NFT_REAL_PRICE;
import static com.mirror.sdk.constant.MirrorUrl.URL_RECOMMEND_SEARCH_NFT;
import static com.mirror.sdk.constant.MirrorUrl.URL_SEARCH_NFTS;
import static com.mirror.sdk.constant.MirrorUrl.getGetMirrorUrl;
import static com.mirror.sdk.constant.MirrorUrl.getMirrorUrl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.mirror.sdk.constant.MirrorAPIVersion;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorConfirmation;
import com.mirror.sdk.constant.MirrorConstant;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.constant.MirrorLoginPageMode;
import com.mirror.sdk.constant.MirrorResCode;
import com.mirror.sdk.constant.MirrorService;
import com.mirror.sdk.constant.MirrorUrl;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingResponse;
import com.mirror.sdk.listener.metadata.GetCollectionFilterInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionInfoListener;
import com.mirror.sdk.listener.metadata.GetCollectionSummaryListener;
import com.mirror.sdk.listener.metadata.GetNFTEventsListener;
import com.mirror.sdk.listener.metadata.GetNFTRealPriceListener;
import com.mirror.sdk.listener.metadata.GetNFTsListener;
import com.mirror.sdk.listener.metadata.SearchNFTsListener;
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
import com.mirror.sdk.response.metadata.GetCollectionFilterInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionInfoRes;
import com.mirror.sdk.response.metadata.GetCollectionSummaryRes;
import com.mirror.sdk.response.metadata.GetNFTEventsRes;
import com.mirror.sdk.response.metadata.GetNFTRealPriceRes;
import com.mirror.sdk.response.metadata.GetNFTsRes;
import com.mirror.sdk.response.metadata.MirrorMarketSearchNFTObj;
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

    //run time
    private boolean mIsInited = false;
    private WebView webViewPopUp = null;
    private AlertDialog builder = null;
    private Context globalContext = null;
    public Activity mActivity = null;
    public MirrorEnv env = MirrorEnv.MainNet;
    public MirrorChains mChain = MirrorChains.SOLANA;

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
    private MirrorCallback cbWalletLogout = null;
    public MirrorCallback safeFlowCb = null;
    public MirrorCallback updateAuthTokenCb = null;

    //for unity
    /**
     * Every update of login info will call this, and it will pass them to unity
     */
    private MirrorCallback cbConstantLoginString = null;

    //ui
    private WebView mLoginMainWebView = null;

    private static volatile MirrorSDK instance;

    public static MirrorSDK getInstance(){
        if (instance == null){
            synchronized(MirrorSDK.class){
                instance = new MirrorSDK();
            }
        }
        return instance;
    }

    public MirrorEnv getEnv(){
        if(!getInited()){
            logWarn("SDK has not been inited!");
            return MirrorEnv.MainNet;
        }

        return env;
    }

    public boolean getInited(){
        return mIsInited;
    }

    public MirrorChains getChain(){
        return mChain;
    }

    public void InitSDK(Activity activityContext, MirrorEnv env, MirrorChains chain){
        if(mIsInited){
            logFlow("SDK has been inited, please do not init again.");
            return;
        }
        mIsInited = true;

        Log.d("MirrorSDK Version",MirrorConstant.Version);
        this.mActivity = activityContext;
        if(this.mActivity != null){
            this.refreshToken = getRefreshToken(this.mActivity);
        }
        mChain = chain;
        logFlow("init sdk,chain is:"+mChain);
        this.env = env;
        logFlow("init sdk,environment is:"+this.env);
        launchTab(mActivity);
    }

    public void setAuthTokenCallback(MirrorCallback callback){
        logFlow("setAuthTokenCallback seted!");
        updateAuthTokenCb = callback;
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
                openUrlByWebview(url);
            }
        });
    }

    public boolean openUrlByWebview(String url){
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
        if(apiKey.equals("")){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey.equals("")){
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

    public void guestLogin(LoginListener listener){
        String url = MirrorUrl.getActionRoot() + MirrorUrl.URL_GUEST_LOGIN;
        sdkSimpleCheck(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                doGet(url,null, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        logFlow("Guest login result:" + result);
                        listener.onLoginSuccess();
                        CommonResponse<LoginResponse> res = MirrorGsonUtils.getInstance().fromJson(result,new TypeToken<CommonResponse<LoginResponse>>(){}.getType());
                        if(res.code == MirrorResCode.SUCCESS){
                            setLoginResponse(MirrorGsonUtils.getInstance().toJson(res.data));
                            listener.onLoginSuccess();
                        }else {
                            listener.onLoginFail();
                        }
                    }
                });
            }

            @Override
            public void OnUnUsable() {
                logFlow("Please init sdk first!");
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
                String urlPre = getAuthRoot() + apiKey;
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
                String urlPre = getAuthRoot() + apiKey;
                openUrl(urlPre);
                cbLogin = loginCb;
            }

            @Override
            public void OnUnUsable() {
                logFlow("SDK not inited!");
            }
        });
    }
    public void setConstantLoginStringCallback(MirrorCallback callback){
        logFlow("Set constant login string callback result:" + (callback != null));
        cbConstantLoginString = callback;
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
        String finalUrlPre = "";
        if(url.contains("?")){
            finalUrlPre = url + "&useSchemeRedirect=";
        }else {
            finalUrlPre = url + "?useSchemeRedirect=";
        }
        if(MirrorWebviewUtils.isSupportCustomTab(mActivity)){
            finalUrlPre += "true";
        }else {
            finalUrlPre += "false";
        }

        if(apiKey.equals("")){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey.equals("")){
            logFlow("Must set app id first!");
            return;
        }

        if(!MirrorWebviewUtils.isSupportCustomTab(mActivity)){
            logFlow("try to open url with webview:"+url);
            openInnerUrlOnUIThread(finalUrlPre);
        }else {
            logFlow("Try to open url with custom tab:" + finalUrlPre);
            openWebPageWithCustomTab(finalUrlPre);
//            launchTab(mActivity, Uri.parse("mwsdk://userinfo?data=userInfoJsonString&access_token=accesstokentest&refresh_token=refreshtokentest"));
        }
    }

    void launchTab(Context context){
        final CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient client) {
                client.warmup(0L); // This prevents backgrounding after redirection
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

    public void SetDebug(boolean debug){
        Log.d("MirrorSDK","Set debug to:"+debug);
        debugMode = debug;
    }

    public void setWebviewNotice(String notice){
        mWebviewNotice = notice;
    }

    public void SetApiKey(String id){
        apiKey = id.trim();
        if(apiKey.isEmpty()){
            if(mActivity != null){
                apiKey = getSavedString(mActivity, localKeyAPIKey);
                if(apiKey.isEmpty()){
                    logFlow("APIKey must be a valid string!now is:"+apiKey);
                    return;
                }
            }else {
                logFlow("APIKey must be a valid string!now is:"+apiKey);
                return;
            }
        }
        logFlow("Set APIKey:"+apiKey);
        if(mActivity != null){
            saveString(localKeyAPIKey, apiKey);
        }
    }

    public String getAuthRoot(){
        if(env.equals(MirrorEnv.StagingMainNet)){
            return "https://auth-staging.mirrorworld.fun/";
        }else if(env.equals(MirrorEnv.StagingDevNet)){
            return "https://auth-staging.mirrorworld.fun/";
        }else if(env.equals(MirrorEnv.DevNet)){
            return "https://auth.mirrorworld.fun/";
        }else if(env.equals(MirrorEnv.MainNet)){
            return "https://auth.mirrorworld.fun/";
        }else {
            logFlow("Unknown env:"+env);
            return "https://auth-staging.mirrorworld.fun/";
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
//                mWalletAddress = userResponse.sol_address;
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

        String url = MirrorUrl.getActionRoot() + MirrorUrl.URL_LOGIN_WITH_EMAIL;
        doPostRequest(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                MirrorSDK.getInstance().SetAccessToken(MirrorSDK.getInstance().GetAccessTokenFromResponse(result));
                MirrorSDK.getInstance().SetRefreshToken(MirrorSDK.getInstance().GetRefreshTokenFromResponse(result));
                mirrorCallback.callback(result);
            }
        });
    }

    public void FetchUser(FetchUserListener fetchUserListener){
        Map<String,String> map = new HashMap<>();

        String url = MirrorUrl.getActionRoot() + MirrorUrl.URL_IS_AUTHENTICATED;
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

        String url = MirrorUrl.getActionRoot() + MirrorUrl.URL_QUERY_USER;
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

    public void GetNFTDetails(String mint_address,String mint_id, FetchSingleNFTListener fetchSingleNFT){
        String url = getGetMirrorUrl(MirrorService.AssetNFT) + mint_address + "/" + mint_id;
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

        String url = getMirrorUrl(MirrorService.AssetMint,MirrorUrl.URL_MINT_NFT_COLLECTION);//GetAPIRoot() + MirrorUrl.URL_MINT_NFT_COLLECTION;
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

    public void updateNFTProperties(String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl,int seller_fee_basis_points, String confirmation, MintNFTListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mintAddress);
            jsonObject.put("name", name);
            jsonObject.put("symbol", symbol);
            jsonObject.put("update_authority", updateAuthority);
            jsonObject.put("url", NFTJsonUrl);
            jsonObject.put("seller_fee_basis_points", seller_fee_basis_points);
            jsonObject.put("confirmation", confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.AssetNFT,MirrorUrl.URL_UPDATE_NFT_PROPERTIES);//GetAPIRoot() + MirrorUrl.URL_UPDATE_NFT_PROPERTIES;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<MintResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<MintResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onMintSuccess(response.data);
                }else{
                    listener.onMintFailed(response.code,response.message);
                }
            }
        }));
    }

    public void checkStatusOfMinting(List<String> mintAddresses, CheckStatusOfMintingListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (String tag : mintAddresses) {
                jsonArray.put(tag);
            }
            jsonObject.put("mint_addresses", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.Confirmation,MirrorUrl.URL_CHECK_STATUS_OF_MINTING);//GetAPIRoot() + MirrorUrl.URL_CHECK_STATUS_OF_MINTING;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<CheckStatusOfMintingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<CheckStatusOfMintingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onCheckFailed(response.code,response.message);
                }
            }
        }));
    }

    public void checkStatusOfTransactions(List<String> signatures, CheckStatusOfMintingListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (String tag : signatures) {
                jsonArray.put(tag);
            }
            jsonObject.put("signatures", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.Confirmation,MirrorUrl.URL_CHECK_STATUS_OF_TRANSACTION);//GetAPIRoot() + MirrorUrl.URL_CHECK_STATUS_OF_TRANSACTION;
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<CheckStatusOfMintingResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<CheckStatusOfMintingResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onCheckFailed(response.code,response.message);
                }
            }
        }));
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

        String url = getMirrorUrl(MirrorService.AssetMint,MirrorUrl.URL_MINT_TOP_LEVEL_COLLECTION);//GetAPIRoot() + MirrorUrl.URL_MINT_TOP_LEVEL_COLLECTION;
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

        String url = getMirrorUrl(MirrorService.AssetAuction,MirrorUrl.URL_TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET);//GetAPIRoot() + MirrorUrl.URL_TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET;
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

        String url = getMirrorUrl(MirrorService.AssetNFT,MirrorUrl.URL_FETCH_MULTIPLE_NFT);//GetAPIRoot() + MirrorUrl.URL_FETCH_MULTIPLE_NFT;
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
        String url = getMirrorUrl(MirrorService.AssetNFT,MirrorUrl.URL_FETCH_ACTIVITY) + mint_address;//GetAPIRoot() + MirrorUrl.URL_FETCH_ACTIVITY + mint_address;
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

        String url = getMirrorUrl(MirrorService.AssetAuction,MirrorUrl.URL_CANCEL_LISTING_OF_NFT_ON_THE_MARKETPLACE);//GetAPIRoot() + MirrorUrl.URL_CANCEL_LISTING_OF_NFT_ON_THE_MARKETPLACE;
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
            jsonObject.put("confirmation",confirmation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String  data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.AssetAuction,MirrorUrl.URL_UPDATE_LISTING_OF_NFT_ON_THE_MARKETPLACE);//GetAPIRoot() + MirrorUrl.URL_UPDATE_LISTING_OF_NFT_ON_THE_MARKETPLACE;
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

        String url = getMirrorUrl(MirrorService.AssetAuction,MirrorUrl.URL_LIST_NFT_ON_THE_MARKETPLACE);//GetAPIRoot() + MirrorUrl.URL_LIST_NFT_ON_THE_MARKETPLACE;
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

        String url = getMirrorUrl(MirrorService.AssetNFT,MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_MINT_ADDRESS);//GetAPIRoot() + MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_MINT_ADDRESS;
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

        String url = getMirrorUrl(MirrorService.AssetAuction,MirrorUrl.URL_BUY_NFT_ON_THE_MARKETPLACE);//GetAPIRoot() + MirrorUrl.URL_BUY_NFT_ON_THE_MARKETPLACE;
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

    public void openApprovePage(String url){
        url += "?key=" + accessToken;
        openUrl(url);
    }

    public void openMarket(String rootUrl){
        checkSDKInited(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                String urlPre = rootUrl + "?auth=" + accessToken ;
                openUrl(urlPre);
            }

            @Override
            public void OnUnUsable() {
                logFlow("openMarket:sdk not prepared.");
            }
        });
    }

    public void logout(BoolListener listener){
        checkSDKInited(new OnCheckSDKUseable() {
            @Override
            public void OnChecked() {
                logFlow("User logout,clear cache.");

                String urlPre = MirrorUrl.getActionRoot() + MirrorUrl.URL_LOGOUT;
                checkParamsAndPost(urlPre, null, new MirrorCallback() {
                    @Override
                    public void callback(String result) {
                        logFlow("Logout result:" + result);
                        listener.onBool(true);
                        clearCache();
                    }
                });
            }

            @Override
            public void OnUnUsable() {
                logFlow("No need to logout cause you haven't login yet.");
                listener.onBool(false);
            }
        });
    }

    /**
     *
     * @param walletUrl if is "" ,will use default url
     * @param loginCb
     */
    public void OpenWallet(String walletUrl, MirrorCallback loginCb){
        doOpenWallet(walletUrl);
        cbWalletLogout = loginCb;

        logFlow("Set open wallet callback" + cbWalletLogout);
    }
    //Wallet
    private void doOpenWallet(String walletUrl){
        String finalUrlPre = walletUrl;
        if(finalUrlPre == ""){
            finalUrlPre = getAuthRoot() + "jwt?key=" + accessToken;
        }

        openUrl(finalUrlPre);
        loginPageMode = MirrorLoginPageMode.KeepIfLoginDone;
    }

//    public void GetWallet(MirrorCallback mirrorCallback){
////        if(mWalletAddress.equals("")){
////            mWalletAddress = getSavedString(mActivity,localKeyWalletAddress);
////        }
//        String url = GetSSORoot() + MirrorUrl.URL_ME;
//        checkParamsAndGet(url, null, new MirrorCallback() {
//            @Override
//            public void callback(String result) {
//                String address = "";
//                try {
//                    JSONObject obj = new JSONObject(result);
//                    address = obj.getJSONObject("data").getJSONObject("user").getJSONObject("wallet").getString("sol_address");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                mirrorCallback.callback(address);
//            }
//        });
//        return;
//    }

    public void TransferSOL(String toPublickey, float amount, TransferSOLListener transferSOLListener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", toPublickey);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.Wallet,MirrorUrl.URL_TRANSFER_SQL);
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

    public void getTransactionOfTransferSOL(String toPublickey,float amount,GetWalletTransactionListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", toPublickey);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.Wallet,MirrorUrl.URL_GET_TRANSFER_SOL_TRANSACTION);
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                logFlow("getTransactionOfTransferSOL result:" + result);
                CommonResponse<GetWalletTransactionsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTransactionsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onFailed(response.code,response.message);
                }
            }
        }));
    }

    public void getTransactionOfTransferToken(String toPublickey,float amount,String tokenMint,int decimals,GetWalletTransactionListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", toPublickey);
            jsonObject.put("amount", amount);
            jsonObject.put("token_mint", tokenMint);
            jsonObject.put("decimals", decimals);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.Wallet,MirrorUrl.URL_GET_TRANSFER_TOKEN_TRANSACTION);
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                logFlow("getTransactionOfTransferToken result:" + result);
                CommonResponse<GetWalletTransactionsResponse> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetWalletTransactionsResponse>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onFailed(response.code,response.message);
                }
            }
        }));
    }

    public void GetWalletTokens(GetWalletTokenListener walletTokenListener){
        String url = getGetMirrorUrl(MirrorService.Wallet) + MirrorUrl.URL_GET_WALLET_TOKEN;
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
    public void GetWalletTokensByWallet(String walletAddress, GetWalletTokenListener walletTokenListener){
        String url = getGetMirrorUrl(MirrorService.Wallet) + MirrorUrl.URL_GET_WALLET_TOKEN + "/" + walletAddress;
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

        String url = getGetMirrorUrl(MirrorService.Wallet) + MirrorUrl.URL_GET_WALLET_TRANSACTIONS;
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

    public void getTransactionsByWallet(String walletAddress, int limit, MirrorCallback listener){
        HashMap<String,String> map = new HashMap<String,String>();
        if(limit != 0) map.put("limit", String.valueOf(limit));

        String url = getGetMirrorUrl(MirrorService.Wallet)+ walletAddress + "/" + MirrorUrl.URL_GET_WALLET_TRANSACTIONS;
        checkParamsAndGet(url, map, new MirrorCallback() {
            @Override
            public void callback(String result) {
                listener.callback(result);
            }
        });
    }

    public void GetTransactionBySignature(String signature, GetOneWalletTransactionBySigListener listener){
        String url = getGetMirrorUrl(MirrorService.Wallet) + MirrorUrl.URL_GET_WALLET_TRANSACTIONS + "/"+signature;
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
        if(getEnv() == MirrorEnv.DevNet || getEnv() == MirrorEnv.StagingDevNet){
            logWarn("FetchNFTsByUpdateAuthorities API can only run on MAINNET.");
            return;
        }
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

        String url = getMirrorUrl(MirrorService.AssetNFT,MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_UPDATE_AUTHORITY_ADDRESS);
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
        if(getEnv() == MirrorEnv.DevNet || getEnv() == MirrorEnv.StagingDevNet){
            logWarn("FetchNFTsByCreatorAddresses API can only run on MAINNET.");
            return;
        }
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

        String url = getMirrorUrl(MirrorService.AssetNFT,MirrorUrl.URL_FETCH_MULTIPLE_NFTDATA_BY_CREATOR_ADDRESS);
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

    public void TransferETH(String nonce, String gasPrice, String gasLimit, String to,int amount, MirrorCallback mirrorCallback){
        if(!mChain.equals(MirrorChains.EVM)){
            logWarn("This API support only EVM chain.");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nonce", nonce);
            jsonObject.put("gasPrice", gasPrice);
            jsonObject.put("gasLimit", gasLimit);
            jsonObject.put("to", to);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.Wallet,MirrorUrl.URL_GET_WALLET_TRANSFER_ETH);
        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
    }

    public void TransferToken(String toPublickey, float amount, String token_mint, int decimals, MirrorCallback mirrorCallback){
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

        String url = getMirrorUrl(MirrorService.Wallet,MirrorUrl.URL_TRANSFER_TOKEN);
        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
    }

    public void GetCollectionFilterInfo(String collectionAddress, GetCollectionFilterInfoListener listener){
        String url = getGetMirrorUrl(MirrorService.MetadataCollection) + URL_GET_COLLECTION_FILTER_INFO + "?collection=" + collectionAddress;
        Map<String,String> urlParams = new HashMap<>();
        urlParams.put("collection",collectionAddress);
        checkParamsAndGet(url, urlParams, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetCollectionFilterInfoRes> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetCollectionFilterInfoRes>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else{
                    listener.onFail(response.code,response.message);
                }
            }
        });
    }

    public void getCollectionsSummary(List<String> collections, GetCollectionSummaryListener listener){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        String url = getMirrorUrl(MirrorService.MetadataCollection,MirrorUrl.URL_GET_COLLECTION_SUMMARY);
        checkParamsAndPost(url,data,getHandlerCallback(new MirrorCallback() {
            @Override
            public void callback(String result) {
                logFlow("getCollectionsSummary result:"+result);
                CommonResponse<List<GetCollectionSummaryRes>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<GetCollectionSummaryRes>>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFailed(response.code, response.message);
                }
            }
        }));
    }

    public void GetNFTInfoOnSolana(String mintAddress, MirrorCallback listener){
        if(!mChain.equals(MirrorChains.SOLANA)){
            logWarn("This API support only SOLANA chain.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.Metadata) + URL_GET_NFT_INFO + "/" + mintAddress;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {
                listener.callback(result);
            }
        });
    }
    public void GetNFTInfoOnMultiChain(String contractAddress, String tokenID, MirrorCallback listener){
        if(mChain.equals(MirrorChains.SOLANA)){
            logWarn("This API don't support SOLANA chain.Please use the specified API intead.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.Metadata) + URL_GET_NFT_INFO + "/" + contractAddress + "/" + tokenID;
        checkParamsAndGet(url, null, new MirrorCallback() {
            @Override
            public void callback(String result) {
                listener.callback(result);
            }
        });
    }

    public void GetCollectionInfo(List<String> collections, GetCollectionInfoListener listener){
        String url = getMirrorUrl(MirrorService.Metadata,URL_GET_COLLECTION_INFO);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                logFlow("GetCollectionInfo result:"+result);
                CommonResponse<List<GetCollectionInfoRes>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<GetCollectionInfoRes>>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    public void GetNFTEventsOnSolana(String mint_address, int page, int page_size, GetNFTEventsListener listener){
        if(!mChain.equals(MirrorChains.SOLANA)){
            logWarn("This API support only SOLANA chain.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.MetadataNFT) + URL_GET_NFT_EVENTS;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("page", page);
            jsonObject.put("page_size", page_size);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                logFlow("GetNFTEvents result:"+result);
                CommonResponse<GetNFTEventsRes> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetNFTEventsRes>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    public void GetNFTEventsOnMultiChain(String contract, int page, int page_size, GetNFTEventsListener listener){
        if(mChain.equals(MirrorChains.SOLANA)){
            logWarn("Please use solana API instead.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.MetadataNFT) + URL_GET_NFT_EVENTS;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contract", contract);
            jsonObject.put("page", page);
            jsonObject.put("page_size", page_size);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();
        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                logFlow("GetNFTEvents result:"+result);
                CommonResponse<GetNFTEventsRes> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetNFTEventsRes>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    public void SearchNFTs(List<String> collections, String searchStr, SearchNFTsListener listener){
        if(!mChain.equals(MirrorChains.SOLANA)){
            logWarn("This API support only SOLANA chain.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.MetadataNFT) + URL_SEARCH_NFTS;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
            jsonObject.put("search", searchStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<List<MirrorMarketSearchNFTObj>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<MirrorMarketSearchNFTObj>>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    /**
     * Will provide 10 NFT info at most as recommend NFT.
     * Developer can use this API to fill some recommend blank when user trying search.
     * @param collections
     * @param listener
     */
    public void RecommendSearchNFT(List<String> collections, SearchNFTsListener listener){
        if(!mChain.equals(MirrorChains.SOLANA)){
            logWarn("This API support only SOLANA chain.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.MetadataNFTSearch) + URL_RECOMMEND_SEARCH_NFT;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String tag : collections) {
            jsonArray.put(tag);
        }
        try {
            jsonObject.put("collections", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<List<MirrorMarketSearchNFTObj>> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<List<MirrorMarketSearchNFTObj>>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    public void getNFTsByUnabridgedParamsOnSolana(String collection, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, GetNFTsListener listener){
        if(!mChain.equals(MirrorChains.SOLANA)){
            logWarn("This API support only SOLANA chain.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.Metadata) + URL_GET_NFTS;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection", collection);
            jsonObject.put("page", page);
            jsonObject.put("page_size", page_size);

            JSONObject order = new JSONObject();
            order.put("order_by",order_by);
            order.put("desc",desc);
            jsonObject.put("order", order);

            jsonObject.put("sale", sale);

            JSONArray filters = new JSONArray();
            if(filter != null){
                for (JSONObject fi : filter) {
                    filters.put(fi);
                }
            }
            jsonObject.put("filter", filters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetNFTsRes> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetNFTsRes>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    public void getNFTsByUnabridgedParamsOnMultiChain(String contract, int page, int page_size, String order_by, boolean desc, double sale, List<JSONObject> filter, GetNFTsListener listener){
        if(mChain.equals(MirrorChains.SOLANA)){
            logWarn("Please use solana API intead.");
            return;
        }
        String url = getGetMirrorUrl(MirrorService.Metadata) + URL_GET_NFTS;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contract", contract);
            jsonObject.put("page", page);
            jsonObject.put("page_size", page_size);

            JSONObject order = new JSONObject();
            order.put("order_by",order_by);
            order.put("desc",desc);
            jsonObject.put("order", order);

            jsonObject.put("sale", sale);

            JSONArray filters = new JSONArray();
            if(filter != null){
                for (JSONObject fi : filter) {
                    filters.put(fi);
                }
            }
            jsonObject.put("filter", filters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetNFTsRes> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetNFTsRes>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

    public void GetNFTRealPrice(String price, int fee, GetNFTRealPriceListener listener){
        String url = GetSSORoot() + URL_GET_NFT_REAL_PRICE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("price", price);
            jsonObject.put("fee", fee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(url, data, new MirrorCallback() {
            @Override
            public void callback(String result) {
                CommonResponse<GetNFTRealPriceRes> response = MirrorGsonUtils.getInstance().fromJson(result, new TypeToken<CommonResponse<GetNFTRealPriceRes>>(){}.getType());
                if(response.code == MirrorResCode.SUCCESS){
                    listener.onSuccess(response.data);
                }else {
                    listener.onFail(response.code, response.message);
                }
            }
        });
    }

//    private void CreateNewMarketPlace(String treasury_withdrawal_destination, String fee_withdrawal_destination, String treasury_mint, double seller_fee_basis_points, MirrorCallback mirrorCallback){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("treasury_withdrawal_destination", treasury_withdrawal_destination);
//            jsonObject.put("fee_withdrawal_destination", fee_withdrawal_destination);
//            jsonObject.put("treasury_mint", treasury_mint);
//            jsonObject.put("seller_fee_basis_points", seller_fee_basis_points);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//
//        String url = GetAPIRoot() + MirrorUrl.URL_CREATE_NEW_MARKET_PLACE;
//        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
//    }

//    private void UpdateMarketPlace(String new_authority, String treasury_mint, String treasury_withdrawal_destination, String fee_withdrawal_destination, double seller_fee_basis_points,MirrorCallback mirrorCallback){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("new_authority", new_authority);
//            jsonObject.put("treasury_mint",treasury_mint);
//            jsonObject.put("treasury_withdrawal_destination", treasury_withdrawal_destination);
//            jsonObject.put("fee_withdrawal_destination", fee_withdrawal_destination);
//            jsonObject.put("seller_fee_basis_points", seller_fee_basis_points);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data = jsonObject.toString();
//
//        String url = GetAPIRoot() + MirrorUrl.URL_UPDATE_MARKET_PLACE;
//        checkParamsAndPost(url,data,getHandlerCallback(mirrorCallback));
//    }

//    private void QueryMarketPlace(String name, String client_id,
//                                 String authority,String treasury_mint,
//                                 String auction_house_fee_account,String auction_house_treasury,
//                                 String treasury_withdrawal_destination,String fee_withdrawal_destination,
//                                 String seller_fee_basis_points,String requires_sign_off,String can_change_sale_price,
//                                 MirrorCallback mirrorCallback){
//
//        HashMap<String,String> map = new HashMap<String,String>();
//        map.put("name",name);
//        map.put("client_id",client_id);
//        map.put("authority",authority);
//        map.put("treasury_mint",treasury_mint);
//        map.put("auction_house_fee_account",auction_house_fee_account);
//        map.put("auction_house_treasury",auction_house_treasury);
//        map.put("treasury_withdrawal_destination",treasury_withdrawal_destination);
//        map.put("fee_withdrawal_destination",fee_withdrawal_destination);
//        map.put("seller_fee_basis_points",seller_fee_basis_points);
//        map.put(" requires_sign_off", requires_sign_off);
//        map.put("can_change_sale_price",can_change_sale_price);
//
//        String url = GetAPIRoot() + MirrorUrl.URL_QUERY_MARKET_PLACE;
//        checkParamsAndGet(url,map, mirrorCallback);
//    }

    public void sdkSimpleCheck(OnCheckSDKUseable callback){
        if(apiKey.equals("")){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                callback.OnUnUsable();
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey.equals("")){
            logFlow("Must set app id first!");
            callback.OnUnUsable();
            return;
        }
        callback.OnChecked();
    }

    public void checkSDKInited(OnCheckSDKUseable callback){
        if(apiKey.equals("")){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey.equals("")){
            logFlow("Must set app id first!");
            return;
        }

        if(accessToken.equals("")){
            logFlow("No access token,start get flow");
            GetAccessToken(mActivity, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    callback.OnChecked();
                }
            }, new MSimpleCallback() {
                @Override
                public void callback() {
                    callback.OnUnUsable();
                }
            },false);
            return;
        }else{
            callback.OnChecked();
        }
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

    public void GetAccessToken(Activity activityContext, MirrorCallback mirrorCallback,MSimpleCallback failCallback ,boolean autoLogin){
        logFlow("ready to get access token,now refreshToken is:"+refreshToken);
        if(refreshToken.equals("")){
            logFlow("No refresh token,jump to login page...");
            if(autoLogin) autoOpenLogin(mirrorCallback);
            else {
                failCallback.callback();
            }
            return;
        }

        if(apiKey.equals("")){
            apiKey = getSavedString(activityContext, localKeyAPIKey);
        }

        if(apiKey.equals("")){
            logFlow("Must set app id first!");
            return;
        }

        String url = MirrorUrl.getActionRoot() + MirrorUrl.URL_REFRESH_TOKEN;
        new Thread(httpGetRunnableWithRefreshToken(url,refreshToken, new MirrorCallback() {
            @Override
            public void callback(String result) {

                if(activityContext == null){
                    JSONObject itJson = null;
                    try {
                        itJson = new JSONObject(result);
                        int code = (int) itJson.get("code");
                        if (code != 0){
                            logFlow("You have no authorization to visit api."+result);
                            if(autoLogin) autoOpenLogin(mirrorCallback);
                            else {
                                failCallback.callback();
                            }
                        }else{
                            String jwt = itJson.getJSONObject("data").getString("access_token");
                            String newRefreshToken = itJson.getJSONObject("data").getString("refresh_token");
                            String wallet = itJson.getJSONObject("data").getJSONObject("user").getString("sol_address");
                            saveRefreshToken(newRefreshToken);
                            saveString(localKeyWalletAddress,wallet);
                            accessToken = jwt;
//                            mWalletAddress = wallet;
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
                                    logFlow("You have no authorization to visit api."+result);
                                    if(autoLogin) autoOpenLogin(mirrorCallback);
                                    else {
                                        failCallback.callback();
                                    }
                                }else{
                                    String accessToken = itJson.getJSONObject("data").getString("access_token");
                                    String newRefreshToken = itJson.getJSONObject("data").getString("refresh_token");
                                    String wallet = itJson.getJSONObject("data").getJSONObject("user").getString("sol_address");
                                    saveRefreshToken(newRefreshToken);
                                    saveString(localKeyWalletAddress,wallet);
                                    accessToken = accessToken;
//                                    mWalletAddress = wallet;
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
        logFlow("==>Post url:"+url);
        logFlow("==>Post json:"+data);
        logFlow("==>Post accessToken:"+accessToken);
        logFlow("==>Post apiKey:"+apiKey);
        logFlow("==>Post xAuthKey:"+xAuthToken);

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

                logFlow("result:"+String.valueOf(textBuilder));
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

                String result = String.valueOf(textBuilder);
                logFlow("result:"+result);
                if(mirrorCallback != null) mirrorCallback.callback(result);
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
        if(apiKey.equals("")){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey.equals("")){
            logFlow("Must set app id first!");
            return;
        }

        if(accessToken.equals("")){
            logFlow("No access token,start get flow");
            GetAccessToken(mActivity, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    doGet(url, params, mirrorCallback);
                }
            }, new MSimpleCallback() {
                @Override
                public void callback() {
                    logFlow("SDK has not been inited!");
                }
            }, false);
        }else{
//            if(mWalletAddress.equals("") && null != mActivity){
//                mWalletAddress = getSavedString(mActivity,localKeyWalletAddress);
//                if(mWalletAddress.equals("")){
//                    logFlow("Must get mWalletAddress first!");
//                }
//            }
            doGet(url,params, mirrorCallback);
        }
    }

    public void checkParamsAndPost(String url, String data, MirrorCallback mirrorCallback){
        if(apiKey.equals("")){
            if(mActivity == null){
                logFlow("Must init sdk first!");
                return;
            }
            apiKey = getSavedString(mActivity, localKeyAPIKey);
        }
        if(apiKey.equals("")){
            logFlow("Must set app id first!");
            return;
        }

        if(accessToken.equals("")){
            logFlow("Post:No access token,start get flow");
            GetAccessToken(mActivity, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    doPost(url, data, mirrorCallback);
                }
            }, new MSimpleCallback() {
                @Override
                public void callback() {
                    logFlow("SDK has not been inited yet!");
                }
            }, false);
        }else{
//            if(mWalletAddress.equals("")&& null != mActivity){
//                mWalletAddress = getSavedString(mActivity,localKeyWalletAddress);
//                if(mWalletAddress.equals("")){
//                    logFlow("Must get mWalletAddress first!");
//                }
//            }
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
            if(accessToken != null && accessToken != "" && accessToken != "undefined") urlConn.setRequestProperty("Authorization","Bearer "+accessToken);
            urlConn.setRequestMethod("GET");

            logFlow("http get code:"+urlConn.getResponseCode());
            if(urlConn.getResponseCode() == MirrorResCode.NO_RESOURCES){
                CommonResponse res = new CommonResponse();
                res.code = MirrorResCode.NO_RESOURCES;
                urlConn.disconnect();
                return MirrorGsonUtils.getInstance().toJson(res);
            }else if(urlConn.getResponseCode() == MirrorResCode.FORBIDDEN){
                CommonResponse res = new CommonResponse();
                res.code = MirrorResCode.FORBIDDEN;
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
        SetXAuthToken(xAuthToken);
        if(safeFlowCb != null){
            safeFlowCb.callback("");
            safeFlowCb = null;
        }

        if(updateAuthTokenCb != null){
            updateAuthTokenCb.callback(xAuthToken);
        }
    }

    @JavascriptInterface
    public void walletLogout(){
        clearCache();
        if(cbWalletLogout != null){
            logFlow("wallet passivity login success(js).");
            cbWalletLogout.callback("");
            cbWalletLogout = null;
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
//                    mWalletAddress = aaa.user.sol_address;
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
                if(cbConstantLoginString != null){
                    logFlow("constant login string callback called:"+dataJsonStr);
                    cbConstantLoginString.callback(dataJsonStr);
                }
            }
        });
    }

    @JavascriptInterface
    public void closePage() {
        parentDialog.dismiss();
    }

    public void saveRefreshToken(String refreshToken){
        if(refreshToken.equals("")) return;

        logFlow("save refresh token to local:"+refreshToken);
        SetRefreshToken(refreshToken);
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

    final public static void logWarn(String value){
        Log.w("MirrorSDK Warn"+MirrorConstant.Version,value);
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