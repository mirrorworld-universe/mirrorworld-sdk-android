package com.mirror.mirrorworld_sdk_android.adapter;

import static org.junit.Assert.assertEquals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;
import com.mirror.sdk.constant.MirrorChains;
import com.mirror.sdk.MirrorSDK;

import java.util.ArrayList;
import java.util.List;

/*** @author Pu
 * @createTime 2022/8/17 17:12
 * @projectName mirrorworld-sdk-android
 * @className MultiParaItemRecyclerViewAdapter.java
 * @description TODO
 */
public class MultiParaItemRecyclerViewAdapter extends RecyclerView.Adapter<MultiParaItemRecyclerViewAdapter.ViewHolder>{

    private List<MultiItemData.MultiItem> mValues;
    private Activity mActivity;
    private MirrorChains mChain;
    private ClickHandlerBase mClickHandler;

    public MultiParaItemRecyclerViewAdapter(List<MultiItemData.MultiItem> items, MirrorChains chain, Activity context) {
        if(null == mValues){
            this.mValues = new ArrayList<MultiItemData.MultiItem>();
        }
        this.mValues.clear();
        this.mValues.addAll(items);
        this.mChain = chain;
        mActivity = context;
        if(mChain == MirrorChains.Solana){
            mClickHandler = new ClickHandlerSolana(mActivity);
        }else if(mChain == MirrorChains.Ethereum){
            mClickHandler = new ClickHandlerEVM(mActivity);
        }else if(mChain == MirrorChains.Polygon){
            mClickHandler = new ClickHandlerEVM(mActivity);
        }else if(mChain == MirrorChains.BNB){
            mClickHandler = new ClickHandlerEVM(mActivity);
        }else if(mChain == MirrorChains.SUI){
            mClickHandler = new ClickHandlerSUI(mActivity);
        }else {
            MirrorSDK.logWarn("Unknow chain!");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String hintPre = "Param:";
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("No."+mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).name);
        holder.mButton.setText(mValues.get(position).buttonText);
        holder.mResultView.setText(mValues.get(position).resultDefault);
        if(mValues.get(position).et1Hint == null){
            holder.mEt1.setVisibility(View.GONE);
        }else {
            holder.mEt1.setVisibility(View.VISIBLE);
            holder.mEt1.setHint(hintPre+mValues.get(position).et1Hint);
        }
        if(mValues.get(position).et2Hint == null){
            holder.mEt2.setVisibility(View.GONE);
        }else {
            holder.mEt2.setVisibility(View.VISIBLE);
            holder.mEt2.setHint(hintPre+mValues.get(position).et2Hint);
        }
        if(mValues.get(position).et3Hint == null){
            holder.mEt3.setVisibility(View.GONE);
        }else {
            holder.mEt3.setVisibility(View.VISIBLE);
            holder.mEt3.setHint(hintPre+mValues.get(position).et3Hint);
        }
        if(mValues.get(position).et4Hint == null){
            holder.mEt4.setVisibility(View.GONE);
        }else {
            holder.mEt4.setVisibility(View.VISIBLE);
            holder.mEt4.setHint(hintPre+mValues.get(position).et4Hint);
        }
        if(mValues.get(position).et5Hint == null){
            holder.mEt5.setVisibility(View.GONE);
        }else {
            holder.mEt5.setVisibility(View.VISIBLE);
            holder.mEt5.setHint(hintPre+mValues.get(position).et5Hint);
        }
        if(mValues.get(position).et6Hint == null){
            holder.mEt6.setVisibility(View.GONE);
        }else {
            holder.mEt6.setVisibility(View.VISIBLE);
            holder.mEt6.setHint(hintPre+mValues.get(position).et6Hint);
        }
        if(mValues.get(position).spinnerData == null){
            holder.mSpinner.setVisibility(View.GONE);
        }else {
            holder.mSpinner.setVisibility(View.VISIBLE);
//            initAPIItemSpinner(holder.mSpinner,holder.mItem);
        }
        if(mValues.get(position).spinnerData2 == null){
            holder.mSpinner2.setVisibility(View.GONE);
        }else {
            holder.mSpinner2.setVisibility(View.VISIBLE);
//            initAPIItemSpinner2(holder.mSpinner2,holder.mItem);
        }

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickHandler.handleClick(mActivity,mValues.get(position).id,holder,view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void showToast(String content){
        Toast.makeText(mActivity,content,Toast.LENGTH_LONG).show();
    }

    private boolean checkEt(EditText et1){
        if(et1 == null || (et1 != null && et1.getText().length() == 0)){
            Log.e("MirrorSDK","edit text is null!"+et1.getText().length());
            return false;
        }
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
//        public final TextView mContentView;
        public final Button mButton;
        public final TextView mResultView;
        public final EditText mEt1;
        public final EditText mEt2;
        public final EditText mEt3;
        public final EditText mEt4;
        public final EditText mEt5;
        public final EditText mEt6;
        public ConstraintLayout mSpinner;
        public ConstraintLayout mSpinner2;
        public MultiItemData.MultiItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.multiapi_item_number);
            mButton = (Button) view.findViewById(R.id.multiapi_item_button);
            mResultView = (TextView) view.findViewById(R.id.multiapi_item_result);
            mEt1 = (EditText) view.findViewById(R.id.multiapi_item_paramet1);
            mEt2 = (EditText) view.findViewById(R.id.multiapi_item_paramet2);
            mEt3 = (EditText) view.findViewById(R.id.multiapi_item_paramet3);
            mEt4 = (EditText) view.findViewById(R.id.multiapi_item_paramet4);
            mEt5 = (EditText) view.findViewById(R.id.multiapi_item_paramet5);
            mEt6 = (EditText) view.findViewById(R.id.multiapi_item_paramet6);
            mSpinner = (ConstraintLayout) view.findViewById(R.id.api_item_spinner);
            mSpinner2 = (ConstraintLayout) view.findViewById(R.id.api_item_spinner2);
        }
    }

    private String getStringFromEditText(EditText editText){
        return String.valueOf(editText.getText());
    }
}
