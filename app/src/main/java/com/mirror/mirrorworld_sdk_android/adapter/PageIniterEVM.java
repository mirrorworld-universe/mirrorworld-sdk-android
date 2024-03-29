package com.mirror.mirrorworld_sdk_android.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mirror.mirrorworld_sdk_android.enums.DemoAPI;
import com.mirror.mirrorworld_sdk_android.FragmentAdapter;
import com.mirror.mirrorworld_sdk_android.MultiParaItemFragment;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.sdk.constant.MirrorChains;

import java.util.ArrayList;
import java.util.List;

public class PageIniterEVM extends APIPageIniterBase{

    public PageIniterEVM(AppCompatActivity activity, MirrorChains chain) {
        super(activity, chain);
    }

    public void initPage(){
        MirrorChains chain = mChain;
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

        MultiParaItemFragment Auth = new MultiParaItemFragment(authApis(),chain);
        MultiParaItemFragment MarketMint = new MultiParaItemFragment(marketMintApis(),chain);
        MultiParaItemFragment MarketOther = new MultiParaItemFragment(marketOtherApis(),chain);
        MultiParaItemFragment Wallet = new MultiParaItemFragment(walletApis(),chain);
        MultiParaItemFragment marketUI = new MultiParaItemFragment(marketUIAPIs(),chain);

        fragments.add(Auth);
        fragments.add(MarketMint);
        fragments.add(MarketOther);
        fragments.add(Wallet);
        fragments.add(marketUI);

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
                DemoAPI.CREATE_VERIFIED_COLLECTION,"Mint New Top-level Collection",
                "This request is using API Key from collectionMirror World Mobile SDK",
                "TOP_COLLECTION",
                "contract_type","json_url",null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.MINT_NFT,"Mint New NFT on Collection","This request is using API Key from collectionMirror World Mobile SDK",
                        "MINT_NFT","collection_address","token_id",
                        "to_wallet_address",null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.LIST_NFT,"List NFT on the marketplace","List NFT on the marketplace by use mint address",
                        "LIST_NFT","collection_address","token_id",
                        "price","marketplace_address",null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.CANCEL_NFT_LISTING,"Cancel listing of NFT on the marketplace","Cancel listing of NFT on the marketplace",
                        "CANCEL_NFT_LISTING","collection_address","token_id",
                        "marketplace_address",null,null,null));
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
                DemoAPI.GET_WALLET_TOKEN,"Get wallet tokens.","Get wallet tokens.",
                "Get_Wallet_Token",
                null,null,null,null,null,null));
        items.add(new MultiItemData.MultiItem(
                DemoAPI.GET_TOKENS_BY_WALLET,"Get tokens by wallet.","Get tokens by wallet.",
                "Get tokens",
                "wallet address",null,null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.WALLET_TRANSACTIONS,"Get wallet transactions.","Get wallet transactions.",
                        "GetTransactions","limit",null,
                        null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.WALLET_TRANSACTIONS_BY_WALLET,"Get wallet transactions by wallet.","Get wallet transactions by wallet address.",
                        "GetTransactions","wallet_address","limit",
                        null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.WALLET_TRANSACTIONS_BY_SIGNATURE,"Get wallet transaction by signature","Get wallet transaction by signature",
                        "TRANSACTIONS_SIG","signature",null,
                        null,null,null,null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.TRANSFER_TOKEN,"Transfer Token","Transfer SPL Token to another wallet.",
                        "TRANSFER","nonce","gasPrice",
                        "gasLimit","to","amount","contract"));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.TRANSFER_ETH,"Transfer ETH","Transfer ETH to another wallet.",
                        "TRANSFER","nonce","gasPrice",
                        "gasLimit","to","amount",null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.TRANSFER_BNB,"Transfer BNB","Transfer BNB to another wallet.",
                        "TRANSFER","nonce","gasPrice",
                        "gasLimit","to","amount",null));
        items.add(
                new MultiItemData.MultiItem(DemoAPI.TRANSFER_MATIC,"Transfer Matic","Transfer Matic to another wallet.",
                        "TRANSFER","nonce","gasPrice",
                        "gasLimit","to","amount",null));
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
