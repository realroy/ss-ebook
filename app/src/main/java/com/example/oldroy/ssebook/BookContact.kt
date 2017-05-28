package com.example.oldroy.ssebook

import com.example.oldroy.ssebook.models.Book
import com.example.oldroy.ssebook.models.Filter

class BookContact {
    interface BookView {
        fun updateInformation()
    }

    interface BookPresenter {
        fun getInformation(): MutableCollection<Book>
        fun getInformation(filter: Filter): List<Book>
    }
}