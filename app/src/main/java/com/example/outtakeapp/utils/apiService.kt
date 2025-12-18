package com.example.outtakeapp.utils

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface apiService {
    @POST("/an_login_update")
    fun getUpdata(@Body body: RequestBody): Call<Response<Message>>
}

object ServiceCreator {
    private const val BASE_URL = "https://newpay.welikedian.com:4433/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    //使用<reified T>声明一个可重的泛型参数T
    //通过T::class.java获取泛型T的Class对象
    //调用另一个create(Class<T>)方法来创建并返回T类型的实例
    inline fun <reified T> create(): T = create(T ::class.java)
}

/**
 * 挂起函数，用于在协程中等待Retrofit2的Call对象完成
 * @param T 泛型参数，表示返回的数据类型
 * @return 返回的响应数据
 * @throws IOException 如果响应数据为空，则抛出IOException异常
 * @throws Throwable 如果响应数据为空，则抛出Throwable异常
 * */
suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T?>, response: retrofit2.Response<T?>) {
                val body = response.body()
                if (body != null){
                    continuation.resume(body)
                }else{
                    continuation.resumeWithException(IOException("response body is null"))
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}