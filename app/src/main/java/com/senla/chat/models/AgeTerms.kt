package com.senla.chat.models

enum class AgeTerms(array: Array<Int>) {
    SMALL(arrayOf(0,18)),
    YOUNG(arrayOf(18,25)),
    MEDIUM(arrayOf(25,35)),
    OLD(arrayOf(35,100))
}