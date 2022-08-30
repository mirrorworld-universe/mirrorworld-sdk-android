package com.mirror.mirrorworld_sdk_android.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.PlaceholderContent;
import com.mirror.sdk.MirrorCallback;
import com.mirror.sdk.MirrorSDKJava;

import java.util.ArrayList;
import java.util.List;

/*** @author Pu
 * @createTime 2022/8/17 16:56
 * @projectName mirrorworld-sdk-android
 * @className APIItemRecyclerViewAdapter.java
 * @description TODO
 */
public class APIItemRecyclerViewAdapter extends  RecyclerView.Adapter<APIItemRecyclerViewAdapter.ViewHolder>{


    private  List<PlaceholderContent.PlaceholderItem> mValues;
    private Activity mContext;

    public APIItemRecyclerViewAdapter(List<PlaceholderContent.PlaceholderItem> items) {
       if(null == mValues){
           mValues =new ArrayList<PlaceholderContent.PlaceholderItem>();
       }
       mValues.clear();
       mValues.addAll(items);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("No."+mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mResultView.setText(mValues.get(position).details);
        holder.mButton.setText(mValues.get(position).buttonText);
        if(mValues.get(position).inputHint != null){
            holder.mEditText.setHint(mValues.get(position).inputHint);
        }else {
            holder.mEditText.setVisibility(View.GONE);
        }
        final int temb = position;
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(mValues.get(temb).id,holder);
            }
        });
    }

    private void handleClick(int apiId,ViewHolder holder){
        if(apiId == 1){
           // MirrorSDKJava.getInstance().StartLogin();
            MirrorSDKJava.getInstance().StartLogin(new MirrorCallback() {
                @Override
                public void callback(String result) {
                    Log.i("MyApp","Login flow successed!");
                }
            });
        }else if(apiId == 2){
            MirrorSDKJava.getInstance().GetAccessToken(mContext, new MirrorCallback() {
                @Override
                public void callback(String result) {
                    Toast.makeText(mContext,"Access TOken is:"+result,Toast.LENGTH_LONG);
                }
            });
        }else if(apiId == 3){
            String appId = String.valueOf(holder.mEditText.getText());
            Log.i("mirror","input appId is "+appId);
            MirrorSDKJava.getInstance().SetAppID("WsPRi3GQz0FGfoSklYUYzDesdKjKvxdrmtQ");
        }else if(apiId == 11){
            String emailAddr = String.valueOf(holder.mEditText.getText());
            MirrorSDKJava.getInstance().QueryUser(emailAddr, new MirrorCallback() {
                @Override
                public void callback(String s) {
                    holder.mResultView.setText(s);
                }
            });
        }
    }

    public void SetContext(Activity context){
        mContext = context;
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mResultView;
        public final Button mButton;
        public final EditText mEditText;
        public PlaceholderContent.PlaceholderItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.apiitem_number);
            mContentView = (TextView) view.findViewById(R.id.apiitem_content);
            mResultView = (TextView) view.findViewById(R.id.apiitem_text_result);
            mButton = (Button) view.findViewById(R.id.apiitem_button);
            mEditText = (EditText) view.findViewById(R.id.apiitem_edit);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }





    }


}
