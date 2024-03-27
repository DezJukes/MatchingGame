package com.example.matchinggamebeta.models

enum class BoardSize (val numCards: Int){
    EASY(numCards = 16),
    MEDIUM(numCards = 20),
    DIFFICULT(numCards = 36);

    fun getWidth(): Int{
        return when (this){
            EASY -> 4
            MEDIUM -> 4
            DIFFICULT -> 6
        }

    }
    fun getHeight(): Int{
        return numCards/getWidth()
    }
    fun getNumPairs(): Int{
        return numCards/2
    }
}