package com.example.outtakeapp.utils

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.outtakeapp.R

class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs){
    init {
        LayoutInflater.from(context).inflate(R.layout.toolbar_common,this)
        val back = findViewById<Button>(R.id.back)
        val title = findViewById< TextView>(R.id.title)
        back.setOnClickListener {
            (context as Activity).finish()
        }
        title.setOnClickListener {
            Toast.makeText(context,"点击了标题",Toast.LENGTH_SHORT).show()
        }
    }
}