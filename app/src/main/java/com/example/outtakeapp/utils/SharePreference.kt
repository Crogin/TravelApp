package com.example.outtakeapp.utils

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * 创建SharedPreferences的扩展函数，高阶函数使用
 * **/
//添加一个名为 open 的扩展函数
//haredPreferences.Editor.() 表示这是一个带有接收者的函数字面量（function literal with receiver）
//Unit 表示该函数不返回任何值
fun SharedPreferences.open(a: SharedPreferences.Editor.() -> Unit) {
    //edit { ... }：调用 SharedPreferences 的 edit 扩展函数
    edit {
        a()
        apply()
    }
}