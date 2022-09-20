//package com.mirror.sdk.ui.market.widgets;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//import com.mirror.sdk.R;
//
//public class DropTab extends ConstraintLayout {
//    private TextView mTextView;
//    private
//    public DropTab(@NonNull Context context) {
//        super(context);
//    }
//
//    public DropTab(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public DropTab(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    private void initView(Context context){
//        LayoutInflater.from(context).inflate(R.layout.market_filter_recyclerview_item,this);
//        mArrowImageUp = findViewById(R.id.extended_button_iv_up);
//        mArrowImageDown = findViewById(R.id.extended_button_iv_down);
//        mTextView = findViewById(R.id.extended_button_tv);
//
//        mArrowImageUp.setVisibility(View.VISIBLE);
//        mArrowImageDown.setVisibility(View.GONE);
//
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mIsOpen == true){
//                    foldView();
//                }else if(mIsOpen == false){
//                    expandView();
//                }
//            }
//        });
//    }
//}
