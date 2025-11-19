package com.example.outtakeapp.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.outtakeapp.Model.Fruit
import com.example.outtakeapp.R

class ListViewAdapter(activity: Activity, val resourceId: Int, data: List<Fruit>): ArrayAdapter<Fruit>(activity, resourceId, data) {
    inner class ViewHolder(val textView: TextView, val imageView: ImageView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val viewHolder: ViewHolder
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val newText = view.findViewById<TextView>(R.id.text)
            val image = view.findViewById<ImageView>(R.id.image)
            viewHolder = ViewHolder(newText, image)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val fruit = getItem(position) //获取当前项的Fruit实例
        if (fruit != null) {
            viewHolder.textView.text = fruit.name
            viewHolder.imageView.setImageResource(fruit.image)
        }
        return view
    }
}