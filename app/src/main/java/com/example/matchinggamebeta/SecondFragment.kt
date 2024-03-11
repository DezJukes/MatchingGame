package com.example.matchinggamebeta

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matchinggamebeta.databinding.FragmentSecondBinding
import android.app.AlertDialog
import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    private var score = 0
    private var flips = 0
    private lateinit var textTimerCount: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var startTimeMillis: Long = 0
    private var matchedCardCount = 0
    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        initializeViews()
        startTimer()

    }

    private fun initializeViews(){
        buttons = listOf(
            binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4, binding.imageButton5,
            binding.imageButton6, binding.imageButton7, binding.imageButton8, binding.imageButton9, binding.imageButton10,
            binding.imageButton11, binding.imageButton12, binding.imageButton13, binding.imageButton14, binding.imageButton15,
            binding.imageButton16, binding.imageButton17, binding.imageButton18, binding.imageButton19, binding.imageButton20
        )

        val images = mutableListOf(
            R.drawable.apple, R.drawable.avocado, R.drawable.banana,
            R.drawable.cherry, R.drawable.eggplant, R.drawable.grape,
            R.drawable.orange, R.drawable.pineapple, R.drawable.strawberry,
            R.drawable.watermelon)

        images.addAll(images)
        images.shuffle()

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])

        }

        buttons.forEachIndexed{ index, button ->
            button.setOnClickListener{
                //update models
                updateModels(index)
                updateViews()
            }
        }

        textTimerCount = binding.textTimerCount
        binding.textScoreCounter.text = "$score"
        binding.textFlipsCounter.text = "$flips"

    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.androidback)
        }
    }

    private fun updateModels(position:Int){
        val card = cards[position]

        //Error checking
        if(card.isFaceUp){
            return
        }
        //three cases
        // 0 cards previous flipped over => flip over the selected card
        // 1 card previous flipped over => flip over the selected card + check if the images match
        // 2 cards previous flipped over => restore the cards + flip over the selected card
        if(indexOfSingleSelectedCard == null){
            //0 or 2 selected cards
            indexOfSingleSelectedCard = position
            restoreCards()
        } else {
            // exactly 1 cards was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        flips++
        binding.textFlipsCounter.text = "$flips"
    }

    private fun checkForMatch(position1: Int, position2: Int){
        if(cards[position1].identifier == cards[position2].identifier){
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            score+=5
            binding.textScoreCounter.text = "$score"
            matchedCardCount += 2
        } else {
            // If the cards don't match, flip them back after a short delay
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                cards[position1].isFaceUp = false
                cards[position2].isFaceUp = false
                updateViews()
            }, 500) // Adjust the delay time as needed
        }

        if (matchedCardCount == cards.size) {
            // Stop the timer when all cards are matched
            countDownTimer.cancel()
            // Ask if want to play again
            showPlayAgainDialog()
        }
    }

    private fun restoreCards(){
        for(card in cards){
            if(!card.isMatched){
                card.isFaceUp = false
            }
        }
    }

    private fun updateTimer(elapsedTimeMillis: Long) {
        val seconds = (elapsedTimeMillis / 1000).toInt()
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        textTimerCount.text = String.format("%02d:%02d", minutes, remainingSeconds)
    }

    private fun showPlayAgainDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Congratulations!")
        builder.setMessage("You've matched all the cards! Would you like to play again?")

        builder.setPositiveButton("Yes") { _, _ ->
            // Reset the game
            resetGame()
        }

        builder.setNegativeButton("No") { _, _ ->
            // Exit the game or perform any other action
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun resetGame() {
        // Reset game variables, shuffle cards, start timer, etc.
        matchedCardCount = 0
        score = 0
        flips = 0
        cards.forEach { it.reset() }
        indexOfSingleSelectedCard = null
        updateViews()
        cards.forEach { it.isMatched = false }
        binding.textFlipsCounter.text = "$flips"
        binding.textScoreCounter.text = "$score"
        initializeViews()
        startTimer()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis
                updateTimer(elapsedTimeMillis)
            }

            override fun onFinish() {
                // Timer finished (not relevant for increasing timer)
            }
        }
        startTimeMillis = System.currentTimeMillis()
        countDownTimer.start()
    }

}