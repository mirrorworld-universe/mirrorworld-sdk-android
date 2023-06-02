package com.mirror.sdk.evm;

import android.app.Activity;

import com.mirror.sdk.chain.MWEVMWrapper;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.request.ReqEVMFetchNFTsToken;

import java.util.List;

public class MWEVMAsset {

    //Asset/mint
    final public static void mintNFT(Activity returnActivity, String collection_address, int token_id, String confirmation, String to_wallet_address, MintNFTListener mintNFTListener) {
        MWEVMWrapper.mintNFT(returnActivity, collection_address, token_id, confirmation, to_wallet_address, mintNFTListener);
    }

    //Asset/NFT
    final public static void searchNFTsByOwner(String owners, int limit, MirrorCallback fetchByOwnerListener){
        MWEVMWrapper.fetchNFTsByOwnerAddresses(owners, limit, fetchByOwnerListener);
    }

    final public static void searchNFTsByMintAddress(List<ReqEVMFetchNFTsToken> tokens, MirrorCallback fetchByMintAddressListener){
        MWEVMWrapper.fetchNFTsByMintAddresses(tokens, fetchByMintAddressListener);
    }

    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, MirrorCallback listener){
        MWEVMWrapper.fetchNFTsByCreatorAddresses(creators, limit, offset, listener);
    }

    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, MirrorCallback listener){
        MWEVMWrapper.fetchNFTsByUpdateAuthorities(update_authorities, limit, offset, listener);
    }

    final public static void fetchNFTMarketplaceActivity(String mint_address, MirrorCallback fetchSingleNFTActivityListener){
        MWEVMWrapper.fetchNFTMarketplaceActivity(mint_address, fetchSingleNFTActivityListener);
    }

    final public static void mintCollection(Activity returnActivity, String contract_type, String detailUrl, String confirmation, MirrorCallback createTopCollectionListener){
        MWEVMWrapper.createVerifiedCollection(returnActivity, contract_type, detailUrl, confirmation, createTopCollectionListener);
    }

    //Asset/Auction
    final public static void transferNFT(Activity returnActivity, String collection_address, int token_id,String to_wallet_address, MirrorCallback transferNFTListener){
        MWEVMWrapper.transferNFT(returnActivity, collection_address, token_id, to_wallet_address, transferNFTListener);
    }

    final public static void listNFT(Activity returnActivity, String collection_address, int token_id, float price, String marketplace_address, MirrorCallback listener){
        MWEVMWrapper.listNFT(returnActivity, collection_address, token_id, price, marketplace_address, listener);
    }

    final public static void cancelNFTListing(Activity returnActivity, String collection_address, int token_id,String marketplace_address, MirrorCallback listener){
        MWEVMWrapper.cancelNFTListing(returnActivity, collection_address, token_id, marketplace_address, listener);
    }

    final public static void buyNFT(Activity returnActivity, String collection_address, int token_id,float price,String marketplace_address, MirrorCallback buyNFTListener){
        MWEVMWrapper.buyNFT(returnActivity, collection_address, token_id, price, marketplace_address, buyNFTListener);
    }

    //Asset/NFT
    final public static void queryNFT(String contract, String tokenID, MirrorCallback fetchSingleNFT){
        MWEVMWrapper.getNFTDetails(contract,tokenID, fetchSingleNFT);
    }
}
