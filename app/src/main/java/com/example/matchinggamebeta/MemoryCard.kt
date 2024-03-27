package com.example.matchinggamebeta

data class MemoryCard(
    val identifier: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false)
fun MemoryCard.reset() {
    isFaceUp = false
    isMatched = false
}