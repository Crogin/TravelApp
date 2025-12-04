package com.example.outtakeapp.utils

import kotlin.reflect.KProperty

class MyClass {
    fun  <T> method(t: T) {
        println(t)
    }

    fun <T : Number> method2(t: T) {
        println(t)
    }


}

fun <T>  T.build(block: T.() -> Unit) : T {
    block()
    return this
}

class Person {
    var name: String = ""
    var age: Int = 0
}

val person = Person().build {
    name = "Alice"
    age = 30
}

//class MySet{
//    fun hello () {
//        println("hello")
//    }
//    var p by DeleteBy()
////    override val size: Int
////        get() = list.size
////
////    override fun contains(element: T): Boolean {
////        return list.contains(element)
////    }
////
////    override fun containsAll(elements: Collection<T>): Boolean {
////        return list.containsAll(elements)
////    }
////
////    override fun isEmpty(): Boolean {
////        return list.isEmpty()
////    }
////
////    override fun iterator(): Iterator<T> {
////        return list.iterator()
////    }
//
//}

class MySet {
    fun hello () {
        println("hello")
    }
    var p: Any? by DeleteBy()
    fun setP() {
        p = "hello"
    }
}

class DeleteBy {
    private var value: Any? = null

    operator fun getValue(thisRef: MySet, property: KProperty<*>): Any? {
        return value
    }

    operator fun setValue(thisRef: MySet, property: KProperty<*>, value: Any?) {
        this.value = value
    }
}

fun <T> later(block: () -> T) = Later(block)

class Later<T>(val block: () -> T){
    var value : Any? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (value == null) {
            value = block()
        }
        return value as T
    }
}
