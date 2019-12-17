package com.example.a123.myActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a123.R;
import com.example.a123.myClass.Plant;
import com.example.a123.myService.LikeService;
import com.example.a123.myService.PlantService;
import com.wang.avi.AVLoadingIndicatorView;

public class PlantActivity extends BaseActivity {

    private Plant plant;
    private boolean start;
    private MyHandler myHandler;

    private View plantAll;
    private ImageView plantImage;
    private TextView plantFamily;
    private ImageView plantShare;
    private ImageView plantLike;
    private TextView plantName;
    private TextView plantDescribe;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        init();

        avi.setVisibility(View.VISIBLE);

        final String pid = getIntent().getStringExtra("pid");
        new Thread(new Runnable() {
            @Override
            public void run() {
                plant = PlantService.getPlantById(FamilyActivity.user.getEmail(), pid, PlantActivity.this);
                myHandler.obtainMessage(1).sendToTarget();
            }
        }).start();

        plantLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(plant.isLike()) {
                    plant.setLike(false);
                }
                else {
                    plant.setLike(true);
                }
                plantLike.setImageResource(plant.isLike()?R.mipmap.like:R.mipmap.dislike);
            }
        });
    }

    private void init() {
        myHandler = new MyHandler();

        plantAll = findViewById(R.id.plant_all);
        plantImage = findViewById(R.id.plant_image);
        plantFamily = findViewById(R.id.plant_family);
        plantShare = findViewById(R.id.plant_share);
        plantLike = findViewById(R.id.plant_like);
        plantName = findViewById(R.id.plant_name);
        plantDescribe = findViewById(R.id.plant_describe);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    start = plant.isLike();
                    plantImage.setImageBitmap(plant.getImageBitmap());
                    plantFamily.setText(plant.getNameC());
                    plantLike.setImageResource(plant.isLike()?R.mipmap.like:R.mipmap.dislike);
                    plantName.setText(plant.getName());
                    plantDescribe.setText(plant.getDetail());
                    break;
            }
            avi.setVisibility(View.INVISIBLE);
            plantAll.setVisibility(View.VISIBLE);
        }
    }

    protected void onStop(){
        super.onStop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(plant.isLike() && !start) {
                    LikeService.insertLike(FamilyActivity.user.getEmail(), plant.getPid());
                    FamilyActivity.userLikeList.add(plant);
                }
                if(!plant.isLike() && start) {
                    LikeService.deleteLike(FamilyActivity.user.getEmail(), plant.getPid());
                    for(Plant p: FamilyActivity.userLikeList) {
                        if(p.getPid().equals(plant.getPid())) {
                            FamilyActivity.userLikeList.remove(p);
                            break;
                        }
                    }
                }
            }
        });
    }
}
