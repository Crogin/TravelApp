package com.example.outtakeapp.utils

import kotlin.random.Random

/**
 * 拓展函数和重载运算符
 * **/
fun String.lettersCount(): Int {
    var count = 0
    for (char in this) {
        if (char.isLetter()) count++
    }
    return count
}


class Money(val amount: Int){
    operator fun plus(other: Money): Money {
        return Money(amount + other.amount)
    }

    operator fun plus(other: Int): Money {
        return Money(amount + other)
    }
}

fun main() {
    val money1 = Money(10)
    val money2 = Money(20)
    val money3 = money1 + money2
    val money4 = money1 + 5
    println(money3.amount)
    println(money4.amount)
    println("你好".times(3))

    runAble {
        val other = Random(1000).nextInt()
        println("这是runAble")
        if (other > 0){
            println("other > 0")
            return@runAble
        }
    }
}

fun String.times(other: Int): String {
    return  repeat(other)
}

inline fun runAble(crossinline block:() -> Unit){
    val runAble = Runnable {
        block()
    }
}
