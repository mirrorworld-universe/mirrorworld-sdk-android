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
import android.widget.Toast;

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

public class MirrorSDKJava {
    //user custom
    private boolean debugMode = true;
    private String appId = "";

    //secret
    private String accessToken = "";
    private String mWalletAddress = "";
    private final String version = "0.0.1";

    private String appName = "MirrorWorldMobileSDK";
    private String delegateName = "mwm";
    private WebView webViewPopUp = null;
    private AlertDialog builder = null;
    private Context globalContext = null;
    private Activity activityContext = null;
    private String urlAuth = "https://auth.mirrorworld.fun/";
    //    private String urlAuth = "https://auth-staging.mirrorworld.fun/";
    //private String apiRoot = "https://api.mirrorworld.fun/";
    private String apiRoot = "https://api-staging.mirrorworld.fun/";
    private AlertDialog parentDialog = null;
    private WebView webView = null;
    private String userAgent = null;

    private String strLoginSuccess = "Login success!";
    private String localFileKey = "mirror_local_storage";
    private String localKeyRefreshToken = "mirror_refresh_token";
    private String localKeyAppId = "mirror_app_id";
    private String localKeyWalletAddress = "mirror_wallet_address";

    private MirrorCallback cbLogin = null;

    //get url
    private final String urlRefreshToken = apiRoot + "v1/auth/refresh-token";
    private final String urlQueryUser = apiRoot + "v1/auth/user";
    private final String urlQueryNFTDetail = apiRoot + "v1/solana/nft/";
    //post url
    private final String urlFetchMultiNFTsDataByMintAddress = apiRoot + "v1/solana/nft/mints";
    private final String urlFetchMultiNFTsDataByCreatorAddress = apiRoot + "v1/solana/nft/creators";
    private final String urlFetchMultiNFTsDataByUpdateAuthorityAddress = apiRoot + "v1/solana/nft/update-authorities";
    private final String urlMintNFTCollection = apiRoot + "v1/solana/mint/nft";
    private final String urlMintTopLevelCollection = apiRoot + "v1/solana/mint/collection";
    private final String urlMintLowerLevelCollection = apiRoot + "v1/solana/mint/sub-collection";

    // add
    private final String urlFetchmultipleNFTs = apiRoot+"v1/solana/nft/owners";


    // v1/solana/activity/:mint_address  path params
    private final String urlFetchActivity = apiRoot+ "v1/solana/activity/";

    //


    //about market

    private final String urlListNFTOnTheMarketplace = apiRoot + "v1/solana/marketplace/list";
    private final String urlUpdateListingOfNFTOnTheMarketplace = apiRoot + "v1/solana/marketplace/update";
    private final String urlBuyNFTOnTheMarketplace = apiRoot + "v1/solana/marketplace/buy";
    private final String urlCancelListingOfNFTOnTheMarketplace = apiRoot + "v1/solana/marketplace/cancel";
    private final String urlTransferNFTToAnotherSolanaWallet = apiRoot + "v1/solana/marketplace/transfer";

    //wallet api url
    private final String urlMe = apiRoot + "v1/auth/me";


    private final String TransferSQL = apiRoot+"v1/wallet/transfer-sol";

    private final String TransferToken = apiRoot+"v1/wallet/transfer-token";

    private  final String GetWalletToken =apiRoot+"v1/wallet/tokens";

    private final String GetWalletTransactions =apiRoot+"v1/wallet/transactions";





    private MirrorSDKJava(){
    }

    private static volatile MirrorSDKJava instance;

    public static MirrorSDKJava getInstance(){
        if (instance == null){
            synchronized(MirrorSDKJava.class){
                instance = new MirrorSDKJava();
            }
        }
        return instance;
    }

    public void InitSDK(Activity activityContext){
        logFlow("Mirror SDK inited!");
        this.activityContext = activityContext;
    }

