package com.mirror.mirrorworld_sdk_android;

import static org.junit.Assert.assertEquals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.MirrorSDK;
import com.mirror.sdk.utils.MirrorWebviewUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MirrorSDK","onCreate");
        makeStatusBarTransparent(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MirrorSDK.getInstance().InitSDK(this, MirrorEnv.StagingDevNet);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initViewPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MirrorSDK","onDestroy");
    }

    private void initViewPage(){
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("Auth");
        titles.add("Market:Mint&List");
        titles.add("Market:other");
        titles.add("Wallet");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }


        List<Fragment> fragments = new ArrayList<>();

        MultiParaItemFragment Auth = new MultiParaItemFragment(authApis());
        MultiParaItemFragment MarketMint = new MultiParaItemFragment(marketMintApis());
        MultiParaItemFragment MarketOther = new MultiParaItemFragment(marketOtherApis());
        MultiParaItemFragment Wallet = new MultiParaItemFragment(walletApis());

        fragments.add(Auth);
        fragments.add(MarketMint);
        fragments.add(MarketOther);
        fragments.add(Wallet);


        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);

        mViewPager.setAdapter(mFragmentAdapteradapter);

        mTabLayout.setupWithViewPager(mViewPager);


    }

    private List<MultiItemData.MultiItem> authApis() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(
                DemoAPIID.SET_APP_ID,"Set App ID",
                "Set app id before user all apis",
                "SetAppID",
                "appid",null,null,null,null,null));

        items.add(new MultiItemData.MultiItem(
                DemoAPIID.START_LOGIN,"Start login",
                "Open the login page",
                "Login",
                null,null,null,null,null,null));

        items.add(new MultiItemData.MultiItem(
                DemoAPIID.OPEN_LOGIN_PAGE,"Open login page",
                "Open the login page by custom tab",
                "Go",
                null,null,null,null,null,null));

        items.add(new MultiItemData.MultiItem(
                DemoAPIID.LOGIN_With_EMAIL,"Login with email",
                "Logs in a user with their email address and password",
                "LoginWithEmail",
                "email","passWord",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPIID.OPEN_WALLET,"Open wallet",
                "Open user's wallet after login.",
                "OpenWallet",
                null,null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPIID.OPEN_MARKET,"Open market",
                "Open market after login.",
                "OpenMarket",
                null,null,null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(DemoAPIID.FETCH_USER,"FetchUser","Checks whether is authenticated or not and returns the user object if true",
                        "FetchUser",null,null,
                        null,null,null,null));

        items.add(
                new MultiItemData.MultiItem(DemoAPIID.QUERY_USER,"QueryUser","this request is using API Key from collectionMirror World Mobile SDK",
                        "QueryUser","email",null,
                        null,null,null,null));

        return items;
    }

    private List<MultiItemData.MultiItem> marketMintApis() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(
                DemoAPIID.CREATE_VERIFIED_COLLECTION,"Mint New Top-level Collection",
                "This request is using API Key from collectionMirror World Mobile SDK",
                "TOP_COLLECTION",
                "name","symbol","detailUrl",null,null,null));


//        items.add(
//
//                new MultiItemData.MultiItem(DemoAPIID.CREATE_VERIFIED_SUB_COLLECTION,"Mint New Lower-level Collection","This request is using API Key from collectionMirror World Mobile SDK",
//                        "LOW_COLLECTION","collection_mint","name",
//                        "symbol","detailUrl",null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPIID.MINT_NFT,"Mint New NFT on Collection","This request is using API Key from collectionMirror World Mobile SDK",
                        "MINT_NFT","collection_mint","name",
                        "symbol","detailUrl",null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPIID.LIST_NFT,"List NFT on the marketplace","List NFT on the marketplace by use mint address",
                        "LIST_NFT","mint_address","price",
                        null,null,null,null));


        items.add(

                new MultiItemData.MultiItem(DemoAPIID.UPDATE_NFT_LISTING,"Update Listing of NFT on the marketplace","Update Listing of NFT on the marketplace",
                        "UPDATE_NFT_LISTING","mint_address","price",
                        null,null,null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPIID.CANCEL_NFT_LISTING,"Cancel listing of NFT on the marketplace","Cancel listing of NFT on the marketplace",
                        "CANCEL_NFT_LISTING","mint_address","price",
                        null,null,null,null));
        return items;
    }

    private List<MultiItemData.MultiItem> marketOtherApis() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(

                DemoAPIID.FETCH_NFT_BY_OWNER_ADDRESSES,"Fetch multiple NFTs data by owner addresses",
                "Fetch multiple NFTs data by owner addresses",
                "FETCH_BY_OWNER",
                "owner_address","limit","offset",null,null,null));


        items.add(
                new MultiItemData.MultiItem(DemoAPIID.FETCH_NFT_BY_MINT_ADDRESSES,"Fetch multiple NFTs data by mint addresses","Fetch multiple NFTs data by mint addresses",
                        "FETCH_BY_MINT","mint_address",null,
                        null,null,null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPIID.FETCH_NFT_BY_UPDATE_AUTHORITIES,"Fetch multiple NFTs data by update authority addresses","Fetch multiple NFTs data by update authority addresses",
                        "FETCH_BY_AUTHORITIES","update_authorities","limit",
                        "offset",null,null,null));

        items.add(
                new MultiItemData.MultiItem(DemoAPIID.FETCH_SINGLE_NFT_DETAILS,"Fetch single NFT details","Fetch single NFT details",
                        "FETCH_SINGLE_NFT","mint_address",null,
                        null,null,null,null));


        items.add(

                new MultiItemData.MultiItem(DemoAPIID.FETCH_NFT_MARKETPLACE_ACTIVITY,"Fetch activity of a single NFT","Fetch activity of a single NFT",
                        "FETCH_NFT_ACTIVITY","mint_address",null,
                        null,null,null,null));
        items.add(

                new MultiItemData.MultiItem(DemoAPIID.TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET,"Transfer NFT to another solana wallet","Transfer NFT to another solana wallet",
                        "TRANSFER_NFT","mint_address","to_wallet_address",
                        null,null,null,null));
        items.add(

                new MultiItemData.MultiItem(DemoAPIID.BUY_NFT,"Buy NFT on the marketplace","Buy NFT on the marketplace",
                        "Buy_NFT","mint_address","price",
                        null,null,null,null));


        return items;
    }

    private List<MultiItemData.MultiItem> walletApis() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                DemoAPIID.GET_WALLET_TOKEN,"Get wallet tokens.","Get wallet tokens.",
                "Get_Wallet_Token",
                null,null,null,null,null,null));


        items.add(

                new MultiItemData.MultiItem(DemoAPIID.WALLET_TRANSACTIONS,"Get wallet transactions.","Get wallet transactions.",
                        "WALLET_TRANSACTIONS","limit","before",
                        null,null,null,null));

        items.add(
                new MultiItemData.MultiItem(DemoAPIID.WALLET_TRANSACTIONS_BY_SIGNATURE,"Get wallet transaction by signature","Get wallet transaction by signature",
                        "TRANSACTIONS_SIG","signature",null,
                        null,null,null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPIID.TRANSFER_SQL,"Transfer SOL to another address","Transfer SOL to another address",
                        "TRANSFER_SQL","to_publickey","amount",
                        null,null,null,null));


        return items;
    }

    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}