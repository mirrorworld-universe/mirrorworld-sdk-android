
package com.mirror.mirrorworld_sdk_android.GsonUtils;

@SuppressWarnings("unused")
public class Wallet {


    private String ethAddress;

    private String solAddress;

    public String getEthAddress() {
        return ethAddress;
    }

    public String getSolAddress() {
        return solAddress;
    }

    public static class Builder {

        private String ethAddress;
        private String solAddress;

        public Wallet.Builder withEthAddress(String ethAddress) {
            this.ethAddress = ethAddress;
            return this;
        }

        public Wallet.Builder withSolAddress(String solAddress) {
            this.solAddress = solAddress;
            return this;
        }

        public Wallet build() {
            Wallet wallet = new Wallet();
            wallet.ethAddress = ethAddress;
            wallet.solAddress = solAddress;
            return wallet;
        }

    }

}
