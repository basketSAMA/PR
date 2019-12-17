package com.example.a123.myActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Plant;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private List<Plant> plantList;
    private RecyclerView recyclerView;
    private PlantListAdapter plantListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        init();
    }

    private void init() {
        plantList = new ArrayList<>();
        plantList.add(new Plant("十万错", true, R.drawable.asystasia_chelonoides));
        plantList.add(new Plant("白鹤灵芝", false, R.drawable.bhlz));
        plantList.add(new Plant("高良姜", false, R.drawable.glj));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        plantListAdapter = new PlantListAdapter(plantList);
        plantListAdapter.setOnItemClickListener(new PlantListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击事件
            }
        });
        recyclerView.setAdapter(plantListAdapter);
    }
}
