package com.example.oldroy.ssebook.models

import android.util.Log
import com.example.oldroy.ssebook.enums.BookAttr
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class BookRepo : Observable {
    private val apiUrl = "https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/"
    private var bookList: HashMap<String, Book> = HashMap()

    constructor() {
        fetchAllBooks()
    }

    // TODO: Implement fetch real api
    fun fetchAllBooks() {
        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl(apiUrl).addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val bookApi = retrofit.create(BookApi::class.java)
        bookApi.getAllBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>?, response: Response<List<Book>>?) {
                bookList.clear()
                response?.body()?.forEach { book -> bookList.put(book.id.toString(), book) }
                setChanged()
                notifyObservers()
            }

            override fun onFailure(call: Call<List<Book>>?, t: Throwable?) {
                Log.e("now", t.toString())
            }
        })

    }

    fun getAllBook(): MutableCollection<Book> {
        return bookList.values
    }

    fun getBookByFilter(filters: ArrayList<Filter>): MutableCollection<Book> {
        val result: ArrayList<Book> = ArrayList<Book>()
        for (filter in filters) {
            when (filter.attribute) {
                BookAttr.TITLE.toString() -> {
                    getAllBook()
                            .filter { (title) -> title == filter.value }
                            .forEach { book -> result.add(book) }
                }
                BookAttr.PRICE.toString() -> {
                    getAllBook()
                            .filter { book -> book.price == filter.value.toDouble() }
                            .forEach { book -> result.add(book) }
                }
                BookAttr.PUB_YEAR.toString() -> {
                    getAllBook()
                            .filter { book -> book.pubYear == filter.value.toInt() }
                            .forEach { book -> result.add(book) }
                }
                else -> getAllBook()
            }
        }
        return result
    }

    fun sortBy(attr: BookAttr) {
    }

    fun size(): Int {
        return bookList.size
    }

}
