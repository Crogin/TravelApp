package com.example.outtakeapp.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.outtakeapp.Adapter.RecycleViewAdapter
import com.example.outtakeapp.Model.Fruit
import com.example.outtakeapp.R

class RecycleViewActivity : BaseActivity() {
    val fruitList = ArrayList<Fruit>()
    private lateinit var  adapter : RecycleViewAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycle_view)
        initData()
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL//设置布局垂直
        val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        if (!::adapter.isInitialized) adapter = RecycleViewAdapter(fruitList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun initData() {
        repeat(3){
            fruitList.add(Fruit(randomData("Apple"), R.drawable.ali))
            fruitList.add(Fruit(randomData("Banana"), R.drawable.ali))
            fruitList.add(Fruit(randomData("Orange"), R.drawable.ali))
            fruitList.add(Fruit(randomData("Watermelon"), R.drawable.ali))
            fruitList.add(Fruit(randomData("Pear"), R.drawable.ali))
            fruitList.add(Fruit(randomData("Grape"), R.drawable.ali))
        }

    }

    private fun randomData(str: String): String {
        val n = (0..20).random()
        val string = StringBuilder()
        for (i in 0 until n) {
            repeat(n){
                string.append(str)
            }
        }
        return string.toString()
    }
}