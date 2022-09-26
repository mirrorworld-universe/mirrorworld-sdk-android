package com.mirror.mirrorworld_sdk_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mirror.sdk.ui.market.dialogs.MirrorMarketConfirmDialog;
import com.mirror.sdk.ui.market.dialogs.MirrorMarketDialog;
import com.mirror.sdk.ui.market.widgets.multiple.adapter.MainAdapter;
import com.mirror.sdk.ui.market.widgets.multiple.adapter.TabsAdapter;
import com.mirror.sdk.ui.market.widgets.multiple.type.ActivitiesType;
import com.mirror.sdk.ui.market.widgets.multiple.type.AttributeType;
import com.mirror.sdk.ui.market.widgets.multiple.type.BaseType;
import com.mirror.sdk.ui.market.widgets.multiple.type.Details;
import com.mirror.sdk.ui.market.widgets.multiple.type.GamePerformance;
import com.mirror.sdk.ui.market.widgets.multiple.type.HeaderType;
import java.util.ArrayList;
import java.util.List;

public class UITest extends AppCompatActivity {


    private RecyclerView mutiple;

    private int currentHeight = 60;

    private RecyclerView tabs;

    private LinearLayoutCompat customTabs;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mirror.sdk.R.layout.activity_uitest);
        activity = this;
        findViewById(com.mirror.sdk.R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MirrorMarketDialog mirrorMarketDialog = new MirrorMarketDialog();
                List<String> address = new ArrayList<>();
                address.add("qkuKJWMi14rdYLPBghfBRDpJASdbcLU6xZH3cpUZfng");
                mirrorMarketDialog.Init(activity,address);
                mirrorMarketDialog.show(getFragmentManager(),"uifdsa");

//                MirrorMarketConfirmDialog mirrorMarketConfirmDialog = new MirrorMarketConfirmDialog(activity);
//                mirrorMarketConfirmDialog.show();

            }
        });

       //InitView();
    }

    private void InitView()
    {
        mutiple = findViewById(com.mirror.sdk.R.id.mutiple_recyclerview);
        customTabs = findViewById(com.mirror.sdk.R.id.custom_tabs_root);
        tabs = findViewById(com.mirror.sdk.R.id.mutiple_position_tabs);

        mutiple.setLayoutManager(new LinearLayoutManager(this));
        mutiple.setAdapter(new MainAdapter(mockData()));
        tabs.setLayoutManager(new LinearLayoutManager(this));

        mutiple.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                currentHeight+=dy;
                AlphaMapping();
                super.onScrolled(recyclerView, dx, dy);
            }
        });




    }

    private List<BaseType> mockData(){

        List<BaseType> datas = new ArrayList<>();

        HeaderType headerType = new HeaderType();
        GamePerformance gamePerformance = new GamePerformance();
        AttributeType attributeType = new AttributeType();
        Details details = new Details();
        ActivitiesType activitiesType = new ActivitiesType();

        datas.add(headerType);
        datas.add(gamePerformance);
        datas.add(attributeType);
        datas.add(details);
        datas.add(activitiesType);
        return datas;
    }

    private List<String> getTabs(){
        List<String> tabs = new ArrayList<>();

        tabs.add("In-Game-Performance");
        tabs.add("On-Chain-Attribute");
        tabs.add("Details");
        tabs.add("Activities");
        return tabs;
    }

    private void AlphaMapping(){

        if(currentHeight>0 && currentHeight <15){
            customTabs.getBackground().setAlpha(0);

            return;
        }

        if(currentHeight <= 400){
            float rate = currentHeight*(1/400f);
            int alpha = Math.round((rate*255));
            customTabs.getBackground().setAlpha(alpha);
        }


        if(currentHeight> 400){
            customTabs.getBackground().setAlpha(255);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            tabs.setLayoutManager(linearLayoutManager);
            tabs.setAdapter(new TabsAdapter(getTabs(),mutiple));
        }else{
            List<String> empty = new ArrayList<>();
            tabs.setAdapter(new TabsAdapter(empty,mutiple));
        }




    }




}