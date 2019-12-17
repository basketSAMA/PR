package com.example.a123.myActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.a123.R;
import com.example.a123.myService.UpdateService;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineActivity extends BaseActivity {

    private MyHandler myHandler;

    private RecyclerView recyclerView;
    private PlantListAdapter plantListAdapter;

    private CircleImageView userImage;
    private TextView userName;
    private TextView userStar;
    private TextView userFan;
    private TextView userSpace;
    private ImageView setting;
    private ImageView background;
    private Button setSpace;

    private String inputString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        init();

        setSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View setView = getLayoutInflater().inflate(R.layout.input_space_mine, null);
                final EditText inputEdit = setView.findViewById(R.id.input_edit);
                new AlertDialog.Builder(MineActivity.this)
                        .setView(setView)
                        .setTitle("编辑签名")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                inputString = inputEdit.getText().toString();
                                myHandler.obtainMessage(1).sendToTarget();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        myHandler = new MyHandler();

        recyclerView = findViewById(R.id.rv);
        userImage = findViewById(R.id.mine_image_user);
        userName = findViewById(R.id.mine_name);
        userStar = findViewById(R.id.mine_star);
        userFan = findViewById(R.id.mine_fan);
        userSpace = findViewById(R.id.mine_space);
        setting = findViewById(R.id.mine_setting);
        background = findViewById(R.id.mine_image_background);
        setSpace = findViewById(R.id.mine_set_space);

        userImage.setImageBitmap(FamilyActivity.user.getImageBitmap());
        userName.setText(FamilyActivity.user.getName());
        userSpace.setText(FamilyActivity.user.getSpace());
        background.setImageBitmap(FamilyActivity.user.getBackgroundBitmap());

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        plantListAdapter = new PlantListAdapter(FamilyActivity.userLikeList);
        plantListAdapter.setOnItemClickListener(new PlantListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击事件
                Intent intent = new Intent(MineActivity.this, PlantActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(plantListAdapter);
    }

    protected void onStop(){
        super.onStop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateService.updateSpace(FamilyActivity.user.getEmail(), inputString, MineActivity.this);
            }
        });
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    FamilyActivity.user.setSpace(inputString);
                    userSpace.setText(inputString);
                    Toast.makeText(MineActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MineActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
