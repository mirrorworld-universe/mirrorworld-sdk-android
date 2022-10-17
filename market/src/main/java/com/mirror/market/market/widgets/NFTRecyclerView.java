//package com.mirror.market.market.widgets;
//
//import android.content.Context;
//import android.util.AttributeSet;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.market.market.MarketUIController;
//
//public class NFTRecyclerView extends RecyclerView {
//    public NFTRecyclerView(@NonNull Context context) {
//        super(context);
//    }
//
//    public NFTRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public NFTRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
////    @Override
////    public boolean onInterceptTouchEvent(MotionEvent e) {
////        if(MarketUIController.getInstance().isOut){
////            return false;
////        }else {
////            super.onInterceptTouchEvent(e);
////            return true;
////        }
////    }
//
////    @Override
////    public boolean onTouchEvent(MotionEvent e) {
////        Log.i("Recycler TOUCH:","--- ");
////        if(MarketUIController.getInstance().isOut){
////            Log.i("Recycler TOUCH:","OUT ");
////            return false;
////        }else {
////            Log.i("Recycler TOUCH:","Inner ");
////            super.onTouchEvent(e);
////            return true;
////        }
////    }
//}
