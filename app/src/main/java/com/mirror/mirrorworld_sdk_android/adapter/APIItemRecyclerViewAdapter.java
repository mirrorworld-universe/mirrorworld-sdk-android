package com.mirror.mirrorworld_sdk_android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mirror.mirrorworld_sdk_android.R;
import com.mirror.mirrorworld_sdk_android.data.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;


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
//        if(apiId == MirrorConstant.START_LOGIN) {
//           // MirrorSDKJava.getInstance().StartLogin();
//            MirrorSDK.getInstance().StartLogin(new MirrorListener.LoginListener() {
//                @Override
//                public void onLoginSuccess() {
//                    Log.i("MirrorDemo","Login success!");
//                }
//
//                @Override
//                public void onLoginFail() {
//                    Log.i("MirrorDemo","Login failed!");
//                }
//            });
//        }else if(apiId == MirrorConstant.GET_ACCESS_TOKEN){
//            MirrorSDK.getInstance().GetAccessToken(mContext, new MirrorCallback() {
//                @Override
//                public void callback(String result) {
//                    Toast.makeText(mContext,"Access TOken is:"+result,Toast.LENGTH_LONG);
//                }
//            });
//        }else if(apiId == MirrorConstant.SET_APP_ID){
//            String appId = String.valueOf(holder.mEditText.getText());
//            Log.i("mirror","input appId is "+appId);
//            MirrorSDK.getInstance().SetAppID("WsPRi3GQz0FGfoSklYUYzDesdKjKvxdrmtQ");
//        }else if(apiId == MirrorConstant.Query_USER){
//            String emailAddr = String.valueOf(holder.mEditText.getText());
//            MirrorSDK.getInstance().QueryUser(emailAddr, new MirrorListener.FetchUserListener() {
//                @Override
//                public void onUserFetched(UserResponse userResponse) {
//                    String result = MirrorGsonUtils.getInstance().toJson(userResponse);
//                    holder.mResultView.setText(result);
//                }
//
//                @Override
//                public void onFetchFailed(long code, String message) {
//                    holder.mResultView.setText("error code:"+code+" "+message);
//                }
//            });
//        }
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
