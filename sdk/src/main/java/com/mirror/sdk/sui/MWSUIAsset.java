package com.mirror.sdk.sui;

import com.mirror.sdk.chain.MWSUIWrapper;
import com.mirror.sdk.listener.universal.MirrorCallback;
import com.mirror.sdk.request.sui.ReqMintNFTAttribute;

import java.util.List;

public class MWSUIAsset {
    final public void getMintedCollections(MirrorCallback mirrorCallback){
        MWSUIWrapper.getMintedCollections(mirrorCallback);
    }

    final public void getNFTOnCollection(String collectionAddress, MirrorCallback mirrorCallback){
        MWSUIWrapper.getNFTOnCollection(collectionAddress,mirrorCallback);
    }

    final public void mintCollection(String name, String symbol, String description, String[] creators, MirrorCallback mirrorCallback){
        MWSUIWrapper.mintCollection(name,symbol,description,creators,mirrorCallback);
    }

    final public void mintNFT(String collection_address, String name, String description, String image_url, ReqMintNFTAttribute[] attributes, String to_wallet_address, MirrorCallback mirrorCallback){
        MWSUIWrapper.mintNFT(collection_address, name, description, image_url, attributes, to_wallet_address, mirrorCallback);
    }

    final public void queryNFT(String nft_object_id, MirrorCallback mirrorCallback){
        MWSUIWrapper.queryNFT(nft_object_id,mirrorCallback);
    }

    final public void searchNFTsByOwner(String owner_address, MirrorCallback mirrorCallback){
        MWSUIWrapper.searchNFTsByOwner(owner_address,mirrorCallback);
    }

    final public void searchNFTs(List<String> nft_object_ids, MirrorCallback mirrorCallback){
        MWSUIWrapper.searchNFTs(nft_object_ids,mirrorCallback);
    }

}
