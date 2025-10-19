package com.example.outtakeapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.outtakeapp.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binging = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binging.getRoot());

        binging.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
    }
}