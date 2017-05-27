package com.example.oldroy.ssebook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.oldroy.ssebook.models.Book
import com.example.oldroy.ssebook.presenters.BookPresenter


class MainActivity : AppCompatActivity(), BookContact.BookView {

    private val bookPresenter: BookContact.BookPresenter = BookPresenter(this)
    private val books: ArrayList<Book> = ArrayList()
    private var bookAdapter: BooksAdapter = BooksAdapter(this, books)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler_view = findViewById(R.id.recycler_view) as RecyclerView
        bookAdapter = BooksAdapter(this, books)
        val layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layoutManager as RecyclerView.LayoutManager?
        recycler_view.run {
            itemAnimator = DefaultItemAnimator()
            adapter = bookAdapter
        }
        updateInformation()
    }

    override fun updateInformation() {
        books.clear()
        bookPresenter.getInformation().forEach { book: Book -> books.add(book) }
        bookAdapter.notifyDataSetChanged()
    }

}
