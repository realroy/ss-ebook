package com.example.oldroy.ssebook.models

import com.example.oldroy.ssebook.enums.BookAttr
import java.util.*
import kotlin.collections.ArrayList

class BookRepo : Observable() {
    private val apiUrl = "https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/books.json"
    private var bookList: HashMap<String, Book> = HashMap()

    // TODO: Implement fetch real api
    fun fetchAllBooks() {
        val b1 = Book(
                "Functional Web Development with Elixir, OTP, and Phoenix",
                471,
                24.95,
                2017,
                "https://imagery.pragprog.com/products/471/lhelph_largebeta.jpg")
        val b2 = Book(
                "A Common-Sense Guide to Data Structures and Algorithms",
                504,
                24.95,
                2017,
                "https://imagery.pragprog.com/products/471/lhelph_largebeta.jpg")
        val b3 = Book(
                "Rails, Angular, Postgres, and Bootstrap, Second Edition",
                508,
                24.95,
                2017,
                "https://imagery.pragprog.com/products/471/lhelph_largebeta.jpg")
        bookList.put(b1.id.toString(), b1)
        bookList.put(b2.id.toString(), b2)
        bookList.put(b3.id.toString(), b3)
        setChanged()
        notifyObservers()
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
