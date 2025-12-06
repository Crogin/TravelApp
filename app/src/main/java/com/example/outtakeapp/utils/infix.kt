package com.example.outtakeapp.utils

/**
 * infix函数是一种中缀函数，可以用在两个操作数之间，省略调用的点和括号
 * 只能定义为成员函数或扩展函数
 * 必须只有一个参数
 * */
infix fun String.beginWith(other: String) = startsWith(other)

infix fun <T> Collection<T>.has(other: T) = contains(other)

fun main() {
    val a = "hello"
    print(a beginWith "he")

    val list = listOf("apple", "banana", "orange","pear", "grape", "watermelon")
    if (list has "apple"){
        print("apple")
    }else if (list.contains("pear")){
        print("pear")
    }else {
        print("no")
    }
}