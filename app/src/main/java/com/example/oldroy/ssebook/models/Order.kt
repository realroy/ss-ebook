package com.example.oldroy.ssebook.models

import java.io.Serializable

data class Order(val userName: String, val bookList: List<Book>) : Serializable
