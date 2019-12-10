package com.example.a123.myActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d("BaseActivity: onCreate",getClass().getSimpleName());
        Log.d("BaseActivity: Address",this.toString());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("BaseActivity: onDestroy",getClass().getSimpleName());
        ActivityCollector.removeActivity(this);
    }

    protected void onStart(){
        super.onStart();
        Log.d("BaseActivity: onStart",getClass().getSimpleName());
    }

    protected void onResume(){
        super.onResume();
        Log.d("BaseActivity: onResume",getClass().getSimpleName());
    }

    protected void onPause(){
        super.onPause();
        Log.d("BaseActivity: onPause",getClass().getSimpleName());
    }

    protected void onStop(){
        super.onStop();
        Log.d("BaseActivity: onStop",getClass().getSimpleName());
    }

    protected void onRestart(){
        super.onRestart();
        Log.d("BaseActivity: onRestart",getClass().getSimpleName());
    }

}
