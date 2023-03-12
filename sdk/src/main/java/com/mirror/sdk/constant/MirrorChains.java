package com.mirror.sdk.constant;

public enum MirrorChains {
    Solana(101),
    Ethereum(201),
    Polygon(202),
    BNB(203);

    private int number;
    MirrorChains(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
}
