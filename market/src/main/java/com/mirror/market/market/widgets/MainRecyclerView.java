//package com.mirror.market.market.widgets;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.AttributeSet;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.market.market.MarketUIController;
//import com.mirror.sdk.ui.market.utils.MarketUtils;
//
//public class MainRecyclerView extends RecyclerView{
//    private Activity mContext;
//    private OnBottomListener mListener;
//
//    public void init(Activity activity,OnBottomListener listener) {
//        mContext = activity;
//        mListener = listener;
//    }
//
//    public MainRecyclerView(@NonNull Context context) {
//        super(context);
//        init(context);
//    }
//
//    public MainRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public MainRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        if(isScrollEnd()){
//            if(mListener != null) mListener.OnBottom();
//        }
//    }
//
//    private void init(Context context){
//
//    }
//
//    public interface OnBottomListener{
//        void OnBottom();
//    }
//
//    private boolean isScrollEnd() {
//        return !this.canScrollVertically(1);
//    }
//
//    private boolean isChildScrollTop(RecyclerView child) {
//        return !child.canScrollVertically(-1);
//    }
//
//    public static boolean isSlideToBottom(RecyclerView recyclerView) {
//        if (recyclerView == null) return false;
//        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
//                >= recyclerView.computeVerticalScrollRange())
//            return true;
//        return false;
//    }
//}
