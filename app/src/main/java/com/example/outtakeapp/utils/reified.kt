package com.example.outtakeapp.utils

import android.content.Context
import android.content.Intent

/**
 * 创建一个扩展函数，用于启动Activity
 * reified关键字表示函数的参数类型是 reified 类型，即在编译时将参数类型信息保留在代码中。
 * T表示Activity的类型
 * */
inline fun <reified T> startActivity(content: Context,block: Intent.() -> Unit) {
    val intent = Intent(content, T::class.java)
    intent.block()
    content.startActivity(intent)
}