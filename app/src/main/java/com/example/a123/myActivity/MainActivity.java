package com.example.a123.myActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a123.R;
import com.example.a123.myClass.Plant;
import com.example.a123.myClass.Uri2PathUtil;
import com.example.a123.myService.RecognizeService;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Uri imageUri;
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;

    private ImageView show;
    private ImageView take;
    private ImageView add;
    private Button start;

    private AVLoadingIndicatorView avi;

    private MyHandler myHandler;

    private String image_path = null;

    private Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = (ImageView) findViewById(R.id.show);

        take = (ImageView) findViewById(R.id.take);
        take.setOnClickListener(this);

        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(this);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        myHandler = new MyHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take:
                File outputImage = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                image_path = outputImage.getPath();
                Log.i("1", image_path);
                try {
                    outputImage.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.a123.fileProvider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.add:
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //检查权限，没有则动态获取权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.start:
                if(image_path == null)
                    Toast.makeText(MainActivity.this, "请上传图片！", Toast.LENGTH_SHORT).show();
                else{
                    avi.setVisibility(View.VISIBLE);
                    take.setVisibility(View.INVISIBLE);
                    add.setVisibility(View.INVISIBLE);
                    start.setClickable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            plant = RecognizeService.recognizeByImage(image_path, MainActivity.this);
                            if(plant.getName() != null)
                                myHandler.obtainMessage(1).sendToTarget();
                        }
                    }).start();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你拒绝了权限请求", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        show.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK) {
                    if(Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_dowmloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if(imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            show.setImageBitmap(bitmap);
            image_path = imagePath;
        } else {
            Toast.makeText(this, "获取图片失败！", Toast.LENGTH_SHORT).show();
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //Toast.makeText(MainActivity.this, plant.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, PlantActivity.class);
                    intent.putExtra("pid", plant.getPid());
                    startActivity(intent);
                    break;
            }
            avi.setVisibility(View.GONE);
            take.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            start.setClickable(true);
        }
    }
}
