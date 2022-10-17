//package com.mirror.market.market.widgets;
//
//import android.animation.ObjectAnimator;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.mirror.sdk.R;
//
//public class TabExpandButton extends ExpandButtonBase{
//
//    private ViewGroup mTotalView;
//    private View mCardView;
//    private TextView mTextView;
//    private ImageView mArrowImageDown;
//
//    public TabExpandButton(@NonNull Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public TabExpandButton(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public TabExpandButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView(context);
//    }
//
//    @Override
//    public void setText(String text) {
//        super.setText(text);
//        mTextView.setText(text);
//    }
//
//    private void initView(Context context){
//        LayoutInflater.from(context).inflate(R.layout.market_filter_recyclerview_item,this);
//        mTextView = findViewById(R.id.market_filter_recyclerview_item_tv);
//        mArrowImageDown = findViewById(R.id.market_filter_recyclerview_item_arrow_down);
//
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mIsExpanded == true){
//                    fold();
//                }else{
//                    expand();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void expand() {
//        super.expand();
//        ObjectAnimator.ofFloat(mArrowImageDown, ROTATION.getName(), 180).start();
//        if(mListener != null) mListener.onExpand();
//    }
//
//    @Override
//    public void fold() {
//        super.fold();
//        ObjectAnimator.ofFloat(mArrowImageDown, ROTATION.getName(), 0).start();
//        if(mListener != null) mListener.onFold();
//    }
//}
