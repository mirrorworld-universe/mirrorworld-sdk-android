package yalantis.com.sidemenu.sample.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.fragment.placeholder.MultiItemData;

/**
 * A fragment representing a list of Items.
 */
public class MultiParaItemFragment extends Fragment implements ScreenShotable {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    protected int res;
    private Bitmap bitmap;
    public List<MultiItemData.MultiItem> apiItems;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MultiParaItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MultiParaItemFragment newInstance(int columnCount, List<MultiItemData.MultiItem> items) {
        MultiParaItemFragment fragment = new MultiParaItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        fragment.apiItems = items;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
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


    @Override
    public void takeScreenShot() {
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}