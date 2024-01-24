package com.dicoding.jetpack.model

data class Daerah(
    val id: Long,
    val image: String,
    val title: String,
    val desc: String,
    val rate: String,
    var isFav: Boolean = false
)