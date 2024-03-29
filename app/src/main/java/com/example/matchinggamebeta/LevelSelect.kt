package com.example.matchinggamebeta

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.matchinggamebeta.models.BoardSize


class LevelSelect : Fragment() {
    private lateinit var selectEasy:Button
    private lateinit var selectMedium:Button
    private lateinit var selectDifficult:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectEasy = view.findViewById(R.id.btnEasy)
        selectMedium = view.findViewById(R.id.btnMedium)
        selectDifficult = view.findViewById(R.id.btnDifficult)

        selectEasy.setOnClickListener {
            navigateToSecondFragment(BoardSize.EASY)
        }
        selectMedium.setOnClickListener {
            navigateToSecondFragment(BoardSize.MEDIUM)
        }
        selectDifficult.setOnClickListener {
            navigateToSecondFragment(BoardSize.DIFFICULT)
        }
    }

    private fun navigateToSecondFragment(boardSize: BoardSize) {
        val bundle = Bundle().apply {
            putSerializable("boardSize", boardSize)
        }
        findNavController().navigate(R.id.action_levelSelect_to_SecondFragment, bundle)
    }

}