package com.mirror.mirrorworld_sdk_android;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mirror.mirrorworld_sdk_android.adapter.MultiParaItemRecyclerViewAdapter;
import com.mirror.mirrorworld_sdk_android.data.MultiItemData;

import java.util.ArrayList;
import java.util.List;


/*** @author Pu
 * @createTime 2022/8/17 17:07
 * @projectName mirrorworld-sdk-android
 * @className MultiParaItemFragment.java
 * @description TODO
 */
public class MultiParaItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;


    public List<MultiItemData.MultiItem> apiItems;


    public MultiParaItemFragment(List<MultiItemData.MultiItem> items) {

        if(null == this.apiItems){
            this.apiItems = new ArrayList<MultiItemData.MultiItem>();
        }

        this.apiItems.clear();
        this.apiItems.addAll(items);
       // this.mColumnCount =columnCount;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list2, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.api_list2);

        MultiParaItemRecyclerViewAdapter adapter = new MultiParaItemRecyclerViewAdapter(apiItems);
        adapter.SetContext(this.getActivity());

        recyclerView.setAdapter(adapter);

        return view;
    }






}
