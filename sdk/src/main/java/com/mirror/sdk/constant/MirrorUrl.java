package com.mirror.sdk.constant;

public class MirrorUrl {
    public static final String urlAuth = "https://auth-staging.mirrorworld.fun/";
    //auth
    public static final String urlRefreshToken = "auth/refresh-token";
    public static final String urlQueryUser = "auth/user";
    public static final String isAuthenticated = "auth/me";
    public static final String LoginWithEmail = "auth/login";
    public static final String urlMe = "auth/me";

    //market
    public static final String urlQueryNFTDetail = "solana/nft/";
    public static final String urlFetchMultiNFTsDataByMintAddress = "solana/nft/mints";
    public static final String urlFetchMultiNFTsDataByCreatorAddress = "solana/nft/creators";
    public static final String urlFetchMultiNFTsDataByUpdateAuthorityAddress = "solana/nft/update-authorities";
    public static final String urlMintNFTCollection = "solana/mint/nft";
    public static final String urlMintTopLevelCollection = "solana/mint/collection";
    public static final String urlMintLowerLevelCollection = "solana/mint/sub-collection";
    public static final String urlFetchmultipleNFTs = "solana/nft/owners";
    public static final String urlFetchActivity = "solana/nft/activity/";
    public static final String urlListNFTOnTheMarketplace = "solana/marketplace/list";
    public static final String urlUpdateListingOfNFTOnTheMarketplace = "solana/marketplace/update";
    public static final String urlBuyNFTOnTheMarketplace = "solana/marketplace/buy";
    public static final String urlCancelListingOfNFTOnTheMarketplace = "solana/marketplace/cancel";
    public static final String urlTransferNFTToAnotherSolanaWallet = "solana/marketplace/transfer";

    //wallet
    public static final String TransferSQL = "wallet/transfer-sol";
    public static final String TransferToken = "wallet/transfer-token";
    public static  final String GetWalletToken = "wallet/tokens";
    public static final String GetWalletTransactions = "wallet/transactions";
}
