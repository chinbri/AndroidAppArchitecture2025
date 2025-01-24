package com.apparchitecture.domain

data class MyEntity(
    val results: List<Character> = emptyList()
)

data class Character(
    val name: String,
    val species: String
)