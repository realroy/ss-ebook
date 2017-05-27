package com.example.oldroy.ssebook.presenters

import com.example.oldroy.ssebook.BookContact
import com.example.oldroy.ssebook.models.Book
import com.example.oldroy.ssebook.models.BookRepo
import com.example.oldroy.ssebook.models.Filter
import java.util.*

class BookPresenter(val view: BookContact.BookView) : BookContact.BookPresenter, Observer {

    private val repo: BookRepo = BookRepo()

    override fun getInformation(): MutableCollection<Book> {
        repo.fetchAllBooks()
        return repo.getAllBook()
    }

    override fun update(o: Observable?, information: Any?) {
        this.view.updateInformation()
    }


    override fun getInformation(filter: Filter) {
    }
}
