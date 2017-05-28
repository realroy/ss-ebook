package com.example.oldroy.ssebook.presenters

import com.example.oldroy.ssebook.BookContact
import com.example.oldroy.ssebook.models.Book
import com.example.oldroy.ssebook.models.BookRepo
import com.example.oldroy.ssebook.models.Filter
import java.util.*

class BookPresenter : BookContact.BookPresenter, Observer {

    private val repo: BookRepo
    private val view: BookContact.BookView

    constructor(view: BookContact.BookView) {
        this.view = view
        this.repo = BookRepo()
        repo.addObserver(this)
    }

    override fun getInformation(): MutableCollection<Book> {
        return repo.getAllBook()
    }

    override fun update(o: Observable?, information: Any?) {
        view.updateInformation()
    }

    override fun getInformation(filter: Filter): List<Book> {
        return repo.getBookByFilter(filter)
    }
}
