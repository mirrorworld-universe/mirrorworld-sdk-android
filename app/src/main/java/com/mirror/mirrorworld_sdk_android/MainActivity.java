package com.mirror.mirrorworld_sdk_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import com.google.android.material.tabs.TabLayout;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.mirrorworld_sdk_android.data.PlaceholderContent;
import com.mirror.sdk.MirrorEnv;
import com.mirror.sdk.MirrorSDKJava;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        makeStatusBarTransparent(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MirrorSDKJava.getInstance().InitSDK(this, MirrorEnv.Staging);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initViewPager();
    }

    private void initViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("UserAPIs");
        titles.add("AuthAPIs");
        titles.add("MarketAPIs");
        titles.add("NFTAPIs");
        titles.add("WalletAPIs");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();

        APIFragment UserAPIs=new APIFragment("UserAPIs",replaceToUserAPIs());
        APIFragment AuthAPIs=new APIFragment("AuthAPIs",replaceToAuthAPIs());
        MultiParaItemFragment MarketAPIs = new MultiParaItemFragment(replaceToMarketAPIs());
        MultiParaItemFragment NFTAPIs = new MultiParaItemFragment(replaceToNFTAPIs());
        MultiParaItemFragment WalletAPIs = new MultiParaItemFragment(replaceToWalletAPIs());

        fragments.add(UserAPIs);
        fragments.add(AuthAPIs);
        fragments.add(MarketAPIs);
        fragments.add(NFTAPIs);
        fragments.add(WalletAPIs);



        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);

        mViewPager.setAdapter(mFragmentAdapteradapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }




    private List<MultiItemData.MultiItem> replaceToWalletAPIs() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                101,"Get wallet address",
                "Get user's wallet address on solana",
                "GetWallet",
                null,null,null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(102,"Transfer SOL","Transfer SOL",
                        "Get","to_publickey","amount",
                        null,null,null,null));

        items.add(
                new MultiItemData.MultiItem(103,"Transfer Token","Transfer Token",
                        "Get","to_publickey","amount",
                        "token_mint","decimals",null,null));

        items.add(
                new MultiItemData.MultiItem(104,"Get wallet tokens","Get a wallet's tokens",
                        "Get",null,null,
                        null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(105,"Get Wallet Transactions","Get a wallet's transactions by filters",
                        "Get","limit","before",
                        null,null,null,null));


        return items;
    }
    private  List<MultiItemData.MultiItem> replaceToNFTAPIs() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                31,"Mint New NFT",
                "Mint New NFT on Collection",
                "MintNFT",
                "collection_mint","name","symbol","url",null,null));
        items.add(new MultiItemData.MultiItem(
                32,"Mint New Top-level",
                "Mint New Top-level Collection.",
                "MintNFT",
                "name","symbol","url",null,null,null));
        items.add(new MultiItemData.MultiItem(
                33,"Mint New Lower-level",
                "Mint New Lower-level Collection.",
                "MintNFT",
                "collection_mint","name","symbol","url",null,null));
        items.add(new MultiItemData.MultiItem(
                34,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by mint addresses.",
                "FetchNFTs",
                "mint_addresses1","mint_addresses2",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                35,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by creator addresses.",
                "FetchNFTs",
                "creators1","limit","offset",null,null,null));
        items.add(new MultiItemData.MultiItem(
                36,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by update authority addresses.",
                "FetchNFTs",
                "update_authorities1","limit","offset",null,null,null));



        return items;
    }
    private  List<MultiItemData.MultiItem> replaceToMarketAPIs() {


        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(
                12,"Fetch single NFT's details",
                "Fetch single NFT's details by mint address.",
                "FetchNFT","mint address",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                37,"List NFT on the marketplace",
                "Fetch multiple NFTs data by update authority addresses.",
                "ListNFTs",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                38,"Update Listing of NFT",
                "Update Listing of NFT on the marketplace.",
                "ListNFTs",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                39,"Buy NFT",
                "Buy NFT on the marketplace.",
                "BuyNFT",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                40,"Cancel listing of NFT",
                "Cancel listing of NFT on the marketplace.",
                "CancelIt",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                41,"Transfer NFT",
                "Transfer NFT to another solana wallet.",
                "TransferNFT",
                "mint_address","to_wallet_address",null,null,null,null));


        items.add(new MultiItemData.MultiItem(
                42,"Fetch multiple NFTs","Fetch multiple NFTs data by owner addresses",
                "Fetch NFTs","owner","limit","offset",null,null,null
        ));

        items.add(new MultiItemData.MultiItem(
                43,"Fetch activity","Fetch activity of a single NFT",
                "Fetch activity","mint_address",null,null,null,null,null
        ));



        return items;
    }
    private  List<PlaceholderContent.PlaceholderItem> replaceToUserAPIs() {


        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem(
                11,"Query user info",
                "Query user info by email address.",
                "QueryUser","input your email"));
        return items;
    }
    private  List<PlaceholderContent.PlaceholderItem> replaceToAuthAPIs() {


        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem(
                1,"Login",
                "Call login page so that user can get refresh token.",
                "Login",null));
        items.add(new PlaceholderContent.PlaceholderItem(
                2,"Refresh auth token",
                "Get an access token by refresh token so that you can visit APIs.",
                "RefreshToken",null));
        items.add(new PlaceholderContent.PlaceholderItem(
                3,"Set app id",
                "Set app id of your project,you can get it on sdk's site.",
                "Set","app-id"));

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