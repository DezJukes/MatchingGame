package com.example.matchinggamebeta.models

import android.widget.TextView
import com.example.matchinggamebeta.MemoryCard
import com.example.matchinggamebeta.utils.DEFAULT_ICONS

class MemoryGame (private val boardSize: BoardSize) {

    val cards: List<MemoryCard>
    var numPairsFound = 0
    var numFlips = 0

    private var indexOfSingleSelectedCard: Int? = null

    init {
        val chosenImages:List<Int> = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map {MemoryCard(it)}
    }

    fun flipCard(position: Int): Boolean {
        numFlips++
        val card = cards[position]
        //three cases
        // 0 cards previous flipped over => flip over the selected card
        // 1 card previous flipped over => flip over the selected card + check if the images match
        // 2 cards previous flipped over => restore the cards + flip over the selected card
        var foundMatch = false
        if (indexOfSingleSelectedCard == null){
            //0 or 2 selected cards previously flipped over
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 cards was selected previously
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }

        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier) {
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for(card in cards){
            if(!card.isMatched){
                card.isFaceUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }
}