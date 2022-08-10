package yalantis.com.sidemenu.sample.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mirror.sdk.MirrorSDKJava;

import org.w3c.dom.Text;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.fragment.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class APIItemRecyclerViewAdapter extends RecyclerView.Adapter<APIItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private Activity mContext;

    public APIItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
////        if (holder.itemView.getParent() != null) {
////            ((ViewGroup) holder.itemView.getParent()).removeView(holder.itemView);
////        }
//        return holder;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(mValues.get(position).id,holder);
            }
        });
    }

    private void handleClick(int apiId,ViewHolder holder){
        if(apiId == 1){
            MirrorSDKJava.getInstance().StartLogin();
        }else if(apiId == 2){
            MirrorSDKJava.getInstance().GetAccessToken(mContext, new MirrorSDKJava.ICallBack() {
                @Override
                public void callback(String result) {
                    Toast.makeText(mContext,"Access TOken is:"+result,Toast.LENGTH_LONG);
                }
            });
        }else if(apiId == 3){
            String appId = String.valueOf(holder.mEditText.getText());
            Log.i("mirror","input appId is "+appId);
            MirrorSDKJava.getInstance().SetAppID(mContext,"WsPRi3GQz0FGfoSklYUYzDesdKjKvxdrmtQ");
        }else if(apiId == 11){
            String emailAddr = String.valueOf(holder.mEditText.getText());
            MirrorSDKJava.getInstance().APIQueryUser(emailAddr, new MirrorSDKJava.ICallBack() {
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
        public PlaceholderItem mItem;

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