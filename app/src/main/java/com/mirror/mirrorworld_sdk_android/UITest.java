package com.mirror.mirrorworld_sdk_android;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mirror.sdk.ui.market.widgets.multiple.adapter.MainAdapter;
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

    private RelativeLayout customTabs;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mirror.sdk.R.layout.mutiple_recyclerview);
        InitView();
    }


    private void InitView()
    {
        mutiple = findViewById(com.mirror.sdk.R.id.mutiple_recyclerview);
        customTabs = findViewById(com.mirror.sdk.R.id.custom_tabs_root);
        mutiple.setLayoutManager(new LinearLayoutManager(this));
        mutiple.setAdapter(new MainAdapter(mockData()));

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


    private void AlphaMapping(){

        if(currentHeight>0 && currentHeight <15){
            customTabs.getBackground().setAlpha(0);
            return;
        }

        if(currentHeight <= 400){
            float rate = currentHeight*(1/400f);


            Log.e("TT-","rate"+rate);

            int alpha = Math.round((rate*255));

            Log.e("TT-","alpha"+alpha);

            customTabs.getBackground().setAlpha(alpha);

        }




    }




}