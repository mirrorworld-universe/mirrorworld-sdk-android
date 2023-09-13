package com.mirror.sdk;

import android.app.Activity;
import android.content.Context;

import com.mirror.sdk.chain.MWBaseWrapper;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.evm.MWEVM;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.solana.MWSolana;
import com.mirror.sdk.sui.MWSUI;

public class MirrorWorld {
    public static MWSolana Solana = new MWSolana();
    public static MWEVM Ethereum = new MWEVM();
    public static MWEVM Polygon = new MWEVM();
    public static MWEVM BNB = new MWEVM();
    public static MWSUI SUI = new MWSUI();

    /**
     * Type: SDK
     * Function: Init SDK
     */
    public final static void initSDK(Context activityContext, String apiKey, MirrorEnv env,MirrorChains chain){
        if(apiKey == ""){
            MirrorSDK.logWarn("Please input API key");
            return;
        }
        MirrorSDK.getInstance().InitSDK(activityContext,env,chain);
        MirrorSDK.getInstance().SetApiKey(apiKey);
    }

    /**
     * Type: SDK
     * Function: Get now environment.
     * @return
     */
    final public static MirrorEnv getEnvironment(){
        return MWBaseWrapper.getEnvironment();
    }

    /**
     * Type: SDK
     * Function: Show the sdk flow in console if true.
     * @param useDebugMode
     */
    final public static void setDebug(boolean useDebugMode){
        MWBaseWrapper.setDebug(useDebugMode);
    }

    /**
     * Type: SDK
     * Function: Login.
     */
    final public static void startLogin(LoginListener loginListener, Activity currentActivity){
        MWBaseWrapper.startLogin(loginListener,currentActivity);
    }

    final public static void startLogin(MirrorCallback callback, Activity currentActivity){
        MWBaseWrapper.startLogin(callback,currentActivity);
    }

    /**
     * Type: SDK
     * Function: Guest login.
     */
    final public static void guestLogin(LoginListener listener){
        MWBaseWrapper.guestLogin(listener);
    }

    /**
     * Type: SDK
     * Function: Logout.
     * @param listener
     */
    final public static void logout(BoolListener listener){
        MWBaseWrapper.logout(listener);
    }

    /**
     * Type: SDK
     * Function: Login with email and get all response.
     */
    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
        MWBaseWrapper.loginWithEmail(email, password, callback);
    }

    /**
     * Type: SDK
     * Function: Open user's wallet page.
     */
    final public static void openWallet(Activity returnActivity, MirrorCallback callback){
        MWBaseWrapper.openWallet(returnActivity,"",callback);
    }

    final public static void openWallet(Activity returnActivity,String walletUrl,MirrorCallback callback){
        MWBaseWrapper.openWallet(returnActivity,walletUrl,callback);
    }

    /**
     * Type: SDK
     * Function: Open market of this app.
     */
    final public static void openMarket(String marketUrl,Activity returnActivity){
        MWBaseWrapper.openMarket(marketUrl,returnActivity);
    }

    /**
     * Type: SDK
     * Function: Open any url
     */
    final public static void openUrl(String url,Activity returnActivity){
        MWBaseWrapper.openUrl(url,returnActivity);
    }

    /**
     * TYpe: SDK
     * Function: Set RedirectActivity's scheme name to avoid that all scheme name are same.
     * Notice: Remember to keep it is same with the scheme in AndroidManifest.xml
     * @param schemeName
     */
    final public static void setSchemeName(String schemeName){
        MWBaseWrapper.setSchemeName(schemeName);
    }

    /**
     * Type: SDK
     * Function: Fetch details of a user.
     * @param listener
     */
    final public static void fetchUser(FetchUserListener listener){
        MWBaseWrapper.fetchUser(listener);
    }

    /**
     * Type: SDK
     * Function: Query some user and get details;
     * @param email
     * @param listener
     */
    final public static void queryUser(String email, FetchUserListener listener){
        MWBaseWrapper.queryUser(email,listener);
    }

    /**
     * Type: SDK
     * Function: Checks whether the current user is logged in
     * @param listener
     */
    final public static void isLoggedIn(BoolListener listener){
        MWBaseWrapper.isLoggedIn(listener);
    }

}