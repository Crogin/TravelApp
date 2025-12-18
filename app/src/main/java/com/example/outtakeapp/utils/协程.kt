package com.example.outtakeapp.utils

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//sleep和delay区别， delay是挂起函数，sleep是阻塞函数，阻塞函数会阻塞线程，挂起函数不会
//runBlocking 会阻塞线程，但是不会挂起线程
@OptIn(DelicateCoroutinesApi::class)
fun main(){
//    GlobalScope.launch {
//        print("hello world")
//        delay(1500)
//        print("hello world2")
//    }
//    Thread.sleep(1000)
    //改用runBlocking
    runBlocking {
        launch {
            print("hello world")
            delay(1500)
            print("hello world2")
        }
        launch {
            print("hello world3")
            delay(1000)
            print("hello world4")
        }
    }
}

