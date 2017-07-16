package com.example.oldroy.ssebook

import com.example.oldroy.ssebook.models.Filter

class Contact<T> {
    interface View {
        fun updateInfo()
    }

    interface Presenter<T> {
        fun get(): List<T>
        fun get(filter: Filter): List<T>
        fun get(sortBy: Any): List<T>
        fun get(filter: Filter, sortBy: Any): List<T>
    }

    interface Repository<T> {
        fun new(key: String, obj: T): Boolean
        fun all(): List<T>
        fun all(sortBy: Any?): List<T>
        fun where(filters: List<Filter>): List<T>
        fun where(filters: List<Filter>, sortBy: Any): List<T>
        fun size(): Int
        fun delete(filters: List<Filter>): Boolean
        fun update(key: String): Boolean
    }
}