package com.example.outtakeapp.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.outtakeapp.utils.ActivityCollector;

public class BaseActivity extends AppCompatActivity {
    ForceOfflineReceiver receiver;//强制下线

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加Activity到容器中
        ActivityCollector.INSTANCE.addActivity(this);
    }

    @SuppressLint({"UnspecifiedRegisterReceiverFlag", "WrongConstant", "InlinedApi"})
    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.outtakeapp.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();

        // 确保在所有版本中都指定 RECEIVER_EXPORTED 或 RECEIVER_NOT_EXPORTED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 14+ 需要明确指定
            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            // Android 13 及以下版本
            registerReceiver(receiver, filter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.INSTANCE.removeActivity(this);
    }

    public static class ForceOfflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "强制下线", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示")
                        .setMessage("您已被强制下线")
                        .setCancelable(false)
                        .setPositiveButton("确定", (dialog, which) -> {
                            ActivityCollector.INSTANCE.finishAll();
                            Intent i = new Intent(context, LoginActivity.class);
                            context.startActivity(i);
                        });
                builder.show();
        }
    }
}