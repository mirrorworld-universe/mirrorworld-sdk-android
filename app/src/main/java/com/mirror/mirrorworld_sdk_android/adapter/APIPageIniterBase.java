package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.sdk.constant.MirrorChains;

public class APIPageIniterBase {
    TabLayout mTabLayout;
    AppCompatActivity mActivity;
    ViewPager mViewPager;
    MirrorChains mChain;
    public APIPageIniterBase(AppCompatActivity activity, MirrorChains chain){
        mActivity = activity;
        mTabLayout = (TabLayout) mActivity.findViewById(R.id.tabs);
        mViewPager = (ViewPager) mActivity.findViewById(R.id.viewpager);
        mChain = chain;
    }

    public void initPage(){

    }
}
