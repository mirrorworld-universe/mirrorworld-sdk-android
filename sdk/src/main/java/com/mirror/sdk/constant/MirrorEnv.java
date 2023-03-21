package com.mirror.sdk.constant;

public enum MirrorEnv {
    MainNet(1),
    DevNet(2),
    StagingMainNet(3),
    StagingDevNet(4);

    private int number;
    MirrorEnv(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }
}
