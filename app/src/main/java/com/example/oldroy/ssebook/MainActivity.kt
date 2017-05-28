package com.example.oldroy.ssebook

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.oldroy.ssebook.models.Book
import com.example.oldroy.ssebook.models.Filter
import com.example.oldroy.ssebook.presenters.BookPresenter


class MainActivity : AppCompatActivity(), BookContact.BookView {


    private val bookPresenter: BookContact.BookPresenter = BookPresenter(this)
    private val books: ArrayList<Book> = ArrayList()
    private var bookAdapter: BooksAdapter = BooksAdapter(this, books)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycleView()
        updateInformation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                try {
                    val number = query?.trim()?.toDouble()
                    val temp = Math.round(number as Double)
                    val filter = if (temp.equals(number)) Filter("year", query) else Filter("price", query)
                    books.clear()
                    books.addAll(bookPresenter.getInformation(filter))

                } catch (e: NumberFormatException) {
                    books.clear()
                    books.addAll(bookPresenter.getInformation(Filter("title", query?.trim() as String)))
                }
                return true
            }

        })
        searchView?.queryHint = "Search"
        return super.onCreateOptionsMenu(menu)
    }

    fun initRecycleView() {
        val recycler_view = findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layoutManager as RecyclerView.LayoutManager?
        recycler_view.run {
            itemAnimator = DefaultItemAnimator()
            adapter = bookAdapter
        }
    }

    override fun updateInformation() {
        books.clear()
        books.addAll(bookPresenter.getInformation())
        bookAdapter.notifyDataSetChanged()
    }


}
