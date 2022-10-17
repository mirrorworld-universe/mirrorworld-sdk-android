//package com.mirror.sdk.ui.market.dialogs;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.ViewGroup;
//import android.view.Window;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.sdk.R;
//import com.mirror.sdk.ui.market.adapters.MainDialogAdapter;
//import com.mirror.sdk.ui.market.apis.responses.CollectionFilter;
//import com.mirror.sdk.ui.market.enums.MirrorMarketConfig;
//import com.mirror.sdk.ui.market.widgets.MainRecyclerView;
//
//public class MainDialog extends DialogFragment {
//
//    Activity mActivity;
//    MainRecyclerView mTotalView;
//
//
//    public void init(Activity activity){
//        mActivity = activity;
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        if(mActivity == null){
//            Log.e("MirrorMarket","Must init first!");
//            return null;
//        }
//
//        mTotalView = (MainRecyclerView) mActivity.getLayoutInflater().inflate(R.layout.market_main3, null);
//        initView();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        builder.setView(mTotalView);
//        Dialog dialog = builder.create();
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//
//        return dialog;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            Window window = dialog.getWindow();
//            if (window != null) {
//                int width = ViewGroup.LayoutParams.MATCH_PARENT;
//                int height = ViewGroup.LayoutParams.MATCH_PARENT;
//                window.setLayout(width, height);
//                if(MirrorMarketConfig.FULL_SCREEN_MODE) window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            }
//        }
//    }
//
//    private void initView(){
//        mTotalView.init(mActivity);
//        MainDialogAdapter mainRecyclerAdapter = new MainDialogAdapter();
//        mTotalView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mTotalView.setAdapter(mainRecyclerAdapter);
//    }
//}
