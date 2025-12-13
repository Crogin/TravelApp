package com.example.outtakeapp.Activities.testActivities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.outtakeapp.R
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.contracts.Returns

class MyService : Service() {
    private val mBinder = MyBinder()
    //只有第一次启动时调用
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Log.d("MyService ", " onCreate服务已启动...")
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("my_service","前台通知",
            NotificationManager.IMPORTANCE_HIGH)
        // 启用声音和震动确保通知能弹出
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400)
        manager.createNotificationChannel(channel)
        val intent = Intent(this, ServiceActivity::class.java)
        val pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val notification = android.app.Notification.Builder(this,"my_service")
            .setContentTitle("前台服务")
            .setContentText("正在运行...")
            .setSmallIcon(android.R.drawable.ic_media_pause)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentIntent(pi)
            // 添加默认声音和震动
            .setDefaults(android.app.Notification.DEFAULT_ALL)
            .build()
        
        // Android 14+ 需要指定前台服务类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            startForeground(1, notification)
        }
    }

    //每次启动时调用
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService ", " onStartCommand 服务已启动...2")
        thread {
            sleep(5000)
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService ", " onDestroy服务已销毁...")
    }

    //绑定服务时调用
    override fun onBind(intent: Intent?): IBinder {
        Log.d("MyService ", " onBind服务已绑定...")
        return mBinder
    }

    class MyBinder : Binder() {
        fun startDownload() {
            Log.d("MyService ", " startDownload开始下载...")
        }
        fun getProgress() {
            Log.d("MyService ", "getProgress获取下载进度...")
        }
    }
}