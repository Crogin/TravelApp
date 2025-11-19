package com.example.outtakeapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.outtakeapp.Model.Message
import com.example.outtakeapp.R

class MessageAdapter(val messageList: List<Message>): RecyclerView.Adapter<MessageHolder>() {

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        Message.TYPE_RECEIVED -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_left, parent, false)
            LeftViewHolder(view)
        }
        else -> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_right, parent, false)
            RightViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val message = messageList[position]
        when (holder) {
            is LeftViewHolder -> holder.leftMsg.text = message.message
            is RightViewHolder -> holder.rightMsg.text = message.message
        }
    }

    //判断消息类型
    override fun getItemViewType(position: Int): Int {
        return messageList[position].type
    }
}

sealed class MessageHolder(view: View): RecyclerView.ViewHolder(view)
class LeftViewHolder(view: View): MessageHolder(view){
    val leftMsg: TextView = view.findViewById(R.id.msg)
}
class RightViewHolder(view: View): MessageHolder(view)   {
    val rightMsg: TextView = view.findViewById(R.id.msg)
}