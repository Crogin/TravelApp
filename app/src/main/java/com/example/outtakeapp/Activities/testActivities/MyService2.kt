package com.example.outtakeapp.Activities.testActivities

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MyService2 : IntentService("MyService2") {
    val name = 1

    val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)
            if (msg.what == name) {
                Toast.makeText(this@MyService2, name.toString(), Toast.LENGTH_LONG).show()
                Log.d("MyService", "handleMessage")
            }
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("MyService", "onHandleIntent 的 id${Thread.currentThread().name}")
        sleep(2000)
        val message = android.os.Message()
        message.what = name
        handler.sendMessage(message)

        //切换到主线程显示Toast,子线程不能显示Toast，这个是简单写法
//        thread {
//            sleep(2000)
//            Handler(Looper.getMainLooper()).post {
//                Toast.makeText(this@MyService2, "子线程", Toast.LENGTH_LONG).show()
//            }
//            stopSelf()
//        }
    }

    override fun onDestroy() {
        Log.d("MyService", "onDestroy")
        super.onDestroy()
    }
}