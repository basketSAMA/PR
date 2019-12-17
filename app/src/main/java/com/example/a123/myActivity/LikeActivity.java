package com.example.a123.myActivity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a123.R;
import com.wang.avi.AVLoadingIndicatorView;

public class LikeActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private PlantListAdapter plantListAdapter;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        TextView titleName = findViewById(R.id.title_name);
        titleName.setText(getString(R.string.menu_like));

        init();
    }

    private void init() {
        if(FamilyActivity.userLikeList.size() != 0) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            plantListAdapter = new PlantListAdapter(FamilyActivity.userLikeList);
            plantListAdapter.setOnItemClickListener(new PlantListAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    //点击事件
                    Intent intent = new Intent(LikeActivity.this, PlantActivity.class);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(plantListAdapter);
        } else {
            recyclerView.setBackgroundResource(R.drawable.empty);
        }
        avi.setVisibility(View.INVISIBLE);
    }
}
