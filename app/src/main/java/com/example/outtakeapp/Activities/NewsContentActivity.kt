package com.example.outtakeapp.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.example.outtakeapp.Fragment.NewsContentFragment
import com.example.outtakeapp.R
import com.example.outtakeapp.utils.startActivity

class NewsContentActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news_content)
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        if (title != null && content != null){
            val fragment = supportFragmentManager.findFragmentById(R.id.newsContentFrag) as NewsContentFragment
            fragment.reFlash(title, content)
        }
    }

    companion object{
        fun startAty(context: Context, title: String, content : String){
            //泛型实例化，传入参数
            startActivity<NewsContentActivity>(context){
                putExtra("title", title)
                putExtra("content", content)
            }
        }
    }
}