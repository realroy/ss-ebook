package com.example.oldroy.ssebook.models

import android.util.Log
import com.example.oldroy.ssebook.Contact
import com.example.oldroy.ssebook.connections.BookApi
import com.example.oldroy.ssebook.enums.BookEnum
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Observable


class BookRepo : Observable, Contact.Repository<Book> {


    private val API_URL = "https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/"
    private val bookList = HashMap<String, Book>()

    constructor() {
        fetchAllBooks()
    }

    override fun new(key: String, obj: Book): Boolean {
        if(bookList.containsKey(key)) return false
        else bookList.put(key, obj)
        return true
    }

    override fun all(): List<Book> {
        return bookList.values.toList()
    }

    override fun all(sortBy: Any?): List<Book> {
        val list = bookList.values.toList()
        when (sortBy) {
            BookEnum.TITLE -> return list.sortedWith(compareBy({ it.title }))
            BookEnum.PUB_YEAR -> return list.sortedWith(compareBy({ it.pubYear }))
            else -> return list
        }
    }
    override fun where(filters: List<Filter>): List<Book> {
        val result = ArrayList<Book>()
        filters.forEach { filter ->
            run {
                when (filter.attribute) {
                    BookEnum.TITLE -> result.addAll(all().filter { book -> book.title.equals(filter.value) })
                    BookEnum.PRICE -> result.addAll(all().filter { book -> book.price == filter.value.toDouble() })
                    BookEnum.PUB_YEAR -> result.addAll(all().filter { book -> book.pubYear == filter.value.toInt() })
                    else -> result.addAll(all())
                }
            }
        }
        return result
    }

    override fun where(filters: List<Filter>, sortBy: Any): List<Book> {
        val filteredList = where(filters)
        when(sortBy as BookEnum) {
            BookEnum.TITLE -> return filteredList.sortedWith(compareBy { it.title })
            BookEnum.PUB_YEAR -> return filteredList.sortedWith(compareBy { it.pubYear })
            BookEnum.PRICE -> return filteredList.sortedWith(compareBy { it.price })
            else -> return filteredList
        }
    }

    override fun delete(filters: List<Filter>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(oldAttr: List<Filter>, newAttr: List<Filter>): Boolean {

    }

    override fun size(): Int {
        return bookList.size
    }
//    private fun fetchAllBooks() {
//
//        val gson = GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create()
//
//        val retrofit = Retrofit.Builder()
//                .baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//
//        val bookApi = retrofit.create(BookApi::class.java)
//
//        bookApi.getAllBooks().enqueue(object : Callback<List<Book>> {
//            override fun onResponse(call: Call<List<Book>>?, response: Response<List<Book>>?) {
//                bookList.clear()
//                response?.body()?.forEach { book -> bookList.put(book.id.toString(), book) }
//                setChanged()
//                notifyObservers()
//            }
//
//            override fun onFailure(call: Call<List<Book>>?, t: Throwable?) {
//                Log.e("onFailure", t.toString())
//            }
//        })
//    }
//
//    private fun sortBy(list: List<Book>, attr: BookEnum): List<Book> {
//
//    }
//
//    override fun getAndSortBy(sortBy: Any?): List<Book> {
//        return sortBy(get(), sortBy as BookEnum)
//    }
//
//    override fun getByFilter(filter: Filter): List<Book> {
//        when (filter.attribute) {
//            BookEnum.TITLE -> {
//                if (!filter.value.equals("")) return get().filter { (title) -> title.toUpperCase().contains(filter.value.toUpperCase()) }
//                else return get().toList()
//            }
//            BookEnum.PRICE -> return get().filter { book -> book.price.toString().contains(filter.value) }
//            BookEnum.PUB_YEAR -> return get().filter { book -> book.pubYear.toString().contains(filter.value) }
//            else -> return get().toList()
//        }
//    }
//
//    override fun getByFilterAndSortBy(filter: Filter, sortBy: Any?): List<Book> {
//        return sortBy(getByFilter(filter), sortBy as BookEnum)
//    }



}
