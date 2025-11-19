package com.example.outtakeapp.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.outtakeapp.Adapter.ListViewAdapter
import com.example.outtakeapp.Model.Fruit
import com.example.outtakeapp.R

class ListViewActivity : BaseActivity() {

    private val fruitList = ArrayList<Fruit>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new)
        val listView = findViewById<ListView>(R.id.ListView)
        initData()
        val adapter = ListViewAdapter(this,R.layout.item_new,fruitList)
        listView.adapter = adapter
        listView.setOnItemClickListener{_, _, position, _ ->
            val fruit = fruitList[position]
            Toast.makeText(this,"点击了${fruit.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initData() {
        repeat(2){
            fruitList.add(Fruit("Applt", R.drawable.ali))
            fruitList.add(Fruit("李思思", R.drawable.ali))
            fruitList.add(Fruit("啊哈哈", R.drawable.ali))
            fruitList.add(Fruit("顶顶顶顶", R.drawable.ali))
            fruitList.add(Fruit("小小", R.drawable.ali))
            fruitList.add(Fruit("小小是事实", R.drawable.ali))
        }
    }
}