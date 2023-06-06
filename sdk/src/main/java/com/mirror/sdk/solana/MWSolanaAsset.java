package com.mirror.sdk.solana;

import android.app.Activity;

import com.mirror.sdk.chain.MWSolanaWrapper;
import com.mirror.sdk.listener.confirmation.CheckStatusOfMintingListener;
import com.mirror.sdk.listener.market.BuyNFTListener;
import com.mirror.sdk.listener.market.CancelListListener;
import com.mirror.sdk.listener.market.CreateTopCollectionListener;
import com.mirror.sdk.listener.market.FetchByOwnerListener;
import com.mirror.sdk.listener.market.FetchNFTsListener;
import com.mirror.sdk.listener.market.FetchSingleNFTActivityListener;
import com.mirror.sdk.listener.market.FetchSingleNFTListener;
import com.mirror.sdk.listener.market.ListNFTListener;
import com.mirror.sdk.listener.market.MintNFTListener;
import com.mirror.sdk.listener.market.TransferNFTListener;
import com.mirror.sdk.listener.market.UpdateListListener;

import java.util.List;

public class MWSolanaAsset {
    //Asset/NFT
    final public static void fetchNFTsByOwnerAddresses(List<String> owners, int limit, int offset, FetchByOwnerListener fetchByOwnerListener) {
        MWSolanaWrapper.fetchNFTsByOwnerAddresses(owners, limit, offset, fetchByOwnerListener);
    }

    final public static void fetchNFTsByMintAddresses(List<String> mint_addresses, FetchNFTsListener fetchByMintAddressListener) {
        MWSolanaWrapper.fetchNFTsByMintAddresses(mint_addresses, fetchByMintAddressListener);
    }

    final public static void fetchNFTsByCreatorAddresses(List<String> creators, int limit, int offset, FetchNFTsListener listener) {
        MWSolanaWrapper.fetchNFTsByCreatorAddresses(creators, limit, offset, listener);
    }

    final public static void fetchNFTsByUpdateAuthorities(List<String> update_authorities, int limit, int offset, FetchNFTsListener listener) {
        MWSolanaWrapper.fetchNFTsByUpdateAuthorities(update_authorities, limit, offset, listener);
    }

    final public static void fetchNFTMarketplaceActivity(String mint_address, FetchSingleNFTActivityListener fetchSingleNFTActivityListener) {
        MWSolanaWrapper.fetchNFTMarketplaceActivity(mint_address, fetchSingleNFTActivityListener);
    }

    final public static void createVerifiedCollection(Activity returnActivity, String name, String symbol, String detailUrl, String confirmation, CreateTopCollectionListener createTopCollectionListener) {
        MWSolanaWrapper.createVerifiedCollection(returnActivity, name, symbol, detailUrl, confirmation, createTopCollectionListener);
    }

    final public static void mintNFT(Activity returnActivity, String collection_mint, String detailUrl, String confirmation, MintNFTListener mintNFTListener) {
        MWSolanaWrapper.mintNFT(returnActivity, collection_mint, detailUrl, confirmation, mintNFTListener);
    }

    //Confirmation
    final public static void checkMintingStatus(List<String> mintAddresses, CheckStatusOfMintingListener listener) {
        MWSolanaWrapper.checkStatusOfMinting(mintAddresses, listener);
    }

    final public static void checkTransactionsStatus(List<String> signatures, CheckStatusOfMintingListener listener) {
        MWSolanaWrapper.checkStatusOfTransactions(signatures, listener);
    }

    final public static void transferNFT(Activity returnActivity, String mint_address, String to_wallet_address, String confirmation, TransferNFTListener transferNFTListener) {
        MWSolanaWrapper.transferNFT(returnActivity, mint_address, to_wallet_address, confirmation, transferNFTListener);
    }

    final public static void updateNFTListing(String mint_address, Double price, String confirmation, UpdateListListener listener) {
        MWSolanaWrapper.updateNFTListing(mint_address, price, confirmation, listener);
    }

    final public static void updateNFT(Activity returnActivity, String mintAddress, String name, String symbol, String updateAuthority, String NFTJsonUrl, int seller_fee_basis_points, MintNFTListener mintNFTListener) {
        MWSolanaWrapper.updateNFTProperties(returnActivity, mintAddress, name, symbol, updateAuthority, NFTJsonUrl, seller_fee_basis_points, mintNFTListener);
    }

    final public static void listNFT(Activity returnActivity, String mint_address, Double price, String confirmation, ListNFTListener listener) {
        MWSolanaWrapper.listNFT(returnActivity, mint_address, price, confirmation, listener);
    }

    final public static void cancelNFTListing(Activity returnActivity, String mint_address, Double price, String auction_house, CancelListListener listener) {
        MWSolanaWrapper.cancelNFTListing(returnActivity, mint_address, price, auction_house, listener);
    }

    final public static void queryNFT(String mint_address, FetchSingleNFTListener fetchSingleNFT) {
        MWSolanaWrapper.getNFTDetails(mint_address, fetchSingleNFT);
    }

    final public static void buyNFT(Activity returnActivity, String mint_address, Double price, String auctionHouse, BuyNFTListener buyNFTListener) {
        MWSolanaWrapper.buyNFT(returnActivity, mint_address, price, auctionHouse, buyNFTListener);
    }
}
