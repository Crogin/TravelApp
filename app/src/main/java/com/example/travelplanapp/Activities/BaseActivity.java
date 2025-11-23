package com.example.travelplanapp.Activities;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelplanapp.R;

public class BaseActivity extends AppCompatActivity {

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建时，打印当前Activity名称
        Log.d("BaseActivity", "onCreate: " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁时，打印当前Activity名称
        Log.d("BaseActivity", "onDestroy: " + getClass().getSimpleName());
    }
}