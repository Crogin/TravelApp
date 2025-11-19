package com.example.outtakeapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.outtakeapp.Model.Fruit
import com.example.outtakeapp.R

class RecycleViewAdapter(val fruitList: List<Fruit>): RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>()  {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text)
        val imageView: ImageView = view.findViewById(R.id.image)
    }

    // 创建ViewHolder实例
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_new, parent, false)

        return ViewHolder(view)
    }

    // 绑定数据
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.textView.text = fruit.name
        holder.imageView.setImageResource(fruit.image)
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "点击了第${position}个", Toast.LENGTH_SHORT).show()
        }
        holder.imageView.setOnClickListener {
            Toast.makeText(holder.imageView.context, "点击图片", Toast.LENGTH_SHORT).show()
        }
    }

    // 获取数据源大小
    override fun getItemCount(): Int {
        return fruitList.size
    }
}