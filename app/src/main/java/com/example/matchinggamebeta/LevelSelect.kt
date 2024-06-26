package com.example.matchinggamebeta

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.matchinggamebeta.databinding.FragmentFirstBinding
import com.example.matchinggamebeta.databinding.FragmentLevelSelectBinding
import com.example.matchinggamebeta.models.BoardSize
import com.google.android.material.textfield.TextInputEditText


class LevelSelect : Fragment() {
    private var _binding: FragmentLevelSelectBinding? = null
    private lateinit var selectEasy:Button
    private lateinit var selectMedium:Button
    private lateinit var selectDifficult:Button
    private lateinit var txtFieldUsername: TextInputEditText
    private var username: String = ""
    private lateinit var homeButton: ImageButton
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPrefFile = "com.example.matchinggamebeta"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_level_select, container, false)
        txtFieldUsername = view.findViewById(R.id.txtFieldUsername)

        sharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // Retrieve username from SharedPreferences
        username = sharedPreferences.getString("username", "") ?: ""

        // Set the saved username to the TextInputEditText
        txtFieldUsername.setText(username)

        txtFieldUsername.addTextChangedListener {
            // Save username to SharedPreferences
            username = it.toString()
            with(sharedPreferences.edit()) {
                putString("username", username)
                apply()
            }
        }

        return view
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

        val saveButton: Button = view.findViewById(R.id.btnSave)
        saveButton.setOnClickListener {
            // Save username to SharedPreferences
            username = txtFieldUsername.text.toString()
            with(sharedPreferences.edit()) {
                putString("username", username)
                apply()
            }
        }

        homeButton = view.findViewById(R.id.btnHome1)
        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_levelSelect_to_FirstFragment)
        }
    }

    private fun navigateToSecondFragment(boardSize: BoardSize) {
        val bundle = Bundle().apply {
            putSerializable("boardSize", boardSize)
        }
        findNavController().navigate(R.id.action_levelSelect_to_SecondFragment, bundle)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("switch_state", false)) {
            (activity as MainActivity).startBackgroundMusic()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}