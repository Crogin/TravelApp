package com.example.outtakeapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outtakeapp.Activities.NewsContentActivity
import com.example.outtakeapp.Model.News
import com.example.outtakeapp.R

@Suppress("DEPRECATION")
class NewsTitleFragment: Fragment() {

    private var isTwoPane = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_title_frag,container,false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isTwoPane = activity?.findViewById<View>(R.id.newsTitleFrag) != null
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = view?.findViewById<View>(R.id.recyclerView) as RecyclerView
        val adapter = NewsAdapter(getNews())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


    }

    private fun getNews(): List<News> {
        val newsList = ArrayList<News>()
        for (i in 1..50) {
            val news = News("This is news title $i", getRandomLengthString("This is news content $i. "))
            newsList.add(news)
        }
        return newsList
    }

    private fun getRandomLengthString(string: String): String {

        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n){
            builder.append(string)
        }
        return builder.toString()
    }

    inner class NewsAdapter(private val newsList: List<News>) :
        RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val newsTitleText: TextView = view.findViewById(R.id.news_title)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): NewsAdapter.ViewHolder {
            val holder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
            val view = holder.itemView
            view.setOnClickListener {
                val news = newsList[holder.adapterPosition]
                if (isTwoPane){
                    // 如果是双页模式，则将内容展示在右侧的NewsContentFragment中
                    val fragment = NewsContentFragment()
                    fragment.reFlash(news.title, news.content)
                } else {
                    // 如果是单页模式，则直接启动NewsContentActivity
                    NewsContentActivity.startAty(view.context, news.title, news.content)
                }
            }


            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsTitleText.text = news.title
        }

        override fun getItemCount(): Int {
            return newsList.size
        }
    }
}
