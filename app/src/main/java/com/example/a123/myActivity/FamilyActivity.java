package com.example.a123.myActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myClass.Family;
import com.example.a123.myClass.IPUtil;
import com.example.a123.myClass.Plant;
import com.example.a123.myClass.User;
import com.example.a123.myService.FamilyService;
import com.example.a123.myService.LoginService;
import com.google.android.material.navigation.NavigationView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FamilyActivity extends BaseActivity {

    public static List<Plant> userLikeList = new ArrayList<>();
    public static User user;

    private List<Family> familyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FamilyAdapter familyAdapter;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View familyHeader;

    private CircleImageView userImage;
    private TextView userName;
    private TextView userEmail;

    private MyHandler myHandler;

    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        final String email = getIntent().getStringExtra("email");

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        familyHeader = navigationView.inflateHeaderView(R.layout.nav_header_family);
        userImage = familyHeader.findViewById(R.id.nav_user_image);
        userName = familyHeader.findViewById(R.id.nav_name);
        userEmail = familyHeader.findViewById(R.id.nav_email);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        myHandler = new MyHandler();

        recyclerView = findViewById(R.id.rv);

        avi.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                familyList = FamilyService.getFamily(email, 10, FamilyActivity.this);
                user = LoginService.getUserInfo(email, FamilyActivity.this);
                myHandler.obtainMessage(1).sendToTarget();
            }
        }).start();

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
                        Intent intent1 = new Intent(FamilyActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_search:
                        Intent intent2 = new Intent(FamilyActivity.this, SearchActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_like:
                        Intent intent3 = new Intent(FamilyActivity.this, LikeActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_guess:
                        Intent intent4 = new Intent(FamilyActivity.this, GuessActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_tool:
                        break;
                    case R.id.nav_share:
                        break;
                    case R.id.nav_exit:
                        finish();
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
//        familyList = new ArrayList<>();
//        familyList.add(new Family(getString(R.string.Acanthaceae), "Acanthaceae", R.drawable.acanthaceae));
//        familyList.add(new Family(getString(R.string.Compositae), "Compositae", R.drawable.compositae));
//        familyList.add(new Family(getString(R.string.Gramineae), "Gramineae", R.drawable.gramineae));
//        familyList.add(new Family(getString(R.string.Liliaceae), "Liliaceae", R.drawable.liliaceae));
//        familyList.add(new Family(getString(R.string.Malvaceae), "Malvaceae", R.drawable.malvaceae));

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        familyAdapter = new FamilyAdapter(familyList);
//        familyAdapter.setOnItemClickListener(new FamilyAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                //点击事件
//                Intent intent = new Intent(FamilyActivity.this, PlantListActivity.class);
//                startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(familyAdapter);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    userImage.setImageBitmap(user.getImageBitmap());
                    userName.setText(user.getName());
                    userEmail.setText(user.getEmail());

                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    familyAdapter = new FamilyAdapter(familyList);
                    familyAdapter.setOnItemClickListener(new FamilyAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            //点击事件
                            String nameC = familyList.get(position).getNameC();
                            Intent intent = new Intent(FamilyActivity.this, PlantListActivity.class);
                            intent.putExtra("nameC", nameC);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(familyAdapter);
                    avi.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }

    public void drawerBack(View view) {
        drawer.closeDrawers();
    }

    public void toMine(View view) {
        drawerBack(view);
        Intent intent = new Intent(FamilyActivity.this, MineActivity.class);
        startActivity(intent);
    }
}
