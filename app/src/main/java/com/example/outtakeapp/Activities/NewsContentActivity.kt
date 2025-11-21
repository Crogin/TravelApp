package com.example.outtakeapp.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.example.outtakeapp.R

class NewsContentActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news_content)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        if (title != null && content != null){
            findViewById<TextView>(R.id.title).text = title
            findViewById<TextView>(R.id.content).text = content
        }
    }

    companion object{
        fun startAty(context: Context, title: String, content : String){
            val intent = Intent(context, NewsContentActivity::class.java).apply {
                putExtra("title", title)
                putExtra("content", content)
            }
            context.startActivity(intent)
        }
    }
}