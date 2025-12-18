package com.example.outtakeapp.Activities.testActivities

import android.annotation.SuppressLint
import android.media.MediaParser
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.outtakeapp.R
import com.example.outtakeapp.databinding.ActivityWebViewBinding
import com.example.outtakeapp.utils.Message
import com.example.outtakeapp.utils.Response
import com.example.outtakeapp.utils.ServiceCreator
import com.example.outtakeapp.utils.apiService
import com.example.outtakeapp.utils.await
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding

    val apiService = ServiceCreator.create<apiService>()
    val jsonObject = JSONObject()
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 允许JavaScript
        binding.webview.settings.javaScriptEnabled = true
        // 允许使用缓存
        binding.webview.webViewClient = WebViewClient()
        // 加载URL
        binding.webview.loadUrl("https://www.baidu.com")
        jsonObject.put("authCheck", "1")


        binding.button18.setOnClickListener {
//            sendResquest()//这个使用OKHttpClient
//            sendResquestOkHttp()//这个使用OKHttp
//            sendRequestRetrofit()//这个使用Retrofit
            lifecycleScope.launch {
                getData()
            }
        }
    }

    suspend fun getData() {
        val requestBody = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val response =ServiceCreator.create<apiService>().getUpdata(requestBody).await()
        Log.d("TAG", "getData: $response")
    }


    private fun sendRequestRetrofit() {
        // 将JSONObject转换为RequestBody
        val requestBody = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        apiService.getUpdata(requestBody).enqueue(object : retrofit2.Callback<Response<Message>>{
            override fun onResponse(
                call: Call<Response<Message>?>,
                response: retrofit2.Response<Response<Message>?>
            ) {
                val status = response.body()?.status?.toInt() ?: -1
                if (status == 0){
                    val message = response.body()?.message
                    binding.textView8.text = message.toString()
                }
            }

            override fun onFailure(
                call: Call<Response<Message>?>,
                t: Throwable
            ) {
                //输出错误信息
                t.printStackTrace()
                Toast.makeText(this@WebViewActivity, "请求失败", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun sendResquestOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://www.baidu.com")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null){
                    showResponse(responseData)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun sendResquest() {
        thread {
            var connection: HttpURLConnection ?= null
            try {
                val url = URL("https://www.baidu.com")
                val response = StringBuilder()
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                val input  = connection.inputStream
                // 处理输入流
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine{
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun showResponse(toString: String) {
        runOnUiThread {
            binding.textView8.text = toString
        }
    }
}
