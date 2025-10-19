package com.example.outtakeapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.outtakeapp.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    /**
     * 欢迎页面
     **/
    ActivityIntroBinding binging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binging = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binging.getRoot());
        binging.introBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, MainActivity.class)));

        binging.loginBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, LoginActivity.class)));
    }
}