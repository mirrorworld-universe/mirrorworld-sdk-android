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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MirrorSDK","onCreate");
        mActivity = this;
        makeStatusBarTransparent(this);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
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
        titles.add("Client");
        titles.add("Asset");
        titles.add("Asset:other");
        titles.add("Wallet");
        titles.add("Metadata");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }


        List<Fragment> fragments = new ArrayList<>();

        MultiParaItemFragment Auth = new MultiParaItemFragment(authApis());
        MultiParaItemFragment MarketMint = new MultiParaItemFragment(marketMintApis());
        MultiParaItemFragment MarketOther = new MultiParaItemFragment(marketOtherApis());
        MultiParaItemFragment Wallet = new MultiParaItemFragment(walletApis());
        MultiParaItemFragment marketUI = new MultiParaItemFragment(marketUIAPIs());

        fragments.add(Auth);
        fragments.add(MarketMint);
        fragments.add(MarketOther);
        fragments.add(Wallet);
        fragments.add(marketUI);

        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapteradapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<MultiItemData.MultiItem> authApis() {
        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                DemoAPI.INIT_SDK,"Init SDK",
                "Set app id and init sdk",
                "Init",
                "APIKey",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_ENVIRONMENT,"Get environment",
                "Get now environment as a number",
                "Get",
                null,null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SET_JWT,"Set JWT",
                "Set jwt",
                "Set It",
                "jwt",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.START_LOGIN,"Start login",
                "Open the login page",
                "Login",
                null,null,null,null,null,null));

        items.add(new MultiItemData.MultiItem(
                DemoAPI.GUEST_LOGIN,"Guest login",
                "You can login as a guest",
                "Login",
                null,null,null,null,null,null));

        items.add(new MultiItemData.MultiItem(
                DemoAPI.LOGIN_With_EMAIL,"Login with email",
                "Logs in a user with their email address and password",
                "LoginWithEmail",
                "email","passWord",null,null,null,null));

        items.add(new MultiItemData.MultiItem(
                DemoAPI.LOGOUT,"logout",
                "Try to logout current user,cache will be all cleared.",
                "Logout",
                null,null,null,null,null,null));

        items.add(new MultiItemData.MultiItem(
                DemoAPI.OPEN_WALLET,"Open wallet",
                "Open user's wallet after login.",
                "OpenWallet",
                null,null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.OPEN_MARKET,"Open market",
                "Open market after login.",
                "OpenMarket",
                null,null,null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(DemoAPI.FETCH_USER,"FetchUser","Checks whether is authenticated or not and returns the user object if true",
                        "FetchUser",null,null,
                        null,null,null,null));

        items.add(
                new MultiItemData.MultiItem(DemoAPI.QUERY_USER,"QueryUser","this request is using API Key from collectionMirror World Mobile SDK",
                        "QueryUser","email",null,
                        null,null,null,null));

        return items;
    }

    private List<MultiItemData.MultiItem> marketMintApis() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(
                DemoAPI.CREATE_VERIFIED_COLLECTION,"Mint New Top-level Collection",
                "This request is using API Key from collectionMirror World Mobile SDK",
                "TOP_COLLECTION",
                "name","symbol","detailUrl",null,null,null));


