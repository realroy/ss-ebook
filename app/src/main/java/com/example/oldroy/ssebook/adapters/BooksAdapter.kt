package com.example.oldroy.ssebook.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.oldroy.ssebook.R
import com.example.oldroy.ssebook.models.Book

class BooksAdapter(private var context: Context, private var books: List<Book>) : RecyclerView.Adapter<BooksAdapter.viewHolder>() {

    class viewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val text_price = itemView?.findViewById(R.id.text_price) as TextView
        val text_title = itemView?.findViewById(R.id.text_title) as TextView
        val text_pubYear = itemView?.findViewById(R.id.text_year) as TextView
        val image_book = itemView?.findViewById(R.id.image_book) as ImageView
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: viewHolder?, position: Int) {
        val book = this.books[position]
        viewHolder?.text_title?.text = book.title
        viewHolder?.text_price?.text = "${book.price} $"
        viewHolder?.text_pubYear?.text = book.pubYear.toString()
        Glide.with(this.context)
                .load(book.img_url)
                .into(viewHolder?.image_book)
    }

    override fun getItemCount(): Int = books.size

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): viewHolder {
        val itemView: View = LayoutInflater.from(viewGroup?.context)
                .inflate(R.layout.book_card, viewGroup, false)
        return viewHolder(itemView)
    }
}

