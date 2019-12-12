package com.example.a123.myActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Family;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FamilyActivity extends BaseActivity {

    private List<Family> familyList;
    private RecyclerView recyclerView;
    private FamilyAdapter familyAdapter;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();//初始化状态
        drawer.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_recognize:
                        Intent intent = new Intent(FamilyActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_search:
                        break;
                    case R.id.nav_like:
                        break;
                    case R.id.nav_tool:
                        break;
                    case R.id.nav_contact:
                        break;
                    case R.id.nav_share:
                        break;
                    case R.id.nav_back:
                        break;
                }
                menuItem.setCheckable(false);
                drawer.closeDrawers();
                return true;
            }
        });

        init();
    }

    private void init() {
        familyList = new ArrayList<>();
        familyList.add(new Family(getString(R.string.Acanthaceae), "Acanthaceae", R.drawable.acanthaceae));
        familyList.add(new Family(getString(R.string.Compositae), "Compositae", R.drawable.compositae));
        familyList.add(new Family(getString(R.string.Gramineae), "Gramineae", R.drawable.gramineae));
        familyList.add(new Family(getString(R.string.Liliaceae), "Liliaceae", R.drawable.liliaceae));
        familyList.add(new Family(getString(R.string.Malvaceae), "Malvaceae", R.drawable.malvaceae));

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        familyAdapter = new FamilyAdapter(familyList);
        familyAdapter.setOnItemClickListener(new FamilyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击事件
                Intent intent = new Intent(FamilyActivity.this, GeneraActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(familyAdapter);
    }

    public void drawerBack(View view) {
        drawer.closeDrawers();
    }
}
