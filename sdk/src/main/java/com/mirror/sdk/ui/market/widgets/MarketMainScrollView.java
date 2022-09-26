package com.mirror.sdk.ui.market.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.utils.MarketUtils;

public class MarketMainScrollView extends NestedScrollView{
    Context mContext;
    public MarketMainScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        this.setVerticalScrollBarEnabled(false);
        mContext = context;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i("1111","onStartNestedScroll");

        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.i("1111","onNestedScrollAccepted");
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.i("1111","onStopNestedScroll");
        super.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i("1111","onNestedScroll");
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i("1111","onNestedFling");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        Log.i("1111","onNestedPreScroll");
        boolean isParentScroll = dispatchNestedPreScroll(dx, dy, consumed, null, type);
        // 在父嵌套布局没有滑动时，处理此控件是否需要滑动
//        Log.i("Scroll parent", "isParentScroll :" + isParentScroll);
        if (!isParentScroll) {
            // 向上滑动且此控件没有滑动到底部时，需要让此控件继续滑动以保证滑动连贯一致性
            boolean needKeepScroll = dy > 0 && !isScrollEnd();
            if (needKeepScroll) {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
//        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    private float mLastY;
    private float mNestedYOffsets = 0;
    private float mVelocityY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(MarketUIController.getInstance().IsFilterDetailExpanded){
            MarketUIController.getInstance().foldFilterDetail(true);
            return true;
        }
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            mLastY = e.getY();
            mNestedYOffsets = 0;
            mVelocityY = 0;
//            stopScroll();
        }
        RecyclerView child = MarketUIController.getInstance().mMainInnerRecycler;
        boolean handle = false;
        if (child != null) {
            if (isScrollEnd() || (handle = !isChildScrollTop(child))) {
                int deltaY = (int) (mLastY - e.getY());
                child.scrollBy(0, deltaY);
                if (handle) {
                    // 子嵌套布局向下滑动时，要记录y轴的偏移量
                    mNestedYOffsets += deltaY;
                }
            }
        }
        mLastY = e.getY();
        // 更新触摸事件的偏移位置，以保证视图平滑的连贯性
        e.offsetLocation(0, mNestedYOffsets);
        return handle || super.onTouchEvent(e);
    }

    private boolean isScrollEnd() {
        return !this.canScrollVertically(1);
//        return isSlideToBottom(this);
    }

    private boolean isChildScrollTop(RecyclerView child) {
        return !child.canScrollVertically(-1);
    }

//    private boolean isOut = true;
//    private int totalUp = 40;//42
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if(t >= MarketUtils.dpToPx(mContext,totalUp)){
//            Log.e("Market","no out!");
//            isOut = false;
//        }
//        else if(t <= 0){
//            Log.e("Market","is out!");
//            isOut = true;
//        }
//    }
}