    //open login popup window
    public void StartLogin(){
        if(appId == ""){
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }
        logFlow("Start login,sdk version is:"+version);
        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
        AlertDialog dialog = builder.create();
        parentDialog = dialog;
        dialog.setCanceledOnTouchOutside(false);
        WebView wv = new WebView(activityContext);
        setWebView(activityContext,wv);
        dialog.setButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                wv.destroy();
                dialog.dismiss();
            }
        });

        RelativeLayout layout = getPopupWindowLayout(activityContext);
        layout.addView(wv);
        dialog.setView(layout);
        dialog.show();
    }
    public void StartLoginWithCallback(MirrorCallback mirrorCallback){
        StartLogin();
        cbLogin = mirrorCallback;
    }

    //set if use debug mode
    public void SetDebug(boolean debug){
        debugMode = debug;
    }

    public void SetAppID(String id){
        appId = id;
        if(activityContext != null){
            saveString(localKeyAppId,appId);
        }
    }



    // wallet  ==========================================
    public void APIGetWalletAddress(MirrorCallback mirrorCallback){
        if(activityContext == null){
            logFlow("Must call InitSDK() first.");
            mirrorCallback.callback("");
            return;
        }
        if(mWalletAddress == ""){
            mWalletAddress = getSavedString(activityContext,localKeyWalletAddress);
        }
        checkParamsAndGet(urlMe, null, new MirrorCallback() {
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

    public void APIPostTransferSQL(String to_publickey,int amount,MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", to_publickey);
            jsonObject.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(TransferSQL,data,getHandlerCallback(mirrorCallback));

    }



    public void APIPostTransferToken(String to_publickey,int amount,String token_mint,int decimals,MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to_publickey", to_publickey);
            jsonObject.put("amount", amount);
            jsonObject.put("token_mint", token_mint);
            jsonObject.put("decimals", decimals);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(TransferToken,data,getHandlerCallback(mirrorCallback));
    }


    public void APIGetWalletToken(MirrorCallback mirrorCallback){
        if(appId == ""){
            if(activityContext == null){
                logFlow("Must init sdk first!");
                return;
            }
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }

        checkParamsAndGet(GetWalletToken,null, mirrorCallback);
    }

    public void APIGetWalletTransactions(String limit,String before,MirrorCallback mirrorCallback){
        if(appId == ""){
            if(activityContext == null){
                logFlow("Must init sdk first!");
                return;
            }
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }
        HashMap<String,String> map = new HashMap<String,String>();

        map.put("limit",limit);
        map.put("before",before);
        checkParamsAndGet(GetWalletTransactions,map, mirrorCallback);

    }



    // todo add request methods

    //===========================================================

    public void GetAccessToken(Activity activityContext, MirrorCallback mirrorCallback){
        String refreshToken = getRefreshToken(activityContext);
        logFlow("ready to get access token,now refreshToken is:"+refreshToken);
        if(refreshToken == ""){
            StartLogin();
            return;
        }

        if(appId == ""){
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }
        new Thread(httpGetRunnableWithRefreshToken(urlRefreshToken,refreshToken, new MirrorCallback() {
            @Override
            public void callback(String result) {
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
        })).start();
    }

    public void APIQueryUser(String userEmail, MirrorCallback mirrorCallback){
        if(appId == ""){
            if(activityContext == null){
                logFlow("Must init sdk first!");
                return;
            }
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("email",userEmail);
        checkParamsAndGet(urlQueryUser,map, mirrorCallback);
    }

    public void APIFetchSingleNFT(String mint_address, MirrorCallback mirrorCallback){
        if(appId == ""){
            if(activityContext == null){
                logFlow("Must init sdk first!");
                return;
            }
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }

        checkParamsAndGet(urlQueryNFTDetail+mint_address,null, mirrorCallback);
    }

    public void doGet(String url, Map<String, String> params, MirrorCallback mirrorCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String resultStr = httpGetW(url,params,"UTF-8");
                activityContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logFlow("Get Result is:"+resultStr);
                        mirrorCallback.callback(resultStr);
                    }
                });
            }
        }).start();
    }

    public void APIMintNewNFTOnCollection(String collection_mint, String name, String symbol, String url, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("collection_mint", collection_mint);
            jsonObject.put("name", name);
            jsonObject.put("symbol", symbol);
            jsonObject.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(urlMintNFTCollection,data,getHandlerCallback(mirrorCallback));
    }

    public void APIMintNewTopLevelCollection(String name, String symbol, String url, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("symbol", symbol);
            jsonObject.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(urlMintTopLevelCollection,data,getHandlerCallback(mirrorCallback));
    }

    public void APIMintNewLowerLevelCollection(String collection_mint, String name, String symbol, String url, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("collection_mint", collection_mint);
            jsonObject.put("symbol", symbol);
            jsonObject.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(urlMintLowerLevelCollection,data,getHandlerCallback(mirrorCallback));
    }

    public void APITransferNFTToAnotherSolanaWallet(String mint_address, String to_wallet_address, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("to_wallet_address", to_wallet_address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(urlTransferNFTToAnotherSolanaWallet,data,getHandlerCallback(mirrorCallback));
    }


    // add
    public void APIPostFetchMultiple(List<String> owners,int limit,int offset,MirrorCallback mirrorCallback){

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

        checkParamsAndPost(urlFetchmultipleNFTs,data,getHandlerCallback(mirrorCallback));
    }


    public void APIGetFetchActivities(String mint_address,MirrorCallback mirrorCallback){

        checkParamsAndGet(urlFetchActivity+mint_address,null, mirrorCallback);
    }


    public void APICancelListingOfNFT(String mint_address, Double price, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(urlCancelListingOfNFTOnTheMarketplace,data,getHandlerCallback(mirrorCallback));
    }

    public void APIBuyNFT(String mint_address, Double price, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(urlBuyNFTOnTheMarketplace,data,getHandlerCallback(mirrorCallback));
    }

    public void APIUpdateListingOfNFT(String mint_address, Double price, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String  data = jsonObject.toString();

        checkParamsAndPost(urlUpdateListingOfNFTOnTheMarketplace,data,getHandlerCallback(mirrorCallback));
    }

    public void APIListNFTOnTheMarketplace(String mint_address, Double price, MirrorCallback mirrorCallback){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mint_address", mint_address);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject.toString();

        checkParamsAndPost(urlListNFTOnTheMarketplace,data,getHandlerCallback(mirrorCallback));
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
            conn.setRequestProperty("x-api-key",appId);
            conn.setRequestProperty("Authorization","Bearer "+accessToken);
            OutputStream out = conn.getOutputStream();
            //写数据
            out.write(data.getBytes(Charset.forName("UTF-8")));
            //4.获取响应码
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());

                StringBuilder textBuilder = new StringBuilder();
                try (Reader reader = new BufferedReader(new InputStreamReader
                        (inputStream, Charset.forName("UTF-8")))) {
                    int c = 0;
                    while ((c = reader.read()) != -1) {
                        textBuilder.append((char) c);
                    }
                }
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

    public void APIFetchNFTsByUpdateAuthorityAddress(List<String> update_authorities,Double limit,Double offset, MirrorCallback mirrorCallback){
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

        checkParamsAndPost(urlFetchMultiNFTsDataByUpdateAuthorityAddress,data,getHandlerCallback(mirrorCallback));
    }

    public void APIFetchNFTsByCreatorAddress(List<String> creators,Double limit,Double offset, MirrorCallback mirrorCallback){
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

        checkParamsAndPost(urlFetchMultiNFTsDataByCreatorAddress,data,getHandlerCallback(mirrorCallback));
    }

    public void APIFetchNFTsByMintAddress(List<String> mint_addresses, MirrorCallback mirrorCallback){
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

        checkParamsAndPost(urlFetchMultiNFTsDataByMintAddress, data, getHandlerCallback(mirrorCallback));
    }

    private MirrorCallback getHandlerCallback(MirrorCallback mirrorCallback){
        return new MirrorCallback() {
            @Override
            public void callback(String result) {
                activityContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mirrorCallback != null) mirrorCallback.callback(result);
                    }
                });
            }
        };
    }

    private void checkParamsAndGet(String url,  Map<String, String> params, MirrorCallback mirrorCallback){
        if(appId == ""){
            if(activityContext == null){
                logFlow("Must init sdk first!");
                return;
            }
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }

        if(accessToken == ""){
            logFlow("No access token,start get flow");
            GetAccessToken(activityContext, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    doGet(url,params, mirrorCallback);
                }
            });
        }else{
            if(mWalletAddress == ""){
                mWalletAddress = getSavedString(activityContext,localKeyWalletAddress);
                if(mWalletAddress == ""){
                    logFlow("Must get mWalletAddress first!");
                }
            }
            doGet(url,params, mirrorCallback);
        }
    }

    private void checkParamsAndPost(String url, String data, MirrorCallback mirrorCallback){
        if(appId == ""){
            if(activityContext == null){
                logFlow("Must init sdk first!");
                return;
            }
            appId = getSavedString(activityContext,localKeyAppId);
        }
        if(appId == ""){
            logFlow("Must set app id first!");
            return;
        }

        if(accessToken == ""){
            logFlow("No access token,start get flow");
            GetAccessToken(activityContext, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    accessToken = result;
                    doPost(url,data, mirrorCallback);
                }
            });
        }else{
            if(mWalletAddress == ""){
                mWalletAddress = getSavedString(activityContext,localKeyWalletAddress);
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
                        activityContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mirrorCallback != null) mirrorCallback.callback(result);
                            }
                        });
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

                   logFlow("appId"+appId);
                    url = new URL(urlRefreshToken);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("x-refresh-token",accessToken);
                    urlConnection.setRequestProperty("x-api-key",appId);
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
        logFlow("http get(x-api-key):"+appId);
        logFlow("http get Authorization:"+accessToken);
        try {
            URL url = new URL(strUrlPath);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(5000);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Accept","application/json");
            urlConn.setRequestProperty("x-api-key",appId);
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
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer;
    }

    //utils
    private void setWebView(Context context,WebView webView){
        this.webView = webView;
        webView.setWebViewClient(new WebViewClient());
        final String finalUrl = urlAuth +appId;
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
    public void setRefreshToken(String refreshToken) {
        Toast.makeText(globalContext, strLoginSuccess, Toast.LENGTH_SHORT).show();
        saveRefreshToken(refreshToken);
        parentDialog.dismiss();
        if(cbLogin != null) cbLogin.callback("login success");
    }

    @JavascriptInterface
    public void setLoginResponse(String dataJsonStr) {
        logFlow("receive login response:"+dataJsonStr);
        try {
            JSONObject jsonObject = new JSONObject(dataJsonStr);
            String token = jsonObject.getString("refresh_token");
            saveRefreshToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        parentDialog.dismiss();
        if(cbLogin != null) cbLogin.callback(dataJsonStr);
    }

    @JavascriptInterface
    public void closePage() {
        parentDialog.dismiss();
    }

    private void saveRefreshToken(String refreshToken){
        logFlow("save refresh token to local:"+refreshToken);
        //获得SharedPreferences的实例 sp_name是文件名
        SharedPreferences sp = activityContext.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
        //获得Editor 实例
        SharedPreferences.Editor editor = sp.edit();
        //以key-value形式保存数据
        editor.putString(localKeyRefreshToken, refreshToken);
        //apply()是异步写入数据
//        editor.apply()
        //commit()是同步写入数据
        editor.commit();
    }

    private void saveString(String key,String value){
        //获得SharedPreferences的实例 sp_name是文件名
        SharedPreferences sp = activityContext.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
        //获得Editor 实例
        SharedPreferences.Editor editor = sp.edit();
        //以key-value形式保存数据
        editor.putString(key, value);
        //apply()是异步写入数据
//        editor.apply()
        //commit()是同步写入数据
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
            builder = new AlertDialog.Builder(activityContext).create();
            builder.setTitle("");
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
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                cookieManager.setAcceptThirdPartyCookies(webViewPopUp, true);
                cookieManager.setAcceptThirdPartyCookies(webView, true);
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