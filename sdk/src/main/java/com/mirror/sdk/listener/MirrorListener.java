package com.mirror.sdk.listener;

import com.mirror.sdk.response.auth.UserResponse;
import com.mirror.sdk.response.market.NFTObject;

public class MirrorListener {

    public interface LoginListener{
        void onLoginSuccess();
        void onLoginFail();
    }

    public interface FetchUserListener{
        void onUserFetched(UserResponse userResponse);
        void onFetchFailed(long code,String message);
    }

    public interface QueryUserListener{
        void onQuerySuccess(UserResponse userResponse);
        void onQueryFailed(long code,String message);
    }

    public interface CreateTopCollectionListener{
        void onCreateSuccess(UserResponse userResponse);
        void onCreateFailed(long code,String message);
    }

    public interface CreateSubCollectionListener{
        void onCreateSuccess(UserResponse userResponse);
        void onCreateFailed(long code,String message);
    }

    public interface MintNFTListener{
        void onMintSuccess(UserResponse userResponse);
        void onMintFailed(long code,String message);
    }

    public interface ListNFTListener{
        void onListSuccess(UserResponse userResponse);
        void onListFailed(long code,String message);
    }

    public interface UpdateListListener{
        void onUpdateSuccess(UserResponse userResponse);
        void onUpdateFailed(long code,String message);
    }

    public interface CancelListListener{
        void onCancelSuccess(UserResponse userResponse);
        void onCancelFailed(long code,String message);
    }

    public interface FetchByOwnerListener{
        void onFetchSuccess(UserResponse userResponse);
        void onFetchFailed(long code,String message);
    }

    public interface FetchByMintAddressListener{
        void onFetchSuccess(UserResponse userResponse);
        void onFetchFailed(long code,String message);
    }

    public interface FetchSingleNFTListener{
        void onFetchSuccess(UserResponse userResponse);
        void onFetchFailed(long code,String message);
    }

    public interface  FetchSingleNFTActivityListener{
        void onFetchSuccess(UserResponse userResponse);
        void onFetchFailed(long code,String message);
    }

    public interface TransferNFTListener{
        void onTransferSuccess(UserResponse userResponse);
        void onTransferFailed(long code,String message);
    }

    public interface GetWalletTokenListener{
        void onSuccess(UserResponse userResponse);
        void onFailed(long code,String message);
    }

    public interface GetWalletTransactionListener{
        void onSuccess(UserResponse userResponse);
        void onFailed(long code,String message);
    }

    public interface GetWalletTransactionBySigListener{
        void onSuccess(UserResponse userResponse);
        void onFailed(long code,String message);
    }

    public interface TransferSQLListener{
        void onTransferSuccess(UserResponse userResponse);
        void onTransferFailed(long code,String message);
    }


    public interface FetchSingleNFT{
        void onNFTFetched(NFTObject nftObject);
        void onNFTFetchFailed(long code,String message);
    }


}
