package com.mirror.mirrorworld_sdk_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.mirrorworld_sdk_android.data.PlaceholderContent;
import com.mirror.sdk.constant.MirrorConstant;
import com.mirror.sdk.constant.MirrorEnv;
import com.mirror.sdk.MirrorSDK;

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
        MirrorSDK.getInstance().InitSDK(this, MirrorEnv.Staging);
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
        titles.add("newApis");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();

        APIFragment UserAPIs=new APIFragment("UserAPIs",replaceToUserAPIs());
        APIFragment AuthAPIs=new APIFragment("AuthAPIs",replaceToAuthAPIs());
        MultiParaItemFragment MarketAPIs = new MultiParaItemFragment(replaceToMarketAPIs());
        MultiParaItemFragment NFTAPIs = new MultiParaItemFragment(replaceToNFTAPIs());
        MultiParaItemFragment WalletAPIs = new MultiParaItemFragment(replaceToWalletAPIs());

        MultiParaItemFragment newAPIs = new MultiParaItemFragment(newAPIs());

        fragments.add(UserAPIs);
        fragments.add(AuthAPIs);
        fragments.add(MarketAPIs);
        fragments.add(NFTAPIs);
        fragments.add(WalletAPIs);
        fragments.add(newAPIs);



        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);

        mViewPager.setAdapter(mFragmentAdapteradapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }




    private List<MultiItemData.MultiItem> replaceToWalletAPIs() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.POST_TRANSFER_SQL,"Get wallet address",
                "Get user's wallet address on solana",
                "GetWallet",
                null,null,null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(MirrorConstant.POST_TRANSFER_SQL,"Transfer SOL","Transfer SOL",
                        "PostTransferToken","to_publickey","amount",
                        null,null,null,null));

        items.add(
                new MultiItemData.MultiItem(MirrorConstant.POST_TRANSFER_TOKEN,"Transfer Token","Transfer Token",
                        "PostTransferToken","to_publickey","amount",
                        "token_mint","decimals",null,null));

        items.add(
                new MultiItemData.MultiItem(MirrorConstant.GET_WALLET_TOKEN,"Get wallet tokens","Get a wallet's tokens",
                        "GetWalletToken",null,null,
                        null,null,null,null));


        items.add(
                new MultiItemData.MultiItem(MirrorConstant.TRANSACTIONS,"Get Wallet Transactions","Get a wallet's transactions by filters",
                        "Transactions","limit","before",
                        null,null,null,null));


        return items;
    }
    private  List<MultiItemData.MultiItem> replaceToNFTAPIs() {

        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.MINT_NFT,"Mint New NFT",
                "Mint New NFT on Collection",
                "MintNFT",
                "collection_mint","name","symbol","url",null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.CREATE_VERIFIED_COLLECTION,"Mint New Top-level",
                "Mint New Top-level Collection.",
                "CreateVerifiedCollection",
                "name","symbol","url",null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.CREATE_VERIFIED_SUB_COLLECTION,"Mint New Lower-level",
                "Mint New Lower-level Collection.",
                "CreateVerifiedSubCollection",
                "collection_mint","name","symbol","url",null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.FETCH_NFT_BY_MINT_ADDRESSES,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by mint addresses.",
                "FetchNFTByMintAddress",
                "mint_addresses1","mint_addresses2",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.FETCH_NFT_BY_CREATOR_ADDRESSES,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by creator addresses.",
                "FetchNFTByCreatorAddress",
                "creators1","limit","offset",null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.FETCH_NFT_BY_UPDATE_AUTHORITIES,"Fetch multiple NFTs data",
                "Fetch multiple NFTs data by update authority addresses.",
                "FetchNFTByUpdateAuthorities",
                "update_authorities1","limit","offset",null,null,null));



        return items;
    }
    private  List<MultiItemData.MultiItem> replaceToMarketAPIs() {


        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(
                MirrorConstant.FETCH_SINGLE_NFT_DETAILS,"Fetch single NFT's details",
                "Fetch single NFT's details by mint address.",
                "FetchSingleNFTDetails","mint address",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.LIST_NFT,"List NFT on the marketplace",
                "Fetch multiple NFTs data by update authority addresses.",
                "ListNFTs",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.UPDATE_NFT_LISTING,"Update Listing of NFT",
                "Update Listing of NFT on the marketplace.",
                "UpdateNFTListing",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.BUY_NFT,"Buy NFT",
                "Buy NFT on the marketplace.",
                "BuyNFT",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.CANCEL_NFT_LISTING,"Cancel listing of NFT",
                "Cancel listing of NFT on the marketplace.",
                "CancelNFTListing",
                "mint_address","price",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.TRANSFER_NFT_TO_ANOTHER_SOLANA_WALLET,"Transfer NFT",
                "Transfer NFT to another solana wallet.",
                "TransferNFTToAnotherSolanaWallet",
                "mint_address","to_wallet_address",null,null,null,null));


        items.add(new MultiItemData.MultiItem(
                MirrorConstant.FETCH_NFT_BY_OWNER_ADDRESSES,"Fetch multiple NFTs","Fetch multiple NFTs data by owner addresses",
                "FetchNFTByWNERAddress","owner","limit","offset",null,null,null
        ));

        items.add(new MultiItemData.MultiItem(
                MirrorConstant.FETCH_NFT_MARKETPLACE_ACTIVITY,"Fetch activity","Fetch activity of a single NFT",
                "FetchNFTMarketActivity","mint_address",null,null,null,null,null
        ));



        return items;
    }
    private  List<PlaceholderContent.PlaceholderItem> replaceToUserAPIs() {


        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem(
                MirrorConstant.Query_USER,"Query user info",
                "Query user info by email address.",
                "QueryUser","input your email"));
        return items;
    }
    private  List<PlaceholderContent.PlaceholderItem> replaceToAuthAPIs() {


        List<PlaceholderContent.PlaceholderItem> items = new ArrayList<>();
        items.add(new PlaceholderContent.PlaceholderItem(
                MirrorConstant.START_LOGIN,"Login",
                "Call login page so that user can get refresh token.",
                "StartLogin",null));
        items.add(new PlaceholderContent.PlaceholderItem(
                MirrorConstant.GET_ACCESS_TOKEN,"Refresh auth token",
                "Get an access token by refresh token so that you can visit APIs.",
                "GetAccessToken",null));
        items.add(new PlaceholderContent.PlaceholderItem(
                MirrorConstant.SET_APP_ID,"Set app id",
                "Set app id of your project,you can get it on sdk's site.",
                "SetAppID","app-id"));

        return items;
    }


    private  List<MultiItemData.MultiItem> newAPIs() {


        List<MultiItemData.MultiItem> items = new ArrayList<>();

        items.add(new MultiItemData.MultiItem(
                MirrorConstant.LOGIN_WITH_EMAIL,"Login with email",
                "Logs in a user with their email address and password",
                "LoginWithEmail","email","password",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.CHECKS_WEATHER_AUTHENTICATED,"Checks authenticated",
                "Checks whether is authenticated or not and returns the user object if true",
                "Check",
                null,null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.CREATE_NEW_MARKET,"Create new Marketplace ",
                "Create new Marketplace on Solana",
                "Create Marketplace",
                "treasury_withdrawal_destination","fee_withdrawal_destination","treasury_mint","seller_fee_basis_points",null,null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.UPDATE_MARKET,"Updates existing Marketplace",
                "Updates existing Marketplace on Solana",
                "Updates existing Marketplace",
                "new_authority","treasury_mint","treasury_withdrawal_destination","fee_withdrawal_destination","seller_fee_basis_points",null));
        items.add(new MultiItemData.MultiItem(
                MirrorConstant.QUERY_MARKET,"Query marketplaces",
                "Query marketplaces on Solana",
                "Query marketplaces",
                "mint_address","price",null,null,null,null));
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