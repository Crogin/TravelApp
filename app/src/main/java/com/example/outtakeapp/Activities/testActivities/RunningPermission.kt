package com.example.outtakeapp.Activities.testActivities

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.outtakeapp.Activities.BaseActivity
import com.example.outtakeapp.R
import com.example.outtakeapp.databinding.ActivityRunningPermissionBinding
import com.example.outtakeapp.utils.MyClass

class RunningPermission : BaseActivity() {

    lateinit var binding: ActivityRunningPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRunningPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myClass = MyClass()
        val result  = myClass.method(123)

        binding.makeCall.setOnClickListener {
            //ContextCompat.checkSelfPermission() - 检查应用是否已获得CALL_PHONE权限
            //checkSelfPermission() 方法返回一个 int 类型的值，表示权限的授予状态：
            //PackageManager.PERMISSION_GRANTED (值为0)：表示应用已经获得了该权限
            //PackageManager.PERMISSION_DENIED (值为-1)：表示应用没有获得该权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            }else{
                call()
            }
        }

        binding.photo.setOnClickListener { v ->
            startActivity(Intent(this, PhoneActivity::class.java))
        }

        binding.button10.setOnClickListener {
            startActivity(Intent(this, PhotoActivity::class.java))
        }

        /**
         * 获取系统通知服务管理器
         * 判断Android版本是否为8.0(Oreo)及以上
         * 如果是8.0以上版本，则创建一个名为"important"的通知渠道，重要级别为高
         * 通过通知管理器注册创建的通知渠道
        */
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        binding.notice.setOnClickListener {
            //使用NotificationCompat.Builder构建通知对象
            //设置通知的标题、内容文本和小图标
            //manager.notify()方法显示通知，ID为1
            val intent = Intent(this, NotificationActivity::class.java)
            val p = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val notification = NotificationCompat.Builder(this, "important")
                .setContentTitle("标题")
                .setContentText("你好！")
                //设置通知的样式为BigTextStyle，并设置大文本内容
//                .setStyle(NotificationCompat.BigTextStyle().bigText("内容* 获取系统通知服务管理器\n" +
//                        "         * 判断Android版本是否为8.0(Oreo)及以上\n" +
//                        "         * 如果是8.0以上版本，则创建一个名为\"normal\"的通知渠道，重要级别为默认等级\n" +
//                        "         * 通过通知管理器注册创建的通知渠道"))
                //设置图片的
//                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources, R.drawable.beach1)))
                .setSmallIcon(R.drawable.ali)
                .setContentIntent(p)
                .setAutoCancel(true)
                .build()
            manager.notify(1, notification)
        }
    }


    /**
     * 权限申请学习板块
     * **/

    //权限申请结果处理，每次请求都会返回到函数
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                }else{
                    Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = "tel:10086".toUri()
            startActivity(intent)
        }catch (e: SecurityException){
            e.printStackTrace()
        }
    }
    
    private fun sendNotification() {

    }
}