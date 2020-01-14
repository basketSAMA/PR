package com.example.a123.myActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Plant;
import com.example.a123.myService.PlantService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class PlantListActivity extends BaseActivity {

    private List<Plant> plantList;
    private RecyclerView recyclerView;
    private PlantListAdapter plantListAdapter;

    private MyHandler myHandler;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        final String nameC = getIntent().getStringExtra("nameC");
        myHandler = new MyHandler();
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                plantList = PlantService.getPlantByFamily(FamilyActivity.user.getEmail(), nameC, 10, PlantListActivity.this);
                myHandler.obtainMessage(1).sendToTarget();
            }
        }).start();

        //init();
    }

    private void init() {
//        plantList = new ArrayList<>();
//        plantList.add(new Plant("十万错", true, R.drawable.asystasia_chelonoides));
//        plantList.add(new Plant("白鹤灵芝", false, R.drawable.bhlz));
//        plantList.add(new Plant("高良姜", false, R.drawable.glj));
//        plantList.add(new Plant("珊瑚花", true, R.drawable.shh));
//        plantList.add(new Plant("鹿角藤", true, R.drawable.ljt));
//        plantList.add(new Plant("鹭鸶草", false, R.drawable.lsc));
//        plantList.add(new Plant("马利筋", false, R.drawable.mlj));
//        plantList.add(new Plant("银带虾脊兰", true, R.drawable.ydxjl));
//        plantList.add(new Plant("水衰衣", false, R.drawable.ssy));
//        plantList.add(new Plant("长萼素馨", true, R.drawable.cesx));
//
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        plantListAdapter = new PlantListAdapter(plantList);
//        plantListAdapter.setOnItemClickListener(new PlantListAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                //点击事件
//                Intent intent = new Intent(PlantListActivity.this, PlantActivity.class);
//                startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(plantListAdapter);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    plantListAdapter = new PlantListAdapter(plantList);
                    plantListAdapter.setOnItemClickListener(new PlantListAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            //点击事件
                            String pid = plantList.get(position).getPid();
                            Intent intent = new Intent(PlantListActivity.this, PlantActivity.class);
                            intent.putExtra("pid", pid);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(plantListAdapter);
                    avi.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }
}
