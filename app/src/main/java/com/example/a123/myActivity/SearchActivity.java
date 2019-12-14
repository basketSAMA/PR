package com.example.a123.myActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Genera;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private List<Genera> generaList;
    private RecyclerView recyclerView;
    private GeneraAdapter generaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        init();
    }

    private void init() {
        generaList = new ArrayList<>();
        generaList.add(new Genera("十万错", true, R.drawable.asystasia_chelonoides));
        generaList.add(new Genera("白鹤灵芝", false, R.drawable.bhlz));
        generaList.add(new Genera("高良姜", false, R.drawable.glj));

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
