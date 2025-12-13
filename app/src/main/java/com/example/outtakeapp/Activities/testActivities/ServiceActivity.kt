package com.example.outtakeapp.Activities.testActivities

import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.outtakeapp.Activities.BaseActivity
import com.example.outtakeapp.databinding.ActivityServiceBinding
import com.example.outtakeapp.utils.DownloadTask
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import kotlin.concurrent.thread

class ServiceActivity : BaseActivity() {
    lateinit var binding: ActivityServiceBinding
    lateinit var downloadBinder: MyService.MyBinder
    private var progressDialog: ProgressDialog? = null
    private var downloadTask: DownloadTask? = null

    val undateText = 1

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            Log.d("MyService", "onServiceConnected")
            downloadBinder = service as MyService.MyBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        //服务断开，这个只有服务被终止时才会调用
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("MyService", "onServiceDisconnected")
        }
    }

    val handle = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                undateText -> {
                    binding.text.text = "服务已启动..."
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化进度对话框
        progressDialog = ProgressDialog(this).apply {
            setTitle("下载中...")
            setMessage("正在准备下载...")
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            max = 100
            setCancelable(false)
        }

        binding.button12.setOnClickListener {
            postApiRequest()
            //isDaemon:是否是守护线程,start:是否启动线程,priority:线程优先级
            //守护线程（Daemon Thread）：在程序退出时会自动结束的线程
            //非守护线程（User Thread）：程序会等待其执行完毕才退出的线程
            thread(isDaemon = false, start = true, priority = 10) {
                //创建消息
                val msg = Message()
                //设置消息
                Log.d("aaa", "thread:" + msg.what)
                msg.what = undateText
                //发送消息
                handle.sendMessage(msg)
            }
        }

        binding.button13.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
            //服务会一直运行直到调用 stopService() 或 stopSelf()
        }

        binding.button14.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
            //注意：只能停止通过 startService() 启动的服务
        }

        binding.button15.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, BIND_AUTO_CREATE)//绑定服务
        }

        binding.button16.setOnClickListener {
            unbindService(connection)//解绑服务
            //        触发生命周期：onCreate() → onBind()
//        特点：客户端可以通过 IBinder 与服务通信，服务随最后一个客户端解绑而销毁
        }


        binding.button17.setOnClickListener {
            Log.d("MyService", "startJobIntentService 的 id${Thread.currentThread().name}")
            val intent = Intent(this, MyService2::class.java)
            startService(intent)
        }
    }

    private fun postApiRequest() {
        val client = OkHttpClient()
        val jsonObject = JSONObject()
        jsonObject.put("authCheck", "1")
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonObject.toString().toRequestBody(jsonMediaType)
        val request = Request.Builder()
            .url("https://newpay.welikedian.com:4433/an_login_update")
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API_ERROR", "网络请求失败", e)
                runOnUiThread {
                    Toast.makeText(this@ServiceActivity, "网络请求失败", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        Log.e("API_ERROR", "请求失败: ${response.code}")
                        runOnUiThread {
                            Toast.makeText(this@ServiceActivity, "请求失败: ${response.code}", Toast.LENGTH_SHORT).show()
                            return@runOnUiThread
                        }
                    }

                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        try {
                            val jsonResponse = JSONObject(responseBody)
                            val status = jsonResponse.getInt("status")

                            if (status == 0) {
                                val messageObj = jsonResponse.getJSONObject("message")
                                val url = messageObj.getString("url")

                                Log.d("API_SUCCESS", "获取到的URL: $url")
                                runOnUiThread {
                                    // 启动下载任务
                                    startDownload(url)
                                }
                            } else {
                                runOnUiThread {
                                    Toast.makeText(this@ServiceActivity, "服务器返回错误状态", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("API_ERROR", "解析JSON失败", e)
                            runOnUiThread {
                                Toast.makeText(this@ServiceActivity, "数据解析失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun startDownload(url: String) {
        // 显示进度对话框
        progressDialog?.show()

        // 创建并启动下载任务
        downloadTask = DownloadTask(this, progressDialog)
        downloadTask?.execute(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.dismiss()
        downloadTask?.cancel(true)
    }
}