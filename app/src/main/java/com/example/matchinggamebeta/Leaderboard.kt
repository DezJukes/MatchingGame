package com.example.matchinggamebeta

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [Leaderboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class Leaderboard : Fragment() {
    private lateinit var homeButton:ImageButton
    private lateinit var switch:Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeButton = view.findViewById(R.id.btnSettingsToHome)
        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_leaderboard_to_FirstFragment)
        }

        switch = view.findViewById(R.id.switch1)

        sharedPreferences = requireActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
        switch.isChecked = sharedPreferences.getBoolean("switch_state", false)

        switch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("switch_state", isChecked).apply()
            if (isChecked) {
                // Turn on the background music
                (activity as MainActivity).startBackgroundMusic()
            } else {
                // Turn off the background music
                (activity as MainActivity).stopBackgroundMusic()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireActivity().getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("switch_state", false)) {
            (activity as MainActivity).startBackgroundMusic()
        }
    }

}