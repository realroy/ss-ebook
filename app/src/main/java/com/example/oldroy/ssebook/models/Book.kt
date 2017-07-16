package com.example.oldroy.ssebook.models

import java.io.Serializable

data class Book(
        val title: String = "",
        val id: Int = 0,
        val price: Double = 0.0,
        val pubYear: Int = 0,
        val img_url: String = ""
) : Serializable
