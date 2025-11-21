package com.example.outtakeapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.outtakeapp.R

class NewsContentFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_content_frag,container,false)
    }

    fun reFlash(title:String,content: String){
        val view = view?.findViewById< View>(R.id.contentLayout) as LinearLayout
        val newsTitle = view.findViewById<View>(R.id.title) as TextView
        val newsContent = view.findViewById<View>(R.id.content) as TextView
        view.visibility = View.VISIBLE
        newsTitle.text = title // 设置标题
        newsContent.text = content  // 设置内容
    }
}