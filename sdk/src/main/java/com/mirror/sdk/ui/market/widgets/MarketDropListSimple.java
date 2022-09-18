package com.mirror.sdk.ui.market.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mirror.sdk.R;
import com.mirror.sdk.ui.market.droplist.DropDownView;

public class MarketDropListSimple extends ConstraintLayout {

    private DropDownView mDropDownView;
    private View collapsedView;
    private View expandedView;


    public MarketDropListSimple(@NonNull Context context) {
        super(context);
//        initView(context);
    }

    public MarketDropListSimple(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        initView(context);
    }

    public MarketDropListSimple(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initView(context);
    }

//    private void initView(Context context){
//        LayoutInflater.from(context).inflate(R.layout.drop_down_view,this);
//        collapsedView = LayoutInflater.from(context).inflate(R.layout.drop_list_header, null, false);
//        expandedView = LayoutInflater.from(context).inflate(R.layout.drop_list_expanded, null, false);
//
//        mDropDownView.setHeaderView(collapsedView);
//        mDropDownView.setExpandedView(expandedView);
//
//        collapsedView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mDropDownView.isExpanded()) {
//                    mDropDownView.collapseDropDown();
//                } else {
//                    mDropDownView.expandDropDown();
//                }
//            }
//        });
//    }
}
