//package com.mirror.sdk;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Looper;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.webkit.CookieManager;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class MirrorSDKJava {
//    //user custom
//    private boolean debugMode = true;
//    private String appId = "";
//
//    //secret
//    private String accessToken = "";
//    private String mWalletAddress = "";
//    private final String version = "0.0.1";
//
//    private String appName = "MirrorWorldMobileSDK";
//    private String delegateName = "mwm";
//    private WebView webViewPopUp = null;
//    private AlertDialog builder = null;
//    private Context globalContext = null;
//    private Activity activityContext = null;
//    private String urlAuth = "https://auth.mirrorworld.fun/login";
//    private AlertDialog parentDialog = null;
//    private WebView webView = null;
//    private String userAgent = null;
//
//    private String strLoginSuccess = "Login success!";
//    private String localFileKey = "mirror_local_storage";
//    private String localKeyRefreshToken = "mirror_refresh_token";
//    private String localKeyAppId = "mirror_app_id";
//    private String localKeyWalletAddress = "mirror_wallet_address";
//
//    //get url
//    private final String urlRefreshToken = "https://api-staging.mirrorworld.fun/v1/auth/refresh-token";
//    private final String urlQueryUser = "https://api-staging.mirrorworld.fun/v1/auth/user";
//    private final String urlQueryNFTDetail = "https://api-staging.mirrorworld.fun/v1/solana/nft/";
//    //post url
//    private final String urlFetchMultiNFTsDataByMintAddress = "https://api-staging.mirrorworld.fun/v1/solana/nft/mints";
//    private final String urlFetchMultiNFTsDataByCreatorAddress = "https://api-staging.mirrorworld.fun/v1/solana/nft/creators";
//    private final String urlFetchMultiNFTsDataByUpdateAuthorityAddress = "https://api-staging.mirrorworld.fun/v1/solana/nft/update-authorities";
//    private final String urlMintNFTCollection = "https://api-staging.mirrorworld.fun/v1/solana/mint/nft";
//    private final String urlMintTopLevelCollection = "https://api-staging.mirrorworld.fun/v1/solana/mint/collection";
//    private final String urlMintLowerLevelCollection = "https://api-staging.mirrorworld.fun/v1/solana/mint/sub-collection";
//
//    //about market
//    private final String urlListNFTOnTheMarketplace = "https://api-staging.mirrorworld.fun/v1/solana/marketplace/list";
//    private final String urlUpdateListingOfNFTOnTheMarketplace = "https://api-staging.mirrorworld.fun/v1/solana/marketplace/update";
//    private final String urlBuyNFTOnTheMarketplace = "https://api-staging.mirrorworld.fun/v1/solana/marketplace/buy";
//    private final String urlCancelListingOfNFTOnTheMarketplace = "https://api-staging.mirrorworld.fun/v1/solana/marketplace/cancel";
//    private final String urlTransferNFTToAnotherSolanaWallet = "https://api-staging.mirrorworld.fun/v1/solana/marketplace/transfer";
//
//
//    private MirrorSDKJava(){
//    }
//
//    private static volatile MirrorSDKJava instance;
//
//    public static MirrorSDKJava getInstance(){
//        if (instance == null){
//            synchronized(MirrorSDKJava.class){
//                instance = new MirrorSDKJava();
//            }
//        }
//        return instance;
//    }
//
//    public void InitSDK(Activity activityContext){
//        logFlow("Mirror SDK inited!");
//        this.activityContext = activityContext;
//    }
//
//    //open login popup window
//    public void StartLogin(Activity activityContext){
//        logFlow("Start login,sdk version is:"+version);
//        this.activityContext = activityContext;
//        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
//        AlertDialog dialog = builder.create();
//        parentDialog = dialog;
//        dialog.setCanceledOnTouchOutside(false);
//        WebView wv = new WebView(activityContext);
//        setWebView(activityContext,wv);
//
//        RelativeLayout layout = getPopupWindowLayout(activityContext);
//        layout.addView(wv);
//        dialog.setView(layout);
//        dialog.show();
//    }
//
//    //set if use debug mode
//    public void SetDebug(boolean debug){
//        debugMode = debug;
//    }
//
//    public void SetAppID(Context context,String id){
//        appId = id;
//        if(globalContext != null){
//            saveString(context,localKeyAppId,appId);
//        }
//    }
//
//    public void GetAccessToken(Activity activityContext, MirrorCallback mirrorCallback){
//        String refreshToken = getRefreshToken(activityContext);
//        logFlow("ready to get access token,now refreshToken is:"+refreshToken);
//        if(refreshToken == ""){
//            StartLogin(activityContext);
//            return;
//        }
//
//        if(appId == ""){
//            appId = getSavedString(globalContext,localKeyAppId);
//        }
//        if(appId == ""){
//            logFlow("Must set app id first!");
//            return;
//        }
//        Thread(httpGetRunnableWithRefreshToken(urlRefreshToken,refreshToken) {
//            Handler(Looper.getMainLooper()).post {
//                JSONObject itJson = new JSONObject(it);
//                var code = itJson.get("code")
//                if (code != 0){
//                    logFlow("You have no authorization to visit api,now popup login window."+it)
//                    StartLogin(activityContext)
//                }else{
//                    var accessToken:String = itJson.getJSONObject("data").getString("access_token")
//                    var newRefreshToken:String = itJson.getJSONObject("data").getString("refresh_token")
//                    var wallet:String = itJson.getJSONObject("data").getJSONObject("user").getString("sol_address")
//                    saveRefreshToken(activityContext,newRefreshToken)
//                    saveString(activityContext,localKeyWalletAddress,wallet)
//                    this.accessToken = accessToken
//                    this.mWalletAddress = wallet
//                    logFlow("Access token success!"+accessToken)
//                    logFlow("Wallet is:"+wallet)
//                    if(callback != null){
//                        callback(accessToken)
//                    }
//                }
//            }
//        }).start()
//    }
//
//    fun APIQueryUser(userEmail:String,callback: ((String) -> Unit)? = null){
//        if(appId == ""){
//            if(activityContext == null){
//                logFlow("Must init sdk first!")
//                return
//            }
//            appId = getSavedString(activityContext,localKeyAppId)
//        }
//        if(appId == ""){
//            logFlow("Must set app id first!")
//            return
//        }
//
//        val mapp = mapOf("email" to userEmail)
//        Thread(Runnable {
//            var resultStr = httpGetW(urlQueryUser,mapp,"UTF-8")
//            Handler(Looper.getMainLooper()).post {
//                callback?.let { it(resultStr) }
//            }
//        }).start()
//    }
//
//    fun APIFetchSingleNFT(mint_address:String,callback: ((String) -> Unit)? = null){
//        if(appId == ""){
//            if(activityContext == null){
//                logFlow("Must init sdk first!")
//                return
//            }
//            appId = getSavedString(activityContext,localKeyAppId)
//        }
//        if(appId == ""){
//            logFlow("Must set app id first!")
//            return
//        }
//
//        Thread(Runnable {
//            var resultStr = httpGetW(urlQueryNFTDetail+mint_address,null,"UTF-8")
//            Handler(Looper.getMainLooper()).post {
//                logFlow("Fetch NFT Result is:"+resultStr)
//                callback?.let { it(resultStr) }
//            }
//        }).start()
//    }
//
//    fun APIMintNewNFTOnCollection(collection_mint:String,name:String,symbol:String,url:String,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("collection_mint", collection_mint)
//        jsonObject.put("name", name)
//        jsonObject.put("symbol", symbol)
//        jsonObject.put("url", url)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlMintNFTCollection,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APIMintNewTopLevelCollection(name:String,symbol:String,url:String,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("name", name)
//        jsonObject.put("symbol", symbol)
//        jsonObject.put("url", url)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlMintTopLevelCollection,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APIMintNewLowerLevelCollection(collection_mint:String,name:String,symbol:String,url:String,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("collection_mint", collection_mint)
//        jsonObject.put("name", name)
//        jsonObject.put("symbol", symbol)
//        jsonObject.put("url", url)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlMintLowerLevelCollection,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APITransferNFTToAnotherSolanaWallet(mint_address:String,to_wallet_address:String,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("mint_address", mint_address)
//        jsonObject.put("to_wallet_address", to_wallet_address)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlTransferNFTToAnotherSolanaWallet,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APICancelListingOfNFT(mint_address:String,price:Number,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("mint_address", mint_address)
//        jsonObject.put("price", price)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlCancelListingOfNFTOnTheMarketplace,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APIBuyNFT(mint_address:String,price:Number,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("mint_address", mint_address)
//        jsonObject.put("price", price)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlBuyNFTOnTheMarketplace,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APIUpdateListingOfNFT(mint_address:String,price:String,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("mint_address", mint_address)
//        jsonObject.put("price", price)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlUpdateListingOfNFTOnTheMarketplace,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APIListNFTOnTheMarketplace(mint_address:String,price:Double,callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        jsonObject.put("mint_address", mint_address)
//        jsonObject.put("price", price)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlListNFTOnTheMarketplace,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun post(url: String,data:String,callback: ((String) -> Unit)?) {
//        logFlow("post json:"+data)
//        try {
//            //HttpURLConnection
//            //1.实例化一个URL对象
//            val url = URL(url)//URL("http://www.imooc.com/api/okhttp/postmethod")
//
//            //2.获取HttpURLConnection实例
//            val conn = url.openConnection() as HttpURLConnection
//
//            //3.设置和请求相关的属性
//            //请求方式
////            conn.requestMethod = "POST"
//            //设置允许输出
//            conn.doOutput = true
//            //请求超时时长
//            conn.connectTimeout = 6000
//            conn.doInput = true
//            conn.doOutput = true
//            conn.requestMethod = "POST"
//            conn.useCaches = false//使用post方式不能用缓存
//            //设置请求数据的类型
//            conn.setRequestProperty("Content-Type", "application/json")
//            conn.setRequestProperty("Accept","application/json")
//            conn.setRequestProperty("x-api-key",appId)
//            conn.setRequestProperty("Authorization","Bearer "+accessToken)
//            //获取输出流(请求正文)
//            val out = conn.outputStream
//            //写数据
//            out.write(data.toByteArray())//("account=$account".toByteArray())
//            //4.获取响应码
//            if (conn.responseCode != HttpURLConnection.HTTP_OK) {
//                //5.判断响应码并获取响应数据
//                //获取响应的流
//                val inputStream = conn.inputStream
//                val b = ByteArray(1024)
//                var len = 0
//                val baos = ByteArrayOutputStream()
//                //在循环中读取输入的流
//                //in.read(b); //该方法返回值是int类型数据，代表的是实际读到的数据长度
//                while (inputStream.read(b).also { len = it } > -1) {
//                    //将字节数组里面的内容存/写入缓存流
//                    //参数1：待写入的数组
//                    //参数2：起点
//                    //参数3：长度
//                    baos.write(b, 0, len)
//                }
//                val s = String(baos.toByteArray())
//                logFlow(s)
//                if(callback != null) callback(s)
//            }else{
//                logFlow(conn.responseCode.toString())
//                if(callback != null) callback(conn.responseCode.toString())
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun APIFetchNFTsByUpdateAuthorityAddress(update_authorities:List<String>,limit:Number,offset:Number, callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        var jsonArray = JSONArray()
//        for ( tag in update_authorities) {
//            jsonArray.put(tag);
//        }
//        jsonObject.put("update_authorities", jsonArray)
//        jsonObject.put("limit", limit)
//        jsonObject.put("offset", offset)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlFetchMultiNFTsDataByUpdateAuthorityAddress,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APIFetchNFTsByCreatorAddress(creators:List<String>,limit:Number,offset:Number, callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        var jsonArray = JSONArray()
//        for ( tag in creators) {
//            jsonArray.put(tag);
//        }
//        jsonObject.put("creators", jsonArray)
//        jsonObject.put("limit", limit)
//        jsonObject.put("offset", offset)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlFetchMultiNFTsDataByCreatorAddress,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun APIFetchNFTsByMintAddress(mint_addresses:List<String>, callback: ((String) -> Unit)?=null){
//        var jsonObject = JSONObject()
//        var jsonArray = JSONArray()
//        for ( tag in mint_addresses) {
//            jsonArray.put(tag);
//        }
//        jsonObject.put("mint_addresses", jsonArray)
//        var data = jsonObject.toString()
//
//        checkParamsAndPost(urlFetchMultiNFTsDataByMintAddress,data){
//            Handler(Looper.getMainLooper()).post {
//                if(callback != null) callback(it)
//            }
//        }
//    }
//
//    fun checkParamsAndPost(url:String,data:String,callback: ((String) -> Unit)?){
//        if(appId == ""){
//            if(activityContext == null){
//                logFlow("Must init sdk first!")
//                return
//            }
//            appId = getSavedString(activityContext,localKeyAppId)
//        }
//        if(appId == ""){
//            logFlow("Must set app id first!")
//            return
//        }
//
//        if(accessToken == ""){
//            logFlow("No access token,start get flow")
//            GetAccessToken(activityContext){
//                accessToken = it
//
//                Thread(Runnable {
//                    post(url,data){
//                        Handler(Looper.getMainLooper()).post {
//                            if(callback != null) callback(it)
//                        }
//                    }
//                }).start()
//
//            }
//        }else{
//            if(mWalletAddress == ""){
//                mWalletAddress = getSavedString(activityContext,localKeyWalletAddress)
//                if(mWalletAddress == ""){
//                    logFlow("Must get mWalletAddress first!")
//                }
//            }
//            Thread(Runnable {
//                post(url,data){
//                    Handler(Looper.getMainLooper()).post {
//                        if(callback != null) callback(it)
//                    }
//                }
//            }).start()
//        }
//    }
//
//    fun dealResponseResult(inputStream: InputStream): String {
//        var resultData: String? = null      //存储处理结果
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        val data = ByteArray(1024)
//        var len = 0
//        try {
//            while (inputStream.read(data).apply { len = this } != -1) {
//                byteArrayOutputStream.write(data, 0, len)
//            }
//        } catch (e:IOException) {
//            e.printStackTrace()
//        }
//
//        resultData = String(byteArrayOutputStream.toByteArray())
//        return resultData
//    }
//
//    private Runnable httpGetRunnableWithRefreshToken(urlRefreshToken:String,accessToken:String,MirrorCallback mirrorCallback) {
//        return new Runnable() {
//            @Override
//            public void run() {
//                val url = URL(URI(urlRefreshToken).toASCIIString())
//                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection;
//                urlConnection.setRequestProperty("x-refresh-token",accessToken);
//                urlConnection.setRequestProperty("X-API-Key",appId);
//                urlConnection.requestMethod = "GET";
//                urlConnection.connect()
//                val status: Int = urlConnection.getResponseCode()
//                String r = try {
//                    val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)
//                    val resp = inputStream.readBytes();
//                    String(resp, Charsets.UTF_8);
//                } catch (Error err) {
//                    "Network Error";
//                } finally {
//                    urlConnection.disconnect();
//                }
//                mirrorCallback.callback(r);
//            }
//        }
//    }
//
//    fun httpGetW(strUrlPath: String, params:Map<String, String>? = null, encode: String): String {
//        var strUrlPath = strUrlPath
//        /* byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体*/
//        /* String target="http://emb.mobi/register";*/
//        var result: String? = null
//        if(params != null){
//            val append_url = getRequestData(params, encode).toString()
//            strUrlPath = strUrlPath + "?" + append_url
//        }
//
//        logFlow("http get:"+strUrlPath)
//        try {
//            val url = URL(strUrlPath)
//            val urlConn = url.openConnection() as HttpURLConnection
//            urlConn.connectTimeout = 5000
//            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//            urlConn.setRequestProperty("Accept","application/json")
//            urlConn.setRequestProperty("x-api-key",appId)
//
//            val `in` = InputStreamReader(urlConn.inputStream)
//
//            val buffer = BufferedReader(`in`)
//            var inputLine: String? = null
//
//            result = ""
//
//            while (buffer.readLine().apply { inputLine = this } != null) {
//                result += inputLine!! + "\n"
//            }
//
//            `in`.close()
//            urlConn.disconnect()
//        } catch (e: MalformedURLException) {
//            e.printStackTrace()
//        } catch (ioe: IOException) {
//            ioe.printStackTrace()
//            return "err:" + ioe.message.toString()
//        }
//
//        return result!!
//    }
//
//    private fun getRequestDataObject(params: Map<String, Objects>, encode: String): StringBuffer {
//        val stringBuffer = StringBuffer()        //存储封装好的请求体信息
//        try {
//            for ((key, value) in params) {
//                stringBuffer.append(key)
//                        .append("=")
//                        .append(value)
////                    .append(URLEncoder.encode(value, encode))
//                        .append("&")
//            }
//            stringBuffer.deleteCharAt(stringBuffer.length - 1)    //删除最后的一个"&"
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return stringBuffer
//    }
//
//    private fun getRequestData(params: Map<String, String>, encode: String): StringBuffer {
//        params.to("" to "1")
//        val stringBuffer = StringBuffer()        //存储封装好的请求体信息
//        try {
//            for ((key, value) in params) {
//                stringBuffer.append(key)
//                        .append("=")
//                        .append(value)
////                    .append(URLEncoder.encode(value, encode))
//                        .append("&")
//            }
//            stringBuffer.deleteCharAt(stringBuffer.length - 1)    //删除最后的一个"&"
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return stringBuffer
//    }
//
//    fun httpGetRunnableWithAccessToken(urlRefreshToken:String,accessToken:String,callback : (String)-> Unit ) : Runnable {
//        return Runnable {
//            val url = URL(URI(urlRefreshToken).toASCIIString())
//            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
//            urlConnection.setRequestProperty("x-refresh-token",accessToken)
//            urlConnection.setRequestProperty("X-API-Key",appId)
//            urlConnection.requestMethod = "GET"
//            urlConnection.connect()
//            val status: Int = urlConnection.getResponseCode()
//            val r = try {
//                val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)
//                val resp = inputStream.readBytes()
//                String(resp, Charsets.UTF_8)
//            } catch (err: Error) {
//                "Network Error"
//            } finally {
//                urlConnection.disconnect()
//            }
//            callback(r)
//        }
//    }
//
//    //utils
//    private void setWebView(Context context,WebView webView){
//        this.webView = webView;
//        webView.setWebViewClient(WebViewClient());
//        webView.loadUrl(urlAuth);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        //set autofit
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
//        // Set User Agent
//        userAgent = System.getProperty("http.agent");
//        webSettings.setUserAgentString(userAgent + appName)
//
//        // Enable Cookies
//        CookieManager.getInstance().setAcceptCookie(true)
//        if (Build.VERSION.SDK_INT >= 21) CookieManager.getInstance()
//                .setAcceptThirdPartyCookies(webView, true)
//
//        // Handle Popups
//        webView.setWebChromeClient(MirrorChromeClient());
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setSupportMultipleWindows(true);
//        globalContext = context.applicationContext;
//
//        // WebView Tweaks
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setSaveFormData(true);
//        webSettings.setEnableSmoothTransition(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
//
//        webView.addJavascriptInterface(this, delegateName);
//    }
//
//
//    @JavascriptInterface
//    public void setRefreshToken(String refreshToken) {
//        Toast.makeText(globalContext, strLoginSuccess, Toast.LENGTH_SHORT).show();
//        saveRefreshToken(globalContext,refreshToken);
//        parentDialog.dismiss();
//    }
//
//    private void saveRefreshToken(Context context,String refreshToken){
//        logFlow("save refresh token to local:"+refreshToken);
//        //获得SharedPreferences的实例 sp_name是文件名
//        SharedPreferences sp = context.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
//        //获得Editor 实例
//        SharedPreferences.Editor editor = sp.edit();
//        //以key-value形式保存数据
//        editor.putString(localKeyRefreshToken, refreshToken);
//        //apply()是异步写入数据
////        editor.apply()
//        //commit()是同步写入数据
//        editor.commit();
//    }
//
//    private void saveString(Context context,String key,String value){
//        //获得SharedPreferences的实例 sp_name是文件名
//        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
//        //获得Editor 实例
//        SharedPreferences.Editor editor = sp.edit();
//        //以key-value形式保存数据
//        editor.putString(key, value);
//        //apply()是异步写入数据
////        editor.apply()
//        //commit()是同步写入数据
//        editor.commit();
//    }
//
//    private String getSavedString(Context context,String key){
//        SharedPreferences sp = context.getSharedPreferences(localFileKey, Context.MODE_PRIVATE)
//        String value = sp.getString(key,"")
//
//        return value;
//    }
//
//    private String getRefreshToken(Context context){
//        SharedPreferences sp = context.getSharedPreferences(localFileKey, Context.MODE_PRIVATE);
//        String refreshToken = sp.getString(localKeyRefreshToken,"");
//
//        return refreshToken;
//    }
//
//    private RelativeLayout getPopupWindowLayout(Context context) {
//        RelativeLayout relative = new RelativeLayout(context);
//        addProgressBar(context,relative);
//        return relative;
//    }
//
//    private void addProgressBar(Context context,RelativeLayout view ){
//        ProgressBar bar = new ProgressBar(context);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//
//        view.addView(bar, lp);
//    }
//
//    private void logFlow(String value){
//        if(debugMode){
//            Log.d("MirrorSDK",value);
//        }
//    }
//
//    public interface ICallBack {
//        void callback(String result);
//    }
//
//    public class MirrorCallback implements ICallBack{
//
//        @Override
//        public void callback(String result) {
//
//        }
//    }
//    class WebChromeClient MirrorChromeClient() {
//        boolean onCreateWindow(WebView view,boolean isDialog,boolean isUserGesture,Message resultMsg) {
//            webViewPopUp = WebView(globalContext)
//            webViewPopUp!!.isVerticalScrollBarEnabled = false
//            webViewPopUp!!.isHorizontalScrollBarEnabled = false
//            webViewPopUp!!.webChromeClient = MirrorChromeClient()
//            webViewPopUp!!.settings.javaScriptEnabled = true
//            webViewPopUp!!.settings.saveFormData = true
//            webViewPopUp!!.settings.setEnableSmoothTransition(true)
//            webViewPopUp!!.settings.userAgentString = userAgent + appName
//
//            //set autofit
//            webViewPopUp!!.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
//            webViewPopUp!!.setOverScrollMode(View.OVER_SCROLL_NEVER);
////            webViewPopUp!!.settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            webViewPopUp!!.getSettings().setUseWideViewPort(false)
//            webViewPopUp!!.getSettings().setLoadWithOverviewMode(true)
//
//            // pop the  webview with alert dialog
//            builder = AlertDialog.Builder(activityContext).create()
//
////            builder?.setTitle("")
//            builder?.setView(webViewPopUp)
//            builder?.setButton("Close", DialogInterface.OnClickListener { dialog, id ->
//                    webViewPopUp!!.destroy()
//                dialog.dismiss()
//            })
//            builder?.show()
//            var window = builder?.window
//            window?.attributes?.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//            window?.clearFlags(
//                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
//            val cookieManager: CookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptCookie(true)
//            if (Build.VERSION.SDK_INT >= 21) {
//                cookieManager.setAcceptThirdPartyCookies(webViewPopUp, true);
//                cookieManager.setAcceptThirdPartyCookies(webView, true);
//            }
//            WebView.WebViewTransport transport = resultMsg.obj;
//            transport.webView = webViewPopUp
//            resultMsg.sendToTarget()
//            return true
//        }
//
//        void onCloseWindow(WebView window) {
//            //Toast.makeText(contextPop,"onCloseWindow called",Toast.LENGTH_SHORT).show();
//            try {
//                webViewPopUp!!.destroy()
//            } catch (e: Exception) {
//                Log.d("Destroyed with Error ", e.stackTrace.toString())
//            }
//            try {
//                builder?.dismiss()
//            } catch (e: Exception) {
//                Log.d("Dismissed with Error: ", e.stackTrace.toString())
//            }
//        }
//    }
//}