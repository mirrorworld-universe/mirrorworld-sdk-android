package com.mirror.sdk.ui.market.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainFilterDetailLayout extends ConstraintLayout {
    public MainFilterDetailLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public MainFilterDetailLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MainFilterDetailLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("DetailLayout","on touched");
                setVisibility(View.GONE);
                return false;
            }
        });
    }
}
