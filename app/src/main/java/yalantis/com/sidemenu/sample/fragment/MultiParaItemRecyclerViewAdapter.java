package yalantis.com.sidemenu.sample.fragment;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import yalantis.com.sidemenu.sample.MirrorSDK;
import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.fragment.placeholder.MultiItemData;
import yalantis.com.sidemenu.sample.fragment.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MultiParaItemRecyclerViewAdapter extends RecyclerView.Adapter<MultiParaItemRecyclerViewAdapter.ViewHolder> {

    private final List<MultiItemData.MultiItem> mValues;
    private Activity mContext;

    public MultiParaItemRecyclerViewAdapter(List<MultiItemData.MultiItem> items) {
        mValues = items;
    }

    public void SetContext(Activity context){
        mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String hintPre = "Param:";
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("No."+mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).name);
        holder.mButton.setText(mValues.get(position).buttonText);
        holder.mResultView.setText(mValues.get(position).resultDefault);
        if(mValues.get(position).et1Hint == null){
            holder.mEt1.setVisibility(View.GONE);
        }else {
            holder.mEt1.setHint(hintPre+mValues.get(position).et1Hint);
        }
        if(mValues.get(position).et2Hint == null){
            holder.mEt2.setVisibility(View.GONE);
        }else {
            holder.mEt2.setHint(hintPre+mValues.get(position).et2Hint);
        }
        if(mValues.get(position).et3Hint == null){
            holder.mEt3.setVisibility(View.GONE);
        }else {
            holder.mEt3.setHint(hintPre+mValues.get(position).et3Hint);
        }
        if(mValues.get(position).et4Hint == null){
            holder.mEt4.setVisibility(View.GONE);
        }else {
            holder.mEt4.setHint(hintPre+mValues.get(position).et4Hint);
        }
        if(mValues.get(position).et5Hint == null){
            holder.mEt5.setVisibility(View.GONE);
        }else {
            holder.mEt5.setHint(hintPre+mValues.get(position).et5Hint);
        }
        if(mValues.get(position).et6Hint == null){
            holder.mEt6.setVisibility(View.GONE);
        }else {
            holder.mEt6.setHint(hintPre+mValues.get(position).et6Hint);
        }

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(mValues.get(position).id,holder);
            }
        });
    }

    private void handleClick(int apiId, MultiParaItemRecyclerViewAdapter.ViewHolder holder){
        if(apiId == 12){
            String mint_address = String.valueOf(holder.mEt1.getText());
            MirrorSDK.Companion.get().APIFetchSingleNFT(mint_address, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 32){
            String name = String.valueOf(holder.mEt1.getText());
            String symbol = String.valueOf(holder.mEt2.getText());
            String url = String.valueOf(holder.mEt3.getText());
            MirrorSDK.Companion.get().APIMintNewTopLevelCollection(name, symbol, url, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 31){
            String collection = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String url = String.valueOf(holder.mEt3.getText());
            MirrorSDK.Companion.get().APIMintNewNFTOnCollection(collection,name, symbol, url, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 33){
            String collection_mint = String.valueOf(holder.mEt1.getText());
            String name = String.valueOf(holder.mEt2.getText());
            String symbol = String.valueOf(holder.mEt3.getText());
            String url = String.valueOf(holder.mEt4.getText());
            MirrorSDK.Companion.get().APIMintNewLowerLevelCollection(collection_mint,name, symbol, url, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 34){
            String ad1 = String.valueOf(holder.mEt1.getText());
            String ad2 = String.valueOf(holder.mEt2.getText());
            List<String> list = new ArrayList<>();
            list.add(ad1);
            list.add(ad2);
            MirrorSDK.Companion.get().APIFetchNFTsByMintAddress(list, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 35){
            String creator1 = String.valueOf(holder.mEt1.getText());
            Double limit = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            Double offset = Double.valueOf(String.valueOf(holder.mEt3.getText()));
            List<String> list = new ArrayList<>();
            list.add(creator1);
            MirrorSDK.Companion.get().APIFetchNFTsByCreatorAddress(list,limit,offset, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 36){
            String update_authority1 = String.valueOf(holder.mEt1.getText());
            Double limit = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            Double offset = Double.valueOf(String.valueOf(holder.mEt3.getText()));
            List<String> list = new ArrayList<>();
            list.add(update_authority1);
            MirrorSDK.Companion.get().APIFetchNFTsByUpdateAuthorityAddress(list,limit,offset, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 37){
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            MirrorSDK.Companion.get().APIListNFTOnTheMarketplace(mint_address,price, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 38){
            String mint_address = String.valueOf(holder.mEt1.getText());
            String price = String.valueOf(holder.mEt2.getText());
            MirrorSDK.Companion.get().APIUpdateListingOfNFT(mint_address,price, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 39){
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            MirrorSDK.Companion.get().APIBuyNFT(mint_address,price, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 40){
            String mint_address = String.valueOf(holder.mEt1.getText());
            Double price = Double.valueOf(String.valueOf(holder.mEt2.getText()));
            MirrorSDK.Companion.get().APICancelListingOfNFT(mint_address,price, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }else if(apiId == 41){
            String mint_address = String.valueOf(holder.mEt1.getText());
            String to_wallet_address = String.valueOf(holder.mEt2.getText());
            MirrorSDK.Companion.get().APITransferNFTToAnotherSolanaWallet(mint_address,to_wallet_address, new Function1<String, Unit>() {
                @Override
                public Unit invoke(String s) {
                    holder.mResultView.setText(s);
                    return null;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button mButton;
        public final TextView mResultView;
        public final EditText mEt1;
        public final EditText mEt2;
        public final EditText mEt3;
        public final EditText mEt4;
        public final EditText mEt5;
        public final EditText mEt6;
        public MultiItemData.MultiItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.multiapi_item_number);
            mContentView = (TextView) view.findViewById(R.id.multiapi_item_name);
            mButton = (Button) view.findViewById(R.id.multiapi_item_button);
            mResultView = (TextView) view.findViewById(R.id.multiapi_item_result);
            mEt1 = (EditText) view.findViewById(R.id.multiapi_item_paramet1);
            mEt2 = (EditText) view.findViewById(R.id.multiapi_item_paramet2);
            mEt3 = (EditText) view.findViewById(R.id.multiapi_item_paramet3);
            mEt4 = (EditText) view.findViewById(R.id.multiapi_item_paramet4);
            mEt5 = (EditText) view.findViewById(R.id.multiapi_item_paramet5);
            mEt6 = (EditText) view.findViewById(R.id.multiapi_item_paramet6);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}