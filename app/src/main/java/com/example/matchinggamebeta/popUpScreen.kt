package com.example.matchinggamebeta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController


class popUpScreen : DialogFragment() {
    private lateinit var totalScore:TextView
    private lateinit var playAgainButton:Button
    private lateinit var homeButton:Button
    private lateinit var leaderboardButton:Button

    companion object {
        const val ARG_SCORE = "arg_score"
    }

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_up_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            // Retrieve the score argument
            score = it.getInt(ARG_SCORE, 0)
        }
        totalScore = view.findViewById(R.id.tvTotalScore)
        totalScore.text = "$score"

        playAgainButton = view.findViewById(R.id.btnPlayAgain)
        homeButton = view.findViewById(R.id.btnHome)
        leaderboardButton = view.findViewById(R.id.btnLeaderboard)

        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            dismiss()
        }

        leaderboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_leaderboard)
            dismiss()
        }

    }
}