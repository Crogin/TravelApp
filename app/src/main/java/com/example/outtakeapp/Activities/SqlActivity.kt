package com.example.outtakeapp.Activities

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import androidx.core.content.edit
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.outtakeapp.databinding.ActivitySqlBinding
import com.example.outtakeapp.utils.MyDatabaseHelper
import com.example.outtakeapp.utils.cv0f
import com.example.outtakeapp.utils.open

class SqlActivity : BaseActivity() {
    private lateinit var binding: ActivitySqlBinding

    @SuppressLint("Range", "Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySqlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
        binding.click.setOnClickListener {
            dbHelper.writableDatabase
        }
        binding.button4.setOnClickListener {
            val data = dbHelper.writableDatabase
            //插入数据,通常用于Android开发中向数据库插入或更新数据时封装字段值
            val value = cv0f(
                "name" to "The Da Vinci Code",
                "author" to "Dan Brown",
                "pages" to 454,
                "price" to 12.99)
            data.insert("Book", null, value)

            val value2 = contentValuesOf("name" to "The Lost Symbol",
                "author" to "Dan Brown",
                "pages" to 500,
                "price" to 16.99
                )
            data.insert("Book", null, value2)

        }
        binding.button5.setOnClickListener {
            val data = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 10.99)
            //修改数据，需要参数，第一个参数是修改的字段，第二个参数是修改的值，第三个参数是查询条件，第四个参数是查询条件参数
            data.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
        }
        binding.button6.setOnClickListener {
            val data = dbHelper.writableDatabase
            //删除数据，需要参数，第一个参数是删除的字段，第二个参数是查询条件，第三个参数是查询条件参数
            data.delete("Book", "pages > ?", arrayOf("500"))
        }
        binding.button7.setOnClickListener {
            getSharedPreferences("login", MODE_PRIVATE).open {
                putString("username", "admin")
            }
            getSharedPreferences("login", MODE_PRIVATE).edit{
                putString("username", "admin")
            }
            val data = dbHelper.writableDatabase
            //查询数据，需要参数，第一个参数是查询的字段，第二个参数是查询条件，第三个参数是查询条件参数
            //第四个参数是排序字段，第五个参数是排序方式，第六个参数是分页起始位置，第七个参数是分页数量
            val cursor = data.query("Book", null, null, null, null, null, null)
            //将游标移动到结果集的第一行，如果结果集为空则返回false
            if (cursor.moveToFirst()) {
                //使用do-while循环确保至少执行一次循环体，然后继续处理剩余的行数据
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    Log.d(
                        "book",
                        "book name is $name, author is $author, pages is $pages, price is $price"
                    )
                } while (cursor.moveToNext())
            }
        }

        binding.replaceData.setOnClickListener { v ->
            val db = dbHelper.writableDatabase
            db.beginTransaction()
            try {
                db.delete("Book", null, null)
                if (true) {
                    throw NullPointerException()
                }
                val value = ContentValues().apply {
                    put("name", "Game of Thrones")
                    put("author", "George Martin")
                    put("pages", 720)
                    put("price", 20.99)
                }
                db.insert("Book", null, value)
                db.setTransactionSuccessful()
            } catch (e: Exception){
                e.printStackTrace()
            }finally {
                db.endTransaction()
            }
        }
        binding.button8.setOnClickListener {
            val localIntent = Intent("com.example.outtakeapp.FORCE_OFFLINE")
            sendBroadcast(localIntent)
        }
    }
}