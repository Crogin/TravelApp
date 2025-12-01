package com.example.outtakeapp.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(private val context: Context, dbName: String, version: Int): SQLiteOpenHelper(context, dbName, null, version) {
    private val create_table = "create table Book (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "pages integer, " +
            "price real, " +
            "name text," +
            "category_id integer)"

    private val create_category = "create table Category (" +
            "id integer primary key autoincrement, " +
            "category_name text, " +
            "category_code integer)"

    //创建数据库
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(create_table)
        db?.execSQL(create_category)//创建分类表，如果之前table创建了，则不会创建这个，需要在下面添加
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    //更新数据库
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        /**
         * 当数据库版本升级时，执行相应的升级操作，覆盖旧数据
         * **/
        when (oldVersion) {
            1 -> {
                db?.execSQL(create_category)
            }
            2 -> {
                db?.execSQL("alter table Book add column category_id integer")
            }
            else -> {
                //如果版本过旧或无法处理，可以选择重建数据库
                db?.execSQL("drop table if exists Book")
                db?.execSQL("drop table if exists Category")
                onCreate(db)
            }
        }
    }
}