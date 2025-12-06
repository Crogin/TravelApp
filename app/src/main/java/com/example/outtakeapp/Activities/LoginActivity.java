package com.example.outtakeapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.outtakeapp.Activities.testActivities.MediaActivity;
import com.example.outtakeapp.Activities.testActivities.RunningPermission;
import com.example.outtakeapp.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binging = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binging.getRoot());

        SharedPreferences preferencesa = getSharedPreferences("data",MODE_PRIVATE);
        boolean isLogin = preferencesa.getBoolean("isLogin",false);
        if (isLogin){
            binging.editText1.setText(preferencesa.getString("account",""));
            binging.editText2.setText(preferencesa.getString("password",""));
        }
        binging.btnPermissionPage.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RunningPermission.class)));

        binging.button11.setOnClickListener(
                v -> startActivity(new Intent(LoginActivity.this, MediaActivity.class))
        );

        binging.loginBtn.setOnClickListener(v -> {
            String admin = binging.editText1.getText().toString();
            String password = binging.editText2.getText().toString();
            SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("account",admin);
            editor.putString("password",password);
            editor.putBoolean("isLogin",binging.checkBox.isChecked());
            editor.apply();

            if (binging.editText1.getText().toString().equals("admin") && binging.editText2.getText().toString().equals("123456")){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        });

        binging.button3.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SqlActivity.class)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}