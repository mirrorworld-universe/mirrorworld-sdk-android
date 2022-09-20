package com.mirror.sdk.ui.market.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mirror.sdk.R;


/**
 * @Author: yinp
 * @Date: 2021/12/6
 * @Description:
 */
public class DownSelectDialogView extends FrameLayout {
    private int contentWidth;
    private int contentHeight;
    /**
     * 是否时第一次初始化
     */
    private boolean isFirstLoad = false;
    /**
     * 是否能够结束动画
     */
    private boolean isCanEnd = false;
    /**
     * 判断弹窗是否打开了
     */
    private boolean isStarting = false;
    /**
     * 动画的时长
     */
    private long duration = 400;
    /**
     * 存储弹窗页面的矩形，用于点击事件计算，
     * 方便控制弹窗收回
     */
    private Rect rectContent;



    private FrameLayout dialogContentView;
    private RelativeLayout dialogBgView;
    private int bottomMargin = 100;

    public DownSelectDialogView(@NonNull Context context) {
        super(context);
    }

    public DownSelectDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DownSelectDialogView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFirstView(attrs);
    }

    private void initFirstView(AttributeSet attrs) {
        setVisibility(GONE);
        isFirstLoad = true;

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.DownSelectDialogView);
        bottomMargin = ta.getInteger(R.styleable.DownSelectDialogView_dsdvBottomMargin, bottomMargin);
        ta.recycle();

        dialogBgView = new RelativeLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogBgView.setLayoutParams(layoutParams);
        dialogBgView.setBackgroundColor(Color.BLACK);
        addView(dialogBgView, 0);

        dialogContentView = new FrameLayout(getContext());
        LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.bottomMargin = dpToPx(bottomMargin);
        dialogContentView.setLayoutParams(layoutParams2);
        addView(dialogContentView, 1);
    }

    public void mAddView(View child) {
        if (dialogContentView != null) {
            dialogContentView.addView(child);
        }
    }

    private String mFlag = "";

    public void mAddView(View child, String flag) {
        if (child != null && dialogContentView != null) {
            boolean isDiff = mFlag.equals(flag);
            if (!isDiff) {
                mRemoveView();
                dialogContentView.addView(child);
            }
            startAnimations(isDiff);
            mFlag = flag;
        }
    }

    public void mRemoveView() {
        if (dialogContentView != null && dialogContentView.getChildCount() > 0) {
            dialogContentView.removeAllViews();
        }
    }

    /**
     * 获取当前viewGroup中的所有子view
     * 对第一个子view重新摆放位置，放在上边，相当于隐藏，需要时再弹出
     * 所以必须固定格式
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getChildCount() != 2) {
            return;
        }
        if (isFirstLoad) {
            isFirstLoad = false;
            rectContent = new Rect();
        } else {
            contentWidth = dialogContentView.getWidth();
            contentHeight = dialogContentView.getHeight();

            rectContent.left = dialogContentView.getLeft();
            rectContent.top = dialogContentView.getTop();
            rectContent.right = dialogContentView.getRight();
            rectContent.bottom = dialogContentView.getBottom();

            dialogContentView.layout(0, 0, contentWidth, contentHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!rectContent.contains((int) event.getX(), (int) event.getY())) {
                    endAnimation();
                }
                break;
        }
        //必须对点击事件进行拦截
        return true;
    }

    /**
     * 弹窗打开和关闭时的动画
     */
    public void startAnimations(boolean isDiff) {
        if (isStarting || !isEnd) {
            return;
        }
        setVisibility(VISIBLE);
        if (!isDiff) {
            dialogContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    contentHeight = dialogContentView.getHeight();
                    start();
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            start();
        }
    }

    private void start() {
        //清理动画
        dialogContentView.clearAnimation();
        dialogBgView.clearAnimation();
        isCanEnd = false;
        isStarting = true;

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(dialogBgView, "alpha", 0, 0.4f);
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(dialogContentView, "translationY", -contentHeight * 1.0f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, translationAnimator);
        animatorSet.setDuration(duration);
        animatorSet.start();
        if (animatorSet.isStarted()) {
            isCanEnd = true;
        }
    }

    private boolean isEnd = true;

    /**
     * 结束动画
     */
    public void endAnimation() {
        //目的时判断当前是否有开启动画，有才允许关闭动画
        if (!isCanEnd) {
            return;
        }
        //清理动画
        dialogContentView.clearAnimation();
        dialogBgView.clearAnimation();
        isCanEnd = false;
        isEnd = false;
        isStarting = false;

        ObjectAnimator alphaOb = ObjectAnimator.ofFloat(dialogBgView, "alpha", 0.4f, 0f);
        ObjectAnimator translationYOb = ObjectAnimator.ofFloat(dialogContentView, "translationY", 0f, 1.0f * (-contentHeight));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaOb, translationYOb);
        animatorSet.setDuration(duration);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
                isEnd = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 监听弹窗的打开和关闭
     */
    private OnDialogOpenListener onDialogOpenListener;

    public interface OnDialogOpenListener {
        void onDialogOpen(boolean isOpen);
    }

    public void setOnDialogOpenListener(OnDialogOpenListener onDialogOpenListener) {
        this.onDialogOpenListener = onDialogOpenListener;
    }

    /**
     * 设置动画的时长
     *
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isStarting() {
        return isStarting;
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density; return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }
}


