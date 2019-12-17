package com.example.a123.myActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Plant;

import java.util.ArrayList;
import java.util.List;

public class PlantListActivity extends BaseActivity {

    private List<Plant> plantList;
    private RecyclerView recyclerView;
    private PlantListAdapter plantListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        init();
    }

    private void init() {
        plantList = new ArrayList<>();
        plantList.add(new Plant("十万错", true, R.drawable.asystasia_chelonoides));
        plantList.add(new Plant("白鹤灵芝", false, R.drawable.bhlz));
        plantList.add(new Plant("高良姜", false, R.drawable.glj));
        plantList.add(new Plant("珊瑚花", true, R.drawable.shh));
        plantList.add(new Plant("鹿角藤", true, R.drawable.ljt));
        plantList.add(new Plant("鹭鸶草", false, R.drawable.lsc));
        plantList.add(new Plant("马利筋", false, R.drawable.mlj));
        plantList.add(new Plant("银带虾脊兰", true, R.drawable.ydxjl));
        plantList.add(new Plant("水衰衣", false, R.drawable.ssy));
        plantList.add(new Plant("长萼素馨", true, R.drawable.cesx));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        plantListAdapter = new PlantListAdapter(plantList);
        plantListAdapter.setOnItemClickListener(new PlantListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击事件
                Intent intent = new Intent(PlantListActivity.this, PlantActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(plantListAdapter);
    }
}
