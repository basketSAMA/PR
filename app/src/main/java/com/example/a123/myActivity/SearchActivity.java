package com.example.a123.myActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Plant;
import com.example.a123.myService.PlantService;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private List<Plant> plantsList;
    private List<Plant> plantList;
    private RecyclerView recyclerView;
    private PlantListAdapter plantListAdapter;
    private Button button;
    private MyHandler myHandler;
    private EditText searchNameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        button = findViewById(R.id.search_button);
        searchNameContainer = findViewById(R.id.search_name);
        button.setOnClickListener(this);

        myHandler = new MyHandler();
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        init();
    }


    private void init() {
        plantList = new ArrayList<>();
//        plantList.add(new Plant("十万错", true, R.drawable.asystasia_chelonoides));
//        plantList.add(new Plant("白鹤灵芝", false, R.drawable.bhlz));
//        plantList.add(new Plant("高良姜", false, R.drawable.glj));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        plantListAdapter = new PlantListAdapter(plantList);
        plantListAdapter.setOnItemClickListener(new PlantListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击事件
                String pid = plantList.get(position).getPid();
                Intent intent = new Intent(SearchActivity.this, PlantActivity.class);
                intent.putExtra("pid", pid);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(plantListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        plantsList = PlantService.getPlantByName(searchNameContainer.getText().toString().trim(), SearchActivity.this);
                        myHandler.obtainMessage(1).sendToTarget();
                    }
                }).start();
                break;
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    plantListAdapter = new PlantListAdapter(plantList);
                    recyclerView.setAdapter(plantListAdapter);
                    break;
            }
        }
    }


}
