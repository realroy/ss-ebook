package com.example.oldroy.ssebook.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.oldroy.ssebook.Contact
import com.example.oldroy.ssebook.R
import com.example.oldroy.ssebook.adapters.BooksAdapter
import com.example.oldroy.ssebook.enums.BookEnum
import com.example.oldroy.ssebook.models.Book
import com.example.oldroy.ssebook.models.Filter
import com.example.oldroy.ssebook.models.User
import com.example.oldroy.ssebook.presenters.BookPresenter


class StoreActivity : AppCompatActivity(), Contact.View {

    private val bookPresenter: Contact.Presenter<Book> = BookPresenter(this)
    private val books = ArrayList<Book>()
    private val bookAdapter = BooksAdapter(this, books)

    private var sortMode = BookEnum.TITLE
    private var filterMode = Filter(BookEnum.TITLE, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        setUpSpinner()
        setUpRecycleView()
        updateInfo()
        val user = intent.getSerializableExtra("USER") as User
        Toast.makeText(this, "Hello, ${user.name}!", Toast.LENGTH_LONG).show()
    }

    private fun setUpSpinner() {
        val spinner_sort_by: Spinner? = findViewById(R.id.spinner_sort_by) as Spinner?
        val sortByAdapter = ArrayAdapter.createFromResource(this, R.array.sort_book, android.R.layout.simple_dropdown_item_1line)

        sortByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_sort_by?.adapter = sortByAdapter
        spinner_sort_by?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val resStringArr = resources.getStringArray(R.array.sort_book)
                when (parent?.getItemAtPosition(position)) {
                    resStringArr[0] -> sortMode = BookEnum.TITLE
                    else -> sortMode = BookEnum.PUB_YEAR
                }
                updateInfo()
            }

        }
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
                val q = query?.trim()
                try {
                    val number = q?.toDouble()
                    val temp = Math.round(number as Double)
                    if (temp.equals(number)) {
                        filterMode = Filter(BookEnum.PUB_YEAR, q)
                    } else {
                        filterMode = Filter(BookEnum.PRICE, q)
                    }
                } catch (e: NumberFormatException) {
                    filterMode = Filter(BookEnum.TITLE, q as String)
                }
                updateInfo()
                return true
            }
        })
        searchView?.queryHint = "Search"
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpRecycleView() {
        val recycler_view = findViewById(R.id.recycler_view) as RecyclerView
        val layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layoutManager as RecyclerView.LayoutManager?
        recycler_view.run {
            itemAnimator = DefaultItemAnimator()
            adapter = bookAdapter
        }
    }

    override fun updateInfo() {
        books.clear()
        val info = bookPresenter.get(filterMode, sortMode)
        books.addAll(info)
        bookAdapter.notifyDataSetChanged()
    }

}
