package yalantis.com.sidemenu.sample.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.fragment.placeholder.PlaceholderContent;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class APIListFragment extends Fragment implements ScreenShotable {

    private View containerView;
    protected int res;
    private Bitmap bitmap;
    public List<PlaceholderContent.PlaceholderItem> apiItems;

    public static APIListFragment newInstance(int resId,List<PlaceholderContent.PlaceholderItem> items) {
        APIListFragment contentFragment = new APIListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        contentFragment.apiItems = items;
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.api_list);

        APIItemRecyclerViewAdapter adapter = new APIItemRecyclerViewAdapter(apiItems);
        adapter.SetContext(this.getActivity());

        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                APIListFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

