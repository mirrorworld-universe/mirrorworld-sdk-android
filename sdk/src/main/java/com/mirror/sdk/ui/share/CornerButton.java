package com.mirror.sdk.ui.share;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.mirror.sdk.R;

public class CornerButton extends CardView {
    private TextView mTextView;
    private ImageButton mImageButton;
    private int mFormalColor;
    private int mPressColor;
    public CornerButton(@NonNull Context context) {
        super(context);
        initViews(context);
    }

    public CornerButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public CornerButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public void init(boolean isWhiteStyle,String buttonStr, OnClickListener listener){
        mTextView.setText(buttonStr);
        mImageButton.setOnClickListener(listener);

        if(!isWhiteStyle){
            mFormalColor = getResources().getColor(R.color.mirror_button_blue);
            mPressColor = getResources().getColor(R.color.mirror_button_blue_press);
            mTextView.setTextColor(getResources().getColor(R.color.mirror_pure_white));
        }else {
            mFormalColor = getResources().getColor(R.color.mirror_button_white);
            mPressColor = getResources().getColor(R.color.mirror_button_white_press);
            mTextView.setTextColor(getResources().getColor(R.color.mirror_black));
        }
        mImageButton.setBackgroundColor(mFormalColor);
    }

    private void initViews(Context context){
        LayoutInflater.from(context).inflate(R.layout.coner_button,this);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mTextView = findViewById(R.id.corner_button_tv);
        mImageButton = findViewById(R.id.corner_button_ib);

        mImageButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mImageButton.setBackgroundColor(mPressColor);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    mImageButton.setBackgroundColor(mFormalColor);
                }
                return false;
            }
        });
    }
}
