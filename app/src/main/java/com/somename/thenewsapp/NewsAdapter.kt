package com.somename.thenewsapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val context: Context, private var articles: ArrayList<Article>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.titleView.text = article.title
        holder.authorView.text = article.author
        holder.descriptionView.text = article.description
        Glide.with(context)
            .load(article.urlToImage)
            .placeholder(R.drawable.img)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ReadNewsActivity::class.java)
            intent.putExtra("URL", article.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setArticles(articles:ArrayList<Article?>?){
        articles?.filterNotNullTo(this.articles)
        notifyDataSetChanged()
    }


    inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleView: TextView = itemView.findViewById(R.id.title_view)
        val authorView: TextView = itemView.findViewById(R.id.author_view)
        val descriptionView : TextView = itemView.findViewById(R.id.description_view)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

}