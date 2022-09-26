package com.mirror.sdk.ui.market.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ExpandButtonBase extends ConstraintLayout {
    protected boolean mIsExpanded = false;
    protected OnExpandListener mListener;
    public ExpandButtonBase(@NonNull Context context) {
        super(context);
    }

    public ExpandButtonBase(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandButtonBase(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text){

    }

    public void expand(){
        mIsExpanded = true;
    }

    public void fold(){
        mIsExpanded = false;
    }

    public void setExpandListener(OnExpandListener listener){
        mListener = listener;
    }

    public boolean getIsExpanded(){
        return mIsExpanded;
    }

    public interface OnExpandListener{
        void onExpand();
        void onFold();
    }
}
