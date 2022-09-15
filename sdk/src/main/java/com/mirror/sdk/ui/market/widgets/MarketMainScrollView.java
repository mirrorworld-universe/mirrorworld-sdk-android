package com.mirror.sdk.ui.market.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

public class MarketMainScrollView extends ScrollView {
    public MarketMainScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        this.setVerticalScrollBarEnabled(false);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("Market l", String.valueOf(l));
        Log.i("Market t", String.valueOf(t));
        Log.i("Market oldl", String.valueOf(oldl));
        Log.i("Market oldt", String.valueOf(oldt));
    }
}
