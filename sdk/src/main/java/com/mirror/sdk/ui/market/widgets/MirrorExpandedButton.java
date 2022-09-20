package com.mirror.sdk.ui.market.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mirror.sdk.R;

public class MirrorExpandedButton extends ConstraintLayout {

    private TextView mTextView;
    private ImageView mArrowImageUp;
    private ImageView mArrowImageDown;

    private boolean mIsOpen = false;
    private OnExpandedButtonClick expandListener;

    public MirrorExpandedButton(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public MirrorExpandedButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MirrorExpandedButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.extended_button,this);
        mArrowImageUp = findViewById(R.id.extended_button_iv_up);
        mArrowImageDown = findViewById(R.id.extended_button_iv_down);
        mTextView = findViewById(R.id.extended_button_tv);

        mArrowImageUp.setVisibility(View.VISIBLE);
        mArrowImageDown.setVisibility(View.GONE);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsOpen == true){
                    foldView();
                }else if(mIsOpen == false){
                    expandView();
                }
            }
        });
    }

    public void expandView(){
        ObjectAnimator.ofFloat(mArrowImageUp, View.ROTATION.getName(), 180).start();
        mIsOpen = true;
//        mArrowImageUp.setVisibility(View.GONE);
//        mArrowImageDown.setVisibility(View.VISIBLE);
        if(expandListener != null) expandListener.OnExpand();
    }

    public void foldView(){
        ObjectAnimator.ofFloat(mArrowImageUp, View.ROTATION.getName(), -180, 0).start();
        mIsOpen = false;
//        mArrowImageUp.setVisibility(View.VISIBLE);
//        mArrowImageDown.setVisibility(View.GONE);
        if(expandListener != null) expandListener.OnFold();
    }

    public void setText(String text){
        mTextView.setText(text);
    }

    public void setExpandListener(OnExpandedButtonClick listener){
        expandListener = listener;
    }
}

