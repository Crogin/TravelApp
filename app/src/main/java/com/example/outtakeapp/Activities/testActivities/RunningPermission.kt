package com.example.outtakeapp.Activities.testActivities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.outtakeapp.Activities.BaseActivity
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
}