//        items.add(
//
//                new MultiItemData.MultiItem(DemoAPI.CREATE_VERIFIED_SUB_COLLECTION,"Mint New Lower-level Collection","This request is using API Key from collectionMirror World Mobile SDK",
//                        "LOW_COLLECTION","collection_mint","name",
//                        "symbol","detailUrl",null,null));

        items.add(
                new MultiItemData.MultiItem(DemoAPI.MINT_NFT,"Mint New NFT on Collection","This request is using API Key from collectionMirror World Mobile SDK",
                        "MINT_NFT","collection_mint","name",
                        "symbol","detailUrl",null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.CHECK_STATUS_OFMINTING,"Check status of minting","Minting is not finish immediately, check its status use this API",
                        "Check","mint address 1","mint address 2",
                        null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.UPDATE_NFT,"Update NFT on Collection","Update a minted NFT's info",
                        "Update","mint address","NFT name",
                        "NFT symbol","updateAuthority","NFTJsonUrl","seller fee basis points"));
        items.add(

                new MultiItemData.MultiItem(DemoAPI.LIST_NFT,"List NFT on the marketplace","List NFT on the marketplace by use mint address",
                        "LIST_NFT","mint_address","price",
                        null,null,null,null));


        items.add(

                new MultiItemData.MultiItem(DemoAPI.UPDATE_NFT_LISTING,"Update Listing of NFT on the marketplace","Update Listing of NFT on the marketplace",
                        "UPDATE_NFT_LISTING","mint_address","price",
                        null,null,null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPI.CANCEL_NFT_LISTING,"Cancel listing of NFT on the marketplace","Cancel listing of NFT on the marketplace",
                        "CANCEL_NFT_LISTING","mint_address","price",
                        null,null,null,null));
        return items;
    }

    private List<MultiItemData.MultiItem> marketOtherApis() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(

                DemoAPI.FETCH_NFT_BY_OWNER_ADDRESSES,"Fetch multiple NFTs data by owner addresses",
                "Fetch multiple NFTs data by owner addresses",
                "FETCH_BY_OWNER",
                "owner_address","limit","offset",null,null,null));


        items.add(
                new MultiItemData.MultiItem(DemoAPI.FETCH_NFT_BY_MINT_ADDRESSES,"Fetch multiple NFTs data by mint addresses","Fetch multiple NFTs data by mint addresses",
                        "FETCH_BY_MINT","mint_address",null,
                        null,null,null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPI.FETCH_NFT_BY_UPDATE_AUTHORITIES,"Fetch multiple NFTs data by update authority addresses","Fetch multiple NFTs data by update authority addresses",
                        "FETCH_BY_AUTHORITIES","update_authorities","limit",
                        "offset",null,null,null));

        items.add(
                new MultiItemData.MultiItem(DemoAPI.FETCH_SINGLE_NFT_DETAILS,"Fetch single NFT details","Fetch single NFT details",
                        "FETCH_SINGLE_NFT","mint_address",null,
                        null,null,null,null));


        items.add(

                new MultiItemData.MultiItem(DemoAPI.FETCH_NFT_MARKETPLACE_ACTIVITY,"Fetch activity of a single NFT","Fetch activity of a single NFT",
                        "FETCH_NFT_ACTIVITY","mint_address",null,
                        null,null,null,null));
        items.add(

                new MultiItemData.MultiItem(DemoAPI.TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET,"Transfer NFT to another solana wallet","Transfer NFT to another solana wallet",
                        "TRANSFER_NFT","mint_address","to_wallet_address",
                        null,null,null,null));
        items.add(

                new MultiItemData.MultiItem(DemoAPI.BUY_NFT,"Buy NFT on the marketplace","Buy NFT on the marketplace",
                        "Buy_NFT","mint_address","price",
                        null,null,null,null));


        return items;
    }

    private List<MultiItemData.MultiItem> walletApis() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_WALLET_TOKEN,"Get wallet tokens.","Get wallet tokens.",
                "Get_Wallet_Token",
                null,null,null,null,null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPI.TRANSFER_SPL_TOKEN,"Transfer SPL Token","Transfer SPL Token to another wallet.",
                        "TRANSFER","toPublicKey","amount",
                        "token_mint","decimals",null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPI.WALLET_TRANSACTIONS,"Get wallet transactions.","Get wallet transactions.",
                        "WALLET_TRANSACTIONS","limit","before",
                        null,null,null,null));

        items.add(
                new MultiItemData.MultiItem(DemoAPI.WALLET_TRANSACTIONS_BY_SIGNATURE,"Get wallet transaction by signature","Get wallet transaction by signature",
                        "TRANSACTIONS_SIG","signature",null,
                        null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.CHECK_STATUS_TRANSACTION,"Get status of transaction by signature","Check status of transaction by sinature",
                        "Check","signature 1","signature 2",
                        null,null,null,null));

        items.add(

                new MultiItemData.MultiItem(DemoAPI.TRANSFER_SOL,"Transfer SOL to another address","Transfer SOL to another address",
                        "TRANSFER_SQL","to_publickey","amount",
                        null,null,null,null));
        items.add(

                new MultiItemData.MultiItem(DemoAPI.WALLET_GET_TRANSACTION_OF_TRANSFER_SOL,"Get transaction of trans-sol","Get transaction of transfering sol",
                        "Fetch","to_publickey","amount",
                        null,null,null,null));
        items.add(

                new MultiItemData.MultiItem(DemoAPI.WALLET_GET_TRANSACTION_OF_TRANSFER_TOKEN,"Get transaction of trans-token","Get transaction of transfering token",
                        "Fetch","to_publickey","amount",
                        "token mint","decimals",null,null));


        return items;
    }

    private List<MultiItemData.MultiItem> marketUIAPIs() {
        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_COLLECTION_FILTER_INFO,"Get collection filter info.","Get collection filter info.",
                "Get",
                "collection",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_NFT_INFO,"Get NFT info.","Get details in market of a NFT.",
                "Get",
                "mint address",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_COLLECTION_INFO,"Get collection info.","Get a collection's info.",
                "Get",
                "collection 1",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_NFT_EVENTS,"Get NFT events.","Get events of NFTs in market.",
                "Get",
                "mint address","page","page size",null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SEARCH_NFTS,"Search NFTs.","Get details of NFTs in market place.",
                "Search",
                "collection address 1","search string",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.RECOMMEND_SEARCH_NFT,"Recommend search NFT.","Get details in market of a NFT by recommend.",
                "Get",
                "collection address 1",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_NFT_REAL_PRICE,"Get NFT real price.","Get real price of a NFT.",
                "Get",
                "price","price fee",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_NFTS,"Get NFTs.","Get details of NFTs in market place.",
                "Get",
                "collection address 1","page","page size","order by","desc","sale"));
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