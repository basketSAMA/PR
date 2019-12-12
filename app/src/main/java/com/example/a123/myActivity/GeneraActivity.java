package com.example.a123.myActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Genera;

import java.util.ArrayList;
import java.util.List;

public class GeneraActivity extends BaseActivity {

    private List<Genera> generaList;
    private RecyclerView recyclerView;
    private GeneraAdapter generaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        init();

        ImageView back = (ImageView)findViewById(R.id.title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneraActivity.this, FamilyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        generaList = new ArrayList<>();
        generaList.add(new Genera("十万错", true, R.drawable.asystasia_chelonoides));
        generaList.add(new Genera("白鹤灵芝", false, R.drawable.bhlz));
        generaList.add(new Genera("高良姜", false, R.drawable.glj));
        generaList.add(new Genera("珊瑚花", true, R.drawable.shh));
        generaList.add(new Genera("鹿角藤", true, R.drawable.ljt));
        generaList.add(new Genera("鹭鸶草", false, R.drawable.lsc));
        generaList.add(new Genera("马利筋", false, R.drawable.mlj));
        generaList.add(new Genera("银带虾脊兰", true, R.drawable.ydxjl));
        generaList.add(new Genera("水衰衣", false, R.drawable.ssy));
        generaList.add(new Genera("长萼素馨", true, R.drawable.cesx));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        generaAdapter = new GeneraAdapter(generaList);
        generaAdapter.setOnItemClickListener(new GeneraAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击事件
            }
        });
        recyclerView.setAdapter(generaAdapter);
    }
}
