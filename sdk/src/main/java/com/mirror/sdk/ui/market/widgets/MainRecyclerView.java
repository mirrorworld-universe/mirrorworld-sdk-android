package com.mirror.sdk.ui.market.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.sdk.ui.market.MarketUIController;
import com.mirror.sdk.ui.market.utils.MarketUtils;

public class MainRecyclerView extends RecyclerView implements NestedScrollingParent3,NestedScrollingParent2 {
    private Activity mContext;
    private ViewGroup mContentView;
    private LayoutManager mLayoutManager;
    private Adapter mAdapter;

    private float mLastY;
    private float mNestedYOffsets = 0;
    private float mVelocityY = 0;

    public void init(Activity activity) {
        mContext = activity;
    }

    public MainRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MainRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onChildAttachedToWindow(@NonNull View child) {
        if (isTargetPosition(child)) {
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            lp.height = MarketUtils.getScreenHeight(mContext);//getMeasuredHeight();
            child.setLayoutParams(lp);
            mContentView = (ViewGroup) child;
        }
    }

    @Override
    public void onChildDetachedFromWindow(@NonNull View child) {
        if (isTargetPosition(child)) {
            mContentView = null;
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = adapter;
    }

    private void init(Context context) {
        mLayoutManager = new LinearLayoutManager(context);
    }

    protected boolean isTargetPosition(View child) {
        if (mLayoutManager != null && mAdapter != null) {
            int position = mLayoutManager.getPosition(child);
            return position + 1 == mAdapter.getItemCount();
        }
        return false;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        onNestedScrollInternal(dyUnconsumed, type, consumed);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        boolean isParentScroll = dispatchNestedPreScroll(dx, dy, consumed, null, type);
        // 在父嵌套布局没有滑动时，处理此控件是否需要滑动
        Log.i("Scroll parent", "isParentScroll :" + isParentScroll);
        if (!isParentScroll) {
            // 向上滑动且此控件没有滑动到底部时，需要让此控件继续滑动以保证滑动连贯一致性
            boolean needKeepScroll = dy > 0 && !isScrollEnd();
            if (needKeepScroll) {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            mLastY = e.getY();
            mNestedYOffsets = 0;
            mVelocityY = 0;
            stopScroll();
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

    private void onNestedScrollInternal(int dyUnconsumed, int type, @Nullable int[] consumed) {
        final int oldScrollY = computeVerticalScrollOffset();
        scrollBy(0, dyUnconsumed);
        final int myConsumed = computeVerticalScrollOffset() - oldScrollY;

        if (consumed != null) {
            consumed[1] += myConsumed;
        }
        final int myUnconsumed = dyUnconsumed - myConsumed;

        dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null, type, consumed);
    }

    private boolean isScrollEnd() {
        return !this.canScrollVertically(1);
//        return isSlideToBottom(this);
    }

    private boolean isChildScrollTop(RecyclerView child) {
        return !child.canScrollVertically(-1);
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
