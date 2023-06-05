package com.mirror.mirrorworld_sdk_android.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mirror.mirrorworld_sdk_android.FragmentAdapter;
import com.mirror.mirrorworld_sdk_android.MultiParaItemFragment;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.mirrorworld_sdk_android.enums.DemoAPI;
import com.mirror.sdk.constant.MirrorChains;

import java.util.ArrayList;
import java.util.List;

public class PageIniterSUI extends APIPageIniterBase{

    public PageIniterSUI(AppCompatActivity activity, MirrorChains chain) {
        super(activity, chain);
    }

    public void initPage(){
        MirrorChains chain = mChain;
        List<String> titles = new ArrayList<>();
        titles.add("Client");
        titles.add("Asset");
        titles.add("Wallet");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();

        MultiParaItemFragment Auth = new MultiParaItemFragment(authApis(),chain);
        MultiParaItemFragment MarketMint = new MultiParaItemFragment(marketMintApis(),chain);
//        MultiParaItemFragment MarketOther = new MultiParaItemFragment(marketOtherApis(),chain);
        MultiParaItemFragment Wallet = new MultiParaItemFragment(walletApis(),chain);
//        MultiParaItemFragment marketUI = new MultiParaItemFragment(marketUIAPIs(),chain);

        fragments.add(Auth);
        fragments.add(MarketMint);
//        fragments.add(MarketOther);
        fragments.add(Wallet);
//        fragments.add(marketUI);

        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(mActivity.getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapteradapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private List<MultiItemData.MultiItem> authApis() {
        List<MultiItemData.MultiItem> items = new ArrayList<>();
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
                DemoAPI.IS_LOGGED,"",
                "Check current user's login state.",
                "Check",
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
                DemoAPI.SUI_GET_MINTED_COLLECTIONS,"Get minted collections",
                "This endpoint is used to fetch collections that user already minted",
                "Get",
                null,null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SUI_GET_NFT_ON_COLLECTION,"Get minted collections",
                "This endpoint is used to fetch NFTs on collection that user already minted",
                "Get",
                "collection_address",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SUI_MINT_COLLECTION,"Mint a new NFT Collection",
                "This endpoint is used to mint a new NFT collection. The collection can be type ERC1155 or ERC721",
                "Get",
                "name","symbol","description",null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SUI_MINT_NFT,"Mint NFT on the collection",
                "This endpoint is used to mint a new NFT on the collection.",
                "Get",
                "collection_address","name","description","image_url","to_wallet_address",null));

        return items;
    }
    private List<MultiItemData.MultiItem> marketOtherApis() {
        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                DemoAPI.FETCH_NFT_BY_CREATOR,"Fetch NFTs data by creator addresses",
                "Fetch NFTs data by creator addresses",
                "Fetch",
                "crator","limit","offset",null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.FETCH_NFT_BY_OWNER_ADDRESSES,"Fetch NFT by owner addresses",
                "Fetch multiple NFTs data by owner addresses",
                "FETCH_BY_OWNER",
                "owner_address","limit",null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.FETCH_NFT_BY_MINT_ADDRESSES,"Fetch multiple NFTs data by mint addresses","Fetch multiple NFTs data by mint addresses",
                        "FETCH_BY_MINT","token_address_1","token_id_1",
                        "token_address_2","token_id_2",null,null));
        items.add(

                new MultiItemData.MultiItem(DemoAPI.FETCH_NFT_BY_UPDATE_AUTHORITIES,"Fetch multiple NFTs data by update authority addresses","Fetch multiple NFTs data by update authority addresses",
                        "FETCH_BY_AUTHORITIES","update_authorities","limit",
                        "offset",null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.FETCH_SINGLE_NFT_DETAILS,"Fetch single NFT details","Fetch single NFT details",
                        "FETCH_SINGLE_NFT","token_address","token_id",
                        null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.FETCH_NFT_MARKETPLACE_ACTIVITY,"Fetch activity of a single NFT","Fetch activity of a single NFT",
                        "FETCH_NFT_ACTIVITY","mint_address",null,
                        null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.TRANSFER_NFT_TO_ANOTHER_WALLET,"Transfer NFT to another solana wallet","Transfer NFT to another solana wallet",
                        "TRANSFER_NFT","collection_address","token_id",
                        "to_wallet_address",null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.BUY_NFT,"Buy NFT on the marketplace","Buy NFT on the marketplace",
                        "Buy_NFT","collection_address","token_id",
                        "price","marketplace_address",null,null));
        return items;
    }
    private List<MultiItemData.MultiItem> walletApis() {
        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SUI_GET_TRANSACTIONS,"Get wallet transaction by digest.","Get Sui transaction detail info.",
                "Get",
                "digest",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SUI_TRANSFER_SUI,"Transfer Sui to another address.","Transfer Sui to another address.",
                "Transfer",
                "to_publickey","amount",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SUI_TRANSFER_TOKEN,"Transfer token to another address.","Transfer Token to another address.",
                "Transfer",
                "to_publickey","amount","token",null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.SUI_GET_TOKENS,"Get wallet tokens.","Get Sui wallet tokens.",
                "GET",
                null,null,null,null,null,null));

        return items;
    }
    private List<MultiItemData.MultiItem> marketUIAPIs() {
        List<MultiItemData.MultiItem> items = new ArrayList<>();
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_COLLECTION_FILTER_INFO,"Get collection filter info.","Get collection filter info.",
                "Get",
                "collection",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_COLLECTION_INFO,"Get collection info.","Get a collection's info.",
                "Get",
                "collection 1",null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.METADATA_GET_COLLECTION_SUMMARY,"Get collection summary.","Get the collection summary info.",
                "Get",
                "collection 1","collection 2",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.METADATA_GET_NFT_INFO,"Get NFT info.","Get details in market of a NFT.",
                "Get",
                "contract","token_id",null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_NFT_EVENTS,"Get NFT events.","Get events of NFTs in market.",
                "Get",
                "contract","token_id","page","page size",null,null));
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
                DemoAPI.METADATA_GET_NFTS_BY_PARAMS,"Get NFTs.","Get details of NFTs in market place.",
                "Get",
                "collection address 1","page","page size","order by","desc","sale"));
        return items;
    }
}
