package com.example.outtakeapp.utils

import android.content.ContentValues

/**
 * 创建ContentValues对象，并添加键值对
 * @param pairs 键值对
 * @return ContentValues
 **/

//在 apply 代码块内可以直接调用 ContentValues 的方法（如 put、putNull）
//避免重复书写对象名
//最终返回 ContentValues 实例本身
fun cv0f(vararg pairs: Pair<String, Any?>) =  ContentValues().apply {
   for (pair in pairs) {
       val key = pair.first
       when (val value = pair.second) {
           is Int -> put(key, value)
           is Long -> put(key, value)
           is Float -> put(key, value)
           is Double -> put(key, value)
           is String -> put(key, value)
           is ByteArray -> put(key, value)
           is Boolean -> put(key, value)
           null -> putNull(key)
       }
   }
}