package com.example.a123.myActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Plant;
import com.example.a123.myService.GuessService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class GuessActivity extends BaseActivity {

    private List<Plant> plantList;
    private RecyclerView recyclerView;
    private PlantListAdapter plantListAdapter;

    private MyHandler myHandler;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        myHandler = new MyHandler();

        TextView titleName = findViewById(R.id.title_name);
        titleName.setText(getString(R.string.menu_guess));

        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                plantList = GuessService.getGuessLike(FamilyActivity.user.getEmail(), 10, GuessActivity.this);
                myHandler.obtainMessage(1).sendToTarget();
            }
        }).start();
    }

    @Override
    protected void onResume(){
        super.onResume();
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
                            Intent intent = new Intent(GuessActivity.this, PlantActivity.class);
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
