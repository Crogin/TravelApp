package com.example.outtakeapp.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.outtakeapp.Adapter.MessageAdapter
import com.example.outtakeapp.Model.Message
import com.example.outtakeapp.R

class ChatActivity : AppCompatActivity() {
    val messageList = ArrayList<Message>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        initData()
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycleView)
        val layoutManager = LinearLayoutManager(this)
        val send = findViewById<android.widget.Button>(R.id.send)
        recyclerView.layoutManager = layoutManager
        val adapter = MessageAdapter(messageList)
        recyclerView.adapter = adapter
        send.setOnClickListener(){
            send->
            val send = findViewById<android.widget.EditText>(R.id.input_text)
            if (send.text.isNotEmpty()){
                val msg = Message(send.text.toString(), Message.TYPE_SENT)
                messageList.add(msg)
                adapter.notifyItemInserted(messageList.size-1)// 刷新数据
                recyclerView.scrollToPosition(messageList.size-1) // 滚动到底部
                send.setText("")
            }
        }
    }

    private fun initData() {
        val msg1 = Message("小王", Message.TYPE_RECEIVED)
        messageList.add(msg1)
        val msg2 = Message("我爱你", Message.TYPE_SENT)
        messageList.add(msg2)
        val msg3 = Message("骗你的", Message.TYPE_RECEIVED)
        messageList.add(msg3)
    }

}