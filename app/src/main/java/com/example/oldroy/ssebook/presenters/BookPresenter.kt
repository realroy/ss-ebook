package com.example.oldroy.ssebook.presenters

import com.example.oldroy.ssebook.Contact
import com.example.oldroy.ssebook.enums.BookEnum
import com.example.oldroy.ssebook.models.Book
import com.example.oldroy.ssebook.models.BookRepo
import com.example.oldroy.ssebook.models.Filter
import java.util.Observer
import java.util.Observable

class BookPresenter : Contact.Presenter<Book>, Observer {


    private val repo: Repository<Book>
    private val view: Contact.View

    constructor(view: Contact.View) {
        this.view = view
        this.repo = BookRepo()
        repo.addObserver(this)
    }

    override fun get(): List<Book> {
        return repo.get()
    }

    override fun update(o: Observable?, information: Any?) {
        view.updateInfo()
    }

    override fun get(filter: Filter, sortBy: Any): List<Book> {
        return repo.getByFilterAndSortBy(filter, sortBy as BookEnum)
    }

    override fun get(filter: Filter): List<Book> {
        return repo.getByFilter(filter)
    }

    override fun get(sortBy: Any): List<Book> {
        return repo.getAndSortBy(sortBy as BookEnum)
    }
}
