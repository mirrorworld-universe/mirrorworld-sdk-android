package com.mirror.mirrorworld_sdk_android;

import static org.junit.Assert.assertEquals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.mirror.mirrorworld_sdk_android.adapter.APIPageIniterBase;
import com.mirror.mirrorworld_sdk_android.adapter.PageIniterEVM;
import com.mirror.mirrorworld_sdk_android.adapter.PageIniterSolana;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.sdk.constant.MirrorChains;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    public static Activity mActivity;

    private APIPageIniterBase mPageIniter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MirrorSDK","onCreate");
        mActivity = this;
        makeStatusBarTransparent(this);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

        int chain = getIntent().getIntExtra("chain",0);
        if(chain == MirrorChains.Solana.getNumber()){
            mPageIniter = new PageIniterSolana(this,MirrorChains.Solana);
        }else if(chain == MirrorChains.Ethereum.getNumber()){
            mPageIniter = new PageIniterEVM(this,MirrorChains.Ethereum);
        }else if(chain == MirrorChains.Polygon.getNumber()){
            mPageIniter = new PageIniterEVM(this,MirrorChains.Polygon);
        }else if(chain == MirrorChains.BNB.getNumber()){
            mPageIniter = new PageIniterEVM(this,MirrorChains.BNB);
        }
        mPageIniter.initPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MirrorSDK","onDestroy");
    }

    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}