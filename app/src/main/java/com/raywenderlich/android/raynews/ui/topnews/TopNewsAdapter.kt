package com.raywenderlich.android.raynews.ui.topnews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.raywenderlich.android.raynews.R
import com.raywenderlich.android.raynews.model.topnews.Article

class TopNewsAdapter(private var list:List<Article>)
    : RecyclerView.Adapter<TopNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.top_news_list_row, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int  = list.size

    fun setItems(items: List<Article>) {
        this.list = items
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val subjectNameTextView: TextView = itemView.findViewById(R.id.text)

        fun bind(currentValue: Article) = with(itemView) {
            subjectNameTextView.text = currentValue.title
        }
    }

}