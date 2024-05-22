package com.deniz.vlmediadating.api.data

data class User(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val location: Location,
    val origin: Location,
    val episode: List<String>
)