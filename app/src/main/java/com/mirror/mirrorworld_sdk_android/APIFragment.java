//package com.mirror.mirrorworld_sdk_android;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mirror.mirrorworld_sdk_android.adapter.APIItemRecyclerViewAdapter;
//import com.mirror.mirrorworld_sdk_android.data.PlaceholderContent;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///*** @author Pu
// * @createTime 2022/8/17 15:44
// * @projectName mirrorworld-sdk-android
// * @className APIFragment.java
// * @description TODO
// */
//public class APIFragment extends Fragment {
//
//
//    private String newsType;
//
//    public List<PlaceholderContent.PlaceholderItem> DataSource;
//
//    public APIFragment(String newsType,List<PlaceholderContent.PlaceholderItem> DataSorce){
//        super();
//        this.newsType=newsType;
//        if(null == this.DataSource){
//            this.DataSource = new ArrayList<PlaceholderContent.PlaceholderItem>();
//        }
//        this.DataSource.clear();
//        this.DataSource.addAll(DataSorce);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);
//        RecyclerView recyclerView = rootView.findViewById(R.id.api_list);
//
//        APIItemRecyclerViewAdapter adapter = new APIItemRecyclerViewAdapter(DataSource);
//        adapter.SetContext(this.getActivity());
//        recyclerView.setAdapter(adapter);
//
//        return rootView;
//    }
//
//
//
//
//}
