package com.mirror.sdk.chain;

import android.app.Activity;

import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.listener.auth.FetchUserListener;
import com.mirror.sdk.listener.auth.LoginListener;
import com.mirror.sdk.listener.universal.BoolListener;
import com.mirror.sdk.listener.universal.MirrorCallback;

public class MWBaseWrapper {

    /**
     * Type: SDK
     * Function: Get now environment.
     * @return
     */
    final public static MirrorEnv getEnvironment(){
        return MirrorSDK.getInstance().getEnv();
    }

    /**
     * Type: SDK
     * Function: Show the sdk flow in console if true.
     * @param useDebugMode
     */
    final public static void setDebug(boolean useDebugMode){
        MirrorSDK.getInstance().SetDebug(useDebugMode);
    }

    /**
     * Type: SDK
     * Function: Login.
     */
    final public static void startLogin(LoginListener loginListener,Activity returnActivity){
        MirrorSDK.getInstance().openLoginPage(loginListener,returnActivity);
    }

    final public static void startLogin(MirrorCallback callback,Activity returnActivity){
        MirrorSDK.getInstance().openLoginPage(callback,returnActivity);
    }

    /**
     * Type: SDK
     * Function: Guest login.
     */
    final public static void guestLogin(LoginListener listener){
        MirrorSDK.getInstance().guestLogin(listener);
    }

    /**
     * Type: SDK
     * Function: Logout.
     * @param listener
     */
    final public static void logout(BoolListener listener){
        MirrorSDK.getInstance().logout(listener);
    }

    /**
     * Type: SDK
     * Function: Login with email and get all response.
     */
    final public static void loginWithEmail(String email,String password,MirrorCallback callback){
        MirrorSDK.getInstance().LoginWithEmail(email, password, callback);
    }

    /**
     * Type: SDK
     * Function: Open user's wallet page.
     */
    final public static void openWallet(Activity returnActivity, MirrorCallback callback){
        MirrorSDK.getInstance().OpenWallet(returnActivity,"",callback);
    }

    final public static void openWallet(Activity returnActivity, String walletUrl,MirrorCallback callback){
        MirrorSDK.getInstance().OpenWallet(returnActivity,walletUrl,callback);
    }

    /**
     * Type: SDK
     * Function: Open market of this app.
     */
    final public static void openMarket(String marketUrl,Activity returnActivity){
        MirrorSDK.getInstance().openMarket(marketUrl,returnActivity);
    }

    /**
     * Type: SDK
     * Function: Open any url
     */
    final public static void openUrl(String url,Activity returnActivity){
        MirrorSDK.getInstance().openUrl(url,returnActivity);
    }

    final public static void setSchemeName(String schemeName){
        MirrorSDK.getInstance().setSchemeName(schemeName);
    }

    /**
     * Type: SDK
     * Function: Fetch details of a user.
     * @param listener
     */
    final public static void fetchUser(FetchUserListener listener){
        MirrorSDK.getInstance().FetchUser(listener);
    }

    /**
     * Type: SDK
     * Function: Query some user and get details;
     * @param email
     * @param listener
     */
    final public static void queryUser(String email, FetchUserListener listener){
        MirrorSDK.getInstance().QueryUser(email,listener);
    }

    /**
     * Type: SDK
     * Function: Checks whether the current user is logged in
     * @param listener
     */
    final public static void isLoggedIn(BoolListener listener){
        MirrorSDK.getInstance().CheckAuthenticated(listener);
    }
}
