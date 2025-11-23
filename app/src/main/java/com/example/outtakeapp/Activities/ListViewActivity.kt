package com.example.outtakeapp.Activities

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.outtakeapp.Adapter.ListViewAdapter
import com.example.outtakeapp.Model.Fruit
import com.example.outtakeapp.R

class ListViewActivity : BaseActivity() {
    lateinit var timeReceiver: TimeReceiver

    private val fruitList = ArrayList<Fruit>()
    @SuppressLint("MissingInflatedId", "UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new)
        val back = findViewById<Button>(R.id.back)
        val listView = findViewById<ListView>(R.id.ListView)
        initData()
        val adapter = ListViewAdapter(this,R.layout.item_new,fruitList)
        listView.adapter = adapter
        listView.setOnItemClickListener{_, _, position, _ ->
            val fruit = fruitList[position]
            Toast.makeText(this,"点击了${fruit.name}", Toast.LENGTH_SHORT).show()
            val intent = Intent("aaaaaaaaaaaa")
            //限定广播接收器只能接收本应用的广播
            intent.setPackage(packageName)
            //设置广播优先级
            sendBroadcast(intent,null)
        }
        back.setOnClickListener {

        }
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeReceiver = TimeReceiver()
        registerReceiver(timeReceiver,intentFilter)
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

    inner class TimeReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context,"时间到了",Toast.LENGTH_SHORT).show()
        }

    }
}