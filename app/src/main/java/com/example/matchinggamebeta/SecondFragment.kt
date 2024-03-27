package com.example.matchinggamebeta

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.matchinggamebeta.databinding.FragmentSecondBinding
import android.app.AlertDialog
import android.media.MediaPlayer
import android.widget.Adapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchinggamebeta.models.BoardSize
import com.example.matchinggamebeta.models.MemoryGame
import com.example.matchinggamebeta.utils.DEFAULT_ICONS

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
    private lateinit var backgroundMusicPlayer: MediaPlayer
    private lateinit var cardClickSoundPlayer: MediaPlayer

    private lateinit var rvBoard: RecyclerView
    private lateinit var resetGame:Button
    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter
    private var boardSize = BoardSize.EASY
    private var gameFinished = false
    private var isBackgroundMusicPlaying = false
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
        rvBoard = binding.rvBoard

        backgroundMusicPlayer = MediaPlayer.create(requireContext(), R.raw.bgm_menu)
        backgroundMusicPlayer.isLooping = true

        cardClickSoundPlayer = MediaPlayer.create(requireContext(), R.raw.card_flip)

        val boardSize = arguments?.getSerializable("boardSize") as BoardSize
        setupBoard(boardSize)

        startTimer(boardSize)
        resetGame = binding.btnReset
        resetGame.setOnClickListener {
            setupBoard(boardSize)
            resetGameOverall()
            countDownTimer.cancel()
            startTimer(boardSize)
        }
    }
    override fun onResume() {
        super.onResume()
        if (!isBackgroundMusicPlaying) {
            backgroundMusicPlayer.start()
            isBackgroundMusicPlaying = true
        }
    }

    override fun onPause() {
        super.onPause()
        if (isBackgroundMusicPlaying) {
            backgroundMusicPlayer.pause()
            isBackgroundMusicPlaying = false
        }
    }

    private fun resetGameOverall() {
        flips = 0
        score = 0
        binding.textFlipsCounter.text = "$flips"
        binding.textScoreCounter.text = "$score"
        gameFinished = false
    }

    private fun setupBoard(boardSize: BoardSize) {
        this.boardSize = boardSize

        memoryGame = MemoryGame(boardSize)

        adapter = MemoryBoardAdapter(requireContext(), boardSize, memoryGame.cards, object : MemoryBoardAdapter.CardClickListener {
            override fun onCardClicked(position: Int) {
                updateGameWithFlip(position)
            }

        })
        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(requireContext(), boardSize.getWidth())
    }

    private fun updateGameWithFlip(position: Int) {
        //Error checking
        if (memoryGame.haveWonGame()){
            return
        }
        if (memoryGame.isFaceUp(position)){
            return
        }
        if (gameFinished){
            return
        }
        flips++
        binding.textFlipsCounter.text = "$flips"
        if(memoryGame.flipCard(position)){
            score+=5
            binding.textScoreCounter.text = "$score"
        }
        adapter.notifyDataSetChanged()

        if (memoryGame.haveWonGame()){
            shuffleCardsAgain()
        }
    }

    private fun shuffleCardsAgain() {
        setupBoard(boardSize)
        adapter.notifyDataSetChanged()
    }

    private fun startTimer(boardSize: BoardSize) {
        val timerDuration = when (boardSize) {
            BoardSize.EASY -> 60_000L // 1 min
            BoardSize.MEDIUM -> 105_000L // 1 min 45 secs
            BoardSize.DIFFICULT -> 120_000L // 2 min
        }

        countDownTimer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val minutes = secondsLeft / 60
                val seconds = secondsLeft % 60
                binding.textTimerCount.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                backgroundMusicPlayer.pause()
                gameFinished = true
                playGameCompletedSound()
                val showPopUp = popUpScreen()

                val args = Bundle()
                args.putInt(popUpScreen.ARG_SCORE, score) // Pass the score here
                showPopUp.arguments = args

                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Release MediaPlayer resources when the fragment is destroyed
        backgroundMusicPlayer.release()
        cardClickSoundPlayer.release()
    }

    private fun playCardClickSound() {
        cardClickSoundPlayer.seekTo(0) // Reset sound to start
        cardClickSoundPlayer.start()
    }

    private fun playGameCompletedSound(){
        val gameCompletedSoundPlayer = MediaPlayer.create(requireContext(), R.raw.game_completed)
        gameCompletedSoundPlayer.start()

        gameCompletedSoundPlayer.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.release()
        }
    }

}